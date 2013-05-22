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
	protected int start_point = 0;
	protected boolean is_midi;
	protected int soundfile_raw_id;
	protected int soundpool_id;
	
	public SoundTrack() {}
	
	public SoundTrack(SoundTrack s)
	{
	  this.type = s.getType();
	  this.name = s.getName();
	  this.duration = s.duration;
	}
	
	public void setStartPoint(int start)
	{
		Log.e("START POINT", "" + start);
		start_point = start;
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
	
	public boolean ismidi() {
		return is_midi;
	}

	public int getSoundfileId() {
		return soundfile_raw_id;
	}
	
	public int getSoundPoolId()
	{
		return soundpool_id;
	}

	@Override
	public void update(Observable observable, Object data) {
		// TODO Auto-generated method stub
		int cur_time = (Integer)data;
		Log.e("Incoming Object: ", "" + cur_time);
		if(cur_time == start_point)
		{
			SoundManager.playSound(soundpool_id, 1);
		}
	}

	
}
