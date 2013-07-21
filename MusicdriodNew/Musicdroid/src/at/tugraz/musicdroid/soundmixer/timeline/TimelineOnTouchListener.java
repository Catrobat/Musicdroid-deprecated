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

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import at.tugraz.musicdroid.R;
import at.tugraz.musicdroid.soundmixer.SoundMixer;

public class TimelineOnTouchListener implements OnTouchListener {
	private Timeline timeline;
	private ImageButton startPoint = null;
	private ImageButton endPoint = null;
	private int xDelta = 0;

	public TimelineOnTouchListener(Timeline t) {
		this.timeline = t;
		startPoint = (ImageButton) timeline
				.findViewById(R.id.timeline_start_point);
		endPoint = (ImageButton) timeline.findViewById(R.id.timeline_end_point);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		if (v.getId() == startPoint.getId()) {
			Log.i("TOUCH: ", "StartPoint");
			return handleStartPointOnTouch(event);
		}
		if (v.getId() == endPoint.getId()) {
			Log.i("TOUCH: ", "EndPoint");
			return handleEndPointOnTouch(event);
		}
		Log.i("TOUCH: ", "Timeline");
		return gestureDetector.onTouchEvent(event);

	}

	@SuppressWarnings("deprecation")
	final GestureDetector gestureDetector = new GestureDetector(
			new GestureDetector.SimpleOnGestureListener() {
				public void onLongPress(final MotionEvent e) {
					// Log.i("Location: ", "X = " + e.getX() + " | Y = " +
					// e.getY());
					int[] location = { (int) e.getX(), (int) e.getY() };
					timeline.setClickLocation(location);
					timeline.startTimelineActionMode();
				}
			});

	private boolean handleStartPointOnTouch(MotionEvent event) {
		final int X = (int) event.getRawX();
		boolean ret = true;
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			timeline.requestDisallowInterceptTouchEvent(true);
			RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) startPoint
					.getLayoutParams();
			xDelta = X - lParams.leftMargin;
			break;
		case MotionEvent.ACTION_MOVE:
			timeline.requestDisallowInterceptTouchEvent(true);
			RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) startPoint
					.getLayoutParams();
			int old_margin = layoutParams.leftMargin;
			int margin = X - xDelta;

			if (margin != old_margin) {
				layoutParams.leftMargin = margin;
				int[] location = { margin, 0 };
				SoundMixer.getInstance().setStartPoint(location);
			}
			ret = true;
			break;
		}
		return ret;
	}

	private boolean handleEndPointOnTouch(MotionEvent event) {
		final int X = (int) event.getRawX();
		boolean ret = true;
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			timeline.requestDisallowInterceptTouchEvent(true);
			RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) endPoint
					.getLayoutParams();
			xDelta = X - lParams.leftMargin;
			break;
		case MotionEvent.ACTION_MOVE:
			timeline.requestDisallowInterceptTouchEvent(true);
			RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) endPoint
					.getLayoutParams();
			int old_margin = layoutParams.leftMargin;
			int margin = X - xDelta;

			if (margin != old_margin) {
				layoutParams.leftMargin = margin;
				int[] location = { margin, 0 };
				SoundMixer.getInstance().setEndPoint(location);
			}
			ret = true;
			break;
		}
		return ret;
	}

	public void setStartPoint(ImageButton startPoint) {
		this.startPoint = startPoint;
	}

	public void setEndPoint(ImageButton endPoint) {
		this.endPoint = endPoint;
	}

}
