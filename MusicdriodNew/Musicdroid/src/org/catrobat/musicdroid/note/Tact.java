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

import java.io.Serializable;

public class Tact implements Serializable {
	private static final long serialVersionUID = 888797518903394570L;

	private int beatsPerTact;
	private NoteLength noteLength;

	public Tact() {
		this(4, NoteLength.QUARTER);
	}

	public Tact(int beatsPerTact, NoteLength noteLength) {
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
		if ((obj == null) || !(obj instanceof Tact)) {
			return false;
		}

		Tact tact = (Tact) obj;

		return (beatsPerTact == tact.getBeatsPerTact()) && noteLength.equals(tact.getNoteLength());
	}

	@Override
	public String toString() {
		return "[Tact] beatsPerTact=" + beatsPerTact + " noteLength=" + noteLength;
	}
}
