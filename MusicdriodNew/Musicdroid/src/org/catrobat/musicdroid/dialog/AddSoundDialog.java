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
package org.catrobat.musicdroid.dialog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;

import org.catrobat.musicdroid.MainActivity;
import org.catrobat.musicdroid.R;
import org.catrobat.musicdroid.RecorderActivity;
import org.catrobat.musicdroid.instruments.piano.PianoActivity;
import org.catrobat.musicdroid.soundtracks.SoundTrackDrums;
import org.catrobat.musicdroid.soundtracks.SoundTrackView;
import org.catrobat.musicdroid.types.SoundType;

public final class AddSoundDialog extends BaseDialog implements OnItemClickListener, OnItemLongClickListener {

	private static AddSoundDialog instance;
	private MainActivity parent;
	private AddSoundAdapter addSoundButtonAdapter;

	private AddSoundDialog(Context context) {
		super(context);
		parent = (MainActivity) context;
		addSoundButtonAdapter = new AddSoundAdapter(context, false);
	}

	public static AddSoundDialog getInstance() {
		if (instance == null) {
			throw new IllegalStateException("Error: Not initialized");
		}
		return instance;
	}

	//FIXME bad practice
	public static void init(MainActivity mainActivity) {
		instance = new AddSoundDialog(mainActivity);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_sound_menu);
		setTitle(R.string.dialog_add_sound_title);
		setCanceledOnTouchOutside(true);
		GridView gridView = (GridView) findViewById(R.id.gridview_add_sound_menu);
		gridView.setAdapter(addSoundButtonAdapter);
		gridView.setOnItemClickListener(this);
		gridView.setOnItemLongClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View button, int position, long id) {
		SoundType toolType = addSoundButtonAdapter.getSoundType(position);

		switch (toolType) {
			case DRUMS:
				SoundTrackDrums stvd = new SoundTrackDrums();
				parent.addSoundTrack(new SoundTrackView(parent, stvd));
				break;
			case PIANO:
				parent.startActivity(new Intent(parent, PianoActivity.class));
				// parent.startActivity(new Intent(parent, DrawTrackActivity.class));
				// SoundTrackPiano stvp = new SoundTrackPiano();
				// parent.addSoundTrack(new SoundTrackView(parent, stvp));
				break;
			case MIC:
				// SoundTrackMic stvm = new SoundTrackMic();
				// parent.addSoundTrack(new SoundTrackView(parent, stvm));
				Intent intent = new Intent(parent, RecorderActivity.class);
				parent.startActivityForResult(intent, 1);
				break;
			default:
				break;
		}
		dismiss();
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> adapterView, View button, int position, long id) {
		// Display help message
		return true;
	}
}
