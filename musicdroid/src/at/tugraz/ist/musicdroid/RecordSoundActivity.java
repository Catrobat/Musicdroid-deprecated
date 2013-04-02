package at.tugraz.ist.musicdroid;

import java.io.File;
import java.io.IOException;

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
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import at.tugraz.ist.musicdroid.common.Constants;
import at.tugraz.ist.musicdroid.common.DataManagement;
import at.tugraz.ist.musicdroid.common.Projekt;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockFragmentActivity;


public class RecordSoundActivity extends Activity {
	private final static String Appname = "Record_Sound";
	private Button recordButton;
	private Button stopButton;
	private Button playButton;
	
	private Chronometer chrono;

	private File dir;
	private PdService pdService = null;
	private File patch;
	private ImageView recordlight;
	private int patchID = 0;
	
	private AlertDialog.Builder builder;
	private AlertDialog alert;
	boolean unsaved_changes = false;
	boolean on_back_pressed = false;


	private final ServiceConnection pdConnection = new ServiceConnection() {

    	public void onServiceConnected(ComponentName name, IBinder service) {
    		pdService = ((PdService.PdBinder)service).getService();
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
		dir = getFilesDir();
		IoUtils.extractZipResource(
				getResources().openRawResource(R.raw.recordtest), dir, true);
		File patchFile = new File(dir, "recordtest.pd");	
		patchID = PdBase.openPatch(patchFile.getAbsolutePath());	
    }
    
    
    
    private void initPd() throws IOException {		
		pdService.initAudio(-1, 1, -1, -1);
		pdService.startAudio();
	}

    
    public void recordSoundFile() {
      String filename= "firstrecord.wav";
      String status = "start";
      PdBase.sendSymbol("filename", filename);
      PdBase.sendSymbol("status", status);
      recordlight.setImageResource(R.drawable.recordlighton);
    }

	@Override
	public void onDestroy() {
		PdBase.closePatch(patchID);
		unbindService(pdConnection);
		super.onDestroy();
	}

    
    
    
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
    	if(patch == null) menu.getItem(1).setEnabled(false);
        else menu.getItem(1).setEnabled(true);
    	return true;
    }
    	
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.layout.menu, menu);        
        
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuAnalyze: 
            	String path = "";
            	
            	if(patch != null)
            	{
            		path = patch.getAbsolutePath();
            	}
            	else  // else-Zweig zum testen
            	{
            		
            		path = "/mnt/sdcard/records/test.wav";
            		File f = new File(path);
            		
            		if(f.exists())
            		{
            			Log.i(Appname, "Analyzing test.wav file");
            		}
            		else
            			break;
            	}
            	Intent i = new Intent(RecordSoundActivity.this, PitchDetectionActivity.class);
            	i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);

            	Bundle b = new Bundle();	
            	
            	b.putString("path", path); 
            	i.putExtras(b);

                startActivity(i);
            	
                break;
            case R.id.menuSave:
            	SaveRecord();
            	break;
        }
        return true;
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
					File newFile = new File(Constants.MAIN_DIRECTORY
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
