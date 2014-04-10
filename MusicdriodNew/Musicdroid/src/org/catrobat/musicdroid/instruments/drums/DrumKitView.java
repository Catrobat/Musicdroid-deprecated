package org.catrobat.musicdroid.instruments.drums;

import android.graphics.Color;
import android.widget.GridLayout;

import org.catrobat.musicdroid.R;

import java.util.ArrayList;

public class DrumKitView extends GridLayout {
	private static final int NUM_COLUMNS = 5;
	private static final int NUM_ROWS = 3;

	//private int NUM_COLUMNS = 5;
	//private int NUM_ROWS = 3;

	private ArrayList<DrumKitPartView> drumSetList;
	private DrumActivity drumActivity;

	public DrumKitView(DrumActivity drumActivity) {
		super(drumActivity);
		this.drumActivity = drumActivity;

		drumSetList = new ArrayList<DrumKitPartView>();

		this.setColumnCount(getNumColumns());
		this.setRowCount(getNumRows());

		initializeDrumKit();

		this.setBackgroundColor(Color.RED);

	}

	private void initializeDrumKit() {

		drumSetList.add(new DrumKitPartView(drumActivity, new DrumKitPart("Crash", R.drawable.crash, Color.YELLOW)));
		drumSetList.add(new DrumKitPartView(drumActivity, new DrumKitPart("Tom 1", R.drawable.upper_tom, Color.CYAN)));
		drumSetList.add(new DrumKitPartView(drumActivity, new DrumKitPart("Tom 2", R.drawable.upper_tom, Color.CYAN)));
		drumSetList.add(new DrumKitPartView(drumActivity, new DrumKitPart("Ride", R.drawable.ride, Color.MAGENTA)));
		drumSetList.add(new DrumKitPartView(drumActivity, new DrumKitPart("HitHat", R.drawable.hit_hat, Color.YELLOW)));

		drumSetList.add(new DrumKitPartView(drumActivity,
				new DrumKitPart("SnareDrum", R.drawable.snare_drum, Color.RED)));
		drumSetList.add(new DrumKitPartView(drumActivity, new DrumKitPart("Crash", R.drawable.crash, Color.YELLOW)));
		drumSetList.add(new DrumKitPartView(drumActivity, new DrumKitPart("Tom 1", R.drawable.upper_tom, Color.CYAN)));
		drumSetList.add(new DrumKitPartView(drumActivity, new DrumKitPart("Tom 2", R.drawable.upper_tom, Color.CYAN)));
		drumSetList.add(new DrumKitPartView(drumActivity, new DrumKitPart("Ride", R.drawable.ride, Color.MAGENTA)));
		drumSetList.add(new DrumKitPartView(drumActivity, new DrumKitPart("HitHat", R.drawable.hit_hat, Color.YELLOW)));
		drumSetList.add(new DrumKitPartView(drumActivity, new DrumKitPart("Tom 1", R.drawable.upper_tom, Color.CYAN)));
		drumSetList.add(new DrumKitPartView(drumActivity, new DrumKitPart("Tom 2", R.drawable.upper_tom, Color.CYAN)));
		drumSetList.add(new DrumKitPartView(drumActivity, new DrumKitPart("Ride", R.drawable.ride, Color.MAGENTA)));
		drumSetList.add(new DrumKitPartView(drumActivity, new DrumKitPart("HitHat", R.drawable.hit_hat, Color.YELLOW)));

		for (int i = 0; i < (getNumRows() * getNumColumns()); i++) {
			addView(drumSetList.get(i));
		}

	}

	public void replaceDrumKitPartOfDrumSet(int drumPosition, DrumKitPart drumKitPart) {

		drumSetList.add(drumPosition, new DrumKitPartView(drumActivity, drumKitPart));
	}

	public static int getNumColumns() {
		return NUM_COLUMNS;
	}

	public static int getNumRows() {
		return NUM_ROWS;
	}

}