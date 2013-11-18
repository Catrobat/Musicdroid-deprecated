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
package org.catrobat.musicdroid.note.symbol;

import junit.framework.TestCase;

import org.catrobat.musicdroid.note.NoteLength;
import org.catrobat.musicdroid.note.NoteName;

public class NoteSymbolTest extends TestCase {

	public void testNoteSymbol() {
		NoteLength[] noteLengths = { NoteLength.QUARTER };
		NoteName[] noteNames = { NoteName.C1 };
		NoteSymbol noteSymbol = new NoteSymbol(noteLengths, noteNames);

		assertEquals(noteLengths, noteSymbol.getNoteLengths());
		assertEquals(noteNames, noteSymbol.getNoteNames());
	}

	public void testToString() {
		NoteLength[] noteLengths = { NoteLength.QUARTER, NoteLength.EIGHT };
		NoteName[] noteNames = { NoteName.C1, NoteName.D1 };
		NoteSymbol noteSymbol = new NoteSymbol(noteLengths, noteNames);

		String expectedString = "[AbstractSymbol] duration= " + NoteLength.getTickDurationFromNoteLengths(noteLengths);

		assertEquals(expectedString, noteSymbol.toString());
	}

	public void testEquals1() {
		NoteLength[] noteLengths1 = { NoteLength.QUARTER };
		NoteName[] noteNames1 = { NoteName.C1 };
		NoteSymbol noteSymbol1 = new NoteSymbol(noteLengths1, noteNames1);

		NoteLength[] noteLengths2 = { NoteLength.QUARTER };
		NoteName[] noteNames2 = { NoteName.C1 };
		NoteSymbol noteSymbol2 = new NoteSymbol(noteLengths2, noteNames2);

		assertTrue(noteSymbol1.equals(noteSymbol2));
	}

	public void testEquals2() {
		NoteLength[] noteLengths1 = { NoteLength.QUARTER };
		NoteName[] noteNames1 = { NoteName.C1 };
		NoteSymbol noteSymbol1 = new NoteSymbol(noteLengths1, noteNames1);

		NoteLength[] noteLengths2 = { NoteLength.QUARTER, NoteLength.EIGHT };
		NoteName[] noteNames2 = { NoteName.C1 };
		NoteSymbol noteSymbol2 = new NoteSymbol(noteLengths2, noteNames2);

		assertFalse(noteSymbol1.equals(noteSymbol2));
	}

	public void testEquals3() {
		NoteLength[] noteLengths1 = { NoteLength.QUARTER };
		NoteName[] noteNames1 = { NoteName.C1 };
		NoteSymbol noteSymbol1 = new NoteSymbol(noteLengths1, noteNames1);

		NoteLength[] noteLengths2 = { NoteLength.QUARTER };
		NoteName[] noteNames2 = { NoteName.C1, NoteName.D1 };
		NoteSymbol noteSymbol2 = new NoteSymbol(noteLengths2, noteNames2);

		assertFalse(noteSymbol1.equals(noteSymbol2));
	}

	public void testEquals4() {
		NoteLength[] noteLengths1 = { NoteLength.QUARTER };
		NoteName[] noteNames1 = { NoteName.C1 };
		NoteSymbol noteSymbol1 = new NoteSymbol(noteLengths1, noteNames1);

		NoteLength[] noteLengths2 = { NoteLength.QUARTER, NoteLength.EIGHT };
		NoteName[] noteNames2 = { NoteName.C1, NoteName.D1 };
		NoteSymbol noteSymbol2 = new NoteSymbol(noteLengths2, noteNames2);

		assertFalse(noteSymbol1.equals(noteSymbol2));
	}

	public void testEquals5() {
		NoteLength[] noteLengths = { NoteLength.QUARTER };
		NoteName[] noteNames = { NoteName.C1 };
		NoteSymbol noteSymbol = new NoteSymbol(noteLengths, noteNames);

		assertFalse(noteSymbol.equals(null));
	}

	public void testEquals6() {
		NoteLength[] noteLengths = { NoteLength.QUARTER };
		NoteName[] noteNames = { NoteName.C1 };
		NoteSymbol noteSymbol = new NoteSymbol(noteLengths, noteNames);

		assertFalse(noteSymbol.equals(""));
	}

	public void testGetIndexForNoteWithBiggestDistanceToMiddleLine() {
		NoteLength[] noteLengths = { NoteLength.QUARTER };
		NoteName[] noteNames = { NoteName.C1 };
		NoteSymbol noteSymbol = new NoteSymbol(noteLengths, noteNames);

		assertEquals(0, noteSymbol.getIndexForNoteWithBiggestDistanceToMiddleLine());
	}

	public void testGetIndexForNoteWithBiggestDistanceToMiddleLine1() {
		NoteLength[] noteLengths = { NoteLength.QUARTER };
		NoteName[] noteNames = { NoteName.C1, NoteName.B3 };
		NoteSymbol noteSymbol = new NoteSymbol(noteLengths, noteNames);

		assertEquals(0, noteSymbol.getIndexForNoteWithBiggestDistanceToMiddleLine());
	}

	public void testGetIndexForNoteWithBiggestDistanceToMiddleLine2() {
		NoteLength[] noteLengths = { NoteLength.QUARTER };
		NoteName[] noteNames = { NoteName.B3, NoteName.C3 };
		NoteSymbol noteSymbol = new NoteSymbol(noteLengths, noteNames);

		assertEquals(1, noteSymbol.getIndexForNoteWithBiggestDistanceToMiddleLine());
	}

	public void testGetIndexForNoteWithBiggestDistanceToMiddleLine3() {
		NoteLength[] noteLengths = { NoteLength.QUARTER };
		NoteName[] noteNames = { NoteName.C2, NoteName.A3 };
		NoteSymbol noteSymbol = new NoteSymbol(noteLengths, noteNames);

		assertEquals(0, noteSymbol.getIndexForNoteWithBiggestDistanceToMiddleLine());
	}

	public void testGetIndexForNoteWithBiggestDistanceToMiddleLine4() {
		NoteLength[] noteLengths = { NoteLength.QUARTER, NoteLength.HALF };
		NoteName[] noteNames = { NoteName.C2, NoteName.A3, NoteName.C3, NoteName.A4 };
		NoteSymbol noteSymbol = new NoteSymbol(noteLengths, noteNames);

		assertEquals(0, noteSymbol.getIndexForNoteWithBiggestDistanceToMiddleLine());
	}

	public void testIsSteamUp() {
		NoteLength[] noteLengths = { NoteLength.QUARTER, NoteLength.HALF };
		NoteName[] noteNames = { NoteName.C2, NoteName.A3, NoteName.C3, NoteName.A4 };
		NoteSymbol noteSymbol = new NoteSymbol(noteLengths, noteNames);

		assertTrue(noteSymbol.isStemUp());
	}
}
