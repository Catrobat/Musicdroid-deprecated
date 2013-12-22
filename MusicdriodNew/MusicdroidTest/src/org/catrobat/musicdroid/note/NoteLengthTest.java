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
package org.catrobat.musicdroid.note;

import junit.framework.TestCase;

public class NoteLengthTest extends TestCase {

	public void testCalculateDuration1() {
		long expected = Math.round(NoteLength.DEFAULT_DURATION * 1f);
		long actual = NoteLength.QUARTER.getTickDuration();

		assertEquals(expected, actual);
	}

	public void testGetNoteLengthFromTick1() {
		NoteLength expectedNoteLength = NoteLength.WHOLE_DOT;
		long tick = expectedNoteLength.getTickDuration();

		NoteLength actualNoteLength = NoteLength.getNoteLengthFromTickDuration(tick);

		assertEquals(expectedNoteLength, actualNoteLength);
	}

	public void testGetNoteLengthFromTick2() {
		NoteLength expectedNoteLength = NoteLength.WHOLE_DOT;
		long tick = expectedNoteLength.getTickDuration();
		tick += 1;

		NoteLength actualNoteLength = NoteLength.getNoteLengthFromTickDuration(tick);

		assertEquals(expectedNoteLength, actualNoteLength);
	}

	public void testGetNoteLengthFromTick3() {
		NoteLength expectedNoteLength = NoteLength.QUARTER;
		long tick = NoteLength.QUARTER_DOT.getTickDuration();
		tick -= 1;

		NoteLength actualNoteLength = NoteLength.getNoteLengthFromTickDuration(tick);

		assertEquals(expectedNoteLength, actualNoteLength);
	}

	public void testGetNoteLengthFromTick4() {
		NoteLength expectedNoteLength = NoteLength.QUARTER;
		long tick = expectedNoteLength.getTickDuration();
		tick += 1;

		NoteLength actualNoteLength = NoteLength.getNoteLengthFromTickDuration(tick);

		assertEquals(expectedNoteLength, actualNoteLength);
	}

	public void testHasStem1() {
		assertFalse(NoteLength.WHOLE.hasStem());
	}

	public void testHasStem2() {
		assertFalse(NoteLength.WHOLE_DOT.hasStem());
	}

	public void testHasStem3() {
		assertTrue(NoteLength.QUARTER.hasStem());
	}

	public void testHasDot1() {
		assertFalse(NoteLength.QUARTER.hasDot());
	}

	public void testHasDot2() {
		assertTrue(NoteLength.WHOLE_DOT.hasDot());
	}

	public void testHasDot3() {
		assertTrue(NoteLength.HALF_DOT.hasDot());
	}

	public void testHasDot4() {
		assertTrue(NoteLength.QUARTER_DOT.hasDot());
	}

	public void testHasDot5() {
		assertTrue(NoteLength.EIGHT_DOT.hasDot());
	}

	public void testHasFlag1() {
		assertFalse(NoteLength.QUARTER.hasFlag());
	}

	public void testHasFlag2() {
		assertTrue(NoteLength.EIGHT.hasFlag());
	}

	public void testHasFlag3() {
		assertTrue(NoteLength.EIGHT_DOT.hasFlag());
	}

	public void testHasFlag4() {
		assertTrue(NoteLength.SIXTEENTH.hasFlag());
	}

	public void testGetAmountOfFlags1() {
		assertEquals(2, NoteLength.SIXTEENTH.getAmountOfFlags());
	}

	public void testGetAmountOfFlags2() {
		assertEquals(1, NoteLength.EIGHT.getAmountOfFlags());
	}

	public void testGetAmountOfFlags3() {
		assertEquals(1, NoteLength.EIGHT_DOT.getAmountOfFlags());
	}

	public void testGetAmountOfFlags4() {
		assertEquals(0, NoteLength.QUARTER.getAmountOfFlags());
	}
}
