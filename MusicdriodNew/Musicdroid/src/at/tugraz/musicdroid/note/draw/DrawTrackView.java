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
package at.tugraz.musicdroid.note.draw;

import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.view.View;
import at.tugraz.musicdroid.note.Track;

/**
 * @author florian.winkelbauer, bteufl
 * 
 */
public class DrawTrackView extends View {

	private static final int POSSIBLE_LINE_SPACES_ON_SCREEN = 12;
	private static final int BOLD_BAR_WIDTH = 5;
	private static final int THIN_BAR_WIDTH = 2;
	private static final int NUMBER_LINES_FROM_CENTER_LINE_IN_BOTH_DIRECTIONS = 2;
	private static final int HEIGHT_OF_KEY_IN_LINE_SPACES = 6;
	private static final int PADDING_BETWEEN_SYMBOLS = 20;

	private Paint paint;

	private Track track;
	private int xStartPositionOfLine;
	private int xEndPositionOfLine;
	private int yCenter;
	private int screenWidth;
	private int screenHeight;
	private int distanceBetweenLines;
	private int halfBarHeight;
	private Canvas canvas;
	private Context context;
	private int idOfKeyImage;

	public DrawTrackView(Context context, int idOfKeyImage) {
		super(context);
		this.context = context;
		this.idOfKeyImage = idOfKeyImage;

		paint = new Paint();
		canvas = new Canvas();

		this.setBackgroundColor(R.drawable.screen_background_dark);

		drawLines();
		drawKey();
		drawLineEndBars();
		drawTact();
		drawBeats();
		drawNotes();

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		this.canvas = canvas;
		this.screenHeight = canvas.getHeight();
		this.screenWidth = canvas.getWidth();
		xEndPositionOfLine = this.screenWidth - PADDING_BETWEEN_SYMBOLS;
		this.yCenter = screenHeight / 2;
		this.xStartPositionOfLine = PADDING_BETWEEN_SYMBOLS;
		this.distanceBetweenLines = screenHeight
				/ POSSIBLE_LINE_SPACES_ON_SCREEN;
		this.halfBarHeight = NUMBER_LINES_FROM_CENTER_LINE_IN_BOTH_DIRECTIONS
				* distanceBetweenLines;
		paint.setColor(Color.BLACK);

		drawLines();
		drawLineEndBars();
		drawKey();
		drawTact();
		drawBeats();
		drawNotes();

		invalidate();
	};

	private void drawLines() {

		for (int lineDistanceFromCenterLine = -NUMBER_LINES_FROM_CENTER_LINE_IN_BOTH_DIRECTIONS; lineDistanceFromCenterLine <= NUMBER_LINES_FROM_CENTER_LINE_IN_BOTH_DIRECTIONS; lineDistanceFromCenterLine++) {
			int actualLinePosition = yCenter + lineDistanceFromCenterLine
					* distanceBetweenLines;
			canvas.drawLine(xStartPositionOfLine, actualLinePosition,
					xEndPositionOfLine, actualLinePosition, paint);
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

		canvas.drawRect(boldBar, paint);
	}

	private void drawBoldBar(int xBarStartPosition) {
		Rect boldBar = new Rect(xBarStartPosition, yCenter - halfBarHeight,
				xBarStartPosition + BOLD_BAR_WIDTH, yCenter + halfBarHeight);

		canvas.drawRect(boldBar, paint);
	}

	private void drawKey() {
		//refactor: save lio, reu as integer
		Resources res = context.getResources();
		Bitmap bm = BitmapFactory.decodeResource(res, idOfKeyImage);
		int keyHeight = distanceBetweenLines * HEIGHT_OF_KEY_IN_LINE_SPACES;
		Rect rect = new Rect(xStartPositionOfLine + PADDING_BETWEEN_SYMBOLS,
				yCenter - keyHeight / 2, xStartPositionOfLine
						+ distanceBetweenLines * 3, yCenter + keyHeight / 2);
		canvas.drawBitmap(bm, null, rect, null);
	}

	private void drawTact() {
	}

	private void drawBeats() {
	}

	private void drawNotes() {
	}
}
