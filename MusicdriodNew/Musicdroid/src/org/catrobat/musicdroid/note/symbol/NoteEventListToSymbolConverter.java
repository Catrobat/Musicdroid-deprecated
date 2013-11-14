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

import org.catrobat.musicdroid.note.NoteEvent;
import org.catrobat.musicdroid.note.NoteLength;
import org.catrobat.musicdroid.note.NoteName;

import java.util.LinkedList;
import java.util.List;

/**
 * @author musicdroid
 * 
 */
public class NoteEventListToSymbolConverter {

	private long lastTick;
	private boolean hasInsertedNoteBefore;
	private List<NoteEvent> openNoteEvents;

	public NoteEventListToSymbolConverter() {
		lastTick = 0;
		hasInsertedNoteBefore = false;
		openNoteEvents = new LinkedList<NoteEvent>();
	}

	public List<AbstractSymbol> convertNoteEventList(long tick, List<NoteEvent> noteEventList) {
		List<AbstractSymbol> symbols = new LinkedList<AbstractSymbol>();
		List<NoteEvent> newOpenNoteEvents = new LinkedList<NoteEvent>();
		long duration = tick - lastTick;

		for (NoteEvent noteEvent : noteEventList) {
			if (noteEvent.isNoteOn()) {
				if ((lastTick != tick) && (hasInsertedNoteBefore)) {
					NoteLength[] noteLengths = NoteLength.getNoteLengthsFromTickDuration(duration);
					symbols.add(new BreakSymbol(noteLengths));
				}

				newOpenNoteEvents.add(noteEvent);
				hasInsertedNoteBefore = false;
				lastTick = tick;
			} else {
				if (false == openNoteEvents.isEmpty()) {
					NoteLength[] noteLengths = NoteLength.getNoteLengthsFromTickDuration(duration);
					NoteName[] noteNames = new NoteName[openNoteEvents.size()];

					for (int i = 0; i < noteNames.length; i++) {
						noteNames[i] = openNoteEvents.get(i).getNoteName();
					}

					openNoteEvents.clear();
					symbols.add(new NoteSymbol(noteLengths, noteNames));
					hasInsertedNoteBefore = true;
					lastTick = tick;
				}
			}
		}

		openNoteEvents = newOpenNoteEvents;

		return symbols;
	}
}
