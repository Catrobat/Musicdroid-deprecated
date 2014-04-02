package org.catrobat.musicdroid.instruments.drums;

import android.graphics.Color;
import android.widget.GridLayout;

import org.catrobat.musicdroid.R;

import java.util.ArrayList;

public class DrumKitView extends GridLayout {
	private int NUM_COLUMNS = 4;
	private int NUM_ROWS = 6;
	private int DRUM_SET_SIZE = 8;

	private ArrayList<DrumKitPartView> drumSetList;
	private DrumActivity drumActivity;

	public DrumKitView(DrumActivity drumActivity) {
		super(drumActivity);
		this.drumActivity = drumActivity;

		drumSetList = new ArrayList<DrumKitPartView>();
		this.setColumnCount(NUM_COLUMNS);
		this.setRowCount(NUM_ROWS);

		initializeDrumKit();
		this.setVerticalScrollBarEnabled(true);

		this.setBackgroundColor(Color.RED);

	}

	private void initializeDrumKit() {

		drumSetList.add(new DrumKitPartView(drumActivity, new DrumKitPart("Crash", R.drawable.crash)));
		drumSetList.add(new DrumKitPartView(drumActivity, new DrumKitPart("Tom 1", R.drawable.upper_tom)));
		drumSetList.add(new DrumKitPartView(drumActivity, new DrumKitPart("Tom 2", R.drawable.upper_tom)));
		drumSetList.add(new DrumKitPartView(drumActivity, new DrumKitPart("Ride", R.drawable.ride)));
		drumSetList.add(new DrumKitPartView(drumActivity, new DrumKitPart("HitHat", R.drawable.hit_hat)));
		drumSetList.add(new DrumKitPartView(drumActivity, new DrumKitPart("Base Drum", R.drawable.base_drum)));
		drumSetList.add(new DrumKitPartView(drumActivity, new DrumKitPart("Tom 3", R.drawable.lower_tom)));
		drumSetList.add(new DrumKitPartView(drumActivity, new DrumKitPart("SnareDrum", R.drawable.snare_drum)));

		setDefaultPositions(5, 6);

		for (int i = 0; i < DRUM_SET_SIZE; i++) {
			addView(drumSetList.get(i));
		}

	}

	private void setDefaultPositions(int baseDrumPos, int tom3Pos) {
		GridLayout.LayoutParams baseDrumLayoutParams = new GridLayout.LayoutParams();
		baseDrumLayoutParams.rowSpec = GridLayout.spec(1, 2);
		baseDrumLayoutParams.columnSpec = GridLayout.spec(1, 2);
		//baseDrumLayoutParams.setGravity(Gravity.FILL_HORIZONTAL | Gravity.FILL_VERTICAL);

		GridLayout.LayoutParams tom3LayoutParams = new GridLayout.LayoutParams();
		tom3LayoutParams.rowSpec = GridLayout.spec(1, 2);

		drumSetList.get(tom3Pos).setLayoutParams(tom3LayoutParams);
		drumSetList.get(baseDrumPos).setLayoutParams(baseDrumLayoutParams);

	}

	public void replaceDrumKitPartOfDrumSet(int drumPosition, DrumKitPart drumKitPart) {

		drumSetList.add(drumPosition, new DrumKitPartView(drumActivity, drumKitPart));
	}

}