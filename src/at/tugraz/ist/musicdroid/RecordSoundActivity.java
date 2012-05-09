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
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RecordSoundActivity extends Activity {
	private final static String Appname = "Record_Sound";
	private Button recordButton;
	private Button stopButton;
	private TextView testoutput;
	private PdService pdService = null;
	
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
        
        recordButton = (Button) findViewById(R.id.button1);
        testoutput = (TextView) findViewById(R.id.textView1);	
        recordButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				 testoutput.setText("I'm recording now");
				 recordSoundFile();  
		    }
		
        });
        stopButton = (Button) findViewById(R.id.button2);
        stopButton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        	  String status = "stop";
        	  testoutput.setText("Stopped Recording"); 
        	  PdBase.sendSymbol("status", status);	
        	}
        });
        
    }

    private void loadPatch() throws IOException {
    	testoutput = (TextView) findViewById(R.id.textView1);
		File dir = getFilesDir();
		IoUtils.extractZipResource(getResources().openRawResource(R.raw.recordtest),
				dir, true);
		File patchFile = new File(dir, "recordtest.pd");
		
		String path = patchFile.getAbsolutePath();
		testoutput.setText("test");
		
		
    	PdBase.openPatch(patchFile.getAbsolutePath());	
		
    }
    
    
    
    private void initPd() throws IOException {
		// Configure the audio glue
		
		int sampleRate = AudioParameters.suggestSampleRate();
		//PdAudio.initAudio(sampleRate, 1, 2, 8, true);
		pdService.initAudio(sampleRate, 1, 2, 10.0f);
		pdService.startAudio();
		
		//dispatcher = new PdUiDispatcher();
		//PdBase.setReceiver(dispatcher);
		//PdAudio.initAudio(sampleRate, 1, 2, 8, true);

		
		
		
	}
    
    public void recordSoundFile() {
      String filename= "firstrecord.wav";
      String status = "start";
      PdBase.sendSymbol("filename", filename);
      PdBase.sendSymbol("status", status);
      status = "stop";
      PdBase.sendSymbol("status", status);
    	//MediaRecorder recorder = new MediaRecorder();
      //recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
      //recorder.setOutputFormat(MediaRecorder.OutputFormat.);
      //ExtAudioRecorder extAudioRecorder = ExtAudioRecorder.getInstanse(false);
    }
    
    
    @Override
	public void onDestroy() {
		super.onDestroy();
		unbindService(pdConnection);
	}
    	
    	
}

