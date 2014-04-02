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
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * @author AM
 * 
 */
public class DrumTrackView extends LinearLayout {

	/**
	 * @param context
	 */
	private ArrayList<DrumTrackRowView> drumChannels;
	private int NUM_OF_CHANNELS = 3;

	public DrumTrackView(Activity a) {
		super(a);
		this.setOrientation(VERTICAL);
		this.setLayoutParams(new LayoutParams(900, 100, 1.0f));
		LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,
				1.0f);

		drumChannels = new ArrayList<DrumTrackRowView>();

		for (int i = 0; i < NUM_OF_CHANNELS; i++) {
			drumChannels.add(new DrumTrackRowView(a, "Channel " + i));
			drumChannels.get(i).setLayoutParams(layoutParams);
			this.addView(drumChannels.get(i));
		}

	}

	public void resetRowInDrumTrackView() {
		for (int i = 0; i < NUM_OF_CHANNELS; i++) {
			if (drumChannels.get(i).isSelected()) {
				drumChannels.get(i).resetDrumTrackRowView();
			}
		}
	}

	public void updateView(DrumEvent drumEvent) {
		for (int i = 0; i < NUM_OF_CHANNELS; i++) {
			if (drumChannels.get(i).isSelected()) {

				ImageView drumEventView = new ImageView(getContext());
				drumEventView.setImageResource(drumEvent.getImageRessource());
				LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(100, 30);
				drumEventView.setLayoutParams(layoutParams);
				drumChannels.get(i).drawTrackElement(drumEventView);
			}
		}
	}
}
