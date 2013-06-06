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
