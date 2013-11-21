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
package org.catrobat.musicdroid.piano;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * @author musicdroid
 * 
 */
public class PianoView extends LinearLayout {

	private int activeOctave = 3;
	private PianoOctaveView pianoView;
	private Button buttonLeft;
	private Button buttonRight;
	private LayoutParams layoutParams;

	public PianoView(Context context) {
		super(context);
		this.layoutParams = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, 1.0f);
		this.buttonLeft = new Button(context);
		this.pianoView = new PianoOctaveView(context, activeOctave);
		this.buttonRight = new Button(context);
		pianoView.setLayoutParams(layoutParams);
		this.addView(buttonLeft);
		this.addView(pianoView);
		this.addView(buttonRight);

		this.buttonLeft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				incrementOctave();
				removeAllViews();
				addView(buttonLeft);
				pianoView = new PianoOctaveView(getContext(), activeOctave);
				pianoView.setLayoutParams(layoutParams);
				addView(pianoView);
				addView(buttonRight);
			}

		});

		this.buttonRight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				decrementOctave();
				removeAllViews();
				addView(buttonLeft);
				pianoView = new PianoOctaveView(getContext(), activeOctave);
				pianoView.setLayoutParams(layoutParams);
				addView(pianoView);
				addView(buttonRight);
			}

		});
	}

	public void incrementOctave() {
		if (activeOctave > 1) {
			if (--activeOctave == 1) {
				this.buttonLeft.setClickable(false);
			} else if (activeOctave == 4) {
				this.buttonRight.setClickable(true);
			}
		}
	}

	public void decrementOctave() {
		if (activeOctave < 5) {
			if (++activeOctave == 5) {
				this.buttonRight.setClickable(false);
			} else if (activeOctave == 2) {
				this.buttonLeft.setClickable(true);
			}
		}
	}

}
