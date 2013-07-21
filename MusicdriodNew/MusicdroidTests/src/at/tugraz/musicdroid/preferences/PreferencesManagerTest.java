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
package at.tugraz.musicdroid.preferences;

import android.test.AndroidTestCase;
import android.util.Log;

public class PreferencesManagerTest extends AndroidTestCase {
	public static final int SOUNDTRACK_DEFAULT_LENGTH_DEFAULT_VALUE = 45;
	public static final String SOUNDTRACK_DEFAULT_LENGTH_KEY = "soundtrack_length_default";
	public static final int SOUNDTRACK_LENGTH_DEFAULT_VALUE = 45;
	public static final String SOUNDTRACK_LENGTH_KEY = "soundtrack_length";
	public static final int METRONOM_VISUALIZATION_DEFAULT_VALUE = 0;
	public static final String METRONOM_VISUALIZATION_KEY = "metronom_visualization";
	public static final int METRONOM_BPM_DEFAULT_VALUE = 72;
	public static final String METRONOM_BPM_KEY = "metronom_bpm";
	public static final int PLAY_PLAYBACK_DEFAULT_VALUE = 1;
	public static final String PLAY_PLAYBACK_KEY = "play_playback";	
	public static final int ADD_AT_CURRENT_POSITION_DEFAULT_VALUE = 0;
	public static final String ADD_AT_CURRENT_POSITION_KEY = "add_at_current_position";
	
	
	public void testDefaultValues()
	{
		PreferenceManager pm = PreferenceManager.getInstance();
		assertTrue(pm.getPreference(PreferenceManager.SOUNDTRACK_DEFAULT_LENGTH_KEY) == 
				              PreferenceManager.SOUNDTRACK_DEFAULT_LENGTH_DEFAULT_VALUE);
		assertTrue(pm.getPreference(PreferenceManager.SOUNDTRACK_LENGTH_KEY) == 
	              PreferenceManager.SOUNDTRACK_LENGTH_DEFAULT_VALUE);
		assertTrue(pm.getPreference(PreferenceManager.METRONOM_VISUALIZATION_KEY) == 
	              PreferenceManager.METRONOM_VISUALIZATION_DEFAULT_VALUE);
		assertTrue(pm.getPreference(PreferenceManager.METRONOM_BPM_KEY) == 
	              PreferenceManager.METRONOM_BPM_DEFAULT_VALUE);
		assertTrue(pm.getPreference(PreferenceManager.PLAY_PLAYBACK_KEY) == 
	              PreferenceManager.PLAY_PLAYBACK_DEFAULT_VALUE);		
		assertTrue(pm.getPreference(PreferenceManager.ADD_AT_CURRENT_POSITION_KEY) == 
	              PreferenceManager.ADD_AT_CURRENT_POSITION_DEFAULT_VALUE);
	}
	
	public void testSetValues()
	{
		PreferenceManager pm = PreferenceManager.getInstance();
		int value = 125;
		for(int i = 0; i < 10; i++)
		{
		  value = value+i;
		  pm.setPreference(PreferenceManager.SOUNDTRACK_DEFAULT_LENGTH_KEY, value);
		  assertTrue(pm.getPreference(PreferenceManager.SOUNDTRACK_DEFAULT_LENGTH_KEY) == value);
		}
	}
}
