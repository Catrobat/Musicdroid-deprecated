package at.tugraz.musicdroid.soundmixer;

import android.database.Observable;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.webkit.WebView.FindListener;
import android.widget.ImageButton;
import android.widget.Toast;
import at.tugraz.musicdroid.MainActivity;
import at.tugraz.musicdroid.R;
import at.tugraz.musicdroid.animation.HighlightAnimation;

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
			playButton.setImageResource(R.drawable.pause_button);
			displayPlayButton = false;
			
			if(!SoundMixer.getInstance().playAllSoundsInSoundmixer())
			{
				playButton.setImageResource(R.drawable.play_button);
				Toast.makeText(mainActivity.getApplicationContext(), R.string.toast_empty_soundmixer, Toast.LENGTH_LONG).show();
				
				HighlightAnimation.getInstance().highlightViewAnimation(mainActivity.findViewById(R.id.btn_add));
			}
		  }
		  else {
			playButton.setImageResource(R.drawable.play_button);
			SoundMixer.getInstance().stopAllSoundsInSoundmixer();
			
			displayPlayButton = true;
		  }
		}
	}	


	public Boolean getDisplayPlayButton() {
		return displayPlayButton;
	}

}

