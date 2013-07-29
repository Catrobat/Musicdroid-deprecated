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
package at.tugraz.musicdroid.drawtones;

import android.graphics.Point;
import at.tugraz.musicdroid.tone.NoteName;
import at.tugraz.musicdroid.tone.Tone;

/**
 * @author Bianca
 * 
 */
public class NotePosition {

	private int horizontalStartPositionOfTone_;
	private Tone tone_;
	private Point calculatedPositionOfTone_;
	private int viewWidth_;
	private int viewHeight_;
	private int lineOfMiddleToneH_;
	private int spaceBetweenLines_;
	private int halfToneSpace_;
	private final double RELATIONSHIP_TONES_TO_HEIGHT = 1 / 12.0;

	public NotePosition(Tone tone, int horizontalStartPositionOfTone,
			int viewWidth, int viewHeight) {
		reset(tone, horizontalStartPositionOfTone, viewWidth, viewHeight);
	}

	public void reset(Tone tone, int horizontalStartPositionOfTone,
			int viewWidth, int viewHeight) {
		this.tone_ = tone;
		this.horizontalStartPositionOfTone_ = horizontalStartPositionOfTone;
		this.viewHeight_ = viewHeight;
		this.viewWidth_ = viewWidth;
		calculatePositionVariables();
	}

	private void calculatePositionVariables() {
		lineOfMiddleToneH_ = viewHeight_ / 2;
		spaceBetweenLines_ = (new Double(viewHeight_
				* RELATIONSHIP_TONES_TO_HEIGHT)).intValue();
		halfToneSpace_ = spaceBetweenLines_ / 2;
		calculatePositionOfTone();
	}

	private void calculatePositionOfTone() {
		NoteName middle_h = NoteName.B3;

		int halfTones = middle_h.getMidi() - tone_.getNoteName().getMidi();
		calculatedPositionOfTone_.x = halfToneSpace_ * halfTones;
		calculatedPositionOfTone_.y = horizontalStartPositionOfTone_
				+ spaceBetweenLines_;
		if (tone_.getNoteName().isSigned()) {
			calculatedPositionOfTone_.y += spaceBetweenLines_;
		}

	}

	/**
	 * @return the calculatedPositionOfTone_
	 */
	public Point getCalculatedPositionOfTone_() {
		return calculatedPositionOfTone_;
	}

	/**
	 * @param calculatedPositionOfTone_ the calculatedPositionOfTone_ to set
	 */
	public void setCalculatedPositionOfTone_(Point calculatedPositionOfTone_) {
		this.calculatedPositionOfTone_ = calculatedPositionOfTone_;
	}

	/**
	 * @return the halfToneSpace_
	 */
	public int getHalfToneSpace_() {
		return halfToneSpace_;
	}

	/**
	 * @param halfToneSpace_ the halfToneSpace_ to set
	 */
	public void setHalfToneSpace_(int halfToneSpace_) {
		this.halfToneSpace_ = halfToneSpace_;
	}
}
