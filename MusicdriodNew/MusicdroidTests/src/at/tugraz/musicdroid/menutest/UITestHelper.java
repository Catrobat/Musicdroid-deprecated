package at.tugraz.musicdroid.menutest;

import android.app.Activity;

import com.jayway.android.robotium.solo.Solo;
import at.tugraz.musicdroid.R;
import at.tugraz.musicdroid.types.SoundType;

public class UITestHelper {
	private Solo solo;
	private Activity activity;
	
	public UITestHelper(Solo s, Activity a) {
		solo = s;
		activity = a;
	}

	public Boolean clickAddSoundButton()
	{
	   solo.clickOnView(activity.findViewById(R.id.btn_add));
	   return solo.waitForText(solo.getString(R.string.dialog_add_sound_title), 1, 10000, true);
	}
	
	public void addTrack(SoundType type)
	{
		if(clickAddSoundButton())
		{
		   solo.sleep(100);
		   solo.clickOnText(solo.getString(type.getNameResource()));
		   solo.sleep(1000);
		   return;
		}
		
	}
}
