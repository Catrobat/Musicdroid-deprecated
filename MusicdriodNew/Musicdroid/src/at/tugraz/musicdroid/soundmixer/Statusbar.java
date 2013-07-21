/*******************************************************************************
 * Catroid: An on-device visual programming system for Android devices
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
 *  http://www.catroid.org/catroid/licenseadditionalterm
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU Affero General Public License for more details.
 * 
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package at.tugraz.musicdroid.soundmixer;

import android.app.Activity;
import android.content.Context;
import android.database.Observable;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;
import at.tugraz.musicdroid.R;
import at.tugraz.musicdroid.animation.HighlightAnimation;

public class Statusbar extends Observable implements OnTouchListener {
	public static Statusbar instance = null;
	private ImageButton playButton;
	private ImageButton rewindButton;
	private ImageView metronomLightImageView;
	protected Boolean displayPlayButton;
	protected Context context;

	public static Statusbar getInstance() {
		if (instance == null) {
			instance = new Statusbar();
		}
		return instance;
	}

	public Statusbar() {
	}

	public void initStatusbar(Context c) {
		this.context = c;
		displayPlayButton = true;

		playButton = (ImageButton) ((Activity) context)
				.findViewById(R.id.btn_play);
		playButton.setOnTouchListener(this);

		rewindButton = (ImageButton) ((Activity) context)
				.findViewById(R.id.btn_rewind);
		((RelativeLayout.LayoutParams) playButton.getLayoutParams())
				.addRule(RelativeLayout.CENTER_HORIZONTAL);
		((RelativeLayout.LayoutParams) rewindButton.getLayoutParams()).addRule(
				RelativeLayout.LEFT_OF, playButton.getId());
		rewindButton.setOnTouchListener(this);
		rewindButton.setVisibility(View.INVISIBLE);

		metronomLightImageView = (ImageView) ((Activity) context)
				.findViewById(R.id.metronom_light);
		metronomLightImageView
				.setColorFilter(R.color.abs__background_holo_dark);
		// ADD UNDO AND REDO SUPPORT
	}

	public void modifyStatusbarForRecorderActivity() {
		playButton.setVisibility(View.GONE);
		rewindButton.setVisibility(View.GONE);
		LayoutParams lp = (LayoutParams) metronomLightImageView
				.getLayoutParams();
		lp.addRule(RelativeLayout.CENTER_IN_PARENT);
	}

	@Override
	public boolean onTouch(View view, MotionEvent event) {
		switch (view.getId()) {
		case R.id.btn_play:
			onPlayTouch(event);
			return true;
		case R.id.btn_rewind:
			onRewindTouch(event);
			return true;
		default:
			return false;
		}
	}

	private void onPlayTouch(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (displayPlayButton) {
				playButton.setImageResource(R.drawable.pause_button);
				displayPlayButton = false;
				rewindButton.setVisibility(View.VISIBLE);
				if (!SoundMixer.getInstance().playAllSoundsInSoundmixer()) {
					playButton.setImageResource(R.drawable.play_button);
					Toast.makeText(
							((Activity) context).getApplicationContext(),
							R.string.toast_empty_soundmixer, Toast.LENGTH_LONG)
							.show();
					HighlightAnimation.getInstance().highlightViewAnimation(
							((Activity) context).findViewById(R.id.btn_add));
					displayPlayButton = true;
				}
			} else {
				playButton.setImageResource(R.drawable.play_button);
				SoundMixer.getInstance().stopAllSoundsInSoundmixer();

				displayPlayButton = true;
			}
		}
	}

	private void onRewindTouch(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			SoundMixer.getInstance().rewind();
			rewindButton.setVisibility(View.INVISIBLE);
		}
	}

	public Boolean getDisplayPlayButton() {
		return displayPlayButton;
	}

	public ImageView getMetronomLight() {
		return metronomLightImageView;
	}

}
