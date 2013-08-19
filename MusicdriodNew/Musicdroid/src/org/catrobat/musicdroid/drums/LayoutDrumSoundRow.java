package org.catrobat.musicdroid.drums;

import java.util.ArrayList;
import java.util.List;

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

public class LayoutDrumSoundRow extends RelativeLayout implements OnClickListener, OnLongClickListener{
	private DrumSoundRow drumSoundRow = null;
	private Context context = null;
	private String soundRowName = null;
	private DrumSoundSpinner drumSoundSpinner = null;
		
	public LayoutDrumSoundRow(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public LayoutDrumSoundRow(Context context, DrumSoundRow dsr, String rowName) {
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
		List<DrumButton> drumButtons = getListOfDrumButtons();
		for (DrumButton drumButton : drumButtons) {
			drumButton.setOnClickListener(this);
		}
		((RelativeLayout) findViewById(R.id.drum_row_descriptor_box)).setOnClickListener(this);
		((RelativeLayout) findViewById(R.id.drum_row_descriptor_box)).setOnLongClickListener(this);
	}

	public void updateOnPresetLoad(int[] beatArray)
	{
		Logger.beatArrayToLog(this.toString(), beatArray);
		List<DrumButton> drumButtons = getListOfDrumButtons();
		for(int buttonPosition = 0; buttonPosition < drumButtons.size(); buttonPosition++)
		{
			drumButtons.get(buttonPosition).changeDrawableOnClick(beatArray[buttonPosition]);
		}
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
		    drumSoundSpinner.performCustomClick();
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
	
	private List<DrumButton> getListOfDrumButtons()
	{
		List<DrumButton> drumButtons = new ArrayList<DrumButton>();
		drumButtons.add(((DrumButton) findViewById(R.id.drum_button_1_1)));
		drumButtons.add(((DrumButton) findViewById(R.id.drum_button_1_2)));
		drumButtons.add(((DrumButton) findViewById(R.id.drum_button_1_3)));
		drumButtons.add(((DrumButton) findViewById(R.id.drum_button_1_4)));
		drumButtons.add(((DrumButton) findViewById(R.id.drum_button_2_1)));
		drumButtons.add(((DrumButton) findViewById(R.id.drum_button_2_2)));
		drumButtons.add(((DrumButton) findViewById(R.id.drum_button_2_3)));
		drumButtons.add(((DrumButton) findViewById(R.id.drum_button_2_4)));
		drumButtons.add(((DrumButton) findViewById(R.id.drum_button_3_1)));
		drumButtons.add(((DrumButton) findViewById(R.id.drum_button_3_2)));
		drumButtons.add(((DrumButton) findViewById(R.id.drum_button_3_3)));
		drumButtons.add(((DrumButton) findViewById(R.id.drum_button_3_4)));
		drumButtons.add(((DrumButton) findViewById(R.id.drum_button_4_1)));
		drumButtons.add(((DrumButton) findViewById(R.id.drum_button_4_2)));
		drumButtons.add(((DrumButton) findViewById(R.id.drum_button_4_3)));
		drumButtons.add(((DrumButton) findViewById(R.id.drum_button_4_4)));
		return drumButtons;
	}
}

