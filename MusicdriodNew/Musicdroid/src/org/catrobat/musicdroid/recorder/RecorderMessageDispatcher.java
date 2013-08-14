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
package org.catrobat.musicdroid.recorder;

import android.os.Bundle;
import android.os.Message;

/**
 * @author matthias schlesinger
 *
 */
public class RecorderMessageDispatcher {
	private RecorderLayout recorderLayout; 
	private AudioVisualizer audioVisualizer;
	
	public RecorderMessageDispatcher(RecorderLayout layout, AudioVisualizer visualizer) {
		recorderLayout = layout;
		audioVisualizer = visualizer;
	}

	public void sendDurationMessage(long startTime) {
		Message msg = new Message();
		Bundle b = new Bundle();
		b.putInt("duration", (int) ((System.currentTimeMillis() - startTime) / 1000));
		msg.setData(b);
		recorderLayout.sendMessage(msg);
	}

	public void sendAmplitudeMessage(int maxAmplitude) {
		Message msg = new Message();
		Bundle b = new Bundle();
		b.putInt("amplitude", maxAmplitude);
		msg.setData(b);
		audioVisualizer.sendMessage(msg);
	}
}
