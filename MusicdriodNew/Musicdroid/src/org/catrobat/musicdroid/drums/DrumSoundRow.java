package org.catrobat.musicdroid.drums;

import java.util.Observable;
import java.util.Observer;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementArray;
import org.simpleframework.xml.Root;

import android.content.Context;
import android.media.SoundPool;
import android.util.Log;
import org.catrobat.musicdroid.SoundManager;
import org.catrobat.musicdroid.types.DrumType;

@Root
public class DrumSoundRow implements Observer {
	private static final String TAG = DrumSoundRow.class.getSimpleName();
	private Context context = null;
	private LayoutDrums layoutDrums = null;
	private LayoutDrumSoundRow layoutRow = null;
	private SoundPool soundpool = null;
	@ElementArray
	private int[] beatArray = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	@Element
	private int rawId = 0;
	@Element
	private int soundPoolId = 0;
	@Element
	private String soundRowName = null;
	
	public DrumSoundRow() {}
	
	public DrumSoundRow(Context context, LayoutDrums manager, DrumType drumType) 
	{
		this.context = context;
		this.layoutDrums = manager;
		this.rawId = drumType.getSoundResource();
		this.soundRowName = this.context.getResources().getString(drumType.getNameResource());
		
		layoutRow = new LayoutDrumSoundRow(this.context, this, this.soundRowName);
		
        soundPoolId = SoundManager.loadSound(rawId);
		//MIT-FIX
		//soundpool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
		//soundPoolId = soundpool.load(context, rawId, 1);
	}
	
	@Override
	public void update(Observable observable, Object data) 
	{
		int currentBeat = (Integer)data;
		Log.d(TAG, "Incoming Object: " + currentBeat);
		if(beatArray[currentBeat] == 1)
		{
			SoundManager.playSingleSound(soundPoolId, 1, 1);
		}
	}

	public void togglePosition(int position)
	{
		beatArray[position] = beatArray[position] == 0 ? 1 : 0;
		layoutDrums.setUnsavedChanges(true);
	}

	public void updateRow(DrumSoundRow sourceRow) {
		setSoundPoolId(sourceRow.getSoundPoolId());
	    setBeatArray(sourceRow.getBeatArray());
	    setSoundRowNameAndUpdateLayout(sourceRow.getSoundRowName());		
	}
	
	public int getBeatArrayValueAtPosition(int position)
	{
		return beatArray[position];
	}
	
	public void setSoundPoolIdByDrumString(String drumString)
	{
		DrumType type = layoutDrums.getDrumTypeByString(drumString);
		soundPoolId = SoundManager.loadSound(type.getSoundResource());
		soundRowName = context.getResources().getString(type.getNameResource());
		layoutDrums.setUnsavedChanges(true); 
	}
	
	public void setSoundRowNameAndUpdateLayout(String srn)
	{
		Log.i(TAG, "setSoundRowNameAndUpdateLayout " + soundRowName);
		if(!soundRowName.equals(srn))
		{
		  soundRowName = srn;
		  layoutRow.setDrumSoundRowName(soundRowName);
		}
	}
	
	public int getSoundPoolId()
	{
		return soundPoolId;
	}
	
	public int[] getBeatArray()
	{
		return beatArray;
	}
	
	public int getRawId()
	{
		return rawId;
	}
	
	public void setBeatArray(int[] beatArray)
	{
		this.beatArray = beatArray;
		layoutRow.updateOnPresetLoad(this.beatArray);
	}
	
	public void setSoundPoolId(int spId)
	{
		soundPoolId =  spId;
	}
	
	public void setRawId(int rId)
	{
		rawId = rId;
	}
	
	public LayoutDrumSoundRow getLayout()
	{
		return layoutRow;
	}
	
	public String getSoundRowName()
	{
		return soundRowName;
	}
	
	public void setSoundRowName(String srn)
	{
		soundRowName = srn;
	}
}
