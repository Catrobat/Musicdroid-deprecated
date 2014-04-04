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
package org.catrobat.musicdroid.test.note;

import android.test.AndroidTestCase;

import org.catrobat.musicdroid.note.Instrument;
import org.catrobat.musicdroid.note.Project;
import org.catrobat.musicdroid.note.Track;
import org.catrobat.musicdroid.test.note.testutil.ProjectTestDataFactory;
import org.catrobat.musicdroid.test.note.testutil.TrackTestDataFactory;

public class ProjectTest extends AndroidTestCase {

	public void testGetName() {
		Project project = ProjectTestDataFactory.createProject();

		assertEquals(Project.DEFAULT_NAME, project.getName());
	}

	public void testGetBeatsPerMinute() {
		Project project = ProjectTestDataFactory.createProject();

		assertEquals(Project.DEFAULT_BEATS_PER_MINUTE, project.getBeatsPerMinute());
	}

	public void testAddTrack() {
		Project project = ProjectTestDataFactory.createProject();
		project.addTrack(TrackTestDataFactory.createTrack());

		assertEquals(1, project.size());
	}

	public void testGetTrack() {
		Project project = ProjectTestDataFactory.createProject();
		Track track = TrackTestDataFactory.createTrack();
		project.addTrack(track);

		assertEquals(track, project.getTrack(0));
	}

	public void testRemoveTrack() {
		Project project = ProjectTestDataFactory.createProject();
		Track track = TrackTestDataFactory.createTrack();
		project.addTrack(track);
		project.removeTrack(track);

		assertEquals(0, project.size());
	}

	public void testEquals1() {
		Project project1 = ProjectTestDataFactory.createProject();
		Project project2 = ProjectTestDataFactory.createProject();

		assertTrue(project1.equals(project2));
	}

	public void testEquals2() {
		Project project1 = ProjectTestDataFactory.createProject("A Name");
		Project project2 = ProjectTestDataFactory.createProject("A Different Name");

		assertFalse(project1.equals(project2));
	}

	public void testEquals3() {
		Project project1 = ProjectTestDataFactory.createProjectWithTrack();
		Project project2 = ProjectTestDataFactory.createProjectWithTrack();

		assertTrue(project1.equals(project2));
	}

	public void testEquals4() {
		Project project1 = ProjectTestDataFactory.createProjectWithTrack();
		Project project2 = ProjectTestDataFactory.createProjectWithTrack(Instrument.APPLAUSE);

		assertFalse(project1.equals(project2));
	}

	public void testEquals5() {
		Project project1 = ProjectTestDataFactory.createProjectWithTrack();
		Project project2 = ProjectTestDataFactory.createProject();

		assertFalse(project1.equals(project2));
	}

	public void testEquals6() {
		Project project = ProjectTestDataFactory.createProject();

		assertFalse(project.equals(null));
	}

	public void testEquals7() {
		Project project = ProjectTestDataFactory.createProject();

		assertFalse(project.equals(""));
	}

	public void testToString() {
		Project project = ProjectTestDataFactory.createProject();
		String expectedString = "[Project] name=" + project.getName() + " beatsPerMinute="
				+ project.getBeatsPerMinute() + " trackCount=" + project.size();

		assertEquals(expectedString, project.toString());
	}
}
