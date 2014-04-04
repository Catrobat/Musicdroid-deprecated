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
package org.catrobat.musicdroid.uitest.utils;

import android.view.View;
import android.widget.ImageButton;

import com.jayway.android.robotium.solo.Solo;

import org.catrobat.musicdroid.R;
import org.catrobat.musicdroid.tools.DeviceInfo;
import org.catrobat.musicdroid.types.SoundType;

public final class UiTestHelper {
	private UiTestHelper() {
	}

	public static Boolean clickAddSoundButton(Solo solo) {
		solo.clickOnView(solo.getView(R.id.btn_add));
		return solo.waitForText(solo.getString(R.string.dialog_add_sound_title), 1, 10000, true);
	}

	public static void addTrack(Solo solo, SoundType type) {
		if (clickAddSoundButton(solo)) {
			solo.sleep(100);
			solo.clickOnText(solo.getString(type.getNameResource()));
			solo.sleep(1000);
			return;
		}
	}

	public static boolean scrollToSide(Solo solo, View v) {
		int[] location = { 0, 0 };
		v.getLocationOnScreen(location);

		int startX = location[0];
		int startY = location[1];
		solo.sleep(100);
		int width = DeviceInfo.getScreenWidth(solo.getCurrentActivity());
		solo.drag(startX + width / 2, 0, startY, startY, 1);

		int[] newLocation = { 0, 0 };
		v.getLocationOnScreen(newLocation);
		return (location[0] != newLocation[0]);
	}

	public static void addTimelineMarker(Solo solo, int positionX, int positionY, String markerString) {
		solo.clickLongOnScreen(positionX, positionY);
		solo.waitForText(markerString);
		solo.clickOnText(markerString);
		solo.sleep(1000);
	}

	public static void createMicTrack(Solo solo, int durationSeconds) {
		solo.clickOnView(solo.getCurrentActivity().findViewById(R.id.btn_add));
		solo.waitForText(solo.getString(R.string.dialog_add_sound_title), 1, 10000, true);
		solo.sleep(100);
		solo.clickOnText(solo.getString(SoundType.MIC.getNameResource()));
		solo.sleep(2000);

		ImageButton recordButton = (ImageButton) solo.getCurrentActivity().findViewById(R.id.microphone_record_button);
		solo.clickOnView(recordButton);
		solo.sleep(1000);

		if (solo.searchText(solo.getCurrentActivity().getString(R.string.dialog_continue))) {
			solo.clickOnButton(solo.getCurrentActivity().getString(R.string.dialog_continue));
		}

		solo.sleep(durationSeconds * 1000);

		solo.clickOnView(recordButton);
		solo.sleep(1000);
		solo.clickOnText(solo.getCurrentActivity().getString(R.string.recorder_add_to_sound_mixer_text));
		solo.sleep(500);
	}
}
