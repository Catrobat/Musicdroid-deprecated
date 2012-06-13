package at.tugraz.ist.musicdroid;

import java.io.IOException;
import java.util.HashMap;


import android.content.Context;

import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.SoundPool;
import android.util.Log;


public class SoundPlayer {
	private SoundPool soundPool;
	private HashMap<Integer, Integer> soundPoolMap;
	private static final int PRIORITY=1;
	private int soundID = 0;
	private Context context;
	private HashMap<Integer, PlayThread> threadMap = null;
	
	
	public SoundPlayer(Context cxt){
		context = cxt;
		threadMap = new HashMap<Integer, SoundPlayer.PlayThread>();
	}
	
	private void fillSoundpool (String path){
		
		soundPoolMap.put(soundID, soundPool.load(path, PRIORITY));
	    soundID++;
		
		
	}
	
	
	public void initSoundpool () {
		
		soundPool = new SoundPool(8, AudioManager.STREAM_MUSIC, 100);
        soundPoolMap = new HashMap<Integer, Integer>();
        
	}
	
	public void setSoundpool(String path){
		
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
		
		
	}
	
	public void playNote(int note)
	{
		if (!isNotePlaying(note))
		{
			PlayThread thread = new PlayThread(note);
			thread.start();
			threadMap.put(note, thread);
		}
	}
	
	
	public boolean isNotePlaying(int note)
	{
		return threadMap.containsKey(note);
	}
	
	public void stopNote(int note)
	{
		PlayThread thread = threadMap.get(note);
		if (thread != null)
		{
			thread.requestStop();
			threadMap.remove(note);
		}
	}
	
	
	
	
	private class PlayThread extends Thread
	{
		int note;
		boolean stop = false;
		
		public PlayThread(int note)
		{
			super();
			this.note = note;
		}

		public void run()
		{
			note = note - 35;
			AudioManager audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
			float curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
			float maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
			float leftVolume = curVolume/maxVolume;
			float rightVolume = curVolume/maxVolume;
			int priority = 1;
			int no_loop = 0;
			float normal_playback_rate = 1f;
			soundPool.play(note, leftVolume, rightVolume, priority, no_loop, normal_playback_rate);

		}
		
		public synchronized void requestStop()
		{
			stop = true;
		}
	}
}
