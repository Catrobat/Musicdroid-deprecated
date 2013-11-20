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
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

import org.catrobat.musicdroid.R;
import org.catrobat.musicdroid.note.Key;
import org.catrobat.musicdroid.note.NoteLength;
import org.catrobat.musicdroid.note.NoteName;
import org.catrobat.musicdroid.note.draw.NoteBodyDrawer;
import org.catrobat.musicdroid.note.draw.NotePosition;
import org.catrobat.musicdroid.note.draw.NoteStemDrawer;
import org.catrobat.musicdroid.tool.draw.NoteSheetCanvas;
import org.catrobat.musicdroid.tools.PictureTools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class NoteSymbol extends AbstractSymbol {

	protected NoteName[] noteNames;
	protected RectF[] noteSurroundingRects;
	protected int[] toneDistancesToMiddleLineInHalflineDistances;
	protected PointF startPointForBogen;
	protected PointF endPointForBogen;

	public NoteSymbol(NoteLength[] noteLengths, NoteName[] noteNames) {
		super(noteLengths);
		this.setNoteNamesInAscendingOrder(noteNames);
		this.noteSurroundingRects = new RectF[noteNames.length];
		this.setToneDistancesFromToneToMiddleLineInHalflineDistances();
	}

	private void setNoteNamesInAscendingOrder(NoteName[] noteNames) {
		List<NoteName> names = new ArrayList<NoteName>();
		for (NoteName noteName : noteNames) {
			names.add(noteName);
		}

		Collections.sort(names);
		this.noteNames = new NoteName[noteNames.length];
		for (int noteIndex = 0; noteIndex < noteNames.length; noteIndex++) {
			this.noteNames[noteIndex] = names.get(noteIndex);
		}
	}

	public NoteName[] getNoteNames() {
		return noteNames;
	}

	private void setToneDistancesFromToneToMiddleLineInHalflineDistances() {
		Key key = Key.VIOLIN;
		this.toneDistancesToMiddleLineInHalflineDistances = new int[noteNames.length];
		for (int noteIndex = 0; noteIndex < noteNames.length; noteIndex++) {
			this.toneDistancesToMiddleLineInHalflineDistances[noteIndex] = NotePosition
					.getToneDistanceFromToneToMiddleLineInHalfLineDistances(key, this.getNoteNames()[noteIndex]);
		}
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
		for (NoteLength noteLength : this.getNoteLengths()) {
			drawNotesForOnePosition(noteSheetCanvas, key, context, noteLength);
		}

		if (this.isBogenNeeded()) {
			drawBogen(noteSheetCanvas);
		}
	}

	private void drawBogen(NoteSheetCanvas noteSheetCanvas) {
		float centerX = this.startPointForBogen.x + ((this.endPointForBogen.x - this.startPointForBogen.x) / 2);
		float centerY = this.startPointForBogen.y + ((this.endPointForBogen.y - this.startPointForBogen.y) / 2);

		double xLen = (this.endPointForBogen.x - this.startPointForBogen.x);
		double yLen = (this.endPointForBogen.y - this.startPointForBogen.y);
		float radius = (float) (Math.sqrt(xLen * xLen + yLen * yLen) / 2);

		RectF oval = new RectF(centerX - radius, centerY - radius / 2, centerX + radius, centerY + radius / 2);

		double radStartAngle = 0;
		if (this.isStemUp()) {
			radStartAngle = Math.atan2(this.endPointForBogen.y - centerY, this.endPointForBogen.x - centerX);
		} else {
			radStartAngle = Math.atan2(this.startPointForBogen.y - centerY, this.startPointForBogen.x - centerX);
		}
		float startAngle = (float) Math.toDegrees(radStartAngle);

		Path p = new Path();
		p.addArc(oval, startAngle, 180);

		Paint paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(4);
		noteSheetCanvas.getCanvas().drawArc(oval, startAngle, 180, false, paint);

	}

	private void drawNotesForOnePosition(NoteSheetCanvas noteSheetCanvas, Key key, Context context,
			NoteLength noteLength) {
		List<Integer> yPositionsForCrosses = new LinkedList<Integer>();
		boolean isStemUpdirected = this.isStemUp();
		for (int noteIndex = 0; noteIndex < this.getNoteNames().length; noteIndex++) {
			if (this.getNoteNames()[noteIndex].isSigned()) {
				yPositionsForCrosses.add(toneDistancesToMiddleLineInHalflineDistances[noteIndex]);
			}
		}

		if (yPositionsForCrosses.size() > 0) {
			drawCrosses(noteSheetCanvas, yPositionsForCrosses, context);
		}

		if (noteLength == NoteLength.WHOLE || noteLength == NoteLength.HALF) {
			noteSurroundingRects = NoteBodyDrawer.drawBody(noteSheetCanvas,
					toneDistancesToMiddleLineInHalflineDistances, false, isStemUpdirected);
		} else {
			noteSurroundingRects = NoteBodyDrawer.drawBody(noteSheetCanvas,
					toneDistancesToMiddleLineInHalflineDistances, true, isStemUpdirected);
		}

		drawHelpLines(noteSheetCanvas);

		Point startPointOfNoteStem = new Point();
		Point endPointOfNoteStem = new Point();

		if (isStemUpdirected) {
			startPointOfNoteStem.y = (int) Math
					.round((noteSurroundingRects[0].bottom + noteSurroundingRects[0].top) / 2.0);
			endPointOfNoteStem.y = (int) Math
					.round((noteSurroundingRects[noteSurroundingRects.length - 1].bottom + noteSurroundingRects[noteSurroundingRects.length - 1].top) / 2.0);
		} else {
			startPointOfNoteStem.y = (int) Math
					.round((noteSurroundingRects[noteSurroundingRects.length - 1].bottom + noteSurroundingRects[noteSurroundingRects.length - 1].top) / 2.0);
			endPointOfNoteStem.y = (int) Math
					.round((noteSurroundingRects[0].bottom + noteSurroundingRects[0].top) / 2.0);
		}

		if (NoteSymbol.isSteamNeeded(noteLength)) {
			if (isStemUpdirected) {
				startPointOfNoteStem.x = (int) noteSurroundingRects[0].right;
				endPointOfNoteStem.x = (int) noteSurroundingRects[0].right;
			} else {

				startPointOfNoteStem.x = (int) noteSurroundingRects[0].left;
				endPointOfNoteStem.x = (int) noteSurroundingRects[0].left;
			}

			NoteStemDrawer.drawStem(noteSheetCanvas, noteLength, startPointOfNoteStem, endPointOfNoteStem,
					isStemUpdirected);
		}

		if (this.isBogenNeeded()) {
			if (this.startPointForBogen == null) {
				float xPosition = noteSurroundingRects[0].left;
				float yPosition = 0;
				if (this.isStemUp()) {
					yPosition = noteSurroundingRects[0].bottom;
				} else {
					yPosition = noteSurroundingRects[noteSurroundingRects.length - 1].top;
				}
				this.startPointForBogen = new PointF(xPosition, yPosition);
			} else {
				float xPosition = noteSurroundingRects[0].right;
				float yPosition = 0;
				if (this.isStemUp()) {
					yPosition = noteSurroundingRects[0].bottom;
				} else {
					yPosition = noteSurroundingRects[noteSurroundingRects.length - 1].top;
				}
				this.endPointForBogen = new PointF(xPosition, yPosition);
			}
		}

	}

	private boolean isBogenNeeded() {
		if (this.getNoteLengths().length > 1) {
			return true;
		}

		return false;
	}

	private void drawHelpLines(NoteSheetCanvas noteSheetCanvas) {

		int numberOfHalfLineDistancesWithoutHelpLines = 5;
		for (int noteIndex = 0; noteIndex < this.getNoteNames().length; noteIndex++) {
			if (Math.abs(toneDistancesToMiddleLineInHalflineDistances[noteIndex]) > numberOfHalfLineDistancesWithoutHelpLines) {

				for (int halfTones = 5; halfTones <= Math.abs(toneDistancesToMiddleLineInHalflineDistances[noteIndex]); halfTones++) {
					if (halfTones % 2 == 0) {
						Paint paint = new Paint();
						paint.setColor(Color.BLACK);
						paint.setStyle(Style.STROKE);
						paint.setStrokeWidth(4);
						int startX = (int) (noteSurroundingRects[noteIndex].left - (noteSurroundingRects[noteIndex]
								.width() / 3));
						int stopX = (int) (noteSurroundingRects[noteIndex].right + (noteSurroundingRects[noteIndex]
								.width() / 3));
						int distanceFromCenterLineToLinePosition = halfTones
								* noteSheetCanvas.getDistanceBetweenNoteLines() / 2;
						if (toneDistancesToMiddleLineInHalflineDistances[noteIndex] < 0) {
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

	private void drawCrosses(NoteSheetCanvas noteSheetCanvas,
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

	public boolean isStemUp() {
		boolean isStemUpDirected = false;
		int noteIndex = this.getIndexForNoteWithBiggestDistanceToMiddleLine();

		if (toneDistancesToMiddleLineInHalflineDistances[noteIndex] > 0) {
			isStemUpDirected = true;
		} else {
			isStemUpDirected = false;
		}

		return isStemUpDirected;
	}

	public int getIndexForNoteWithBiggestDistanceToMiddleLine() {

		if (Math.abs(toneDistancesToMiddleLineInHalflineDistances[0]) > Math
				.abs(toneDistancesToMiddleLineInHalflineDistances[toneDistancesToMiddleLineInHalflineDistances.length - 1])) {
			return 0;
		} else {
			return toneDistancesToMiddleLineInHalflineDistances.length - 1;
		}

	}
}
