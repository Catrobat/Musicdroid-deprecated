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

import android.util.Log;

/**
 * @author matthias schlesinger
 * 
 */
public class CommunicationThread extends Thread {
	private RecorderMessageDispatcher messageDispatcher = null;
	private Recorder recorder = null;
	private long startTime;
	private boolean stop = false;

	public CommunicationThread(RecorderMessageDispatcher dispatcher, Recorder recorder) {
		this.messageDispatcher = dispatcher;
		this.recorder = recorder;
	}

	@Override
	public void run() {
		stop = false;
		startTime = System.currentTimeMillis();
		int sleepCounter = 1;
		while (!stop) {
			try {
				Thread.sleep(125);
				sendCommunicationMessages(sleepCounter);
				sleepCounter = sleepCounter + 1;
			} catch (Exception e) {
				Log.v("Error", e.toString());
			}
		}
	}

	public void stopThread() {
		stop = true;
	}

	private void sendCommunicationMessages(int sleepCounter) {
		if (sleepCounter % 8 == 0)
			messageDispatcher.sendDurationMessage(startTime);

		messageDispatcher.sendAmplitudeMessage(recorder.getMaxAmplitude());
	}
}
