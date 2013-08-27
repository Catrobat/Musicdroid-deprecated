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
package org.catrobat.musicdroid.tooltests;

import junit.framework.TestCase;

import org.catrobat.musicdroid.preferences.PreferenceManager;
import org.catrobat.musicdroid.tools.StringFormatter;

import java.util.Random;

/**
 * @author matthias schlesinger
 * 
 */
public class StringFormatterTest extends TestCase {

	public void testStringFormatter() {
		int duration = 5;
		assertTrue(StringFormatter.durationStringFromInt(duration).equals("00:05"));

		duration = 60;
		assertTrue(StringFormatter.durationStringFromInt(duration).equals("01:00"));

		duration = 990;
		assertTrue(StringFormatter.durationStringFromInt(duration).equals("16:30"));

		duration = 3600;
		assertTrue(StringFormatter.durationStringFromInt(duration).equals("1:00:00"));
	}

	public void testBuildDrumExportDurationString() {
		int progress = 20;
		int beatsPerLoop = 4;

		int seconds = beatsPerLoop * progress * 60
				/ PreferenceManager.getInstance().getPreference(PreferenceManager.METRONOM_BPM_KEY);

		String referenceString = "Number of Loops: " + progress + " / Duration: " + seconds + " Sec";

		String formattedString = StringFormatter.buildDrumExportDurationString(progress, beatsPerLoop);

		assertEquals(referenceString, formattedString);
	}

	public void testBeatArrayToString() {
		int[] beatArray = new int[16];

		Random randomGenerator = new Random();
		for (int idx = 0; idx <= 15; ++idx) {
			beatArray[idx] = randomGenerator.nextInt(2);
		}

		String referenceString = "BeatArray = " + beatArray[0] + " " + beatArray[1] + " " + beatArray[2] + " "
				+ beatArray[3] + " " + beatArray[4] + " " + beatArray[5] + " " + beatArray[6] + " " + beatArray[7]
				+ " " + beatArray[8] + " " + beatArray[9] + " " + beatArray[10] + " " + beatArray[11] + " "
				+ beatArray[12] + " " + beatArray[13] + " " + beatArray[14] + " " + beatArray[15] + " ";

		String generatedString = StringFormatter.beatArrayToString(beatArray);

		System.out.println("Reference " + referenceString);
		System.out.println("Generated " + generatedString);

		assertEquals(referenceString, generatedString);
	}
}
