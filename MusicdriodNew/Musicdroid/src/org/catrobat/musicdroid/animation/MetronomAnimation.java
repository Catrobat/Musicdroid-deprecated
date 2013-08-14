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
package org.catrobat.musicdroid.animation;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import org.catrobat.musicdroid.R;

public class MetronomAnimation extends AnimationDrawable {
	private Context context;
	private int intervalTick;
	private int intervalTock;
	private int sign; // 1 or -1
	private int numIterationsToOneMs;

	public MetronomAnimation(Context context, double beatsPerMinute) {
		new AnimationDrawable();
		this.context = context;
		
		computeValues(beatsPerMinute);

		createBeatFrames(0);
		createBeatFrames(4);

		setOneShot(false);
	}
	
	private void createBeatFrames(int startBeat)
	{
		addBlinkingFrames(R.drawable.metronom_light_tick, getIntervalLength(startBeat));
		for (int i = startBeat + 1; i <= startBeat + 3; i++) {
			addBlinkingFrames(R.drawable.metronom_light_tock, getIntervalLength(i));
		}
	}
	
	private void addBlinkingFrames(int drawableRessource, int intervalLength)
	{
		addFrame(context.getResources().getDrawable(drawableRessource), intervalTick);
		addFrame(context.getResources().getDrawable(R.drawable.metronom_light), getIntervalLength(0));
	}

	private int getIntervalLength(int i) {
		return numIterationsToOneMs == i ? intervalTock + (-1 * sign) : intervalTock;
	}

	private void computeValues(double beatsPerMinute) {
		double secondsPerBeat = 60.0 / beatsPerMinute * 1000; // in milliseconds

		intervalTick = (int) Math.round(secondsPerBeat / 3);
		intervalTock = (int) Math.round(secondsPerBeat - (int) Math.round(secondsPerBeat / 3));

		double roundError = secondsPerBeat - (intervalTick + intervalTock);
		sign = roundError < 0 ? -1 : 1;
		numIterationsToOneMs = Math.abs((int) Math.round(1 / roundError));

		Log.i("MetronomAnimation", "SPB = " + secondsPerBeat
				+ " intervalTick = " + intervalTick + " intervalTock = "+ intervalTock);
		Log.i("MetronomAnimation", "RoundError = " + roundError
				+ " NumIterMs = " + numIterationsToOneMs);
	}

}
