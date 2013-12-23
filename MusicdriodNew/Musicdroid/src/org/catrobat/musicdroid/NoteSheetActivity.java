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
package org.catrobat.musicdroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

import org.catrobat.musicdroid.note.NoteEvent;
import org.catrobat.musicdroid.note.NoteLength;
import org.catrobat.musicdroid.note.Track;
import org.catrobat.musicdroid.note.draw.NoteSheetView;
import org.catrobat.musicdroid.piano.PianoView;

/**
 * @author musicdroid
 * 
 */
public class NoteSheetActivity extends Activity {

	private Track track;
	private int tick;
	private NoteSheetView noteSheetView;
	private PianoView pianoView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.track = new Track();
		this.tick = 0;
		LinearLayout linearLayout = new LinearLayout(this);
		LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,
				1.0f);
		noteSheetView = new NoteSheetView(this);
		pianoView = new PianoView(this);
		noteSheetView.setLayoutParams(layoutParams);
		pianoView.setLayoutParams(layoutParams);
		linearLayout.addView(noteSheetView);
		linearLayout.addView(pianoView);
		linearLayout.setOrientation(1);

		this.setContentView(linearLayout);
	}

	public void addNoteEventToTrackAndRedraw(NoteEvent noteEvent) {
		if (false == noteEvent.isNoteOn()) {
			incrementTick();
		}

		track.addNoteEvent(tick, noteEvent);

		drawTrack();
	}

	private void drawTrack() {
		noteSheetView.setTrack(track);
		noteSheetView.invalidate();
	}

	public void setTrack(Track track) {
		this.track = track;
	}

	private void incrementTick() {
		tick += NoteLength.QUARTER.getTickDuration();
	}
}
