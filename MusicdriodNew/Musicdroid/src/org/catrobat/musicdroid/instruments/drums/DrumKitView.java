package org.catrobat.musicdroid.instruments.drums;

import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;

public class DrumKitView extends GridLayout implements View.OnTouchListener {

	private ImageView imageViewDrumSet;
	private ImageView imageViewDrumSetMask;

	public DrumKitView(DrumActivity drumActivity) {
		super(drumActivity);
		initComponents();
		this.setColumnCount(4);
		this.setRowCount(4);

		this.setBackgroundColor(Color.RED);
		DrumKitPartView dkpv1 = new DrumKitPartView(drumActivity);
		Button button1 = new Button(getContext());
		Button button2 = new Button(getContext());

		Button button3 = new Button(getContext());

		Button button4 = new Button(getContext());

		Button button5 = new Button(getContext());

		LayoutParams layoutParams = new GridLayout.LayoutParams();
		layoutParams.columnSpec = GridLayout.spec(0, 2);
		button1.setLayoutParams(layoutParams);

		button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DrumActivity drumActivity = (DrumActivity) getContext();
				drumActivity.addDrumEvent(new DrumEvent("B1"));
			}

		});

		this.addView(button1);
		this.addView(button2);
		this.addView(button3);
		this.addView(button4);
		this.addView(button5);
		this.addView(dkpv1);

	}

	public void initComponents() {

	}

	@Override
	public boolean onTouch(View v, MotionEvent ev) {
		return false;
	}
}