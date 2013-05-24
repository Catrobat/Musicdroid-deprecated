package at.tugraz.musicdroid;

/* Looks like Sherlock-Functionality is provided natively in Android 4.0
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem; */
import at.tugraz.musicdroid.dialog.AddSoundDialog;
import at.tugraz.musicdroid.dialog.SoundLenghtDialog;
import at.tugraz.musicdroid.helper.Helper;
import at.tugraz.musicdroid.preferences.PreferenceActivity;
import at.tugraz.musicdroid.preferences.SettingsFragment;
import at.tugraz.musicdroid.soundtracks.*;

import android.os.Build;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class MainActivity extends MenuFileActivity { 
	private static final int ANDROID_VERSION_ICE_CREAM_SANDWICH = 14;
	protected Statusbar statusbar;
	protected SoundMixer mixer;
	protected SoundLenghtDialog settingsDialog = null;
	
    public Statusbar getStatusbar() {
		return statusbar;
	}
    
    private ActionMode.Callback callback;
    private ActionMode actionMode; 
 
 
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
		AddSoundDialog.init(this);
    	
    	Helper helper = Helper.getInstance();
    	helper.init(this);
    	
        setContentView(R.layout.activity_main);
        
        SoundMixer.getInstance().initSoundMixer(this, (RelativeLayout)findViewById(R.id.sound_mixer_view));
        
        initTopStatusBar();
        statusbar = new Statusbar(this);
        
        //TEST 
        callback = new ActionMode.Callback() {
        	 
            /** Invoked whenever the action mode is shown. This is invoked immediately after onCreateActionMode */
            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }
 
            /** Called when user exits action mode */
            @Override
            public void onDestroyActionMode(ActionMode mode) {
                SoundMixer.getInstance().enableUnselectedViews();
                actionMode = null;
            }
 
            /** This is called when the action mode is created. This is called by startActionMode() */
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                getMenuInflater().inflate(R.menu.sound_track_dialog_menu, menu);
                SoundMixer.getInstance().disableUnselectedViews();
                
                String name = null;
                if((name = SoundMixer.getInstance().getCallingTrack().getName()) != null)
                	mode.setTitle(name);
                else
                	mode.setTitle("Fix this");
                return true;
            }
 
            /** This is called when an item in the context menu is selected */
            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch(item.getItemId()){
                case R.id.soundtrack_context_edit:
                    Toast.makeText(getBaseContext(), "Editing of tracks not yet implemented ", Toast.LENGTH_LONG).show();
                    mode.finish();    // Automatically exists the action mode, when the user selects this action
                    break;
                case R.id.soundtrack_context_copy:
    				SoundMixer.getInstance().handleCopy();
    				mode.finish();
                    break;
                case R.id.soundtrack_context_delete:
    				SoundMixer.getInstance().deleteCallingTrack();
                    mode.finish();
                    break;
                }
                return false;
            }
        };

        
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
		case android.R.id.home:
			showSecurityQuestionBeforeExit();
			return true;
		case R.id.btn_add:
			AddSoundDialog.getInstance().show();
			return true;
		case R.id.btn_settings:
			Intent intent = new Intent(MainActivity.this, PreferenceActivity.class);
			MainActivity.this.startActivity(intent);
			return true;
		default:
			//calls MenuFileActivitys onOptionItemSelect for all File-Related entries
			return super.onOptionsItemSelected(item);
		}
	}
	
	
	private void showSecurityQuestionBeforeExit() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.closing_security_question);
		builder.setCancelable(true);
		builder.setPositiveButton(R.string.yes,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						finish();
					}
				});
		builder.setNegativeButton(R.string.no,
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
		startActionMode(callback);
	}
	
    public void addSoundTrack(SoundTrackView track)
    {
    	SoundMixer.getInstance().addSoundTrackViewToSoundMixer(track);
    }

    private void initTopStatusBar()
    {
		getActionBar().setCustomView(R.layout.status_bar);
		getActionBar().setDisplayShowHomeEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayShowCustomEnabled(true);
		getActionBar().setIcon(R.drawable.ic_launcher);
		
		if (Build.VERSION.SDK_INT < ANDROID_VERSION_ICE_CREAM_SANDWICH) {
			Bitmap bitmapActionBarBackground = Bitmap.createBitmap(1, 1,
					Config.ARGB_8888);
			bitmapActionBarBackground.eraseColor(getResources().getColor(
					R.color.custom_background_color));
			Drawable drawable = new BitmapDrawable(bitmapActionBarBackground);
			getActionBar().setBackgroundDrawable(drawable);
			getActionBar().setSplitBackgroundDrawable(drawable);
		}
		
    }
    

}
