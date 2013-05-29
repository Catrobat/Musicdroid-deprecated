package at.tugraz.musicdroid.soundmixer;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Observable;

import android.util.Log;
import android.view.View;

import at.tugraz.musicdroid.SoundManager;
import at.tugraz.musicdroid.helper.Helper;
import at.tugraz.musicdroid.soundtracks.SoundTrack;

public class SoundMixerEventHandler extends Observable {
	private SoundMixer mixer;
	private int longestTrack = 0;
	private int endPoint = 0;
	private int startPoint = 0;
	private int stopPoint = 0;
	private int time = 0;
	private int screenWidth;
	private int secondInPixel;
	private boolean shouldContinue;
	
	public SoundMixerEventHandler(SoundMixer m)
	{
		mixer = m;
		setEndPoint(mixer.DEFAULT_LENGTH);
		screenWidth = Helper.getInstance().getScreenWidth();
		secondInPixel = screenWidth/mixer.DEFAULT_LENGTH;
	}
	
	public void play()
	{
		if(countObservers() > 0)
		{
			new Thread(new Runnable() {
		        @Override
		        public void run() {
		        	time = setStartTime();
		        	shouldContinue = true;
		            while (shouldContinue && time <= endPoint) {
		                try {
		                    Thread.sleep(1000);
		        			setChanged();
		        			notifyObservers(time);
		        			time = time + 1;
		                } catch (Exception e) {
		                    // TODO: handle exception
		                }
		            }
		            Log.i("TIME: " + time, "EndPoint: " + endPoint);
		            SoundManager.stopAllSounds();
		            return;
		        }
		    }).start();
		}
	}
	
	public void stopNotifyThread()
	{
		stopPoint = time;
		shouldContinue = false;
	}
	
	public void rewind()
	{
		stopPoint = startPoint;
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
	
	public int getEndPoint() {
		return endPoint;
	}

	public boolean setEndPoint(int endPoint) {
		Log.i("Set EndPoint", "EndPoint = " + endPoint);
		
		if(endPoint < startPoint) return false;
		
		this.endPoint = endPoint;
		return true;
	}

	public int getStartPoint() {
		return startPoint;
	}

	public boolean setStartPoint(int startPoint) {
		if(startPoint > endPoint) return false;
		
		this.startPoint = startPoint;
		return true;
	}

	private int setStartTime()
	{
		if(stopPoint > startPoint)
			return stopPoint; 
		else
			return startPoint;
	}
	
}
