package at.tugraz.ist.musicdroid;

import java.io.File;


import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import at.tugraz.ist.musicdroid.common.DataManagement;
import at.tugraz.ist.musicdroid.common.Projekt;

public class MusicdroidSubMenu extends SherlockFragmentActivity implements OnClickListener {
	/** Called when the activity is first created. */

	private final int REQUEST_SELECT_MUSIC = 0;
	TextView my_list_view;
	String filename_;
	private Button OpenRecorderButton;
	private Button OpenPianoButton;
	private Button OpenNoteCreatorButton;
	private ActionBar actionBar;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.sub_menu);

		OpenRecorderButton = (Button) findViewById(R.id.openRecorderButton);
		OpenRecorderButton.setOnClickListener(this);
		OpenPianoButton = (Button) findViewById(R.id.openPianoButton);
		OpenPianoButton.setOnClickListener(this);
		OpenNoteCreatorButton = (Button) findViewById(R.id.openNoteCreator);
		OpenNoteCreatorButton.setOnClickListener(this);
		setUpActionBar();
		
	}

	public void handleLoadFileButton(View v) {
		//System.out.println("Handler!");
		//LoadFile();

	}

	/*public void onPlaySound(View v) {
		startActivity(new Intent(this, PlaySoundActivity.class));
		/*
		 * TextView tv=new TextView(this); tv.setText("hugo");
		 * setContentView(tv);
		 */

	//}

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
		
		if (arg0 == OpenRecorderButton) {
			Intent i = new Intent(MusicdroidSubMenu.this,
					RecordSoundActivity.class);
			startActivity(i);
		}
		if (arg0 == OpenPianoButton) {
			Intent i = new Intent(MusicdroidSubMenu.this,
					PianoActivity.class);
			startActivity(i);
		}
		if (arg0 == OpenNoteCreatorButton) {
			Intent i = new Intent(MusicdroidSubMenu.this,
					RecToFrequencyActivity.class);
			startActivity(i);
			
		}
		
		
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home: {
				Intent intent = new Intent(this, MusicdroidActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				return true;
			}
			/*case R.id.menu_add: {
				NewProjectDialog dialog = new NewProjectDialog();
				dialog.show(getSupportFragmentManager(), NewProjectDialog.DIALOG_FRAGMENT_TAG);
				return true;
			}*/
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void setUpActionBar() {
		String title = "Back";
		actionBar = getSupportActionBar();
		actionBar.setTitle(title);
		actionBar.setDisplayHomeAsUpEnabled(true);
	}
}
