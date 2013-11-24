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
import com.leff.midi.event.ChannelEvent;
import com.leff.midi.event.ProgramChange;
import com.leff.midi.event.meta.Tempo;
import com.leff.midi.event.meta.TimeSignature;

import org.catrobat.musicdroid.note.Instrument;
import org.catrobat.musicdroid.note.NoteEvent;
import org.catrobat.musicdroid.note.Project;
import org.catrobat.musicdroid.note.Track;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProjectToMidiConverter {

	private static final int MAX_CHANNEL = 16;

	private NoteEventToMidiEventConverter eventConverter;
	private ArrayList<Instrument> usedChannels;

	public ProjectToMidiConverter() {
		eventConverter = new NoteEventToMidiEventConverter();
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

		for (int i = 0; i < project.size(); i++) {
			Track track = project.getTrack(i);
			int channel = addInstrumentAndGetChannel(track.getInstrument());

			MidiTrack noteTrack = createNoteTrack(track, channel);

			tracks.add(noteTrack);
		}

		return new MidiFile(MidiFile.DEFAULT_RESOLUTION, tracks);
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

	protected MidiTrack createTempoTrack(int beatsPerMinute) {
		MidiTrack tempoTrack = new MidiTrack();

		Tempo tempo = new Tempo();
		tempo.setBpm(beatsPerMinute);

		tempoTrack.insertEvent(tempo);

		TimeSignature timeSignature = new TimeSignature();
		timeSignature.setTimeSignature(4, 4, TimeSignature.DEFAULT_METER, TimeSignature.DEFAULT_DIVISION);

		tempoTrack.insertEvent(timeSignature);

		return tempoTrack;
	}

	public MidiTrack createNoteTrack(Track track, int channel) throws MidiException {
		MidiTrack noteTrack = new MidiTrack();

		ProgramChange program = new ProgramChange(0, channel, track.getInstrument().getProgram());
		noteTrack.insertEvent(program);

		for (long tick : track.getSortedTicks()) {
			List<NoteEvent> noteEventList = track.getNoteEventsForTick(tick);

			for (NoteEvent noteEvent : noteEventList) {
				ChannelEvent channelEvent = eventConverter.convertNoteEvent(tick, noteEvent, channel);
				noteTrack.insertEvent(channelEvent);
			}
		}

		return noteTrack;
	}
}
