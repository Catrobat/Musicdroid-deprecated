package org.catrobat.musicdroid.drums;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import org.catrobat.musicdroid.R;

public class DrumSoundPositionRowLayout extends RelativeLayout{
	private Context context = null;
	private ArrayList<ImageView> positionArray = null;

	
	public DrumSoundPositionRowLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}	
	
	public DrumSoundPositionRowLayout(Context context) {
		super(context);
		this.context = context;
		this.positionArray = new ArrayList<ImageView>();
		
        LayoutInflater inflater = LayoutInflater.from(this.context);
        inflater.inflate(R.layout.drum_sound_row_position_layout, this);
        
        populatePositionMap();
	}


	private void populatePositionMap()
	{
		positionArray.add((ImageView) findViewById(R.id.drum_position_1));
		positionArray.add((ImageView) findViewById(R.id.drum_position_2));
		positionArray.add((ImageView) findViewById(R.id.drum_position_3));
		positionArray.add((ImageView) findViewById(R.id.drum_position_4));
		positionArray.add((ImageView) findViewById(R.id.drum_position_5));
		positionArray.add((ImageView) findViewById(R.id.drum_position_6));
		positionArray.add((ImageView) findViewById(R.id.drum_position_7));
		positionArray.add((ImageView) findViewById(R.id.drum_position_8));
		positionArray.add((ImageView) findViewById(R.id.drum_position_9));
		positionArray.add((ImageView) findViewById(R.id.drum_position_10));
		positionArray.add((ImageView) findViewById(R.id.drum_position_11));
		positionArray.add((ImageView) findViewById(R.id.drum_position_12));
		positionArray.add((ImageView) findViewById(R.id.drum_position_13));
		positionArray.add((ImageView) findViewById(R.id.drum_position_14));
		positionArray.add((ImageView) findViewById(R.id.drum_position_15));
		positionArray.add((ImageView) findViewById(R.id.drum_position_16));
	}

	public void setBeatVisibility(int beat) {
		setPreviousBeatInvisible(beat);
		positionArray.get(beat).setVisibility(VISIBLE);	
	}
	
	private void setPreviousBeatInvisible(int beat) {
		if(beat == 0)
			positionArray.get(positionArray.size()-1).setVisibility(INVISIBLE);
		else
			positionArray.get(beat-1).setVisibility(INVISIBLE);
	}
	
}
