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
package org.catrobat.musicdroid.note.midi.testutil;

import android.os.Environment;

import com.leff.midi.MidiFile;
import com.leff.midi.MidiTrack;
import com.leff.midi.event.meta.Tempo;
import com.leff.midi.event.meta.Text;

import org.catrobat.musicdroid.note.Project;
import org.catrobat.musicdroid.note.midi.MidiException;
import org.catrobat.musicdroid.note.midi.MidiToProjectConverter;
import org.catrobat.musicdroid.note.midi.ProjectToMidiConverter;
import org.catrobat.musicdroid.note.testutil.ProjectTestDataFactory;

import java.io.File;
import java.io.IOException;

public class MidiFileTestDataFactory {

	public static boolean createAndWriteMidiFileWithDeleteOnSuccess() {
		Project project = ProjectTestDataFactory.createProject();
		try {
			createAndWriteMidiFile(project);
		} catch (Exception e) {
			return false;
		} finally {
			File file = new File(project.getName() + ".midi");
			file.delete();
		}

		return true;
	}

	private static void createAndWriteMidiFile(Project project) throws IOException, MidiException {
		ProjectToMidiConverter converter = new ProjectToMidiConverter();
		converter.convertProjectAndWriteMidi(project, Environment.getExternalStorageDirectory().getAbsolutePath()); //TODO: PFAD ANGEBEN
	}

	public static boolean createAndReadMidiFile() {
		Project project = ProjectTestDataFactory.createProject();
		try {
			createAndWriteMidiFile(project);
			MidiToProjectConverter converter = new MidiToProjectConverter();
			project = converter.readFileAndConvertMidi(new File(Environment.getExternalStorageDirectory()
					.getAbsoluteFile() + File.separator + project.getName() + ".midi"));

			return (project != null);
		} catch (Exception e) {
			return false;
		} finally {
			File file = new File(project.getName() + ".midi");
			file.delete();
		}
	}

	public static MidiFile createMidiFile() {
		return new MidiFile();
	}

	public static MidiFile createMidiFileWithEmptyTrack() {
		MidiFile midiFile = createMidiFile();
		midiFile.addTrack(new MidiTrack());

		return midiFile;
	}

	public static MidiFile createMidiFileWithTrackContainingTempoEvent() {
		MidiFile midiFile = createMidiFile();
		MidiTrack midiTrack = new MidiTrack();
		midiTrack.insertEvent(new Tempo());
		midiFile.addTrack(midiTrack);

		return midiFile;
	}

	public static MidiFile createMidiFileWithTrackContainingSomeTextEvent() {
		MidiFile midiFile = createMidiFile();
		MidiTrack midiTrack = new MidiTrack();
		midiTrack.insertEvent(new Text(0, 0, "Some text that does not matter"));
		midiFile.addTrack(midiTrack);

		return midiFile;
	}
}
