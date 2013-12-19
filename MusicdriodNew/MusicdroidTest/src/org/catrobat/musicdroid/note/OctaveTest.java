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

import java.util.Arrays;

public class OctaveTest extends TestCase {

	public void testGetFirstNoteNameOfOctave() {
		Octave[] octaves = new Octave[] {
				Octave.SUB_CONTRA_OCTAVE, Octave.CONTRA_OCTAVE,
				Octave.GREAT_OCTAVE, Octave.SMALL_OCTAVE,
				Octave.ONE_LINE_OCTAVE, Octave.TWO_LINE_OCTAVE,
				Octave.THREE_LINE_OCTAVE, Octave.FOUR_LINE_OCTAVE,
				Octave.FIVE_LINE_OCTAVE};
		
		NoteName[] noteNames = new NoteName[] {
				NoteName.A0, NoteName.C1,
				NoteName.C2, NoteName.C3,
				NoteName.C4, NoteName.C5,
				NoteName.C6, NoteName.C7,
				NoteName.C8};
		
		for (int i = 0; i < octaves.length; i++) {
			assertEquals(noteNames[i], octaves[i].getFirstNoteNameOfOctave());
		}
	}
	
	public void testNext1() {
		Octave[] octaves = new Octave[] {
				Octave.SUB_CONTRA_OCTAVE, Octave.CONTRA_OCTAVE,
				Octave.GREAT_OCTAVE, Octave.SMALL_OCTAVE,
				Octave.ONE_LINE_OCTAVE, Octave.TWO_LINE_OCTAVE,
				Octave.THREE_LINE_OCTAVE, Octave.FOUR_LINE_OCTAVE,
				Octave.FIVE_LINE_OCTAVE};
		
		Octave octave = Octave.SUB_CONTRA_OCTAVE;
		
		for (int i = 0; i < octaves.length; i++) {
			assertEquals(octaves[i], octave);
			octave = octave.next();
		}
	}
	
	public void testNext2() {
		Octave octave = Octave.FIVE_LINE_OCTAVE;
		assertEquals(octave, octave.next());
	}
	
	public void testPrevious1() {
		Octave[] octaves = new Octave[] {
				Octave.FIVE_LINE_OCTAVE, Octave.FOUR_LINE_OCTAVE,
				Octave.THREE_LINE_OCTAVE, Octave.TWO_LINE_OCTAVE,
				Octave.ONE_LINE_OCTAVE, Octave.SMALL_OCTAVE,
				Octave.GREAT_OCTAVE, Octave.CONTRA_OCTAVE,
				Octave.SUB_CONTRA_OCTAVE
		};
		
		Octave octave = Octave.FIVE_LINE_OCTAVE;
		
		for (int i = 0; i < octaves.length; i++) {
			assertEquals(octaves[i], octave);
			octave = octave.previous();
		}
	}
	
	public void testPrevious2() {
		Octave octave = Octave.SUB_CONTRA_OCTAVE;
		assertEquals(octave, octave.previous());
	}
	
	public void testGetNoteNames1() {
		NoteName[] noteNames = new NoteName[] {
				NoteName.A0, NoteName.A0S,
				NoteName.B0};
		Octave octave = Octave.SUB_CONTRA_OCTAVE;
		
		assertTrue(Arrays.equals(noteNames, octave.getNoteNames()));
	}
	
	public void testGetNoteNames2() {
		NoteName[] noteNames = new NoteName[] {
				NoteName.C1, NoteName.C1S,
				NoteName.D1, NoteName.D1S,
				NoteName.E1, NoteName.F1,
				NoteName.F1S, NoteName.G1,
				NoteName.G1S, NoteName.A1,
				NoteName.A1S, NoteName.B1};
		Octave octave = Octave.CONTRA_OCTAVE;
		
		assertTrue(Arrays.equals(noteNames, octave.getNoteNames()));
	}
	
	public void testGetNoteNames3() {
		NoteName[] noteNames = new NoteName[] {
				NoteName.C2, NoteName.C2S,
				NoteName.D2, NoteName.D2S,
				NoteName.E2, NoteName.F2,
				NoteName.F2S, NoteName.G2,
				NoteName.G2S, NoteName.A2,
				NoteName.A2S, NoteName.B2};
		Octave octave = Octave.GREAT_OCTAVE;
		
		assertTrue(Arrays.equals(noteNames, octave.getNoteNames()));
	}
	
	public void testGetNoteNames4() {
		NoteName[] noteNames = new NoteName[] {
				NoteName.C3, NoteName.C3S,
				NoteName.D3, NoteName.D3S,
				NoteName.E3, NoteName.F3,
				NoteName.F3S, NoteName.G3,
				NoteName.G3S, NoteName.A3,
				NoteName.A3S, NoteName.B3};
		Octave octave = Octave.SMALL_OCTAVE;
		
		assertTrue(Arrays.equals(noteNames, octave.getNoteNames()));
	}
	
	public void testGetNoteNames5() {
		NoteName[] noteNames = new NoteName[] {
				NoteName.C4, NoteName.C4S,
				NoteName.D4, NoteName.D4S,
				NoteName.E4, NoteName.F4,
				NoteName.F4S, NoteName.G4,
				NoteName.G4S, NoteName.A4,
				NoteName.A4S, NoteName.B4};
		Octave octave = Octave.ONE_LINE_OCTAVE;
		
		assertTrue(Arrays.equals(noteNames, octave.getNoteNames()));
	}
	
	public void testGetNoteNames6() {
		NoteName[] noteNames = new NoteName[] {
				NoteName.C5, NoteName.C5S,
				NoteName.D5, NoteName.D5S,
				NoteName.E5, NoteName.F5,
				NoteName.F5S, NoteName.G5,
				NoteName.G5S, NoteName.A5,
				NoteName.A5S, NoteName.B5};
		Octave octave = Octave.TWO_LINE_OCTAVE;
		
		assertTrue(Arrays.equals(noteNames, octave.getNoteNames()));
	}
	
	public void testGetNoteNames7() {
		NoteName[] noteNames = new NoteName[] {
				NoteName.C6, NoteName.C6S,
				NoteName.D6, NoteName.D6S,
				NoteName.E6, NoteName.F6,
				NoteName.F6S, NoteName.G6,
				NoteName.G6S, NoteName.A6, 
				NoteName.A6S, NoteName.B6};
		Octave octave = Octave.THREE_LINE_OCTAVE;
		
		assertTrue(Arrays.equals(noteNames, octave.getNoteNames()));
	}
	
	public void testGetNoteNames8() {
		NoteName[] noteNames = new NoteName[] {
				NoteName.C7, NoteName.C7S,
				NoteName.D7, NoteName.D7S,
				NoteName.E7, NoteName.F7,
				NoteName.F7S, NoteName.G7,
				NoteName.G7S, NoteName.A7,
				NoteName.A7S, NoteName.B7};
		Octave octave = Octave.FOUR_LINE_OCTAVE;
		
		assertTrue(Arrays.equals(noteNames, octave.getNoteNames()));
	}
	
	public void testGetNoteNames9() {
		NoteName[] noteNames = new NoteName[] {
				NoteName.C8};
		Octave octave = Octave.FIVE_LINE_OCTAVE;
		
		assertTrue(Arrays.equals(noteNames, octave.getNoteNames()));
	}
}
