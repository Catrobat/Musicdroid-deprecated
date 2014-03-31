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
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author AM
 * 
 */
public class DrumTrackView extends LinearLayout {

	/**
	 * @param context
	 */
	private DrumTrackRowView channel1View;

	public DrumTrackView(Activity a) {
		super(a);

		channel1View = new DrumTrackRowView(a, "Channel 1");
		this.addView(channel1View);
	}

	/**
	 * @param drumEvent
	 */
	public void updateView(DrumEvent drumEvent) {
		if (channel1View.isSelected()) {
			TextView testTextView = new TextView(getContext());
			testTextView.setText(drumEvent.getDrumPartName());
			this.addView(testTextView);
		}
	}
}
