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
package org.catrobat.musicdroid.projecttest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import org.catrobat.musicdroid.tools.Utils;
import junit.framework.TestCase;

/**
 * @author matthias schlesinger
 *
 */
public class LogTagTest extends TestCase {
	private static final String NEWLINE = System.getProperty("line.separator");
	private static final String ANYTHING = ".*";
	private static final String OPENING_BRACKET = "\\(";
	private static final String LOG_CALL = "Log\\.(d|e|i|v|w)";
	private static final String LOG_TAG = "TAG";
	private static final String COMMA = ",";
	
	private StringBuilder untaggedLogs = new StringBuilder();

	private final String[] pathToProjects = {
											"../Musicdroid/src",
											"../MusicdroidTest/src",
											"../MusicdroidUiTest/src"
										};
	
	public LogTagTest() {
	}

	public void setUp() throws Exception {
	}
	
	public void testLogsUseTagMember() throws Exception{
		for (String pathToProject : pathToProjects)
		{
			File directory = new File(pathToProject);
			assertTrue("Couldn't find directory: " + pathToProject, directory.exists() && 
																	directory.isDirectory());
			assertTrue("Couldn't read directory: " + pathToProject, directory.canRead());
	
			List<File> filesToCheck = 
					Utils.getFilesFromDirectoryByExtension(directory, new String[] { ".java", });
			for (File file : filesToCheck) {
				checkFileForLogs(file);
			}
		}
		assertEquals(untaggedLogs.toString(), 0, untaggedLogs.length());
	}
	
	private void checkFileForLogs(File file) throws IOException
	{
		assertTrue("Could not read file " + file.getAbsolutePath(), file.exists() && file.canRead());
				
		BufferedReader reader = new BufferedReader(new FileReader(file));
		int lineCount = 1;
		String line = null;

		String logPattern = ANYTHING + LOG_CALL + OPENING_BRACKET + ANYTHING;
		String validTaggedLogPattern = ANYTHING + LOG_CALL + OPENING_BRACKET + LOG_TAG + COMMA + ANYTHING;
		
		while ((line = reader.readLine()) != null) {			
			if (line.matches(logPattern) && !line.matches(validTaggedLogPattern)) {
				untaggedLogs.append(file.getAbsolutePath() + " in line " + lineCount + NEWLINE);
			}
			++lineCount;
		}
		reader.close();
	}
}
