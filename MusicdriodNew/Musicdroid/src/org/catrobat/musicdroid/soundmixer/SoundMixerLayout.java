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

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import org.catrobat.musicdroid.MainActivity;
import org.catrobat.musicdroid.R;
import org.catrobat.musicdroid.soundmixer.timeline.Timeline;
import org.catrobat.musicdroid.soundtracks.SoundTrackView;

/**
 * @author matthias schlesinger
 */
public class SoundMixerLayout implements HorizontalScrollViewListener {
	private ObservableHorizontalScrollView horScrollView;
	private RelativeLayout parentLayout;
	private SoundMixer soundMixer;
	private Timeline timeline;

	public SoundMixerLayout(MainActivity activity, SoundMixer mixer) {
		soundMixer = mixer;
		horScrollView = (ObservableHorizontalScrollView) activity.findViewById(R.id.sound_mixer_view);
		parentLayout = (RelativeLayout) horScrollView.findViewById(R.id.sound_mixer_relative);

		horScrollView.setScrollViewListener(this);
	}

	@Override
	public void onScrollChanged(ObservableHorizontalScrollView scrollView, int x, int y, int oldx, int oldy) {
	}

	public void addTrackToLayout(SoundTrackView track) {
		positionTrack(track);
		parentLayout.addView(track);
	}

	public void removeTrackFromLayout(SoundTrackView removedTrack, int positionOfRemovedTrack) {
		parentLayout.removeView(removedTrack);
		reorderTrackPositionOnRemoveTrack(positionOfRemovedTrack);
	}

	public void disableUnselectedViews(int callingTrackId) {
		for (int child = 0; child < parentLayout.getChildCount(); child++) {
			View view = parentLayout.getChildAt(child);
			if (view.getId() != timeline.getId() && view.getId() != callingTrackId) {
				((SoundTrackView) view).disableView();
			}
		}
	}

	public void enableUnselectedViews(int callingTrackId) {
		for (int child = 0; child < parentLayout.getChildCount(); child++) {
			View view = parentLayout.getChildAt(child);
			if (view.getId() != timeline.getId() && view.getId() != callingTrackId) {
				((SoundTrackView) view).enableView();
			}
		}
	}

	private void positionTrack(SoundTrackView track) {
		if (soundMixer.getNumberOfTracks() > 0) {
			SoundTrackView lowermostTrack = soundMixer.getTrackAtPosition(soundMixer.getNumberOfTracks() - 1);
			track.alignTrack(RelativeLayout.BELOW, lowermostTrack.getId());
		} else {
			track.alignTrack(RelativeLayout.BELOW, timeline.getId());
		}
	}

	private void reorderTrackPositionOnRemoveTrack(int trackPosition) {
		Log.i("SoundMixerLayout", "Reorder: Track Pos = " + trackPosition);

		int numberOfTracksInSoundMixer = soundMixer.getNumberOfTracks();
		if (trackPosition != 0 && trackPosition != numberOfTracksInSoundMixer - 1) {
			SoundTrackView predecessor = soundMixer.getTrackAtPosition(trackPosition - 1);
			SoundTrackView successor = soundMixer.getTrackAtPosition(trackPosition + 1);
			successor.alignTrack(RelativeLayout.BELOW, predecessor.getId());
		} else if (trackPosition == 0 && numberOfTracksInSoundMixer > 1) {
			SoundTrackView successor = soundMixer.getTrackAtPosition(trackPosition + 1);
			successor.alignTrack(RelativeLayout.BELOW, timeline.getId());
		}
	}

	public void addTimelineToLayout(Timeline timeline) {
		this.timeline = timeline;
		LayoutParams lp = (LayoutParams) timeline.getLayoutParams();
		parentLayout.addView(timeline, lp);
	}

	public void removeView(SoundTrackView removedView) {
		parentLayout.removeView(removedView);
	}

	public void resizeLayoutWidth(int newWidth) {
		ViewGroup.LayoutParams layoutParams = parentLayout.getLayoutParams();
		layoutParams.width = newWidth;
		parentLayout.setLayoutParams(layoutParams);
	}
}
