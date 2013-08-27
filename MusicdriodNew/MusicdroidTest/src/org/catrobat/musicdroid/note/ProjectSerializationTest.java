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

import android.os.Environment;
import android.text.format.Time;

import junit.framework.TestCase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ProjectSerializationTest extends TestCase {

	public void testSerialize() throws IOException, ClassNotFoundException {
		Project project = new Project();
		new Time();
		Track track = new Track();
		project.addTrack(track);
		File file = new File(Environment.getExternalStorageDirectory(), "projectSerializedTest");

		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
		out.writeObject(project);
		out.close();

		ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
		Project readProject = (Project) in.readObject();
		in.close();

		file.delete();

		assertEquals(project, readProject);
	}
}
