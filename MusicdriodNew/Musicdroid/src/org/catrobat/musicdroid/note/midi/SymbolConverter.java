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

import com.leff.midi.MidiTrack;

import org.catrobat.musicdroid.note.Break;
import org.catrobat.musicdroid.note.Chord;
import org.catrobat.musicdroid.note.Note;
import org.catrobat.musicdroid.note.NoteLength;
import org.catrobat.musicdroid.note.Symbol;

public class SymbolConverter {

	private static int DEFAULT_VELOCITY = 64;

	public long addSymbolAndReturnNewTick(MidiTrack noteTrack, Symbol symbol, int channel, long tick) {
		if (symbol instanceof Break) {
			Break br = (Break) symbol;
			tick = addBreakAndReturnNewTick(noteTrack, br, channel, tick);
		} else if (symbol instanceof Note) {
			Note note = (Note) symbol;
			tick = addNoteAndReturnNewTick(noteTrack, note, channel, tick);
		} else if (symbol instanceof Chord) {
			Chord chord = (Chord) symbol;
			tick = addChordAndReturnNewTick(noteTrack, chord, channel, tick);
		} else {
			throw new IllegalArgumentException("Unsupported Symbol!");
		}

		return tick;
	}

	protected long addBreakAndReturnNewTick(MidiTrack noteTrack, Break br, int channel, long tick) {
		long duration = NoteLength.calculateDuration(br.getNoteLength());

		return tick + duration;
	}

	protected long addNoteAndReturnNewTick(MidiTrack noteTrack, Note note, int channel, long tick) {
		long duration = NoteLength.calculateDuration(note.getNoteLength());

		noteTrack.insertNote(channel, note.getNoteName().getMidi(), DEFAULT_VELOCITY, tick, duration);

		return tick + duration;
	}

	protected long addChordAndReturnNewTick(MidiTrack noteTrack, Chord chord, int channel, long tick) {
		long duration = NoteLength.calculateDuration(chord.getNoteLength());

		for (int i = 0; i < chord.size(); i++) {
			noteTrack.insertNote(channel, chord.getNoteName(i).getMidi(), DEFAULT_VELOCITY, tick, duration);
		}

		return tick + duration;
	}
}
