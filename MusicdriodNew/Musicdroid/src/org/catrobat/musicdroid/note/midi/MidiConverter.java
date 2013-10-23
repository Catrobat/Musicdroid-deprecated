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
import com.leff.midi.event.MidiEvent;
import com.leff.midi.event.NoteOn;
import com.leff.midi.event.ProgramChange;
import com.leff.midi.event.meta.Tempo;

import org.catrobat.musicdroid.note.Instrument;
import org.catrobat.musicdroid.note.Key;
import org.catrobat.musicdroid.note.Note;
import org.catrobat.musicdroid.note.NoteLength;
import org.catrobat.musicdroid.note.NoteName;
import org.catrobat.musicdroid.note.Project;
import org.catrobat.musicdroid.note.Symbol;
import org.catrobat.musicdroid.note.Tact;
import org.catrobat.musicdroid.note.Track;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class MidiConverter {

	private static final Instrument DEFAULT_INSTRUMENT = Instrument.ACOUSTIC_GRAND_PIANO;

	public Project readFileAndConvertMidi(File file) throws FileNotFoundException, IOException, MidiException {
		MidiFile midi = new MidiFile(file);

		try {
			return convertMidi(midi, file.getName());
		} catch (Exception e) {
			throw new MidiException("Unsupported MIDI format!");
		}
	}

	protected Project convertMidi(MidiFile midi, String name) throws MidiException {
		ArrayList<MidiTrack> midiTracks = midi.getTracks();

		MidiTrack tempoTrack = midiTracks.get(0);

		Project project = new Project(name, getBeatsPerMinute(tempoTrack));

		for (int i = 1; i < midiTracks.size(); i++) {
			Track track = createTrack(midiTracks.get(i));
			project.addTrack(track);
		}

		return project;
	}

	protected int getBeatsPerMinute(MidiTrack tempoTrack) throws MidiException {
		Iterator<MidiEvent> it = tempoTrack.getEvents().iterator();

		while (it.hasNext()) {
			MidiEvent event = it.next();

			if (event instanceof Tempo) {
				Tempo tempo = (Tempo) event;
				return (int) tempo.getBpm();
			}
		}

		throw new MidiException("No Tempo found in track. Unsupported MIDI format!");
	}

	protected Track createTrack(MidiTrack midiTrack) throws MidiException {
		Iterator<MidiEvent> it = midiTrack.getEvents().iterator();
		Instrument instrument = DEFAULT_INSTRUMENT;
		ArrayList<Symbol> symbols = new ArrayList<Symbol>();

		while (it.hasNext()) {
			MidiEvent event = it.next();

			if (event instanceof ProgramChange) {
				ProgramChange program = (ProgramChange) event;

				instrument = Instrument.getInstrumentFromProgram(program.getProgramNumber());
			} else if (event instanceof NoteOn) {
				NoteOn noteOn = (NoteOn) event;

				if (false == it.hasNext()) {
					throw new MidiException("No NoteOff event found for a NoteOn event. Unsupported MIDI format");
				}

				NoteOn noteOff = (NoteOn) it.next();

				NoteName note = NoteName.getNoteNameFromMidiValue(noteOn.getNoteValue());
				long duration = noteOff.getTick() - noteOn.getTick();
				NoteLength length = NoteLength.getNoteLengthFromDuration(duration);

				// TODO wie geh ich mit Breaks um?

				symbols.add(new Note(note, length));
			}
		}

		Track track = new Track(instrument, Key.VIOLIN, new Tact());

		for (Symbol symbol : symbols) {
			track.addSymbol(symbol);
		}

		return track;
	}
}
