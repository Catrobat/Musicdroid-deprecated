/*******************************************************************************
 * Catroid: An on-device visual programming system for Android devices
 *  Copyright (C) 2010-2013 The Catrobat Team
 *  (<http://developer.catrobat.org/credits>)
 *  
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 * 
 *  An additional term exception under section 7 of the GNU Affero
 *  General Public License, version 3, is available at
 *  http://www.catroid.org/catroid/licenseadditionalterm
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU Affero General Public License for more details.
 * 
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package org.catrobat.musicdroid.menutest;

import org.catrobat.musicdroid.MainActivity;
import org.catrobat.musicdroid.R;
import org.catrobat.musicdroid.soundmixer.SoundMixer;
import org.catrobat.musicdroid.soundmixer.timeline.Timeline;
import org.catrobat.musicdroid.soundtracks.SoundTrackView;
import org.catrobat.musicdroid.types.SoundType;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.RelativeLayout;

import com.jayway.android.robotium.solo.Solo;

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
	
	@Override
	   public void tearDown() throws Exception {
	        solo.finishOpenedActivities();
	        
	  }
	
	public void testResetSoundMixerAtNewSongYes()
	{
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
		testAddSpecificTrack(SoundType.DRUMS);
	}
	
	public void testDeleteTrack()
	{
		helper.addTrack(SoundType.DRUMS);
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
		helper.addTrack(SoundType.DRUMS);
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

	public void testAddMultipleTracksAndDelete()
	{
		helper.addTrack(SoundType.DRUMS);
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
}



