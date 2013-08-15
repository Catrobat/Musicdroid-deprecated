package org.catrobat.musicdroid.drums;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import org.catrobat.musicdroid.DrumsActivity;
import org.catrobat.musicdroid.R;
import org.catrobat.musicdroid.dialog.ExportDrumSoundDialog;
import org.catrobat.musicdroid.dialog.listener.ExportDrumSoundDialogListener;
import org.catrobat.musicdroid.types.DrumType;

public class DrumsLayout implements OnClickListener {
	private Context context = null;
	private RelativeLayout soundRowBox = null; 
	private RelativeLayout addDrumsToSoundMixerBox = null;
	private int localId = 5010;
	private boolean unsavedChanges = false;
	private ArrayList<DrumSoundRow> drumSoundRowsArray;
	private HashMap<String, DrumType> stringToDrumTypeMap;
	private ExportDrumSoundDialog addToSoundMixerDialog = null;
	
	public DrumsLayout(Context c)
	{
		this.context = c;
		this.soundRowBox = (RelativeLayout) ((DrumsActivity)context).findViewById(R.id.drums_drum_row_box);
		this.drumSoundRowsArray = new ArrayList<DrumSoundRow>();
		this.stringToDrumTypeMap = new HashMap<String, DrumType>();
		this.addDrumsToSoundMixerBox = (RelativeLayout) ((DrumsActivity)context).findViewById(R.id.drums_add_to_sound_mixer_box);
		this.addToSoundMixerDialog = new ExportDrumSoundDialog(new ExportDrumSoundDialogListener((DrumsActivity)c));
		
		populateDrumToDrumSoundMap();
        initializeDrumSoundRows();
        addDrumsToSoundMixerBox.setOnClickListener(this);
	}
		

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.drums_add_to_sound_mixer_box)
		{
			handleOnAddToSoundmixerClick();
		}
		
	}
	
	public void loadPresetToDrumLayout(DrumPreset preset)
	{
		Log.i("DrumsLayoutManager", "loadPresetToDrumLayout");
		if(preset.getDrumSoundRowsArray().size() != drumSoundRowsArray.size())
		{
			Log.e("DrumsLayoutManager", "Error at loading preset: to few/much drum rows");
		}
		for(int i = 0; i < drumSoundRowsArray.size(); i++)
		{
		    DrumSoundRow r = preset.getDrumSoundRowsArray().get(i);
		    drumSoundRowsArray.get(i).setSoundPoolId(r.getSoundPoolId());
		    drumSoundRowsArray.get(i).setBeatArray(r.getBeatArray());
		    drumSoundRowsArray.get(i).setSoundRowNameAndUpdateLayout(r.getSoundRowName());
		}
	} 
	
	public void resetLayout()
	{
		soundRowBox.removeAllViews();
		drumSoundRowsArray.clear();
		initializeDrumSoundRows();
	}
	
	
	private void handleOnAddToSoundmixerClick()
	{
		addToSoundMixerDialog.show(((DrumsActivity)context).getFragmentManager(), null);
		//((RecorderActivity)context).returnToMainActivtiy();
	}
	
	
	private void populateDrumToDrumSoundMap()
	{
		stringToDrumTypeMap.put(context.getResources().getString(DrumType.BASE_DRUM.getNameResource()), DrumType.BASE_DRUM);
		stringToDrumTypeMap.put(context.getResources().getString(DrumType.SNARE_DRUM.getNameResource()), DrumType.SNARE_DRUM);
		stringToDrumTypeMap.put(context.getResources().getString(DrumType.SNARE_DRUM_HARD.getNameResource()), DrumType.SNARE_DRUM_HARD);
		stringToDrumTypeMap.put(context.getResources().getString(DrumType.HIGH_HAT_CLOSED.getNameResource()), DrumType.HIGH_HAT_CLOSED);
		stringToDrumTypeMap.put(context.getResources().getString(DrumType.HIGH_HAT_OPEN.getNameResource()), DrumType.HIGH_HAT_OPEN);
		stringToDrumTypeMap.put(context.getResources().getString(DrumType.TOM_HIGH.getNameResource()), DrumType.TOM_HIGH);
		stringToDrumTypeMap.put(context.getResources().getString(DrumType.TOM_LOW.getNameResource()), DrumType.TOM_LOW);
		stringToDrumTypeMap.put(context.getResources().getString(DrumType.TAMBOURINE.getNameResource()), DrumType.TAMBOURINE);
		stringToDrumTypeMap.put(context.getResources().getString(DrumType.CRASH_ONE.getNameResource()), DrumType.CRASH_ONE);
		stringToDrumTypeMap.put(context.getResources().getString(DrumType.CRASH_TWO.getNameResource()), DrumType.CRASH_TWO);	
	}
	
	
	private void initializeDrumSoundRows() 
	{
		DrumSoundPositionRow position = new DrumSoundPositionRow(context);
		DrumSoundPositionRowLayout positionLayout = position.getLayout();
		positionLayout.setId(getNewId());
		((DrumsActivity)context).addObserverToEventHandler(position);
		soundRowBox.addView(positionLayout);
		
		DrumSoundRow baseDrum = new DrumSoundRow(context, this, DrumType.BASE_DRUM);
		DrumSoundRowLayout baseDrumLayout = baseDrum.getLayout();
		baseDrumLayout.setId(getNewId());
		LayoutParams layoutParamsBase = (LayoutParams) baseDrumLayout.getLayoutParams();
		layoutParamsBase.addRule(RelativeLayout.BELOW, positionLayout.getId());
		soundRowBox.addView(baseDrumLayout, layoutParamsBase);
		drumSoundRowsArray.add(baseDrum);
		((DrumsActivity)context).addObserverToEventHandler(baseDrum);
		
		DrumSoundRow snareDrum = new DrumSoundRow(context, this, DrumType.SNARE_DRUM);//R.string.snare_drum, R.raw.snare_drum);
		DrumSoundRowLayout snareDrumLayout = snareDrum.getLayout();
		snareDrumLayout.setId(getNewId());
		LayoutParams layoutParamsSnare = (LayoutParams) snareDrumLayout.getLayoutParams();
		layoutParamsSnare.addRule(RelativeLayout.BELOW, baseDrumLayout.getId());
		soundRowBox.addView(snareDrumLayout, layoutParamsSnare);
		drumSoundRowsArray.add(snareDrum);
		((DrumsActivity)context).addObserverToEventHandler(snareDrum);
		
		DrumSoundRow highHatClosed = new DrumSoundRow(context, this, DrumType.HIGH_HAT_CLOSED);//R.string.high_hat_closed, R.raw.high_hat_closed);
		DrumSoundRowLayout highHatClosedLayout = highHatClosed.getLayout();
		highHatClosedLayout.setId(getNewId());
		LayoutParams layoutParamsHighHatClosed = (LayoutParams) highHatClosedLayout.getLayoutParams();
		layoutParamsHighHatClosed.addRule(RelativeLayout.BELOW, snareDrumLayout.getId());
		soundRowBox.addView(highHatClosedLayout, layoutParamsHighHatClosed);
		drumSoundRowsArray.add(highHatClosed);
		((DrumsActivity)context).addObserverToEventHandler(highHatClosed);
		
		DrumSoundRow highHatOpen = new DrumSoundRow(context, this, DrumType.HIGH_HAT_OPEN);//R.string.high_hat_open, R.raw.high_hat_open);
		DrumSoundRowLayout highHatOpenLayout = highHatOpen.getLayout();
		highHatOpenLayout.setId(getNewId());
		LayoutParams layoutParamsHighHatOpen = (LayoutParams) highHatOpenLayout.getLayoutParams();
		layoutParamsHighHatOpen.addRule(RelativeLayout.BELOW, highHatClosedLayout.getId());
		soundRowBox.addView(highHatOpenLayout, layoutParamsHighHatOpen);
		drumSoundRowsArray.add(highHatOpen);
		((DrumsActivity)context).addObserverToEventHandler(highHatOpen);
		
		DrumSoundRow highTom = new DrumSoundRow(context, this, DrumType.TOM_HIGH);//R.string.high_tom, R.raw.tom_high);
		DrumSoundRowLayout highTomLayout = highTom.getLayout();
		highTomLayout.setId(getNewId());
		LayoutParams layoutParamsHighTom = (LayoutParams) highTomLayout.getLayoutParams();
		layoutParamsHighTom.addRule(RelativeLayout.BELOW, highHatOpenLayout.getId());
		soundRowBox.addView(highTomLayout, layoutParamsHighTom);
		drumSoundRowsArray.add(highTom);
		((DrumsActivity)context).addObserverToEventHandler(highTom);
		
		DrumSoundRow lowTom = new DrumSoundRow(context, this, DrumType.TOM_LOW);// R.string.low_tom, R.raw.tom_low);
		DrumSoundRowLayout lowTomLayout = lowTom.getLayout();
		lowTomLayout.setId(getNewId());
		LayoutParams layoutParamsLowTom = (LayoutParams) lowTomLayout.getLayoutParams();
		layoutParamsLowTom.addRule(RelativeLayout.BELOW, highTomLayout.getId());
		layoutParamsLowTom.bottomMargin = 0;
		lowTomLayout.setLayoutParams(layoutParamsLowTom);
		soundRowBox.addView(lowTomLayout, layoutParamsLowTom);
		drumSoundRowsArray.add(lowTom);
		((DrumsActivity)context).addObserverToEventHandler(lowTom);
	}
	
	private int getNewId()
	{
		localId = localId + 1;
		return localId;
	}
	
	public ArrayList<DrumSoundRow> getDrumSoundRowsArray()
	{
		return drumSoundRowsArray;
	}
	
	public DrumType getDrumTypeByString(String str)
	{
		return stringToDrumTypeMap.get(str);
	}
	
	public HashMap<String, DrumType> getStringToDrumTypeMap()
	{
		return stringToDrumTypeMap;
	}
	
	public boolean hasUnsavedChanges()
	{
		return unsavedChanges;
	}
	
	public void setUnsavedChanges(boolean state)
	{
		unsavedChanges = state;
	}


}
