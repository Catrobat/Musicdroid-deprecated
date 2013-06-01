package at.tugraz.musicdroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import at.tugraz.musicdroid.dialog.AddSoundDialog;
import at.tugraz.musicdroid.dialog.SoundLenghtDialog;
import at.tugraz.musicdroid.helper.Helper;
import at.tugraz.musicdroid.preferences.PreferenceActivity;
import at.tugraz.musicdroid.soundmixer.ObservableHorizontalScrollView;
import at.tugraz.musicdroid.soundmixer.SoundMixer;
import at.tugraz.musicdroid.soundmixer.SoundMixerMenuCallback;
import at.tugraz.musicdroid.soundmixer.Statusbar;
import at.tugraz.musicdroid.soundmixer.timeline.TimelineMenuCallback;
import at.tugraz.musicdroid.soundtracks.SoundTrack;
import at.tugraz.musicdroid.soundtracks.SoundTrackMic;
import at.tugraz.musicdroid.soundtracks.SoundTrackView;
import at.tugraz.musicdroid.soundtracks.SoundTrackViewMenuCallback;

public class MainActivity extends MenuFileActivity { 
	protected Statusbar statusbar;
	protected SoundMixer mixer;
	protected SoundLenghtDialog settingsDialog = null;
    private TimelineMenuCallback callbackTimelineMenu;
	
    public Statusbar getStatusbar() {
		return statusbar;
	}
    
 
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.prepareFolderStructure();

        
        AddSoundDialog.init(this);
    			
    	Helper helper = Helper.getInstance();
    	helper.init(this);
    	
        setContentView(R.layout.activity_main);
        
        SoundMixer.getInstance().initSoundMixer(this, (ObservableHorizontalScrollView)findViewById(R.id.sound_mixer_view));
        
        initTopStatusBar();
        statusbar = new Statusbar(this);
        
        //TESTING 
	    SoundManager.getInstance();
	    SoundManager.initSounds(this);
	   // SoundManager.loadSounds(); 
    }
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		return true;
	}
    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.menu_item_quit:
			showSecurityQuestionBeforeExit();
			return true;
		case R.id.menu_item_preferences:
			Intent intent = new Intent(MainActivity.this, PreferenceActivity.class);
			MainActivity.this.startActivity(intent);
			return true;
		case android.R.id.home:
			showSecurityQuestionBeforeExit();
			return true;
		case R.id.btn_add:
			AddSoundDialog.getInstance().show();
			return true;
		case R.id.btn_settings:
			SoundMixerMenuCallback callbackSoundMixerMenu = new SoundMixerMenuCallback(this);
			startActionMode(callbackSoundMixerMenu);
			return true;
		default:
			//calls MenuFileActivitys onOptionItemSelect for all File-Related entries
			return super.onOptionsItemSelected(item);
		}
	}
	
	
	
	@Override
    public void onBackPressed() {
		showSecurityQuestionBeforeExit();
    }
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		  if (requestCode == 1) {

		     if(resultCode == Activity.RESULT_OK){
		    	 if(data.hasExtra("mic_filename"))
		    	 {
		           String result = data.getStringExtra("mic_filename");
		           Log.i("MainActivity", "Received String from Activity " + result);
		           SoundTrackMic stm = new SoundTrackMic(result);
		           addSoundTrack(new SoundTrackView(this, stm));
		    	 }
		     }
		     if (resultCode == Activity.RESULT_CANCELED) {    
		         //probably not needed
		     }
		  }
	}
	
	private void showSecurityQuestionBeforeExit() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.closing_security_question);
		builder.setCancelable(true);
		builder.setNegativeButton(R.string.yes,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						SoundMixer.getInstance().resetSoundMixer();
						finish();
					}
				});
		builder.setPositiveButton(R.string.no,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	
	public void startActionMode(int id, SoundTrack soundTrack)
	{
		SoundMixer.getInstance().setCallingParameters(id, soundTrack); 
        SoundTrackViewMenuCallback callbackSoundTrackViewMenu = new SoundTrackViewMenuCallback(this);
		startActionMode(callbackSoundTrackViewMenu);
	}
	
	public void startTimelineActionMode()
	{
		startActionMode(callbackTimelineMenu);
	}
	
    public void addSoundTrack(SoundTrackView track)
    {
    	SoundMixer.getInstance().addSoundTrackViewToSoundMixer(track);
    }
    
    public void setCallbackTimelineMenu(TimelineMenuCallback callback)
    {
    	callbackTimelineMenu = callback;
    }

    private void initTopStatusBar()
    {
		getActionBar().setCustomView(R.layout.status_bar);
		getActionBar().setDisplayShowHomeEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayShowCustomEnabled(true);
		getActionBar().setIcon(R.drawable.ic_launcher); 		
    }
    
    
    

}
