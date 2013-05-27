package at.tugraz.musicdroid.soundmixer.timeline;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;

public class TimelineOnTouchListener implements OnTouchListener {
	public Timeline timeline;
	
	public TimelineOnTouchListener(Timeline t)
	{
		this.timeline = t;
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return gestureDetector.onTouchEvent(event);
	}
	
	@SuppressWarnings("deprecation")
	final GestureDetector gestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {
	    public void onLongPress(final MotionEvent e) {
	    	//Log.i("Location: ", "X = " + e.getX() + " | Y = " + e.getY());
	    	int[] location = {(int) e.getX(), (int) e.getY()};
	    	timeline.setClickLocation(location);
	    	timeline.startTimelineActionMode();
	    }
	});

}
