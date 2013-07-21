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
package at.tugraz.musicdroid.projecttest;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Scanner;

import junit.framework.TestCase;

public class LicenseTest extends TestCase {
	
	private static final boolean REPLACE_LICENSE_TEXT = false;
	private String NEWLINE = System.getProperty("line.separator");
	private StringBuilder missing_license_text_files = new StringBuilder();

	final String[] path_to_projects = {
											"../Musicdroid/src",
											"../MusicdroidTests/src",
											"../MusicdroidUiTest/src"
										};
	private  String[] license = {};
	
	private String license_string = "";
	
	public LicenseTest() {

	}

	public void setUp() throws Exception {
	    Scanner scanner = null;
		try {
			scanner = new Scanner(new FileInputStream("license"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    try {	    	
	      while (scanner.hasNextLine()){
	    	  license_string += scanner.nextLine() + NEWLINE;
	      }
	    }
	    finally{
	    	if(scanner!= null)
	    		scanner.close();
	    }
		license = license_string.split(NEWLINE);
	}
	
	public void testIfGplLicenseIsInAllFiles() throws Exception{
		for (String path_to_project : path_to_projects) {
			File directory = new File(path_to_project);
			walkThroughDirectories(directory);
		}
		assertEquals(missing_license_text_files.toString(), 0, missing_license_text_files.length());
	}

	protected void walkThroughDirectories(File file_or_directory) {
	    if (file_or_directory.isDirectory()) {
	        String[] directoryContent = file_or_directory.list();
	        for (int index=0; index < directoryContent.length; index++) {
	        	walkThroughDirectories(new File(file_or_directory, directoryContent[index]));
	        }
	    } else {
	    	if(REPLACE_LICENSE_TEXT == true) {
	    		replaceWithNewLicenseText(file_or_directory);
	    	} 
	        checkFileForLicense(file_or_directory);
	    }
	}
	
	protected void checkFileForLicense(File file)
	{
		System.out.println(file.getAbsolutePath());
		try
		{
			FileInputStream fileInputStream = new FileInputStream(file);
			DataInputStream dataInputStream = new DataInputStream(fileInputStream);
	        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(dataInputStream));
		    
	        String lineFromSourceFile;
		    int indexFromLicenseString = 0;
		    while ((lineFromSourceFile = bufferedReader.readLine()) != null && indexFromLicenseString < license.length)   {
		    	if(!license[indexFromLicenseString].trim().contentEquals(lineFromSourceFile.trim()))
		    	{
		    		missing_license_text_files.append(file.getAbsolutePath() + NEWLINE);
		    		break;
		    	}
//		    	assertEquals(license[indexFromLicenseString].trim(), lineFromSourceFile.trim());
		    	indexFromLicenseString++;
		    }
		    dataInputStream.close();
		}
		catch (Exception e) {
			assertTrue(false);
		}
	}
	
	
	public void replaceWithNewLicenseText(File file) {
		System.out.println(file.getAbsolutePath());
		
		StringBuilder sourceText = new StringBuilder();
	    
	    Scanner scanner = null;
		try {
			scanner = new Scanner(new FileInputStream(file));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    try {	    	
	      while (scanner.hasNextLine()){
	        sourceText.append(scanner.nextLine() + NEWLINE);
	      }
	    }
	    finally{
	      scanner.close();
	    }
	    sourceText.trimToSize();
		try
		{
			if(sourceText.indexOf(license_string) == -1) {
				int startOfOldSourceText = sourceText.indexOf("/**");
				int endOfOldSourceText = sourceText.indexOf(" */");
				
				if(startOfOldSourceText == 0 && endOfOldSourceText > 0) {
					sourceText.replace(0, endOfOldSourceText + 3, license_string);
				} else {
					sourceText.insert(0, license_string);
				}
				Writer out = new OutputStreamWriter(new FileOutputStream(file));
			    try {
			      out.write(sourceText.toString().toCharArray());
			    }
			    finally {
			      out.close();
			    }
			}
		}
		catch (Exception e) {
			assertTrue(false);
		}
	}
}
