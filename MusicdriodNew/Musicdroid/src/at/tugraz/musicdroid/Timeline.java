package at.tugraz.musicdroid;

import java.util.HashMap;

import at.tugraz.musicdroid.helper.Helper;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

public class Timeline extends RelativeLayout {
	private Helper helper = null;
	private Context context = null;
	private View seperator = null;
	private TextView startTimeTextView = null;
	private TextView endTimeTextView = null;
	private int startId = 9876;
	private HashMap<Integer, TimelineTrackPosition> trackPositions = null;

	public Timeline(Context context) {
		super(context);
		this.context = context;
		helper = Helper.getInstance();
		trackPositions = new HashMap<Integer, TimelineTrackPosition>();
		initTimeline();
	}
	
	public Timeline(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	
	private void initTimeline()
	{
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(helper.getScreenWidth(), 
				   helper.getScreenHeight()/18);
		setLayoutParams(layoutParams);
		setBackgroundColor(getResources().getColor(R.color.abs__background_holo_light));
		
		addSeperator();
		addStartTime();
		addEndTime();
	}
	
	
	public void addNewTrackPosition(int id, int colorRes)
	{
		trackPositions.put(id, new TimelineTrackPosition(this, context, colorRes));
	}
	
	public void updateTrackEndText(int duration)
	{		
		if(endTimeTextView == null) return;
		
		int minutes = duration/60;
	    int seconds = duration%60;
	    String min = "" + minutes;
	    String sec = "" + seconds;
	    
	    if(minutes < 10)
	    	min = "0" + min;
	    if(seconds < 10)
	    	sec = "0" + sec;
	    	
		endTimeTextView.setText(min + ":" + sec);
	}	
	
	public void updateTimelineOnMove(int id, int pixPos, int secPos)
	{
		trackPositions.get(id).updateTrackPosition(pixPos, secPos);
	}

	public void removeTrackPosition(int id)
	{
		TimelineTrackPosition tp = trackPositions.get(id);
		this.removeView(tp.getTrackPosition());
		this.removeView(tp.getTrackPositionText());
		trackPositions.remove(id);
	}
	
	private void addStartTime()
	{
		startTimeTextView = new TextView(context);
	
		startTimeTextView.setText("00:00");
		startTimeTextView.setTextColor(context.getResources().getColor(R.color.custom_background_color));
		LayoutParams textParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		textParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		textParams.addRule(RelativeLayout.CENTER_VERTICAL);
		startTimeTextView.setLayoutParams(textParams);
		
		addView(startTimeTextView);
	}
	
	private void addEndTime()
	{
		endTimeTextView = new TextView(context);
		
		endTimeTextView.setText("");
		endTimeTextView.setTextColor(context.getResources().getColor(R.color.custom_background_color));
		LayoutParams textParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		textParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		textParams.addRule(RelativeLayout.CENTER_VERTICAL);
		endTimeTextView.setLayoutParams(textParams);
		
		addView(endTimeTextView);
	}
	
	private void addSeperator()
	{		
		seperator = new View(context);
		LayoutParams seperatorParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,2);
		seperatorParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		seperatorParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		seperator.setLayoutParams(seperatorParams);
		//int reference_id = getNewId();
		//seperator.setId(reference_id);
		seperator.setBackgroundColor(getResources().getColor(R.color.custom_background_color));
		
		addView(seperator);
	}
	
	public int getNewId()
	{
	  int id = startId;
	  startId = startId + 1;
	  return id;
	}
	
}
