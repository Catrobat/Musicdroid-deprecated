package at.tugraz.musicdroid.soundtracks;

import java.util.Observable;
import java.util.Observer;

import android.media.MediaPlayer;
import android.util.Log;

import at.tugraz.musicdroid.SoundManager;
import at.tugraz.musicdroid.types.SoundType;

public class SoundTrack implements Observer {
	protected SoundType type = null;
	protected String name = null;
	protected int duration = 0; //in seconds
	protected int startPoint = 0;
	protected boolean isMidi;
	protected int soundfileRawId;
	protected int soundpoolId;
	protected float volume; 
	
	public SoundTrack() {
		volume = 1;
	}
	
	public SoundTrack(SoundTrack s)
	{
	  this.type = s.getType();
	  this.name = s.getName();
	  this.duration = s.duration;
	  this.volume = s.volume;
	}
	
	public void setStartPoint(int start)
	{
		Log.e("START POINT", "" + start);
		startPoint = start;
	}
	
	public void setVolume(float vol)
	{
		Log.e("SET VOLUME", "" + vol);
		volume = vol;
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
	
	public int getSoundPoolId()
	{
		return soundpoolId;
	}
	
	public float getVolume()
	{
		return volume;
	}

	@Override
	public void update(Observable observable, Object data) {
		// TODO Auto-generated method stub
		int cur_time = (Integer)data;
		Log.e("Incoming Object: ", "" + cur_time);
		if(cur_time == startPoint)
		{
			SoundManager.playSound(soundpoolId, 1, volume);
		}
	}

	
}
