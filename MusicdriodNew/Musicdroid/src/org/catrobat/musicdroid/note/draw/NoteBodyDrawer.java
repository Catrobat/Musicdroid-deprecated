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

import org.catrobat.musicdroid.tool.draw.NoteSheetCanvas;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.drawable.shapes.OvalShape;

/**
 * @author Bianca TEUFL, Elisabeth Heschl, Daniel Neuhold
 * 
 */
public class NoteBodyDrawer {

	public static RectF drawBody(NoteSheetCanvas noteSheetCanvas,
			int toneDistanceFromToneToMiddleLineInHalfTones, boolean isFilled) {

		int lineHeigth = noteSheetCanvas.getDistanceBetweenNoteLines();
		int noteHeigth = lineHeigth / 2;
		int noteWidth = noteHeigth * 130 / 100;

		Point centerPointOfSpaceForNote = noteSheetCanvas
				.getCenterPointForNextSymbol();
		Point centerPointOfNote = centerPointOfSpaceForNote;
		centerPointOfNote.y += toneDistanceFromToneToMiddleLineInHalfTones
				* noteHeigth;

		int left = centerPointOfNote.x - noteWidth;
		int top = centerPointOfNote.y - noteHeigth;
		int right = centerPointOfNote.x + noteWidth;
		int bottom = centerPointOfNote.y + noteHeigth;

		RectF noteSurroundingRect = new RectF(left, top, right, bottom);

		Paint paint = new Paint();
		paint.setColor(Color.BLACK);
		if (!isFilled) {
			paint.setStyle(Style.STROKE);
			paint.setStrokeWidth(4);
		}

		noteSheetCanvas.getCanvas().drawOval(noteSurroundingRect, paint);
		return noteSurroundingRect;
	}
}
