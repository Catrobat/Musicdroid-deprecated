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

public enum NoteLength {
	WHOLE(4f), HALF(2f), QUARTER(1f), EIGHT(1 / 2f), SIXTEENTH(1 / 4f);

	// http://stackoverflow.com/questions/2467995/actual-note-duration-from-midi-duration
	protected static final double DEFAULT_DURATION = 384 / 48 * 60;

	private double length;

	private NoteLength(double length) {
		this.length = length;
	}

	public double getLength() {
		return length;
	}

	public static long calculateDuration(NoteLength noteLength) {
		return Math.round(DEFAULT_DURATION * noteLength.length);
	}

	public static NoteLength getNoteLengthFromDuration(long duration) {
		NoteLength[] noteLengths = NoteLength.values();

		double length = duration / DEFAULT_DURATION;

		for (int i = 0; i < noteLengths.length; i++) {
			if (noteLengths[i].getLength() == length) {
				return noteLengths[i];
			}
		}

		return QUARTER;
	}
}
