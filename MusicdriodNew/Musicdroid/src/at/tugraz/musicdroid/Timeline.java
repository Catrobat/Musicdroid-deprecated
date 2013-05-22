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
	private TextView start_time = null;
	private TextView end_time = null;
	private int start_id = 9876;
	private HashMap<Integer, TimelineTrackPosition> track_positions = null;

	public Timeline(Context context) {
		super(context);
		this.context = context;
		helper = Helper.getInstance();
		track_positions = new HashMap<Integer, TimelineTrackPosition>();
		init();
	}
	
	public Timeline(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	
	private void init()
	{
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(helper.getScreenWidth(), 
				   helper.getScreenHeight()/18);
		setLayoutParams(layoutParams);
		setBackgroundColor(getResources().getColor(R.color.abs__background_holo_light));
		
		addSeperator();
		addStartTime();
		addEndTime();
	}
	
	
	public void addNewTrackPosition(int id, int color_res)
	{
		track_positions.put(id, new TimelineTrackPosition(this, context, color_res));
	}
	
	public void updateTrackEndText(int duration)
	{		
		if(end_time == null) return;
		
		int minutes = duration/60;
	    int seconds = duration%60;
	    String min = "" + minutes;
	    String sec = "" + seconds;
	    
	    if(minutes < 10)
	    	min = "0" + min;
	    if(seconds < 10)
	    	sec = "0" + sec;
	    	
		end_time.setText(min + ":" + sec);
	}	
	
	public void updateTimelineOnMove(int id, int pix_pos, int sec_pos)
	{
		track_positions.get(id).updateTrackPosition(pix_pos, sec_pos);
	}

	public void removeTrackPosition(int id)
	{
		TimelineTrackPosition tp = track_positions.get(id);
		this.removeView(tp.getTrackPosition());
		this.removeView(tp.getTrackPositionText());
		track_positions.remove(id);
	}
	
	private void addStartTime()
	{
		start_time = new TextView(context);
	
		start_time.setText("00:00");
		start_time.setTextColor(context.getResources().getColor(R.color.custom_background_color));
		LayoutParams text_params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		text_params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		text_params.addRule(RelativeLayout.CENTER_VERTICAL);
		start_time.setLayoutParams(text_params);
		
		addView(start_time);
	}
	
	private void addEndTime()
	{
		end_time = new TextView(context);
		
		end_time.setText("");
		end_time.setTextColor(context.getResources().getColor(R.color.custom_background_color));
		LayoutParams text_params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		text_params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		text_params.addRule(RelativeLayout.CENTER_VERTICAL);
		end_time.setLayoutParams(text_params);
		
		addView(end_time);
	}
	
	private void addSeperator()
	{		
		seperator = new View(context);
		LayoutParams seperator_params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,2);
		seperator_params.addRule(RelativeLayout.CENTER_HORIZONTAL);
		seperator_params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		seperator.setLayoutParams(seperator_params);
		//int reference_id = getNewId();
		//seperator.setId(reference_id);
		seperator.setBackgroundColor(getResources().getColor(R.color.custom_background_color));
		
		addView(seperator);
	}
	
	public int getNewId()
	{
	  int id = start_id;
	  start_id = start_id + 1;
	  return id;
	}
	
}
