/**
 *  Catroid: An on-device visual programming system for Android devices
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
 *  http://developer.catrobat.org/license_additional_term
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU Affero General Public License for more details.
 *  
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.catrobat.musicdroid.instruments.drums;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;

/**
 * @author AM
 * 
 */
public class DrumKitPartView extends LinearLayout implements OnTouchListener {
	private DrumKitPart drumKitPart;
	DrumActivity drumActivity;

	public DrumKitPartView(Activity a, DrumKitPart drumKitPart) {
		super(a);
		drumActivity = (DrumActivity) a;
		this.drumKitPart = drumKitPart;
		ImageButton drumPartButton = new ImageButton(a);
		drumPartButton.setBackgroundResource(drumKitPart.getDrawableId());

		drumPartButton.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

		//this.addView(drumPartNameView);
		this.addView(drumPartButton);

	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return true; // With this i tell my layout to consume all the touch events from its childs
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:

				break;
			case MotionEvent.ACTION_MOVE:
				//Log.d(TAG, String.format("ACTION_MOVE | x:%s y:%s", 
				break;
			case MotionEvent.ACTION_UP:
				drumActivity.addDrumEvent(new DrumEvent(drumKitPart.getDrumPartName(), drumKitPart.getDrawableId()));
				break;
		}
		return true;
	}

	public DrumKitPart getDrumKitPart() {
		return drumKitPart;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnTouchListener#onTouch(android.view.View, android.view.MotionEvent)
	 */
	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		return true;
	}
}