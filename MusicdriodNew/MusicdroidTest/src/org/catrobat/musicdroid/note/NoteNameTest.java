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

public class NoteNameTest extends TestCase {

	public void testMidi() {
		NoteName[] noteNames = new NoteName[] { NoteName.C1, NoteName.C2, NoteName.C3, NoteName.C4, NoteName.C5,
				NoteName.C6, NoteName.C7, NoteName.C8, };

		int startMidi = 24;
		int increment = 12;

		for (int i = 0; i < noteNames.length; i++) {
			int midi = startMidi + i * increment;
			assertEquals(midi, noteNames[i].getMidi());
		}
	}

	public void testIsSigned1() {
		NoteName c1 = NoteName.C1;

		assertFalse(c1.isSigned());
	}

	public void testIsSigned2() {
		NoteName c1s = NoteName.C1S;

		assertTrue(c1s.isSigned());
	}

	public void testCalculateDistance1() {
		NoteName c1 = NoteName.C1S;
		NoteName c1s = NoteName.C1;

		assertEquals(-1, NoteName.calculateDistance(c1, c1s));
	}

	public void testCalculateDistance2() {
		NoteName c1 = NoteName.C1S;
		NoteName c1s = NoteName.C1;

		assertEquals(1, NoteName.calculateDistance(c1s, c1));
	}

	public void testCalculateDistance3() {
		NoteName c1 = NoteName.C1;

		assertEquals(0, NoteName.calculateDistance(c1, c1));
	}

	public void testGetNoteNameFromMidiValue1() {
		NoteName expectedNoteName = NoteName.A0;
		int midiValue = expectedNoteName.getMidi();

		NoteName actualNoteName = NoteName.getNoteNameFromMidiValue(midiValue);

		assertEquals(actualNoteName, expectedNoteName);
	}

	public void testGetNoteNameFromMidiValue2() {
		NoteName expectedNoteName = NoteName.C8;
		int midiValue = expectedNoteName.getMidi();

		NoteName actualNoteName = NoteName.getNoteNameFromMidiValue(midiValue);

		assertEquals(actualNoteName, expectedNoteName);
	}

	public void testGetNoteNameFromMidiValue3() {
		NoteName expectedNoteName = NoteName.C4;
		int midiValue = expectedNoteName.getMidi();

		NoteName actualNoteName = NoteName.getNoteNameFromMidiValue(midiValue);

		assertEquals(actualNoteName, expectedNoteName);
	}

	public void testCalculateDistanceCountingNoneSignedNotesOnly1() {
		NoteName n1 = NoteName.D1;
		NoteName n2 = NoteName.C1S;

		assertEquals(-1, NoteName.calculateDistanceCountingNoneSignedNotesOnly(n1, n2));
	}

	public void testCalculateDistanceCountingNoneSignedNotesOnly2() {
		NoteName n1 = NoteName.C1;
		NoteName n2 = NoteName.C1S;

		assertEquals(0, NoteName.calculateDistanceCountingNoneSignedNotesOnly(n1, n2));
	}

	public void testCalculateDistanceCountingNoneSignedNotesOnly3() {
		NoteName n1 = NoteName.D3;
		NoteName n2 = NoteName.B3;

		assertEquals(5, NoteName.calculateDistanceCountingNoneSignedNotesOnly(n1, n2));
	}
}
