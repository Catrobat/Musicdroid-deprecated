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

public class BreakSymbolTest extends TestCase {

	public void testBreakSymbol() {
		NoteLength noteLength = NoteLength.QUARTER_DOT;
		BreakSymbol breakSymbol = new BreakSymbol(noteLength);

		assertEquals(noteLength, breakSymbol.getNoteLength());
	}

	public void testEquals1() {
		BreakSymbol breakSymbol1 = new BreakSymbol(NoteLength.QUARTER);
		BreakSymbol breakSymbol2 = new BreakSymbol(NoteLength.QUARTER);

		assertTrue(breakSymbol1.equals(breakSymbol2));
	}

	public void testEquals2() {
		BreakSymbol breakSymbol1 = new BreakSymbol(NoteLength.QUARTER);
		BreakSymbol breakSymbol2 = new BreakSymbol(NoteLength.EIGHT);

		assertFalse(breakSymbol1.equals(breakSymbol2));
	}

	public void testEquals3() {
		BreakSymbol breakSymbol = new BreakSymbol(NoteLength.QUARTER);

		assertFalse(breakSymbol.equals(null));
	}

	public void testEquals4() {
		BreakSymbol breakSymbol = new BreakSymbol(NoteLength.QUARTER);

		assertFalse(breakSymbol.equals(""));
	}

	public void testToString() {
		BreakSymbol breakSymbol = new BreakSymbol(NoteLength.QUARTER);

		assertEquals("[BreakSymbol] noteLength: " + breakSymbol.getNoteLength(), breakSymbol.toString());
	}
}
