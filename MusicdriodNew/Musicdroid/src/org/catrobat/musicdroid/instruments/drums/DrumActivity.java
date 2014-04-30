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

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;

import org.catrobat.musicdroid.R;
import org.catrobat.musicdroid.instruments.Instrument;
import org.catrobat.musicdroid.note.NoteEvent;

import java.util.ArrayList;

/**
 * @author musicdroid
 * 
 */

public class DrumActivity extends Instrument {

	//private DrumTrackView drumTrackView;
	//	private DrumKitView drumKitView;
	private ArrayList<DrumEvent> drumEventList;
	private int drumEventLength = 10;
	private static final float MENU_OFFSET = (float) 0.8;
	private ArrayList<Button> drumPartButtons;
	private ArrayList<DrumKitPart> drumKitParts;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LinearLayout ll = new LinearLayout(this);
		setContentView(R.layout.drum_kit_layout);

		Point size = new Point();
		getWindowManager().getDefaultDisplay().getSize(size);
		int buttonSizeWidth = size.x / DrumKitView.getNumColumns();
		int buttonSizeHeight = (int) ((size.y / DrumKitView.getNumRows()) * MENU_OFFSET);

		drumPartButtons = new ArrayList<Button>();
		drumKitParts = new ArrayList<DrumKitPart>();

		initDrumPartButtons();
		initDrumKitParts();

		setupDrumPartButtons();

		for (int i = 0; i < drumPartButtons.size(); i++) {
			//drumPartButtons.get(i).setLayoutParams(new LayoutParams(buttonSizeWidth, buttonSizeHeight));
		}

		//drumEventList = new ArrayList<DrumEvent>();
		/*
		 * //drumTrackView = new DrumTrackView(this);
		 * //drumKitView = new DrumKitView(this);
		 * 
		 * LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
		 * LayoutParams.MATCH_PARENT,
		 * 1.0f);
		 * //drumTrackView.setLayoutParams(layoutParams);
		 * //drumKitView.setLayoutParams(layoutParams);
		 * LinearLayout linearLayout = new LinearLayout(this);
		 * 
		 * // HorizontalScrollView horizontalScrollView = new HorizontalScrollView(this);
		 * // horizontalScrollView.addView(drumTrackView);
		 * 
		 * //ScrollView horizontalScrollView2 = new ScrollView(this);
		 * //horizontalScrollView2.addView(drumKitView);
		 * 
		 * //linearLayout.addView(horizontalScrollView);
		 * linearLayout.addView(drumKitView);
		 * 
		 * linearLayout.setOrientation(1);
		 * setContentView(linearLayout);
		 */
	}

	private void setupDrumPartButtons() {
		for (int i = 0; i < drumPartButtons.size(); i++) {
			drumPartButtons.get(i).setBackgroundResource(drumKitParts.get(i).getDrawableId());
		}

	}

	private void initDrumKitParts() {
		drumKitParts.add(new DrumKitPart("Crash", R.drawable.crash_1, Color.YELLOW));
		drumKitParts.add(new DrumKitPart("Crash", R.drawable.crash_1, Color.YELLOW));
		drumKitParts.add(new DrumKitPart("Crash", R.drawable.crash_1, Color.YELLOW));
		drumKitParts.add(new DrumKitPart("Crash", R.drawable.crash_1, Color.YELLOW));
		drumKitParts.add(new DrumKitPart("Crash", R.drawable.crash_1, Color.YELLOW));
		drumKitParts.add(new DrumKitPart("Crash", R.drawable.crash_1, Color.YELLOW));
		drumKitParts.add(new DrumKitPart("Crash", R.drawable.crash_1, Color.YELLOW));
		drumKitParts.add(new DrumKitPart("Crash", R.drawable.crash_1, Color.YELLOW));
		drumKitParts.add(new DrumKitPart("Crash", R.drawable.crash_1, Color.YELLOW));
		drumKitParts.add(new DrumKitPart("Crash", R.drawable.crash_1, Color.YELLOW));
		drumKitParts.add(new DrumKitPart("Crash", R.drawable.crash_1, Color.YELLOW));
		drumKitParts.add(new DrumKitPart("Crash", R.drawable.crash_1, Color.YELLOW));
		drumKitParts.add(new DrumKitPart("Crash", R.drawable.crash_1, Color.YELLOW));
		drumKitParts.add(new DrumKitPart("Crash", R.drawable.crash_1, Color.YELLOW));
		drumKitParts.add(new DrumKitPart("Crash", R.drawable.crash_1, Color.YELLOW));

	}

	public void initDrumPartButtons() {
		drumPartButtons.add((Button) findViewById(R.id.drum_kit_part_1_button));
		drumPartButtons.add((Button) findViewById(R.id.drum_kit_part_2_button));
		drumPartButtons.add((Button) findViewById(R.id.drum_kit_part_3_button));
		drumPartButtons.add((Button) findViewById(R.id.drum_kit_part_4_button));
		drumPartButtons.add((Button) findViewById(R.id.drum_kit_part_5_button));
		drumPartButtons.add((Button) findViewById(R.id.drum_kit_part_6_button));
		drumPartButtons.add((Button) findViewById(R.id.drum_kit_part_7_button));
		drumPartButtons.add((Button) findViewById(R.id.drum_kit_part_8_button));
		drumPartButtons.add((Button) findViewById(R.id.drum_kit_part_9_button));
		drumPartButtons.add((Button) findViewById(R.id.drum_kit_part_10_button));
		drumPartButtons.add((Button) findViewById(R.id.drum_kit_part_11_button));
		drumPartButtons.add((Button) findViewById(R.id.drum_kit_part_12_button));
		drumPartButtons.add((Button) findViewById(R.id.drum_kit_part_13_button));
		drumPartButtons.add((Button) findViewById(R.id.drum_kit_part_14_button));
		drumPartButtons.add((Button) findViewById(R.id.drum_kit_part_15_button));
	}

	@Override
	protected void doAfterAddNoteEvent(NoteEvent noteEvent) {
		//noteSheetView.redraw(getTrack());
	}

	public void addDrumEvent(DrumEvent drumEvent) {
		drumEvent.setEventLength(drumEventLength);
		drumEventList.add(drumEvent);
		//	drumTrackView.updateView(drumEvent);
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
				//	drumTrackView.resetRowInDrumTrackView();
				break;
			/*
			 * case R.id.drum_menu_item_18:
			 * drumEventLength = 10;
			 * break;
			 * case R.id.drum_menu_item_14:
			 * drumEventLength = 20;
			 * 
			 * break;
			 * case R.id.drum_menu_item_12:
			 * drumEventLength = 40;
			 * 
			 * break;
			 * case R.id.drum_menu_item_1:
			 * drumEventLength = 80;
			 * 
			 * break;
			 * case R.id.drum_menu_item_p:
			 * drumEventLength = (int) (drumEventLength * 1.5);
			 * break;
			 * case R.id.drum_menu_item_additional:
			 * break;
			 */
			default:
				return super.onOptionsItemSelected(item);
		}
		return true;

	}

}