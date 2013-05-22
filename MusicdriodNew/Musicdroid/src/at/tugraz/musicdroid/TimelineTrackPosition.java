package at.tugraz.musicdroid;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

public class TimelineTrackPosition {
	private Timeline parent;
	private Context context;
	private int color_id;
	private View track_position = null;
	private TextView track_position_text = null;
	
	public TimelineTrackPosition(Timeline t, Context c, int col_id)
	{
		parent = t;
		context = c;
		color_id = col_id;
		addTrackPosition();
		addTrackPositionText();
	}
	
	public void updateTrackPosition(int pix_pos, int sec_pos)
	{
		track_position.setVisibility(View.VISIBLE);
		track_position_text.setVisibility(View.VISIBLE);
		
		RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) track_position.getLayoutParams();
		layoutParams.leftMargin = pix_pos;
		track_position.setLayoutParams(layoutParams);
		
		int minutes = sec_pos/60;
	    int seconds = sec_pos%60;
	    String min = "" + minutes;
	    String sec = "" + seconds;
	    
	    if(minutes < 10)
	    	min = "0" + min;
	    if(seconds < 10)
	    	sec = "0" + sec;
	    	
		
		track_position_text.setText(min + ":" + sec);
	}
	
	private void addTrackPosition()
	{
		track_position = new View(context);
		LayoutParams seperator_params = new RelativeLayout.LayoutParams(2, LayoutParams.WRAP_CONTENT);
		seperator_params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		track_position.setLayoutParams(seperator_params);
		track_position.setId(parent.getNewId());
		track_position.setBackgroundColor(context.getResources().getColor(color_id));
		track_position.setVisibility(View.INVISIBLE);
		parent.addView(track_position);
	}
	
	private void addTrackPositionText()
	{
		track_position_text = new TextView(context);
		
		track_position_text.setText("00:00");
		track_position_text.setTextColor(context.getResources().getColor(R.color.custom_background_color));
		LayoutParams text_params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		text_params.addRule(RelativeLayout.ALIGN_LEFT, track_position.getId());
		text_params.addRule(RelativeLayout.CENTER_VERTICAL);
		text_params.leftMargin = 5;
		track_position_text.setLayoutParams(text_params);
		track_position_text.setVisibility(View.INVISIBLE);
		parent.addView(track_position_text);
	}
	

	public View getTrackPosition() {
		return track_position;
	}

	public TextView getTrackPositionText() {
		return track_position_text;
	}
	
}
