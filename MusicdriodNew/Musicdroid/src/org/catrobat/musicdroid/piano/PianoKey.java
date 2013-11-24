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
package org.catrobat.musicdroid.piano;

import android.content.Context;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout.LayoutParams;

import org.catrobat.musicdroid.NoteSheetActivity;
import org.catrobat.musicdroid.note.NoteEvent;
import org.catrobat.musicdroid.note.NoteName;

/**
 * @author Bianca TEUFL
 * 
 */
public class PianoKey extends Button {

	protected NoteName noteName;

	public PianoKey(Context context, NoteName noteName, int width, int height, int xPosition) {
		super(context);
		this.noteName = noteName;
		this.setLayoutParams(new LayoutParams(width, height));
		this.setX(xPosition);

		this.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent event) {
				NoteSheetActivity noteSheetActivity = (NoteSheetActivity) view.getContext();
				if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
					noteSheetActivity.getTrack().addNoteEvent(noteSheetActivity.getTick(),
							new NoteEvent(getNoteName(), true));
					setText(PianoKey.this.noteName.toString());
					setTextColor(Color.BLUE);
				} else if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
					noteSheetActivity.incrementTick();
					noteSheetActivity.getTrack().addNoteEvent(noteSheetActivity.getTick(),
							new NoteEvent(getNoteName(), false));
					noteSheetActivity.drawTrack();
					setText("");
					setTextColor(Color.BLUE);
				}
				return true;
			}
		});
	}

	public NoteName getNoteName() {
		return noteName;
	}

	public void setNoteName(NoteName noteName) {
		this.noteName = noteName;
	}
}
