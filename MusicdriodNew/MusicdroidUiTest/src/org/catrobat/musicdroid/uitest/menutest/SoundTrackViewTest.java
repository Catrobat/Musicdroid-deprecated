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
import android.view.View;
import android.widget.RelativeLayout;

import com.jayway.android.robotium.solo.Solo;

import org.catrobat.musicdroid.MainActivity;
import org.catrobat.musicdroid.R;
import org.catrobat.musicdroid.soundmixer.SoundMixer;
import org.catrobat.musicdroid.soundtracks.SoundTrackView;
import org.catrobat.musicdroid.types.SoundType;
import org.catrobat.musicdroid.uitest.utils.UiTestHelper;

public class SoundTrackViewTest extends ActivityInstrumentationTestCase2<MainActivity> {
	protected Solo solo = null;
	protected SoundMixer mixer = null;

	public SoundTrackViewTest() {
		super(MainActivity.class);
	}

	@Override
	protected void setUp() {
		solo = new Solo(getInstrumentation(), getActivity());
		mixer = SoundMixer.getInstance();
	}

	@Override
	protected void tearDown() {
		solo.finishOpenedActivities();
	}

	public void testSoundTrackViewInactive() {
		UiTestHelper.addTrack(solo, SoundType.DRUMS);
		UiTestHelper.addTrack(solo, SoundType.DRUMS);
		assertTrue(((RelativeLayout) getActivity().findViewById(R.id.sound_mixer_relative)).getChildCount() >= 2);

		SoundTrackView v = (SoundTrackView) ((RelativeLayout) getActivity().findViewById(R.id.sound_mixer_relative))
				.getChildAt(1);
		SoundTrackView check = (SoundTrackView) ((RelativeLayout) getActivity().findViewById(R.id.sound_mixer_relative))
				.getChildAt(2);
		assertTrue(check.findViewById(R.id.play_button).isEnabled());
		assertTrue(check.findViewById(R.id.lock_button).isEnabled());

		solo.clickLongOnView(v.findViewById(R.id.img_sound_track_type));
		solo.waitForText(v.getSoundTrack().getName(), 1, 10000, true);

		assertFalse(check.findViewById(R.id.play_button).isEnabled());
		assertFalse(check.findViewById(R.id.lock_button).isEnabled());

		solo.clickOnText(solo.getString(R.string.sound_track_menu_entry_copy));
		solo.sleep(1000);

		assertTrue(check.findViewById(R.id.play_button).isEnabled());
		assertTrue(check.findViewById(R.id.lock_button).isEnabled());
	}

	//will fail
	//	public void testDragOnlyInsideScreen()
	//	{
	//		int[] location = new int[2];
	//		int[] new_location = new int[2];
	//		ui_helper.addTrack(SoundType.DRUMS);
	//		SoundTrackView v = (SoundTrackView)((RelativeLayout)getActivity().findViewById(R.id.sound_mixer_relative)).getChildAt(1);
	//		
	//		v.getLocationOnScreen(location);
	//		int start_x = location[0];
	//		int start_y = location[1];
	//		solo.sleep(100);
	//		solo.clickOnView(v.findViewById(R.id.lock_button));
	//		solo.sleep(100);
	//		
	//		solo.drag(start_x+v.getWidth()/2, -300, start_y, start_y, 1);
	//		solo.sleep(100);
	//		
	//		v.getLocationOnScreen(new_location);
	//		assertTrue(new_location[0] >= 0);
	//		
	//		solo.drag(start_x+v.getWidth()/2, helper.getScreenWidth()*2, start_y, start_y, 1);
	//		new_location = new int[2];
	//		v.getLocationOnScreen(new_location);
	//		
	//		assertTrue(new_location[0] <= helper.getScreenWidth());
	//		
	//	}

	public void testLock() {
		int[] location = new int[2];
		int[] newLocation = new int[2];

		UiTestHelper.addTrack(solo, SoundType.DRUMS);

		SoundTrackView v = (SoundTrackView) ((RelativeLayout) getActivity().findViewById(R.id.sound_mixer_relative))
				.getChildAt(1);

		v.getLocationOnScreen(location);
		int startX = location[0];
		int startY = location[1];

		//Drag, assert position remains unchanged because Sound Track is locked
		solo.drag(startX + v.getWidth() / 2, 300, startY, startY, 1);

		v.getLocationOnScreen(newLocation);
		assertTrue(startX == newLocation[0]);
		assertTrue(startY == newLocation[1]);

		//Unlock Sound Track
		solo.sleep(100);
		solo.clickOnView(v.findViewById(R.id.lock_button));
		solo.sleep(100);

		//Drag again, view should move  
		newLocation = new int[2];
		solo.drag(startX + v.getWidth() / 2, 300, startY, startY, 1);
		solo.sleep(100);
		v.getLocationOnScreen(newLocation);
		int newX = newLocation[0];
		assertTrue(startX != newX);

		//Unlock again
		solo.sleep(100);
		solo.clickOnView(v.findViewById(R.id.lock_button));
		solo.sleep(100);

		//Drag again, view should not move  
		newLocation = new int[2];
		solo.drag(newX + v.getWidth() / 4, -300, startY, startY, 1);
		solo.sleep(100);
		v.getLocationOnScreen(newLocation);
		assertTrue(newX == newLocation[0]);
	}

	public void testCollapseAndExpand() {
		//ui_helper.addTrack(SoundType.DRUMS);
		UiTestHelper.createMicTrack(solo, 5);

		SoundTrackView v = (SoundTrackView) ((RelativeLayout) getActivity().findViewById(R.id.sound_mixer_relative))
				.getChildAt(1);
		assertTrue(v.getLayoutParams().width < SoundTrackView.MINIMAL_WIDTH);

		assertTrue(isCollapsed(v));

		solo.clickOnView(v.findViewById(R.id.expand_button));
		solo.sleep(1000);

		assertTrue(isExpanded(v));

		solo.clickOnView(v.findViewById(R.id.expand_button));
		solo.sleep(1000);

		assertTrue(isCollapsed(v));
	}

	private boolean isCollapsed(SoundTrackView view) {
		return (view.findViewById(R.id.sound_track_text).getVisibility() == View.GONE
				&& view.findViewById(R.id.horizontal_seperator).getVisibility() == View.GONE
				&& view.findViewById(R.id.play_button).getVisibility() == View.GONE
				&& view.findViewById(R.id.lock_button).getVisibility() == View.GONE
				&& view.findViewById(R.id.volume_button).getVisibility() == View.GONE && view.findViewById(
				R.id.expand_button).getVisibility() == View.VISIBLE);
	}

	private boolean isExpanded(SoundTrackView view) {
		return (view.findViewById(R.id.sound_track_text).getVisibility() == View.VISIBLE
				&& view.findViewById(R.id.horizontal_seperator).getVisibility() == View.VISIBLE
				&& view.findViewById(R.id.play_button).getVisibility() == View.VISIBLE
				&& view.findViewById(R.id.lock_button).getVisibility() == View.VISIBLE && view.findViewById(
				R.id.volume_button).getVisibility() == View.VISIBLE);
	}
}
