package at.tugraz.musicdroid.soundmixer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import at.tugraz.musicdroid.R;
import at.tugraz.musicdroid.R.color;
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

	public Timeline(Context context, int defaultLength) {
		super(context);
		this.context = context;
		helper = Helper.getInstance();
		trackPositions = new HashMap<Integer, TimelineTrackPosition>();
		initTimeline(defaultLength);
	}
	
	public Timeline(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	
	private void initTimeline(int defaultLength)
	{
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(helper.getScreenWidth(), 
				   helper.getScreenHeight()/18);
		setLayoutParams(layoutParams);
		setBackgroundColor(getResources().getColor(R.color.abs__background_holo_light));
		
		addSeperator();
		addStartTime();
		addEndTime();
		updateTrackEndText(defaultLength);
	}
	
	
	public void resetTimeline()
	{
		Iterator<Entry<Integer, TimelineTrackPosition>> it = trackPositions.entrySet().iterator();
		
        while (it.hasNext()) {
            HashMap.Entry<Integer, TimelineTrackPosition> pairs = (Entry<Integer, TimelineTrackPosition>)it.next();
            removeView(pairs.getValue().getTrackPositionText());
            removeView(pairs.getValue().getTrackPosition());
        }
	}
	
	public void addNewTrackPosition(int id, int colorRes)
	{
		trackPositions.put(id, new TimelineTrackPosition(this, context, colorRes));
	}
	
	public void updateTrackEndText(int duration)
	{		
		if(endTimeTextView == null) return;

		endTimeTextView.setText(Helper.getInstance().durationStringFromInt(duration));
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
