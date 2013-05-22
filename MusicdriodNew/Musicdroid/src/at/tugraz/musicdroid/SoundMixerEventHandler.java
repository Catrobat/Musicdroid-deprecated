package at.tugraz.musicdroid;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Observable;

import android.util.Log;

import at.tugraz.musicdroid.helper.Helper;
import at.tugraz.musicdroid.soundtracks.SoundTrack;

public class SoundMixerEventHandler extends Observable {
	private SoundMixer mixer;
	private int longestTrack;
	private int screenWidth;
	private int secondInPixel;
	private boolean shouldContinue;
	
	public SoundMixerEventHandler(SoundMixer m)
	{
		mixer = m;
		longestTrack = 0;
		screenWidth = Helper.getInstance().getScreenWidth();
	}
	
	public void play()
	{
		if(countObservers() > 0)
		{
			new Thread(new Runnable() {
		        @Override
		        public void run() {
		        	int time = 0;
		        	shouldContinue = true;
		            while (shouldContinue && time <= longestTrack) {
		                try {
		                    Thread.sleep(1000);
		        			setChanged();
		        			notifyObservers(time);
		        			time = time + 1;
		                } catch (Exception e) {
		                    // TODO: handle exception
		                }
		            }
		            Log.e("TIME: " + time, "Sec: " + secondInPixel);
		            return;
		        }
		    }).start();
		}
	}
	
	public void stopNotifyThread()
	{
		shouldContinue = false;
	}
	
	public void setLongestTrack(int length)
	{
		
		longestTrack = length;
		computeSecondInPixel();
	}
	
	public void computeSecondInPixel()
	{
		Log.e("Longest Track ", "" + longestTrack);
		secondInPixel = screenWidth/longestTrack;
	}

	
	public int computeStartPointInSecondsByPixel(int start_pos_pixel)
	{
		return start_pos_pixel/secondInPixel;
	}
	
	
}
