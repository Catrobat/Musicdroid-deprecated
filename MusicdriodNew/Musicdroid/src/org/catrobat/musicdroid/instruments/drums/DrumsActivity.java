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
import android.widget.LinearLayout;

import org.catrobat.musicdroid.instruments.Instrument;
import org.catrobat.musicdroid.note.NoteEvent;
import org.catrobat.musicdroid.note.draw.NoteSheetView;

/**
 * @author musicdroid
 * 
 */
public class DrumsActivity extends Instrument {

	private NoteSheetView noteSheetView;
	private DrumView drumView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		noteSheetView = new NoteSheetView(this);
		drumView = new DrumView(this);

		LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,
				1.0f);
		noteSheetView.setLayoutParams(layoutParams);
		drumView.setLayoutParams(layoutParams);

		LinearLayout linearLayout = new LinearLayout(this);

		linearLayout.addView(noteSheetView);
		linearLayout.addView(drumView);

		//RelativeLayout item = (RelativeLayout) view.findViewById(R.id.item);
		//View.inflate(this, R.layout.drumset_layout, null);//addView(drumView);
		linearLayout.setOrientation(1);
		setContentView(linearLayout);
	}

	@Override
	protected void doAfterAddNoteEvent(NoteEvent noteEvent) {
		noteSheetView.redraw(getTrack());
	}
}
