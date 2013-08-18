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

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.catrobat.musicdroid.tools.FileExtensionMethods;

import android.util.LogPrinter;

import junit.framework.TestCase;

/**
 * @author matthias schlesinger
 *
 */
public class LogTagTest extends TestCase {
	private static final String TAG = LogTagTest.class.getSimpleName();
	private String NEWLINE = System.getProperty("line.separator");
	private StringBuilder missingTagFiles = new StringBuilder();

	final String[] path_to_projects = {
											"../Musicdroid/src",
											"../MusicdroidTests/src",
											"../MusicdroidUiTest/src"
										};
	
	public LogTagTest() {
	}

	public void setUp() throws Exception {
	}
	
	public void testIfLogTagInEachClass() throws Exception{
		for (String path_to_project : path_to_projects) {
			File directory = new File(path_to_project);
			walkThroughDirectories(directory);
		}
		assertEquals(missingTagFiles.toString(), 0, missingTagFiles.length());
	}

	protected void walkThroughDirectories(File file_or_directory) {
	    if (file_or_directory.isDirectory()) {
	        String[] directoryContent = file_or_directory.list();
	        for (int index=0; index < directoryContent.length; index++) {
	        	walkThroughDirectories(new File(file_or_directory, directoryContent[index]));
	        }
	    } else {
	    	if(file_or_directory.getName().endsWith(".java"))
	    	{
	            checkClassForTag(file_or_directory);
	            checkLogCallForTag(file_or_directory);
	    	}
	    	  
	    }
	}
	
	protected void checkClassForTag(File file)
	{
	    StringBuilder fileContents = new StringBuilder((int)file.length());
	    Scanner scanner;
	    String lineSeparator = System.getProperty("line.separator");
		try {
			scanner = new Scanner(file);
	        while(scanner.hasNextLine()) {        
	            fileContents.append(scanner.nextLine() + lineSeparator);
	        }
	        String classAsString = fileContents.toString();
	        String tagString = buildTagString(file);
	        System.out.println(tagString);
	        if(!classAsString.contains(tagString))
	        {
	        	missingTagFiles.append(file.getAbsolutePath() + NEWLINE);
	        }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}		
	}
	
	protected void checkLogCallForTag(File file)
	{
	    StringBuilder fileContents = new StringBuilder((int)file.length());
	    Scanner scanner;
	    String lineSeparator = System.getProperty("line.separator");
		try {
			scanner = new Scanner(file);
	        while(scanner.hasNextLine()) {        
	            fileContents.append(scanner.nextLine() + lineSeparator);
	        }
	        String classAsString = fileContents.toString();
	        
	        String logPattern = "Log.(d|e|i|v|w)\\(TAG";
	        classAsString = classAsString.replaceAll(logPattern, "");
	        
	        if(classAsString.contains("Log."))
	        {
	        	missingTagFiles.append(file.getAbsolutePath() + NEWLINE);
	        }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}		
	}
	
	private String buildTagString(File file)
	{
		String filename = FileExtensionMethods.removeFileEnding(file.getName());
		return "static final String TAG = " + filename + ".class.getSimpleName()";
	}
}
