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
package at.tugraz.musicdroid.helper;

import android.content.Context;
import android.graphics.Point;
import at.tugraz.musicdroid.MainActivity;

public class Helper {
	private static Helper instance = null;
	private Context context = null;

	public static Helper getInstance() {
		if (instance == null) {
			instance = new Helper();
		}
		return instance;
	}

	public void init(Context c) {
		context = c;
	}

	public Point getScreenSize() {
		if (context == null)
			throw new IllegalStateException("Helper not initialized");

		if (android.os.Build.VERSION.SDK_INT >= 13) {
			Point size = new Point();
			((MainActivity) context).getWindowManager().getDefaultDisplay()
					.getSize(size);
			return size;
		} else {
			int width = ((MainActivity) context).getWindowManager()
					.getDefaultDisplay().getWidth();
			int height = ((MainActivity) context).getWindowManager()
					.getDefaultDisplay().getHeight();
			return new Point(width, height);
		}
	}

	public int getScreenHeight() {
		if (context == null)
			throw new IllegalStateException("Helper not initialized");
		return getScreenSize().y;
	}

	public int getScreenWidth() {
		if (context == null)
			throw new IllegalStateException("Helper not initialized");
		return getScreenSize().x;
	}

	public String durationStringFromInt(int duration) {
		int minutes = duration / 60;
		int seconds = duration % 60;
		String min = "" + minutes;
		String sec = "" + seconds;

		if (minutes < 10)
			min = "0" + min;
		if (seconds < 10)
			sec = "0" + sec;

		return min + ":" + sec;
	}

	public String getFilenameFromPath(String path) {
		String filename = path;
		int pos = path.lastIndexOf('/');
		if (pos != -1) {
			filename = path.substring(pos + 1);
		}

		filename = removeFileEnding(filename);

		return filename;

	}

	public String removeFileEnding(String file) {
		int pos = file.lastIndexOf('.');

		if (pos != -1) {
			file = file.substring(0, pos);
		}
		return file;
	}
}
