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
package org.catrobat.musicdroid.recorder;

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

import org.catrobat.musicdroid.R;
import org.catrobat.musicdroid.RecorderActivity;
import org.catrobat.musicdroid.dialog.MetronomQuickSettingsDialog;
import org.catrobat.musicdroid.preferences.PreferenceManager;

public class RecorderMenuCallback implements ActionMode.Callback {
	RecorderActivity parent = null;
	private MetronomQuickSettingsDialog metronomDialog = null;

	public RecorderMenuCallback(RecorderActivity p) {
		parent = p;
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

	/**
	 * Called when user exits action mode
	 */
	@Override
	public void onDestroyActionMode(ActionMode mode) {
		MenuItem item = mode.getMenu().getItem(1);
		if (item.getIcon().getConstantState() == parent.getResources().getDrawable(R.drawable.checkbox_unchecked)
				.getConstantState()) {
			PreferenceManager.getInstance().setPreference(PreferenceManager.PLAY_PLAYBACK_KEY, 0);
		} else {
			PreferenceManager.getInstance().setPreference(PreferenceManager.PLAY_PLAYBACK_KEY, 1);
		}
	}

	/**
	 * This is called when the action mode is created. This is called by
	 * startActionMode()
	 */
	@Override
	public boolean onCreateActionMode(ActionMode mode, Menu menu) {
		parent.getMenuInflater().inflate(R.menu.recorder_callback_menu, menu);
		mode.setTitle(R.string.recorder_context_title);

		MenuItem item = mode.getMenu().getItem(1);
		if (PreferenceManager.getInstance().getPreference(PreferenceManager.PLAY_PLAYBACK_KEY) == 1) {
			item.setIcon(R.drawable.checkbox_checked);
		} else {
			item.setIcon(R.drawable.checkbox_unchecked);
		}

		return true;
	}

	/**
	 * This is called when an item in the context menu is selected
	 */
	@Override
	public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
		switch (item.getItemId()) {
			case R.id.recorder_context_playback:
				if (item.getIcon().getConstantState() == parent.getResources()
						.getDrawable(R.drawable.checkbox_unchecked).getConstantState()) {
					item.setIcon(R.drawable.checkbox_checked);
				} else {
					item.setIcon(R.drawable.checkbox_unchecked);
				}
				break;
			case R.id.recorder_context_bpm:
				metronomDialog.show(parent.getFragmentManager(), null);
				mode.finish();
		}
		return false;
	}
}
