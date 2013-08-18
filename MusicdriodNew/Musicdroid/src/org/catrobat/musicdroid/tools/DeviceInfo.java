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
package org.catrobat.musicdroid.tools;

import org.catrobat.musicdroid.MainActivity;

import android.content.Context;
import android.graphics.Point;

/**
 * @author matthias schlesinger
 *
 */
public class DeviceInfo {
	private static final String TAG = DeviceInfo.class.getSimpleName();
	
	public static Point getScreenSize(Context context) {
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

	public static int getScreenHeight(Context context) {
		if (context == null)
			throw new IllegalStateException("Helper not initialized");
		return getScreenSize(context).y;
	}

	public static int getScreenWidth(Context context) {
		if (context == null)
			throw new IllegalStateException("Helper not initialized");
		return getScreenSize(context).x;
	}
}
