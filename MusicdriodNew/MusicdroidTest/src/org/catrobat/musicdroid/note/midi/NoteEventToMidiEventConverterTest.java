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

import com.leff.midi.event.ChannelEvent;
import com.leff.midi.event.NoteOff;
import com.leff.midi.event.NoteOn;

import junit.framework.TestCase;

import org.catrobat.musicdroid.note.NoteEvent;
import org.catrobat.musicdroid.note.NoteName;

public class NoteEventToMidiEventConverterTest extends TestCase {

	public void testConvertNoteEvent1() {
		NoteEventToMidiEventConverter noteEventConverter = new NoteEventToMidiEventConverter();
		long tick = 0;
		NoteName noteName = NoteName.C1;
		NoteEvent noteEvent = new NoteEvent(noteName, true);
		int channel = 3;
		int velocity = 64;

		ChannelEvent channelEvent = noteEventConverter.convertNoteEvent(tick, noteEvent, channel);

		assertEquals(tick, channelEvent.getTick());
		assertEquals(channel, channelEvent.getChannel());
		assertTrue(channelEvent instanceof NoteOn);
		NoteOn noteOnEvent = (NoteOn) channelEvent;
		assertEquals(noteName.getMidi(), noteOnEvent.getNoteValue());
		assertEquals(velocity, noteOnEvent.getVelocity());
	}

	public void testConvertNoteEvent2() {
		NoteEventToMidiEventConverter noteEventConverter = new NoteEventToMidiEventConverter();
		long tick = 0;
		NoteName noteName = NoteName.C1;
		NoteEvent noteEvent = new NoteEvent(noteName, false);
		int channel = 3;
		int velocity = 0;

		ChannelEvent channelEvent = noteEventConverter.convertNoteEvent(tick, noteEvent, channel);

		assertEquals(tick, channelEvent.getTick());
		assertEquals(channel, channelEvent.getChannel());
		assertTrue(channelEvent instanceof NoteOff);
		NoteOff noteOnEvent = (NoteOff) channelEvent;
		assertEquals(noteName.getMidi(), noteOnEvent.getNoteValue());
		assertEquals(velocity, noteOnEvent.getVelocity());
	}
}
