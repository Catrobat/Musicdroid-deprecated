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

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;

import org.catrobat.musicdroid.R;
import org.catrobat.musicdroid.note.Key;
import org.catrobat.musicdroid.note.NoteName;
import org.catrobat.musicdroid.note.symbol.BoundNoteSymbol;
import org.catrobat.musicdroid.note.symbol.BreakSymbol;
import org.catrobat.musicdroid.note.symbol.NoteSymbol;
import org.catrobat.musicdroid.note.symbol.Symbol;
import org.catrobat.musicdroid.tools.PictureTools;

import java.util.List;

public class SymbolDrawer {

	public void drawSymbol(Symbol symbol, NoteSheetCanvas noteSheetCanvas, Context context, Key key) {
		if (symbol instanceof BreakSymbol) {
			drawBreakSymbol((BreakSymbol) symbol, noteSheetCanvas, context);
		} else if (symbol instanceof NoteSymbol) {
			drawNoteSymbol((NoteSymbol) symbol, noteSheetCanvas, context, key);
		} else if (symbol instanceof BoundNoteSymbol) {
			drawBoundNoteSymbol((BoundNoteSymbol) symbol, noteSheetCanvas, context);
		} else {
			throw new IllegalArgumentException();
		}
	}

	private void drawBreakSymbol(BreakSymbol symbol, NoteSheetCanvas noteSheetCanvas, Context context) {
		// TODO das Dream Team Eli und Flo :D
	}

	private void drawNoteSymbol(NoteSymbol symbol, NoteSheetCanvas noteSheetCanvas, Context context, Key key) {
		boolean isStemUpdirected = symbol.isStemUp(key);

		Integer xPositionForCrosses = null;

		for (NoteName noteName : symbol.getNoteNamesSorted()) {
			if (noteName.isSigned()) {
				if (xPositionForCrosses == null) {
					xPositionForCrosses = noteSheetCanvas.getStartXPointForNextSmallSymbolSpace();
				}
				drawCross(noteSheetCanvas, xPositionForCrosses,
						NoteName.calculateDistanceToMiddleLineCountingSignedNotesOnly(key, noteName), context);
			}
		}

		List<RectF> noteSurroundingRects = NoteBodyDrawer.drawBody(noteSheetCanvas, symbol, isStemUpdirected, key);
		drawHelpLines(noteSheetCanvas, noteSurroundingRects, symbol, key);
	}

	private void drawHelpLines(NoteSheetCanvas noteSheetCanvas, List<RectF> noteSurroundingRects, NoteSymbol symbol,
			Key key) {

		int numberOfHalfLineDistancesWithoutHelpLines = 5;
		List<NoteName> noteNames = symbol.getNoteNamesSorted();
		for (int noteIndex = 0; noteIndex < noteNames.size(); noteIndex++) {
			int absDistance = Math.abs(NoteName.calculateDistanceToMiddleLineCountingSignedNotesOnly(key,
					noteNames.get(noteIndex)));
			if (absDistance > numberOfHalfLineDistancesWithoutHelpLines) {

				for (int halfTones = 5; halfTones <= absDistance; halfTones++) {
					if (halfTones % 2 == 0) {
						Paint paint = new Paint();
						paint.setColor(Color.BLACK);
						paint.setStyle(Style.STROKE);
						paint.setStrokeWidth(4);
						int startX = (int) (noteSurroundingRects.get(noteIndex).left - (noteSurroundingRects.get(
								noteIndex).width() / 3));
						int stopX = (int) (noteSurroundingRects.get(noteIndex).right + (noteSurroundingRects.get(
								noteIndex).width() / 3));
						int distanceFromCenterLineToLinePosition = halfTones
								* noteSheetCanvas.getDistanceBetweenNoteLines() / 2;
						if (NoteName
								.calculateDistanceToMiddleLineCountingSignedNotesOnly(key, noteNames.get(noteIndex)) < 0) {
							distanceFromCenterLineToLinePosition *= (-1);
						}
						int startY = noteSheetCanvas.getYPositionOfCenterLine() + distanceFromCenterLineToLinePosition;
						int stopY = startY;
						noteSheetCanvas.getCanvas().drawLine(startX, startY, stopX, stopY, paint);
					}
				}
			}
		}
	}

	private void drawBoundNoteSymbol(BoundNoteSymbol symbol, NoteSheetCanvas noteSheetCanvas, Context context) {
		// TODO das Dream Team Eli und Flo :D
	}

	private void drawCross(NoteSheetCanvas noteSheetCanvas, int xPosition, int yPosition, Context context) {
		int crossHeight = 2 * noteSheetCanvas.getDistanceBetweenNoteLines();

		Resources res = context.getResources();
		Bitmap crossPicture = BitmapFactory.decodeResource(res, R.drawable.cross);
		int xStartPositionForCrosses = noteSheetCanvas.getStartXPointForNextSmallSymbolSpace();

		Rect rect = PictureTools.calculateProportionalPictureContourRect(crossPicture, crossHeight,
				xStartPositionForCrosses,
				noteSheetCanvas.getYPositionOfCenterLine() + yPosition * noteSheetCanvas.getDistanceBetweenNoteLines()
						/ 2);

		noteSheetCanvas.getCanvas().drawBitmap(crossPicture, null, rect, null);

	}
}
