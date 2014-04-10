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
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * @author AM
 * 
 */
public class DrumKitPartView extends LinearLayout implements OnTouchListener {
	private static final float MENU_OFFSET = (float) 0.8;
	private DrumKitPart drumKitPart;
	private DrumActivity drumActivity;

	public DrumKitPartView(Activity a, DrumKitPart drumKitPart) {
		super(a);
		drumActivity = (DrumActivity) a;
		this.drumKitPart = drumKitPart;

		Point size = new Point();
		a.getWindowManager().getDefaultDisplay().getSize(size);
		int buttonSizeWidth = size.x / DrumKitView.getNumColumns();
		int buttonSizeHeight = (int) ((size.y / DrumKitView.getNumRows()) * MENU_OFFSET);

		Button drumPartButton = new Button(a);
		drumPartButton.setBackgroundResource(drumKitPart.getDrawableId());

		drumPartButton.setLayoutParams(new LayoutParams(buttonSizeWidth, buttonSizeHeight));
		this.addView(drumPartButton);

	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:

				break;
			case MotionEvent.ACTION_MOVE:
				break;
			case MotionEvent.ACTION_UP:
				drumActivity.addDrumEvent(new DrumEvent(drumKitPart));
				break;
		}
		return true;
	}

	public DrumKitPart getDrumKitPart() {
		return drumKitPart;
	}

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		return true;
	}
}