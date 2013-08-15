package org.catrobat.musicdroid.dialog.listener;

import android.app.Activity;
import org.catrobat.musicdroid.DrumsActivity;

public class ExportDrumSoundDialogListener extends DialogListener {
	

	public ExportDrumSoundDialogListener(Activity a) {
		super(a);
	}

	public void onStringChanged(String str)
	{

	}
	
	public void onIntChanged(int integer)
	{	
		((DrumsActivity)activity).returnToMainActivity(integer);
	}
}
