package at.tugraz.ist.musicdroid;

import java.io.File;
import java.io.IOException;
import java.lang.String;

import org.puredata.android.io.AudioParameters;
import org.puredata.android.service.PdService;
import org.puredata.android.utils.PdUiDispatcher;
import org.puredata.core.PdBase;
import org.puredata.core.PdListener;
import org.puredata.core.utils.IoUtils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;

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
	
	private final ServiceConnection pdConnection = new ServiceConnection() {
    	@Override
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
    
    
    @Override
    public void onServiceDisconnected(ComponentName name) {
    	
    	
        }
    };
    
	
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        bindService(new Intent(this, PdService.class),pdConnection,BIND_AUTO_CREATE);
    
        setContentView(R.layout.record);
        
        recordButton = (Button) findViewById(R.id.button2);
        stopButton = (Button) findViewById(R.id.stopButton);
        playButton = (Button) findViewById(R.id.playButton);
        stopButton.setEnabled(false);
        playButton.setEnabled(false); //todo  
        
        testoutput = (TextView) findViewById(R.id.textView1);	
        chrono = (Chronometer) findViewById(R.id.chronometer1);
        
        recordButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				 chrono.setBase(SystemClock.elapsedRealtime());
				 chrono.start();
				 stopButton.setEnabled(true);
				 playButton.setEnabled(true);
				 recordSoundFile();  
		    }
		
        });
        
        stopButton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        	  String status = "stop";
        	
        	  chrono.stop(); 
        	  PdBase.sendSymbol("status", status);	
        	  File file = new File(dir, "firstrecord.wav");
        	  dir = file;
        	}
        });
        
        playButton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        	  playfile(); 
        	}
        }); 
        
    }

    private void loadPatch() throws IOException {
    	Log.e("test", "test");
		dir = getFilesDir();
		IoUtils.extractZipResource(getResources().openRawResource(R.raw.recordtest),
				dir, true);
		File patchFile = new File(dir, "recordtest.pd");
		path = patchFile.getAbsolutePath();		
		PdBase.openPatch(patchFile.getAbsolutePath());	

    }
    
    
    
    private void initPd() throws IOException {
		String name = getResources().getString(R.string.app_name);
		pdService.initAudio(-1, -1, -1, -1);
		pdService.startAudio(new Intent(this, RecordSoundActivity.class), 
				             R.drawable.musicdroid_launcher, name, "Retrun to " 
		                                                          + name + ".");
				
		
	}
    
    public void recordSoundFile() {
      String filename= "firstrecord.wav";
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
    	Uri myUri = Uri.fromFile(dir);
    	MediaPlayer mediaPlayer = new MediaPlayer();
    	mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    	try {
    		mediaPlayer.setDataSource(getApplicationContext(), myUri);
    	} catch (IllegalArgumentException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} catch (SecurityException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} catch (IllegalStateException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} catch (IOException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	try {
    		mediaPlayer.prepare();
    	} catch (IllegalStateException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} catch (IOException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	mediaPlayer.start();	
    }
    	
    	
}

