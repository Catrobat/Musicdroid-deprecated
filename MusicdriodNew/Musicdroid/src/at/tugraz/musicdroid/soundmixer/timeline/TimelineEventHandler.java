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
package at.tugraz.musicdroid.soundmixer.timeline;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout.LayoutParams;
import at.tugraz.musicdroid.soundmixer.SoundMixer;

public class TimelineEventHandler extends Handler {
	private static TimelineEventHandler instance = null;
	private View trackPositionView = null;
	private Timeline timeline = null;

	public static TimelineEventHandler getInstance() {
		if (instance == null) {
			instance = new TimelineEventHandler();
		}
		return instance;
	}

	public void init(Timeline t) {
		this.timeline = t;
	}

	@Override
	public void handleMessage(Message msg) {
		Bundle b = msg.getData();
		if (b.containsKey("position")) {
			trackPositionView = timeline.getTrackPositionView();
			int key = b.getInt("position");
			Log.i("TimelineEventHandler", "MessageDuration = " + key
					+ "  Width = " + key
					* SoundMixer.getInstance().getPixelPerSecond());
			LayoutParams params = (LayoutParams) trackPositionView
					.getLayoutParams();
			params.width = key * SoundMixer.getInstance().getPixelPerSecond();
			trackPositionView.setLayoutParams(params);
		}
	}
}
