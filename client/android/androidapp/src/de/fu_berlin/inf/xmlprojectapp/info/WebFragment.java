package de.fu_berlin.inf.xmlprojectapp.info;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import de.fu_berlin.inf.xmlprojectapp.R;

public class WebFragment extends DialogFragment
{
	private String xml;
	private String xslt;

	private void setXML(String xml)
	{
		this.xml = xml;
	}

	private void setXSLT(String xslt)
	{
		this.xslt = xslt;
	}

	public static WebFragment newInstance(String xml, String xslt)
	{
		WebFragment f = new WebFragment();
		f.setXML(xml);
		f.setXSLT(xslt);
		f.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog);
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	        Bundle savedInstanceState)
	{
		final View rootView = new LinearLayout(this.getActivity());

		rootView.setLayoutParams(new LinearLayout.LayoutParams(
		        LinearLayout.LayoutParams.WRAP_CONTENT,
		        LinearLayout.LayoutParams.WRAP_CONTENT));

		WebView webview = new WebView(this.getActivity());
		webview.setId(42);
		webview.getSettings().setBuiltInZoomControls(true);
		((LinearLayout) rootView).addView(webview);

		return rootView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);

		this.renderXML(xml, xslt);
	}

	public void renderXML(String xml, String xslt)
	{
		((WebView) this.getView().findViewById(42)).loadData(
		        WebFragment.transformXML(xml, xslt), "text/html", null);
		Log.i("web", WebFragment.transformXML(xml, xslt));
	}

	private static String transformXML(String xml, String xslt)
	{
		String html = "";

		try
		{
			InputStream xmlStream = new ByteArrayInputStream(
			        xml.getBytes("UTF-8"));
			InputStream xsltStream = new ByteArrayInputStream(
			        xslt.getBytes("UTF-8"));

			StringWriter htmlWriter = new StringWriter();
			TransformerFactory
			        .newInstance()
			        .newTransformer(new StreamSource(xsltStream))
			        .transform(new StreamSource(xmlStream),
			                new StreamResult(htmlWriter));
			html = htmlWriter.toString();

			xmlStream.close();
			xsltStream.close();
		} catch(UnsupportedEncodingException e)
		{
			e.printStackTrace();
		} catch(TransformerConfigurationException e)
		{
			e.printStackTrace();
		} catch(TransformerFactoryConfigurationError e)
		{
			e.printStackTrace();
		} catch(TransformerException e)
		{
			e.printStackTrace();
		} catch(IOException e)
		{
			e.printStackTrace();
		} catch(Exception e)
		{
			e.printStackTrace();
		}

		return html;
	}

}
