package at.tugraz.musicdroid.soundmixer.timeline;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import at.tugraz.musicdroid.MainActivity;
import at.tugraz.musicdroid.R;
import at.tugraz.musicdroid.helper.Helper;
import at.tugraz.musicdroid.soundmixer.SoundMixer;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Timeline extends RelativeLayout {
	private Helper helper = null;
	private Context context = null;
	private View seperator = null;
	private TextView startTimeTextView = null;
	private TextView endTimeTextView = null;
	private ImageView arrowView = null;
	
	private int startId = 9876;
	private int height = 0;
	private HashMap<Integer, TimelineTrackPosition> trackPositions = null;

	public Timeline(Context context, int defaultLength) {
		super(context);
		this.context = context;
		helper = Helper.getInstance();
		trackPositions = new HashMap<Integer, TimelineTrackPosition>();
		initTimeline(defaultLength);
		this.setOnTouchListener(new TimelineOnTouchListener(this));
	}
	
	public Timeline(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	
	private void initTimeline(int defaultLength)
	{
		height = helper.getScreenHeight()/18;
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(helper.getScreenWidth(), height);
		setLayoutParams(layoutParams);
		setBackgroundColor(getResources().getColor(R.color.abs__background_holo_light));
		
		addSeperator();
		addStartTime();
		addEndTime();
		addPositionMarker(defaultLength);
		updateTrackEndText(defaultLength);
	}
	
	public void resizeTimeline(int newLength)
	{
		int oldLength = getWidth()/SoundMixer.getInstance().getPixelPerSecond();
		int defaultLength = SoundMixer.getInstance().DEFAULT_LENGTH;
		
		RelativeLayout.LayoutParams layoutParams = (LayoutParams) getLayoutParams();
		layoutParams.width = SoundMixer.getInstance().getPixelPerSecond() * newLength;
		
		Log.i("NewLenght " + newLength, "OldLength " + oldLength);
		
		if(newLength > oldLength)
		{
			for(int second = oldLength-5; second < newLength; second++)
			{
				addView(newPositionMarker(second));
			} 
		}
		
		if(newLength > defaultLength && arrowView == null)
		{
			addArrow();
		}			
	}
	
	
	public void updateArrowOnScroll(int scrollX)
	{
		if(arrowView == null) return;
		
		RelativeLayout.LayoutParams layout = (LayoutParams) arrowView.getLayoutParams();
		int oldMargin = layout.leftMargin;
		layout.setMargins(oldMargin+scrollX, 0, 0, 0);
		arrowView.setLayoutParams(layout);
	}
	
	private void addArrow()
	{
		arrowView = new ImageView(context);
		arrowView.setImageDrawable(getResources().getDrawable(R.drawable.timeline_arrow));
		RelativeLayout.LayoutParams layout = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, getHeight());
		layout.addRule(ALIGN_PARENT_LEFT);
		layout.addRule(ALIGN_PARENT_TOP);
		layout.setMargins(Helper.getInstance().getScreenWidth()-50, 0, 0, 0);
		arrowView.setLayoutParams(layout);
		arrowView.setColorFilter(Color.BLACK);
		addView(arrowView);
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
	
	public void resetTimeline()
	{
		Iterator<Entry<Integer, TimelineTrackPosition>> it = trackPositions.entrySet().iterator();
		
        while (it.hasNext()) {
            HashMap.Entry<Integer, TimelineTrackPosition> pairs = (Entry<Integer, TimelineTrackPosition>)it.next();
            removeView(pairs.getValue().getTrackPositionText());
            removeView(pairs.getValue().getTrackPosition());
        }
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
		endTimeTextView.setVisibility(GONE);
		addView(endTimeTextView);
	}
	
	private void addSeperator()
	{		
		seperator = new View(context);
		LayoutParams seperatorParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,2);
		seperatorParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		seperatorParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		seperator.setLayoutParams(seperatorParams);
		seperator.setBackgroundColor(getResources().getColor(R.color.custom_background_color));
		
		addView(seperator);
	}
	
	private void addPositionMarker(int defaultLength)
	{	
		for(int second = 3; second <= defaultLength; second++)
		{
			addView(newPositionMarker(second));
		} 
	}
	
	private View newPositionMarker(int second)
	{
		int pixelPerSecond = SoundMixer.getInstance().getPixelPerSecond();
		
		View positionMarker = new View(context);
		LayoutParams markerParams = new RelativeLayout.LayoutParams(2, height*1/4);
		
		if(second%5 == 0)
			markerParams = new RelativeLayout.LayoutParams(2, height*3/8);
		
		markerParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		markerParams.addRule(RelativeLayout.ALIGN_LEFT);
		markerParams.leftMargin = pixelPerSecond*second;
		positionMarker.setLayoutParams(markerParams);
		positionMarker.setBackgroundColor(Color.BLACK);
		positionMarker.setId(getNewId());
		return positionMarker;
	}
	
	public void startTimelineActionMode()
	{
		((MainActivity)context).startTimelineActionMode();
	}
	
	public int getNewId()
	{
	  int id = startId;
	  startId = startId + 1;
	  return id;
	}
	
	
}
