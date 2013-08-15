package org.catrobat.musicdroid.drums;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import org.catrobat.musicdroid.R;
import org.catrobat.musicdroid.SoundManager;
import org.catrobat.musicdroid.types.DrumType;

public class DrumSoundRowLayout extends RelativeLayout implements OnClickListener, OnLongClickListener, OnItemSelectedListener{
	private DrumSoundRow drumSoundRow = null;
	private Context context = null;
	private String soundRowName = null;
	private ArrayAdapter<String> adapter = null;
	private Spinner drumSoundSpinner = null; 
	private boolean spinnerInitialized = false;
		
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
		
		initDrumSoundSpinner();
		setDrumButtonOnClickListener();
	}
	
	private void initDrumSoundSpinner()
	{	
		drumSoundSpinner = (Spinner) findViewById(R.id.drum_sound_spinner);
		
		ArrayList<String> spinnerArray = DrumType.getTypeArray(context);
		adapter = new ArrayAdapter<String>(context, R.layout.custom_simple_spinner_item, spinnerArray);
		adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
		drumSoundSpinner.setAdapter(adapter);
		
		drumSoundSpinner.setSelection(adapter.getPosition(soundRowName));
		
		drumSoundSpinner.setEnabled(false);
		drumSoundSpinner.setClickable(false);
		drumSoundSpinner.setOnItemSelectedListener(this);
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
		printBeatArrayToLogCat(beatArray);
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
			SoundManager.getInstance().playSound(drumSoundRow.getSoundPoolId(), 1, 1);
		}
	}
	

	@Override
	public boolean onLongClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == ((RelativeLayout) findViewById(R.id.drum_row_descriptor_box)).getId())
		{
			Log.i("DrumSoundRowLayout", "LongClick on Box");
		    drumSoundSpinner.setEnabled(true);
		    drumSoundSpinner.setClickable(true);
		    drumSoundSpinner.performClick();
		    drumSoundSpinner.setEnabled(false);
		    drumSoundSpinner.setClickable(false);
		    return true;
		}
		return false;
	}


	private void printBeatArrayToLogCat(int[] beatArray)
	{
	  Log.i("DrumSoundRowLayout", "BeatArray = " + beatArray[0] + " " 
			  									 + beatArray[1] + " " 
			  									 + beatArray[2] + " "
			  									 + beatArray[3] + " " 
			  									 + beatArray[4] + " "
			  									 + beatArray[5] + " " 
			  									 + beatArray[6] + " "
			  									 + beatArray[7] + " " 
			  									 + beatArray[8] + " "
			  									 + beatArray[9] + " " 
			  									 + beatArray[10] + " "
			  									 + beatArray[11] + " "
			  									 + beatArray[12] + " "
			  									 + beatArray[13] + " "
			  									 + beatArray[14] + " "
			  									 + beatArray[15] + " ");
	}
	
	public void onItemSelected(AdapterView<?> parent, View view, 
            int pos, long id) {

		if(spinnerInitialized)
		{
		   Log.i("DrumSoundRowLayout", "onItemSelected "+drumSoundSpinner.getSelectedItem());
		   drumSoundRow.setSoundPoolIdByDrumString((String) drumSoundSpinner.getSelectedItem());
		}

		spinnerInitialized = true;  
    }

    public void onNothingSelected(AdapterView<?> parent) {
		Log.i("DrumSoundRowLayout", "onNothingSelected");
    }
    
	public void setDrumSoundRowName(String name)
	{
		drumSoundSpinner.setSelection(adapter.getPosition(name));
	}
}

