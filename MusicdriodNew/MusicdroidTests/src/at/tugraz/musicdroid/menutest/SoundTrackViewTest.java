package at.tugraz.musicdroid.menutest;

import com.jayway.android.robotium.solo.Solo;
import at.tugraz.musicdroid.MainActivity;
import at.tugraz.musicdroid.R;
import at.tugraz.musicdroid.SoundMixer;
import at.tugraz.musicdroid.helper.Helper;
import at.tugraz.musicdroid.soundtracks.SoundTrack;
import at.tugraz.musicdroid.soundtracks.SoundTrackView;
import at.tugraz.musicdroid.types.SoundType;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

public class SoundTrackViewTest extends ActivityInstrumentationTestCase2<MainActivity> {
	protected Solo solo = null;
	protected SoundMixer mixer = null;
	protected UITestHelper ui_helper;
	private Helper helper;
	
	public SoundTrackViewTest() {
		super(MainActivity.class);
	}
	
	@Override
	protected void setUp() {
		 solo = new Solo(getInstrumentation(), getActivity());
		 mixer = SoundMixer.getInstance();
		 ui_helper = new UITestHelper(solo, getActivity());
		 helper = Helper.getInstance();
		 helper.init(getActivity());
	}
	
	public void testSoundTrackViewInactive()
	{
		ui_helper.addTrack(SoundType.DRUMS);
		ui_helper.addTrack(SoundType.PIANO);
		assertTrue(((RelativeLayout)getActivity().findViewById(R.id.sound_mixer_view)).getChildCount() == 2);
		
		SoundTrackView v = (SoundTrackView)((RelativeLayout)getActivity().findViewById(R.id.sound_mixer_view)).getChildAt(0);
		SoundTrackView check = (SoundTrackView)((RelativeLayout)getActivity().findViewById(R.id.sound_mixer_view)).getChildAt(1);
		assertTrue(check.getPlayButton().isEnabled());
		assertTrue(check.getLock().isEnabled());
		
		//Open Context Menu of first view
		solo.clickLongOnView(v.getSoundTypeImage());
		solo.waitForText(v.getSoundTrack().getName(), 1, 10000, true);
		
		//Buttons of second view should be disabled
		assertFalse(check.getPlayButton().isEnabled());
		assertFalse(check.getLock().isEnabled());
		
		//Close context menu by clicking on an entry
		solo.clickOnText(solo.getString(R.string.sound_track_menu_entry_copy));
		solo.sleep(1000);
		
		//Buttons of second view should be enabled again
		assertTrue(check.getPlayButton().isEnabled());
		assertTrue(check.getLock().isEnabled());
	}
	
	
	public void testDragOnlyInsideScreen()
	{
		int[] location = new int[2];
		int[] new_location = new int[2];
		ui_helper.addTrack(SoundType.DRUMS);
		SoundTrackView v = (SoundTrackView)((RelativeLayout)getActivity().findViewById(R.id.sound_mixer_view)).getChildAt(0);
		
		v.getLocationOnScreen(location);
		int start_x = location[0];
		int start_y = location[1];
		solo.sleep(100);
		solo.clickOnView(v.getLock());
		solo.sleep(100);
		
		solo.drag(start_x+v.getWidth()/2, -300, start_y, start_y, 1);
		solo.sleep(100);
		
		v.getLocationOnScreen(new_location);
		assertTrue(new_location[0] >= 0);
		
		solo.drag(start_x+v.getWidth()/2, helper.getScreenWidth()*2, start_y, start_y, 1);
		new_location = new int[2];
		v.getLocationOnScreen(new_location);
		
		assertTrue(new_location[0] <= helper.getScreenWidth());
		
	}
	
	public void testLock()
	{
		int[] location = new int[2];
		int[] new_location = new int[2];
		
		ui_helper.addTrack(SoundType.DRUMS);
		SoundTrackView v = (SoundTrackView)((RelativeLayout)getActivity().findViewById(R.id.sound_mixer_view)).getChildAt(0);
		
		v.getLocationOnScreen(location);
		int start_x = location[0];
		int start_y = location[1];
		
		//Drag, assert position remains unchanged because Sound Track is locked
		solo.drag(start_x+v.getWidth()/2, 300, start_y, start_y, 1);
		
		v.getLocationOnScreen(new_location);
		assertTrue(start_x == new_location[0]);
		assertTrue(start_y == new_location[1]);
		
		//Unlock Sound Track
		solo.sleep(100);
		solo.clickOnView(v.getLock());
		solo.sleep(100);
		
		//Drag again, view should move  
		new_location = new int[2];
		solo.drag(start_x+v.getWidth()/2, 300, start_y, start_y, 1);
		solo.sleep(100);
		v.getLocationOnScreen(new_location);
		int new_x = new_location[0];
		assertTrue(start_x != new_x);
		
		//Unlock again
		solo.sleep(100);
		solo.clickOnView(v.getLock());
		solo.sleep(100);
		
		//Drag again, view should not move  
		new_location = new int[2];
		solo.drag(new_x+v.getWidth()/4, -300, start_y, start_y, 1);
		solo.sleep(100);
		v.getLocationOnScreen(new_location);
		assertTrue(new_x == new_location[0]);
	}
	
	

}