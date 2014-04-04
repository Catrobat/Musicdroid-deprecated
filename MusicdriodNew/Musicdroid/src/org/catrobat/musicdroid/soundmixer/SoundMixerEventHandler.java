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
package org.catrobat.musicdroid.soundmixer;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import org.catrobat.musicdroid.SoundManager;
import org.catrobat.musicdroid.preferences.PreferenceManager;
import org.catrobat.musicdroid.tools.DeviceInfo;

import java.util.Observable;

public class SoundMixerEventHandler extends Observable {
	private SoundMixer mixer;
	private int longestTrack = 0;
	private int endPoint = 0;
	private int startPoint = 0;
	private int stopPoint = 0;
	private int time = 0;
	private int screenWidth;
	private int secondInPixel;
	private boolean shouldContinue;

	public SoundMixerEventHandler(SoundMixer m) {
		mixer = m;
		setEndPoint(PreferenceManager.getInstance().getPreference(PreferenceManager.SOUNDTRACK_DEFAULT_LENGTH_KEY));
		screenWidth = DeviceInfo.getScreenWidth(mixer.parentActivity);
		secondInPixel = screenWidth
				/ PreferenceManager.getInstance().getPreference(PreferenceManager.SOUNDTRACK_DEFAULT_LENGTH_KEY);
	}

	public void play() {
		if (countObservers() > 0) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					time = setStartTime();
					shouldContinue = true;
					while (shouldContinue && time <= endPoint) {
						try {
							Thread.sleep(1000);
							setChanged();
							notifyObservers(time);
							time = time + 1;
							if (shouldContinue && time < endPoint) {
								sendTrackPositionMessage(time);
							}
						} catch (Exception e) {
						}
					}
					Log.i("TIME: " + time, "EndPoint: " + endPoint);
					SoundManager.stopAllSounds();
					return;
				}
			}).start();
		}
	}

	private void sendTrackPositionMessage(int time) {
		Log.i("Set position message", "");
		Message msg = new Message();
		Bundle b = new Bundle();
		b.putInt("position", time);
		msg.setData(b);
		mixer.getTimeline().getTimelineEventHandler().sendMessage(msg);
	}

	public void stopNotifyThread() {
		stopPoint = time;
		shouldContinue = false;
	}

	public void rewind() {
		stopPoint = startPoint;
	}

	public void setLongestTrack(int length) {
		longestTrack = length;
		computeSecondInPixel();
	}

	public void computeSecondInPixel() {
		Log.d("Longest Track ", "" + longestTrack);
		secondInPixel = screenWidth / longestTrack;
	}

	public int computeStartPointInSecondsByPixel(int startPosPixel) {
		return startPosPixel / secondInPixel;
	}

	public int getEndPoint() {
		return endPoint;
	}

	public int getStartPoint() {
		return startPoint;
	}

	public int getStopPoint() {
		return stopPoint;
	}

	public boolean setStartPoint(int startPoint) {
		if (startPoint > endPoint) {
			return false;
		}

		this.startPoint = startPoint;
		return true;
	}

	private int setStartTime() {
		if (stopPoint > startPoint) {
			return stopPoint;
		} else {
			return startPoint;
		}
	}

	public boolean setEndPoint(int endPoint) {
		Log.i("Set EndPoint", "EndPoint = " + endPoint);

		if (endPoint < startPoint) {
			return false;
		}

		this.endPoint = endPoint;
		return true;
	}

}
