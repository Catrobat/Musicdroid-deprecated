package at.tugraz.musicdroid.menutest;

import com.jayway.android.robotium.solo.Solo;

import at.tugraz.musicdroid.MainActivity;
import at.tugraz.musicdroid.R;
import at.tugraz.musicdroid.helper.Helper;
import at.tugraz.musicdroid.soundmixer.ObservableHorizontalScrollView;
import at.tugraz.musicdroid.soundmixer.SoundMixer;
import at.tugraz.musicdroid.soundmixer.timeline.Timeline;
import at.tugraz.musicdroid.soundtracks.SoundTrack;
import at.tugraz.musicdroid.soundtracks.SoundTrackView;
import at.tugraz.musicdroid.types.SoundType;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.View;
import android.webkit.WebView.FindListener;
import android.widget.RelativeLayout;

public class SoundMixerTest extends ActivityInstrumentationTestCase2<MainActivity> {
	protected Solo solo = null;
	protected SoundMixer mixer = null;
	protected Timeline timeline = null;
	protected UITestHelper helper;
	
	public SoundMixerTest() {
		super(MainActivity.class);
	}
	
	@Override
	protected void setUp() {
		 solo = new Solo(getInstrumentation(), getActivity());
		 mixer = SoundMixer.getInstance();
		 helper = new UITestHelper(solo, getActivity());
		 timeline = (Timeline) getActivity().findViewById(R.id.timeline);
	}
	
	public void testResetSoundMixerAtNewSongYes()
	{
		helper.addTrack(SoundType.PIANO);
		helper.addTrack(SoundType.DRUMS);
		helper.addTrack(SoundType.DRUMS);
		assertTrue(((RelativeLayout)getActivity().findViewById(R.id.sound_mixer_relative)).getChildCount() >= 3);
		
		solo.clickOnMenuItem(getActivity().getResources().getString(R.string.menu_new_song));
		
		solo.waitForText(getActivity().getResources().getString(R.string.dialog_warning_new_song), 1, 10000, true);
		
		solo.clickOnText(getActivity().getResources().getString(R.string.yes));
		solo.sleep(1000);
		
		assertTrue(((RelativeLayout)getActivity().findViewById(R.id.sound_mixer_relative)).getChildCount() <= 1);	
	}
	
	public void testSoundMixerUnchangedAtNewSongNo()
	{
		helper.addTrack(SoundType.PIANO);
		helper.addTrack(SoundType.DRUMS);
		helper.addTrack(SoundType.DRUMS);
		int oldCount = ((RelativeLayout)getActivity().findViewById(R.id.sound_mixer_relative)).getChildCount(); 
		assertTrue(oldCount >= 3);
		
		solo.clickOnMenuItem(getActivity().getResources().getString(R.string.menu_new_song));
		
		solo.waitForText(getActivity().getResources().getString(R.string.dialog_warning_new_song), 1, 10000, true);
		
		solo.clickOnText(getActivity().getResources().getString(R.string.no));
		solo.sleep(1000);
		
		assertTrue(((RelativeLayout)getActivity().findViewById(R.id.sound_mixer_relative)).getChildCount() == oldCount);	
	}
	
	public void testAddTrackAtClickingAddSoundButton()
	{
		testAddSpecificTrack(SoundType.DRUMS);
		testAddSpecificTrack(SoundType.PIANO);
		testAddSpecificTrack(SoundType.DRUMS);
	}
	
	public void testDeleteTrack()
	{
		helper.addTrack(SoundType.PIANO);
		int number_of_childs_old = ((RelativeLayout)getActivity().findViewById(R.id.sound_mixer_relative)).getChildCount();
		int number_of_tracks_old = SoundMixer.getInstance().getNumberOfTracks();
		
		SoundTrackView v = (SoundTrackView)((RelativeLayout)getActivity().findViewById(R.id.sound_mixer_relative)).getChildAt(1);
		
		solo.clickLongOnView(v.findViewById(R.id.img_sound_track_type));
		solo.waitForText(v.getSoundTrack().getName(), 1, 10000, true);
		solo.clickOnText(solo.getString(R.string.sound_track_menu_entry_delete));
		solo.sleep(1000);
		int number_of_childs_new = ((RelativeLayout)getActivity().findViewById(R.id.sound_mixer_relative)).getChildCount();
		int number_of_tracks_new = SoundMixer.getInstance().getNumberOfTracks();
		
		assertTrue("Child not deleted", number_of_childs_new == number_of_childs_old-1);
		assertTrue("Track not deleted", number_of_tracks_new == number_of_tracks_old-1);
	}
	
	public void testCopyTrack()
	{
		helper.addTrack(SoundType.PIANO);
		int number_of_childs_old = ((RelativeLayout)getActivity().findViewById(R.id.sound_mixer_relative)).getChildCount();
		int number_of_tracks_old = SoundMixer.getInstance().getNumberOfTracks();
		
		SoundTrackView v = (SoundTrackView)((RelativeLayout)getActivity().findViewById(R.id.sound_mixer_relative)).getChildAt(1);
		
		solo.clickLongOnView(v.findViewById(R.id.img_sound_track_type));
		solo.waitForText(v.getSoundTrack().getName(), 1, 10000, true);
		solo.clickOnText(solo.getString(R.string.sound_track_menu_entry_copy));
		solo.sleep(1000);
		int number_of_childs_new = ((RelativeLayout)getActivity().findViewById(R.id.sound_mixer_relative)).getChildCount();
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
		helper.addTrack(SoundType.DRUMS);
		
		testDeleteTrack();
	}
	
	
	
	private void testAddSpecificTrack(SoundType type)
	{
		int number_of_childs_old = ((RelativeLayout)getActivity().findViewById(R.id.sound_mixer_relative)).getChildCount();
		int number_of_tracks_old = SoundMixer.getInstance().getNumberOfTracks();
		 
		helper.addTrack(type);
		 
		assertTrue("No " + type + " sound track added", number_of_tracks_old < SoundMixer.getInstance().getNumberOfTracks());
		assertTrue("No child layout added", number_of_childs_old < ((RelativeLayout)getActivity().findViewById(R.id.sound_mixer_relative)).getChildCount());
	}
	
	
	//Timeline Tests
	
	public void testTrackLengthSettinsg()
	{
		int numTextViewsTopBegin = ((RelativeLayout)timeline.getChildAt(0)).getChildCount();
		int numViewsBottomBegin = ((RelativeLayout)timeline.getChildAt(1)).getChildCount();
		solo.clickOnView(getActivity().findViewById(R.id.btn_settings));
		solo.waitForText(getActivity().getString(R.string.soundmixer_context_title));
		solo.clickOnView(getActivity().findViewById(R.id.soundmixer_context_length));
		solo.sleep(1000);
		solo.drag(Helper.getInstance().getScreenWidth()/2-50, Helper.getInstance().getScreenHeight()/2, 
				  Helper.getInstance().getScreenWidth()/2-50, Helper.getInstance().getScreenHeight()/3, 1);
		solo.sleep(1000);
		solo.clickOnText(getActivity().getString(R.string.settings_button_apply));
		solo.sleep(1000);
		assertTrue("SoundMixer is not scrollable", helper.scrollToSide(timeline));
		
		int numTextViewsTopEnd = ((RelativeLayout)timeline.getChildAt(0)).getChildCount();
		int numViewsBottomEnd = ((RelativeLayout)timeline.getChildAt(1)).getChildCount();
		
		Log.i("Begin: " + numTextViewsTopBegin, "End: " + numTextViewsTopEnd);
		Log.i("Begin: " + numViewsBottomBegin, "End: " + numViewsBottomEnd);
		assertFalse(numTextViewsTopBegin == numTextViewsTopEnd);
		assertFalse(numViewsBottomBegin == numViewsBottomEnd);
		
		//add one View for each second but only one TextView each 5 seconds
		assertTrue(numViewsBottomBegin >= numTextViewsTopBegin*5); 
		assertTrue(numViewsBottomEnd >= numTextViewsTopEnd*5);
		
	}
	
	
	public void startEndMarkerTest()
	{
		int[] timelineLocation = {0,0};
		timeline.getLocationOnScreen(timelineLocation);
		
		int clickXPosition = timelineLocation[0]+200;
		helper.addTimelineMarker(clickXPosition, timelineLocation[1], R.string.timeline_menu_entry_start_point);
		View startMarker = timeline.findViewById(R.id.timeline_start_point);
		int margin = ((RelativeLayout.LayoutParams)startMarker.getLayoutParams()).leftMargin;
		int pixelPerSecond = SoundMixer.getInstance().getPixelPerSecond();
		
		Log.i("StartEndMarkerTest", "Margin: " + margin + " ClickX: " + clickXPosition + " PpS: " + pixelPerSecond);
		assertTrue(margin >= clickXPosition-pixelPerSecond*2 && margin <= clickXPosition+pixelPerSecond*2);
		
		//End Marker
		clickXPosition = timelineLocation[0] + 600;
		helper.addTimelineMarker(clickXPosition, timelineLocation[1], R.string.timeline_menu_entry_end_point);
		View endMarker = timeline.findViewById(R.id.timeline_end_point);
		int marginEnd = ((RelativeLayout.LayoutParams)endMarker.getLayoutParams()).leftMargin;
		
		Log.i("StartEndMarkerTest", "Margin: " + marginEnd + " ClickX: " + clickXPosition + " PpS: " + pixelPerSecond);
		assertTrue(marginEnd >= clickXPosition-pixelPerSecond && marginEnd <= clickXPosition+pixelPerSecond);
	}
	
	public void testStartMarkerBeforeEndMarker()
	{
		int[] timelineLocation = {0,0};
		timeline.getLocationOnScreen(timelineLocation);
		int clickXPosition = timelineLocation[0]+200;
		helper.addTimelineMarker(clickXPosition, timelineLocation[1], R.string.timeline_menu_entry_start_point);
		View startMarker = timeline.findViewById(R.id.timeline_start_point); 
		
		//place endMarker before startMarker - will fail
		clickXPosition = timelineLocation[0] + 100;
		helper.addTimelineMarker(clickXPosition, timelineLocation[1], R.string.timeline_menu_entry_end_point);
		
		View endMarker = timeline.findViewById(R.id.timeline_end_point);
		assertTrue(endMarker.getVisibility() == View.GONE);
		
		//place endMarker behind startMarker
		clickXPosition = timelineLocation[0] + 400;
		helper.addTimelineMarker(clickXPosition, timelineLocation[1], R.string.timeline_menu_entry_end_point);
		
		//place startMarker behind endMarker - will fail
		int oldMarginStartMarker = ((RelativeLayout.LayoutParams)startMarker.getLayoutParams()).leftMargin;
		clickXPosition = timelineLocation[0]+600;
		helper.addTimelineMarker(clickXPosition, timelineLocation[1], R.string.timeline_menu_entry_start_point);
		int newMarginStartMarker = ((RelativeLayout.LayoutParams)startMarker.getLayoutParams()).leftMargin;
		assertTrue(oldMarginStartMarker == newMarginStartMarker);
		
	}
	
	public void testMoveMarker()
	{
		int[] timelineLocation = {0,0};
		timeline.getLocationOnScreen(timelineLocation);
		int clickXPosition = timelineLocation[0]+200;
		helper.addTimelineMarker(clickXPosition, timelineLocation[1], R.string.timeline_menu_entry_start_point);
		View startMarker = timeline.findViewById(R.id.timeline_start_point);
		
		
		clickXPosition = timelineLocation[0] + 400;
		helper.addTimelineMarker(clickXPosition, timelineLocation[1], R.string.timeline_menu_entry_end_point);
		View endMarker = timeline.findViewById(R.id.timeline_end_point);
		
		int[] startMarkerLocation = {0,0};
		startMarker.getLocationOnScreen(startMarkerLocation);
		
		int startMargin = ((RelativeLayout.LayoutParams)startMarker.getLayoutParams()).leftMargin;
		int endMargin = ((RelativeLayout.LayoutParams)endMarker.getLayoutParams()).leftMargin;
		
		int drag = endMargin - startMargin;
		
		solo.drag(startMarkerLocation[0], startMarkerLocation[0]+ drag + 10, 
		          startMarkerLocation[1], startMarkerLocation[1], 20);
		
		solo.sleep(1000);
		
		assertTrue(((RelativeLayout.LayoutParams)startMarker.getLayoutParams()).leftMargin <
					((RelativeLayout.LayoutParams)endMarker.getLayoutParams()).leftMargin);
	}

	
	
	

}



