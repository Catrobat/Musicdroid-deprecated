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

import org.catrobat.musicdroid.note.Key;
import org.catrobat.musicdroid.note.NoteName;
import org.catrobat.musicdroid.note.symbol.testutil.NoteSymbolTestDataFactory;

public class NoteSymbolTest extends TestCase {

	public void testAddNote() {
		NoteSymbol noteSymbol = NoteSymbolTestDataFactory.createNoteSymbolWithNote();

		assertTrue(noteSymbol.size() == 1);
	}

	public void testEquals1() {
		NoteSymbol noteSymbol1 = NoteSymbolTestDataFactory.createNoteSymbol();
		NoteSymbol noteSymbol2 = NoteSymbolTestDataFactory.createNoteSymbol();

		assertTrue(noteSymbol1.equals(noteSymbol2));
	}

	public void testEquals2() {
		NoteSymbol noteSymbol1 = NoteSymbolTestDataFactory.createNoteSymbolWithNote();
		NoteSymbol noteSymbol2 = NoteSymbolTestDataFactory.createNoteSymbolWithNote();

		assertTrue(noteSymbol1.equals(noteSymbol2));
	}

	public void testEquals3() {
		NoteSymbol noteSymbol1 = NoteSymbolTestDataFactory.createNoteSymbolWithNote();
		NoteSymbol noteSymbol2 = NoteSymbolTestDataFactory.createNoteSymbol();

		assertFalse(noteSymbol1.equals(noteSymbol2));
	}

	public void testEquals4() {
		NoteSymbol noteSymbol = NoteSymbolTestDataFactory.createNoteSymbol();

		assertFalse(noteSymbol.equals(null));
	}

	public void testEquals5() {
		NoteSymbol noteSymbol = NoteSymbolTestDataFactory.createNoteSymbol();

		assertFalse(noteSymbol.equals(""));
	}

	public void testToString() {
		NoteSymbol noteSymbol = NoteSymbolTestDataFactory.createNoteSymbol();
		String expectedString = "[NoteSymbol] size: " + noteSymbol.size();

		assertEquals(expectedString, noteSymbol.toString());
	}

	public void testIsStemUp1() {
		Key key = Key.VIOLIN;
		NoteSymbol noteSymbol = NoteSymbolTestDataFactory.createNoteSymbolWithNote(NoteName.C5);

		assertFalse(noteSymbol.isStemUp(key));
	}

	public void testIsStemUp2() {
		Key key = Key.VIOLIN;
		NoteSymbol noteSymbol = NoteSymbolTestDataFactory.createNoteSymbolWithNote(NoteName.D4);

		assertTrue(noteSymbol.isStemUp(key));
	}

	public void testIsStemUp3() {
		Key key = Key.VIOLIN;
		NoteSymbol noteSymbol = NoteSymbolTestDataFactory.createNoteSymbolWithNote(NoteName.B4);

		assertFalse(noteSymbol.isStemUp(key));
	}

	public void testIsStemUp4() {
		Key key = Key.VIOLIN;
		NoteSymbol noteSymbol = NoteSymbolTestDataFactory.createNoteSymbolWithAccord(NoteName.D5, NoteName.G4,
				NoteName.B4);

		assertFalse(noteSymbol.isStemUp(key));
	}

	public void testIsStemUp5() {
		Key key = Key.VIOLIN;
		NoteSymbol noteSymbol = NoteSymbolTestDataFactory.createNoteSymbolWithAccord(NoteName.D5, NoteName.G3,
				NoteName.B4);

		assertTrue(noteSymbol.isStemUp(key));
	}
}
