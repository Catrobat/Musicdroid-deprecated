package at.tugraz.musicdroid.menutest;

import com.jayway.android.robotium.solo.Solo;

import at.tugraz.musicdroid.MainActivity;
import at.tugraz.musicdroid.R;
import at.tugraz.musicdroid.soundmixer.Statusbar;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.ImageButton;

public class PlayButtonMainMenuTest extends ActivityInstrumentationTestCase2<MainActivity> {
	protected Solo solo = null;
	protected ImageButton buttonPlay = null;
	protected ImageButton buttonStop = null;
	
	public PlayButtonMainMenuTest() {
		super(MainActivity.class);
	}
	
	@Override
	protected void setUp() {
		 solo = new Solo(getInstrumentation(), getActivity());
		 buttonPlay = (ImageButton) getActivity().findViewById(R.id.btn_play);
	}
	
	public void testButtonChangesOnClick()
	{
		solo.clickOnView((ImageButton) getActivity().findViewById(R.id.btn_play));
		solo.sleep(1000);
		assertFalse(Statusbar.getInstance().getDisplayPlayButton());
		solo.clickOnView((ImageButton) getActivity().findViewById(R.id.btn_play));
		solo.sleep(1000);
		assertTrue(Statusbar.getInstance().getDisplayPlayButton());
	}
	
	

}
