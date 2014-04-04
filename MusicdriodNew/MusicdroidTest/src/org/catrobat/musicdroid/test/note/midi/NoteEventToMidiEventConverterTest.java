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
package org.catrobat.musicdroid.test.note.midi;

import android.test.AndroidTestCase;

import com.leff.midi.event.ChannelEvent;
import com.leff.midi.event.NoteOff;
import com.leff.midi.event.NoteOn;

import org.catrobat.musicdroid.note.NoteEvent;
import org.catrobat.musicdroid.note.NoteName;
import org.catrobat.musicdroid.note.midi.NoteEventToMidiEventConverter;
import org.catrobat.musicdroid.test.note.testutil.NoteEventTestDataFactory;

public class NoteEventToMidiEventConverterTest extends AndroidTestCase {

	public void testConvertNoteEvent1() {
		NoteEventToMidiEventConverter noteEventConverter = new NoteEventToMidiEventConverter();
		NoteName noteName = NoteName.C1;
		NoteEvent noteEvent = NoteEventTestDataFactory.createNoteEvent(noteName);
		long tick = 0;
		int channel = 3;
		int velocity = 64;

		NoteOn noteOnEvent = (NoteOn) noteEventConverter.convertNoteEvent(tick, noteEvent, channel);

		assertNoteOnEvent(noteOnEvent, noteName, tick, channel, velocity);
	}

	public void testConvertNoteEvent2() {
		NoteEventToMidiEventConverter noteEventConverter = new NoteEventToMidiEventConverter();
		long tick = 0;
		NoteName noteName = NoteName.C1;
		NoteEvent noteEvent = new NoteEvent(noteName, false);
		int channel = 3;
		int velocity = 0;

		NoteOff noteOffEvent = (NoteOff) noteEventConverter.convertNoteEvent(tick, noteEvent, channel);

		assertNoteOffEvent(noteOffEvent, noteName, tick, channel, velocity);
	}

	private void assertChannelEvent(ChannelEvent channelEvent, long tick, int channel) {
		assertEquals(tick, channelEvent.getTick());
		assertEquals(channel, channelEvent.getChannel());
	}

	private void assertNoteOnEvent(NoteOn noteOnEvent, NoteName noteName, long tick, int channel, int velocity) {
		assertChannelEvent(noteOnEvent, tick, channel);
		assertEquals(noteName.getMidi(), noteOnEvent.getNoteValue());
		assertEquals(velocity, noteOnEvent.getVelocity());
	}

	private void assertNoteOffEvent(NoteOff noteOffEvent, NoteName noteName, long tick, int channel, int velocity) {
		assertChannelEvent(noteOffEvent, tick, channel);
		assertEquals(noteName.getMidi(), noteOffEvent.getNoteValue());
		assertEquals(velocity, noteOffEvent.getVelocity());
	}
}
