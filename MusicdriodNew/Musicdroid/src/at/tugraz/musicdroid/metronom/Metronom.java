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
package at.tugraz.musicdroid.metronom;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.widget.ImageView;
import at.tugraz.musicdroid.animation.MetronomAnimation;
import at.tugraz.musicdroid.preferences.PreferenceManager;
import at.tugraz.musicdroid.soundmixer.Statusbar;

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
	private final int tickLengthInSamples = 1000; //
	private AudioGenerator audioGenerator = new AudioGenerator(8000);

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
		silenceDuration = (int) (((60 / beatsPerMinute) * 8000) - tickLengthInSamples);
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
			double[] tickArray = audioGenerator.getSineWave(
					tickLengthInSamples, 8000, accentSound);
			double[] tockArray = audioGenerator.getSineWave(
					tickLengthInSamples, 8000, sound);
			double silence = 0;
			double[] soundArray = new double[8000];
			int t = 0, s = 0, b = 0;

			@Override
			public void run() {
				while (play) {
					for (int i = 0; i < soundArray.length && play; i++) {
						if (t < tickLengthInSamples) {
							if (b == 0) {
								soundArray[i] = tockArray[t];
							} else {
								soundArray[i] = tickArray[t];
							}
							t++;
						} else {
							soundArray[i] = silence;
							s++;
							if (s >= silenceDuration) {
								t = 0;
								s = 0;
								b++;
								if (b > (beat - 1))
									b = 0;
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
