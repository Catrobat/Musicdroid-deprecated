package at.tugraz.ist.musicdroid;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class PlaySoundActivity extends Activity {

	public static MediaPlayer mp_;
	private short state_ = 0;// stopped
	private Button but_paus_res_;
	private Handler handler = new Handler();

	public String getFileNameForViewing(String filename_path) {
		String file = "";
		String[] splittArray = filename_path.split("/");
		file = splittArray[splittArray.length - 1];
		return file;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		System.out.println("On Create!");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.play_sound);

		TextView filltextview = new TextView(this);
		filltextview = (TextView) findViewById(R.id.name_of_soundfile);
		filltextview.setText(getFileNameForViewing(getIntent().getExtras()
				.getString("filename")));

		but_paus_res_ = (Button) findViewById(R.id.pauseResumeButton);
		mp_ = new MediaPlayer();

		mp_.setOnPreparedListener(new OnPreparedListener() {
			ProgressBar progressbar = (ProgressBar) findViewById(R.id.soundfile_timeline);

			public void onPrepared(final MediaPlayer mp) {
				progressbar.setMax(mp.getDuration());

				new Thread(new Runnable() {

					public void run() {
						while (mp != null
								&& mp.getCurrentPosition() < mp.getDuration()) {

							progressbar.setProgress(mp.getCurrentPosition());
							Message msg = new Message();
							int millis = mp.getCurrentPosition();

							msg.obj = millis / 1000;
							handler.sendMessage(msg);
							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}

						}
					}
				}).start();

			}
		});

		initFile(getIntent().getExtras().getString("filename"));
	}

	public static void initFile(String filename) {

		System.out.println("initFile");
		FileInputStream fis;
		try {
			// TODO Pfad als Parameter setzen
			fis = new FileInputStream(new File(filename));

			FileDescriptor fileDescriptor = fis.getFD();
			mp_.setDataSource(fileDescriptor);

			fis.close();
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
			if (state_ == 2) {
				mp_.start();
				state_ = 1;
			} else {
				if (!mp_.isPlaying()) {
					mp_.seekTo(0);
					mp_.start();
					state_ = 1;
				}
			}
		}
	}

	public void on_pause_resume(View v) {
		if (mp_ != null) {
			if (mp_.isPlaying()) {
				if (state_ == 1) {
					mp_.pause();
					state_ = 2;
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
		}
	}

	@Override
	public void onBackPressed() {
		if (mp_ != null) {

			if (mp_.isPlaying())
				mp_.stop();

			mp_.reset();
		}

		this.finish();
		super.onBackPressed();

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