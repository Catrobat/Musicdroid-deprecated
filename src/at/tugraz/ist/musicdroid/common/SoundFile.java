package at.tugraz.ist.musicdroid.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import at.tugraz.ist.musicdroid.R;

public class SoundFile extends Activity {

	private Context context;

	public SoundFile(Context context) {
		this.context = context;
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

	
		// Branch: open_soundfile!!!
		
	}

	public void LoadFile() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("audio/*");
		startActivityForResult(Intent.createChooser(intent,
				getString(R.string.load_sound_file_chooser_text)),
				0);
	};

	public void SaveFile() {
	};
	

	public void AppendFile() {
	};

	public void AppendToFile() {
	};

	public void Split() {
	};

	public void Play() {
	};

	private String filename_;
	private Activity activity_;
	
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);/*

		if (resultCode == activity_.RESULT_OK
				&& requestCode == 0) {
			try {

				String tmp_filename = "";

				Uri sound_file_uri = data.getData();
				tmp_filename = sound_file_uri.getPath();

				filename_=tmp_filename;
				

			} catch (Exception e) {
				Log.v("musicdroid", "obkackt!!");
			}
		}*/

	}

}
