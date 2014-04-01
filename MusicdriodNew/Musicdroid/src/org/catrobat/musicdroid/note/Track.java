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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Track implements Serializable {

	public static final Instrument DEFAULT_INSTRUMENT = Instrument.ACOUSTIC_GRAND_PIANO;
	private static final long serialVersionUID = 7483021689872527955L;

	private Instrument instrument;
	private HashMap<Long, List<NoteEvent>> events;
	private Key key;

	public Track(Key key) {
		this.events = new HashMap<Long, List<NoteEvent>>();
		this.instrument = DEFAULT_INSTRUMENT;
		this.key = key;
	}

	public Track(Key key, Instrument instrument) {
		this.events = new HashMap<Long, List<NoteEvent>>();
		this.instrument = instrument;
		this.key = key;
	}

	public Instrument getInstrument() {
		return instrument;
	}

	public Key getKey() {
		return key;
	}

	public void addNoteEvent(long tick, NoteEvent noteEvent) {
		List<NoteEvent> noteEventList = null;

		if (events.containsKey(tick)) {
			noteEventList = events.get(tick);
		} else {
			noteEventList = new LinkedList<NoteEvent>();
			events.put(tick, noteEventList);
		}

		noteEventList.add(noteEvent);
	}

	public List<NoteEvent> getNoteEventsForTick(long tick) {
		return events.get(tick);
	}

	public Set<Long> getSortedTicks() {
		return new TreeSet<Long>(events.keySet());
	}

	public int size() {
		int size = 0;

		for (List<NoteEvent> noteEventList : events.values()) {
			size += noteEventList.size();
		}

		return size;
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

		Set<Long> ownTrackTicks = getSortedTicks();
		Set<Long> otherTrackTicks = track.getSortedTicks();

		if (otherTrackTicks.equals(ownTrackTicks)) {
			for (long tick : ownTrackTicks) {
				List<NoteEvent> ownNoteEventList = getNoteEventsForTick(tick);
				List<NoteEvent> otherNoteEventList = track.getNoteEventsForTick(tick);

				if (false == ownNoteEventList.equals(otherNoteEventList)) {
					return false;
				}
			}

			return true;
		}

		return false;
	}

	@Override
	public String toString() {
		return "[Track] instrument=" + instrument + " key=" + key + " size=" + size();
	}
}
