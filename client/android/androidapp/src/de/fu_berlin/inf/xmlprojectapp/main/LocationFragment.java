package de.fu_berlin.inf.xmlprojectapp.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationStatusCodes;

import de.fu_berlin.inf.xmlprojectapp.R;
import de.fu_berlin.inf.xmlprojectapp.content.historic.HistoricContentDelivery;
import de.fu_berlin.inf.xmlprojectapp.content.historic.serialisation.HistoricImageInfo;
import de.fu_berlin.inf.xmlprojectapp.content.historic.serialisation.HistoricLocation;
import de.fu_berlin.inf.xmlprojectapp.info.InfoActivity;
import de.fu_berlin.inf.xmlprojectapp.info.ReceiveTransitionsIntentService;

public class LocationFragment extends Fragment implements
        GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener, LocationListener
{

	LocationClient lc;
	boolean mock_mode;

	final HistoricContentDelivery content;
	final Random r;

	public LocationFragment()
	{
		super();
		this.content = new HistoricContentDelivery();
		this.r = new Random(System.currentTimeMillis());
	}

	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		this.lc = new LocationClient(activity, this, this);
		this.lc.connect();
	}

	@Override
	public void onDetach()
	{
		lc.disconnect();
		super.onDetach();
	}

	private PendingIntent getTransitionPendingIntent()
	{
		Intent intent = new Intent(this.getActivity(),
		        ReceiveTransitionsIntentService.class);
		return PendingIntent.getService(this.getActivity(), 0, intent,
		        PendingIntent.FLAG_UPDATE_CURRENT);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	        Bundle savedInstanceState)
	{
		final View rootView = inflater.inflate(R.layout.location_fragment,
		        container, false);

		((Button) rootView.findViewById(R.id.mock_settings))
		        .setOnClickListener(new OnClickListener()
		        {
			        @Override
			        public void onClick(View view)
			        {
				        DialogFragment dialog = new MockLocationDialog();
				        dialog.setTargetFragment(LocationFragment.this, 0);
				        dialog.show(LocationFragment.this.getFragmentManager(),
				                "mock_location");
			        }
		        });

		((Button) rootView.findViewById(R.id.mock_settings)).setEnabled(false);

		((Button) rootView.findViewById(R.id.get_location))
		        .setOnClickListener(new OnClickListener()
		        {
			        @Override
			        public void onClick(View view)
			        {
				        Location location = lc.getLastLocation();

				        if(location != null)
				        {
					        ((TextView) LocationFragment.this.getView()
					                .findViewById(R.id.textview_latitude))
					                .setText(String.valueOf(location
					                        .getLatitude()));
					        ((TextView) LocationFragment.this.getView()
					                .findViewById(R.id.textview_longitude))
					                .setText(String.valueOf(location
					                        .getLongitude()));
				        } else
				        {
					        ((TextView) LocationFragment.this.getView()
					                .findViewById(R.id.textview_latitude))
					                .setText("");
					        ((TextView) LocationFragment.this.getView()
					                .findViewById(R.id.textview_longitude))
					                .setText("");
				        }

				        if(!LocationFragment.this.mock_mode)
				        {
					        lc.requestLocationUpdates(
					                LocationRequest
					                        .create()
					                        .setPriority(
					                                LocationRequest.PRIORITY_HIGH_ACCURACY),
					                LocationFragment.this);
				        }
			        }
		        });

		((Button) rootView.findViewById(R.id.get_location)).setEnabled(false);
		((Button) rootView.findViewById(R.id.trigger_random_location))
		        .setEnabled(false);

		return rootView;
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0)
	{
		Log.e(getTag(), arg0.toString());
	}

	@Override
	public void onConnected(Bundle connectionHint)
	{
		this.getView().findViewById(R.id.mock_settings).setEnabled(true);
		this.getView().findViewById(R.id.get_location).setEnabled(true);
		final Button trigger = (Button) this.getView().findViewById(
		        R.id.trigger_random_location);

		// TODO: This should only occur once, not on every connect to the
		// Google Play Services.
		this.content.getGeofencesAsync(null,
		        new HistoricContentDelivery.Callback<List<HistoricLocation>>()
		        {
			        @Override
			        public void process(List<HistoricLocation> __geofences)
			        {
				        final List<HistoricLocation> _geofences = __geofences;

				        trigger.setEnabled(true);
				        trigger.setOnClickListener(new OnClickListener()
				        {
					        @Override
					        public void onClick(View v)
					        {
						        final HistoricLocation historicLocation = _geofences
						                .get(LocationFragment.this.r
						                        .nextInt(_geofences.size()));

						        final HistoricContentDelivery content = new HistoricContentDelivery();

						        content.getImageInfoAsync(
						                historicLocation.flickerid,
						                new HistoricContentDelivery.Callback<HistoricImageInfo>()
						                {

							                @Override
							                public void process(
							                        HistoricImageInfo arg)
							                {
								                Intent imageIntent = new Intent(
								                        "android.intent.action.MAIN");
								                imageIntent.setClass(
								                        LocationFragment.this
								                                .getActivity(),
								                        InfoActivity.class);
								                imageIntent
								                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

								                imageIntent
								                        .putExtra(
								                                "flickerid",
								                                historicLocation.flickerid);
								                imageIntent.putExtra(
								                        "filename",
								                        arg.filename);
								                imageIntent.putExtra(
								                        "imagecount",
								                        arg.imagecount);

								                LocationFragment.this
								                        .startActivity(imageIntent);
							                }
						                });

					        }
				        });

				        Geofence.Builder builder = new Geofence.Builder();
				        builder.setExpirationDuration(TimeUnit.MILLISECONDS
				                .convert(15L, TimeUnit.MINUTES));
				        builder.setLoiteringDelay((int) TimeUnit.MILLISECONDS
				                .convert(10L, TimeUnit.SECONDS));
				        // builder.setNotificationResponsiveness(0);
				        // TODO: This was supposed to be Geofence.GEOFENCE_TRANSITION_DWELL, but under Android 4.4 KitKat they may
				        //       experience severe delays (see https://code.google.com/p/gmaps-api-issues/issues/detail?id=6396),
				        //       so for now use GEOFENCE_TRANSITION_ENTER instead.
				        //       Possible solution: Keep track of enter and exit events, use custom events to recreate dwell behaviour.
				        builder.setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER);

				        // TODO: The Google Play Services allows only
				        // 100
				        // Geofences per application per device,
				        // but we have more than 100 locations. For now,
				        // we just use the first 100
				        // locations for Geofences, but that should be
				        // changed so we can detect being near
				        // any one of them (and not only the first 100).
				        // Possible solution: Group locations that are
				        // near each other together to form
				        // "bigger" Geofences and track those bigger
				        // Geofences (which we have to calculate
				        // in such a way that there are at most 100).
				        // Once entering one of the bigger Geofences,
				        // remove all Geofences and add only those
				        // actual, smaller Geofences, that belong to
				        // the bigger Geofence.
				        final int num_geofences = Math.min(100,
				                _geofences.size());

				        Log.i("", String.format("Registering %d geofences",
				                num_geofences));
				        final List<Geofence> geofences = new ArrayList<Geofence>(
				                num_geofences);

				        for(int i = 0; i < num_geofences; i += 1)
				        {
					        HistoricLocation geofence = _geofences.get(i);
					        builder.setCircularRegion(geofence.latitude,
					                geofence.longitude, geofence.radius);
					        builder.setRequestId(geofence.flickerid);
					        geofences.add(builder.build());
				        }
				        LocationFragment.this.lc.addGeofences(
				                geofences,
				                LocationFragment.this
				                        .getTransitionPendingIntent(),
				                new LocationClient.OnAddGeofencesResultListener()
				                {
					                @Override
					                public void onAddGeofencesResult(
					                        int statusCode,
					                        String[] geofenceRequestIds)
					                {
						                if(LocationStatusCodes.SUCCESS != statusCode)
						                {
							                Log.e("",
							                        "Could not add following geofences because of "
							                                + String.valueOf(statusCode)
							                                + " :");
							                for(String arg : geofenceRequestIds)
							                {
								                Log.e("", arg);
							                }
						                }
					                }
				                });
			        }
		        });
		// }
	}

	@Override
	public void onDisconnected()
	{
		this.getView().findViewById(R.id.mock_settings).setEnabled(false);
		this.getView().findViewById(R.id.get_location).setEnabled(false);
		this.lc.connect();
	}

	@Override
	public void onLocationChanged(Location location)
	{
		lc.removeLocationUpdates(this);
		((TextView) LocationFragment.this.getView().findViewById(
		        R.id.textview_latitude)).setText(String.valueOf(location
		        .getLatitude()));
		((TextView) LocationFragment.this.getView().findViewById(
		        R.id.textview_longitude)).setText(String.valueOf(location
		        .getLongitude()));
	}
}
