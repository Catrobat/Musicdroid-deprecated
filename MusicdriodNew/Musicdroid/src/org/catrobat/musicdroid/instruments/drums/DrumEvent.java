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
package org.catrobat.musicdroid.instruments.drums;

/**
 * @author AM
 * 
 */
public class DrumEvent {

	private int eventLength;
	private String drumPartName;
	private int imageRessource;
	private int drumKitBackgroundColor;

	public DrumEvent(DrumKitPart drumKitPart) {
		this.drumPartName = drumKitPart.getDrumPartName();
		imageRessource = drumKitPart.getDrawableId();
		drumKitBackgroundColor = drumKitPart.getBackgroundColor();
	}

	public String getDrumPartName() {
		return drumPartName;
	}

	public int getImageRessource() {
		return imageRessource;
	}

	public int getEventLength() {
		return eventLength;
	}

	public void setEventLength(int eventLength) {
		this.eventLength = eventLength;
	}

	public int getDrumKitBackgroundColor() {
		return drumKitBackgroundColor;
	}

}
