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

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import at.tugraz.musicdroid.note.Track;

/**
 * @author florian.winkelbauer, bteufl
 * 
 */
public class DrawTrackView extends View {

	private static final int LINE_DISTANCE = 42;
	private static final int NOTE_DISTANCE = 42;
	private static final int PADDING_NOTE_LINE = 5;

	private Paint paint;

	private Track track;
	private int xStartPositionOfLine;
	private int xEndPositionOfLine;
	private int yCenter;
	private int screenWidth;
	private int screenHeight;

	private Canvas canvas;

	public DrawTrackView(Context context, int screenWidth, int screenHeight) {
		super(context);
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		yCenter = screenHeight / 2;
		xStartPositionOfLine = PADDING_NOTE_LINE;
		xEndPositionOfLine = this.screenWidth - PADDING_NOTE_LINE;

		paint = new Paint();
		canvas = new Canvas();

		drawLines();
		drawKey();
		drawLineEndBars();
		drawTact();
		drawBeats();
		drawNotes();

	}

	/**
	 * 
	 */
	private void drawLineEndBars() {
		// TODO Auto-generated method stub

	}

	private void drawLines() {

		paint.setColor(Color.BLACK);

		canvas.drawLine(xStartPositionOfLine, yCenter, xEndPositionOfLine,
				yCenter, paint);

		invalidate();

		// int last_x = 0;
		// if (tones.size() > 0)
		// last_x = ((Tone) (tones.get(tones.size() - 1))).get_X() + 50;
		//
		// if (last_x < this.getRight())
		// last_x = this.getRight();
		//
		// drawViolinschluessel(canvas);
		// for (int i = 0; i < 5; i++) {
		// canvas.drawLine(this.getLeft(), first_line_ + i * radius_ * 2,
		// last_x, first_line_ + i * radius_ * 2, paint);
		// }
		//
		// Paint p_ = new Paint(paint);
		// p_.setStrokeWidth(10);
		//
		// canvas.drawLine(last_x - 10, first_line_, last_x - 10, first_line_ +
		// 8
		// * radius_, paint);
		// canvas.drawLine(last_x, first_line_ - 1, last_x, first_line_ + 8
		// * radius_ + 1, p_);
		//
		// canvas.drawLine(1, first_line_ - 1, 1, first_line_ + 8 * radius_ + 1,
		// p_);
		// canvas.drawLine(10, first_line_, 10, first_line_ + 8 * radius_,
		// paint);

	}

	private void drawKey() {
	}

	private void drawTact() {
	}

	private void drawBeats() {
	}

	private void drawNotes() {
	}
}
