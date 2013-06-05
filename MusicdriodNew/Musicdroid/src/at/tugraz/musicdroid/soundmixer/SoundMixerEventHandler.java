package at.tugraz.musicdroid.soundmixer;

import java.util.Observable;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import at.tugraz.musicdroid.SoundManager;
import at.tugraz.musicdroid.helper.Helper;
import at.tugraz.musicdroid.preferences.PreferenceManager;
import at.tugraz.musicdroid.soundmixer.timeline.TimelineEventHandler;

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
		setEndPoint(PreferenceManager.getInstance().getPreference(PreferenceManager.SOUNDTRACK_DEFAULT_LENGTH_KEY));
		screenWidth = Helper.getInstance().getScreenWidth();
		secondInPixel = screenWidth/PreferenceManager.getInstance().getPreference(PreferenceManager.SOUNDTRACK_DEFAULT_LENGTH_KEY);
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
		        			if(shouldContinue && time < endPoint) sendTrackPositionMessage(time - setStartTime() +1);
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
	
	private void sendTrackPositionMessage(int time)
	{
		Log.i("Set position message", "");
		Message msg = new Message();
        Bundle b = new Bundle();
        b.putInt("position", time);
        msg.setData(b);
	    TimelineEventHandler.getInstance().sendMessage(msg);
	}
	
}
