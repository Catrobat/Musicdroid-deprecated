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

public class ChordTest extends TestCase {

	public void testChord() {
		NoteName[] names = { NoteName.C1, NoteName.C2 };
		NoteLength noteLength = NoteLength.QUARTER;
		Chord chord = new Chord(noteLength, names);

		assertEquals(names.length, chord.size());
		assertEquals(noteLength, chord.getNoteLength());

		for (int i = 0; i < names.length; i++) {
			assertEquals(names[i], chord.getNoteName(i));
		}
	}

	public void testEquals1() {
		Chord chord1 = new Chord(NoteLength.QUARTER, new NoteName[] { NoteName.C1, NoteName.C2 });
		Chord chord2 = new Chord(NoteLength.QUARTER, new NoteName[] { NoteName.C1, NoteName.C2 });

		assertTrue(chord1.equals(chord2));
	}

	public void testEquals2() {
		Chord chord1 = new Chord(NoteLength.QUARTER, new NoteName[] { NoteName.C1, NoteName.C2 });
		Chord chord2 = new Chord(NoteLength.QUARTER, new NoteName[] { NoteName.C3, NoteName.C2 });

		assertFalse(chord1.equals(chord2));
	}

	public void testEquals3() {
		Chord chord1 = new Chord(NoteLength.QUARTER, new NoteName[] { NoteName.C1, NoteName.C2 });
		Chord chord2 = new Chord(NoteLength.WHOLE, new NoteName[] { NoteName.C1, NoteName.C2 });

		assertFalse(chord1.equals(chord2));
	}

	public void testEquals4() {
		Chord chord1 = new Chord(NoteLength.QUARTER, new NoteName[] { NoteName.C1, NoteName.C2 });
		Chord chord2 = new Chord(NoteLength.WHOLE, new NoteName[] { NoteName.C3, NoteName.C2 });

		assertFalse(chord1.equals(chord2));
	}

	public void testEquals5() {
		Chord chord = new Chord(NoteLength.QUARTER, new NoteName[] { NoteName.C1, NoteName.C2 });

		assertFalse(chord.equals(null));
	}

	public void testEquals6() {
		Chord chord = new Chord(NoteLength.QUARTER, new NoteName[] { NoteName.C1, NoteName.C2 });

		assertFalse(chord.equals(""));
	}

	public void testToString() {
		NoteLength noteLength = NoteLength.WHOLE;
		NoteName[] names = { NoteName.C1, NoteName.C2 };
		Chord chord = new Chord(noteLength, names);

		assertEquals("[Chord] noteLength=" + noteLength + " namesCount=" + names.length, chord.toString());
	}
}
