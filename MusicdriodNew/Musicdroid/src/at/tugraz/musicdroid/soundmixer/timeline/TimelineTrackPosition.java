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

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import at.tugraz.musicdroid.R;

public class TimelineTrackPosition {
	private Timeline parent;
	private RelativeLayout parentLayout;
	private Context context;
	private int colorId;
	private View trackPositionView = null;

	// private TextView trackPositionText = null;

	public TimelineTrackPosition(Timeline t, Context c, int colId) {
		parent = t;
		parentLayout = (RelativeLayout) parent
				.findViewById(R.id.timeline_bottom);
		context = c;
		colorId = colId;
		addTrackPosition();
		// addTrackPositionText();
	}

	public void updateTrackPosition(int pixPos, int secPos) {
		trackPositionView.setVisibility(View.VISIBLE);
		// trackPositionText.setVisibility(View.VISIBLE);

		RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) trackPositionView
				.getLayoutParams();
		layoutParams.leftMargin = pixPos;
		trackPositionView.setLayoutParams(layoutParams);

		// trackPositionText.setText(Helper.getInstance().durationStringFromInt(secPos));
	}

	private void addTrackPosition() {
		trackPositionView = new View(context);
		LayoutParams seperatorParams = new RelativeLayout.LayoutParams(2,
				LayoutParams.WRAP_CONTENT);
		seperatorParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		trackPositionView.setLayoutParams(seperatorParams);
		trackPositionView.setId(parent.getNewId());
		trackPositionView.setBackgroundColor(context.getResources().getColor(
				colorId));
		trackPositionView.setVisibility(View.INVISIBLE);
		parentLayout.addView(trackPositionView);
	}

	// private void addTrackPositionText()
	// {
	// trackPositionText = new TextView(context);
	//
	// trackPositionText.setText("00:00");
	// trackPositionText.setTextColor(context.getResources().getColor(R.color.custom_background_color));
	// LayoutParams textParams = new
	// RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
	// LayoutParams.WRAP_CONTENT);
	// textParams.addRule(RelativeLayout.ALIGN_LEFT, trackPositionView.getId());
	// textParams.leftMargin = 5;
	// trackPositionText.setLayoutParams(textParams);
	// trackPositionText.setVisibility(View.INVISIBLE);
	// parent.addView(trackPositionText);
	// }
	//

	public View getTrackPosition() {
		return trackPositionView;
	}

	// public TextView getTrackPositionText() {
	// return trackPositionText;
	// }

}
