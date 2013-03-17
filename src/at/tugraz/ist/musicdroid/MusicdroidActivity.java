package at.tugraz.ist.musicdroid;

import java.io.File;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockFragmentActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import at.tugraz.ist.musicdroid.common.DataManagement;
import at.tugraz.ist.musicdroid.common.Projekt;

@SuppressWarnings("unused")
public class MusicdroidActivity extends SherlockFragmentActivity implements OnClickListener {
	/** Called when the activity is first created. */


	private final int REQUEST_SELECT_MUSIC = 0;
	TextView my_list_view;
	String filename_;

	private Button OpenRecorderButton;
	private Button OpenPlayerButton;
	private Button OpenSoundfileButton;
	private Button OpenCatroidButton;
	private Button OpenCatroidForumButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		OpenRecorderButton = (Button) findViewById(R.id.soundRecorderButton);
		OpenRecorderButton.setOnClickListener(this);
		OpenPlayerButton = (Button) findViewById(R.id.soundPlayerButton);
		OpenPlayerButton.setOnClickListener(this);
		OpenSoundfileButton = (Button) findViewById(R.id.openSoundfileButton);
		OpenSoundfileButton.setOnClickListener(this);
		OpenCatroidButton = (Button)findViewById(R.id.openCatroidButton);
		OpenCatroidButton.setOnClickListener(this);
		OpenCatroidForumButton = (Button) findViewById(R.id.openForumButton);
		OpenCatroidForumButton.setOnClickListener(this);
	}

	public void handleLoadFileButton(View v) {
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
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == Activity.RESULT_OK && requestCode == 0) {
			DataManagement management = new DataManagement();
			management.LoadSoundFile(data);
		}
	}


	public void onClick(View arg0) {
	
		if (arg0 == OpenSoundfileButton) {
			LoadFile();
		}
		
		if(arg0==OpenPlayerButton)
		{
			String filename = Projekt.getInstance().getLastSoundFile();
			//System.out.println(filename);

			if (new File(filename).exists()) {
				Intent intent = new Intent(MusicdroidActivity.this,
						PlaySoundActivity.class);
				intent.putExtra("filename", filename);
				startActivity(intent);
				
			}
		}
		
		if (arg0 == OpenRecorderButton) {
			Intent i = new Intent(MusicdroidActivity.this,
			MusicdroidSubMenu.class);
	        startActivity(i);			
		}
		
		if (arg0 == OpenCatroidButton){
			Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getText(R.string.catroid_website).toString()));
			startActivity(browserIntent);
		}
		
		if (arg0 == OpenCatroidForumButton){
			Intent browerIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getText(R.string.catrobat_forum).toString()));
			startActivity(browerIntent);
		}
	}
}
