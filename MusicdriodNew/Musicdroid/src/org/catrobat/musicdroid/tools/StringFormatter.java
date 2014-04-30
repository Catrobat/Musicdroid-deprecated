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
package org.catrobat.musicdroid.tools;

/**
 * @author matthias schlesinger
 */
public final class StringFormatter {
	private static final String SEPERATOR = ":";

	private StringFormatter() {
	}

	public static String durationStringFromInt(int duration) {
		int hours = duration / 3600;
		int remainder = duration - hours * 3600;
		int minutes = remainder / 60;
		remainder = remainder - minutes * 60;
		int seconds = remainder;

		String durationString = "";

		if (hours > 0) {
			durationString = "" + hours + SEPERATOR;
		}

		String min = "" + minutes;
		String sec = "" + seconds;

		if (minutes < 10) {
			min = "0" + min;
		}
		if (seconds < 10) {
			sec = "0" + sec;
		}

		return durationString + min + SEPERATOR + sec;
	}
}
