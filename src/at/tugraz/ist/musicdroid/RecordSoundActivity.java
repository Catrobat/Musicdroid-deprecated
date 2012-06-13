package at.tugraz.ist.musicdroid;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import org.puredata.android.service.PdService;
import org.puredata.core.PdBase;
import org.puredata.core.utils.IoUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.SystemClock;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class RecordSoundActivity extends Activity {
	private final static String Appname = "Record_Sound";
	private Button recordButton;
	private Button stopButton;
	private Button playButton;
	private TextView testoutput;
	private Chronometer chrono;
	private EditText editText;
	private File dir;
	private PdService pdService = null;
	private String path;
	private File patch;
	private File directory;
	private File newFile;
	private ImageView recordlight;

	private final ServiceConnection pdConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			pdService = ((PdService.PdBinder) service).getService();
			try {
				initPd();
				loadPatch();
			} catch (IOException e) {
				Log.e(Appname, e.toString());
				finish();
			}
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {

		}
	};

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		directory = new File(Environment.getExternalStorageDirectory()
				+ File.separator + "records");
		directory.mkdirs();
		newFile = new File(directory, "test.wav");

		bindService(new Intent(this, PdService.class), pdConnection,
				BIND_AUTO_CREATE);

		setContentView(R.layout.record);

		recordButton = (Button) findViewById(R.id.button2);
		stopButton = (Button) findViewById(R.id.stopButton);
		playButton = (Button) findViewById(R.id.playButton);
		stopButton.setBackgroundResource(R.drawable.stopdisabled);
		stopButton.setEnabled(false);
		playButton.setBackgroundResource(R.drawable.playdisabled);
		playButton.setEnabled(false); // todo
		recordlight = (ImageView) findViewById(R.id.recordlight);
		recordlight.setImageResource(R.drawable.recordlighton);
		// testoutput = (TextView) findViewById(R.id.textView1);
		chrono = (Chronometer) findViewById(R.id.chronometer1);

		recordButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				chrono.setBase(SystemClock.elapsedRealtime());
				chrono.start();
				stopButton.setEnabled(true);
				stopButton.setBackgroundResource(R.drawable.stop);
				playButton.setEnabled(true);
				playButton.setBackgroundResource(R.drawable.play);
				recordSoundFile();
			}

		});

		stopButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				String status = "stop";

				chrono.stop();
				recordlight.setImageResource(R.drawable.recordlightoff);
				PdBase.sendSymbol("status", status);
				File file = new File(dir, "firstrecord.wav");
				patch = file;
				getRecordName(file);

			}
		});

		playButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				playfile();
			}
		});

	}

	public void getRecordName(File file) {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Please enter a filename.");
		final EditText input = new EditText(this);
		alert.setView(input);
		String neu = "";

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			String value = "";

			@Override
			public void onClick(DialogInterface dialog, int which) {
				value = input.getText().toString();

				newFile = new File(directory, value + ".wav");

				try {
					copyFile(patch, newFile);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});

		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

					}
				});

		alert.show();

	}

	private void loadPatch() throws IOException {
		Log.e("test", "test");
		dir = getFilesDir();
		IoUtils.extractZipResource(
				getResources().openRawResource(R.raw.recordtest), dir, true);
		File patchFile = new File(dir, "recordtest.pd");
		path = patchFile.getAbsolutePath();
		PdBase.openPatch(patchFile.getAbsolutePath());

	}

	private void initPd() throws IOException {
		String name = getResources().getString(R.string.app_name);
		pdService.initAudio(-1, -1, -1, -1);
		pdService
				.startAudio(new Intent(this, RecordSoundActivity.class),
						R.drawable.musicdroid_launcher, name, "Retrun to "
								+ name + ".");

	}

	public void recordSoundFile() {
		String filename = "firstrecord.wav";
		String status = "start";
		PdBase.sendSymbol("filename", filename);
		PdBase.sendSymbol("status", status);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		unbindService(pdConnection);
	}

	public void playfile() {
		Uri myUri = Uri.fromFile(patch);
		MediaPlayer mediaPlayer = new MediaPlayer();
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		try {
			mediaPlayer.setDataSource(getApplicationContext(), myUri);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
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
			mediaPlayer.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mediaPlayer.start();
	}

	public static void copyFile(File src, File dest) throws IOException {
		Log.e("Copy File:", "Copy File");
		FileChannel inChannel = new FileInputStream(src).getChannel();
		FileChannel outChannel = new FileOutputStream(dest).getChannel();
		try {
			inChannel.transferTo(0, inChannel.size(), outChannel);
		} finally {
			if (inChannel != null)
				inChannel.close();
			if (outChannel != null)
				outChannel.close();
		}
	}
}
