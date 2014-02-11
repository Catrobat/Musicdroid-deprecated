package org.catrobat.musicdroid.instruments.drums;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import org.catrobat.musicdroid.R;
import org.catrobat.musicdroid.instruments.Instrument;
import org.catrobat.musicdroid.tools.ColorInspectTool;

public class DrumView extends FrameLayout implements View.OnTouchListener {

	private ImageView imageViewDrumSet;
	private ImageView imageViewDrumSetMask;

	public DrumView(Instrument drumActivity) {
		super(drumActivity);
		initComponents();
	}

	public void initComponents() {

		imageViewDrumSet = new ImageView(getContext());
		imageViewDrumSet.setImageResource(R.drawable.ds_main);

		imageViewDrumSetMask = new ImageView(getContext());
		imageViewDrumSetMask.setImageResource(R.drawable.ds_mask);

		imageViewDrumSetMask.setVisibility(INVISIBLE);

		if (imageViewDrumSet != null) {

			imageViewDrumSet.setOnTouchListener(this);
		}

		addView(imageViewDrumSet);
		addView(imageViewDrumSetMask);
	}

	@Override
	public boolean onTouch(View v, MotionEvent ev) {
		boolean handledHere = false;

		final int xEventPos = (int) ev.getX();
		final int yEventPos = (int) ev.getY();
		int nextImage = -1;

		switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:

				int touchColor = getAreaColor(xEventPos, yEventPos);
				ColorInspectTool colorInspector = new ColorInspectTool();

				int tolerance = 25; // TODO: Tolerance

				if (colorInspector.isMatch(Color.RED, touchColor, tolerance)) {
					nextImage = R.drawable.ds_red;
					onDrumClicked(new DrumEvent("RE", 1, Color.RED));
					// TODO: PLAY SOUND / animate
				} else if (colorInspector.isMatch(Color.BLUE, touchColor, tolerance)) {
					nextImage = R.drawable.ds_blue;
					onDrumClicked(new DrumEvent("BL", 1, Color.BLUE));
					// TODO: PLAY SOUND / animate
				} else if (colorInspector.isMatch(Color.YELLOW, touchColor, tolerance)) {
					nextImage = R.drawable.ds_yellow;
					onDrumClicked(new DrumEvent("YL", 1, Color.YELLOW));
					// TODO: PLAY SOUND / animate
				} else if (colorInspector.isMatch(Color.WHITE, touchColor, tolerance)) {
					// TODO: PLAY SOUND / animate
				} else if (colorInspector.isMatch(Color.CYAN, touchColor, tolerance)) {
					nextImage = R.drawable.ds_cyan;
					onDrumClicked(new DrumEvent("CY", 1, Color.CYAN));
					// TODO: PLAY SOUND / animate
				} else if (colorInspector.isMatch(Color.MAGENTA, touchColor, tolerance)) {
					nextImage = R.drawable.ds_magenta;
					onDrumClicked(new DrumEvent("MA", 1, Color.MAGENTA));
					// TODO: PLAY SOUND / animate
				} else if (colorInspector.isMatch(Color.BLACK, touchColor, tolerance)) {
					nextImage = R.drawable.ds_black;
					onDrumClicked(new DrumEvent("BL", 1, Color.BLACK));
					// TODO: PLAY SOUND / animate
				} else if (colorInspector.isMatch(Color.GREEN, touchColor, tolerance)) {
					nextImage = R.drawable.ds_green;
					onDrumClicked(new DrumEvent("GR", 1, Color.GREEN));
					// TODO: PLAY SOUND / animate
				} else if (colorInspector.isMatch(0x996633, touchColor, tolerance)) {
					nextImage = R.drawable.ds_brown;
					onDrumClicked(new DrumEvent("BR", 1, 0x996633));
					// TODO: PLAY SOUND / animate
				} else if (colorInspector.isMatch(0xFF9900, touchColor, tolerance)) {
					nextImage = R.drawable.ds_orange;
					onDrumClicked(new DrumEvent("OR", 1, 0xFF9900));
					// TODO: PLAY SOUND / animate
				}
				handledHere = true;

				break;

			//TODO: 2 drum-parts simultaneously touched

			case MotionEvent.ACTION_UP:
				Log.d("Drum", " UP");

				nextImage = R.drawable.ds_main;

				// TODO: Drum Animation
				handledHere = true;
				break;
			default:
				handledHere = false;
		}

		if (handledHere) {
			if (nextImage > 0) {
				imageViewDrumSet.setImageResource(nextImage);
				imageViewDrumSet.setTag(nextImage);
			}
		}
		return handledHere;
	}

	private void onDrumClicked(DrumEvent drumEvent) {
		DrumsActivity drumsActivity = (DrumsActivity) getContext();
		drumsActivity.addDrumEvent(drumEvent);
	}

	public int getAreaColor(int x, int y) {
		imageViewDrumSetMask.setDrawingCacheEnabled(true);
		Bitmap maskPixelColors = Bitmap.createBitmap(imageViewDrumSetMask.getDrawingCache());
		imageViewDrumSetMask.setDrawingCacheEnabled(false);
		return maskPixelColors.getPixel(x, y);
	}
}
