package de.fu_berlin.inf.xmlprojectapp.main;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import de.fu_berlin.inf.xmlprojectapp.R;

public class MockLocationDialog extends DialogFragment
{
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();

		View rootView = inflater.inflate(R.layout.mock_dialog, null);
		rootView.findViewById(R.id.mock_close).setOnClickListener(
		        new OnClickListener()
		        {
			        @Override
			        public void onClick(View view)
			        {
				        MockLocationDialog.this.getDialog().cancel();
			        }
		        });

		rootView.findViewById(R.id.mock_toggle).setOnClickListener(
		        new OnClickListener()
		        {
			        @Override
			        public void onClick(View view)
			        {
				        try
				        {
					        boolean mock_mode = !((LocationFragment) MockLocationDialog.this
					                .getTargetFragment()).mock_mode;
					        ((LocationFragment) MockLocationDialog.this
					                .getTargetFragment()).lc
					                .setMockMode(mock_mode);
					        getDialog().findViewById(R.id.mock_set).setEnabled(
					                mock_mode);
					        ((LocationFragment) MockLocationDialog.this
					                .getTargetFragment()).mock_mode = mock_mode;
				        } catch(SecurityException e)
				        {
					        new AlertDialog.Builder(MockLocationDialog.this
					                .getActivity())
					                .setTitle("Mock location")
					                .setMessage(e.getMessage())
					                .setPositiveButton(
					                        "Got it",
					                        new DialogInterface.OnClickListener()
					                        {
						                        @Override
						                        public void onClick(
						                                DialogInterface dialog,
						                                int which)
						                        {}
					                        })
					                .setIcon(android.R.drawable.ic_dialog_alert)
					                .show();
				        }
			        }
		        });

		rootView.findViewById(R.id.mock_set).setOnClickListener(
		        new OnClickListener()
		        {
			        @Override
			        public void onClick(View view)
			        {

				        Location mockLocation = new Location(
				                LocationManager.GPS_PROVIDER);
				        mockLocation.setTime(System.currentTimeMillis());
				        mockLocation.setElapsedRealtimeNanos(SystemClock
				                .elapsedRealtimeNanos());
				        mockLocation.setAccuracy(1.0f);
				        try
				        {
					        mockLocation.setLatitude(Double
					                .valueOf(((EditText) getDialog()
					                        .findViewById(R.id.mock_latitude))
					                        .getText().toString()));
					        mockLocation.setLongitude(Double
					                .valueOf(((EditText) getDialog()
					                        .findViewById(R.id.mock_longitude))
					                        .getText().toString()));

					        ((LocationFragment) MockLocationDialog.this
					                .getTargetFragment()).lc
					                .setMockLocation(mockLocation);

					        MockLocationDialog.this.getDialog().dismiss();
				        } catch(NumberFormatException e)
				        {
					        new AlertDialog.Builder(MockLocationDialog.this
					                .getActivity())
					                .setTitle("Mock location")
					                .setMessage(
					                        "You must enter valid latitude and longitude!")
					                .setPositiveButton(
					                        "Got it",
					                        new DialogInterface.OnClickListener()
					                        {
						                        @Override
						                        public void onClick(
						                                DialogInterface dialog,
						                                int which)
						                        {}
					                        })
					                .setIcon(android.R.drawable.ic_dialog_alert)
					                .show();
				        }
			        }
		        });

		rootView.findViewById(R.id.mock_set)
		        .setEnabled(
		                ((LocationFragment) MockLocationDialog.this
		                        .getTargetFragment()).mock_mode);

		builder.setView(rootView);
		return builder.create();
	}
}
