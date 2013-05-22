package at.tugraz.musicdroid;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Observable;

import android.util.Log;

import at.tugraz.musicdroid.helper.Helper;
import at.tugraz.musicdroid.soundtracks.SoundTrack;

public class SoundMixerEventHandler extends Observable {
	private SoundMixer mixer;
	private int longest_track;
	private int screen_width;
	private int second_in_pixel;
	private boolean should_continue;
	
	public SoundMixerEventHandler(SoundMixer m)
	{
		mixer = m;
		longest_track = 0;
		screen_width = Helper.getInstance().getScreenWidth();
	}
	
	public void play()
	{
		if(countObservers() > 0)
		{
			new Thread(new Runnable() {
		        @Override
		        public void run() {
		        	int time = 0;
		        	should_continue = true;
		            while (should_continue && time <= longest_track) {
		                try {
		                    Thread.sleep(1000);
		        			setChanged();
		        			notifyObservers(time);
		        			time = time + 1;
		                } catch (Exception e) {
		                    // TODO: handle exception
		                }
		            }
		            Log.e("TIME: " + time, "Sec: " + second_in_pixel);
		            return;
		        }
		    }).start();
		}
	}
	
	public void stopNotifyThread()
	{
		should_continue = false;
	}
	
	public void setLongestTrack(int length)
	{
		
		longest_track = length;
		computeSecondInPixel();
	}
	
	public void computeSecondInPixel()
	{
		Log.e("Longest Track ", "" + longest_track);
		second_in_pixel = screen_width/longest_track;
	}

	public int computeStartPointInSecondsByPixel(int start_pos_pixel)
	{
		return start_pos_pixel/second_in_pixel;
	}
	
	
}
