package at.tugraz.musicdroid.preferences;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.widget.Toast;
import at.tugraz.musicdroid.R;
import at.tugraz.musicdroid.dialog.SoundLenghtDialog;

public class SettingsFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener{
	private SoundLenghtDialog settingsDialog = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
        
    	settingsDialog = new SoundLenghtDialog();
        
        Preference dialogPreference = (Preference) getPreferenceScreen().findPreference("preferences_max_soundmixer_length");
        dialogPreference.setOnPreferenceClickListener(new OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
        			settingsDialog.show(getFragmentManager(), null);
                    return true;
                }
            });
    }
    
    
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("preferences_max_soundmixer_length")) {
            //Show your AlertDialog here!
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