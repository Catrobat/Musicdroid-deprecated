package at.tugraz.ist.musicdroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import at.tugraz.ist.musicdroid.common.Constants;
import at.tugraz.ist.musicdroid.common.DataManagement;
import at.tugraz.ist.musicdroid.common.Projekt;

public class MusicdroidActivity extends Activity implements OnClickListener {
	/** Called when the activity is first created. */

	private final int REQUEST_SELECT_MUSIC = 0;
	TextView my_list_view;
	String filename_;

	private Button OpenRecorderButton;
	private Button OpenPlayerButton;
	private Button OpenSoundfileButton;
	private Button NewProjectButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		OpenRecorderButton = (Button) findViewById(R.id.soundRecorderButton);
		OpenRecorderButton.setOnClickListener(this);
		OpenPlayerButton = (Button) findViewById(R.id.soundPlayerButton);
		OpenPlayerButton.setOnClickListener(this);
		NewProjectButton = (Button) findViewById(R.id.newProjectButton);
		NewProjectButton.setOnClickListener(this);
		OpenSoundfileButton = (Button) findViewById(R.id.openSoundfileButton);
		OpenSoundfileButton.setOnClickListener(this);
	}

	public void handleLoadFileButton(View v) {
		System.out.println("Handler!");
		LoadFile();

	}

	public void onPlaySound(View v) {
		startActivity(new Intent(this, PlaySoundActivity.class));
		/*
		 * TextView tv=new TextView(this); tv.setText("hugo");
		 * setContentView(tv);
		 */

	}

	public void LoadFile() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("audio/*");
		startActivityForResult(Intent.createChooser(intent,
				getString(R.string.load_sound_file_chooser_text)),
				REQUEST_SELECT_MUSIC);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == Activity.RESULT_OK && requestCode == 0) {
			DataManagement management = new DataManagement();
			management.LoadSoundFile(data);
		}
	}

	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if (arg0 == NewProjectButton) {
			// new project
		}
		if (arg0 == OpenSoundfileButton) {
			LoadFile();
		}
		if (arg0 == OpenPlayerButton) {
			// open player

			// pl.initFile("mnt/sdcard/bluetooth/test.midi");
			// String filename = Projekt.getInstance().getLastSoundFile();
			// System.out.println(Projekt.getInstance().getLastSoundFile());
			String filename = Projekt.getInstance().getLastSoundFile();
			Intent intent = new Intent(MusicdroidActivity.this,
					PlaySoundActivity.class);
			intent.putExtra("filename", filename);
			// PlaySoundActivity.getInstance().initFile(filename);
			// PlaySoundActivity.getInstanceCount().initFile(filename);
			// startActivity(new Intent(this, PlaySoundActivity.class));
			System.out.println("Vor StartActivity");
			startActivity(intent);
		}
		if (arg0 == OpenRecorderButton) {
			// open Recorder
		}
	}
}
