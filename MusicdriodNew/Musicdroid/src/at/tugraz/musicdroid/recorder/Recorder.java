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
package at.tugraz.musicdroid.recorder;

import java.io.IOException;

import android.content.Context;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

public class Recorder {
	private Context context = null;
	private RecorderLayout layout = null;
	private static MediaRecorder recorder = null;
	private AudioVisualizer visualizer = null;
	private long startTime = 0;
	private boolean stop = false;

	public Recorder(Context c, RecorderLayout layout, AudioVisualizer visualizer) {
		this.context = c;
		this.layout = layout;
		this.visualizer = visualizer;
	}

	public boolean record() {
		recorder = new MediaRecorder();
		stop = false;

		setPreferences();
		try {
			recorder.prepare();
		} catch (IOException e) {
			Log.e("LOG", "prepare() failed");
		}

		recorder.start();
		startTime = System.currentTimeMillis();
		startCommunicationThread();
		return true;

	}

	public void stopRecording() {
		recorder.stop();
		stop = true;

	}

	private void startCommunicationThread() {
		Thread background = new Thread(new Runnable() {
			@Override
			public void run() {
				int sleepCounter = 1;
				while (!stop) {
					try {
						Thread.sleep(125);

						if (sleepCounter % 8 == 0)
							sendDurationMessage();

						sendAmplitudeMessage();

						sleepCounter = sleepCounter + 1;
					} catch (Exception e) {
						Log.v("Error", e.toString());
					}
				}
			}
		});
		background.start();
	}

	private void setPreferences() {
		Log.i("Recorder", "Filename = "
				+ AudioHandler.getInstance().getFilenameFullPath());
		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		recorder.setOutputFile(AudioHandler.getInstance().getFilenameFullPath());
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
	}

	private void sendDurationMessage() {
		Message msg = new Message();
		Bundle b = new Bundle();
		b.putInt("duration",
				(int) ((System.currentTimeMillis() - startTime) / 1000));
		msg.setData(b);
		layout.sendMessage(msg);
	}

	private void sendAmplitudeMessage() {
		Message msg = new Message();
		Bundle b = new Bundle();
		b.putInt("amplitude", recorder.getMaxAmplitude());
		msg.setData(b);
		visualizer.sendMessage(msg);
	}

	public void setLayout(RecorderLayout l) {
		layout = l;
	}

}
