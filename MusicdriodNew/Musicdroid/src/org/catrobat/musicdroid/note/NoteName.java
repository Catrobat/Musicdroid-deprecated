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

public enum NoteName {
	A0(21, false), A0S(22, true), B0(23, false), C1(24, false), C1S(25, true), D1(26, false), D1S(27, true), E1(28,
			false), F1(29, false), F1S(30, true), G1(31, false), G1S(32, true), A1(33, false), A1S(34, true), B1(35,
			false), C2(36, false), C2S(37, true), D2(38, false), D2S(39, true), E2(40, false), F2(41, false), F2S(42,
			true), G2(43, false), G2S(44, true), A2(45, false), A2S(46, true), B2(47, false), C3(48, false), C3S(49,
			true), D3(50, false), D3S(51, true), E3(52, false), F3(53, false), F3S(54, true), G3(55, false), G3S(56,
			true), A3(57, false), A3S(58, true), B3(59, false), C4(60, false), C4S(61, true), D4(62, false), D4S(63,
			true), E4(64, false), F4(65, false), F4S(66, true), G4(67, false), G4S(68, true), A4(69, false), A4S(70,
			true), B4(71, false), C5(72, false), C5S(73, true), D5(74, false), D5S(75, true), E5(76, false), F5(77,
			false), F5S(78, true), G5(79, false), G5S(80, true), A5(81, false), A5S(82, true), B5(83, false), C6(84,
			false), C6S(85, true), D6(86, false), D6S(87, true), E6(88, false), F6(89, false), F6S(90, true), G6(91,
			false), G6S(92, true), A6(93, false), A6S(94, true), B6(95, false), C7(96, false), C7S(97, true), D7(98,
			false), D7S(99, true), E7(100, false), F7(101, false), F7S(102, true), G7(103, false), G7S(104, true), A7(
			105, false), A7S(106, true), B7(107, false), C8(108, false);

	public static final NoteName DEFAULT_NOTE_NAME = NoteName.C4;

	private int midi;
	private boolean signed;

	private NoteName(int midi, boolean signed) {
		this.midi = midi;
		this.signed = signed;
	}

	public int getMidi() {
		return midi;
	}

	public NoteName next() {
		int index = this.ordinal() + 1;

		if (index >= values().length) {
			index--;
		}

		return values()[index];
	}

	public NoteName previous() {
		int index = this.ordinal() - 1;

		if (index < 0) {
			index++;
		}

		return values()[index];
	}

	public boolean isSigned() {
		return signed;
	}

	public static NoteName getNoteNameFromMidiValue(int midiValue) {
		NoteName[] noteNames = NoteName.values();

		for (int i = 0; i < noteNames.length; i++) {
			if (noteNames[i].getMidi() == midiValue) {
				return noteNames[i];
			}
		}

		return DEFAULT_NOTE_NAME;
	}

	public static int calculateDistanceCountingNoneSignedNotesOnly(NoteName noteName1, NoteName noteName2) {
		int distance = 0;
		boolean isDownGoing = (noteName2.midi - noteName1.midi) > 0;

		NoteName smallNoteName = isDownGoing ? noteName1 : noteName2;
		NoteName largeNoteName = isDownGoing ? noteName2 : noteName1;

		if (smallNoteName.isSigned()) {
			distance = 1;
		} else if (largeNoteName.isSigned()) {
			distance = -1;
		}

		while (smallNoteName.getMidi() != largeNoteName.getMidi()) {
			if (!smallNoteName.isSigned()) {
				distance++;
			}

			smallNoteName = smallNoteName.next();
		}

		return (isDownGoing ? distance : distance * (-1));
	}

	public static int calculateDistanceToMiddleLineCountingSignedNotesOnly(Key key, NoteName noteName) {
		return calculateDistanceCountingNoneSignedNotesOnly(key.getNoteNameOnMiddleLine(), noteName);
	}
}
