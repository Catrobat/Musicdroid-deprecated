package at.tugraz.musicdroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import at.tugraz.musicdroid.dialog.AddSoundDialog;
import at.tugraz.musicdroid.preferences.PreferenceActivity;
import at.tugraz.musicdroid.recorder.AudioHandler;
import at.tugraz.musicdroid.recorder.RecorderLayout;
import at.tugraz.musicdroid.recorder.RecorderMenuCallback;
import at.tugraz.musicdroid.soundmixer.SoundMixerMenuCallback;
import at.tugraz.musicdroid.soundmixer.Statusbar;

public class RecorderActivity extends FragmentActivity {
	private RecorderLayout layout = null;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("RecorderActivitiy", "ONCREATE");

    	setContentView(R.layout.activity_recorder);
    	initTopStatusBar();
    	Statusbar.getInstance().initStatusbar(this);
    	Statusbar.getInstance().modifyStatusbarForRecorderActivity();
	}
	
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.recorder_menu, menu);
		return true;
	}
    
    @Override
    protected void onResume()
    {
    	super.onResume();
    	Log.i("RecorderActivity", "ON RESUME");

    	setContentView(R.layout.activity_recorder);
    	
		layout = new RecorderLayout();
		layout.init(this);
		
		AudioHandler.getInstance().init(this, layout);
    	AudioHandler.getInstance().setContext(this);
    }

    @Override
    protected void onPause()
    {
    	super.onPause();
    	layout.reset();
    	AudioHandler.getInstance().reset();
    	Log.i("RecorderActivity", "OnPause: "+ ((LinearLayout)findViewById(R.id.recorder_activity_layout)).getChildCount());
    }
    
    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		Log.i("RecorderActivity", "onOptionsItemSelected");
		switch (item.getItemId()) {
		case R.id.btn_settings:
			RecorderMenuCallback callbackSoundMixerMenu = new RecorderMenuCallback(this);
			startActionMode(callbackSoundMixerMenu);
			return true;
		}
		return false;
	}
	
    private void initTopStatusBar()
    {
		getActionBar().setCustomView(R.layout.status_bar);
		getActionBar().setDisplayShowHomeEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayShowCustomEnabled(true);
		getActionBar().setIcon(R.drawable.ic_launcher); 		
    }
	
    
    public void returnToMainActivtiy()
    {
    	 Intent returnIntent = new Intent();
    	 returnIntent.putExtra("mic_filename",AudioHandler.getInstance().getFilenameFullPath());
    	 setResult(RESULT_OK,returnIntent);
    	 finish();
    }


    
}
