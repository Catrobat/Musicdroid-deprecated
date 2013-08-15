package org.catrobat.musicdroid.drums;

import java.util.Observable;

import android.util.Log;
import org.catrobat.musicdroid.preferences.PreferenceManager;

public class DrumLoopEventHandler extends Observable {
	private boolean shouldContinue;
	private int num_loops = 2000;
	
	public DrumLoopEventHandler()
	{
	}
	
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
		        	
//		        	long now = System.currentTimeMillis();
//		        	long expectedElapsedTime = now + sleepDuration;
//		        	while(now < expectedElapsedTime){
//		        	    now = System.currentTimeMillis();
//		        	} 
		        	
		            while (shouldContinue && (num_loops > 0 && loops < num_loops)) {
		                try {
		        			setChanged();
		        			notifyObservers(beat);
		        			beat = beat + 1;
		        			if(beat == 16){  
		        				beat = 0;
		        				loops = loops + 1;
		        			}
		                    //Thread.sleep(sleepDuration);
		        			long now = System.currentTimeMillis();
				        	long expectedElapsedTime = now + sleepDuration;
				        	while(now < expectedElapsedTime){
				        	    now = System.currentTimeMillis();
				        	}
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
		return 60000/(bpm*4); //return millisec
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
