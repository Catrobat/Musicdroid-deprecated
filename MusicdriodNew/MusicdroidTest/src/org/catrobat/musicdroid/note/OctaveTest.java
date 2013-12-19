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
}
