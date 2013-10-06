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

	private static float SIXTEENTH_DURATION = 1 / 4f;
	private static float EIGHT_DURATION = 1 / 2f;
	private static float QUARTER_DURATION = 1f;
	private static float HALF_DURATION = 2f;
	private static float WHOLE_DURATION = 4f;

	public void testCalculateDuration1() {
		NoteLength noteLength = NoteLength.SIXTEENTH;
		int beatsPerMinute = 60;

		int expected = Math.round(beatsPerMinute * SIXTEENTH_DURATION);
		int actual = NoteLength.calculateDuration(noteLength, beatsPerMinute);

		assertEquals(expected, actual);
	}

	public void testCalculateDuration2() {
		NoteLength noteLength = NoteLength.EIGHT;
		int beatsPerMinute = 60;

		int expected = Math.round(beatsPerMinute * EIGHT_DURATION);
		int actual = NoteLength.calculateDuration(noteLength, beatsPerMinute);

		assertEquals(expected, actual);
	}

	public void testCalculateDuration3() {
		NoteLength noteLength = NoteLength.QUARTER;
		int beatsPerMinute = 60;

		int expected = Math.round(beatsPerMinute * QUARTER_DURATION);
		int actual = NoteLength.calculateDuration(noteLength, beatsPerMinute);

		assertEquals(expected, actual);
	}

	public void testCalculateDuration4() {
		NoteLength noteLength = NoteLength.HALF;
		int beatsPerMinute = 60;

		int expected = Math.round(beatsPerMinute * HALF_DURATION);
		int actual = NoteLength.calculateDuration(noteLength, beatsPerMinute);

		assertEquals(expected, actual);
	}

	public void testCalculateDuration5() {
		NoteLength noteLength = NoteLength.WHOLE;
		int beatsPerMinute = 60;

		int expected = Math.round(beatsPerMinute * WHOLE_DURATION);
		int actual = NoteLength.calculateDuration(noteLength, beatsPerMinute);

		assertEquals(expected, actual);
	}
}
