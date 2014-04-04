/**
 *  Catroid: An on-device visual programming system for Android devices
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
 *  http://developer.catrobat.org/license_additional_term
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.catrobat.musicdroid.instruments.piano;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

import org.catrobat.musicdroid.R;
import org.catrobat.musicdroid.instruments.Instrument;
import org.catrobat.musicdroid.note.Key;
import org.catrobat.musicdroid.note.NoteEvent;
import org.catrobat.musicdroid.note.NoteLength;
import org.catrobat.musicdroid.note.NoteName;
import org.catrobat.musicdroid.note.Project;
import org.catrobat.musicdroid.note.Track;
import org.catrobat.musicdroid.note.draw.NoteSheetView;
import org.catrobat.musicdroid.note.midi.MidiException;
import org.catrobat.musicdroid.note.midi.ProjectToMidiConverter;
import org.catrobat.musicdroid.soundplayer.SoundPlayer;

import java.io.File;
import java.io.IOException;

public class PianoActivity extends Instrument {

	private static final String TAG = PianoActivity.class.getSimpleName();
	private static final String RECORDS_DIRECTORY_NAME = "records";
	private static final String MIDI_DIRECTORY_NAME = "piano_midi_sounds";
	SoundPlayer soundPlayer;
	ProgressDialog progress;
	private NoteSheetView noteSheetView;
	private PianoView pianoView;
	private int beatsPerMinute = 100;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		noteSheetView = new NoteSheetView(this);
		pianoView = new PianoView(this);
		LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,
				1.0f);
		noteSheetView.setLayoutParams(layoutParams);
		pianoView.setLayoutParams(layoutParams);
		LinearLayout linearLayout = new LinearLayout(this);
		linearLayout.addView(noteSheetView);
		linearLayout.addView(pianoView);
		linearLayout.setOrientation(1);
		setContentView(linearLayout);
		soundPlayer = new SoundPlayer(this);

		progress = ProgressDialog.show(this, getString(R.string.piano), getString(R.string.piano_loading_sounds));
		new Thread(new Runnable() {
			@Override
			public void run() {
				soundPlayer.initSoundpool();
				createMidiSounds();
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						progress.dismiss();
					}
				});
			}
		}).start();

	}

	@Override
	protected void doAfterAddNoteEvent(NoteEvent noteEvent) {
		noteSheetView.redraw(getTrack());
	}

	private boolean createMidiSounds() {
		boolean success = false;
		File directory;
		String noteNameName;
		String fileName;
		String path;
		directory = new File(Environment.getExternalStorageDirectory() + File.separator + RECORDS_DIRECTORY_NAME
				+ File.separator + MIDI_DIRECTORY_NAME);
		if (!directory.exists()) {

			directory.mkdirs();
		}
		for (NoteName noteName : NoteName.values()) {
			noteNameName = "" + noteName;
			fileName = noteNameName + "_" + noteName.getMidi() + ".midi";
			path = directory.getAbsolutePath();
			File file = new File(path + File.separator + fileName);
			if (!file.exists()) {

				createMidiFileOfMidiVal(noteName, path, noteNameName + "_" + noteName.getMidi());

			}
			soundPlayer.setSoundpool(noteName.getMidi(), path + File.separator + fileName);
		}
		return success;
	}

	private void createMidiFileOfMidiVal(NoteName noteName, String path, String fileName) {
		Project project = new Project(fileName, beatsPerMinute, Key.VIOLIN);

		Track track = new Track(Key.VIOLIN);
		long tick = 0;
		NoteEvent noteEvent = new NoteEvent(noteName, true);
		track.addNoteEvent(tick, noteEvent);
		tick += NoteLength.WHOLE.getTickDuration();
		noteEvent = new NoteEvent(noteName, false);
		track.addNoteEvent(tick, noteEvent);

		project.addTrack(track);

		ProjectToMidiConverter converter = new ProjectToMidiConverter();
		try {
			converter.convertProjectAndWriteMidi(project, path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, Log.getStackTraceString(e));
		} catch (MidiException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, Log.getStackTraceString(e));
		}
	}

	public SoundPlayer getSoundPlayer() {
		return this.soundPlayer;
	}
}
