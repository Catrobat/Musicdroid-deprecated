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
package at.tugraz.musicdroid.menutest;

import com.jayway.android.robotium.solo.Solo;

import at.tugraz.musicdroid.MainActivity;
import at.tugraz.musicdroid.R;
import at.tugraz.musicdroid.helper.Helper;
import at.tugraz.musicdroid.recorder.RecorderUITest;
import at.tugraz.musicdroid.soundmixer.SoundMixer;
import at.tugraz.musicdroid.soundmixer.timeline.Timeline;
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
		assertTrue(((RelativeLayout)getActivity().findViewById(R.id.sound_mixer_relative)).getChildCount() >= 2);
		
		SoundTrackView v = (SoundTrackView)((RelativeLayout)getActivity().findViewById(R.id.sound_mixer_relative)).getChildAt(1);
		SoundTrackView check = (SoundTrackView)((RelativeLayout)getActivity().findViewById(R.id.sound_mixer_relative)).getChildAt(2);
		assertTrue(check.findViewById(R.id.play_button).isEnabled());
		assertTrue(check.findViewById(R.id.lock_button).isEnabled());
		
		//Open Context Menu of first view
		solo.clickLongOnView(v.findViewById(R.id.img_sound_track_type));
		solo.waitForText(v.getSoundTrack().getName(), 1, 10000, true);
		
		//Buttons of second view should be disabled
		assertFalse(check.findViewById(R.id.play_button).isEnabled());
		assertFalse(check.findViewById(R.id.lock_button).isEnabled());
		
		//Close context menu by clicking on an entry
		solo.clickOnText(solo.getString(R.string.sound_track_menu_entry_copy));
		solo.sleep(1000);
		
		//Buttons of second view should be enabled again
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
	
	public void testLock()
	{
		int[] location = new int[2];
		int[] new_location = new int[2];

		ui_helper.addTrack(SoundType.PIANO);
		
		SoundTrackView v = (SoundTrackView)((RelativeLayout)getActivity().findViewById(R.id.sound_mixer_relative)).getChildAt(1);
		
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
		solo.clickOnView(v.findViewById(R.id.lock_button));
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
		solo.clickOnView(v.findViewById(R.id.lock_button));
		solo.sleep(100);
		
		//Drag again, view should not move  
		new_location = new int[2];
		solo.drag(new_x+v.getWidth()/4, -300, start_y, start_y, 1);
		solo.sleep(100);
		v.getLocationOnScreen(new_location);
		assertTrue(new_x == new_location[0]);
	}
	
	
	public void testCollapseAndExpand()
	{
		ui_helper.addTrack(SoundType.DRUMS);
				
		SoundTrackView v = (SoundTrackView)((RelativeLayout)getActivity().findViewById(R.id.sound_mixer_relative)).getChildAt(1);
		assertTrue(v.getLayoutParams().width < SoundTrackView.MINIMAL_WIDTH);
		
		assertTrue(isCollapsed(v));
		
		solo.clickOnView(v.findViewById(R.id.expand_button));
		solo.sleep(1000);
		
		assertTrue(isExpanded(v));
		
		solo.clickOnView(v.findViewById(R.id.expand_button));
		solo.sleep(1000);
		
		assertTrue(isCollapsed(v));
	}
	
	private boolean isCollapsed(SoundTrackView view)
	{
		return (
			view.findViewById(R.id.sound_track_text).getVisibility() == View.GONE &&
			view.findViewById(R.id.horizontal_seperator).getVisibility() == View.GONE &&
			view.findViewById(R.id.play_button).getVisibility() == View.GONE &&
			view.findViewById(R.id.lock_button).getVisibility() == View.GONE &&
			view.findViewById(R.id.volume_button).getVisibility() == View.GONE &&
			view.findViewById(R.id.expand_button).getVisibility() == View.VISIBLE);
	}
	
	private boolean isExpanded(SoundTrackView view)
	{
		return (
			view.findViewById(R.id.sound_track_text).getVisibility() == View.VISIBLE &&
			view.findViewById(R.id.horizontal_seperator).getVisibility() == View.VISIBLE &&
			view.findViewById(R.id.play_button).getVisibility() == View.VISIBLE &&
			view.findViewById(R.id.lock_button).getVisibility() == View.VISIBLE &&
			view.findViewById(R.id.volume_button).getVisibility() == View.VISIBLE);
	}
}
