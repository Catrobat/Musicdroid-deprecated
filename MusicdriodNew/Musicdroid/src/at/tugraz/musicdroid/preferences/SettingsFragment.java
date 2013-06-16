package at.tugraz.musicdroid.preferences;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceFragment;
import android.util.Log;
import at.tugraz.musicdroid.R;
import at.tugraz.musicdroid.dialog.MetronomQuickSettingsDialog;
import at.tugraz.musicdroid.dialog.SoundLenghtDialog;

public class SettingsFragment extends PreferenceFragment implements OnPreferenceClickListener, OnSharedPreferenceChangeListener{
	private SoundLenghtDialog settingsDialog = null;
	private MetronomQuickSettingsDialog metronomSettingsDialog = null;
	private Preference metronomDialogPreference = null;
	private Preference dialogPreference = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
        
    	settingsDialog = new SoundLenghtDialog();
    	metronomSettingsDialog = new MetronomQuickSettingsDialog();
        
    	dialogPreference = (Preference) getPreferenceScreen().findPreference("preferences_max_soundmixer_length");
        dialogPreference.setOnPreferenceClickListener(this);
        
        metronomDialogPreference = (Preference) getPreferenceScreen().findPreference("preferences_bpm");
        metronomDialogPreference.setOnPreferenceClickListener(this);

    }
    

	@Override
	public boolean onPreferenceClick(Preference preference) {
		if(preference.getKey() == dialogPreference.getKey()) {
	        settingsDialog.show(getFragmentManager(), null);
            return true;
        }
		else if(preference.getKey() == metronomDialogPreference.getKey()) {
           	metronomSettingsDialog.show(getFragmentManager(), null);
            return true;
        }
		
		return false;
	}   
    
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("preferences_max_soundmixer_length")) {
        }
    }
    
    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onPause() {
    	getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

}