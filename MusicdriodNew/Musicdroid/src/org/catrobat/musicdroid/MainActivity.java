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
package org.catrobat.musicdroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import org.catrobat.musicdroid.dialog.AddSoundDialog;
import org.catrobat.musicdroid.dialog.SoundLenghtDialog;
import org.catrobat.musicdroid.preferences.PreferenceActivity;
import org.catrobat.musicdroid.soundmixer.SoundMixer;
import org.catrobat.musicdroid.soundmixer.SoundMixerMenuCallback;
import org.catrobat.musicdroid.soundmixer.Statusbar;
import org.catrobat.musicdroid.soundmixer.timeline.TimelineMenuCallback;
import org.catrobat.musicdroid.soundtracks.SoundTrack;
import org.catrobat.musicdroid.soundtracks.SoundTrackMic;
import org.catrobat.musicdroid.soundtracks.SoundTrackView;
import org.catrobat.musicdroid.soundtracks.SoundTrackViewMenuCallback;

public class MainActivity extends MenuFileActivity {
	protected Statusbar statusbar;
	protected SoundMixer mixer;
	protected SoundLenghtDialog settingsDialog = null;
	private TimelineMenuCallback callbackTimelineMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.prepareFolderStructure();

		AddSoundDialog.init(this);

		setContentView(R.layout.activity_main);

		initTopStatusBar();
		Statusbar.getInstance().initStatusbar(this);

		SoundMixer.getInstance().initSoundMixer(this);

		// TESTING
		SoundManager.getInstance();
		SoundManager.initSounds(this);
		SoundManager.loadSounds();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
			case R.id.menu_item_quit:
				showSecurityQuestionBeforeExit();
				return true;
			case R.id.menu_item_preferences:
				Intent intent = new Intent(MainActivity.this, PreferenceActivity.class);
				MainActivity.this.startActivity(intent);
				return true;
			case android.R.id.home:
				showSecurityQuestionBeforeExit();
				return true;
			case R.id.btn_add:
				AddSoundDialog.getInstance().show();
				return true;
			case R.id.btn_settings:
				SoundMixerMenuCallback callbackSoundMixerMenu = new SoundMixerMenuCallback(this);
				startActionMode(callbackSoundMixerMenu);
				return true;
			default:
				// calls MenuFileActivitys onOptionItemSelect for all File-Related entries
				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onBackPressed() {
		showSecurityQuestionBeforeExit();
	}

	@Override
	public void onResume() {
		super.onResume();
		Statusbar.getInstance().initStatusbar(this);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		SoundMixer.getInstance().resetSoundMixer();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == 1) {

			if (resultCode == Activity.RESULT_OK) {
				if (data.hasExtra("mic_filename")) {
					String result = data.getStringExtra("mic_filename");
					Log.i("MainActivity", "Received String from Activity " + result);
					SoundTrackMic stm = new SoundTrackMic(result);
					addSoundTrack(new SoundTrackView(this, stm));
				}
			}
			if (resultCode == Activity.RESULT_CANCELED) {
				// probably not needed
			}
		}
	}

	private void showSecurityQuestionBeforeExit() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.closing_security_question);
		builder.setCancelable(true);
		builder.setNegativeButton(R.string.yes, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				SoundMixer.getInstance().resetSoundMixer();
				finish();
			}
		});
		builder.setPositiveButton(R.string.no, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}

	public void startActionMode(int id, SoundTrack soundTrack) {
		SoundMixer.getInstance().setCallingParameters(id, soundTrack);
		SoundTrackViewMenuCallback callbackSoundTrackViewMenu = new SoundTrackViewMenuCallback(this);
		startActionMode(callbackSoundTrackViewMenu);
	}

	public void startTimelineActionMode() {
		startActionMode(callbackTimelineMenu);
	}

	public void addSoundTrack(SoundTrackView track) {
		SoundMixer.getInstance().addSoundTrackViewToSoundMixer(track);
	}

	public void setCallbackTimelineMenu(TimelineMenuCallback callback) {
		callbackTimelineMenu = callback;
	}

	private void initTopStatusBar() {
		getActionBar().setCustomView(R.layout.status_bar);
		getActionBar().setDisplayShowHomeEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayShowCustomEnabled(true);
		getActionBar().setIcon(R.drawable.ic_launcher);
	}

}
