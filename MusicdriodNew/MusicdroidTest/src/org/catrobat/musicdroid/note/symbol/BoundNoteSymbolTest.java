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

public class BoundNoteSymbolTest extends TestCase {

	public void testAddNoteSymbol() {
		BoundNoteSymbol boundNoteSymbol = new BoundNoteSymbol();

		assertTrue(boundNoteSymbol.size() == 0);

		boundNoteSymbol.addNoteSymbol(new NoteSymbol());

		assertTrue(boundNoteSymbol.size() == 1);
	}

	public void testGetNoteSymbol() {
		BoundNoteSymbol boundNoteSymbol = new BoundNoteSymbol();
		NoteSymbol noteSymbol = new NoteSymbol();

		boundNoteSymbol.addNoteSymbol(noteSymbol);

		assertEquals(noteSymbol, boundNoteSymbol.getNoteSymbol(0));
	}

	public void testEquals1() {
		BoundNoteSymbol boundNoteSymbol1 = new BoundNoteSymbol();
		BoundNoteSymbol boundNoteSymbol2 = new BoundNoteSymbol();

		assertTrue(boundNoteSymbol1.equals(boundNoteSymbol2));
	}

	public void testEquals2() {
		NoteSymbol noteSymbol = new NoteSymbol();
		BoundNoteSymbol boundNoteSymbol1 = new BoundNoteSymbol();
		boundNoteSymbol1.addNoteSymbol(noteSymbol);
		BoundNoteSymbol boundNoteSymbol2 = new BoundNoteSymbol();
		boundNoteSymbol2.addNoteSymbol(noteSymbol);

		assertTrue(boundNoteSymbol1.equals(boundNoteSymbol2));
	}

	public void testEquals3() {
		BoundNoteSymbol boundNoteSymbol1 = new BoundNoteSymbol();
		boundNoteSymbol1.addNoteSymbol(new NoteSymbol());
		BoundNoteSymbol boundNoteSymbol2 = new BoundNoteSymbol();

		assertFalse(boundNoteSymbol1.equals(boundNoteSymbol2));
	}

	public void testEquals4() {
		BoundNoteSymbol boundNoteSymbol = new BoundNoteSymbol();

		assertFalse(boundNoteSymbol.equals(null));
	}

	public void testEquals5() {
		BoundNoteSymbol boundNoteSymbol = new BoundNoteSymbol();

		assertFalse(boundNoteSymbol.equals(""));
	}

	public void testToString() {
		BoundNoteSymbol boundNoteSymbol = new BoundNoteSymbol();

		assertEquals("[BoundNoteSymbol] size: " + boundNoteSymbol.size(), boundNoteSymbol.toString());
	}
}
