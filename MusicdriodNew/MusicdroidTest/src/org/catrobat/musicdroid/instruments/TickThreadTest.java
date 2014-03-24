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
package org.catrobat.musicdroid.instruments;

import junit.framework.TestCase;

import org.catrobat.musicdroid.note.NoteEvent;
import org.catrobat.musicdroid.note.NoteLength;
import org.catrobat.musicdroid.note.NoteName;

public class TickThreadTest extends TestCase {

	// TODO fw yes i know that this test is garbage, but until TickThread is not a real Thread...
	public void testGetNextTick1() {
		TickThread tickThread = new TickThread();
		NoteEvent noteEvent = new NoteEvent(NoteName.C4, false);
		long tick = 0;

		assertEquals(tick, tickThread.getNextTick(noteEvent));
		tick += NoteLength.QUARTER.getTickDuration();
		assertEquals(tick, tickThread.getNextTick(noteEvent));
		tick += NoteLength.QUARTER.getTickDuration();
		assertEquals(tick, tickThread.getNextTick(noteEvent));
	}

	public void testGetNextTick2() {
		TickThread tickThread = new TickThread();
		NoteEvent noteEvent = new NoteEvent(NoteName.C4, true);
		long tick = 0;

		assertEquals(tick, tickThread.getNextTick(noteEvent));
		assertEquals(tick, tickThread.getNextTick(noteEvent));
		assertEquals(tick, tickThread.getNextTick(noteEvent));
	}
}
