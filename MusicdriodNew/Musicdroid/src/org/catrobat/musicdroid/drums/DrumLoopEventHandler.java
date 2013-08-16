package org.catrobat.musicdroid.drums;

import java.util.Observable;

import android.util.Log;
import org.catrobat.musicdroid.preferences.PreferenceManager;

public class DrumLoopEventHandler extends Observable {
	private boolean shouldContinue;
	private int num_loops = 2000;
	private static final int ONE_MINUTE_IN_MILISECONDS = 60000;
	
	public void play()
	{
		if(countObservers() > 0)
		{
			new Thread(new Runnable() {
		        @Override
		        public void run() {
		        	shouldContinue = true;
		        	int beat = 0;
		        	int loops = 0; 
		        	int sleepDuration = computeSleep();
		        	Log.i("DrumLoopEventHandler", "Sleep = " + sleepDuration);
		        	
		            while (shouldContinue && (num_loops > 0 && loops < num_loops)) {
		                try {
		        			setChanged();
		        			notifyObservers(beat);
		        			beat = beat + 1;
		        			if(beat == 16){  
		        				beat = 0;
		        				loops = loops + 1;
		        			}
		                    Thread.sleep(sleepDuration);
		                } catch (Exception e) {
		                }
		            }
		            return;
		        }
		    }).start();
		}
	}
	
	private int computeSleep()
	{
		int bpm = PreferenceManager.getInstance().getPreference(PreferenceManager.METRONOM_BPM_KEY);
		return ONE_MINUTE_IN_MILISECONDS/(bpm*4);
	}
	
	public void stop()
	{
		shouldContinue = false;
	}

	public void setLoops(int loops)
	{
		num_loops = loops;
	}

	public int getLoops() {
		return num_loops;
	}
	
}
