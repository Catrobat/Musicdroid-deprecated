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
package at.tugraz.musicdroid.tone;

import junit.framework.TestCase;

public class ToneTest extends TestCase {

	public void testTone() {
		NoteName name = NoteName.C1;
		NoteLength noteLength = NoteLength.EIGHT;
		Tone tone = new Tone(name, noteLength);
		
		assertEquals(name, tone.getNoteName());
		assertEquals(noteLength, tone.getNoteLength());
	}
	
	public void testHalfToneUp() {
		Tone c1 = new Tone(NoteName.C1, NoteLength.QUARTER);
		Tone c1s = c1.halfToneUp();
		
		assertEquals(c1.getNoteLength(), c1s.getNoteLength());
		assertEquals(c1.getNoteName().next(), c1s.getNoteName());
	}

	public void testHalfToneDown() {
		Tone c1s = new Tone(NoteName.C1S, NoteLength.QUARTER);
		Tone c1 = c1s.halfToneDown();
		
		assertEquals(c1.getNoteLength(), c1s.getNoteLength());
		assertEquals(c1s.getNoteName().previous(), c1.getNoteName());
	}
	
	public void testEquals1() {
		Tone tone1 = new Tone(NoteName.C1, NoteLength.QUARTER);
		Tone tone2 = new Tone(NoteName.C1, NoteLength.QUARTER);
		
		assertTrue(tone1.equals(tone2));
	}
	
	public void testEquals2() {
		Tone tone1 = new Tone(NoteName.C1, NoteLength.QUARTER);
		Tone tone2 = new Tone(NoteName.D1, NoteLength.QUARTER);
		
		assertFalse(tone1.equals(tone2));
	}
	
	public void testEquals3() {
		Tone tone1 = new Tone(NoteName.C1, NoteLength.QUARTER);
		Tone tone2 = new Tone(NoteName.C1, NoteLength.HALF);
		
		assertFalse(tone1.equals(tone2));
	}
	
	public void testEquals4() {
		Tone tone = new Tone(NoteName.C1, NoteLength.QUARTER);
		
		assertFalse(tone.equals(null));
	}
	
	public void testEquals5() {
		Tone tone = new Tone(NoteName.C1, NoteLength.QUARTER);
		
		assertFalse(tone.equals(""));
	}
	
	public void testToString() {
		NoteLength noteLength = NoteLength.WHOLE;
		NoteName name = NoteName.A1;
		Tone tone = new Tone(name, noteLength);
		
		assertEquals("[Tone] noteLength=" + noteLength + " name=" + name, tone.toString());
	}
}
