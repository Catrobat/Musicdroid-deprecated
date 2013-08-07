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
package org.catrobat.musicdroid.note.draw;

import org.catrobat.musicdroid.note.NoteLength;
import org.catrobat.musicdroid.tool.draw.NoteSheetCanvas;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

/**
 * @author Bianca TEUFL
 * 
 */
public class NoteStemDrawer {

	private static final double LENGTH_OF_STEM_IN_NOTE_LINE_DISTANCES = 3.5;

	public static void drawStem(NoteSheetCanvas noteSheetCanvas,
			NoteLength noteLength, Point startPointOfNoteStem,
			boolean isUpDirectedStem) {

		Point endPointOfNoteStem = new Point();
		int stemLength = (int) (Math
				.round(LENGTH_OF_STEM_IN_NOTE_LINE_DISTANCES
						* noteSheetCanvas.getDistanceBetweenNoteLines()));

		endPointOfNoteStem.x = startPointOfNoteStem.x;

		if (isUpDirectedStem) {
			endPointOfNoteStem.y = startPointOfNoteStem.y - stemLength;
		} else {
			endPointOfNoteStem.y = startPointOfNoteStem.y + stemLength;
		}

		Paint paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setStrokeWidth(4);

		noteSheetCanvas.getCanvas().drawLine(startPointOfNoteStem.x,
				startPointOfNoteStem.y, endPointOfNoteStem.x,
				endPointOfNoteStem.y, paint);
	}
}
