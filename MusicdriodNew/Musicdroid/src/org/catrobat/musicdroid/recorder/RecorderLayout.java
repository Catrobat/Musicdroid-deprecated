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
package org.catrobat.musicdroid.recorder;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import org.catrobat.musicdroid.R;
import org.catrobat.musicdroid.RecorderActivity;
import org.catrobat.musicdroid.tools.StringFormatter;

public class RecorderLayout extends Handler {
	private Context context = null;
	private TextView filenameTextView = null;
	private ImageButton recordImageButton = null;
	private ImageButton playImageButton = null;
	private View progressBarView = null;
	private TextView recordDurationTextView = null;
	private RelativeLayout progressBarBox = null;
	private RelativeLayout addToSoundMixerBox = null;
	private boolean soundRecorded = false;
	private boolean inStateRecording = false;
	private boolean inStatePlaying = false;
	private int pixelPerSecond = 0;

	public RecorderLayout(Context context) {
		init(context);
	}

	private void init(Context context) {
		this.context = context;

		filenameTextView = (TextView) ((RecorderActivity) context).findViewById(R.id.microphone_filename);
		recordImageButton = (ImageButton) ((RecorderActivity) context).findViewById(R.id.microphone_record_button);
		recordDurationTextView = (TextView) ((RecorderActivity) context).findViewById(R.id.microphone_duration_text);
		playImageButton = (ImageButton) ((RecorderActivity) context).findViewById(R.id.microphone_play_button);
		progressBarView = ((RecorderActivity) context).findViewById(R.id.microphone_progress_bar);
		progressBarBox = (RelativeLayout) ((RecorderActivity) context)
				.findViewById(R.id.microphone_progress_bar_box);
		addToSoundMixerBox = (RelativeLayout) ((RecorderActivity) context)
				.findViewById(R.id.microphone_add_to_sound_mixer_box);

		reorderToRecordLayout();

		RecorderOnClickListener onClickListener = new RecorderOnClickListener((RecorderActivity) context, this);
		filenameTextView.setOnLongClickListener(onClickListener);
		recordImageButton.setColorFilter(Color.RED);
		recordImageButton.setOnClickListener(onClickListener);
		playImageButton.setOnClickListener(onClickListener);
		addToSoundMixerBox.setOnClickListener(onClickListener);
		recordDurationTextView.setText("00:00");
	}

	@Override
	public void handleMessage(Message msg) {
		Bundle data = msg.getData();
		if (data.containsKey("duration")) {
			int duration = data.getInt("duration");
			if (recordDurationTextView != null)
				recordDurationTextView.setText(StringFormatter.durationStringFromInt(duration));
		} else if (data.containsKey("trackposition")) {
			int position = data.getInt("trackposition");
			LayoutParams params = (LayoutParams) progressBarView.getLayoutParams();
			params.width = pixelPerSecond * position;
			progressBarView.setLayoutParams(params);
		}
	}

	public void updateLayoutOnRecordStart() {
		inStateRecording = true;
		if (playImageButton.getVisibility() == View.VISIBLE)
			reorderToRecordLayout();

		recordImageButton.setImageDrawable(context.getResources().getDrawable(R.drawable.pause_button));
		recordImageButton.setColorFilter(Color.WHITE);
	}

	public void updateLayoutOnRecordStop() {
		inStateRecording = false;
		recordImageButton.setImageDrawable(context.getResources().getDrawable(R.drawable.record_button));
		recordImageButton.setColorFilter(Color.RED);
		soundRecorded = true;
		reorderToPlayLayout();
	}

	public void updateLayoutOnPlay() {
		inStatePlaying = true;
		playImageButton.setImageDrawable(context.getResources().getDrawable(R.drawable.pause_button));
	}

	public void updateLayoutOnPause() {
		inStatePlaying = false;
		playImageButton.setImageDrawable(context.getResources().getDrawable(R.drawable.play_button));
	}

	public void handlePlaySoundComplete() {
		inStatePlaying = false;
		playImageButton.setImageDrawable(context.getResources().getDrawable(R.drawable.play_button));
	}

	public void resetLayoutToRecord() {
		inStateRecording = false;
		recordImageButton.setImageDrawable(context.getResources().getDrawable(R.drawable.record_button));
		recordImageButton.setColorFilter(Color.RED);
		if (!recordDurationTextView.getText().equals("00:00"))
			reorderToPlayLayout();
	}

	public void setDurationText(int duration) {
		recordDurationTextView.setText("" + duration);
	}

	public void setTrackDuration(int duration) {
		int width = progressBarBox.getWidth();
		pixelPerSecond = width / duration;
		Log.i("RecorderLayout", "setTrackDuration: width = " + width + " duration = " + duration);
	}

	private void reorderToRecordLayout() {
		addToSoundMixerBox.setVisibility(View.INVISIBLE);
		progressBarView.setVisibility(View.GONE);
		progressBarBox.setVisibility(View.GONE);
		playImageButton.setVisibility(View.GONE);

		((LayoutParams) recordImageButton.getLayoutParams()).addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
		((LayoutParams) recordImageButton.getLayoutParams()).addRule(RelativeLayout.CENTER_IN_PARENT);
	}

	private void reorderToPlayLayout() {
		addToSoundMixerBox.setVisibility(View.VISIBLE);
		progressBarView.setVisibility(View.VISIBLE);
		progressBarBox.setVisibility(View.VISIBLE);
		playImageButton.setVisibility(View.VISIBLE);

		((LayoutParams) recordImageButton.getLayoutParams()).addRule(RelativeLayout.CENTER_IN_PARENT, 0);
		((LayoutParams) recordImageButton.getLayoutParams()).addRule(RelativeLayout.ALIGN_PARENT_LEFT);
	}

	public void setFilename(String filename) {
		if (filenameTextView == null)
			return;
		filenameTextView.setText(filename);
	}

	public void reset() {
		recordDurationTextView = null;
	}

	public boolean isSoundRecorded() {
		return soundRecorded;
	}

	public boolean inStatePlaying() {
		return inStatePlaying;
	}

	public boolean inStateRecording() {
		return inStateRecording;
	}
}
