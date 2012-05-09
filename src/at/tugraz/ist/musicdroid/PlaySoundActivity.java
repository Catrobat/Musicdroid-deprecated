package at.tugraz.ist.musicdroid;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PlaySoundActivity extends Activity {

	public MediaPlayer mp;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.playsound);
		mp = MediaPlayer.create(this, R.raw.iris);
	}

	public void onPlay(View v) {
		if (mp != null) {
			mp.start();
		}
	}

	public void on_pause_resume(View v) {

		if (mp != null) {
			Button but;
			but = (Button) v.findViewById(R.id.but_pause_resume);
			if (but.getText() == "Pause") {
				if (mp.isPlaying()) {
					mp.pause();
					but.setText("Resume");
				}
			} else {
				mp.start();
				but.setText("Pause");

			}
		}

	}

	public void onStop(View v) {
		if (mp != null) {
			if (mp.isPlaying()) {
				mp.pause();
				mp.seekTo(0);
				Button but;
				but = (Button) v.findViewById(R.id.but_pause_resume);
				but.setText("Pause");
			}
		}
	}

	@Override
	public void onDestroy() {
		mp.release();
		super.onDestroy();
	}

}
