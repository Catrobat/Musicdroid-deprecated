package at.tugraz.ist.musicdroid;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.String;
import java.nio.channels.FileChannel;

import org.puredata.android.io.AudioParameters;
import org.puredata.android.service.PdService;
import org.puredata.android.utils.PdUiDispatcher;
import org.puredata.core.PdBase;
import org.puredata.core.PdListener;
import org.puredata.core.utils.IoUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
	private File patch;
	private File directory;
	private File newFile;
	private ImageView recordlight;
	private AlertDialog.Builder builder;
	private AlertDialog alert;
	
	
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
    	bindService(new Intent(this, PdService.class),pdConnection,BIND_AUTO_CREATE);
        setContentView(R.layout.record);
    	 initGui();
    	 guiHandler();
       
    }
    
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.record);
        guiHandler();
     //   initGui();
    }
    
    public void initGui() {
    	builder = new AlertDialog.Builder(this);
		 builder.setMessage(R.string.overwrite_dialog)
		        .setCancelable(false)
		        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int id) {
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
		        })
		        .setNegativeButton("No", new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int id) {
		            	dialog.cancel();
		            }
		        });
		 
		alert = builder.create();
       
        directory = new File(Environment.getExternalStorageDirectory()+File.separator+"records");
 	    directory.mkdirs();
 	    newFile = new File(directory, "test.wav");

       
       
       recordButton = (Button) findViewById(R.id.button2);
       stopButton = (Button) findViewById(R.id.stopButton);
       playButton = (Button) findViewById(R.id.playButton);
       stopButton.setBackgroundResource(R.drawable.stopdisabled);
       stopButton.setEnabled(false);
       playButton.setBackgroundResource(R.drawable.playdisabled);
       playButton.setEnabled(false); //todo  

     //  testoutput = (TextView) findViewById(R.id.textView1);	
       chrono = (Chronometer) findViewById(R.id.chronometer1);
          	
    }
    
    private void guiHandler()
    {
    	recordButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				
			     String file = android.os.Environment.getExternalStorageDirectory().getPath() + "/records/test.wav";
				 File newFile = new File(file);
				 if(newFile.exists())
				 {
					 alert.show();
				 }
				 else
				 {
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
       	  File file = new File(dir, "firstrecord.wav");
       	  patch = file;
       	  try {
				copyFile(patch, newFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
       	  
       	  
       	}
       });
       
       playButton.setOnClickListener(new View.OnClickListener() {
       	public void onClick(View view) {
       	  if(!recordButton.isEnabled())
       	  {
	    		Context context = getApplicationContext();
	    		int duration = Toast.LENGTH_SHORT;
	    		Toast toast = Toast.makeText(context, R.string.record_toast, duration);
	    		toast.show();  
       	  }
       	  else
       	  {
       	    playfile();
       	  }
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
    	Uri myUri = Uri.fromFile(patch);
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
    	
    
    public static void copyFile(File src, File dest) throws IOException
    {
        Log.e("Copy File:", "Copy File");
    	FileChannel inChannel = new FileInputStream(src).getChannel();
        FileChannel outChannel = new FileOutputStream(dest).getChannel();
        try
        {
            inChannel.transferTo(0, inChannel.size(), outChannel);
        }
        finally
        {
            if (inChannel != null)
                inChannel.close();
            if (outChannel != null)
                outChannel.close();
        }
    }
}

