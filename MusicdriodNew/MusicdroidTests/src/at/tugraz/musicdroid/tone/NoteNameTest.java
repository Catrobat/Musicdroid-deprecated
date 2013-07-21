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

public class NoteNameTest extends TestCase {

	public void testMidi() {
		assertEquals(36, NoteName.C1.getMidi());
		assertEquals(48, NoteName.C2.getMidi());
		assertEquals(60, NoteName.C3.getMidi());
		assertEquals(72, NoteName.C4.getMidi());
		assertEquals(84, NoteName.C5.getMidi());
	}
	
	public void testNext() {
		NoteName a5s = NoteName.A5S;
		NoteName b5 = NoteName.B5;
		
		assertEquals(b5, a5s.next());
		assertEquals(b5, b5.next());
	}
	
	public void testPrevious() {
		NoteName c1 = NoteName.C1;
		NoteName c1s = NoteName.C1S;
		
		assertEquals(c1, c1.previous());
		assertEquals(c1, c1s.previous());
	}
}
