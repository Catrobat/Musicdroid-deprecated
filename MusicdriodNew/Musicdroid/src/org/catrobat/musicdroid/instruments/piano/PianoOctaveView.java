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
package org.catrobat.musicdroid.instruments.piano;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import org.catrobat.musicdroid.note.NoteName;
import org.catrobat.musicdroid.note.Octave;

/**
 * @author Bianca TEUFL
 */
@SuppressLint("ViewConstructor")
public class PianoOctaveView extends RelativeLayout {

	private int widthOfWhiteKey;
	private int widthOfBlackKey;
	private int heightOfWhiteKey;
	private int heightOfBlackKey;

	private Octave octave;

	public PianoOctaveView(PianoActivity pianoActivity, Octave octave) {
		super(pianoActivity);
		this.octave = octave;
		initComponents();
	}

	private void initComponents() {
		setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f));
		setWillNotDraw(false);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		createPianoKeys(canvas.getWidth(), canvas.getHeight());
		setWillNotDraw(true);
	}

	private void createPianoKeys(int width, int height) {
		setSizeOfKeys(width, height);
		createWhitePianoKeys();
		createBlackPianoKeys();
	}

	private void setSizeOfKeys(int width, int height) {
		widthOfWhiteKey = width / Octave.NUMBER_OF_UNSIGNED_HALF_TONE_STEPS_PER_OCTAVE;
		heightOfWhiteKey = height;
		widthOfBlackKey = widthOfWhiteKey / 2;
		heightOfBlackKey = heightOfWhiteKey / 2;
	}

	private void createWhitePianoKeys() {
		NoteName[] noteNames = octave.getNoteNames();
		int nextWhiteButtonPosition = 0;

		for (int i = 0; i < noteNames.length; i++) {
			NoteName noteName = noteNames[i];

			if (false == noteName.isSigned()) {
				PianoKey pianoKey = createPianoKey(noteName, widthOfWhiteKey, heightOfWhiteKey,
						nextWhiteButtonPosition, false);
				nextWhiteButtonPosition += widthOfWhiteKey;
				addView(pianoKey);
			}
		}
	}

	private void createBlackPianoKeys() {
		NoteName[] noteNames = octave.getNoteNames();
		int nextBlackButtonPosition = widthOfBlackKey * 3 / 2;
		int indexWithNoBlackPianoKey = 4;

		for (int i = 0; i < noteNames.length; i++) {
			NoteName noteName = noteNames[i];

			if (noteName.isSigned()) {
				PianoKey pianoKey = createPianoKey(noteName, widthOfBlackKey, heightOfBlackKey,
						nextBlackButtonPosition, true);
				nextBlackButtonPosition += widthOfWhiteKey;
				addView(pianoKey);
			}

			if (i == indexWithNoBlackPianoKey) {
				nextBlackButtonPosition += widthOfWhiteKey;
			}
		}
	}

	private PianoKey createPianoKey(NoteName noteName, int width, int height, int position, boolean isBlackKey) {
		return new PianoKey((PianoActivity) getContext(), noteName, width, height, position, isBlackKey);
	}
}
