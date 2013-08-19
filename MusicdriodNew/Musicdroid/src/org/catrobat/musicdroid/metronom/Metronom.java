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
package org.catrobat.musicdroid.metronom;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.widget.ImageView;
import org.catrobat.musicdroid.animation.MetronomAnimation;
import org.catrobat.musicdroid.preferences.PreferenceManager;
import org.catrobat.musicdroid.soundmixer.Statusbar;

public class Metronom {
	private static final int METRONOM_OFF = 0;
	private static final int METRONOM_LIGHT = 1;
	private static final int METRONOM_LIGHT_AND_SOUND = 2;
	private Context context = null;
	private ImageView metronomView = null;
	private AnimationDrawable animation = null;
	private boolean play = true;

	private double beatsPerMinute;
	private int beat;
	private int silenceDuration;
	private int metronomState;
	private double accentSound;
	private double sound;
	private static final int TICK_LENGTH_IN_SAMPLES = 1000; //
	private static final int SAMPLE_RATE = 8000;
	private AudioGenerator audioGenerator = new AudioGenerator(SAMPLE_RATE);

	public Metronom(Context c) {
		context = c;
	}

	private void initializeValues() {
		metronomView = Statusbar.getInstance().getMetronomLight();
		beatsPerMinute = PreferenceManager.getInstance().getPreference(
				PreferenceManager.METRONOM_BPM_KEY);
		metronomState = PreferenceManager.getInstance().getPreference(
				PreferenceManager.METRONOM_VISUALIZATION_KEY);
		audioGenerator.createPlayer();
		beat = 4;
		sound = 261.63;
		accentSound = 392.00;
		silenceDuration = (int) (((60 / beatsPerMinute) * SAMPLE_RATE) - TICK_LENGTH_IN_SAMPLES);
		animation = new MetronomAnimation(context, beatsPerMinute);
	}

	public void startMetronome() {
		Log.i("Metronom", "START");
		initializeValues();
		play = true;
		if (metronomState == METRONOM_OFF)
			return;
		if (metronomState >= METRONOM_LIGHT)
			blink();
		if (metronomState == METRONOM_LIGHT_AND_SOUND)
			play();
	}

	public void stopMetronome() {
		play = false;
		stopBlinking();
		audioGenerator.destroyAudioTrack();
	}

	private void play() {
		new Thread(new Runnable() {
			double[] tickArray = audioGenerator.getSineWave(TICK_LENGTH_IN_SAMPLES, SAMPLE_RATE, accentSound);
			double[] tockArray = audioGenerator.getSineWave(TICK_LENGTH_IN_SAMPLES, SAMPLE_RATE, sound);
			double silence = 0;
			double[] soundArray = new double[8000];
			int tickCounter = 0, sampleCounter = 0, beatCounter = 0;

			@Override
			public void run() {
				while (play) {
					for (int i = 0; i < soundArray.length && play; i++) {
						if (tickCounter < TICK_LENGTH_IN_SAMPLES) {
							if (beatCounter == 0) {
								soundArray[i] = tockArray[tickCounter];
							} else {
								soundArray[i] = tickArray[tickCounter];
							}
							tickCounter++;
						} else {
							soundArray[i] = silence;
							sampleCounter++;
							if (sampleCounter >= silenceDuration) {
								tickCounter = 0;
								sampleCounter = 0;
								beatCounter++;
								if (beatCounter > (beat - 1))
									beatCounter = 0;
							}
						}
					}
					audioGenerator.writeSound(soundArray);
				}
			}
		}).start();
	}

	private void blink() {
		metronomView.setImageDrawable(animation);
		animation.start();
	}

	private void stopBlinking() {
		animation.stop();
	}

}
