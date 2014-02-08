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
		final int action = ev.getAction();

		final int xEventPos = (int) ev.getX();
		final int yEventPos = (int) ev.getY();
		int nextImage = -1;

		switch (action) {
			case MotionEvent.ACTION_DOWN:

				int touchColor = getAreaColor(xEventPos, yEventPos);
				ColorInspectTool colorInspector = new ColorInspectTool();

				int tolerance = 25; // TODO: Tolerance

				if (colorInspector.isMatch(Color.RED, touchColor, tolerance)) {
					nextImage = R.drawable.ds_red;
					Log.d("Drum", "RED");
					// TODO: PLAY SOUND / animate
				} else if (colorInspector.isMatch(Color.BLUE, touchColor, tolerance)) {
					nextImage = R.drawable.ds_blue;
					Log.d("Drum", "BLUE");

					// TODO: PLAY SOUND / animate
				} else if (colorInspector.isMatch(Color.YELLOW, touchColor, tolerance)) {
					nextImage = R.drawable.ds_yellow;
					// TODO: PLAY SOUND / animate
				} else if (colorInspector.isMatch(Color.WHITE, touchColor, tolerance)) {
					//nextImage = R.drawable.ds_mask;
					// TODO: PLAY SOUND / animate
				} else if (colorInspector.isMatch(Color.CYAN, touchColor, tolerance)) {
					nextImage = R.drawable.ds_cyan;
					// TODO: PLAY SOUND / animate
				} else if (colorInspector.isMatch(Color.MAGENTA, touchColor, tolerance)) {
					nextImage = R.drawable.ds_magenta;
					// TODO: PLAY SOUND / animate
				} else if (colorInspector.isMatch(Color.BLACK, touchColor, tolerance)) {
					nextImage = R.drawable.ds_black;
					// TODO: PLAY SOUND / animate
				} else if (colorInspector.isMatch(Color.GREEN, touchColor, tolerance)) {
					nextImage = R.drawable.ds_green;
					// TODO: PLAY SOUND / animate
				} else if (colorInspector.isMatch(0x996633, touchColor, tolerance)) {
					nextImage = R.drawable.ds_brown;
					// TODO: PLAY SOUND / animate
				} else if (colorInspector.isMatch(0xFF9900, touchColor, tolerance)) {
					nextImage = R.drawable.ds_orange;
					// TODO: PLAY SOUND / animate
				}
				handledHere = true;
				break;
			case MotionEvent.ACTION_POINTER_DOWN:
				touchColor = getAreaColor(xEventPos, yEventPos);
				colorInspector = new ColorInspectTool();

				tolerance = 25; // TODO: Tolerance

				if (colorInspector.isMatch(Color.RED, touchColor, tolerance)) {
					nextImage = R.drawable.ds_red;
					Log.d("Drum", "RED");
					// TODO: PLAY SOUND / animate
				} else if (colorInspector.isMatch(Color.BLUE, touchColor, tolerance)) {
					nextImage = R.drawable.ds_blue;
					Log.d("Drum", "BLUE");

					// TODO: PLAY SOUND / animate
				} else if (colorInspector.isMatch(Color.YELLOW, touchColor, tolerance)) {
					nextImage = R.drawable.ds_yellow;
					// TODO: PLAY SOUND / animate
				} else if (colorInspector.isMatch(Color.WHITE, touchColor, tolerance)) {
					//nextImage = R.drawable.ds_mask;
					// TODO: PLAY SOUND / animate
				} else if (colorInspector.isMatch(Color.CYAN, touchColor, tolerance)) {
					nextImage = R.drawable.ds_cyan;
					// TODO: PLAY SOUND / animate
				} else if (colorInspector.isMatch(Color.MAGENTA, touchColor, tolerance)) {
					nextImage = R.drawable.ds_magenta;
					// TODO: PLAY SOUND / animate
				} else if (colorInspector.isMatch(Color.BLACK, touchColor, tolerance)) {
					nextImage = R.drawable.ds_black;
					// TODO: PLAY SOUND / animate
				} else if (colorInspector.isMatch(Color.GREEN, touchColor, tolerance)) {
					nextImage = R.drawable.ds_green;
					// TODO: PLAY SOUND / animate
				} else if (colorInspector.isMatch(0x996633, touchColor, tolerance)) {
					nextImage = R.drawable.ds_brown;
					// TODO: PLAY SOUND / animate
				} else if (colorInspector.isMatch(0xFF9900, touchColor, tolerance)) {
					nextImage = R.drawable.ds_orange;
					// TODO: PLAY SOUND / animate
				}
				break;
			case MotionEvent.ACTION_UP:
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

	public int getAreaColor(int x, int y) {
		imageViewDrumSetMask.setDrawingCacheEnabled(true);
		Bitmap maskPixelColors = Bitmap.createBitmap(imageViewDrumSetMask.getDrawingCache());
		imageViewDrumSetMask.setDrawingCacheEnabled(false);
		return maskPixelColors.getPixel(x, y);
	}
}
