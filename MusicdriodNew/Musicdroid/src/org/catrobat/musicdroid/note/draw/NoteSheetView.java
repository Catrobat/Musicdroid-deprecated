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
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.view.View;

import org.catrobat.musicdroid.R;
import org.catrobat.musicdroid.note.Key;
import org.catrobat.musicdroid.note.NoteEvent;
import org.catrobat.musicdroid.note.NoteLength;
import org.catrobat.musicdroid.note.NoteName;
import org.catrobat.musicdroid.note.Track;
import org.catrobat.musicdroid.tool.draw.NoteSheetCanvas;
import org.catrobat.musicdroid.tools.NoteSheetTools;
import org.catrobat.musicdroid.tools.PictureTools;

/**
 * @author musicdroid
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
	private NoteSheetTools noteSheetTools;
	private Context context;
	private int xPositionOfNextSheetElement;
	private TrackDrawer trackDrawer;

	public NoteSheetView(Context context) {
		super(context);
		this.context = context;
		paint = new Paint();
		track = new Track();

		noteSheetTools = new NoteSheetTools();

		long tick = 0;
		Track track = new Track();
		//		track.addNoteEvent(tick, new NoteEvent(NoteName.C5S, true));
		track.addNoteEvent(tick, new NoteEvent(NoteName.F4S, true));
		track.addNoteEvent(tick, new NoteEvent(NoteName.B3, true));
		track.addNoteEvent(tick, new NoteEvent(NoteName.A3, true));
		tick += NoteLength.WHOLE.getTickDuration() + NoteLength.QUARTER.getTickDuration();
		//		track.addNoteEvent(tick, new NoteEvent(NoteName.C5S, false));
		track.addNoteEvent(tick, new NoteEvent(NoteName.F4S, false));
		track.addNoteEvent(tick, new NoteEvent(NoteName.B3, false));
		track.addNoteEvent(tick, new NoteEvent(NoteName.A3, false));

		track.addNoteEvent(tick, new NoteEvent(NoteName.D3, true));
		tick += NoteLength.HALF.getTickDuration();
		track.addNoteEvent(tick, new NoteEvent(NoteName.D3, false));

		trackDrawer = new TrackDrawer(track);
		this.xPositionOfNextSheetElement = NOTE_SHEET_PADDING;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		noteSheetTools.setNoteSheetCanvas(new NoteSheetCanvas(canvas));

		NoteSheetCanvas noteSheetCanvas = noteSheetTools.getNoteSheetCanvas();

		xEndPositionOfLine = noteSheetCanvas.getCanvas().getWidth() - NOTE_SHEET_PADDING;
		this.yCenter = noteSheetCanvas.getYPositionOfCenterLine();
		this.xStartPositionOfLine = NOTE_SHEET_PADDING;
		this.distanceBetweenLines = noteSheetCanvas.getDistanceBetweenNoteLines();
		this.halfBarHeight = NUMBER_LINES_FROM_CENTER_LINE_IN_BOTH_DIRECTIONS * distanceBetweenLines;
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
		trackDrawer.drawTrack(noteSheetTools.getNoteSheetCanvas(), context);
	}

	private void drawLines() {

		for (int lineDistanceFromCenterLine = -NUMBER_LINES_FROM_CENTER_LINE_IN_BOTH_DIRECTIONS; lineDistanceFromCenterLine <= NUMBER_LINES_FROM_CENTER_LINE_IN_BOTH_DIRECTIONS; lineDistanceFromCenterLine++) {
			int actualLinePosition = yCenter + lineDistanceFromCenterLine * distanceBetweenLines;
			noteSheetTools.getNoteSheetCanvas().getCanvas()
					.drawLine(xStartPositionOfLine, actualLinePosition, xEndPositionOfLine, actualLinePosition, paint);
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
		noteSheetTools.getNoteSheetCanvas().setEndXPositionNotes(leftPositionOfEndBar);
	}

	private void drawThinBar(int xBarStartPosition) {
		int xEndThinBar = xBarStartPosition + THIN_BAR_WIDTH;
		Rect boldBar = new Rect(xBarStartPosition, yCenter - halfBarHeight, xEndThinBar, yCenter + halfBarHeight);

		noteSheetTools.getNoteSheetCanvas().getCanvas().drawRect(boldBar, paint);
		this.setXPositionOfNextSheetElement(xEndThinBar);
	}

	private void drawBoldBar(int xBarStartPosition) {
		Rect boldBar = new Rect(xBarStartPosition, yCenter - halfBarHeight, xBarStartPosition + BOLD_BAR_WIDTH, yCenter
				+ halfBarHeight);

		noteSheetTools.getNoteSheetCanvas().getCanvas().drawRect(boldBar, paint);
	}

	private void drawKey() {
		Resources res = context.getResources();
		Bitmap keyPicture;
		//TODO AUS SETTINGS AUSlesen
		Key key = Key.VIOLIN;
		if (key == Key.VIOLIN) {
			keyPicture = BitmapFactory.decodeResource(res, R.drawable.violine);
		} else {
			keyPicture = BitmapFactory.decodeResource(res, R.drawable.bass);
		}

		int keyPictureHeight = distanceBetweenLines * HEIGHT_OF_KEY_IN_LINE_SPACES;

		Rect rect = PictureTools.calculateProportionalPictureContourRect(keyPicture, keyPictureHeight,
				this.xPositionOfNextSheetElement, yCenter);

		noteSheetTools.getNoteSheetCanvas().getCanvas().drawBitmap(keyPicture, null, rect, null);
		this.setXPositionOfNextSheetElement(rect.right);
	}

	private void drawTactUnit() {
		Resources res = context.getResources();
		Bitmap tactPicture;

		// TODO: Tact has to be checked here
		tactPicture = BitmapFactory.decodeResource(res, R.drawable.tact_3_4);

		int tactPictureHeight = distanceBetweenLines * 4;

		Rect rect = PictureTools.calculateProportionalPictureContourRect(tactPicture, tactPictureHeight,
				this.xPositionOfNextSheetElement, yCenter);

		noteSheetTools.getNoteSheetCanvas().getCanvas().drawBitmap(tactPicture, null, rect, null);
		noteSheetTools.getNoteSheetCanvas().setStartXPositionNotes(rect.right);
	}

	private void drawBeats() {
	}

	private void setXPositionOfNextSheetElement(int newPosition) {
		this.xPositionOfNextSheetElement = newPosition;
	}

}