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
package org.catrobat.musicdroid.test.tooltests;

import android.test.AndroidTestCase;

import org.catrobat.musicdroid.tools.FileExtensionMethods;

/**
 * @author matthias
 * 
 */
public class FileExtensionMethodsTest extends AndroidTestCase {

	public void testGetFilenameFromPath() {
		String path = "/dummy/test/check.xml";
		assertTrue((FileExtensionMethods.getFilenameFromPath(path)).equals("check"));

		path = "/dummy/test/check";
		assertTrue((FileExtensionMethods.getFilenameFromPath(path)).equals("check"));
	}

	public void testRemoveExtension() {
		String filename = "check.xml";
		assertTrue((FileExtensionMethods.removeFileEnding(filename)).equals("check"));

		filename = "check";
		assertTrue((FileExtensionMethods.removeFileEnding(filename)).equals("check"));
	}
}
