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
package at.tugraz.musicdroid.soundmixer.timeline;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import at.tugraz.musicdroid.MainActivity;
import at.tugraz.musicdroid.R;
import at.tugraz.musicdroid.helper.Helper;
import at.tugraz.musicdroid.preferences.PreferenceManager;
import at.tugraz.musicdroid.soundmixer.SoundMixer;

public class Timeline extends RelativeLayout {
	private Helper helper = null;
	private Context context = null;
	private RelativeLayout timelineTop = null;
	private RelativeLayout timelineBottom = null;
	private TextView startTimeTextView = null;
	private ImageView arrowView = null;
	private ImageButton startPointImageButton = null;
	private ImageButton endPointImageButton = null;
	private View currentPositionView = null;

	private int startId = 9876;
	private int height = 0;
	private int lastSetTime = 0;
	private int[] clickLocation;
	private HashMap<Integer, TimelineTrackPosition> trackPositions = null;
	private TimelineOnTouchListener onTouchListener = null;

	public Timeline(Context context) {
		super(context);
		this.context = context;
		helper = Helper.getInstance();
		trackPositions = new HashMap<Integer, TimelineTrackPosition>();

		LayoutInflater inflater = LayoutInflater.from(this.context);
		inflater.inflate(R.layout.timeline_layout, this);

		initTimeline();
		onTouchListener = new TimelineOnTouchListener(this);
		this.setOnTouchListener(onTouchListener);
	}

	public Timeline(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	private void initTimeline() {
		height = helper.getScreenHeight() / 18;
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
				helper.getScreenWidth(), height * 2);
		setLayoutParams(layoutParams);
		setBackgroundColor(getResources().getColor(
				R.color.background_holo_light));

		timelineTop = (RelativeLayout) findViewById(R.id.timeline_top);
		timelineBottom = (RelativeLayout) findViewById(R.id.timeline_bottom);

		((RelativeLayout.LayoutParams) timelineTop.getLayoutParams()).height = height;
		((RelativeLayout.LayoutParams) timelineBottom.getLayoutParams()).height = height;

		startTimeTextView = (TextView) findViewById(R.id.timeline_start_time);
		startPointImageButton = (ImageButton) findViewById(R.id.timeline_start_point);
		endPointImageButton = (ImageButton) findViewById(R.id.timeline_end_point);
		currentPositionView = (View) findViewById(R.id.timeline_currentPosition);

		startTimeTextView.setText("00:00");

		addPositionMarker();
	}

	public void resizeTimeline(int newLength) {
		int oldLength = getWidth()
				/ SoundMixer.getInstance().getPixelPerSecond();
		int defaultLength = PreferenceManager.getInstance().getPreference(
				PreferenceManager.SOUNDTRACK_DEFAULT_LENGTH_KEY);

		RelativeLayout.LayoutParams layoutParams = (LayoutParams) getLayoutParams();
		layoutParams.width = SoundMixer.getInstance().getPixelPerSecond()
				* newLength;

		if (newLength > oldLength) {
			for (int second = oldLength - 5; second < newLength; second++) {
				timelineBottom.addView(newPositionMarker(second));
			}
		}

		if (newLength > defaultLength && arrowView == null) {
			// addArrow(); //TODO ms
		}
	}

	public void addNewTrackPosition(int id, int colorRes) {
		trackPositions.put(id, new TimelineTrackPosition(this, context,
				colorRes));
	}

	public void updateTimelineOnMove(int id, int pixPos, int secPos,
			int duration) {
		int pixelPerSecond = SoundMixer.getInstance().getPixelPerSecond();
		trackPositions.get(id).updateTrackPosition(pixPos, secPos);
		if ((secPos + duration) * pixelPerSecond > getWidth()) {
			SoundMixer.getInstance().setSoundMixerLength(secPos + duration);
			resizeTimeline(secPos + duration);
		}
	}

	public void removeTrackPosition(int id) {
		TimelineTrackPosition tp = trackPositions.get(id);
		this.removeView(tp.getTrackPosition());
		// this.removeView(tp.getTrackPositionText());
		trackPositions.remove(id);
	}

	public void setStartPoint(int x) {
		int pixelPerSecond = SoundMixer.getInstance().getPixelPerSecond();
		RelativeLayout.LayoutParams layout = (LayoutParams) startPointImageButton
				.getLayoutParams();
		layout.height = getHeight();
		layout.width = pixelPerSecond;
		int leftMargin = x - (x % pixelPerSecond) - pixelPerSecond + 1;

		layout.setMargins(leftMargin, 0, 0, 0);
		startPointImageButton.setColorFilter(Color.BLACK);
		startPointImageButton.setVisibility(VISIBLE);
		startPointImageButton.setLayoutParams(layout);
		startPointImageButton.setOnTouchListener(onTouchListener);

		RelativeLayout.LayoutParams positionLayout = (LayoutParams) currentPositionView
				.getLayoutParams();
		positionLayout.setMargins(leftMargin + pixelPerSecond, 0, 0, 0);
		positionLayout.width = 0;
		currentPositionView.setLayoutParams(positionLayout);
	}

	public void setEndPoint(int x) {
		int pixelPerSecond = SoundMixer.getInstance().getPixelPerSecond();
		RelativeLayout.LayoutParams layout = (LayoutParams) endPointImageButton
				.getLayoutParams();
		layout.height = getHeight();
		layout.width = pixelPerSecond;
		layout.setMargins(x - (x % pixelPerSecond), 0, 0, 0);
		endPointImageButton.setColorFilter(Color.BLACK);
		endPointImageButton.setVisibility(VISIBLE);
		endPointImageButton.setLayoutParams(layout);
		endPointImageButton.setOnTouchListener(onTouchListener);
	}

	public void resetTimeline() {
		Iterator<Entry<Integer, TimelineTrackPosition>> it = trackPositions
				.entrySet().iterator();

		while (it.hasNext()) {
			HashMap.Entry<Integer, TimelineTrackPosition> pairs = (Entry<Integer, TimelineTrackPosition>) it
					.next();
			removeView(pairs.getValue().getTrackPosition());
		}
	}

	private void addPositionMarker() {
		int defaultLength = PreferenceManager.getInstance().getPreference(
				PreferenceManager.SOUNDTRACK_DEFAULT_LENGTH_KEY);
		for (int second = 0; second <= defaultLength; second++) {
			timelineBottom.addView(newPositionMarker(second));
		}
	}

	private View newPositionMarker(int second) {
		int pixelPerSecond = SoundMixer.getInstance().getPixelPerSecond();

		View positionMarker = new View(context);
		LayoutParams markerParams = new RelativeLayout.LayoutParams(2,
				height * 2 / 5);

		if (second % 5 == 0) {
			markerParams = new RelativeLayout.LayoutParams(2, height * 4 / 8);
			if (second > 0 && second > lastSetTime)
				newPositionText(second, second * pixelPerSecond);
		}

		markerParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		markerParams.addRule(RelativeLayout.ALIGN_LEFT);
		markerParams.leftMargin = pixelPerSecond * second;
		positionMarker.setLayoutParams(markerParams);
		positionMarker.setBackgroundColor(Color.BLACK);
		positionMarker.setId(getNewId());
		return positionMarker;
	}

	private void newPositionText(int second, int position) {
		lastSetTime = second;
		TextView positionText = new TextView(context);
		positionText
				.setText(Helper.getInstance().durationStringFromInt(second));
		LayoutParams textParams = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		textParams.leftMargin = position - 25;
		positionText.setLayoutParams(textParams);
		timelineTop.addView(positionText);
	}

	public void rewind() {
		LayoutParams params = (LayoutParams) currentPositionView
				.getLayoutParams();
		params.width = 0;
		currentPositionView.setLayoutParams(params);
	}

	public void startTimelineActionMode() {
		((MainActivity) context).startTimelineActionMode();
	}

	public void setClickLocation(int[] l) {
		clickLocation = l;
	}

	public int[] getClickLocation() {
		return clickLocation;
	}

	public int getNewId() {
		int id = startId;
		startId = startId + 1;
		return id;
	}

	public View getTrackPositionView() {
		return currentPositionView;
	}

	/*
	 * public void updateArrowOnScroll(int scrollX) { if(arrowView == null)
	 * return;
	 * 
	 * RelativeLayout.LayoutParams layout = (LayoutParams)
	 * arrowView.getLayoutParams(); int oldMargin = layout.leftMargin;
	 * layout.setMargins(oldMargin+scrollX, 0, 0, 0);
	 * arrowView.setLayoutParams(layout); }
	 * 
	 * private void addArrow() { arrowView = new ImageView(context);
	 * arrowView.setImageDrawable
	 * (getResources().getDrawable(R.drawable.timeline_arrow));
	 * RelativeLayout.LayoutParams layout = new
	 * RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, getHeight());
	 * layout.addRule(ALIGN_PARENT_LEFT); layout.addRule(ALIGN_PARENT_TOP);
	 * layout.setMargins(Helper.getInstance().getScreenWidth()-50, 0, 0, 0);
	 * arrowView.setLayoutParams(layout); arrowView.setColorFilter(Color.BLACK);
	 * addView(arrowView); }
	 */
}
