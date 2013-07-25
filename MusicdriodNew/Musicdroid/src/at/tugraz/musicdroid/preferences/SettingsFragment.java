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

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceFragment;
import at.tugraz.musicdroid.R;
import at.tugraz.musicdroid.dialog.MetronomQuickSettingsDialog;
import at.tugraz.musicdroid.dialog.SoundLenghtDialog;

public class SettingsFragment extends PreferenceFragment implements
		OnPreferenceClickListener, OnSharedPreferenceChangeListener {
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

		dialogPreference = (Preference) getPreferenceScreen().findPreference(
				"preferences_max_soundmixer_length");
		dialogPreference.setOnPreferenceClickListener(this);

		metronomDialogPreference = (Preference) getPreferenceScreen()
				.findPreference("preferences_bpm");
		metronomDialogPreference.setOnPreferenceClickListener(this);

	}

	@Override
	public boolean onPreferenceClick(Preference preference) {
		if (preference.getKey() == dialogPreference.getKey()) {
			settingsDialog.show(getFragmentManager(), null);
			return true;
		} else if (preference.getKey() == metronomDialogPreference.getKey()) {
			metronomSettingsDialog.show(getFragmentManager(), null);
			return true;
		}

		return false;
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		if (key.equals("preferences_max_soundmixer_length")) {
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		getPreferenceScreen().getSharedPreferences()
				.registerOnSharedPreferenceChangeListener(this);

	}

	@Override
	public void onPause() {
		getPreferenceScreen().getSharedPreferences()
				.unregisterOnSharedPreferenceChangeListener(this);
		super.onPause();
	}

}
