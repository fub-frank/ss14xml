package de.fu_berlin.inf.xmlprojectapp.content.historic;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;

import android.util.Log;

import com.alexgilleran.icesoap.exception.SOAPException;
import com.alexgilleran.icesoap.observer.SOAP11ListObserver;
import com.alexgilleran.icesoap.observer.SOAP11Observer;
import com.alexgilleran.icesoap.request.Request;
import com.alexgilleran.icesoap.request.RequestFactory;
import com.alexgilleran.icesoap.request.SOAP11ListRequest;
import com.alexgilleran.icesoap.request.SOAP11Request;
import com.alexgilleran.icesoap.request.impl.RequestFactoryImpl;
import com.alexgilleran.icesoap.soapfault.SOAP11Fault;

import de.fu_berlin.inf.xmlprojectapp.content.historic.envelopes.GetGeofencesEnvelope;
import de.fu_berlin.inf.xmlprojectapp.content.historic.envelopes.GetImageEnvelope;
import de.fu_berlin.inf.xmlprojectapp.content.historic.envelopes.GetImageInfoEnvelope;
import de.fu_berlin.inf.xmlprojectapp.content.historic.envelopes.GetMetadataEnvelope;
import de.fu_berlin.inf.xmlprojectapp.content.historic.serialisation.HistoricImage;
import de.fu_berlin.inf.xmlprojectapp.content.historic.serialisation.HistoricImageInfo;
import de.fu_berlin.inf.xmlprojectapp.content.historic.serialisation.HistoricLocation;
import de.fu_berlin.inf.xmlprojectapp.content.historic.serialisation.HistoricMetadata;

public class HistoricContentDelivery
{
	public interface Callback<T>
	{
		public void process(T arg);
	}

	private final ExecutorService pool;
	private final RequestFactory requestFactory;

	public HistoricContentDelivery()
	{
		this.pool = Executors.newCachedThreadPool();
		this.requestFactory = new RequestFactoryImpl();
	}

	public Future<List<HistoricLocation>> getGeofencesAsync(
	        HistoricContentDelivery.Callback<HistoricLocation> onNextGeofence,
	        HistoricContentDelivery.Callback<List<HistoricLocation>> onAllGeofences)
	{
		final HistoricContentDelivery.Callback<HistoricLocation> _onNextGeofence = onNextGeofence;
		final HistoricContentDelivery.Callback<List<HistoricLocation>> _onAllGeofences = onAllGeofences;

		final Semaphore finish = new Semaphore(0, false);

		return this.pool.submit(new Callable<List<HistoricLocation>>()
		{
			@Override
			public List<HistoricLocation> call()
			{
				SOAP11ListRequest<HistoricLocation> request = HistoricContentDelivery.this.requestFactory
				        .buildListRequest(
				                "http://xml.df-root.com:8370/geodata?wsdl",
				                new GetGeofencesEnvelope(),
				                "http://xml.df-root.com:8370/geodata",
				                HistoricLocation.class);

				request.execute(new SOAP11ListObserver<HistoricLocation>()
				{
					@Override
					public void onNewItem(
					        Request<List<HistoricLocation>, SOAP11Fault> arg0,
					        HistoricLocation arg1)
					{
						if(_onNextGeofence != null
						        && arg0.getSOAPFault() == null
						        && arg0.getException() == null)
						{
							_onNextGeofence.process(arg1);
						}
					}

					@Override
					public void onCompletion(
					        Request<List<HistoricLocation>, SOAP11Fault> arg0)
					{
						if(_onAllGeofences != null
						        && arg0.getSOAPFault() == null
						        && arg0.getException() == null)
						{
							_onAllGeofences.process(arg0.getResult());
						}

						finish.release();
					}

					@Override
					public void onException(
					        Request<List<HistoricLocation>, SOAP11Fault> arg0,
					        SOAPException arg1)
					{
						arg1.printStackTrace();

						SOAP11Request<List<HistoricLocation>> request = HistoricContentDelivery.this.requestFactory
						        .buildListRequest(
						                "http://xml.df-root.com:8370/geodata?wsdl",
						                new GetGeofencesEnvelope(),
						                "http://xml.df-root.com:8370/geodata",
						                HistoricLocation.class);
						request.setDebugMode(true);
						request.execute(new SOAP11Observer<List<HistoricLocation>>()
						{

							@Override
							public void onCompletion(
							        Request<List<HistoricLocation>, SOAP11Fault> arg0)
							{}

							@Override
							public void onException(
							        Request<List<HistoricLocation>, SOAP11Fault> arg0,
							        SOAPException arg1)
							{
								Log.e(HistoricContentDelivery.class.getName(),
								        String.format("SOAP Request: %s",
								                arg0.getRequestXML()));
								Log.e(HistoricContentDelivery.class.getName(),
								        String.format("SOAP Response: %s",
								                arg0.getResponseXML()));
							}
						});
					}
				});

				finish.acquireUninterruptibly();

				return request.getResult();
			}
		});
	}

	public Future<HistoricImageInfo> getImageInfoAsync(String flickerid,
	        HistoricContentDelivery.Callback<HistoricImageInfo> onComplete)
	{
		final String _flickerid = flickerid;
		final HistoricContentDelivery.Callback<HistoricImageInfo> _onComplete = onComplete;

		final Semaphore finish = new Semaphore(0, false);

		return this.pool.submit(new Callable<HistoricImageInfo>()
		{
			@Override
			public HistoricImageInfo call()
			{
				SOAP11Request<HistoricImageInfo> request = HistoricContentDelivery.this.requestFactory
				        .buildRequest(
				                "http://xml.df-root.com:8370/imagedata?wsdl",
				                new GetImageInfoEnvelope(_flickerid),
				                "http://xml.df-root.com:8370/imagedata",
				                HistoricImageInfo.class);

				request.execute(new SOAP11Observer<HistoricImageInfo>()
				{
					@Override
					public void onCompletion(
					        Request<HistoricImageInfo, SOAP11Fault> arg0)
					{
						if(_onComplete != null && arg0.getSOAPFault() == null
						        && arg0.getException() == null)
						{
							_onComplete.process(arg0.getResult());
						}

						finish.release();
					}

					@Override
					public void onException(
					        Request<HistoricImageInfo, SOAP11Fault> arg0,
					        SOAPException arg1)
					{
						arg1.printStackTrace();

						SOAP11Request<HistoricImageInfo> request = HistoricContentDelivery.this.requestFactory
						        .buildRequest(
						                "http://xml.df-root.com:8370/imagedata?wsdl",
						                new GetImageInfoEnvelope(_flickerid),
						                "http://xml.df-root.com:8370/imagedata",
						                HistoricImageInfo.class);
						request.setDebugMode(true);
						request.execute(new SOAP11Observer<HistoricImageInfo>()
						{

							@Override
							public void onCompletion(
							        Request<HistoricImageInfo, SOAP11Fault> arg0)
							{}

							@Override
							public void onException(
							        Request<HistoricImageInfo, SOAP11Fault> arg0,
							        SOAPException arg1)
							{
								Log.e(HistoricContentDelivery.class.getName(),
								        String.format("SOAP Request: %s",
								                arg0.getRequestXML()));
								Log.e(HistoricContentDelivery.class.getName(),
								        String.format("SOAP Response: %s",
								                arg0.getResponseXML()));
							}
						});
					}
				});

				finish.acquireUninterruptibly();

				return request.getResult();
			}
		});
	}

	public Future<java.util.Map.Entry<Integer, HistoricImage>> getImageAsync(
	        String filename,
	        int number,
	        HistoricContentDelivery.Callback<java.util.Map.Entry<Integer, HistoricImage>> onComplete)
	{
		final String _filename = filename;
		final int _number = number;
		final HistoricContentDelivery.Callback<java.util.Map.Entry<Integer, HistoricImage>> _onComplete = onComplete;

		final Semaphore finish = new Semaphore(0, false);

		return this.pool
		        .submit(new Callable<java.util.Map.Entry<Integer, HistoricImage>>()
		        {
			        @Override
			        public java.util.Map.Entry<Integer, HistoricImage> call()
			        {
				        SOAP11Request<HistoricImage> request = HistoricContentDelivery.this.requestFactory
				                .buildRequest(
				                        "http://xml.df-root.com:8370/imagedata?wsdl",
				                        new GetImageEnvelope(_filename, _number),
				                        "http://xml.df-root.com:8370/imagedata",
				                        HistoricImage.class);

				        request.execute(new SOAP11Observer<HistoricImage>()
				        {
					        @Override
					        public void onCompletion(
					                Request<HistoricImage, SOAP11Fault> arg0)
					        {
						        if(_onComplete != null
						                && arg0.getSOAPFault() == null
						                && arg0.getException() == null)
						        {
							        _onComplete
							                .process(new java.util.AbstractMap.SimpleEntry<Integer, HistoricImage>(
							                        _number, arg0.getResult()));
						        }

						        finish.release();
					        }

					        @Override
					        public void onException(
					                Request<HistoricImage, SOAP11Fault> arg0,
					                SOAPException arg1)
					        {
						        arg1.printStackTrace();

						        SOAP11Request<HistoricImage> request = HistoricContentDelivery.this.requestFactory
						                .buildRequest(
						                        "http://xml.df-root.com:8370/imagedata?wsdl",
						                        new GetImageEnvelope(_filename,
						                                _number),
						                        "http://xml.df-root.com:8370/imagedata",
						                        HistoricImage.class);
						        request.setDebugMode(true);
						        request.execute(new SOAP11Observer<HistoricImage>()
						        {

							        @Override
							        public void onCompletion(
							                Request<HistoricImage, SOAP11Fault> arg0)
							        {}

							        @Override
							        public void onException(
							                Request<HistoricImage, SOAP11Fault> arg0,
							                SOAPException arg1)
							        {
								        Log.e(HistoricContentDelivery.class
								                .getName(), String.format(
								                "SOAP Request: %s",
								                arg0.getRequestXML()));
								        Log.e(HistoricContentDelivery.class
								                .getName(), String.format(
								                "SOAP Response: %s",
								                arg0.getResponseXML()));
							        }
						        });
					        }
				        });

				        finish.acquireUninterruptibly();

				        return new java.util.AbstractMap.SimpleEntry<Integer, HistoricImage>(
				                _number, request.getResult());
			        }
		        });
	}

	public Future<HistoricMetadata> getMetadataAsync(String flickerid,
	        HistoricContentDelivery.Callback<HistoricMetadata> onComplete)
	{
		final String _flickerid = flickerid;
		final HistoricContentDelivery.Callback<HistoricMetadata> _onComplete = onComplete;

		final Semaphore finish = new Semaphore(0, false);

		return this.pool.submit(new Callable<HistoricMetadata>()
		{
			@Override
			public HistoricMetadata call()
			{
				SOAP11Request<HistoricMetadata> request = HistoricContentDelivery.this.requestFactory
				        .buildRequest(
				                "http://xml.df-root.com:8370/metadata?wsdl",
				                new GetMetadataEnvelope(_flickerid),
				                "http://xml.df-root.com:8370/metadata",
				                HistoricMetadata.class);
				request.setDebugMode(true);

				request.execute(new SOAP11Observer<HistoricMetadata>()
				{
					@Override
					public void onCompletion(
					        Request<HistoricMetadata, SOAP11Fault> arg0)
					{
						if(_onComplete != null && arg0.getSOAPFault() == null
						        && arg0.getException() == null)
						{
							HistoricMetadata result = arg0.getResult();
							result.xml = arg0.getResponseXML();
							_onComplete.process(result);
						}

						finish.release();
					}

					@Override
					public void onException(
					        Request<HistoricMetadata, SOAP11Fault> arg0,
					        SOAPException arg1)
					{
						arg1.printStackTrace();
					}
				});

				finish.acquireUninterruptibly();

				return request.getResult();
			}
		});
	}

}
