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

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

public class AudioGenerator {

	private int sampleRate;
	private AudioTrack audioTrack;

	public AudioGenerator(int sampleRate) {
		this.sampleRate = sampleRate;
	}

	public double[] getSineWave(int samples, int sampleRate,
			double frequencyOfTone) {
		double[] sample = new double[samples];
		for (int i = 0; i < samples; i++) {
			sample[i] = Math.sin(2 * Math.PI * i
					/ (sampleRate / frequencyOfTone));
		}
		return sample;
	}

	public byte[] get16BitPcm(double[] samples) {
		byte[] generatedSound = new byte[2 * samples.length];
		int index = 0;
		for (double sample : samples) {
			short maxSample = (short) ((sample * Short.MAX_VALUE));
			generatedSound[index++] = (byte) (maxSample & 0x00ff);
			generatedSound[index++] = (byte) ((maxSample & 0xff00) >>> 8);

		}
		return generatedSound;
	}

	public void createPlayer() {
		audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, sampleRate,
				AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT,
				sampleRate, AudioTrack.MODE_STREAM);

		audioTrack.play();
	}

	public void writeSound(double[] samples) {
		byte[] generatedSnd = get16BitPcm(samples);
		audioTrack.write(generatedSnd, 0, generatedSnd.length);
	}

	public void destroyAudioTrack() {
		audioTrack.stop();
		audioTrack.release();
	}

}
