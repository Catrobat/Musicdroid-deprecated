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

import com.jayway.android.robotium.solo.Solo;

import org.catrobat.musicdroid.MainActivity;
import org.catrobat.musicdroid.R;
import org.catrobat.musicdroid.soundmixer.SoundMixer;
import org.catrobat.musicdroid.soundmixer.SoundMixerEventHandler;
import org.catrobat.musicdroid.soundmixer.timeline.Timeline;
import org.catrobat.musicdroid.types.SoundType;
import org.catrobat.musicdroid.uitest.utils.UiTestHelper;

import java.util.Observable;
import java.util.Observer;

public class SoundMixerEventHandlerTest extends ActivityInstrumentationTestCase2<MainActivity> implements Observer {
	protected SoundMixerEventHandler eventHandler = null;
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
		eventHandler = SoundMixer.getInstance().getEventHandler();
		eventHandler.addObserver(this);
	}

	@Override
	protected void tearDown() {
		solo.finishOpenedActivities();
	}

	public void testObserver() {
		UiTestHelper.addTrack(solo, SoundType.DRUMS);
		solo.clickOnView(getActivity().findViewById(R.id.btn_play));
		solo.sleep(25000);

		solo.clickOnView(getActivity().findViewById(R.id.btn_rewind));

		int[] timelineLocation = { 0, 0 };
		Timeline timeline = (Timeline) getActivity().findViewById(R.id.timeline);
		timeline.getLocationOnScreen(timelineLocation);

		int clickXPosition = timelineLocation[0] + 200;
		UiTestHelper.addTimelineMarker(solo, clickXPosition, timelineLocation[1],
				getActivity().getString(R.string.timeline_menu_entry_start_point));

		solo.clickOnView(getActivity().findViewById(R.id.btn_play));
		solo.sleep(10000);

	}

	@Override
	public void update(Observable observable, Object data) {
		int i = (Integer) data;
		assertTrue(i >= eventHandler.getStartPoint() && i <= eventHandler.getEndPoint());
	}
}
