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
package org.catrobat.musicdroid;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.Log;
import android.util.SparseIntArray;

public final class SoundManager {
	private static final SoundManager INSTANCE = new SoundManager();
	private static final String TAG = SoundManager.class.getSimpleName();
	private static SoundPool soundPool;
	private static SparseIntArray soundPoolMap;
	private static SparseIntArray soundPlayMap;
	private static AudioManager audioManager;
	private static Context context;

	private SoundManager() {
	}

	public static SoundManager getInstance() {
		return INSTANCE;
	}

	public static void initSounds(Context theContext) {
		context = theContext;
		soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
		soundPoolMap = new SparseIntArray();
		soundPlayMap = new SparseIntArray();
		audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
	}

	public static void addSound(int index, int soundId) {
		soundPoolMap.put(index, soundPool.load(context, soundId, 1));
	}

	public static int addSoundByPath(String path) {
		int soundId = soundPool.load(path, 1);
		int position = soundPoolMap.size() + 1;
		soundPoolMap.put(position, soundId);
		return position;
	}

	public static int loadSound(int rawId) {
		int position = soundPoolMap.size() + 1;
		soundPoolMap.put(position, soundPool.load(context, rawId, 1));
		return position;
	}

	public static void loadSounds() {
		soundPoolMap.put(soundPlayMap.size() + 1, soundPool.load(context, R.raw.test_midi, 1));
		soundPoolMap.put(soundPlayMap.size() + 1, soundPool.load(context, R.raw.test_wav, 1));
	}

	public static void playSoundByRawId(int rawId, float speed) {
		playSound(soundPoolMap.get(rawId), speed, 1);
	}

	public static void stopSoundByRawId(int rawId) {
		stopSound(soundPoolMap.get(rawId));
	}

	public static void playSound(int index, float speed, float volume) {
		float streamVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		streamVolume = streamVolume / audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

		Log.i("SoundManager", "SoundPoolID = " + index);
		int poolId = soundPoolMap.get(index);
		Integer streamId = soundPool.play(poolId, volume, volume, 1, 0, speed);
		Log.d("PUT: ", "" + index + " " + streamId);
		soundPlayMap.put(index, streamId);
	}

	public static void stopAllSounds() {
		for (int index = 0; index < soundPlayMap.size(); index++) {
			soundPool.stop(soundPlayMap.valueAt(index));
		}
	}

	public static void stopSound(int index) {
		soundPool.stop(soundPlayMap.get(index));
		soundPlayMap.removeAt(index);
	}

	public static void cleanup() {
		soundPool.release();
		soundPool = null;
		soundPoolMap.clear();
		audioManager.unloadSoundEffects();
	}

	public static int getSoundfileDuration(int soundfileId) {
		MediaPlayer player = MediaPlayer.create(context, soundfileId);
		int duration = player.getDuration();
		return duration / 1000;
	}

	public static int getSoundfileDurationByPath(String path) {
		Log.i("Player", "playRecorderFile");
		MediaPlayer player = new MediaPlayer();

		try {
			player.setDataSource(path);
			player.prepare();
		} catch (Exception e) {
			Log.e(TAG, "Exception", e);
		}

		return player.getDuration() / 1000;

	}

}
