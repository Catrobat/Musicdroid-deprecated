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
package org.catrobat.musicdroid.note.midi;

import com.leff.midi.MidiFile;
import com.leff.midi.MidiTrack;
import com.leff.midi.event.ProgramChange;
import com.leff.midi.event.meta.Tempo;

import org.catrobat.musicdroid.note.Instrument;
import org.catrobat.musicdroid.note.Note;
import org.catrobat.musicdroid.note.NoteLength;
import org.catrobat.musicdroid.note.Project;
import org.catrobat.musicdroid.note.Symbol;
import org.catrobat.musicdroid.note.Track;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ProjectConverter {

	private static int DEFAULT_VELOCITY = 64;
	private static int MAX_CHANNEL = 16;

	private ArrayList<Instrument> usedChannels;

	public ProjectConverter() {
		usedChannels = new ArrayList<Instrument>();
	}

	public void convertProjectAndWriteMidi(Project project) throws IOException, MidiException {
		MidiFile midi = convertProject(project);
		midi.writeToFile(new File(project.getName() + ".midi"));
	}

	protected MidiFile convertProject(Project project) throws MidiException {
		ArrayList<MidiTrack> tracks = new ArrayList<MidiTrack>();

		MidiTrack tempoTrack = createTempoTrack(project.getBeatsPerMinute());
		tracks.add(tempoTrack);

		for (MidiTrack track : createNoteTracks(project)) {
			tracks.add(track);
		}

		return new MidiFile(MidiFile.DEFAULT_RESOLUTION, tracks);
	}

	private MidiTrack createTempoTrack(int beatsPerMinute) {
		MidiTrack tempoTrack = new MidiTrack();

		Tempo t = new Tempo();
		t.setBpm(beatsPerMinute);

		tempoTrack.insertEvent(t);

		return tempoTrack;
	}

	private ArrayList<MidiTrack> createNoteTracks(Project project) throws MidiException {
		ArrayList<MidiTrack> noteTracks = new ArrayList<MidiTrack>();

		for (int i = 0; i < project.size(); i++) {
			Track track = project.getTrack(i);
			MidiTrack noteTrack = createNoteTrack(track);

			noteTracks.add(noteTrack);
		}

		return noteTracks;
	}

	private MidiTrack createNoteTrack(Track track) throws MidiException {
		MidiTrack noteTrack = new MidiTrack();

		int channel = addInstrumentAndGetChannel(track.getInstrument());

		ProgramChange program = new ProgramChange(0, channel, track.getInstrument().getProgram());
		noteTrack.insertEvent(program);

		long tick = 0;

		for (int i = 0; i < track.size(); i++) {
			Symbol symbol = track.getSymbol(i);
			long duration = NoteLength.calculateDuration(symbol.getNoteLength());

			if (symbol instanceof Note) {
				Note note = (Note) symbol;
				noteTrack.insertNote(channel, note.getNoteName().getMidi(), DEFAULT_VELOCITY, tick, duration);
			}

			tick += duration;
		}

		return noteTrack;
	}

	protected int addInstrumentAndGetChannel(Instrument instrument) throws MidiException {
		if (usedChannels.contains(instrument)) {
			return usedChannels.indexOf(instrument) + 1;
		} else if (usedChannels.size() == MAX_CHANNEL) {
			throw new MidiException("You cannot have more than " + MAX_CHANNEL + " channels!");
		} else {
			usedChannels.add(instrument);

			return usedChannels.indexOf(instrument) + 1;
		}
	}
}
