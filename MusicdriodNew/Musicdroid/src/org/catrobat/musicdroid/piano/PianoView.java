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
package org.catrobat.musicdroid.piano;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import org.catrobat.musicdroid.note.Octave;

/**
 * @author musicdroid
 * 
 */
public class PianoView extends LinearLayout {

	private Octave activeOctave = Octave.ONE_LINE_OCTAVE;
	private PianoOctaveView pianoView;
	private Button buttonLeft;
	private Button buttonRight;
	private LayoutParams layoutParams;

	public PianoView(Context context) {
		super(context);
		this.layoutParams = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, 1.0f);
		this.buttonLeft = new Button(context);
		this.pianoView = new PianoOctaveView(context, activeOctave);
		this.buttonRight = new Button(context);
		pianoView.setLayoutParams(layoutParams);
		this.addView(buttonLeft);
		this.addView(pianoView);
		this.addView(buttonRight);

		this.buttonLeft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				incrementOctave();
				removeAllViews();
				addView(buttonLeft);
				pianoView = new PianoOctaveView(getContext(), activeOctave);
				pianoView.setLayoutParams(layoutParams);
				addView(pianoView);
				addView(buttonRight);
			}

		});

		this.buttonRight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				decrementOctave();
				removeAllViews();
				addView(buttonLeft);
				pianoView = new PianoOctaveView(getContext(), activeOctave);
				pianoView.setLayoutParams(layoutParams);
				addView(pianoView);
				addView(buttonRight);
			}

		});
	}

	public void incrementOctave() {
		Octave oldOctave = activeOctave;
		activeOctave = activeOctave.next();

		if (activeOctave == oldOctave) {
			buttonRight.setClickable(false);
		}

		buttonLeft.setClickable(true);
	}

	public void decrementOctave() {
		Octave oldOctave = activeOctave;
		activeOctave = activeOctave.previous();

		if (activeOctave == oldOctave) {
			buttonLeft.setClickable(false);
		}

		buttonRight.setClickable(true);
	}

}
