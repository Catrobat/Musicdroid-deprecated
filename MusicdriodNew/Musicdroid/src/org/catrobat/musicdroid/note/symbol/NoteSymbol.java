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
package org.catrobat.musicdroid.note.symbol;

import org.catrobat.musicdroid.note.NoteLength;
import org.catrobat.musicdroid.note.NoteName;

import java.util.HashMap;
import java.util.Map;

public class NoteSymbol implements Symbol {

	private Map<NoteName, NoteLength> notes;

	public NoteSymbol() {
		notes = new HashMap<NoteName, NoteLength>();
	}

	public void addNote(NoteName noteName, NoteLength noteLength) {
		notes.put(noteName, noteLength);
	}

	public int size() {
		return notes.size();
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof NoteSymbol)) {
			return false;
		}

		NoteSymbol noteSymbol = (NoteSymbol) obj;

		if (notes.equals(noteSymbol.notes)) {
			return true;
		}

		return false;
	}

	@Override
	public String toString() {
		return "[NoteSymbol] size: " + size();
	}
}
