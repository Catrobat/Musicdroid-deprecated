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

import android.annotation.SuppressLint;
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
@SuppressLint("ViewConstructor")
public class PianoKey extends Button {

	public PianoKey(Context context, NoteName noteName, int width, int height, int xPosition, boolean isBlackKey) {
		super(context);
		initComponents(noteName, width, height, xPosition, isBlackKey);
	}

	private void initComponents(final NoteName noteName, int width, int height, int xPosition, boolean isBlackKey) {
		setLayoutParams(new LayoutParams(width, height));
		setX(xPosition);

		if (isBlackKey) {
			setBackgroundColor(Color.BLACK);
		}

		setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent event) {

				if (isDownActionEvent(event)) {
					addKeyPress(new NoteEvent(noteName, true));
				} else if (isUpActionEvent(event)) {
					addKeyPress(new NoteEvent(noteName, false));
				}

				return true;
			}

			private boolean isDownActionEvent(MotionEvent event) {
				return (event.getAction() == android.view.MotionEvent.ACTION_DOWN);
			}

			private boolean isUpActionEvent(MotionEvent event) {
				return (event.getAction() == android.view.MotionEvent.ACTION_UP);
			}
		});
	}

	private void addKeyPress(NoteEvent noteEvent) {
		NoteSheetActivity noteSheetActivity = (NoteSheetActivity) getContext();
		noteSheetActivity.addNoteEventToTrackAndRedraw(noteEvent);
		setPianoKeyText(noteEvent);
	}

	private void setPianoKeyText(NoteEvent noteEvent) {
		if (noteEvent.isNoteOn()) {
			setText(noteEvent.getNoteName().toString());
		} else {
			setText("");
		}

		setTextColor(Color.BLUE);
	}
}
