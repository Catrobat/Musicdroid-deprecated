package org.catrobat.musicdroid.drums;

import java.util.Observable;
import java.util.Observer;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;


public class DrumSoundPositionRow extends Handler implements Observer{
	private Context context = null;
	private LayoutDrumSoundPositionRow layout = null;

	public DrumSoundPositionRow(Context context) {
		this.context = context;
		
        layout = new LayoutDrumSoundPositionRow(this.context);
	}

	public LayoutDrumSoundPositionRow getLayout() {
		return layout;
	}
	
	@Override
	public void handleMessage(Message msg) {
		Bundle b = msg.getData();
		if(b.containsKey("position"))
		{
			int key = b.getInt("position");
			layout.setBeatVisibility(key);
		}
	}

	@Override
	public void update(Observable observable, Object data) {
		int currentBeat = (Integer)data;
		Message msg = new Message();
        Bundle b = new Bundle();
        b.putInt("position", currentBeat);
        msg.setData(b);
	    sendMessage(msg);
	}

	
}
