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

import org.catrobat.musicdroid.R;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.catrobat.musicdroid.note.Key;
import org.catrobat.musicdroid.note.Note;
import org.catrobat.musicdroid.note.NoteLength;
import org.catrobat.musicdroid.note.NoteName;
import org.catrobat.musicdroid.note.Track;
import org.catrobat.musicdroid.tool.draw.NoteSheetCanvas;

/**
 * @author florian.winkelbauer, bteufl
 * 
 */
public class NoteSheetView extends View {

	private static final int BOLD_BAR_WIDTH = 5;
	private static final int THIN_BAR_WIDTH = 2;
	private static final int NUMBER_LINES_FROM_CENTER_LINE_IN_BOTH_DIRECTIONS = 2;
	private static final int HEIGHT_OF_KEY_IN_LINE_SPACES = 6;
	private static final int NOTE_SHEET_PADDING = 20;

	private Paint paint;

	private Track track;
	private int xStartPositionOfLine;
	private int xEndPositionOfLine;
	private int yCenter;
	private int distanceBetweenLines;
	private int halfBarHeight;
	private NoteSheetCanvas noteSheetCanvas;
	private Context context;
	private int xPositionOfNextSheetElement;

	public NoteSheetView(Context context) {
		super(context);
		this.context = context;
		paint = new Paint();
		track = new Track();

		track.addSymbol(new Note(NoteName.C3, NoteLength.WHOLE));
		track.addSymbol(new Note(NoteName.C3S, NoteLength.QUARTER));
		track.addSymbol(new Note(NoteName.D3, NoteLength.WHOLE));
		track.addSymbol(new Note(NoteName.D3S, NoteLength.WHOLE));
		track.addSymbol(new Note(NoteName.B3, NoteLength.QUARTER));

		this.xPositionOfNextSheetElement = NOTE_SHEET_PADDING;

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		this.noteSheetCanvas = new NoteSheetCanvas(canvas);
		xEndPositionOfLine = noteSheetCanvas.getCanvas().getWidth()
				- NOTE_SHEET_PADDING;
		this.yCenter = noteSheetCanvas.getYPositionOfCenterLine();
		this.xStartPositionOfLine = NOTE_SHEET_PADDING;
		this.distanceBetweenLines = noteSheetCanvas
				.getDistanceBetweenNoteLines();
		this.halfBarHeight = NUMBER_LINES_FROM_CENTER_LINE_IN_BOTH_DIRECTIONS
				* distanceBetweenLines;
		paint.setColor(Color.BLACK);

		drawSheetElements();

		this.setXPositionOfNextSheetElement(NOTE_SHEET_PADDING);
	};

	private void drawSheetElements() {

		drawLines();
		drawLineEndBars();
		drawKey();
		drawTactUnit();
		drawBeats();
		TrackDrawer.drawTrack(noteSheetCanvas, track);
	}

	private void drawLines() {

		for (int lineDistanceFromCenterLine = -NUMBER_LINES_FROM_CENTER_LINE_IN_BOTH_DIRECTIONS; lineDistanceFromCenterLine <= NUMBER_LINES_FROM_CENTER_LINE_IN_BOTH_DIRECTIONS; lineDistanceFromCenterLine++) {
			int actualLinePosition = yCenter + lineDistanceFromCenterLine
					* distanceBetweenLines;
			noteSheetCanvas.getCanvas().drawLine(xStartPositionOfLine,
					actualLinePosition, xEndPositionOfLine, actualLinePosition,
					paint);
		}

	}

	private void drawLineEndBars() {
		paint.setStyle(Style.FILL);
		drawEndBar();
		drawFrontBars();
	}

	private void drawFrontBars() {
		drawBoldBar(xStartPositionOfLine);
		drawThinBar(xStartPositionOfLine + 2 * BOLD_BAR_WIDTH);
	}

	private void drawEndBar() {
		int leftPositionOfEndBar = xEndPositionOfLine - BOLD_BAR_WIDTH;
		drawBoldBar(leftPositionOfEndBar);
		noteSheetCanvas.setEndXPositionNotes(leftPositionOfEndBar);
	}

	private void drawThinBar(int xBarStartPosition) {
		int xEndThinBar = xBarStartPosition + THIN_BAR_WIDTH;
		Rect boldBar = new Rect(xBarStartPosition, yCenter - halfBarHeight,
				xEndThinBar, yCenter + halfBarHeight);

		noteSheetCanvas.getCanvas().drawRect(boldBar, paint);
		this.setXPositionOfNextSheetElement(xEndThinBar);
	}

	private void drawBoldBar(int xBarStartPosition) {
		Rect boldBar = new Rect(xBarStartPosition, yCenter - halfBarHeight,
				xBarStartPosition + BOLD_BAR_WIDTH, yCenter + halfBarHeight);

		noteSheetCanvas.getCanvas().drawRect(boldBar, paint);
	}

	private void drawKey() {
		Resources res = context.getResources();
		Bitmap keyPicture;
		if (track.getKey() == Key.VIOLIN) {
			keyPicture = BitmapFactory.decodeResource(res, R.drawable.violine);
		} else {
			keyPicture = BitmapFactory.decodeResource(res, R.drawable.violine);
		}

		int keyPictureHeight = distanceBetweenLines
				* HEIGHT_OF_KEY_IN_LINE_SPACES;

		Rect rect = calculateProportionalPictureContourRect(keyPicture,
				keyPictureHeight);

		noteSheetCanvas.getCanvas().drawBitmap(keyPicture, null, rect, null);
		this.setXPositionOfNextSheetElement(rect.right);
	}

	private void drawTactUnit() {
		Resources res = context.getResources();
		Bitmap tactPicture;

		// TODO: Tact has to be checked here
		tactPicture = BitmapFactory.decodeResource(res, R.drawable.tact_3_4);

		int tactPictureHeight = distanceBetweenLines * 4;

		// Point leftUpperOfRect = new Point(this.xPositionOfNextSheetElement,
		// yCenter - 2 * distanceBetweenLines);
		// Point rightBottomOfRect = new Point(
		// this.xPositionOfNextSheetElement + 100, yCenter + 2
		// * distanceBetweenLines);
		//
		// Rect rect1 = new Rect(leftUpperOfRect.x, leftUpperOfRect.y,
		// rightBottomOfRect.x, rightBottomOfRect.y);

		Rect rect = this.calculateProportionalPictureContourRect(tactPicture,
				tactPictureHeight);

		noteSheetCanvas.getCanvas().drawBitmap(tactPicture, null, rect, null);
		noteSheetCanvas.setStartXPositionNotes(rect.right);
	}

	private void drawBeats() {
	}

	private Rect calculateProportionalPictureContourRect(
			Bitmap originalPicture, int height) {

		double proportionHeigth = originalPicture.getHeight() / height;

		int keyPictureWidth = (int) (originalPicture.getWidth() / proportionHeigth);

		Point leftUpperOfRect = new Point(this.xPositionOfNextSheetElement,
				yCenter - height / 2);

		Point rightBottomOfRect = new Point(this.xPositionOfNextSheetElement
				+ keyPictureWidth, yCenter + height / 2);

		return new Rect(leftUpperOfRect.x, leftUpperOfRect.y,
				rightBottomOfRect.x, rightBottomOfRect.y);
	}

	private void setXPositionOfNextSheetElement(int newPosition) {
		this.xPositionOfNextSheetElement = newPosition;
	}

}
