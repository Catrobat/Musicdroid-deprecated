package org.catrobat.musicdroid.drums;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import org.catrobat.musicdroid.R;

public class DrumSoundPositionRowLayout extends RelativeLayout{
	private Context context = null;
	private ArrayList<DrumButton> positionArray = null;
	private int currentBeat = 0;

	
	public DrumSoundPositionRowLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}	
	
	public DrumSoundPositionRowLayout(Context context) { //int rowStringId, int soundRawId) {
		super(context);
		this.context = context;
		this.positionArray = new ArrayList<DrumButton>();
		
        LayoutInflater inflater = LayoutInflater.from(this.context);
        inflater.inflate(R.layout.drum_sound_row_position_layout, this);
        
        populatePositionMap();
	}


	private void populatePositionMap()
	{
		positionArray.add((DrumButton) findViewById(R.id.drum_position_1));
		positionArray.add((DrumButton) findViewById(R.id.drum_position_2));
		positionArray.add((DrumButton) findViewById(R.id.drum_position_3));
		positionArray.add((DrumButton) findViewById(R.id.drum_position_4));
		positionArray.add((DrumButton) findViewById(R.id.drum_position_5));
		positionArray.add((DrumButton) findViewById(R.id.drum_position_6));
		positionArray.add((DrumButton) findViewById(R.id.drum_position_7));
		positionArray.add((DrumButton) findViewById(R.id.drum_position_8));
		positionArray.add((DrumButton) findViewById(R.id.drum_position_9));
		positionArray.add((DrumButton) findViewById(R.id.drum_position_10));
		positionArray.add((DrumButton) findViewById(R.id.drum_position_11));
		positionArray.add((DrumButton) findViewById(R.id.drum_position_12));
		positionArray.add((DrumButton) findViewById(R.id.drum_position_13));
		positionArray.add((DrumButton) findViewById(R.id.drum_position_14));
		positionArray.add((DrumButton) findViewById(R.id.drum_position_15));
		positionArray.add((DrumButton) findViewById(R.id.drum_position_16));
	}

	public void setBeatVisibility(int key) {
		if(key == 0)
			positionArray.get(positionArray.size()-1).setVisibility(INVISIBLE);
		else
			positionArray.get(key-1).setVisibility(INVISIBLE);
		positionArray.get(key).setVisibility(VISIBLE);
		
	}
	
}
