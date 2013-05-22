package at.tugraz.musicdroid.menutest;

import java.util.ArrayList;

import com.jayway.android.robotium.solo.Solo;
import at.tugraz.musicdroid.MainActivity;
import at.tugraz.musicdroid.R;
import at.tugraz.musicdroid.types.SoundType;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.GridView;

public class AddSoundDialogTest extends ActivityInstrumentationTestCase2<MainActivity> {
	protected Solo solo = null;
	protected int screenWidth;
	protected int screenHeight;
	protected UITestHelper helper;
	
	public AddSoundDialogTest() {
		super(MainActivity.class);
	}
	
	@Override
	protected void setUp() {
		 solo = new Solo(getInstrumentation(), getActivity());			
		 screenWidth = solo.getCurrentActivity().getWindowManager().getDefaultDisplay().getWidth();
		 screenHeight = solo.getCurrentActivity().getWindowManager().getDefaultDisplay().getHeight();
		 helper = new UITestHelper(solo, getActivity());
	}
	
	public void testAddSoundButton()
	{
		 /*Test if dialog disappears when clicking on screen*/
		 solo.clickOnView(getActivity().findViewById(R.id.btn_add));
		 assertTrue("Add Sounds dialog not visible",
					solo.waitForText(solo.getString(R.string.dialog_add_sound_title), 1, 10000, true));
		 solo.sleep(1000);

		 solo.clickOnScreen(20, screenHeight/2);
		 solo.sleep(1000);
		 assertFalse("Add Sounds dialog not visible",
		 			solo.waitForText(solo.getString(R.string.dialog_add_sound_title), 1, 1000, true));
		 solo.sleep(1000);

		 
		 /*Test if dialog disappears when clicking back-button */
		 solo.clickOnView(getActivity().findViewById(R.id.btn_add));
		 assertTrue("Add Sounds dialog not visible",
					solo.waitForText(solo.getString(R.string.dialog_add_sound_title), 1, 10000, true));
		 solo.sleep(1000);
		 
		 solo.goBack();
		 assertFalse("Add Sounds dialog not visible",
		 			solo.waitForText(solo.getString(R.string.dialog_add_sound_title), 1, 1000, true));
		 solo.sleep(1000);
	}
	
	public void testDialogMenuEntries()
	{
		/*Test if all menu entries are there*/
		solo.clickOnView(getActivity().findViewById(R.id.btn_add));
		assertTrue("Add Sounds dialog not visible",
					solo.waitForText(solo.getString(R.string.dialog_add_sound_title), 1, 10000, true));
		solo.sleep(1000);
		SoundType[] possibleValues = SoundType.class.getEnumConstants();
		for(int i = 0; i < possibleValues.length; i++)
		{
		  assertTrue("Menu Entry " + possibleValues[i].name() + " not visibile",
  		     	      solo.waitForText(solo.getString(possibleValues[i].getNameResource()), 1, 1000, true));
		}
	}
	
	

}
