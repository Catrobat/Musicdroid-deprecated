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
package org.catrobat.musicdroid.instruments.piano;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import org.catrobat.musicdroid.note.Octave;

/**
 * @author musicdroid
 */
@SuppressLint("ViewConstructor")
public class PianoView extends LinearLayout {

	private static final Octave[] SUPPORTED_OCTAVES = new Octave[] { Octave.createSmallOctave(),
			Octave.createOneLineOctave(), Octave.createTwoLineOctave() };

	private int activeOctaveIndex;
	private PianoOctaveView pianoOctaveView;
	private Button buttonLeft;
	private Button buttonRight;

	public PianoView(PianoActivity pianoActivity) {
		super(pianoActivity);
		activeOctaveIndex = 1;
		initComponents();
	}

	private void initComponents() {
		removeAllViews();

		buttonLeft = new Button(getContext());
		buttonLeft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				decrementOctave();
				initComponents();
			}

		});

		buttonRight = new Button(getContext());
		buttonRight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				incrementOctave();
				initComponents();
			}

		});

		pianoOctaveView = new PianoOctaveView((PianoActivity) getContext(), SUPPORTED_OCTAVES[activeOctaveIndex]);

		addView(buttonLeft);
		addView(pianoOctaveView);
		addView(buttonRight);
	}

	public void incrementOctave() {
		int newOctaveIndex = activeOctaveIndex + 1;

		if (newOctaveIndex == SUPPORTED_OCTAVES.length) {
			buttonRight.setClickable(false);
			return;
		}

		activeOctaveIndex = newOctaveIndex;
		buttonLeft.setClickable(true);
	}

	public void decrementOctave() {
		int newOctaveIndex = activeOctaveIndex - 1;

		if (newOctaveIndex < 0) {
			buttonLeft.setClickable(false);
			return;
		}

		activeOctaveIndex = newOctaveIndex;
		buttonRight.setClickable(true);
	}
}
