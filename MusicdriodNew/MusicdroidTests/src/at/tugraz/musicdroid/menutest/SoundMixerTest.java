package at.tugraz.musicdroid.menutest;

import com.jayway.android.robotium.solo.Solo;
import at.tugraz.musicdroid.MainActivity;
import at.tugraz.musicdroid.R;
import at.tugraz.musicdroid.SoundMixer;
import at.tugraz.musicdroid.soundtracks.SoundTrack;
import at.tugraz.musicdroid.soundtracks.SoundTrackView;
import at.tugraz.musicdroid.types.SoundType;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

public class SoundMixerTest extends ActivityInstrumentationTestCase2<MainActivity> {
	protected Solo solo = null;
	protected SoundMixer mixer = null;
	protected UITestHelper helper;
	
	public SoundMixerTest() {
		super(MainActivity.class);
	}
	
	@Override
	protected void setUp() {
		 solo = new Solo(getInstrumentation(), getActivity());
		 mixer = SoundMixer.getInstance();
		 helper = new UITestHelper(solo, getActivity());
	}
	
	public void testResetSoundMixerAtNewSongYes()
	{
		helper.addTrack(SoundType.PIANO);
		helper.addTrack(SoundType.DRUMS);
		helper.addTrack(SoundType.MIC);
		assertTrue(((RelativeLayout)getActivity().findViewById(R.id.sound_mixer_view)).getChildCount() == 3);
		
		solo.clickOnMenuItem(getActivity().getResources().getString(R.string.menu_new_song));
		
		solo.waitForText(getActivity().getResources().getString(R.string.dialog_warning_new_song), 1, 10000, true);
		
		solo.clickOnText(getActivity().getResources().getString(R.string.yes));
		solo.sleep(1000);
		
		assertTrue(((RelativeLayout)getActivity().findViewById(R.id.sound_mixer_view)).getChildCount() == 0);	
	}
	
	public void testSoundMixerUnchangedAtNewSongNo()
	{
		helper.addTrack(SoundType.PIANO);
		helper.addTrack(SoundType.DRUMS);
		helper.addTrack(SoundType.MIC);
		assertTrue(((RelativeLayout)getActivity().findViewById(R.id.sound_mixer_view)).getChildCount() == 3);
		
		solo.clickOnMenuItem(getActivity().getResources().getString(R.string.menu_new_song));
		
		solo.waitForText(getActivity().getResources().getString(R.string.dialog_warning_new_song), 1, 10000, true);
		
		solo.clickOnText(getActivity().getResources().getString(R.string.no));
		solo.sleep(1000);
		
		assertTrue(((RelativeLayout)getActivity().findViewById(R.id.sound_mixer_view)).getChildCount() == 3);	
	}
	
	public void testAddTrackAtClickingAddSoundButton()
	{
		testAddSpecificTrack(SoundType.DRUMS);
		testAddSpecificTrack(SoundType.PIANO);
		testAddSpecificTrack(SoundType.MIC);
	}
	
	public void testDeleteTrack()
	{
		helper.addTrack(SoundType.PIANO);
		int number_of_childs_old = ((RelativeLayout)getActivity().findViewById(R.id.sound_mixer_view)).getChildCount();
		int number_of_tracks_old = SoundMixer.getInstance().getNumberOfTracks();
		
		SoundTrackView v = (SoundTrackView)((RelativeLayout)getActivity().findViewById(R.id.sound_mixer_view)).getChildAt(0);
		
		solo.clickLongOnView(v.getSoundTypeImage());
		solo.waitForText(v.getSoundTrack().getName(), 1, 10000, true);
		solo.clickOnText(solo.getString(R.string.sound_track_menu_entry_delete));
		solo.sleep(1000);
		int number_of_childs_new = ((RelativeLayout)getActivity().findViewById(R.id.sound_mixer_view)).getChildCount();
		int number_of_tracks_new = SoundMixer.getInstance().getNumberOfTracks();
		
		assertTrue("Child not deleted", number_of_childs_new == number_of_childs_old-1);
		assertTrue("Track not deleted", number_of_tracks_new == number_of_tracks_old-1);
	}
	
	public void testCopyTrack()
	{
		helper.addTrack(SoundType.PIANO);
		int number_of_childs_old = ((RelativeLayout)getActivity().findViewById(R.id.sound_mixer_view)).getChildCount();
		int number_of_tracks_old = SoundMixer.getInstance().getNumberOfTracks();
		
		SoundTrackView v = (SoundTrackView)((RelativeLayout)getActivity().findViewById(R.id.sound_mixer_view)).getChildAt(0);
		
		solo.clickLongOnView(v.getSoundTypeImage());
		solo.waitForText(v.getSoundTrack().getName(), 1, 10000, true);
		solo.clickOnText(solo.getString(R.string.sound_track_menu_entry_copy));
		solo.sleep(1000);
		int number_of_childs_new = ((RelativeLayout)getActivity().findViewById(R.id.sound_mixer_view)).getChildCount();
		int number_of_tracks_new = SoundMixer.getInstance().getNumberOfTracks();
		
		assertTrue("Child not deleted", number_of_childs_new == number_of_childs_old+1);
		assertTrue("Track not deleted", number_of_tracks_new == number_of_tracks_old+1);
	}
	
	public void testCopyAndDeleteTrack()
	{
		testCopyTrack();
		testDeleteTrack();
	}

	public void testAddMultipleTracksAndDelete()
	{
		helper.addTrack(SoundType.DRUMS);
		helper.addTrack(SoundType.PIANO);
		helper.addTrack(SoundType.MIC);
		
		testDeleteTrack();
	}
	
	private void testAddSpecificTrack(SoundType type)
	{
		int number_of_childs_old = ((RelativeLayout)getActivity().findViewById(R.id.sound_mixer_view)).getChildCount();
		int number_of_tracks_old = SoundMixer.getInstance().getNumberOfTracks();
		 
		helper.addTrack(type);
		 
		assertTrue("No " + type + " sound track added", number_of_tracks_old < SoundMixer.getInstance().getNumberOfTracks());
		assertTrue("No child layout added", number_of_childs_old < ((RelativeLayout)getActivity().findViewById(R.id.sound_mixer_view)).getChildCount());
	}
	

}
