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

import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import org.catrobat.musicdroid.instruments.Instrument;
import org.catrobat.musicdroid.note.NoteEvent;

import java.util.ArrayList;

/**
 * @author musicdroid
 * 
 */

public class DrumActivity extends Instrument {

	private DrumTrackView drumTrackView;
	private DrumKitView drumKitView;
	private ArrayList<DrumEvent> drumEventList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		drumEventList = new ArrayList<DrumEvent>();
		drumTrackView = new DrumTrackView(this);
		drumKitView = new DrumKitView(this);

		LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,
				1.0f);
		drumTrackView.setLayoutParams(layoutParams);
		drumKitView.setLayoutParams(layoutParams);

		LinearLayout linearLayout = new LinearLayout(this);
		HorizontalScrollView horizontalScrollView = new HorizontalScrollView(this);
		horizontalScrollView.addView(drumTrackView);

		linearLayout.addView(horizontalScrollView);
		linearLayout.addView(drumKitView);

		//RelativeLayout item = (RelativeLayout) view.findViewById(R.id.item);
		//View.inflate(this, R.layout.drumset_layout, null);//addView(drumView);
		linearLayout.setOrientation(1);
		setContentView(linearLayout);
	}

	@Override
	protected void doAfterAddNoteEvent(NoteEvent noteEvent) {
		//noteSheetView.redraw(getTrack());
	}

	public void addDrumEvent(DrumEvent drumEvent) {
		drumEventList.add(drumEvent);
		drumTrackView.updateView(drumEvent);
	}

}
