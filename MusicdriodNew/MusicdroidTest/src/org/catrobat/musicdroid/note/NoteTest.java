/*******************************************************************************
 * Catroid: An on-device visual programming system for Android devices
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
 *  http://www.catroid.org/catroid/licenseadditionalterm
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU Affero General Public License for more details.
 * 
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package org.catrobat.musicdroid.note;

import junit.framework.TestCase;

public class NoteTest extends TestCase {

	public void testNote() {
		NoteName name = NoteName.C1;
		NoteLength noteLength = NoteLength.EIGHT;
		Note note = new Note(name, noteLength);
		
		assertEquals(name, note.getNoteName());
		assertEquals(noteLength, note.getNoteLength());
	}
	
	public void testHalfToneUp() {
		Note c1 = new Note(NoteName.C1, NoteLength.QUARTER);
		Note c1s = c1.halfToneUp();
		
		assertEquals(c1.getNoteLength(), c1s.getNoteLength());
		assertEquals(c1.getNoteName().next(), c1s.getNoteName());
	}

	public void testHalfToneDown() {
		Note c1s = new Note(NoteName.C1S, NoteLength.QUARTER);
		Note c1 = c1s.halfToneDown();
		
		assertEquals(c1.getNoteLength(), c1s.getNoteLength());
		assertEquals(c1s.getNoteName().previous(), c1.getNoteName());
	}
	
	public void testEquals1() {
		Note note1 = new Note(NoteName.C1, NoteLength.QUARTER);
		Note note2 = new Note(NoteName.C1, NoteLength.QUARTER);
		
		assertTrue(note1.equals(note2));
	}
	
	public void testEquals2() {
		Note note1 = new Note(NoteName.C1, NoteLength.QUARTER);
		Note note2 = new Note(NoteName.D1, NoteLength.QUARTER);
		
		assertFalse(note1.equals(note2));
	}
	
	public void testEquals3() {
		Note note1 = new Note(NoteName.C1, NoteLength.QUARTER);
		Note note2 = new Note(NoteName.C1, NoteLength.HALF);
		
		assertFalse(note1.equals(note2));
	}
	
	public void testEquals4() {
		Note note = new Note(NoteName.C1, NoteLength.QUARTER);
		
		assertFalse(note.equals(null));
	}
	
	public void testEquals5() {
		Note note = new Note(NoteName.C1, NoteLength.QUARTER);
		
		assertFalse(note.equals(""));
	}
	
	public void testToString() {
		NoteLength noteLength = NoteLength.WHOLE;
		NoteName name = NoteName.A1;
		Note note = new Note(name, noteLength);
		
		assertEquals("[Note] noteLength=" + noteLength + " name=" + name, note.toString());
	}
}
