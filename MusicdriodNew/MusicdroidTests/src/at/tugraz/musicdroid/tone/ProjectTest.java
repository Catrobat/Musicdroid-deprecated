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

public class ProjectTest extends TestCase {

	public void testAddTrack() {
		Project project = new Project();
		
		project.addTrack(new Track());
		
		assertEquals(1, project.size());
	}

	public void testRemoveTrack() {
		Project project = new Project();
		
		Track track = new Track();
		project.addTrack(track);
		project.removeTrack(track);
		
		assertEquals(0, project.size());
	}
	
	public void testEquals1() {
		Track track1 = new Track();
		track1.addSymbol(new Break(NoteLength.HALF));
		
		Track track2 = new Track();
		track2.addSymbol(new Break(NoteLength.HALF));
		
		assertTrue(track1.equals(track2));
	}
	
	public void testEquals2() {
		Project project1 = new Project();
		project1.addTrack(new Track());
		
		Project project2 = new Project();
		project2.addTrack(new Track());
		
		assertTrue(project1.equals(project2));
	}
	
	public void testEquals3() {
		Project project1 = new Project();
		project1.addTrack(new Track());
		
		Project project2 = new Project();
		
		assertFalse(project1.equals(project2));
	}
	
	public void testEquals4() {
		Project project = new Project();
		
		assertFalse(project.equals(null));
	}
	
	public void testEquals5() {
		Project project = new Project();
		
		assertFalse(project.equals(""));
	}
	
	public void testToString() {
		Project project = new Project();
		
		assertEquals("[Project] trackCount=" + project.size(), project.toString());
	}
}
