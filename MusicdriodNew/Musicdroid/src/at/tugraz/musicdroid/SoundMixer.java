package at.tugraz.musicdroid;

import java.util.ArrayList;

import at.tugraz.musicdroid.helper.Helper;
import at.tugraz.musicdroid.soundtracks.SoundTrack;
import at.tugraz.musicdroid.soundtracks.SoundTrackView;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class SoundMixer{
	public static SoundMixer instance = null;
	public static final int DEFAULT_LENGTH = 45;
	protected RelativeLayout parent_layout;
	protected MainActivity parent;
	protected ArrayList<SoundTrackView> tracks = new ArrayList<SoundTrackView>();
	protected int view_id;
	private int longest_sound_track;
	private int calling_id;
	private SoundTrack calling_track = null;
	private SoundMixerEventHandler event_handler = null;
	private Timeline timeline = null;
	
	public static SoundMixer getInstance() {
        if (instance == null) {
            instance = new SoundMixer();
        }
        return instance;
    }
	
	public void init(MainActivity activity, RelativeLayout layout)
	{
		parent = activity;
		parent_layout = layout;
		event_handler = new SoundMixerEventHandler(this);
		longest_sound_track = DEFAULT_LENGTH;
		event_handler.setLongestTrack(longest_sound_track);
		timeline = new Timeline(parent);

        LayoutParams lp = (LayoutParams) timeline.getLayoutParams();
        LayoutInflater inflater = LayoutInflater.from(parent);
        inflater.inflate(R.layout.timeline_layout, timeline);
        timeline.setId(getNewViewID());
        parent_layout.addView(timeline, lp); 
	}
	
	public SoundMixer() {
		view_id = 1234;
	}
	
	public void handleCopy()
	{
		SoundTrack copy = new SoundTrack(calling_track);
		addTrack(new SoundTrackView(parent, copy));
	}
	
	
	public void addTrack(SoundTrackView track)
	{
		track.setId(getNewViewID());
		checkLongestTrack(track.getSoundTrack().getDuration());
		RelativeLayout.LayoutParams params = positionTrack(track);
        LayoutInflater inflater = LayoutInflater.from(parent);
        inflater.inflate(R.layout.sound_track_layout, track);
        tracks.add(track);
        parent_layout.addView(track, params);    
        event_handler.addObserver(track.getSoundTrack());
        timeline.addNewTrackPosition(track.getId(), track.getSoundTrack().getType().getColorResource());
	}
	
	public void playSounds()
	{
		if(tracks.size() > 0)
		  event_handler.play();
	}
	
	public void stopSounds()
	{
		event_handler.stopNotifyThread();
		SoundManager.stopAllSounds(); 
	}
	
	public void updateTimelineOnMove(int id, int pix_pos, int sec_pos)
	{
		timeline.updateTimelineOnMove(id, pix_pos, sec_pos);
	}
	
	public void deleteCallingTrack()
	{
		deleteTrack(calling_id);
		timeline.removeTrackPosition(calling_id);
	}
	
	public void deleteTrack(int track_id)
	{
		for(int i = 0; i < tracks.size(); i++)
		{
			if(tracks.get(i).getId() == track_id)
			{
				parent_layout.removeView(tracks.get(i));
				reorderLayout(i);
				tracks.remove(i);
			}
		}
	}
	
	public void disableUnselectedViews()
	{
		for(int child = 0; child < parent_layout.getChildCount(); child++)
		{
			View view = parent_layout.getChildAt(child);
			if(view.getId() != timeline.getId() && view.getId() != calling_id)
				((SoundTrackView)view).disableView();
		}
	}
	
	public void enableUnselectedViews()
	{
		for(int child = 0; child < parent_layout.getChildCount(); child++)
		{
			View view = parent_layout.getChildAt(child);
			if(view.getId() != timeline.getId() && view.getId() != calling_id)
				((SoundTrackView)view).enableView();
		}
	}
	
	private void checkLongestTrack(int new_track_length)
	{
		if(new_track_length > longest_sound_track)
		{
			longest_sound_track = new_track_length;
			event_handler.setLongestTrack(longest_sound_track);
			Log.e("ABOUT TO UPDATE", "UPDATE");
			timeline.updateTrackEndText(new_track_length);
			for(int i = 0; i < tracks.size(); i++)
			{
				tracks.get(i).resize();
			}
		}
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
		}
		longest_sound_track = 0;
		
		for(int child = 0; child < parent_layout.getChildCount(); child++)
		{
			View view = parent_layout.getChildAt(child);
			if(view.getId() != timeline.getId())
				parent_layout.removeView(view);
		}
		
		longest_sound_track = DEFAULT_LENGTH;
		tracks.clear();
	}
	
	
	public int getStartPointByPixel(int pixel)
	{
		return event_handler.computeStartPointInSecondsByPixel(pixel);
	}
	
	public void setCallingParameters(int id, SoundTrack track)
	{
		calling_id = id;
		calling_track = track;
	}
	
	public int getNewViewID()
	{
		view_id = view_id + 1;
		return view_id;
	}
	
	public ArrayList<SoundTrackView> getTracks() {
		return tracks;
	}
	
	public int getNumberOfTracks() {
		return tracks.size();
	}
	
	public SoundTrack getCallingTrack()
	{
		return calling_track;
	}
	
	public int getDurationLongestTrack()
	{
		return longest_sound_track;
	}
}
