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

import java.io.File;

import org.catrobat.musicdroid.R;
import org.catrobat.musicdroid.preferences.PreferenceManager;
import org.catrobat.musicdroid.soundmixer.SoundMixer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class AudioHandler {
	public static AudioHandler instance = null;
	private RecorderLayout layout = null;
	private Context context = null;
	private Recorder recorder = null;
	private Player player = null;
	
	public AudioHandler(Context context, RecorderLayout layout)
	{
		this.context = context;
		this.layout = layout;
		RecorderMessageDispatcher messageDispatcher = new RecorderMessageDispatcher(layout, 
			  	  												new AudioVisualizer(context)); 
		recorder = new Recorder(context, messageDispatcher);
		player = new Player(layout);
	}
	
	public boolean startRecording(RecordingSession currentSession) {
		File check = new File(currentSession.getPathToFile());
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
		if (playPlayback())
			SoundMixer.getInstance().stopAllSoundInSoundMixerAndRewind();
		else if (playMetronom())
			SoundMixer.getInstance().stopMetronom();
	}
	
	private boolean playPlayback()
	{
		return PreferenceManager.getInstance().getPreference(
					PreferenceManager.PLAY_PLAYBACK_KEY) == 1;
	}
	
	private boolean playMetronom()
	{
		return PreferenceManager.getInstance().getPreference(
					PreferenceManager.METRONOM_VISUALIZATION_KEY) > 0;
	}

	public void playRecordedFile(RecordingSession currentSession) {
		player.playRecordedFile(currentSession.getPathToFile());
	}

	public void stopRecordedFile() {
		player.stopPlaying();
	}

	private void showDialog() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		alertDialogBuilder
				.setMessage(R.string.dialog_warning_file_overwritten_at_record)
				.setCancelable(true)
				.setPositiveButton(R.string.dialog_abort,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								layout.resetLayoutToRecord();
							}
						})
				.setNegativeButton(R.string.dialog_continue,
						new DialogInterface.OnClickListener() {
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
		if (playPlayback()) 
		{
			if (!SoundMixer.getInstance().playAllSoundsInSoundmixer() && playMetronom())
				SoundMixer.getInstance().startMetronom();
		} 
		else if (playMetronom()) 
		{
			SoundMixer.getInstance().startMetronom();
		}
	}
}
