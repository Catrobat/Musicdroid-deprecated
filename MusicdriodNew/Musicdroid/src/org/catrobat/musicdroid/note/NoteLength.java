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

import java.util.LinkedList;
import java.util.List;

public enum NoteLength {
	WHOLE(4f), HALF(2f), QUARTER(1f), EIGHT(1 / 2f), SIXTEENTH(1 / 4f);

	// http://stackoverflow.com/questions/2467995/actual-note-duration-from-midi-duration
	protected static final double DEFAULT_DURATION = 384 / 48 * 60;
	protected static final NoteLength SMALLEST_NOTE_LENGTH = SIXTEENTH;

	private double length;
	private long tickDuration;

	private NoteLength(double length) {
		this.length = length;
		this.tickDuration = Math.round(DEFAULT_DURATION * this.length);
	}

	public double getLength() {
		return length;
	}

	public long getTickDuration() {
		return tickDuration;
	}

	public static List<NoteLength> getNoteLengthsFromTickDuration(long duration) {
		NoteLength[] allNoteLengths = NoteLength.values();
		LinkedList<NoteLength> noteLengthsList = new LinkedList<NoteLength>();

		while (duration > 0) {
			for (int i = 0; i < allNoteLengths.length; i++) {
				long ticks = allNoteLengths[i].getTickDuration();

				if (ticks <= duration) {
					duration = duration - ticks;
					noteLengthsList.add(allNoteLengths[i]);
					i--;
				}
			}

			if (duration < SMALLEST_NOTE_LENGTH.getTickDuration()) {
				duration = 0;
			}
		}

		return noteLengthsList;
	}

	public static long getTickDurationFromNoteLengths(List<NoteLength> noteLengthsList) {
		long duration = 0;

		for (NoteLength noteLength : noteLengthsList) {
			duration += noteLength.getTickDuration();
		}

		return duration;
	}
}
