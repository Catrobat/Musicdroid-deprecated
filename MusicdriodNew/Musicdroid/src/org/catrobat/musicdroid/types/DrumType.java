/**
 * Free Drum Sounds taken from: http://www.musicradar.com/news/tech/free-music-samples-download-loops-hits-and-multis-217833/65
 * and http://www.musicradar.com/news/tech/sampleradar-285-free-real-world-drum-samples-533702
 */

package org.catrobat.musicdroid.types;

import android.content.Context;

import org.catrobat.musicdroid.R;

import java.util.ArrayList;

public enum DrumType {
	BASE_DRUM(R.string.base_drum, R.raw.base_drum), SNARE_DRUM(R.string.snare_drum, R.raw.snare_drum), SNARE_DRUM_HARD(
			R.string.snare_drum_hard, R.raw.snare_drum_hard), HIGH_HAT_CLOSED(R.string.high_hat_closed,
			R.raw.high_hat_closed), HIGH_HAT_OPEN(R.string.high_hat_open, R.raw.high_hat_open), TOM_HIGH(
			R.string.high_tom, R.raw.tom_high), TOM_LOW(R.string.low_tom, R.raw.tom_low), CRASH_ONE(R.string.crash_one,
			R.raw.crash_one), CRASH_TWO(R.string.crash_two, R.raw.crash_two), TAMBOURINE(R.string.tambourine,
			R.raw.tambourine);

	private int nameResource;
	private int soundResource;
	private static ArrayList<String> types = null;

	private DrumType(int nameResource, int soundResource) {
		this.nameResource = nameResource;
		this.soundResource = soundResource;
	}

	public static ArrayList<String> getTypeArray(Context context) {
		if (types == null) {
			types = new ArrayList<String>();
			for (int i = 0; i < DrumType.values().length; i++) {
				types.add(context.getResources().getString(DrumType.values()[i].nameResource));
			}
		}
		return types;
	}

	public int getNameResource() {
		return nameResource;
	}

	public int getSoundResource() {
		return soundResource;
	}

}
