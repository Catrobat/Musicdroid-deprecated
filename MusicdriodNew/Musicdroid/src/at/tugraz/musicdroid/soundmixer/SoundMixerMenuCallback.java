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
package at.tugraz.musicdroid.soundmixer;

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import at.tugraz.musicdroid.MainActivity;
import at.tugraz.musicdroid.R;
import at.tugraz.musicdroid.dialog.MetronomQuickSettingsDialog;
import at.tugraz.musicdroid.dialog.SoundLenghtDialog;

public class SoundMixerMenuCallback implements ActionMode.Callback {
	MainActivity parent = null;
	private SoundLenghtDialog settingsDialog = null;
	private MetronomQuickSettingsDialog metronomDialog = null;

	public SoundMixerMenuCallback(MainActivity p) {
		parent = p;
		settingsDialog = new SoundLenghtDialog();
		metronomDialog = new MetronomQuickSettingsDialog();
	}

	/**
	 * Invoked whenever the action mode is shown. This is invoked immediately
	 * after onCreateActionMode
	 */
	@Override
	public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
		return false;
	}

	/** Called when user exits action mode */
	@Override
	public void onDestroyActionMode(ActionMode mode) {
	}

	/**
	 * This is called when the action mode is created. This is called by
	 * startActionMode()
	 */
	@Override
	public boolean onCreateActionMode(ActionMode mode, Menu menu) {
		parent.getMenuInflater().inflate(R.menu.sound_mixer_menu, menu);
		mode.setTitle(R.string.soundmixer_context_title);
		return true;
	}

	/** This is called when an item in the context menu is selected */
	@Override
	public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.soundmixer_context_bpm:
			metronomDialog.show(parent.getFragmentManager(), null);
			mode.finish();
			break;
		case R.id.soundmixer_context_length:
			settingsDialog.show(parent.getFragmentManager(), null);
			mode.finish();
			break;
		}
		return false;
	}
}
