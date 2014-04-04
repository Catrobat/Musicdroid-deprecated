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
package org.catrobat.musicdroid.soundmixer;

import android.widget.Toast;

import org.catrobat.musicdroid.MainActivity;
import org.catrobat.musicdroid.R;
import org.catrobat.musicdroid.SoundManager;
import org.catrobat.musicdroid.metronom.Metronom;
import org.catrobat.musicdroid.preferences.PreferenceManager;
import org.catrobat.musicdroid.soundmixer.timeline.Timeline;
import org.catrobat.musicdroid.soundmixer.timeline.TimelineMenuCallback;
import org.catrobat.musicdroid.soundtracks.SoundTrack;
import org.catrobat.musicdroid.soundtracks.SoundTrackView;
import org.catrobat.musicdroid.tools.DeviceInfo;

import java.util.ArrayList;

public class SoundMixer {
	public static SoundMixer instance = null;
	protected MainActivity parentActivity;
	protected ArrayList<SoundTrackView> tracks;
	private SoundMixerLayout layout;
	private int defaultTrackLength;
	private int soundMixerLength;
	private int pixelPerSecond;
	private int callingId;
	private SoundTrack callingTrack = null;
	private SoundMixerEventHandler eventHandler = null;
	private Metronom metronom = null;
	private Timeline timeline;
	private UniqueSoundMixerIDCreator idCreator;

	public SoundMixer() {
		tracks = new ArrayList<SoundTrackView>();
	}

	public static SoundMixer getInstance() {
		if (instance == null) {
			instance = new SoundMixer();
		}
		return instance;
	}

	public void initSoundMixer(MainActivity activity) {
		defaultTrackLength = PreferenceManager.getInstance().getPreference(
				PreferenceManager.SOUNDTRACK_DEFAULT_LENGTH_KEY);
		parentActivity = activity;
		layout = new SoundMixerLayout(activity, this);
		eventHandler = new SoundMixerEventHandler(this);
		timeline = new Timeline(activity);
		metronom = new Metronom(activity);
		idCreator = new UniqueSoundMixerIDCreator();

		timeline.setId(idCreator.getNewId());
		layout.addTimelineToLayout(timeline);

		activity.setCallbackTimelineMenu(new TimelineMenuCallback(activity, timeline));

		soundMixerLength = defaultTrackLength;
		pixelPerSecond = DeviceInfo.getScreenWidth(parentActivity) / defaultTrackLength;
	}

	public void handleCopy() {
		SoundTrack copy = new SoundTrack(callingTrack);
		addSoundTrackViewToSoundMixer(new SoundTrackView(parentActivity, copy));
	}

	public void addSoundTrackViewToSoundMixer(SoundTrackView track) {
		track.setId(idCreator.getNewId());
		checkLongestTrack(track.getSoundTrack().getDuration());
		eventHandler.addObserver(track.getSoundTrack());
		layout.addTrackToLayout(track);
		tracks.add(track);
		timeline.addNewTrackPosition(track.getId(), track.getSoundTrack().getType().getColorResource());
	}

	public boolean playAllSoundsInSoundmixer() {
		if (tracks.size() == 0) {
			return false;
		}

		if (PreferenceManager.getInstance().getPreference(PreferenceManager.METRONOM_VISUALIZATION_KEY) > 0) {
			startMetronom();
		}
		eventHandler.play();
		return true;
	}

	public void stopAllSoundsInSoundmixer() {
		eventHandler.stopNotifyThread();
		if (PreferenceManager.getInstance().getPreference(PreferenceManager.METRONOM_VISUALIZATION_KEY) > 0) {
			stopMetronom();
		}
		SoundManager.stopAllSounds();
	}

	public void stopAllSoundInSoundMixerAndRewind() {
		stopAllSoundsInSoundmixer();
		rewind();
	}

	public void rewind() {
		eventHandler.rewind();
		timeline.rewind();
	}

	public void updateTimelineOnMove(int id, int pixPos, int secPos, int trackDuration) {
		timeline.updateTimelineOnMove(id, pixPos, secPos, trackDuration);
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

	public void deleteTrackById(int deletedTrackID) {
		for (int trackID = 0; trackID < tracks.size(); trackID++) {
			if (tracks.get(trackID).getId() == deletedTrackID) {
				layout.removeTrackFromLayout(tracks.get(trackID), trackID);
				tracks.remove(trackID);
			}
		}
	}

	public void disableUnselectedViews() {
		layout.disableUnselectedViews(callingId);
	}

	public void enableUnselectedViews() {
		layout.enableUnselectedViews(callingId);
	}

	private void checkLongestTrack(int newTrackLength) {
		if (newTrackLength > soundMixerLength) {
			soundMixerLength = newTrackLength;
			layout.resizeLayoutWidth(getPixelPerSecond() * newTrackLength);
			timeline.resizeTimeline(newTrackLength);
		}
	}

	public void resetSoundMixer() {
		for (int i = 0; i < tracks.size(); i++) {
			tracks.get(i).removeAllViews();
			layout.removeView(tracks.get(i));
		}
		timeline.resetTimeline();
		soundMixerLength = defaultTrackLength;
		tracks.clear();
	}

	public void setSoundTrackLengthAndResizeTracks(int minutes, int seconds) {
		int newLength = minutes * 60 + seconds;
		soundMixerLength = newLength;
		layout.resizeLayoutWidth(getPixelPerSecond() * newLength);
		timeline.resizeTimeline(newLength);
	}

	public void setStartPoint(int[] location) {
		if (eventHandler.setStartPoint(location[0] / pixelPerSecond)) {
			timeline.setStartPoint(location[0]);
		} else {
			Toast.makeText(parentActivity, R.string.warning_invalid_marker_position, Toast.LENGTH_SHORT).show();
		}
	}

	public void setEndPoint(int[] location) {
		if (eventHandler.setEndPoint(location[0] / pixelPerSecond)) {
			timeline.setEndPoint(location[0]);
		} else {
			Toast.makeText(parentActivity, R.string.warning_invalid_marker_position, Toast.LENGTH_SHORT).show();
		}
	}

	public void setCallingParameters(int id, SoundTrack track) {
		callingId = id;
		callingTrack = track;
	}

	public int getStartPointByPixel(int pixel) {
		return eventHandler.computeStartPointInSecondsByPixel(pixel);
	}

	public int getNumberOfTracks() {
		return tracks.size();
	}

	public SoundTrack getCallingTrack() {
		return callingTrack;
	}

	public int getSoundMixerLength() {
		return soundMixerLength;
	}

	public void setSoundMixerLength(int length) {
		if (length > soundMixerLength) {
			soundMixerLength = length;
		}
	}

	public int getPixelPerSecond() {
		if (pixelPerSecond == 0) {
			pixelPerSecond = DeviceInfo.getScreenWidth(parentActivity) / defaultTrackLength;
		}
		return pixelPerSecond;
	}

	public SoundMixerEventHandler getEventHandler() {
		return eventHandler;
	}

	public int getStopPointFromEventHandler() {
		return eventHandler.getStopPoint();
	}

	public SoundTrackView getTrackAtPosition(int position) {
		return tracks.get(position);
	}

	public Timeline getTimeline() {
		return timeline;
	}
}
