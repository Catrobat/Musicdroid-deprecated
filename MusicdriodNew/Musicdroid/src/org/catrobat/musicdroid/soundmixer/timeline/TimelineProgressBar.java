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

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import org.catrobat.musicdroid.soundmixer.SoundMixer;

/**
 * @author matthias schlesinger
 */
public class TimelineProgressBar extends View {
	public TimelineProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void rewind() {
		LayoutParams params = (LayoutParams) getLayoutParams();
		params.width = 0;
		setLayoutParams(params);
	}

	public void setProgres(int positionInSeconds) {
		Log.i("TimelineProgressBar", "MessageDuration = " + positionInSeconds + "  Width = " + positionInSeconds
				* SoundMixer.getInstance().getPixelPerSecond());
		LayoutParams params = (LayoutParams) getLayoutParams();
		params.width = positionInSeconds * SoundMixer.getInstance().getPixelPerSecond();
		setLayoutParams(params);
	}

	public void setStartPosition(int position) {
		RelativeLayout.LayoutParams positionLayout = (LayoutParams) getLayoutParams();
		positionLayout.setMargins(position, 0, 0, 0);
		positionLayout.width = 0;
		setLayoutParams(positionLayout);
	}
}
