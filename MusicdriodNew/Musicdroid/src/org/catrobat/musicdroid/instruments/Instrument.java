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
package org.catrobat.musicdroid.instruments;

import android.app.Activity;

import org.catrobat.musicdroid.instruments.drums.DrumEvent;
import org.catrobat.musicdroid.note.Key;
import org.catrobat.musicdroid.note.NoteEvent;
import org.catrobat.musicdroid.note.Track;
import org.catrobat.musicdroid.types.SpecialEvent;

public abstract class Instrument extends Activity {

	private TickThread tickThread;
	private Track track;

	//private List<DrumEvent> drumsDB;

	public Instrument() {
		tickThread = new TickThread();
		track = new Track(Key.VIOLIN); //TODO FW
	}

	//	public Instrument(String instrumentName) {
	//		if(instrumentName == "drums")
	//			drumsDB = new ArrayList<DrumEvent>();
	//	}

	public Track getTrack() {
		return track;
	}

	public void addNoteEvent(NoteEvent noteEvent) {
		track.addNoteEvent(tickThread.getNextTick(noteEvent), noteEvent);
		doAfterAddAnEvent(noteEvent);
	}

	public void addDrumEvent(DrumEvent drumEvent) {
		//drumsDB.add(drumEvent);//(tickThread.getNextTick(drumEvent), drumEvent);
		doAfterAddAnEvent(drumEvent);
	}

	// TODO: its not beautiful :(
	protected abstract void doAfterAddAnEvent(SpecialEvent anEvent);

}