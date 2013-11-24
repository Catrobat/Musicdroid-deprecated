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

import android.graphics.Canvas;
import android.graphics.Color;

import org.catrobat.musicdroid.note.NoteName;

/**
 * @author Bianca TEUFL
 * 
 */
public class PianoKeyOctave {
	private static final int NUMBER_OF_WHITE_PIANO_KEYS_PER_OCTAVE = 7;
	private static final int NUMBER_OF_BLACK_PIANO_KEYS_PER_OCTAVE = 5;
	private NoteName octaveStartNote;
	private int widthOfWhiteKey;
	private int widthOfBlackKey;
	private int heightOfWhiteKey;
	private int heightOfBlackKey;
	private int nextXPositionForWhiteButton;
	private int nextXPositionForBlackButton;
	private PianoKey[] blackPianoKeys;
	private PianoKey[] whitePianoKeys;
	private int octaveNumber;
	private PianoOctaveView pianoView;
	private Canvas canvas;

	public PianoKeyOctave(PianoOctaveView pianoView, int octaveNumber, Canvas canvas) {
		this.pianoView = pianoView;
		this.octaveNumber = octaveNumber;
		this.blackPianoKeys = new PianoKey[NUMBER_OF_BLACK_PIANO_KEYS_PER_OCTAVE];
		this.whitePianoKeys = new PianoKey[NUMBER_OF_WHITE_PIANO_KEYS_PER_OCTAVE];
		this.octaveStartNote = NoteName.getFirstNoteOfOctave(octaveNumber);
		this.canvas = canvas;
		setSizeOfKeys();
		this.nextXPositionForBlackButton = widthOfBlackKey * 3 / 2;
		this.nextXPositionForWhiteButton = 0;
		initializePianoKeys();

	}

	private void initializePianoKeys() {
		NoteName actualNoteName = octaveStartNote;

		for (int whiteKeyIndex = 0; whiteKeyIndex < NUMBER_OF_WHITE_PIANO_KEYS_PER_OCTAVE; whiteKeyIndex++) {
			whitePianoKeys[whiteKeyIndex] = new PianoKey(pianoView.getContext(), actualNoteName, widthOfWhiteKey,
					heightOfWhiteKey, getNextXPositionForWhiteButton());
			actualNoteName = NoteName.getNextWhiteKeyNoteName(actualNoteName);
			pianoView.addView(whitePianoKeys[whiteKeyIndex]);
		}

		actualNoteName = octaveStartNote;
		actualNoteName = NoteName.getNextBlackKeyNoteName(actualNoteName);

		for (int blackKeyIndex = 0; blackKeyIndex < NUMBER_OF_BLACK_PIANO_KEYS_PER_OCTAVE; blackKeyIndex++) {
			blackPianoKeys[blackKeyIndex] = new PianoKey(pianoView.getContext(), actualNoteName, widthOfBlackKey,
					heightOfBlackKey, getNextXPositionForBlackButton(actualNoteName));
			actualNoteName = NoteName.getNextBlackKeyNoteName(actualNoteName);
			blackPianoKeys[blackKeyIndex].setBackgroundColor(Color.BLACK);
			pianoView.addView(blackPianoKeys[blackKeyIndex]);
		}

	}

	private void setSizeOfKeys() {

		widthOfWhiteKey = canvas.getWidth() / NUMBER_OF_WHITE_PIANO_KEYS_PER_OCTAVE;
		widthOfBlackKey = widthOfWhiteKey / 2;

		heightOfWhiteKey = canvas.getHeight();
		heightOfBlackKey = heightOfWhiteKey / 2;

	}

	public int getOctaveNumber() {
		return octaveNumber;
	}

	public void setOctaveNumber(int octaveNumber) {
		this.octaveNumber = octaveNumber;
	}

	public int getWidthOfWhiteKey() {
		return widthOfWhiteKey;
	}

	public void setWidthOfWhiteKey(int widthOfWhiteKey) {
		this.widthOfWhiteKey = widthOfWhiteKey;
	}

	public int getWidthOfBlackKey() {
		return widthOfBlackKey;
	}

	public void setWidthOfBlackKey(int widthOfBlackKey) {
		this.widthOfBlackKey = widthOfBlackKey;
	}

	public int getHeightOfWhiteKey() {
		return heightOfWhiteKey;
	}

	public void setHeightOfWhiteKey(int heightOfWhiteKey) {
		this.heightOfWhiteKey = heightOfWhiteKey;
	}

	public int getHeightOfBlackKey() {
		return heightOfBlackKey;
	}

	public void setHeightOfBlackKey(int heightOfBlackKey) {
		this.heightOfBlackKey = heightOfBlackKey;
	}

	public int getNextXPositionForWhiteButton() {
		int returnValue = nextXPositionForWhiteButton;
		nextXPositionForWhiteButton += widthOfWhiteKey;
		return returnValue;
	}

	public int getNextXPositionForBlackButton(NoteName actuelNote) {
		int returnValue = nextXPositionForBlackButton;
		nextXPositionForBlackButton += 2 * widthOfBlackKey;
		if (actuelNote.isBlackKeyWithNoDirectRightNeighbour()) {
			nextXPositionForBlackButton += 2 * widthOfBlackKey;
		}
		return returnValue;
	}
}
