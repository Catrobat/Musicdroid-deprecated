package org.catrobat.musicdroid.drums;

import android.app.Activity;
import android.content.Context;
import android.database.Observable;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import org.catrobat.musicdroid.DrumsActivity;
import org.catrobat.musicdroid.R;

public class StatusbarDrums extends Observable implements OnTouchListener {
	public static StatusbarDrums instance = null;
	private ImageButton playLoopButton;
	protected Boolean displayPlayButton;
	protected Context context;

	public static StatusbarDrums getInstance() {
		if (instance == null) {
			instance = new StatusbarDrums();
		}
		return instance;
	}

	public StatusbarDrums() {
	}

	public void initStatusbar(Context c) {
		this.context = c;
		displayPlayButton = true;

		playLoopButton = (ImageButton) ((Activity) context).findViewById(R.id.btn_play_loop);
		playLoopButton.setOnTouchListener(this);

		((RelativeLayout.LayoutParams) playLoopButton.getLayoutParams()).addRule(RelativeLayout.CENTER_HORIZONTAL);
	}

	@Override
	public boolean onTouch(View view, MotionEvent event) {
		switch (view.getId()) {
			case R.id.btn_play_loop:
				onPlayTouch(event);
				return true;
			default:
				return false;
		}
	}

	private void onPlayTouch(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (displayPlayButton) {
				playLoopButton.setImageResource(R.drawable.pause_button);
				((DrumsActivity) context).startPlayLoop();
				displayPlayButton = false;
			} else {
				playLoopButton.setImageResource(R.drawable.play_loop_button);
				((DrumsActivity) context).stopPlayLoop();
				displayPlayButton = true;
			}
		}
	}

	public Boolean getDisplayPlayButton() {
		return displayPlayButton;
	}

}
