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

import android.content.Context;
import android.media.MediaRecorder;
import android.util.Log;

import org.catrobat.musicdroid.RecorderActivity;

import java.io.IOException;

public class Recorder {
	private Context context = null;
	private static MediaRecorder recorder = null;
	private RecorderMessageDispatcher messageDispatcher = null;
	private CommunicationThread communicationThread = null;

	public Recorder(Context context, RecorderMessageDispatcher dispatcher) {
		this.context = context;
		this.messageDispatcher = dispatcher;
		this.communicationThread = new CommunicationThread(dispatcher, this);
	}

	public boolean record() {
		recorder = new MediaRecorder();
		communicationThread = new CommunicationThread(messageDispatcher, this);

		setPreferences();
		try {
			recorder.prepare();
		} catch (IOException e) {
			Log.e("LOG", "prepare() failed");
		}

		recorder.start();
		communicationThread.start();
		return true;

	}

	public void stopRecording() {
		recorder.stop();
		communicationThread.stopThread();
		try {
			communicationThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void setPreferences() {
		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		recorder.setOutputFile(((RecorderActivity) context).getCurrentRecordingSession().getPathToFile());
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
	}

	public int getMaxAmplitude() {
		return recorder.getMaxAmplitude();
	}

}
