package at.tugraz.musicdroid.soundmixer.timeline;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;
import at.tugraz.musicdroid.R;
import at.tugraz.musicdroid.R.color;
import at.tugraz.musicdroid.helper.Helper;

public class TimelineTrackPosition {
	private Timeline parent;
	private Context context;
	private int colorId;
	private View trackPositionView = null;
	private TextView trackPositionText = null;
	
	public TimelineTrackPosition(Timeline t, Context c, int colId)
	{
		parent = t;
		context = c;
		colorId = colId;
		addTrackPosition();
		addTrackPositionText();
	}
	
	public void updateTrackPosition(int pixPos, int secPos)
	{
		trackPositionView.setVisibility(View.VISIBLE);
		trackPositionText.setVisibility(View.VISIBLE);
		
		RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) trackPositionView.getLayoutParams();
		layoutParams.leftMargin = pixPos;
		trackPositionView.setLayoutParams(layoutParams);
		
		trackPositionText.setText(Helper.getInstance().durationStringFromInt(secPos));
	}
	
	private void addTrackPosition()
	{
		trackPositionView = new View(context);
		LayoutParams seperatorParams = new RelativeLayout.LayoutParams(2, LayoutParams.WRAP_CONTENT);
		seperatorParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		trackPositionView.setLayoutParams(seperatorParams);
		trackPositionView.setId(parent.getNewId());
		trackPositionView.setBackgroundColor(context.getResources().getColor(colorId));
		trackPositionView.setVisibility(View.INVISIBLE);
		parent.addView(trackPositionView);
	}
	
	private void addTrackPositionText()
	{
		trackPositionText = new TextView(context);
		
		trackPositionText.setText("00:00");
		trackPositionText.setTextColor(context.getResources().getColor(R.color.custom_background_color));
		LayoutParams textParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		textParams.addRule(RelativeLayout.ALIGN_LEFT, trackPositionView.getId());
		textParams.leftMargin = 5;
		trackPositionText.setLayoutParams(textParams);
		trackPositionText.setVisibility(View.INVISIBLE);
		parent.addView(trackPositionText);
	}
	

	public View getTrackPosition() {
		return trackPositionView;
	}

	public TextView getTrackPositionText() {
		return trackPositionText;
	}
	
}
