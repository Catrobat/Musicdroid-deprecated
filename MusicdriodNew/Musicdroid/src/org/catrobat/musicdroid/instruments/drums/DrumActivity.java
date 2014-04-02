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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;

import org.catrobat.musicdroid.R;
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
		setContentView(R.layout.main_drum_layout);

		drumEventList = new ArrayList<DrumEvent>();

		drumTrackView = new DrumTrackView(this);
		drumKitView = new DrumKitView(this);

		LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,
				1.0f);
		//drumTrackView.setLayoutParams(layoutParams);
		drumKitView.setLayoutParams(layoutParams);

		LinearLayout linearLayout = new LinearLayout(this);

		HorizontalScrollView horizontalScrollView = new HorizontalScrollView(this);
		horizontalScrollView.addView(drumTrackView);

		ScrollView horizontalScrollView2 = new ScrollView(this);
		horizontalScrollView2.addView(drumKitView);

		linearLayout.addView(horizontalScrollView);
		linearLayout.addView(horizontalScrollView2);

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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.drum_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
			case R.id.drum_menu_item_delete:
				drumTrackView.resetRowInDrumTrackView();
				break;
			default:
				return super.onOptionsItemSelected(item);
		}
		return true;

	}

}