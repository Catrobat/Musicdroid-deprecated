package at.tugraz.musicdroid.soundmixer.timeline;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;

public class TimelineOnTouchListener implements OnTouchListener {
	private Timeline timeline;
	private ImageButton startPoint = null;
	private ImageButton endPoint = null;
	
	
	public TimelineOnTouchListener(Timeline t)
	{
		this.timeline = t;
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		if(startPoint != null)
		{
			int[] startPointLocation = {0,0};
			startPoint.getLocationOnScreen(startPointLocation);
			if(v.getX() > startPointLocation[0]-10 && v.getX() < startPointLocation[0]+10)
			{
				Log.i("TOUCH: ", "StartPoint");
				return true;
			}
			
		}		
		if(endPoint != null)
		{
			int[] endPointLocation = {0,0};
			endPoint.getLocationOnScreen(endPointLocation);
			if(v.getX() > endPointLocation[0]-10 && v.getX() < endPointLocation[0]+10)
			{
				Log.i("TOUCH: ", "EndPoint");
				return true;
			}
		}
		
		Log.i("TOUCH: ", "Timeline");
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
	

	public void setStartPoint(ImageButton startPoint) {
		this.startPoint = startPoint;
	}

	public void setEndPoint(ImageButton endPoint) {
		this.endPoint = endPoint;
	}


}
