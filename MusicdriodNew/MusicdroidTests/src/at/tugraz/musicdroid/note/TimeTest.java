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
package at.tugraz.musicdroid.note;

import at.tugraz.musicdroid.note.NoteLength;
import at.tugraz.musicdroid.note.Tact;
import junit.framework.TestCase;

public class TimeTest extends TestCase {

	public void testTime1() {
		Tact tact = new Tact();
		
		assertEquals(4, tact.getBeatsPerTact());
		assertEquals(NoteLength.QUARTER, tact.getNoteLength());
	}
	
	public void testTime2() {
		Tact tact = new Tact(19, NoteLength.EIGHT);
		
		assertEquals(19, tact.getBeatsPerTact());
		assertEquals(NoteLength.EIGHT, tact.getNoteLength());
	}
	
	public void testEquals1() {
		Tact tact1 = new Tact(19, NoteLength.EIGHT);
		Tact tact2 = new Tact(19, NoteLength.EIGHT);
		
		assertTrue(tact1.equals(tact2));
	}
	
	public void testEquals2() {
		Tact tact1 = new Tact(19, NoteLength.EIGHT);
		Tact tact2 = new Tact(20, NoteLength.EIGHT);
		
		assertFalse(tact1.equals(tact2));
	}
	
	public void testEquals3() {
		Tact tact1 = new Tact(19, NoteLength.EIGHT);
		Tact tact2 = new Tact(20, NoteLength.SIXTEENTH);
		
		assertFalse(tact1.equals(tact2));
	}
	
	public void testEquals4() {
		Tact tact = new Tact();
		
		assertFalse(tact.equals(null));
	}
	
	public void testEquals5() {
		Tact tact = new Tact();
		
		assertFalse(tact.equals(""));
	}
	
	public void testToString() {
		int beatsPerTact = 20;
		NoteLength noteLength = NoteLength.SIXTEENTH;
		Tact tact = new Tact(beatsPerTact, noteLength);
		
		assertEquals("[Tact] beatsPerTact=" + beatsPerTact + " noteLength=" + noteLength, tact.toString());
	}
}
