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
package org.catrobat.musicdroid.soundmixer.timeline;

import org.catrobat.musicdroid.soundmixer.SoundMixer;
import org.catrobat.musicdroid.tools.StringFormatter;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
 
/**
 * @author matthias schlesinger
 *
 */
public class TimelineMarkerText extends TextView {
	private static final String TAG = TimelineMarkerText.class.getSimpleName();

	public TimelineMarkerText(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}


	public TimelineMarkerText(Context context, int second) {
		super(context);
		
		setText(StringFormatter.durationStringFromInt(second));
		LayoutParams textParams = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		textParams.leftMargin = SoundMixer.getInstance().getPixelPerSecond()*second - 25;
		setLayoutParams(textParams);
	}



}
