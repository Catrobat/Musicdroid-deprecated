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
import com.leff.midi.event.NoteOn;
import com.leff.midi.event.ProgramChange;
import com.leff.midi.event.meta.Tempo;

import org.catrobat.musicdroid.note.NoteEvent;
import org.catrobat.musicdroid.note.Track;

public class TrackConverter {

	private NoteEventConverter eventConverter;

	public TrackConverter() {
		eventConverter = new NoteEventConverter();
	}

	public MidiTrack createTempoTrack(int beatsPerMinute) {
		MidiTrack tempoTrack = new MidiTrack();

		Tempo t = new Tempo();
		t.setBpm(beatsPerMinute);

		tempoTrack.insertEvent(t);

		return tempoTrack;
	}

	public MidiTrack createNoteTrack(Track track, int channel) throws MidiException {
		MidiTrack noteTrack = new MidiTrack();

		ProgramChange program = new ProgramChange(0, channel, track.getInstrument().getProgram());
		noteTrack.insertEvent(program);

		for (int i = 0; i < track.size(); i++) {
			NoteEvent event = track.getNoteEvent(i);
			NoteOn noteOn = eventConverter.convertNoteEvent(event, channel);
			noteTrack.insertEvent(noteOn);
		}

		return noteTrack;
	}
}
