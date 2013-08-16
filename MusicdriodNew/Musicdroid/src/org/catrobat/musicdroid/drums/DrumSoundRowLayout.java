package org.catrobat.musicdroid.drums;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.RelativeLayout;
import org.catrobat.musicdroid.R;
import org.catrobat.musicdroid.SoundManager;
import org.catrobat.musicdroid.tools.Logger;

public class DrumSoundRowLayout extends RelativeLayout implements OnClickListener, OnLongClickListener{
	private DrumSoundRow drumSoundRow = null;
	private Context context = null;
	private String soundRowName = null;
	private DrumSoundSpinner drumSoundSpinner = null;
		
	public DrumSoundRowLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public DrumSoundRowLayout(Context context, DrumSoundRow dsr, String rowName) {
		super(context);
		this.context = context;
		this.drumSoundRow = dsr;
		this.soundRowName = rowName;

        LayoutInflater inflater = LayoutInflater.from(this.context);
        inflater.inflate(R.layout.drum_sound_row_layout, this);

        initDrumSoundRow();
	}
	
	private void initDrumSoundRow()
	{
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		setLayoutParams(layoutParams);
		
		drumSoundSpinner = (DrumSoundSpinner) findViewById(R.id.drum_sound_spinner);
		drumSoundSpinner.initialize(context, drumSoundRow);
		drumSoundSpinner.setSelectionByName(soundRowName);
		setDrumButtonOnClickListener();
	}

	
	private void setDrumButtonOnClickListener()
	{
		((DrumButton) findViewById(R.id.drum_button_1_1)).setOnClickListener(this);
		((DrumButton) findViewById(R.id.drum_button_1_2)).setOnClickListener(this);
		((DrumButton) findViewById(R.id.drum_button_1_3)).setOnClickListener(this);
		((DrumButton) findViewById(R.id.drum_button_1_4)).setOnClickListener(this);
		((DrumButton) findViewById(R.id.drum_button_2_1)).setOnClickListener(this);
		((DrumButton) findViewById(R.id.drum_button_2_2)).setOnClickListener(this);
		((DrumButton) findViewById(R.id.drum_button_2_3)).setOnClickListener(this);
		((DrumButton) findViewById(R.id.drum_button_2_4)).setOnClickListener(this);
		((DrumButton) findViewById(R.id.drum_button_3_1)).setOnClickListener(this);
		((DrumButton) findViewById(R.id.drum_button_3_2)).setOnClickListener(this);
		((DrumButton) findViewById(R.id.drum_button_3_3)).setOnClickListener(this);
		((DrumButton) findViewById(R.id.drum_button_3_4)).setOnClickListener(this);
		((DrumButton) findViewById(R.id.drum_button_4_1)).setOnClickListener(this);
		((DrumButton) findViewById(R.id.drum_button_4_2)).setOnClickListener(this);
		((DrumButton) findViewById(R.id.drum_button_4_3)).setOnClickListener(this);
		((DrumButton) findViewById(R.id.drum_button_4_4)).setOnClickListener(this);
		((RelativeLayout) findViewById(R.id.drum_row_descriptor_box)).setOnClickListener(this);
		((RelativeLayout) findViewById(R.id.drum_row_descriptor_box)).setOnLongClickListener(this);
	}

	public void updateOnPresetLoad(int[] beatArray)
	{
		Logger.beatArrayToLog(this.toString(), beatArray);
		((DrumButton) findViewById(R.id.drum_button_1_1)).changeDrawableOnClick(beatArray[0]);
		((DrumButton) findViewById(R.id.drum_button_1_2)).changeDrawableOnClick(beatArray[1]);
		((DrumButton) findViewById(R.id.drum_button_1_3)).changeDrawableOnClick(beatArray[2]);
		((DrumButton) findViewById(R.id.drum_button_1_4)).changeDrawableOnClick(beatArray[3]);
		((DrumButton) findViewById(R.id.drum_button_2_1)).changeDrawableOnClick(beatArray[4]);
		((DrumButton) findViewById(R.id.drum_button_2_2)).changeDrawableOnClick(beatArray[5]);
		((DrumButton) findViewById(R.id.drum_button_2_3)).changeDrawableOnClick(beatArray[6]);
		((DrumButton) findViewById(R.id.drum_button_2_4)).changeDrawableOnClick(beatArray[7]);
		((DrumButton) findViewById(R.id.drum_button_3_1)).changeDrawableOnClick(beatArray[8]);
		((DrumButton) findViewById(R.id.drum_button_3_2)).changeDrawableOnClick(beatArray[9]);
		((DrumButton) findViewById(R.id.drum_button_3_3)).changeDrawableOnClick(beatArray[10]);
		((DrumButton) findViewById(R.id.drum_button_3_4)).changeDrawableOnClick(beatArray[11]);
		((DrumButton) findViewById(R.id.drum_button_4_1)).changeDrawableOnClick(beatArray[12]);
		((DrumButton) findViewById(R.id.drum_button_4_2)).changeDrawableOnClick(beatArray[13]);
		((DrumButton) findViewById(R.id.drum_button_4_3)).changeDrawableOnClick(beatArray[14]);
		((DrumButton) findViewById(R.id.drum_button_4_4)).changeDrawableOnClick(beatArray[15]);
	}
	
	@Override
	public void onClick(View v) {
		if(v instanceof DrumButton)
		{
			int position = ((DrumButton)v).getPosition();
			drumSoundRow.togglePosition(position-1);
			((DrumButton)v).changeDrawableOnClick(drumSoundRow.getBeatArrayValueAtPosition(position-1));
		}	
		else if(v.getId() == ((RelativeLayout) findViewById(R.id.drum_row_descriptor_box)).getId())
		{
			SoundManager.playSound(drumSoundRow.getSoundPoolId(), 1, 1);
		}
	}
	
	@Override
	public boolean onLongClick(View v) {
		if(v.getId() == ((RelativeLayout) findViewById(R.id.drum_row_descriptor_box)).getId())
		{
		    drumSoundSpinner.setEnabled(true);
		    drumSoundSpinner.setClickable(true);
		    drumSoundSpinner.performClick();
		    drumSoundSpinner.setEnabled(false);
		    drumSoundSpinner.setClickable(false);
		    return true;
		}
		return false;
	}

	public void setDrumSoundRowName(String name)
	{
		drumSoundSpinner.setSelectionByName(name);
	}

	public void align(int alignment, int alignTo) {
		LayoutParams layoutParamsBase = (LayoutParams) getLayoutParams();
		layoutParamsBase.addRule(alignment, alignTo);
	}
}

