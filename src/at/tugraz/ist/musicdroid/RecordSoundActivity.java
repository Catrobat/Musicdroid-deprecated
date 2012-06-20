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
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import at.tugraz.ist.musicdroid.common.Constants;
import at.tugraz.ist.musicdroid.common.DataManagement;
import at.tugraz.ist.musicdroid.common.Projekt;

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
	private AlertDialog.Builder builder;
	private AlertDialog alert;
	boolean unsaved_changes = false;
	boolean on_back_pressed = false;

	private final ServiceConnection pdConnection = new ServiceConnection() {
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

		public void onServiceDisconnected(ComponentName name) {

		}
	};

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		bindService(new Intent(this, PdService.class), pdConnection,
				BIND_AUTO_CREATE);
		setContentView(R.layout.record);
		initGui();
		guiHandler();

	}

	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		setContentView(R.layout.record);
		guiHandler();
	}

	public void initGui() {
		builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.save_dialog)
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								SaveRecord();
							}
						})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						setDefaultButtonStatus();
					}
				});

		alert = builder.create();

		recordButton = (Button) findViewById(R.id.button2);
		stopButton = (Button) findViewById(R.id.stopButton);
		playButton = (Button) findViewById(R.id.playButton);
		chrono = (Chronometer) findViewById(R.id.chronometer1);

		setDefaultButtonStatus();
	}

	private void guiHandler() {
		recordButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {

				String file = android.os.Environment
						.getExternalStorageDirectory().getPath()
						+ "/records/test.wav";
				File newFile = new File(file);
				if (unsaved_changes) {
					alert.show();
				} else {
					chrono.setBase(SystemClock.elapsedRealtime());
					chrono.start();
					stopButton.setEnabled(true);
					stopButton.setBackgroundResource(R.drawable.stop);
					playButton.setEnabled(true);
					playButton.setBackgroundResource(R.drawable.play);
					recordButton.setEnabled(false);
					recordButton.setBackgroundResource(R.drawable.recdisabled);
					recordlight = (ImageView) findViewById(R.id.recordlight);
					recordlight.setImageResource(R.drawable.recordlighton);
					recordSoundFile();
				}
			}

		});

		stopButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				String status = "stop";

				chrono.stop();
				recordlight.setImageResource(R.drawable.recordlightoff);
				recordButton.setEnabled(true);
				recordButton.setBackgroundResource(R.drawable.rec);
				PdBase.sendSymbol("status", status);
				unsaved_changes = true;
				File file = new File(dir, "firstrecord.wav");
				patch = file;
			}
		});

		playButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				if (!recordButton.isEnabled()) {
					Context context = getApplicationContext();
					int duration = Toast.LENGTH_SHORT;
					Toast toast = Toast.makeText(context,
							R.string.record_toast, duration);
					toast.show();
				} else {
					playfile();
				}
			}
		});

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
		Intent intent = new Intent(RecordSoundActivity.this,
				PlaySoundActivity.class);
		intent.putExtra("filename", patch.getAbsolutePath());
		startActivity(intent);

	}

	public void SaveRecord() {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Please enter a filename.");
		final EditText input = new EditText(this);
		alert.setView(input);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			String value = "";

			public void onClick(DialogInterface dialog, int which) {
				DataManagement management = new DataManagement();
				value = input.getText().toString();

				if (value != "") {
					newFile = new File(Constants.MAIN_DIRECTORY
							+ Constants.RECORDS_SUB_DIRECTORY, value + ".wav");

					try {
						management.checkDirectory(newFile.getAbsolutePath());
						management.copyFile(patch, newFile);
						patch.delete();
						Projekt.getInstance().addRecord(
								newFile.getAbsolutePath());
					} catch (IOException e) {
						e.printStackTrace();
					}

					playButton.setBackgroundResource(R.drawable.playdisabled);
					playButton.setEnabled(false);
					stopButton.setBackgroundResource(R.drawable.stopdisabled);
					stopButton.setEnabled(false);
					chrono.setBase(SystemClock.elapsedRealtime());
				}
			}

		});

		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {

					}
				});

		alert.show();
		unsaved_changes = false;
	}

	@Override
	public void onBackPressed() {

		if (unsaved_changes)
			alert.show();
		else
			super.onBackPressed();
	}

	void setDefaultButtonStatus() {
		chrono.setBase(SystemClock.elapsedRealtime());
		playButton.setBackgroundResource(R.drawable.playdisabled);
		playButton.setEnabled(false);
		stopButton.setBackgroundResource(R.drawable.stopdisabled);
		stopButton.setEnabled(false);
		unsaved_changes = false;
	}

}