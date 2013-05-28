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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

public class Timeline extends RelativeLayout {
	private Helper helper = null;
	private Context context = null;
	private View seperator = null;
	private TextView startTimeTextView = null;
	private TextView endTimeTextView = null;
	private ImageView arrowView = null;
	private ImageButton startPoint = null;
	private ImageButton endPoint = null;
	
	private int startId = 9876;
	private int height = 0;
	private int[] clickLocation;
	private HashMap<Integer, TimelineTrackPosition> trackPositions = null;
	private TimelineOnTouchListener onTouchListener = null;

	public Timeline(Context context, int defaultLength) {
		super(context);
		this.context = context;
		helper = Helper.getInstance();
		trackPositions = new HashMap<Integer, TimelineTrackPosition>();
		//eventMarkers = new TimelineEventMarkers(context, this, onTouchListener);
		
        LayoutInflater inflater = LayoutInflater.from(this.context);
        inflater.inflate(R.layout.timeline_layout, this);
		
		initTimeline(defaultLength);
		onTouchListener = new TimelineOnTouchListener(this);
		this.setOnTouchListener(onTouchListener);
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
		
		seperator = (View) findViewById(R.id.timeline_seperator);
		startTimeTextView = (TextView) findViewById(R.id.timeline_start_time);
		endTimeTextView = (TextView) findViewById(R.id.timeline_end_time);
		startPoint = (ImageButton) findViewById(R.id.timeline_start_point);
		endPoint = (ImageButton) findViewById(R.id.timeline_end_point);
		
		startTimeTextView.setText("00:00");
		
		addPositionMarker(defaultLength);
		updateTrackEndText(defaultLength);
	}
	
	public void resizeTimeline(int newLength)
	{
		int oldLength = getWidth()/SoundMixer.getInstance().getPixelPerSecond();
		int defaultLength = SoundMixer.getInstance().DEFAULT_LENGTH;
		
		RelativeLayout.LayoutParams layoutParams = (LayoutParams) getLayoutParams();
		layoutParams.width = SoundMixer.getInstance().getPixelPerSecond() * newLength;
		
		updateTrackEndText(newLength);
		
		if(newLength > oldLength)
		{
			for(int second = oldLength-5; second < newLength; second++)
			{
				addView(newPositionMarker(second));
			} 
		}
		
		if(newLength > defaultLength && arrowView == null)
		{
			//addArrow(); //TODO ms
		}			
	}	

	
	public void addNewTrackPosition(int id, int colorRes)
	{
		trackPositions.put(id, new TimelineTrackPosition(this, context, colorRes));
	}
	
	public void updateTrackEndText(int duration)
	{		
		if(endTimeTextView == null) return;

		Log.i("Timeline", "Set EndText: " + Helper.getInstance().durationStringFromInt(duration));
		endTimeTextView.setText(Helper.getInstance().durationStringFromInt(duration));
	}	
	
	public void updateTimelineOnMove(int id, int pixPos, int secPos, int duration)
	{
		int pixelPerSecond = SoundMixer.getInstance().getPixelPerSecond();
		trackPositions.get(id).updateTrackPosition(pixPos, secPos);
		if((secPos+duration)*pixelPerSecond > getWidth())
		{
			resizeTimeline(secPos+duration);
		}
	}

	public void removeTrackPosition(int id)
	{
		TimelineTrackPosition tp = trackPositions.get(id);
		this.removeView(tp.getTrackPosition());
		this.removeView(tp.getTrackPositionText());
		trackPositions.remove(id);
	}
	
	public boolean setStartPoint(int x)
	{	
		/*
		if(!checkValidPosition(x, true))
		{
			Toast.makeText(context, R.string.warning_invalid_marker_position, Toast.LENGTH_LONG ).show();
			return false;
		} */
		int pixelPerSecond = SoundMixer.getInstance().getPixelPerSecond();
		RelativeLayout.LayoutParams layout = (LayoutParams) startPoint.getLayoutParams();
		layout.height = getHeight();
		layout.width = pixelPerSecond;
		layout.setMargins(x-(x % pixelPerSecond)-pixelPerSecond+1, 0, 0, 0);
		startPoint.setColorFilter(Color.BLACK);
		startPoint.setVisibility(VISIBLE);
		startPoint.setLayoutParams(layout);
		startPoint.setOnTouchListener(onTouchListener);
		return true;
	}

	
	public boolean setEndPoint(int x)
	{
		/*if(!checkValidPosition(x, false))
		{
			Toast.makeText(context, R.string.warning_invalid_marker_position, Toast.LENGTH_SHORT ).show();
			return false;
		} */
		int pixelPerSecond = SoundMixer.getInstance().getPixelPerSecond();
		RelativeLayout.LayoutParams layout = (LayoutParams) endPoint.getLayoutParams();
		layout.height = getHeight();
		layout.width = pixelPerSecond;
		layout.setMargins(x-(x % pixelPerSecond)-pixelPerSecond+1, 0, 0, 0);
		endPoint.setColorFilter(Color.BLACK);
		endPoint.setVisibility(VISIBLE);
		endPoint.setLayoutParams(layout);
		endPoint.setOnTouchListener(onTouchListener);
		return true;
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

	
	private boolean checkValidPosition(int pos, boolean isStartPoint)
	{
		int[] location = {0,0};
		if(isStartPoint)
		{
			endPoint.getLocationOnScreen(location);
			Log.i("CheckInvalidPosition", "Pos: " + pos + " Loc: " + location[0]);
			if(location[0] > 0 && pos >= location[0]) return false;
		}
		else
		{
			startPoint.getLocationOnScreen(location);
			Log.i("CheckInvalidPosition", "Pos: " + pos + " Loc: " + location[0] + " Width: " + startPoint.getLayoutParams().width);
			if(pos <= location[0]+startPoint.getLayoutParams().width) return false;
		}
		return true;
		
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
	
	public void setClickLocation(int[] l)
	{
		clickLocation = l;
	}
	
	public int[] getClickLocation()
	{
		return clickLocation;
	}
	
	public int getNewId()
	{
	  int id = startId;
	  startId = startId + 1;
	  return id;
	}
	
	/*
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
	 */	
}
