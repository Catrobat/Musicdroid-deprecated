package at.tugraz.ist.musicdroid;

import java.util.HashMap;





import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;


public class SoundPlayer {
	private SoundPool soundPool;
	private HashMap<Integer, Integer> soundPoolMap;
	private static final int PRIORITY=1;
	private int soundID = 36;
	private Context context;
	//private AudioManager audiomanager;
	
	public SoundPlayer(Context cxt){
		context = cxt;
	}
	
	private void fillSoundpool (String path){
		Log.e("SoundPlayer:", "fillSound " + path);
		soundPoolMap.put(soundID, soundPool.load(path, PRIORITY));
	    soundID++;
		
		
	}
	
	
	public void initSoundpool () {
		Log.e("SoundPlayer:", "initSoundpool ");
		soundPool = new SoundPool(8, AudioManager.STREAM_MUSIC, 100);
        soundPoolMap = new HashMap<Integer, Integer>();
        //soundPool.setOnLoadCompleteListener(this);
        //audiomanager=(AudioManager)this.getSystemService(Context.AUDIO_SERVICE);
	}
	
	public void setSoundpool(String path){
		Log.e("SoundPlayer:", "fillSound " + path);
		fillSoundpool(path);
	}
	
	public void playSound(int midiValue){
		midiValue = midiValue - 35;
		AudioManager audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
		float curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		float maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		float leftVolume = curVolume/maxVolume;
		float rightVolume = curVolume/maxVolume;
		int priority = 1;
		int no_loop = 0;
		float normal_playback_rate = 1f;
		soundPool.play(midiValue, leftVolume, rightVolume, priority, no_loop, normal_playback_rate);
		
		//Toast.makeText(SoundPlayer.this, "soundPool.play()", Toast.LENGTH_LONG).show();
	}

}
