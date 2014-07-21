package de.fu_berlin.inf.xmlprojectapp.info;

import java.util.List;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationClient;

import de.fu_berlin.inf.xmlprojectapp.content.historic.HistoricContentDelivery;
import de.fu_berlin.inf.xmlprojectapp.content.historic.serialisation.HistoricImageInfo;

public class ReceiveTransitionsIntentService extends IntentService
{

	public ReceiveTransitionsIntentService()
	{
		super("ReceiveTransitionsIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent)
	{
		Log.i("", String.format("Geofence transition received: %d",
		        LocationClient.getGeofenceTransition(intent)));

		if(!LocationClient.hasError(intent))
		{
			switch(LocationClient.getGeofenceTransition(intent))
			{
				case Geofence.GEOFENCE_TRANSITION_ENTER:
					List<Geofence> triggerList = LocationClient
					        .getTriggeringGeofences(intent);
					String[] triggerIds = new String[triggerList.size()];
					for(int i = 0; i < triggerIds.length; i += 1)
					{
						triggerIds[i] = triggerList.get(i).getRequestId();
					}

					// Our Geofences are supposed to be disjunct, so we only
					// care about the very first one.
					final String flickerid = triggerIds[0];

					final HistoricContentDelivery content = new HistoricContentDelivery();

					content.getImageInfoAsync(
					        flickerid,
					        new HistoricContentDelivery.Callback<HistoricImageInfo>()
					        {

						        @Override
						        public void process(HistoricImageInfo arg)
						        {
							        Intent imageIntent = new Intent(
							                "android.intent.action.MAIN");
							        imageIntent
							                .setClass(
							                        ReceiveTransitionsIntentService.this,
							                        InfoActivity.class);
							        imageIntent
							                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

							        imageIntent
							                .putExtra("flickerid", flickerid);
							        imageIntent.putExtra("filename",
							                arg.filename);
							        imageIntent.putExtra("imagecount",
							                arg.imagecount);

							        ReceiveTransitionsIntentService.this
							                .startActivity(imageIntent);
						        }
					        });

					break;
				default:
					break;
			}
		} else
		{
			Log.e("ReceiveTransitionsIntentService", String.format(
			        "Location Services error: %d",
			        String.valueOf(LocationClient.getErrorCode(intent))));
		}

	}
}
