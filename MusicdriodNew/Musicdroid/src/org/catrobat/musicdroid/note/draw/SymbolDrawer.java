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
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;

import org.catrobat.musicdroid.R;
import org.catrobat.musicdroid.note.Key;
import org.catrobat.musicdroid.note.NoteLength;
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
		// TODO das Dream Team Eli und Flo :D Bianca und die anderen(neuen) + Dani 

		if (symbol.getNoteLength() == NoteLength.WHOLE) {
			drawWholeBreak(noteSheetCanvas, context);
		} else if (symbol.getNoteLength() == NoteLength.HALF) {
			drawHalfBreak(noteSheetCanvas, context);
		} else {
			drawBreakPicture(noteSheetCanvas, context, symbol);
		}
	}

	private void drawBreakPicture(NoteSheetCanvas noteSheetCanvas, Context context, BreakSymbol symbol) {
		int breakHeight = 0;

		Resources res = context.getResources();
		Bitmap breakPicture = null;
		if (symbol.getNoteLength() == NoteLength.QUARTER) {
			breakHeight = 3 * noteSheetCanvas.getDistanceBetweenNoteLines();
			breakPicture = BitmapFactory.decodeResource(res, R.drawable.break_4);
		} else if (symbol.getNoteLength() == NoteLength.EIGHT) {
			breakHeight = 2 * noteSheetCanvas.getDistanceBetweenNoteLines();
			breakPicture = BitmapFactory.decodeResource(res, R.drawable.break_8);
		} else if (symbol.getNoteLength() == NoteLength.SIXTEENTH) {
			breakHeight = 4 * noteSheetCanvas.getDistanceBetweenNoteLines();
			breakPicture = BitmapFactory.decodeResource(res, R.drawable.break_16);
		}

		int xStartPositionForCrosses = noteSheetCanvas.getStartXPointForNextSmallSymbolSpace();

		Rect rect = PictureTools.calculateProportionalPictureContourRect(breakPicture, breakHeight,
				xStartPositionForCrosses, noteSheetCanvas.getYPositionOfCenterLine());

		noteSheetCanvas.getCanvas().drawBitmap(breakPicture, null, rect, null);

	}

	private void drawHalfBreak(NoteSheetCanvas noteSheetCanvas, Context context) {
		Point centerPoint = noteSheetCanvas.getCenterPointForNextSymbol();
		RectF rect = new RectF();
		int breakWidth = noteSheetCanvas.getDistanceBetweenNoteLines();
		rect.left = centerPoint.x - breakWidth / 2;
		rect.right = centerPoint.x + breakWidth / 2;
		rect.bottom = centerPoint.y;
		rect.top = centerPoint.y - breakWidth / 2;

		Paint paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setStyle(Style.FILL);
		noteSheetCanvas.getCanvas().drawRect(rect, paint);
	}

	private void drawWholeBreak(NoteSheetCanvas noteSheetCanvas, Context context) {
		Point centerPoint = noteSheetCanvas.getCenterPointForNextSmallSymbol();
		RectF rect = new RectF();
		int breakWidth = noteSheetCanvas.getDistanceBetweenNoteLines();
		rect.left = centerPoint.x - breakWidth / 2;
		rect.right = centerPoint.x + breakWidth / 2;
		rect.top = centerPoint.y;
		rect.bottom = centerPoint.y + breakWidth / 2;

		Paint paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setStyle(Style.FILL);
		noteSheetCanvas.getCanvas().drawRect(rect, paint);
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

		Point startPointOfNoteStem = new Point();
		Point endPointOfNoteStem = new Point();

		if (!isStemUpdirected) {
			startPointOfNoteStem.y = (int) Math
					.round((noteSurroundingRects.get(0).bottom + noteSurroundingRects.get(0).top) / 2.0);
			endPointOfNoteStem.y = (int) Math
					.round((noteSurroundingRects.get(noteSurroundingRects.size() - 1).bottom + noteSurroundingRects
							.get(noteSurroundingRects.size() - 1).top) / 2.0);
		} else {
			startPointOfNoteStem.y = (int) Math
					.round((noteSurroundingRects.get(noteSurroundingRects.size() - 1).bottom + noteSurroundingRects
							.get(noteSurroundingRects.size() - 1).top) / 2.0);
			endPointOfNoteStem.y = (int) Math
					.round((noteSurroundingRects.get(0).bottom + noteSurroundingRects.get(0).top) / 2.0);
		}

		if (!isStemUpdirected) {
			startPointOfNoteStem.x = (int) noteSurroundingRects.get(0).right;
			endPointOfNoteStem.x = (int) noteSurroundingRects.get(0).right;
		} else {

			startPointOfNoteStem.x = (int) noteSurroundingRects.get(0).left;
			endPointOfNoteStem.x = (int) noteSurroundingRects.get(0).left;
		}
		NoteStemDrawer.drawStem(noteSheetCanvas, NoteLength.SIXTEENTH, startPointOfNoteStem, endPointOfNoteStem,
				!isStemUpdirected);
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
