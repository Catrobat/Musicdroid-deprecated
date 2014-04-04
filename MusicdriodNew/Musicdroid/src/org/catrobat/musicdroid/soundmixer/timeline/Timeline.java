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
package org.catrobat.musicdroid.soundmixer.timeline;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.catrobat.musicdroid.R;
import org.catrobat.musicdroid.preferences.PreferenceManager;
import org.catrobat.musicdroid.soundmixer.SoundMixer;
import org.catrobat.musicdroid.tools.DeviceInfo;

public class Timeline extends RelativeLayout {
	private Context context = null;
	private RelativeLayout timelineTop = null;
	private RelativeLayout timelineBottom = null;
	private ImageButton startPointImageButton = null;
	private ImageButton endPointImageButton = null;
	private TimelineProgressBar timelineProgressBar = null;
	private int height = 0;
	private int lastSetTime = 0;
	private int[] clickLocation;
	private SparseArray<TimelineTrackPosition> trackPositions = null;
	private TimelineOnTouchListener onTimelineTouchListener = null;
	private TimelineEventHandler timelineEventHandler;

	public Timeline(Context context) {
		super(context);
		this.context = context;
		trackPositions = new SparseArray<TimelineTrackPosition>();

		LayoutInflater inflater = LayoutInflater.from(this.context);
		inflater.inflate(R.layout.timeline_layout, this);

		initTimeline();
		timelineEventHandler = new TimelineEventHandler(this);
		onTimelineTouchListener = new TimelineOnTouchListener(this);
		this.setOnTouchListener(onTimelineTouchListener);
	}

	public Timeline(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	private void initTimeline() {
		height = DeviceInfo.getScreenHeight(context) / 18;
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(DeviceInfo.getScreenWidth(context),
				height * 2);
		setLayoutParams(layoutParams);
		setBackgroundColor(getResources().getColor(R.color.background_holo_light));

		timelineTop = (RelativeLayout) findViewById(R.id.timeline_top);
		timelineBottom = (RelativeLayout) findViewById(R.id.timeline_bottom);

		((RelativeLayout.LayoutParams) timelineTop.getLayoutParams()).height = height;
		((RelativeLayout.LayoutParams) timelineBottom.getLayoutParams()).height = height;

		((TextView) findViewById(R.id.timeline_start_time)).setText("00:00");
		startPointImageButton = (ImageButton) findViewById(R.id.timeline_start_point);
		endPointImageButton = (ImageButton) findViewById(R.id.timeline_end_point);
		timelineProgressBar = (TimelineProgressBar) findViewById(R.id.timeline_progressBar);

		initMarkerBar();
	}

	private void initMarkerBar() {
		int defaultLength = PreferenceManager.getInstance().getPreference(
				PreferenceManager.SOUNDTRACK_DEFAULT_LENGTH_KEY);
		for (int second = 0; second <= defaultLength; second++) {
			addMarker(second);
		}
	}

	public void resizeTimeline(int newLength) {
		int oldLength = getWidth() / SoundMixer.getInstance().getPixelPerSecond();

		RelativeLayout.LayoutParams layoutParams = (LayoutParams) getLayoutParams();
		layoutParams.width = SoundMixer.getInstance().getPixelPerSecond() * newLength;

		if (newLength > oldLength) {
			for (int second = oldLength - 5; second < newLength; second++) {
				addMarker(second);
			}
		}
	}

	private void addMarker(int second) {
		timelineBottom.addView(new TimelineMarker(context, second, height));
		if (second > 0 && second % 5 == 0 && second > lastSetTime) {
			lastSetTime = second;
			timelineTop.addView(new TimelineMarkerText(context, second));
		}
	}

	public void addNewTrackPosition(int id, int colorRes) {
		Log.i("Timeline", "AddTrackPos ID = " + id);
		TimelineTrackPosition trackPosition = new TimelineTrackPosition(context, colorRes);
		timelineBottom.addView(trackPosition);
		trackPositions.put(id, trackPosition);
	}

	public void removeTrackPosition(int id) {
		Log.i("Timeline", "RemoveTrackPosition ID = " + id);
		TimelineTrackPosition tp = trackPositions.get(id);
		timelineBottom.removeView(tp.getTrackPosition());
		trackPositions.remove(id);
	}

	public void updateTimelineOnMove(int id, int pixPos, int secPos, int duration) {
		int pixelPerSecond = SoundMixer.getInstance().getPixelPerSecond();
		trackPositions.get(id).updateTrackPosition(pixPos, secPos);
		if ((secPos + duration) * pixelPerSecond > getWidth()) {
			SoundMixer.getInstance().setSoundMixerLength(secPos + duration);
			resizeTimeline(secPos + duration);
		}
	}

	public void setStartPoint(int startPointX) {
		int pixelPerSecond = SoundMixer.getInstance().getPixelPerSecond();
		int leftMargin = startPointX - (startPointX % pixelPerSecond) - pixelPerSecond + 1;
		setBoundaryPointParameters(startPointImageButton, leftMargin);

		timelineProgressBar.setStartPosition(leftMargin + pixelPerSecond);
	}

	public void setEndPoint(int startPointX) {
		int pixelPerSecond = SoundMixer.getInstance().getPixelPerSecond();
		int leftMargin = startPointX - (startPointX % pixelPerSecond);
		setBoundaryPointParameters(endPointImageButton, leftMargin);
	}

	private void setBoundaryPointParameters(ImageButton boundaryPoint, int leftMargin) {
		int pixelPerSecond = SoundMixer.getInstance().getPixelPerSecond();
		RelativeLayout.LayoutParams layout = (LayoutParams) boundaryPoint.getLayoutParams();
		layout.height = getHeight();
		layout.width = pixelPerSecond;
		layout.setMargins(leftMargin, 0, 0, 0);
		boundaryPoint.setColorFilter(Color.BLACK);
		boundaryPoint.setVisibility(VISIBLE);
		boundaryPoint.setLayoutParams(layout);
		boundaryPoint.setOnTouchListener(onTimelineTouchListener);
	}

	public void resetTimeline() {
		for (int position = 0; position < trackPositions.size(); position++) {
			removeView(trackPositions.valueAt(position));
		}
	}

	public void rewind() {
		timelineProgressBar.rewind();
	}

	public void startTimelineActionMode() {
		Toast.makeText(context, "startTimelineActionMode is wrong implemented here!", Toast.LENGTH_SHORT).show();
		//		((MainActivity) context).startTimelineActionMode();
	}

	public int[] getClickLocation() {
		return clickLocation;
	}

	public void setClickLocation(int[] l) {
		clickLocation = l;
	}

	public TimelineProgressBar getTimelineProgressBar() {
		return timelineProgressBar;
	}

	public TimelineEventHandler getTimelineEventHandler() {
		return timelineEventHandler;
	}
}
