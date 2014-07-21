package de.fu_berlin.inf.xmlprojectapp.info;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;

public class InfoActivity extends FragmentActivity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		Bundle extras = this.getIntent().getExtras();
		String flickerid = extras.getString("flickerid");
		String filename = extras.getString("filename");
		int imagecount = extras.getInt("imagecount");

		DialogFragment dialog = PictureFragment.newInstance(flickerid, filename,
		        imagecount);
		dialog.show(this.getSupportFragmentManager(), "info_activity");
	}
}
