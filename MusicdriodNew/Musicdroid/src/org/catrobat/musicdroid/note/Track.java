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
import java.util.ArrayList;
import java.util.List;

public class Track implements Serializable {

	private static final long serialVersionUID = 7483021689872527955L;

	private Instrument instrument;
	private List<NoteEvent> events;

	public Track() {
		this.events = new ArrayList<NoteEvent>();
		this.instrument = Instrument.ACOUSTIC_GRAND_PIANO;
	}

	public Track(Instrument instrument) {
		this.events = new ArrayList<NoteEvent>();
		this.instrument = instrument;
	}

	public Instrument getInstrument() {
		return instrument;
	}

	public void addNoteEvent(NoteEvent event) {
		events.add(event);
	}

	public void removeNoteEvent(NoteEvent event) {
		events.remove(event);
	}

	public NoteEvent getNoteEvent(int location) {
		return events.get(location);
	}

	public int size() {
		return events.size();
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof Track)) {
			return false;
		}

		Track track = (Track) obj;

		if (track.getInstrument() != getInstrument()) {
			return false;
		}

		if (size() == track.size()) {
			for (int i = 0; i < size(); i++) {
				if (false == getNoteEvent(i).equals(track.getNoteEvent(i))) {
					return false;
				}
			}

			return true;
		}

		return false;
	}

	@Override
	public String toString() {
		return "[Track] instrument= " + instrument + " eventCount=" + size();
	}
}
