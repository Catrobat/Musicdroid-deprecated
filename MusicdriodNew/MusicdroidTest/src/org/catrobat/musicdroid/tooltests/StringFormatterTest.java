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

import org.catrobat.musicdroid.tools.StringFormatter;

/**
 * @author matthias
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
}
