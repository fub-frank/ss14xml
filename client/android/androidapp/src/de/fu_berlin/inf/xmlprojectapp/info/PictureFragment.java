package de.fu_berlin.inf.xmlprojectapp.info;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Base64;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import de.fu_berlin.inf.xmlprojectapp.R;
import de.fu_berlin.inf.xmlprojectapp.content.historic.HistoricContentDelivery;
import de.fu_berlin.inf.xmlprojectapp.content.historic.serialisation.HistoricImage;
import de.fu_berlin.inf.xmlprojectapp.content.historic.serialisation.HistoricMetadata;

public class PictureFragment extends DialogFragment
{
	private String flickerid;
	private String filename;
	private int imagecount;

	private boolean keep_activity_alive;

	public static PictureFragment newInstance(String flickerid,
	        String filename, int imagecount)
	{
		PictureFragment f = new PictureFragment();
		Bundle args = new Bundle();
		args.putString("flickerid", flickerid);
		args.putString("filename", filename);
		args.putInt("imagecount", imagecount);
		f.setArguments(args);
		f.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog);
		return f;
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		if(!keep_activity_alive)
		{
			this.getActivity().finish();
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.flickerid = this.getArguments().getString("flickerid");
		this.filename = this.getArguments().getString("filename");
		this.imagecount = this.getArguments().getInt("imagecount");
		this.keep_activity_alive = false;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		Dialog dialog = super.onCreateDialog(savedInstanceState);
		// dialog.setCanceledOnTouchOutside(true);

		return dialog;
	}

	private class MaximumSizedHorizontalScrollView extends HorizontalScrollView
	{
		private Point size;

		public MaximumSizedHorizontalScrollView(Context context)
		{
			super(context);
			size = new Point();
		}

		@Override
		protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
		{
			WindowManager wm = (WindowManager) PictureFragment.this
			        .getActivity().getSystemService(Context.WINDOW_SERVICE);
			Display display = wm.getDefaultDisplay();
			display.getSize(size);
			widthMeasureSpec = MeasureSpec.makeMeasureSpec(
			        (int) Math.floor(size.x * 0.8), MeasureSpec.AT_MOST);
			heightMeasureSpec = MeasureSpec.makeMeasureSpec(
			        (int) Math.floor(size.y * 0.8), MeasureSpec.AT_MOST);
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	        Bundle savedInstanceState)
	{
		final HorizontalScrollView rootView = new MaximumSizedHorizontalScrollView(
		        this.getActivity());
		rootView.setLayoutParams(new FrameLayout.LayoutParams(
		        FrameLayout.LayoutParams.WRAP_CONTENT,
		        FrameLayout.LayoutParams.WRAP_CONTENT));

		final LinearLayout scroll = new LinearLayout(this.getActivity());
		scroll.setLayoutParams(new LinearLayout.LayoutParams(
		        LinearLayout.LayoutParams.MATCH_PARENT,
		        LinearLayout.LayoutParams.MATCH_PARENT));
		scroll.setOrientation(LinearLayout.HORIZONTAL);
		scroll.setAlpha(1.0f);
		rootView.addView(scroll);

		final HistoricContentDelivery content = new HistoricContentDelivery();

		final ImageView[] images = new ImageView[PictureFragment.this.imagecount];

		for(int i = 0; i < PictureFragment.this.imagecount; i += 1)
		{
			images[i] = (ImageView) new ImageView(
			        PictureFragment.this.getActivity());
			images[i].setLayoutParams(new LinearLayout.LayoutParams(
			        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			scroll.addView(images[i]);

			content.getImageAsync(
			        PictureFragment.this.filename,
			        1 + i,
			        new HistoricContentDelivery.Callback<java.util.Map.Entry<Integer, HistoricImage>>()
			        {
				        @Override
				        public void process(
				                java.util.Map.Entry<Integer, HistoricImage> arg)
				        {
					        ByteArrayInputStream is = new ByteArrayInputStream(
					                Base64.decode(arg.getValue().data,
					                        Base64.DEFAULT));
					        Bitmap bitmap = BitmapFactory.decodeStream(is);

					        Point size = new Point();
					        PictureFragment.this.getActivity()
					                .getWindowManager().getDefaultDisplay()
					                .getSize(size);

					        int j = arg.getKey() - 1;
					        images[j].setAdjustViewBounds(true);
					        images[j].setMaxWidth((int) Math
					                .floor(size.x * 0.8));
					        images[j].setMaxHeight((int) Math
					                .floor(size.y * 0.8));

					        images[j].setImageBitmap(bitmap);
				        }
			        });

			images[i].setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View arg0)
				{
					(new HistoricContentDelivery())
					        .getMetadataAsync(
					                PictureFragment.this.flickerid,
					                new HistoricContentDelivery.Callback<HistoricMetadata>()
					                {
						                @Override
						                public void process(HistoricMetadata arg)
						                {
							                try
							                {
								                InputStream xsltStream = PictureFragment.this
								                        .getActivity()
								                        .getAssets()
								                        .open("xslt.xml");
								                java.util.Scanner scanner = new java.util.Scanner(
								                        xsltStream);
								                scanner.useDelimiter("\\A");
								                String xslt = scanner.hasNext() ? scanner
								                        .next() : "";
								                scanner.close();
								                String xml = arg.xml;

								                if(xml != null)
								                {
									                WebFragment web = WebFragment
									                        .newInstance(xml,
									                                xslt);
									                web.show(
									                        PictureFragment.this
									                                .getActivity()
									                                .getSupportFragmentManager(),
									                        "info_activity");
								                }
							                } catch(IOException e)
							                {
								                e.printStackTrace();
							                }
						                }

					                });
				}
			});
		}

		return rootView;
	}
}
