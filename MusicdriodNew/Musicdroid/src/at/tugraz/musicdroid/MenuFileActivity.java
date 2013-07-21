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
package at.tugraz.musicdroid;

import java.io.File;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;
import android.widget.Toast;
import at.tugraz.musicdroid.soundmixer.SoundMixer;

public class MenuFileActivity extends FragmentActivity {

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_item_load_song:
			break;
		case R.id.menu_item_new_song:
			onNewSong();
			break;
		case R.id.menu_item_save_song:
			;
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}

	private void onNewSong() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder
				.setMessage(R.string.dialog_warning_new_song)
				.setCancelable(true)
				.setPositiveButton(R.string.yes,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								createNewSoundMixer();
							}
						})
				.setNegativeButton(R.string.no,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
								Toast.makeText(getApplicationContext(),
										R.string.save_suggestion,
										Toast.LENGTH_LONG).show();
							}
						});
		AlertDialog alertNewImage = alertDialogBuilder.create();
		alertNewImage.show();
	}

	// TODO ms do this in a seperate class
	public void prepareFolderStructure() {
		new File(Environment.getExternalStorageDirectory().getAbsolutePath(),
				"musicdroid").mkdir();
		new File(Environment.getExternalStorageDirectory().getAbsolutePath(),
				"musicdroid/soundtracks").mkdir();
		new File(Environment.getExternalStorageDirectory().getAbsolutePath(),
				"musicdroid/soundtracks/mic").mkdir();
		new File(Environment.getExternalStorageDirectory().getAbsolutePath(),
				"musicdroid/soundtracks/piano").mkdir();

	}

	private void createNewSoundMixer() {
		SoundMixer.getInstance().resetSoundMixer();
	}

}
