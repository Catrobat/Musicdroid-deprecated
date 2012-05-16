package at.tugraz.ist.musicdroid;

import at.tugraz.ist.musicdroid.R;
import at.tugraz.ist.musicdroid.common.SoundFile;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MusicdroidActivity extends Activity {
	/** Called when the activity is first created. */

	SoundFile sound_file;
	private final int REQUEST_SELECT_MUSIC = 0;
	TextView my_list_view;
	String filename_;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		//sound_file = new SoundFile();
		// Branch: open_soundfile!!!
		my_list_view = (TextView) findViewById(R.id.textView1);
	}

	public void handleLoadFileButton(View v){
			Log.v("musicdroid", "button geklickt!!");
			LoadFile();
			
			
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
		
		SoundFile.GetInstance().LoadFile(data);
		}
		
	}
}