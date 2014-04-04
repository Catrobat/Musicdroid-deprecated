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

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;

import org.catrobat.musicdroid.recorder.AudioHandler;
import org.catrobat.musicdroid.recorder.RecorderLayout;
import org.catrobat.musicdroid.recorder.RecorderMenuCallback;
import org.catrobat.musicdroid.soundmixer.Statusbar;

public class RecorderActivity extends FragmentActivity {
	private RecorderLayout layout = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("RecorderActivitiy", "ONCREATE");

		setContentView(R.layout.activity_recorder);
		initTopStatusBar();
		Statusbar.getInstance().initStatusbar(this);
		Statusbar.getInstance().modifyStatusbarForRecorderActivity();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.recorder_menu, menu);
		return true;
	}

	@Override
	public void onBackPressed() {
		if (layout.isSoundRecorded()) {
			showSecurityQuestionBeforeExit();
		} else {
			finish();
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.i("RecorderActivity", "ON RESUME");

		setContentView(R.layout.activity_recorder);

		layout = new RecorderLayout();
		layout.init(this);

		AudioHandler.getInstance().init(this, layout);
		AudioHandler.getInstance().setContext(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		layout.reset();
		AudioHandler.getInstance().reset();
		Log.i("RecorderActivity",
				"OnPause: " + ((LinearLayout) findViewById(R.id.recorder_activity_layout)).getChildCount());
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		Log.i("RecorderActivity", "onOptionsItemSelected");
		switch (item.getItemId()) {
			case R.id.btn_settings:
				RecorderMenuCallback callbackSoundMixerMenu = new RecorderMenuCallback(this);
				startActionMode(callbackSoundMixerMenu);
				return true;
		}
		return false;
	}

	private void initTopStatusBar() {
		getActionBar().setCustomView(R.layout.status_bar);
		getActionBar().setDisplayShowHomeEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayShowCustomEnabled(true);
		getActionBar().setIcon(R.drawable.ic_launcher);
	}

	public void returnToMainActivtiy() {
		Intent returnIntent = new Intent();
		returnIntent.putExtra("mic_filename", AudioHandler.getInstance().getFilenameFullPath());
		setResult(RESULT_OK, returnIntent);
		finish();
	}

	private void showSecurityQuestionBeforeExit() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.on_recorder_back_pressed_security_question);
		builder.setCancelable(true);
		builder.setNegativeButton(R.string.yes, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
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
}
