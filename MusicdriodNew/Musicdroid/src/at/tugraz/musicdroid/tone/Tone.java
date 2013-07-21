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
package at.tugraz.musicdroid.tone;

import java.io.Serializable;

public class Tone extends Symbol implements Serializable {

	private static final long serialVersionUID = 2238272682118731619L;

	private NoteName name;

	Tone(NoteName name, NoteLength length) {
		super(length);
		this.name = name;
	}

	public NoteName getNoteName() {
		return name;
	}

	public Tone halfToneUp() {
		Tone newTone = new Tone(name.next(), noteLength);
		return newTone;
	}

	public Tone halfToneDown() {
		Tone newTone = new Tone(name.previous(), noteLength);
		return newTone;
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof Tone)) {
			return false;
		}

		Tone tone = (Tone) obj;

		if (super.equals(obj)) {
			return name.equals(tone.getNoteName());
		}

		return false;
	}

	@Override
	public String toString() {
		return "[Tone] noteLength=" + noteLength + " name=" + name;
	}
}
