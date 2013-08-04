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
import android.view.View;

import org.catrobat.musicdroid.note.Key;
import org.catrobat.musicdroid.note.Track;
import org.catrobat.musicdroid.tool.draw.NoteSheetCanvas;

import com.actionbarsherlock.R.string;

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

	public NoteSheetView(Context context) {
		super(context);
		this.context = context;
		paint = new Paint();
		track = new Track();

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

		drawLines();
		drawLineEndBars();
		drawKey();
		drawTactUnit();
		drawBeats();

	};

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
		drawFrontBars();
		drawEndBar();
	}

	private void drawFrontBars() {
		drawBoldBar(xStartPositionOfLine);
		drawThinBar(xStartPositionOfLine + 2 * BOLD_BAR_WIDTH);
	}

	private void drawEndBar() {
		drawBoldBar(xEndPositionOfLine - BOLD_BAR_WIDTH);
	}

	private void drawThinBar(int xBarStartPosition) {
		Rect boldBar = new Rect(xBarStartPosition, yCenter - halfBarHeight,
				xBarStartPosition + THIN_BAR_WIDTH, yCenter + halfBarHeight);

		noteSheetCanvas.getCanvas().drawRect(boldBar, paint);
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
		int keyHeight = distanceBetweenLines * HEIGHT_OF_KEY_IN_LINE_SPACES;

		Point leftUpperOfRect = new Point(xStartPositionOfLine
				+ NOTE_SHEET_PADDING, yCenter - keyHeight / 2);

		Point rightBottomOfRect = new Point(xStartPositionOfLine
				+ distanceBetweenLines * 3, yCenter + keyHeight / 2);

		Rect rect = new Rect(leftUpperOfRect.x, leftUpperOfRect.y,
				rightBottomOfRect.x, rightBottomOfRect.y);

		noteSheetCanvas.getCanvas().drawBitmap(keyPicture, null, rect, null);
	}

	private void drawTactUnit() {

	}

	private void drawBeats() {
	}

}
