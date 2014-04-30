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
package org.catrobat.musicdroid.soundtracks;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.catrobat.musicdroid.MainActivity;
import org.catrobat.musicdroid.R;
import org.catrobat.musicdroid.SoundManager;
import org.catrobat.musicdroid.soundmixer.SoundMixer;
import org.catrobat.musicdroid.tools.DeviceInfo;
import org.catrobat.musicdroid.tools.StringFormatter;

public class SoundTrackView extends RelativeLayout implements OnClickListener, View.OnTouchListener {
	public static final int MINIMAL_WIDTH = 280;
	public static final int EXPANDED_WIDTH = 400;
	final OnClickListener soundTrackViewOnClickListener = new OnClickListener() {
		@Override
		public void onClick(final View v) {
			switch (v.getId()) {
				case R.id.volume_button:
					handleOnClickVolumeButton();
					break;
				case R.id.play_button:
					handleOnClickPlayButton();
					break;
				case R.id.lock_button:
					handleOnClickLockButton();
					break;
				case R.id.expand_button:
					handleOnClickExpandButton();
					break;
				default:
					break;
			}
		}
	};
	public boolean moveableLocked = true;
	public boolean displayPlayButton = true;
	public boolean isMuted = false;
	public boolean collapse = false;
	protected ImageView soundTypeImageView = null;
	protected View verticalSeperatorView = null;
	protected TextView soundtrackDescriptionTextView = null;
	protected View horizontalSeperatorView = null;
	protected ImageButton playImageButton = null;
	protected ImageButton lockImageButton = null;
	protected ImageButton volumeImageButton = null;
	protected ImageButton expandImageButton = null;
	protected RelativeLayout soundTrackViewSubMenuLayout = null;
	private Context context = null;
	private SoundTrack soundTrack = null;
	private int xDelta;
	private boolean collapseCompletely = false;

	public SoundTrackView(Context context, SoundTrack st) {
		super(context);
		this.context = context;
		soundTrack = st;

		LayoutInflater inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.sound_track_layout, this);

		initSoundTrackView();
	}

	public SoundTrackView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void initSoundTrackView() {
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(computeWidthRelativeToDuration(),
				DeviceInfo.getScreenHeight(context) / 6);
		setLayoutParams(layoutParams);
		setBackgroundColor(getResources().getColor(soundTrack.getType().getColorResource()));
		setOnClickListener(this);
		setOnTouchListener(this);
		setRessources(soundTrack.getType().getImageResource(), soundTrack.getName(), soundTrack.getDuration());
		setFocusableInTouchMode(true);

		if (collapse) {
			collapse();
		}
	}

	private int computeWidthRelativeToDuration() {
		int duration = soundTrack.getDuration();
		int width = duration * SoundMixer.getInstance().getPixelPerSecond();
		if (width < 280) {
			collapse = true;
			if (width < 100) {
				collapseCompletely = true;
			}
		}
		return width;
	}

	protected void setRessources(int id, String name, int duration) {
		soundTypeImageView = (ImageView) findViewById(R.id.img_sound_track_type);
		soundTrackViewSubMenuLayout = (RelativeLayout) findViewById(R.id.sound_track_view_sub_layout);
		verticalSeperatorView = findViewById(R.id.vertical_seperator);
		soundtrackDescriptionTextView = (TextView) findViewById(R.id.sound_track_text);
		horizontalSeperatorView = findViewById(R.id.horizontal_seperator);
		playImageButton = (ImageButton) findViewById(R.id.play_button);
		volumeImageButton = (ImageButton) findViewById(R.id.volume_button);
		lockImageButton = (ImageButton) findViewById(R.id.lock_button);
		expandImageButton = (ImageButton) findViewById(R.id.expand_button);

		RelativeLayout.LayoutParams vp = new RelativeLayout.LayoutParams(
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		vp.addRule(RelativeLayout.CENTER_VERTICAL);
		soundTypeImageView.setLayoutParams(vp);
		soundTypeImageView.setImageResource(id);

		soundtrackDescriptionTextView.setText(name + " | " + StringFormatter.durationStringFromInt(duration));

		volumeImageButton.setOnClickListener(soundTrackViewOnClickListener);
		playImageButton.setOnClickListener(soundTrackViewOnClickListener);
		lockImageButton.setOnClickListener(soundTrackViewOnClickListener);
		expandImageButton.setOnClickListener(soundTrackViewOnClickListener);

		soundTypeImageView.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				((MainActivity) getContext()).startActionMode(getId(), soundTrack);
				return true;
			}
		});
	}

	private void handleOnClickVolumeButton() {
		if (isMuted) {
			soundTrack.setVolume(1);
			volumeImageButton.setImageResource(R.drawable.volume_button);
			isMuted = false;
		} else {
			soundTrack.setVolume(0);
			volumeImageButton.setImageResource(R.drawable.volume_button_mute);
			isMuted = true;
		}
	}

	private void handleOnClickPlayButton() {
		if (displayPlayButton) {
			displayPlayButton = false;
			playImageButton.setImageResource(R.drawable.pause_button_sound_track);
			Log.e("VOLUME: ", "" + soundTrack.getVolume());
			SoundManager.playSound(soundTrack.getSoundPoolId(), 1, soundTrack.getVolume());
		} else {
			displayPlayButton = true;
			playImageButton.setImageResource(R.drawable.play_button_sound_track);
			SoundManager.stopSound(soundTrack.getSoundPoolId());
		}
	}

	private void handleOnClickLockButton() {
		if (moveableLocked) {
			lockImageButton.setImageResource(R.drawable.lock_unlocked);
			moveableLocked = false;
		} else {
			lockImageButton.setImageResource(R.drawable.lock_locked);
			moveableLocked = true;
		}
	}

	private void handleOnClickExpandButton() {
		if (collapse) {
			expandImageButton.setImageResource(R.drawable.collapse_button);
			collapse = false;
			expand();
		} else {
			expandImageButton.setImageResource(R.drawable.expand_button);
			collapse = true;
			collapse();
		}
	}

	@Override
	public void onClick(View v) {
		Toast.makeText(getContext().getApplicationContext(), "You clicked - here's a Toast", Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onTouch(View view, MotionEvent event) {
		final int xTouchPosition = (int) event.getRawX();
		boolean ret = true;
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
			case MotionEvent.ACTION_DOWN:
				getParent().requestDisallowInterceptTouchEvent(true);
				RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
				xDelta = xTouchPosition - lParams.leftMargin;
				break;
			case MotionEvent.ACTION_MOVE:
				if (moveableLocked) {
					break;
				}

				getParent().requestDisallowInterceptTouchEvent(true);
				RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
				int oldMargin = layoutParams.leftMargin;
				int margin = xTouchPosition - xDelta;

				if (margin < 0) {
					margin = 0;
					// if(margin > helper.getScreenWidth()-layoutParams.width) margin =
					// helper.getScreenWidth()-layoutParams.width;
				}

				if (margin != oldMargin) {
					layoutParams.leftMargin = margin;
					int startPoint = SoundMixer.getInstance().getStartPointByPixel(margin);
					soundTrack.setStartPoint(startPoint);
					SoundMixer.getInstance().updateTimelineOnMove(getId(), margin, startPoint,
							soundTrack.getDuration());
					view.setLayoutParams(layoutParams);
				}
				ret = true;
				break;
		}
		// invalidate();
		return ret;
	}

	public void resizeTrack() {
		RelativeLayout.LayoutParams layoutParams = (LayoutParams) getLayoutParams();
		layoutParams.width = computeWidthRelativeToDuration();
		if (collapse) {
			collapse();
		}
		setLayoutParams(layoutParams);
	}

	private void collapse() {
		soundtrackDescriptionTextView.setVisibility(GONE);
		horizontalSeperatorView.setVisibility(GONE);
		playImageButton.setVisibility(GONE);
		lockImageButton.setVisibility(GONE);
		volumeImageButton.setVisibility(GONE);
		expandImageButton.setVisibility(VISIBLE);

		if (collapseCompletely) {
			verticalSeperatorView.setVisibility(GONE);
			soundTypeImageView.setVisibility(GONE);
		}

		RelativeLayout.LayoutParams layoutParams = (LayoutParams) getLayoutParams();
		layoutParams.width = computeWidthRelativeToDuration();
		setLayoutParams(layoutParams);
		// soundTrackViewSubMenuLayout.setBackgroundColor(soundTrack.getType().getColorResource());
	}

	private void expand() {
		soundtrackDescriptionTextView.setVisibility(VISIBLE);
		horizontalSeperatorView.setVisibility(VISIBLE);
		playImageButton.setVisibility(VISIBLE);
		lockImageButton.setVisibility(VISIBLE);
		volumeImageButton.setVisibility(VISIBLE);
		expandImageButton.setVisibility(VISIBLE);
		verticalSeperatorView.setVisibility(VISIBLE);
		soundTypeImageView.setVisibility(VISIBLE);
		RelativeLayout.LayoutParams layoutParams = (LayoutParams) getLayoutParams();
		layoutParams.width = EXPANDED_WIDTH;
		setLayoutParams(layoutParams);
	}

	private void expandToFullSize() {
		soundtrackDescriptionTextView.setVisibility(VISIBLE);
		horizontalSeperatorView.setVisibility(VISIBLE);
		playImageButton.setVisibility(VISIBLE);
		lockImageButton.setVisibility(VISIBLE);
		volumeImageButton.setVisibility(VISIBLE);
		expandImageButton.setVisibility(GONE);
	}

	public void resize() {
		RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) getLayoutParams();
		lParams.width = computeWidthRelativeToDuration();
		lParams.height = DeviceInfo.getScreenHeight(context) / 6;
		setLayoutParams(lParams);

		if (lParams.width < MINIMAL_WIDTH) {
			collapse();
		}
		if (expandImageButton.getVisibility() == View.VISIBLE && lParams.width >= MINIMAL_WIDTH) {
			expandToFullSize();
		}
	}

	public void disableView() {
		setBackgroundColor(Color.GRAY);
		soundTypeImageView.setColorFilter(R.color.foreground_disabled_holo_dark);
		playImageButton.setColorFilter(R.color.foreground_disabled_holo_dark);
		lockImageButton.setColorFilter(R.color.foreground_disabled_holo_dark);
		soundtrackDescriptionTextView.setTextColor(getResources().getColor(R.color.foreground_disabled_holo_dark));
		horizontalSeperatorView.setBackgroundColor(getResources().getColor(R.color.foreground_disabled_holo_dark));
		verticalSeperatorView.setBackgroundColor(getResources().getColor(R.color.foreground_disabled_holo_dark));
		playImageButton.setEnabled(false);
		lockImageButton.setEnabled(false);
	}

	public void enableView() {
		setBackgroundColor(getResources().getColor(soundTrack.getType().getColorResource()));
		soundTypeImageView.setColorFilter(Color.WHITE);
		playImageButton.setColorFilter(Color.WHITE);
		lockImageButton.setColorFilter(Color.WHITE);
		soundtrackDescriptionTextView.setTextColor(getResources().getColor(R.color.custom_background_color));
		horizontalSeperatorView.setBackgroundColor(Color.WHITE);
		verticalSeperatorView.setBackgroundColor(Color.WHITE);
		playImageButton.setEnabled(true);
		lockImageButton.setEnabled(true);
	}

	public SoundTrack getSoundTrack() {
		return soundTrack;
	}

	public void alignTrack(int alignment, int alignTo) {
		RelativeLayout.LayoutParams params = (LayoutParams) this.getLayoutParams();
		params.addRule(alignment, alignTo);
		this.setLayoutParams(params);
	}

}
