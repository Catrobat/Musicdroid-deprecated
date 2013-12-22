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
package org.catrobat.musicdroid.note.draw;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.RectF;

import org.catrobat.musicdroid.tool.draw.NoteSheetCanvas;

public class NoteBodyDrawer {

	public static RectF[] drawBody(NoteSheetCanvas noteSheetCanvas, int[] toneDistanceFromToneToMiddleLineInHalfTones,
			boolean isFilled, boolean isStemUp) {

		int lineHeight = noteSheetCanvas.getDistanceBetweenNoteLines();
		int noteHeigth = lineHeight / 2;
		int noteWidth = noteHeigth * 130 / 100;

		Point centerPointOfSpaceForNote = noteSheetCanvas.getCenterPointForNextSymbol();

		RectF[] noteSurroundingRects = new RectF[toneDistanceFromToneToMiddleLineInHalfTones.length];

		Paint paint = new Paint();
		paint.setColor(Color.BLACK);
		if (!isFilled) {
			paint.setStyle(Style.STROKE);
			paint.setStrokeWidth(4);
		}

		for (int distanceIndex = 0; distanceIndex < toneDistanceFromToneToMiddleLineInHalfTones.length; distanceIndex++) {
			Point centerPointOfActualNote = new Point(centerPointOfSpaceForNote);
			centerPointOfActualNote.y += toneDistanceFromToneToMiddleLineInHalfTones[distanceIndex] * noteHeigth;
			int left = centerPointOfActualNote.x - noteWidth;
			int top = centerPointOfActualNote.y - noteHeigth;
			int right = centerPointOfActualNote.x + noteWidth;
			int bottom = centerPointOfActualNote.y + noteHeigth;

			if (distanceIndex > 0) {
				int differenceBetweenNotesInHalfTones = Math
						.abs(toneDistanceFromToneToMiddleLineInHalfTones[distanceIndex - 1]
								- toneDistanceFromToneToMiddleLineInHalfTones[distanceIndex]);
				if (differenceBetweenNotesInHalfTones == 1) {
					if (isStemUp) {
						right += 2 * noteWidth;
						left += 2 * noteWidth;
					} else {
						left -= 2 * noteWidth;
						right -= 2 * noteWidth;
					}
				}
			}

			RectF rect = new RectF(left, top, right, bottom);

			noteSurroundingRects[distanceIndex] = rect;
			noteSheetCanvas.getCanvas().drawOval(noteSurroundingRects[distanceIndex], paint);
		}

		return noteSurroundingRects;
	}
}
