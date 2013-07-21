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

import java.util.ArrayList;

import com.jayway.android.robotium.solo.Solo;

import at.tugraz.musicdroid.MainActivity;
import at.tugraz.musicdroid.R;
import at.tugraz.musicdroid.types.SoundType;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.GridView;

public class AddSoundDialogTest extends ActivityInstrumentationTestCase2<MainActivity> {
	protected Solo solo = null;
	protected int screenWidth;
	protected int screenHeight;
	protected UITestHelper helper;
	
	public AddSoundDialogTest() {
		super(MainActivity.class);
	}
	
	@Override
	protected void setUp() {
		 solo = new Solo(getInstrumentation(), getActivity());			
		 screenWidth = solo.getCurrentActivity().getWindowManager().getDefaultDisplay().getWidth();
		 screenHeight = solo.getCurrentActivity().getWindowManager().getDefaultDisplay().getHeight();
		 helper = new UITestHelper(solo, getActivity());
	}
	
	public void testAddSoundButton()
	{
		 /*Test if dialog disappears when clicking on screen*/
		 solo.clickOnView(getActivity().findViewById(R.id.btn_add));
		 assertTrue("Add Sounds dialog not visible",
					solo.waitForText(solo.getString(R.string.dialog_add_sound_title), 1, 10000, true));
		 solo.sleep(1000);

		 solo.clickOnScreen(20, screenHeight/2);
		 solo.sleep(1000);
		 assertFalse("Add Sounds dialog not visible",
		 			solo.waitForText(solo.getString(R.string.dialog_add_sound_title), 1, 1000, true));
		 solo.sleep(1000);

		 
		 /*Test if dialog disappears when clicking back-button */
		 solo.clickOnView(getActivity().findViewById(R.id.btn_add));
		 assertTrue("Add Sounds dialog not visible",
					solo.waitForText(solo.getString(R.string.dialog_add_sound_title), 1, 10000, true));
		 solo.sleep(1000);
		 
		 solo.goBack();
		 assertFalse("Add Sounds dialog not visible",
		 			solo.waitForText(solo.getString(R.string.dialog_add_sound_title), 1, 1000, true));
		 solo.sleep(1000);
	}
	
	public void testDialogMenuEntries()
	{
		/*Test if all menu entries are there*/
		solo.clickOnView(getActivity().findViewById(R.id.btn_add));
		assertTrue("Add Sounds dialog not visible",
					solo.waitForText(solo.getString(R.string.dialog_add_sound_title), 1, 10000, true));
		solo.sleep(1000);
		SoundType[] possibleValues = SoundType.class.getEnumConstants();
		for(int i = 0; i < possibleValues.length; i++)
		{
		  assertTrue("Menu Entry " + possibleValues[i].name() + " not visibile",
  		     	      solo.waitForText(solo.getString(possibleValues[i].getNameResource()), 1, 1000, true));
		}
	}
	
	

}
