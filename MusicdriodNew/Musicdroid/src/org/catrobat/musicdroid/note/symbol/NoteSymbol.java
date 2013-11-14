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
package org.catrobat.musicdroid.note.symbol;

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
import org.catrobat.musicdroid.note.NoteLength;
import org.catrobat.musicdroid.note.NoteName;
import org.catrobat.musicdroid.note.draw.NoteBodyDrawer;
import org.catrobat.musicdroid.note.draw.NotePosition;
import org.catrobat.musicdroid.tool.draw.NoteSheetCanvas;
import org.catrobat.musicdroid.tools.PictureTools;

import java.util.LinkedList;
import java.util.List;

public class NoteSymbol extends AbstractSymbol {

	protected NoteName[] noteNames;

	public NoteSymbol(NoteLength[] noteLengths, NoteName[] noteNames) {
		super(noteLengths);
		this.noteNames = noteNames;
	}

	public NoteName[] getNoteNames() {
		return noteNames;
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof NoteSymbol)) {
			return false;
		}

		NoteSymbol noteSymbol = (NoteSymbol) obj;
		NoteLength[] otherNoteLengths = noteSymbol.getNoteLengths();
		NoteName[] otherNoteNames = noteSymbol.getNoteNames();

		if (otherNoteLengths.length != noteLengths.length) {
			return false;
		}

		for (int i = 0; i < noteLengths.length; i++) {
			if (noteLengths[i] != otherNoteLengths[i]) {
				return false;
			}
		}

		if (otherNoteNames.length != noteNames.length) {
			return false;
		}

		for (int i = 0; i < noteNames.length; i++) {
			if (noteNames[i] != otherNoteNames[i]) {
				return false;
			}
		}

		return true;
	}

	@Override
	public void draw(NoteSheetCanvas noteSheetCanvas, Key key, Context context) {
		/// das wäre eigentlich ok. die mehreren Notenames müssen aber den gleichen x wert behalten
		/// dafür müssen wir es schaffen, dass eine Note quasi 2 zeichnet (überbundene von vornhinein)
		for (NoteLength length : this.getNoteLengths()) {
			drawNotesForOnePosition(noteSheetCanvas, key, context, length);
		}
	}

	private void drawNotesForOnePosition(NoteSheetCanvas noteSheetCanvas, Key key, Context context,
			NoteLength noteLength) {
		int[] toneDistanceFromToneToMiddleLineInHalfLineDistances = new int[this.getNoteNames().length];
		List<Integer> yPositionsForCrosses = new LinkedList<Integer>();

		for (int noteIndex = 0; noteIndex < this.getNoteNames().length; noteIndex++) {
			toneDistanceFromToneToMiddleLineInHalfLineDistances[noteIndex] = NotePosition
					.getToneDistanceFromToneToMiddleLineInHalfLineDistances(key, this.getNoteNames()[noteIndex]);

			if (this.getNoteNames()[noteIndex].isSigned()) {
				yPositionsForCrosses.add(toneDistanceFromToneToMiddleLineInHalfLineDistances[noteIndex]);
			}
		}

		if (yPositionsForCrosses.size() > 0) {
			drawCross(noteSheetCanvas, yPositionsForCrosses, context);
		}

		RectF noteSurroundingRect[] = new RectF[this.getNoteNames().length];

		if (noteLength == NoteLength.WHOLE || noteLength == NoteLength.HALF) {
			noteSurroundingRect = NoteBodyDrawer.drawBody(noteSheetCanvas,
					toneDistanceFromToneToMiddleLineInHalfLineDistances, false);
		} else {
			noteSurroundingRect = NoteBodyDrawer.drawBody(noteSheetCanvas,
					toneDistanceFromToneToMiddleLineInHalfLineDistances, true);
		}

		for (int noteIndex = 0; noteIndex < this.getNoteNames().length; noteIndex++) {
			drawHelpLines(toneDistanceFromToneToMiddleLineInHalfLineDistances[noteIndex],
					noteSurroundingRect[noteIndex], noteSheetCanvas);

		}

		//		for(RectF surroundingRect : noteSurroundingRect) {   
		//			Point startPointOfStem = new Point();
		//			   startPointOfStem.y = (int) Math.round((noteSurroundingRect.bottom + noteSurroundingRect.top) / 2.0);
		//
		//			   boolean isUpDirectedStem;
		//
		//			   if (toneDistanceFromToneToMiddleLineInHalfLineDistances > 0) {
		//			    isUpDirectedStem = true;
		//			    startPointOfStem.x = (int) noteSurroundingRect.right;
		//			   } else {
		//			    isUpDirectedStem = false;
		//			    startPointOfStem.x = (int) noteSurroundingRect.left;
		//			   }
		//		}
	}

	private void drawHelpLines(int toneDistanceFromToneToMiddleLineInHalfLineDistances, RectF noteSurroundingRect,
			NoteSheetCanvas noteSheetCanvas) {
		//GET HÖCHSTE NOTE NUR FÜR DIE MÜSSEN HILFSLINIEN GEZEICHNET WERDEN UND TIEFSTE EVTL AUCH
		int numberOfHalfLineDistancesWithoutHelpLines = 5;

		if (Math.abs(toneDistanceFromToneToMiddleLineInHalfLineDistances) > numberOfHalfLineDistancesWithoutHelpLines) {

			for (int halfTones = 5; halfTones <= Math.abs(toneDistanceFromToneToMiddleLineInHalfLineDistances); halfTones++) {
				if (halfTones % 2 == 0) {
					Paint paint = new Paint();
					paint.setColor(Color.BLACK);
					paint.setStyle(Style.STROKE);
					paint.setStrokeWidth(4);
					int startX = (int) (noteSurroundingRect.left - (noteSurroundingRect.width() / 3));
					int stopX = (int) (noteSurroundingRect.right + (noteSurroundingRect.width() / 3));
					int distanceFromCenterLineToLinePosition = halfTones
							* noteSheetCanvas.getDistanceBetweenNoteLines() / 2;
					if (toneDistanceFromToneToMiddleLineInHalfLineDistances < 0) {
						distanceFromCenterLineToLinePosition *= (-1);
					}
					int startY = noteSheetCanvas.getYPositionOfCenterLine() + distanceFromCenterLineToLinePosition;
					int stopY = startY;
					noteSheetCanvas.getCanvas().drawLine(startX, startY, stopX, stopY, paint);
				}
			}
		}
	}

	private void drawCross(NoteSheetCanvas noteSheetCanvas,
			List<Integer> toneDistanceFromToneToMiddleLineInHalfLineDistances, Context context) {
		int crossHeight = 2 * noteSheetCanvas.getDistanceBetweenNoteLines();

		Resources res = context.getResources();
		Bitmap crossPicture = BitmapFactory.decodeResource(res, R.drawable.cross);
		int xStartPositionForCrosses = noteSheetCanvas.getStartXPointForNextSmallSymbolSpace();

		for (Integer yPositionForCross : toneDistanceFromToneToMiddleLineInHalfLineDistances) {
			Rect rect = PictureTools.calculateProportionalPictureContourRect(crossPicture, crossHeight,
					xStartPositionForCrosses, noteSheetCanvas.getYPositionOfCenterLine() + yPositionForCross
							* noteSheetCanvas.getDistanceBetweenNoteLines() / 2);

			noteSheetCanvas.getCanvas().drawBitmap(crossPicture, null, rect, null);
		}

	}

	private static boolean isSteamNeeded(NoteLength noteLength) {
		return noteLength != NoteLength.WHOLE;
	}
}
