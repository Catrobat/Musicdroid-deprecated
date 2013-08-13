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

import org.catrobat.musicdroid.R;
import org.catrobat.musicdroid.RecorderActivity;
import org.catrobat.musicdroid.dialog.ChangeFilenameDialog;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;

/**
 * @author matthias schlesinger
 *
 */
public class RecorderOnClickListener implements OnClickListener, OnLongClickListener {
	private RecorderActivity recorderActivity;
	private RecorderLayout recorderLayout;
	private AudioHandler audioHandler;
	
	public RecorderOnClickListener(RecorderActivity activity, RecorderLayout layout)
	{
		recorderActivity = activity;
		recorderLayout = layout;
		audioHandler = new AudioHandler(activity, layout);
	}
	
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.microphone_record_button) {
			handleOnRecordClick();
		}
		if (v.getId() == R.id.microphone_play_button) {
			handleOnPlayClick();
		}
		if (v.getId() == R.id.microphone_add_to_sound_mixer_box) {
			handleOnAddToSoundmixerClick();
		}
	}

	@Override
	public boolean onLongClick(View v) {
		if (v.getId() == R.id.microphone_filename) {
			handleOnFilenameLongClick();
		}
		return false;
	}
	
	private void handleOnFilenameLongClick() {
		ChangeFilenameDialog dialog = new ChangeFilenameDialog();
		dialog.show(((RecorderActivity) recorderActivity).getFragmentManager(), null);
	}

	private void handleOnRecordClick() {
		if(recorderLayout.inStateRecording())
		{
			audioHandler.stopRecording();
			recorderLayout.updateLayoutOnRecordStop();
		}
		else
		{
			audioHandler.startRecording(recorderActivity.getCurrentRecordingSession());
			recorderLayout.updateLayoutOnRecordStart();
		}
	}

	private void handleOnPlayClick() {
		if(recorderLayout.inStatePlaying())
		{
			audioHandler.stopRecordedFile();
			recorderLayout.updateLayoutOnPause();
		}
		else
		{
			audioHandler.playRecordedFile(recorderActivity.getCurrentRecordingSession());
			recorderLayout.updateLayoutOnPlay();
		}
	}

	private void handleOnAddToSoundmixerClick() {
		recorderActivity.returnToMainActivtiy();
	}

}
