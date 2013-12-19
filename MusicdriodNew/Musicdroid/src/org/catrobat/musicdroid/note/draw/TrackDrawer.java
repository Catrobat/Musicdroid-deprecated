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

import org.catrobat.musicdroid.note.Key;
import org.catrobat.musicdroid.note.Track;
import org.catrobat.musicdroid.note.symbol.Symbol;
import org.catrobat.musicdroid.note.symbol.TrackToSymbolsConverter;
import org.catrobat.musicdroid.tool.draw.NoteSheetCanvas;

import java.util.List;

public class TrackDrawer {
	private List<Symbol> allSymbolsForTrack;
	private Key key;

	//private TacktNaming tactNaming;
	//int usedSixteenthOfCurrentTact;
	//int maximumSixteenthOfTact;

	public TrackDrawer(Track track) {
		TrackToSymbolsConverter converter = new TrackToSymbolsConverter();
		this.allSymbolsForTrack = converter.convertTrack(track);
		this.key = Key.VIOLIN;
		//this.tactNaming = track.getNaming();
	}

	public void drawTrack(NoteSheetCanvas noteSheetCanvas, Context context) {
		for (Symbol symbol : allSymbolsForTrack) {
			drawWohleSymbol(symbol, noteSheetCanvas, context);
		}
	}

	private void drawWohleSymbol(Symbol symbol, NoteSheetCanvas noteSheetCanvas, Context context) {
		// TODO fw cry
		//symbol.draw(noteSheetCanvas, key, context);
	}
}
