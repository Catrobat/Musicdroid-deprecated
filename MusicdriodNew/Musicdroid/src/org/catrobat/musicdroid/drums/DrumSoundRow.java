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
	private Context context = null;
	private DrumsLayout layoutManager = null;
	private DrumSoundRowLayout layout = null;
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
	
	public DrumSoundRow(Context context, DrumsLayout manager, DrumType drumType) {
		this.context = context;
		this.layoutManager = manager;
		this.rawId = drumType.getSoundResource();
		this.soundRowName = this.context.getResources().getString(drumType.getNameResource());
		
		layout = new DrumSoundRowLayout(this.context, this, this.soundRowName);
		
        soundPoolId = SoundManager.loadSound(rawId);
		//MIT-FIX
		//soundpool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
		//soundPoolId = soundpool.load(context, rawId, 1);
	}
	
	@Override
	public void update(Observable observable, Object data) {
		int currentBeat = (Integer)data;
		Log.i("DrumSoundRowa", "Incoming Object: " + currentBeat);
		if(beatArray[currentBeat] == 1)
		{
			SoundManager.playSingleSound(soundPoolId, 1, 1);
		}
	}

	public void togglePosition(int position)
	{
		beatArray[position] = beatArray[position] == 0 ? 1 : 0;
		layoutManager.setUnsavedChanges(true);
	}
	
	public int getBeatArrayValueAtPosition(int position)
	{
		return beatArray[position];
	}
	
	public void setSoundPoolIdByDrumString(String drumString)
	{
		DrumType type = layoutManager.getDrumTypeByString(drumString);
		soundPoolId = SoundManager.loadSound(type.getSoundResource());
		soundRowName = context.getResources().getString(type.getNameResource());
		layoutManager.setUnsavedChanges(true); 
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
		layout.updateOnPresetLoad(this.beatArray);
	}
	
	public void setSoundPoolId(int spId)
	{
		soundPoolId =  spId;
	}
	
	public void setRawId(int rId)
	{
		rawId = rId;
	}
	
	public DrumSoundRowLayout getLayout()
	{
		return layout;
	}
	
	public String getSoundRowName()
	{
		return soundRowName;
	}
	
	public void setSoundRowName(String srn)
	{
		soundRowName = srn;
	}
	
	public void setSoundRowNameAndUpdateLayout(String srn)
	{
		Log.i("DrumSoundRow", "setSoundRowNameAndUpdateLayout " + soundRowName);
		if(!soundRowName.equals(srn))
		{
		  soundRowName = srn;
		  layout.setDrumSoundRowName(soundRowName);
		}
	}
	
}
