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

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.util.Log;

import org.catrobat.musicdroid.R;
import org.catrobat.musicdroid.preferences.PreferenceManager;
import org.catrobat.musicdroid.soundmixer.SoundMixer;
import org.catrobat.musicdroid.tools.FileExtensionMethods;

import java.io.File;

public final class AudioHandler {
	public static AudioHandler instance = null;
	private RecorderLayout layout = null;
	private Context context = null;
	private String path = null;
	private String filename = null;
	private boolean init = false;
	private Recorder recorder = null;
	private Player player = null;
	private AudioVisualizer visualizer = null;

	private AudioHandler() {
		this.path = Environment.getExternalStorageDirectory().getAbsolutePath();
		this.filename = "test.mp3";
	}

	public static AudioHandler getInstance() {
		if (instance == null) {
			instance = new AudioHandler();
		}
		return instance;
	}

	public void init(Context context, RecorderLayout layout) {
		if (init) {
			return;
		}

		this.context = context;
		this.layout = layout;
		visualizer = new AudioVisualizer(context);
		recorder = new Recorder(context, layout, visualizer);
		player = new Player(layout);
		init = true;
	}

	public boolean startRecording() {
		Log.i("AudioHandler", "StartRecording");
		File check = new File(path + '/' + filename);
		if (check.exists()) {
			showDialog();
		} else {
			checkAndStartPlaybackAndMetronom();
			recorder.record();
		}
		return true;
	}

	public void stopRecording() {
		recorder.stopRecording();
		if (PreferenceManager.getInstance().getPreference(PreferenceManager.PLAY_PLAYBACK_KEY) == 1) {
			SoundMixer.getInstance().stopAllSoundInSoundMixerAndRewind();
		} else if (PreferenceManager.getInstance().getPreference(PreferenceManager.METRONOM_VISUALIZATION_KEY) > 0) {
			SoundMixer.getInstance().stopMetronom();
		}
	}

	public void playRecordedFile() {
		player.playRecordedFile();
	}

	public void stopRecordedFile() {
		player.stopPlaying();
	}

	private void showDialog() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		alertDialogBuilder.setMessage(R.string.dialog_warning_file_overwritten_at_record).setCancelable(true)
				.setPositiveButton(R.string.dialog_abort, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						layout.resetLayoutToRecord();
					}
				}).setNegativeButton(R.string.dialog_continue, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
						checkAndStartPlaybackAndMetronom();
						recorder.record();
					}
				});
		AlertDialog alertNewImage = alertDialogBuilder.create();
		alertNewImage.show();
	}

	private void checkAndStartPlaybackAndMetronom() {
		int metronom = PreferenceManager.getInstance().getPreference(PreferenceManager.METRONOM_VISUALIZATION_KEY);
		if (PreferenceManager.getInstance().getPreference(PreferenceManager.PLAY_PLAYBACK_KEY) == 1) {
			if (!SoundMixer.getInstance().playAllSoundsInSoundmixer() && metronom > 0) {
				SoundMixer.getInstance().startMetronom();
			}
		} else if (metronom > 0) {
			SoundMixer.getInstance().startMetronom();
		}
	}

	public String getFilenameFullPath() {
		return path + "/" + filename;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String f) {
		this.filename = f;
		layout.updateFilename(FileExtensionMethods.removeFileEnding(this.filename));
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public String getPath() {
		return path;
	}

	public void reset() {
		init = false;
		instance = null;
	}
}
