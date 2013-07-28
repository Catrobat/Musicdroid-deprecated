/*******************************************************************************
 * Catroid: An on-device visual programming system for Android devices
 *  Copyright (C) 2010-2013 The Catrobat Team
 *  (<http://developer.catrobat.org/credits>)
 *  
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 * 
 *  An additional term exception under section 7 of the GNU Affero
 *  General Public License, version 3, is available at
 *  http://www.catroid.org/catroid/licenseadditionalterm
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU Affero General Public License for more details.
 * 
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package at.tugraz.musicdroid.soundmixer;

import java.util.ArrayList;


import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;
import at.tugraz.musicdroid.MainActivity;
import at.tugraz.musicdroid.R;
import at.tugraz.musicdroid.SoundManager;
import at.tugraz.musicdroid.helper.Helper;
import at.tugraz.musicdroid.metronom.Metronom;
import at.tugraz.musicdroid.preferences.PreferenceManager;
import at.tugraz.musicdroid.soundmixer.timeline.Timeline;
import at.tugraz.musicdroid.soundmixer.timeline.TimelineEventHandler;
import at.tugraz.musicdroid.soundmixer.timeline.TimelineMenuCallback;
import at.tugraz.musicdroid.soundtracks.SoundTrack;
import at.tugraz.musicdroid.soundtracks.SoundTrackView;

public class SoundMixer implements HorizontalScrollViewListener {
	public static SoundMixer instance = null;
	protected ObservableHorizontalScrollView horScrollView;
	protected RelativeLayout parentLayout;
	protected MainActivity parent;
	protected ArrayList<SoundTrackView> tracks = new ArrayList<SoundTrackView>();
	protected int viewId;
	private int defaultLength;
	private int longestSoundTrack;
	private int soundMixerLength;
	private int callingId;
	private int pixelPerSecond;
	private SoundTrack callingTrack = null;
	private SoundMixerEventHandler eventHandler = null;
	private TimelineEventHandler timelineEventHandler = null;
	private Timeline timeline = null;
	private Metronom metronom = null;

	public static SoundMixer getInstance() {
		if (instance == null) {
			instance = new SoundMixer();
		}
		return instance;
	}

	public void initSoundMixer(MainActivity activity,
			ObservableHorizontalScrollView scrollView) {
		
		defaultLength = PreferenceManager.getInstance().getPreference(
				PreferenceManager.SOUNDTRACK_DEFAULT_LENGTH_KEY);
		parent = activity;
		horScrollView = scrollView;
		parentLayout = (RelativeLayout) horScrollView
				.findViewById(R.id.sound_mixer_relative);
		timeline = new Timeline(parent);

		timelineEventHandler = new TimelineEventHandler();
		timelineEventHandler.init(timeline);
		

		eventHandler = new SoundMixerEventHandler(this, timelineEventHandler);

		activity.setCallbackTimelineMenu(new TimelineMenuCallback(activity,
				timeline));

		Log.i("SoundMixer", "DefaultLength " + defaultLength);
		soundMixerLength = longestSoundTrack = defaultLength;
		pixelPerSecond = Helper.getScreenWidth(activity) / defaultLength;

		LayoutParams lp = (LayoutParams) timeline.getLayoutParams();
		timeline.setId(getNewViewID());
		parentLayout.addView(timeline, lp);
		horScrollView.setScrollViewListener(this);

		metronom = new Metronom(activity);
	}

	@Override
	public void onScrollChanged(ObservableHorizontalScrollView scrollView,
			int x, int y, int oldx, int oldy) {
		// if(Math.abs(oldx-x)>2)
		// {
		// Log.i("X " + x, "OldX " + oldx);
		// timeline.updateArrowOnScroll(x-oldx);
		// }

	}

	public SoundMixer() {
		viewId = 1234;
	}

	public void handleCopy() {
		SoundTrack copy = new SoundTrack(callingTrack);
		addSoundTrackViewToSoundMixer(new SoundTrackView(parent, copy));
	}

	public void addSoundTrackViewToSoundMixer(SoundTrackView track) {
		track.setId(getNewViewID());
		checkLongestTrack(track.getSoundTrack().getDuration());
		RelativeLayout.LayoutParams params = positionTrack(track);
		tracks.add(track);
		parentLayout.addView(track, params);
		eventHandler.addObserver(track.getSoundTrack());
		timeline.addNewTrackPosition(track.getId(), track.getSoundTrack()
				.getType().getColorResource());
	}

	public boolean playAllSoundsInSoundmixer() {
		if (tracks.size() == 0) {
			return false;
		}

		if (PreferenceManager.getInstance().getPreference(
				PreferenceManager.METRONOM_VISUALIZATION_KEY) > 0)
			startMetronom();
		eventHandler.play();
		return true;
	}

	public void stopAllSoundsInSoundmixer() {
		eventHandler.stopNotifyThread();
		if (PreferenceManager.getInstance().getPreference(
				PreferenceManager.METRONOM_VISUALIZATION_KEY) > 0)
			stopMetronom();
		SoundManager.stopAllSounds();
	}

	public void stopAllSoundInSoundMixerAndRewind() {
		stopAllSoundsInSoundmixer();
		eventHandler.rewind();
		timeline.rewind();
	}

	public void rewind() {
		eventHandler.rewind();
		timeline.rewind();
	}

	public void updateTimelineOnMove(int id, int pix_pos, int sec_pos,
			int duration) {
		timeline.updateTimelineOnMove(id, pix_pos, sec_pos, duration);
	}

	public void startMetronom() {
		metronom.startMetronome();
	}

	public void stopMetronom() {
		metronom.stopMetronome();
	}

	public void deleteCallingTrack() {
		deleteTrackById(callingId);
		eventHandler.deleteObserver(getCallingTrack());
		timeline.removeTrackPosition(callingId);
	}

	public void deleteTrackById(int tId) {
		for (int i = 0; i < tracks.size(); i++) {
			if (tracks.get(i).getId() == tId) {
				parentLayout.removeView(tracks.get(i));
				reorderLayout(i);
				tracks.remove(i);
			}
		}
	}

	public void disableUnselectedViews() {
		for (int child = 0; child < parentLayout.getChildCount(); child++) {
			View view = parentLayout.getChildAt(child);
			if (view.getId() != timeline.getId() && view.getId() != callingId)
				((SoundTrackView) view).disableView();
		}
	}

	public void enableUnselectedViews() {
		for (int child = 0; child < parentLayout.getChildCount(); child++) {
			View view = parentLayout.getChildAt(child);
			if (view.getId() != timeline.getId() && view.getId() != callingId)
				((SoundTrackView) view).enableView();
		}
	}

	private void checkLongestTrack(int newTrackLength) {
		if (newTrackLength > soundMixerLength) {
			soundMixerLength = newTrackLength;
			resizeSoundMixer(newTrackLength);
			timeline.resizeTimeline(newTrackLength);
		}
	}

	private void resizeSoundMixer(int length) {
		ViewGroup.LayoutParams layoutParams = (ViewGroup.LayoutParams) parentLayout
				.getLayoutParams();
		layoutParams.width = SoundMixer.getInstance().getPixelPerSecond()
				* length;
		parentLayout.setLayoutParams(layoutParams);
	}

	private void reorderLayout(int position) {
		if (position != 0 && position != tracks.size() - 1) {
			SoundTrackView predecessor = tracks.get(position - 1);
			SoundTrackView successor = tracks.get(position + 1);

			RelativeLayout.LayoutParams params = (LayoutParams) successor
					.getLayoutParams();
			params.addRule(RelativeLayout.BELOW, predecessor.getId());
			successor.setLayoutParams(params);
		}
		if (position == 0 && tracks.size() > 1) {
			SoundTrackView successor = tracks.get(position + 1);
			RelativeLayout.LayoutParams params = (LayoutParams) successor
					.getLayoutParams();
			params.addRule(RelativeLayout.BELOW, timeline.getId());
			successor.setLayoutParams(params);
		}
	}

	private RelativeLayout.LayoutParams positionTrack(SoundTrackView track) {
		if (tracks.size() > 0) {
			SoundTrackView lowermost_track = tracks.get(tracks.size() - 1);
			RelativeLayout.LayoutParams layoutParams = (LayoutParams) track
					.getLayoutParams();
			layoutParams.addRule(RelativeLayout.BELOW, lowermost_track.getId());
			return layoutParams;
		} else {
			RelativeLayout.LayoutParams layoutParams = (LayoutParams) track
					.getLayoutParams();
			layoutParams.addRule(RelativeLayout.BELOW, timeline.getId());
			return layoutParams;
		}
	}

	public void resetSoundMixer() {
		for (int i = 0; i < tracks.size(); i++) {
			tracks.get(i).removeAllViews();
			parentLayout.removeView(tracks.get(i));
		}
		longestSoundTrack = 0;

		timeline.resetTimeline();

		longestSoundTrack = soundMixerLength = defaultLength;
		tracks.clear();
	}

	public void setSoundMixerLength(int length) {
		if (length > soundMixerLength)
			soundMixerLength = length;
	}

	public void setSoundTrackLengthAndResizeTracks(int minutes, int seconds) {
		int newLength = minutes * 60 + seconds;
		if (newLength > soundMixerLength) {
			soundMixerLength = newLength;
			resizeSoundMixer(newLength);
			timeline.resizeTimeline(newLength);
		} else if (newLength < soundMixerLength && newLength >= defaultLength) {
			soundMixerLength = newLength;
			resizeSoundMixer(newLength);
			timeline.resizeTimeline(newLength);
		}
	}

	public void setStartPoint(int[] location) {
		if (eventHandler.setStartPoint(location[0] / pixelPerSecond))
			timeline.setStartPoint(location[0]);
		else
			Toast.makeText(parent, R.string.warning_invalid_marker_position,
					Toast.LENGTH_SHORT).show();

	}

	public void setEndPoint(int[] location) {
		if (eventHandler.setEndPoint(location[0] / pixelPerSecond))
			timeline.setEndPoint(location[0]);
		else
			Toast.makeText(parent, R.string.warning_invalid_marker_position,
					Toast.LENGTH_SHORT).show();
	}

	public void setCallingParameters(int id, SoundTrack track) {
		callingId = id;
		callingTrack = track;
	}

	public int getStartPointByPixel(int pixel) {
		return eventHandler.computeStartPointInSecondsByPixel(pixel);
	}

	public int getNewViewID() {
		viewId = viewId + 1;
		return viewId;
	}

	public ArrayList<SoundTrackView> getTracks() {
		return tracks;
	}

	public int getNumberOfTracks() {
		return tracks.size();
	}

	public SoundTrack getCallingTrack() {
		return callingTrack;
	}

	public int getDurationLongestTrack() {
		return longestSoundTrack;
	}

	public int getSoundMixerLength() {
		return soundMixerLength;
	}

	public int getPixelPerSecond() {
		if (pixelPerSecond == 0)
			pixelPerSecond = Helper.getScreenWidth(parent)
					/ defaultLength;
		return pixelPerSecond;
	}

	public SoundMixerEventHandler getEventHandler() {
		return eventHandler;
	}

	public int getStopPointFromEventHandler() {
		return eventHandler.getStopPoint();
	}

}
