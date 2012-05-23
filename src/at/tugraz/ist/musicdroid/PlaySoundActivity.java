package at.tugraz.ist.musicdroid;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PlaySoundActivity extends Activity {

	public static MediaPlayer mp_;
	private short state_ = 0;// stopped
	private Button but_paus_res_;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.play_sound);
		// mp_ = MediaPlayer.create(this, R.raw.iris);
		but_paus_res_ = (Button)findViewById(R.id.pauseResumeButton);
		mp_ = new MediaPlayer();
		initFile();

	}

	public static void initFile() {

		FileInputStream fis;
		try {
			// TODO Pfad als Parameter setzen
			fis = new FileInputStream(new File(
					"mnt/sdcard/bluetooth/Iris.mp3"));

			FileDescriptor fileDescriptor = fis.getFD();

			mp_.setDataSource(fileDescriptor);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			mp_.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void onPlay(View v) {
		if (mp_ != null) {
			mp_.seekTo(0);
			mp_.start();
			state_ = 1;
			if (but_paus_res_ != null)
				but_paus_res_.setText(R.string.pause_text);

		}
	}

	public void on_pause_resume(View v) {
		if (mp_ != null) {
			if (but_paus_res_.getText().equals(getString(R.string.pause_text)) && mp_.isPlaying()) {
				if (state_ == 1) {
					mp_.pause();
					but_paus_res_.setText(R.string.resume_text);
					state_ = 2;
				}

			} else if (but_paus_res_.getText().equals(getString(R.string.resume_text))) {
				if (state_ == 2) {
					mp_.start();
					but_paus_res_.setText(R.string.pause_text);
					state_ = 1;
				}
			}
		}

	}

	public void onStop(View v) {

		if (mp_ != null) {
			if (state_ == 1 || state_ == 2) {

				if (state_ == 1)
					mp_.pause();

				state_ = 0;
				mp_.seekTo(0);
			}
			if (but_paus_res_!=null) {
				but_paus_res_.setText(R.string.pause_text);
			}
		}

	}

	public static MediaPlayer getMP() {
		return mp_;
	}

	@Override
	public void onDestroy() {
		mp_.release();
		super.onDestroy();
	}

}
