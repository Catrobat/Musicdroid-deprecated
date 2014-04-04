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
package org.catrobat.musicdroid.soundtracks;

import android.util.Log;

import org.catrobat.musicdroid.SoundManager;
import org.catrobat.musicdroid.types.SoundType;

import java.util.Observable;
import java.util.Observer;

public class SoundTrack implements Observer {
	protected SoundType type = null;
	protected String name = null;
	protected int duration = 0; // in seconds
	protected int startPoint = 0;
	protected boolean isMidi;
	protected int soundfileRawId;
	protected int soundpoolId;
	protected float volume;

	public SoundTrack() {
		volume = 1;
	}

	public SoundTrack(SoundTrack s) {
		this.type = s.getType();
		this.name = s.getName();
		this.duration = s.duration;
		this.volume = s.volume;
	}

	public void setStartPoint(int start) {
		Log.e("START POINT", "" + start);
		startPoint = start;
	}

	public SoundType getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public int getDuration() {
		return duration;
	}

	public boolean isMidi() {
		return isMidi;
	}

	public int getSoundfileId() {
		return soundfileRawId;
	}

	public int getSoundPoolId() {
		return soundpoolId;
	}

	public float getVolume() {
		return volume;
	}

	public void setVolume(float vol) {
		Log.e("SET VOLUME", "" + vol);
		volume = vol;
	}

	@Override
	public void update(Observable observable, Object data) {
		int curTime = (Integer) data;
		Log.i("Incoming Object: ", "" + curTime);
		if (curTime == startPoint) {
			SoundManager.playSound(soundpoolId, 1, volume);
		}
	}

}
