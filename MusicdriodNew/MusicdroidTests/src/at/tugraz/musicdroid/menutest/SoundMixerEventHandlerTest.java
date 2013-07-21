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

import java.util.Observable;
import java.util.Observer;

import com.jayway.android.robotium.solo.Solo;

import android.test.ActivityInstrumentationTestCase2;
import at.tugraz.musicdroid.MainActivity;
import at.tugraz.musicdroid.R;
import at.tugraz.musicdroid.soundmixer.SoundMixer;
import at.tugraz.musicdroid.soundmixer.SoundMixerEventHandler;
import at.tugraz.musicdroid.soundmixer.timeline.Timeline;
import at.tugraz.musicdroid.types.SoundType;

public class SoundMixerEventHandlerTest extends ActivityInstrumentationTestCase2<MainActivity> implements Observer {
	protected SoundMixerEventHandler eventHandler = null;
	protected UITestHelper uiHelper = null;
	protected Solo solo = null;
	
	public SoundMixerEventHandlerTest(Class<MainActivity> activityClass) {
		super(activityClass);
	}

	public SoundMixerEventHandlerTest() {
		super(MainActivity.class);
	}
	
	@Override
	protected void setUp() {
		solo = new Solo(getInstrumentation(), getActivity());
		uiHelper = new UITestHelper(solo, getActivity());
		eventHandler = SoundMixer.getInstance().getEventHandler();
		eventHandler.addObserver(this);
	}

	public void testObserver()
	{
		uiHelper.addTrack(SoundType.DRUMS);
		solo.clickOnView(getActivity().findViewById(R.id.btn_play));
		solo.sleep(25000);
		
		solo.clickOnView(getActivity().findViewById(R.id.btn_rewind));
		
		int[] timelineLocation = {0,0};
		Timeline timeline = (Timeline) getActivity().findViewById(R.id.timeline);
		timeline.getLocationOnScreen(timelineLocation);
		
		int clickXPosition = timelineLocation[0]+200;
		uiHelper.addTimelineMarker(clickXPosition, timelineLocation[1], R.string.timeline_menu_entry_start_point);
		
		solo.clickOnView(getActivity().findViewById(R.id.btn_play));
		solo.sleep(10000);
		
	}
	
	@Override
	public void update(Observable observable, Object data) {
		int i = (Integer) data;
		assertTrue(i >= eventHandler.getStartPoint() && i <= eventHandler.getEndPoint());
	}
}
