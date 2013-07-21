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

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.jayway.android.robotium.solo.Solo;
import at.tugraz.musicdroid.R;
import at.tugraz.musicdroid.helper.Helper;
import at.tugraz.musicdroid.types.SoundType;

public class UITestHelper {
	private Solo solo;
	private Activity activity;
	
	public UITestHelper(Solo s, Activity a) {
		solo = s;
		activity = a;
	}

	public Boolean clickAddSoundButton()
	{
	   solo.clickOnView(activity.findViewById(R.id.btn_add));
	   return solo.waitForText(solo.getString(R.string.dialog_add_sound_title), 1, 10000, true);
	}
	
	public void addTrack(SoundType type)
	{
		if(clickAddSoundButton())
		{
		   solo.sleep(100);
		   solo.clickOnText(solo.getString(type.getNameResource()));
		   solo.sleep(1000);
		   return;
		}
		 
	}
	
	public boolean scrollToSide(View v)
	{
		int[] location = {0,0};
		v.getLocationOnScreen(location);
		
		int start_x = location[0];
		int start_y = location[1];
		solo.sleep(100);
		int width = Helper.getInstance().getScreenWidth();
		solo.drag(start_x+width/2, 0, start_y, start_y, 1);
		
		int[] newLocation = {0,0};
		v.getLocationOnScreen(newLocation);
		return(location[0] != newLocation[0]);
	}
	
	public void addTimelineMarker(int positionX, int positionY, int markerId)
	{
		solo.clickLongOnScreen(positionX, positionY);
		solo.waitForText(activity.getString(markerId));
		solo.clickOnText(activity.getString(markerId));
		solo.sleep(1000);	
	}
}
