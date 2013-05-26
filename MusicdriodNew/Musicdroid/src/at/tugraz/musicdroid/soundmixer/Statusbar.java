package at.tugraz.musicdroid.soundmixer;

import java.util.HashMap;
import java.util.Vector;

import android.content.Context;
import android.database.Observable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.Toast;
import at.tugraz.musicdroid.MainActivity;
import at.tugraz.musicdroid.R;
import at.tugraz.musicdroid.R.drawable;
import at.tugraz.musicdroid.R.id;

public class Statusbar extends Observable implements OnTouchListener {
	private ImageButton playButton;
	protected Boolean displayPlayButton;
	protected MainActivity mainActivity;

    public Statusbar(MainActivity mainActivity) {
	   this.mainActivity = mainActivity;
	   displayPlayButton = true;
	
	   playButton = (ImageButton) mainActivity.findViewById(R.id.btn_play);
	   playButton.setOnTouchListener(this);
	
	   //ADD UNDO AND REDO SUPPORT
    }
    
	@Override
	public boolean onTouch(View view, MotionEvent event) {
		switch (view.getId()) {
		case R.id.btn_play:
			onPlayTouch(event);
			return true;
		default:
			return false;
		}
	}
	
	private void onPlayTouch(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
		  if(displayPlayButton)	{
			//Toast.makeText(mainActivity.getApplicationContext(), "Someday you will hear a sound now", Toast.LENGTH_SHORT).show();
			playButton.setImageResource(R.drawable.pause_button);
			displayPlayButton = false;
			
			SoundMixer.getInstance().playAllSoundsInSoundmixer();
			//TESTING - Demonstrates playing midi and wav sounds at the same time	
		    //SoundManager.playSound(1, 1);
		    //SoundManager.playSound(2, 1);
			//TESTING
		  }
		  else {
			playButton.setImageResource(R.drawable.play_button);
			SoundMixer.getInstance().stopAllSoundsInSoundmixer();
			
			//TESTING
			//SoundManager.stopAllSounds();
			displayPlayButton = true;
		  }
		}
	}	


	public Boolean getDisplayPlayButton() {
		return displayPlayButton;
	}

}

