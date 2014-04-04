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
package org.catrobat.musicdroid.soundtracks;

import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.catrobat.musicdroid.MainActivity;
import org.catrobat.musicdroid.R;
import org.catrobat.musicdroid.soundmixer.SoundMixer;

public class SoundTrackViewMenuCallback implements ActionMode.Callback {
	MainActivity parent = null;

	public SoundTrackViewMenuCallback(MainActivity p) {
		parent = p;
	}

	@Override
	public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
		return false;
	}

	@Override
	public void onDestroyActionMode(ActionMode mode) {
		SoundMixer.getInstance().enableUnselectedViews();
	}

	@Override
	public boolean onCreateActionMode(ActionMode mode, Menu menu) {
		parent.getMenuInflater().inflate(R.menu.sound_track_dialog_menu, menu);
		SoundMixer.getInstance().disableUnselectedViews();

		String name = null;
		if ((name = SoundMixer.getInstance().getCallingTrack().getName()) != null) {
			mode.setTitle(name);
		} else {
			mode.setTitle("Fix this");
			Log.e("TODO", "Fix this!");
		}
		return true;
	}

	/**
	 * This is called when an item in the context menu is selected
	 */
	@Override
	public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
		switch (item.getItemId()) {
			case R.id.soundtrack_context_edit:
				Toast.makeText(parent.getBaseContext(), "Editing of tracks not yet implemented ", Toast.LENGTH_LONG)
						.show();
				mode.finish();
				break;
			case R.id.soundtrack_context_copy:
				SoundMixer.getInstance().handleCopy();
				mode.finish();
				break;
			case R.id.soundtrack_context_delete:
				SoundMixer.getInstance().deleteCallingTrack();
				mode.finish();
				break;
		}
		return false;
	}
}
