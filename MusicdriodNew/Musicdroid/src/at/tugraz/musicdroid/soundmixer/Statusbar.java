package at.tugraz.musicdroid.soundmixer;

import android.database.Observable;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import at.tugraz.musicdroid.MainActivity;
import at.tugraz.musicdroid.R;
import at.tugraz.musicdroid.animation.HighlightAnimation;

public class Statusbar extends Observable implements OnTouchListener {
	private ImageButton playButton;
	private ImageButton rewindButton;
	private ImageView metronomLightImageView;
	protected Boolean displayPlayButton;
	protected MainActivity mainActivity;

    public Statusbar(MainActivity mainActivity) {
	   this.mainActivity = mainActivity;
	   displayPlayButton = true;
	
	   playButton = (ImageButton) mainActivity.findViewById(R.id.btn_play);
	   playButton.setOnTouchListener(this);
	   
	   rewindButton = (ImageButton) mainActivity.findViewById(R.id.btn_rewind);
	   //((RelativeLayout.LayoutParams)playButton.getLayoutParams()).setMargins(Helper.getInstance().getScreenWidth()/3-10, 0,0,0);
	   ((RelativeLayout.LayoutParams)playButton.getLayoutParams()).addRule(RelativeLayout.CENTER_HORIZONTAL);
	   ((RelativeLayout.LayoutParams)rewindButton.getLayoutParams()).addRule(RelativeLayout.LEFT_OF, playButton.getId());
	   rewindButton.setOnTouchListener(this);
	   rewindButton.setVisibility(View.INVISIBLE);
	
	   
	   metronomLightImageView = (ImageView) mainActivity.findViewById(R.id.metronom_light);
	   metronomLightImageView.setColorFilter(R.color.abs__background_holo_dark);
	   //ADD UNDO AND REDO SUPPORT
    }
    
	@Override
	public boolean onTouch(View view, MotionEvent event) {
		switch (view.getId()) {
		case R.id.btn_play:
			onPlayTouch(event);
			return true;
		case R.id.btn_rewind:
			onRewindTouch(event);
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
			rewindButton.setVisibility(View.VISIBLE);
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
	
	private void onRewindTouch(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			SoundMixer.getInstance().rewind();
			rewindButton.setVisibility(View.INVISIBLE);
		}
	}


	public Boolean getDisplayPlayButton() {
		return displayPlayButton;
	}

}

