package at.tugraz.musicdroid.menutest;

import com.jayway.android.robotium.solo.Solo;

import at.tugraz.musicdroid.MainActivity;
import at.tugraz.musicdroid.R;
import at.tugraz.musicdroid.soundmixer.SoundMixer;
import at.tugraz.musicdroid.soundmixer.Statusbar;
import at.tugraz.musicdroid.types.SoundType;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

public class StatusbarMainMenuTest extends ActivityInstrumentationTestCase2<MainActivity> {
	protected Solo solo = null;
	protected ImageButton playImageButton = null;
	protected ImageButton rewindImageButton = null;
	protected UITestHelper helper = null;
	
	public StatusbarMainMenuTest() {
		super(MainActivity.class);
	}
	
	@Override
	protected void setUp() {
		solo = new Solo(getInstrumentation(), getActivity());
		helper = new UITestHelper(solo, getActivity());
		playImageButton = (ImageButton) getActivity().findViewById(R.id.btn_play);
		rewindImageButton = (ImageButton) getActivity().findViewById(R.id.btn_rewind);
	}
	
	public void testButtonChangesOnClick()
	{
		helper.addTrack(SoundType.DRUMS);
		solo.clickOnView(playImageButton);
		solo.sleep(1000);
		assertFalse(Statusbar.getInstance().getDisplayPlayButton());
		solo.clickOnView(playImageButton);
		solo.sleep(1000);
		assertTrue(Statusbar.getInstance().getDisplayPlayButton());
	}
	
	public void testRewindButtonAppearsOnPlayButtonClick()
	{
		helper.addTrack(SoundType.DRUMS);
		assertTrue(rewindImageButton.getVisibility() == View.INVISIBLE);
		solo.clickOnView(playImageButton);
		solo.sleep(5000);
		solo.clickOnView(playImageButton);
		assertTrue(rewindImageButton.getVisibility() == View.VISIBLE);
	}
	
	public void testRewindButton()
	{
		testRewindButtonAppearsOnPlayButtonClick();
		assertTrue(SoundMixer.getInstance().getStopPointFromEventHandler() > 4);
		solo.clickOnView(rewindImageButton);
		assertTrue(SoundMixer.getInstance().getStopPointFromEventHandler() == 0);	
	}
	
	

}
