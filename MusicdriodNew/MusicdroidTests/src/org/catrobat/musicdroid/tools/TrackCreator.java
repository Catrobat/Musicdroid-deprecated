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
package org.catrobat.musicdroid.tools;

import java.io.File;

import org.catrobat.musicdroid.R;
import org.catrobat.musicdroid.recorder.AudioHandler;
import org.catrobat.musicdroid.types.SoundType;

import android.app.Activity;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jayway.android.robotium.solo.Solo;

/**
 * @author matthias
 *
 */
public class TrackCreator {
	
	public static void createMicTrack(Solo solo, int durationSeconds)
	{
		solo.clickOnView(solo.getCurrentActivity().findViewById(R.id.btn_add));
		solo.waitForText(solo.getString(R.string.dialog_add_sound_title), 1, 10000, true);
		solo.sleep(100);
		solo.clickOnText(solo.getString(SoundType.MIC.getNameResource()));
		solo.sleep(2000); 
 	    
		ImageButton recordButton = (ImageButton) solo.getCurrentActivity().findViewById(R.id.microphone_record_button);
		solo.clickOnView(recordButton);
		solo.sleep(1000);
		
		if(solo.searchText(solo.getCurrentActivity().getString(R.string.dialog_continue)))
		{
    	  solo.clickOnButton(solo.getCurrentActivity().getString(R.string.dialog_continue));
		}
		
		solo.sleep(durationSeconds*1000);
		
		solo.clickOnView(recordButton);
		solo.sleep(1000);
		solo.clickOnText(solo.getCurrentActivity().getString(R.string.recorder_add_to_sound_mixer_text));
		solo.sleep(500);
	}
}
