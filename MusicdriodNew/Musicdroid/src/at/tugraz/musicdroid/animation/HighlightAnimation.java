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
package at.tugraz.musicdroid.animation;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

public class HighlightAnimation {
	public static HighlightAnimation instance = null;

	public static HighlightAnimation getInstance() {
		if (instance == null) {
			instance = new HighlightAnimation();
		}
		return instance;
	}

	public void highlightViewAnimation(View v) {
		final Animation animation = new AlphaAnimation(1, 0);
		animation.setDuration(750);
		animation.setInterpolator(new LinearInterpolator());
		animation.setRepeatCount(5);
		animation.setRepeatMode(Animation.REVERSE);
		v.startAnimation(animation);

	}
}
