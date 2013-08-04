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
package org.catrobat.musicdroid.tool.draw;

import android.graphics.Canvas;

/**
 * @author Bianca TEUFL
 * 
 */
public class NoteSheetCanvas {

	private static final int POSSIBLE_LINE_SPACES_ON_SCREEN = 12;
	
	private int startXPositionNotes; 


	private Canvas canvas;
	
	public NoteSheetCanvas(Canvas canvas) {
		this.canvas = canvas;
	}

	public Canvas getCanvas() {
		return canvas;
	}

	public int getDistanceBetweenNoteLines() {
		return canvas.getHeight() / POSSIBLE_LINE_SPACES_ON_SCREEN;
	}

	public int getYPositionOfCenterLine() {
		return canvas.getHeight() / 2;
	}

	public int getStartXPositionNotes() {
		return startXPositionNotes;
	}

	public void setStartXPositionNotes(int startXPositionNotes) {
		this.startXPositionNotes = startXPositionNotes;
	}

}
