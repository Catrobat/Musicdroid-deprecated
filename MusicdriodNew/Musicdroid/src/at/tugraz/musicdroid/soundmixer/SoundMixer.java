package at.tugraz.musicdroid.soundmixer;

import java.util.ArrayList;

import at.tugraz.musicdroid.MainActivity;
import at.tugraz.musicdroid.R;
import at.tugraz.musicdroid.SoundManager;
import at.tugraz.musicdroid.R.id;
import at.tugraz.musicdroid.R.layout;
import at.tugraz.musicdroid.helper.Helper;
import at.tugraz.musicdroid.soundmixer.timeline.Timeline;
import at.tugraz.musicdroid.soundmixer.timeline.TimelineMenuCallback;
import at.tugraz.musicdroid.soundtracks.SoundTrack;
import at.tugraz.musicdroid.soundtracks.SoundTrackView;
import at.tugraz.musicdroid.types.SoundType;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Choreographer.FrameCallback;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class SoundMixer implements HorizontalScrollViewListener{
	public static SoundMixer instance = null;
	public static final int DEFAULT_LENGTH = 45;
	protected ObservableHorizontalScrollView horScrollView;
	protected RelativeLayout parentLayout;
	protected MainActivity parent;
	protected ArrayList<SoundTrackView> tracks = new ArrayList<SoundTrackView>();
	protected int viewId;
	private int longestSoundTrack;
	private int soundMixerLength;
	private int callingId;
	private int pixelPerSecond;
	private SoundTrack callingTrack = null;
	private SoundMixerEventHandler eventHandler = null;
	private Timeline timeline = null;
	
	public static SoundMixer getInstance() {
        if (instance == null) {
            instance = new SoundMixer();
        }
        return instance;
    }
	
	public void initSoundMixer(MainActivity activity, ObservableHorizontalScrollView scrollView)
	{
		parent = activity;
		horScrollView = scrollView;
		parentLayout = (RelativeLayout) horScrollView.findViewById(R.id.sound_mixer_relative); 
		eventHandler = new SoundMixerEventHandler(this);
		timeline = new Timeline(parent, DEFAULT_LENGTH);
		

        activity.setCallbackTimelineMenu(new TimelineMenuCallback(activity, timeline));
        
		soundMixerLength = longestSoundTrack = DEFAULT_LENGTH;
		pixelPerSecond = Helper.getInstance().getScreenWidth()/DEFAULT_LENGTH;
		
		LayoutParams lp = (LayoutParams) timeline.getLayoutParams();
        LayoutInflater inflater = LayoutInflater.from(parent);
        inflater.inflate(R.layout.timeline_layout, timeline);
        timeline.setId(getNewViewID());
        parentLayout.addView(timeline, lp); 

        horScrollView.setScrollViewListener(this);
	}
	
	

	@Override
	public void onScrollChanged(ObservableHorizontalScrollView scrollView,
			int x, int y, int oldx, int oldy) {
		//if(Math.abs(oldx-x)>2)
		//{
		//	Log.i("X " + x, "OldX " + oldx);
		//    timeline.updateArrowOnScroll(x-oldx);
		//}
		
	}
	
	
	public SoundMixer() {
		viewId = 1234;
	}
	
	public void handleCopy()
	{
		SoundTrack copy = new SoundTrack(callingTrack);
		addSoundTrackViewToSoundMixer(new SoundTrackView(parent, copy));
	}
	
	
	public void addSoundTrackViewToSoundMixer(SoundTrackView track)
	{
		track.setId(getNewViewID());
		checkLongestTrack(track.getSoundTrack().getDuration());
		RelativeLayout.LayoutParams params = positionTrack(track);
        tracks.add(track);
        parentLayout.addView(track, params);    
        eventHandler.addObserver(track.getSoundTrack());
        timeline.addNewTrackPosition(track.getId(), track.getSoundTrack().getType().getColorResource());
	}
	
	public void playAllSoundsInSoundmixer()
	{
		if(tracks.size() > 0) 
		  eventHandler.play();
	}
	
	public void stopAllSoundsInSoundmixer()
	{
		eventHandler.stopNotifyThread();
		SoundManager.stopAllSounds(); 
	}
	
	public void updateTimelineOnMove(int id, int pix_pos, int sec_pos)
	{
		timeline.updateTimelineOnMove(id, pix_pos, sec_pos);
	}
	
	public void deleteCallingTrack()
	{
		deleteTrackById(callingId);
		timeline.removeTrackPosition(callingId);
	}
	
	public void deleteTrackById(int tId)
	{
		for(int i = 0; i < tracks.size(); i++)
		{
			if(tracks.get(i).getId() == tId)
			{
				parentLayout.removeView(tracks.get(i));
				reorderLayout(i);
				tracks.remove(i);
			}
		}
	}
	
	public void disableUnselectedViews()
	{
		for(int child = 0; child < parentLayout.getChildCount(); child++)
		{
			View view = parentLayout.getChildAt(child);
			if(view.getId() != timeline.getId() && view.getId() != callingId)
				((SoundTrackView)view).disableView();
		}
	}
	
	public void enableUnselectedViews()
	{
		for(int child = 0; child < parentLayout.getChildCount(); child++)
		{
			View view = parentLayout.getChildAt(child);
			if(view.getId() != timeline.getId() && view.getId() != callingId)
				((SoundTrackView)view).enableView();
		}
	}
	
	
	private void checkLongestTrack(int newTrackLength)
	{
		if(newTrackLength > soundMixerLength)
		{
			soundMixerLength = newTrackLength;
			resizeSoundMixer(newTrackLength);
			timeline.resizeTimeline(newTrackLength);
			timeline.updateTrackEndText(newTrackLength);
		}
		
		/*if(newTrackLength < DEFAULT_LENGTH)
		{
			longestSoundTrack = newTrackLength;
			eventHandler.setLongestTrack(longestSoundTrack);
			for(int i = 0; i < tracks.size(); i++)
			{
				tracks.get(i).resize();
			}
		} */
	}
	
	private void resizeSoundMixer(int length)
	{
		ViewGroup.LayoutParams layoutParams = (ViewGroup.LayoutParams) parentLayout.getLayoutParams();
		layoutParams.width = SoundMixer.getInstance().getPixelPerSecond() * length;
		parentLayout.setLayoutParams(layoutParams);
	}
	
	
	private void reorderLayout(int position)
	{
		if(position != 0 && position != tracks.size()-1)
		{
			SoundTrackView predecessor = tracks.get(position-1);
			SoundTrackView successor = tracks.get(position+1);
			
			RelativeLayout.LayoutParams params = (LayoutParams) successor.getLayoutParams();
			params.addRule(RelativeLayout.BELOW, predecessor.getId());
			successor.setLayoutParams(params);
		}
		if(position == 0 && tracks.size() > 1)
		{
			SoundTrackView successor = tracks.get(position+1);
			RelativeLayout.LayoutParams params = (LayoutParams) successor.getLayoutParams();
			params.addRule(RelativeLayout.BELOW, timeline.getId());
			successor.setLayoutParams(params);
		}
	}
	
	private RelativeLayout.LayoutParams positionTrack(SoundTrackView track)
	{
		if(tracks.size() > 0)
		{
			SoundTrackView lowermost_track = tracks.get(tracks.size() - 1);
			RelativeLayout.LayoutParams layoutParams = (LayoutParams) track.getLayoutParams();
			layoutParams.addRule(RelativeLayout.BELOW, lowermost_track.getId());
			return layoutParams;
		}
		else 
		{
			RelativeLayout.LayoutParams layoutParams = (LayoutParams) track.getLayoutParams();
			layoutParams.addRule(RelativeLayout.BELOW, timeline.getId());
			return layoutParams;
		}
	}
	
	
	public void resetSoundMixer()
	{
		for(int i = 0; i < tracks.size(); i++)
		{
			tracks.get(i).removeAllViews();
			parentLayout.removeView(tracks.get(i));
		}
		longestSoundTrack = 0;
		
		timeline.resetTimeline();
		
		longestSoundTrack = soundMixerLength = DEFAULT_LENGTH;
		tracks.clear();
	}
	
	public void setSoundTrackLengthAndResizeTracks(int minutes, int seconds)
	{
		int newLength = minutes*60 + seconds;
		if(newLength > soundMixerLength)
		{
			soundMixerLength = newLength;
			resizeSoundMixer(newLength);
			timeline.resizeTimeline(newLength);
			timeline.updateTrackEndText(newLength);
		}
		else if(newLength < soundMixerLength && newLength >= DEFAULT_LENGTH)
		{
			soundMixerLength = newLength;
			resizeSoundMixer(newLength);
			timeline.resizeTimeline(newLength);
			timeline.updateTrackEndText(newLength);
			//TODO ms check if new size is too small for current tracks!
			
//		    if(newLength < DEFAULT_LENGTH)
//		    {
//		    	for(int i = 0; i < tracks.size(); i++)
//		    	{
//		    		tracks.get(i).resize(); 
//		    	}
//		    }	
		}
		//soundTrackLength = minutes*60 + seconds;
		//longestSoundTrack = soundTrackLength;
		//eventHandler.setLongestTrack(soundTrackLength);
		
		//if(soundTrackLength > DEFAULT_LENGTH)
		//	timeline.resizeTimeline(soundTrackLength);
		
		//timeline.updateTrackEndText(soundTrackLength);
		//pixelPerSecond = Helper.getInstance().getScreenWidth()/soundTrackLength;
		
		//for(int i = 0; i < tracks.size(); i++)
		//{
		//	tracks.get(i).resize(); 
		//}
	}
	

	public void setCallingParameters(int id, SoundTrack track)
	{
		callingId = id;
		callingTrack = track;
	}
	
	public int getStartPointByPixel(int pixel)
	{
		return eventHandler.computeStartPointInSecondsByPixel(pixel);
	}
		
	public int getNewViewID()
	{
		viewId = viewId + 1;
		return viewId;
	}
	
	public ArrayList<SoundTrackView> getTracks() {
		return tracks;
	}
	
	public int getNumberOfTracks() {
		return tracks.size();
	}
	
	public SoundTrack getCallingTrack()
	{
		return callingTrack;
	}
	
	public int getDurationLongestTrack()
	{
		return longestSoundTrack;
	}
	
	public int getSoundMixerLength()
	{
		return soundMixerLength;
	}
	
	public int getPixelPerSecond()
	{
		if(pixelPerSecond == 0)
			pixelPerSecond = Helper.getInstance().getScreenWidth()/DEFAULT_LENGTH;
		return pixelPerSecond;
	}

}
