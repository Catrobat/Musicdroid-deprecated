/**
 *  Catroid: An on-device visual programming system for Android devices
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
 *  http://developer.catrobat.org/license_additional_term
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU Affero General Public License for more details.
 *  
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.catrobat.musicdroid.note;

import java.io.Serializable;

public class Chord extends Symbol implements Serializable {

	private static final long serialVersionUID = 8777397660187943086L;

	private NoteName[] names;

	public Chord(NoteLength noteLength, NoteName... names) {
		super(noteLength);
		this.names = names;
	}

	public int size() {
		return names.length;
	}

	public NoteName getNoteName(int location) {
		return names[location];
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof Chord)) {
			return false;
		}

		Chord chord = (Chord) obj;

		if (false == super.equals(chord)) {
			return false;
		}

		if (names.length != chord.size()) {
			return false;
		}

		for (int i = 0; i < names.length; i++) {
			if (false == names[i].equals(chord.getNoteName(i))) {
				return false;
			}
		}

		return true;
	}

	@Override
	public String toString() {
		return "[Chord] noteLength=" + noteLength + " namesCount=" + names.length;
	}
}
