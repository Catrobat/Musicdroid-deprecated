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
package org.catrobat.musicdroid.uitest.menutest;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.RelativeLayout;

import com.jayway.android.robotium.solo.Solo;

import org.catrobat.musicdroid.MainActivity;
import org.catrobat.musicdroid.R;
import org.catrobat.musicdroid.soundmixer.SoundMixer;
import org.catrobat.musicdroid.soundmixer.timeline.Timeline;
import org.catrobat.musicdroid.soundtracks.SoundTrackView;
import org.catrobat.musicdroid.types.SoundType;
import org.catrobat.musicdroid.uitest.utils.UiTestHelper;

public class SoundMixerTest extends ActivityInstrumentationTestCase2<MainActivity> {
	protected Solo solo = null;
	protected SoundMixer mixer = null;
	protected Timeline timeline = null;

	public SoundMixerTest() {
		super(MainActivity.class);
	}

	@Override
	protected void setUp() {
		solo = new Solo(getInstrumentation(), getActivity());
		mixer = SoundMixer.getInstance();
		timeline = (Timeline) getActivity().findViewById(R.id.timeline);
	}

	@Override
	public void tearDown() throws Exception {
		solo.finishOpenedActivities();

	}

	public void testResetSoundMixerAtNewSongYes() {
		UiTestHelper.addTrack(solo, SoundType.DRUMS);
		UiTestHelper.addTrack(solo, SoundType.DRUMS);
		assertTrue(((RelativeLayout) getActivity().findViewById(R.id.sound_mixer_relative)).getChildCount() >= 3);

		solo.clickOnMenuItem(getActivity().getResources().getString(R.string.menu_new_song));

		solo.waitForText(getActivity().getResources().getString(R.string.dialog_warning_new_song), 1, 10000, true);

		solo.clickOnText(getActivity().getResources().getString(R.string.yes));
		solo.sleep(1000);

		assertTrue(((RelativeLayout) getActivity().findViewById(R.id.sound_mixer_relative)).getChildCount() <= 1);
	}

	public void testSoundMixerUnchangedAtNewSongNo() {
		UiTestHelper.addTrack(solo, SoundType.DRUMS);
		UiTestHelper.addTrack(solo, SoundType.DRUMS);
		int oldCount = ((RelativeLayout) getActivity().findViewById(R.id.sound_mixer_relative)).getChildCount();
		assertTrue(oldCount >= 3);

		solo.clickOnMenuItem(getActivity().getResources().getString(R.string.menu_new_song));

		solo.waitForText(getActivity().getResources().getString(R.string.dialog_warning_new_song), 1, 10000, true);

		solo.clickOnText(getActivity().getResources().getString(R.string.no));
		solo.sleep(1000);

		assertTrue(((RelativeLayout) getActivity().findViewById(R.id.sound_mixer_relative)).getChildCount() == oldCount);
	}

	public void testAddTrackAtClickingAddSoundButton() {
		testAddSpecificTrack(SoundType.DRUMS);
		testAddSpecificTrack(SoundType.DRUMS);
	}

	public void testDeleteTrack() {
		UiTestHelper.addTrack(solo, SoundType.DRUMS);
		int numberOfChildrenOld = ((RelativeLayout) getActivity().findViewById(R.id.sound_mixer_relative))
				.getChildCount();
		int numberOfTracksOld = SoundMixer.getInstance().getNumberOfTracks();

		SoundTrackView v = (SoundTrackView) ((RelativeLayout) getActivity().findViewById(R.id.sound_mixer_relative))
				.getChildAt(1);

		solo.clickLongOnView(v.findViewById(R.id.img_sound_track_type));
		solo.waitForText(v.getSoundTrack().getName(), 1, 10000, true);
		solo.clickOnText(solo.getString(R.string.sound_track_menu_entry_delete));
		solo.sleep(1000);
		int numberOfChildrenNew = ((RelativeLayout) getActivity().findViewById(R.id.sound_mixer_relative))
				.getChildCount();
		int numberOfTracksNew = SoundMixer.getInstance().getNumberOfTracks();

		assertTrue("Child not deleted", numberOfChildrenNew == numberOfChildrenOld - 1);
		assertTrue("Track not deleted", numberOfTracksNew == numberOfTracksOld - 1);
	}

	public void testCopyTrack() {
		UiTestHelper.addTrack(solo, SoundType.DRUMS);
		int numberOfChildrenOld = ((RelativeLayout) getActivity().findViewById(R.id.sound_mixer_relative))
				.getChildCount();
		int numberOfTracksOld = SoundMixer.getInstance().getNumberOfTracks();

		SoundTrackView v = (SoundTrackView) ((RelativeLayout) getActivity().findViewById(R.id.sound_mixer_relative))
				.getChildAt(1);

		solo.clickLongOnView(v.findViewById(R.id.img_sound_track_type));
		solo.waitForText(v.getSoundTrack().getName(), 1, 10000, true);
		solo.clickOnText(solo.getString(R.string.sound_track_menu_entry_copy));
		solo.sleep(1000);
		int numberOfChildrenNew = ((RelativeLayout) getActivity().findViewById(R.id.sound_mixer_relative))
				.getChildCount();
		int numberOfTracksNew = SoundMixer.getInstance().getNumberOfTracks();

		assertTrue("Child not deleted", numberOfChildrenNew == numberOfChildrenOld + 1);
		assertTrue("Track not deleted", numberOfTracksNew == numberOfTracksOld + 1);
	}

	public void testAddMultipleTracksAndDelete() {
		UiTestHelper.addTrack(solo, SoundType.DRUMS);
		UiTestHelper.addTrack(solo, SoundType.DRUMS);

		testDeleteTrack();
	}

	private void testAddSpecificTrack(SoundType type) {
		int numberOfChildrenOld = ((RelativeLayout) getActivity().findViewById(R.id.sound_mixer_relative))
				.getChildCount();
		int numberOfTracksOld = SoundMixer.getInstance().getNumberOfTracks();

		UiTestHelper.addTrack(solo, type);

		assertTrue("No " + type + " sound track added", numberOfTracksOld < SoundMixer.getInstance()
				.getNumberOfTracks());
		assertTrue("No child layout added",
				numberOfChildrenOld < ((RelativeLayout) getActivity().findViewById(R.id.sound_mixer_relative))
						.getChildCount());
	}
}
