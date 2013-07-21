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
package at.tugraz.musicdroid.tone;

import java.io.Serializable;

public class Time implements Serializable {

	private static final long serialVersionUID = 888797518903394570L;

	private int beatsPerTact;
	private NoteLength noteLength;

	public Time() {
		this(4, NoteLength.QUARTER);
	}

	public Time(int beatsPerTact, NoteLength noteLength) {
		this.beatsPerTact = beatsPerTact;
		this.noteLength = noteLength;
	}

	public int getBeatsPerTact() {
		return beatsPerTact;
	}

	public NoteLength getNoteLength() {
		return noteLength;
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof Time)) {
			return false;
		}

		Time time = (Time) obj;

		return (beatsPerTact == time.getBeatsPerTact())
				&& noteLength.equals(time.getNoteLength());
	}

	@Override
	public String toString() {
		return "[Time] beatsPerTact=" + beatsPerTact + " noteLength="
				+ noteLength;
	}
}
