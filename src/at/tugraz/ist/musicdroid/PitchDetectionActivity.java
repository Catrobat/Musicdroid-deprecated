package at.tugraz.ist.musicdroid;

import java.io.File;
import java.io.IOException;

import org.puredata.android.service.PdService;
import org.puredata.android.utils.PdUiDispatcher;
import org.puredata.core.PdBase;
import org.puredata.core.PdListener;
import org.puredata.core.utils.IoUtils;
import org.puredata.core.utils.PdDispatcher;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class PitchDetectionActivity extends Activity {
	private final static String Appname = "Pitchdetection";

	private File dir;
	private PdService pdService = null;
	private String path;
	
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
    
    /* We'll use this to catch print statements from Pd
    when the user has a [print] object */
 private final PdDispatcher myDispatcher = new PdUiDispatcher() {
   @Override
   public void print(String s) {
     Log.i("Pd print", s);
   }
 };
    

/* We'll use this to listen out for messages from Pd.
   Later we'll hook this up to a named receiver. */
private final PdListener myListener = new PdListener() {	
  public void receiveMessage(String source, String symbol, Object... args) {
    Log.i("receiveMessage symbol:", symbol);
    for (Object arg: args) {
      Log.i("receiveMessage atom:", arg.toString());
    }
  }

  /* What to do when we receive a list from Pd. In this example
     we're collecting the list from Pd and outputting each atom */
  public void receiveList(String source, Object... args) {
    for (Object arg: args) {
      Log.i("receiveList atom:", arg.toString());
      Toast.makeText(0, arg.toString(), Toast.LENGTH_SHORT);
    }
  }

  /* When we receive a symbol from Pd */
  public void receiveSymbol(String source, String symbol) {
    Log.i("receiveSymbol", symbol);
  }
  /* When we receive a float from Pd */
  public void receiveFloat(String source, float x) {
    Log.i("receiveFloat", ((Float)x).toString());
  }
  /* When we receive a bang from Pd */
  public void receiveBang(String source) {
    Log.i("receiveBang", "bang!");
  }
};
    
	
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        bindService(new Intent(this, PdService.class),pdConnection,BIND_AUTO_CREATE);
    
        setContentView(R.layout.pitchdetection);
        
        
       
        
    }
    
    public void onRunClick(View view) {
    	File inputFile = new File(dir, "Hammond.wav");
    	String input_wav = inputFile.getAbsolutePath();		
    	File f = new File(input_wav);
    	
    	if(!f.exists())
    	{
    		Log.i("pitchde","Sound-file not found!");
    	}
    	
    	PdBase.sendSymbol("input-wav", input_wav);	    	
    }

    private void loadPatch() throws IOException {
    	Log.e("Pitchdet", "test");
		dir = getFilesDir();
		IoUtils.extractZipResource(getResources().openRawResource(R.raw.pitchdet),
				dir, true);
		File patchFile = new File(dir, "pitchdet.pd");
		path = patchFile.getAbsolutePath();		
		PdBase.openPatch(patchFile.getAbsolutePath());	

    }
    
    
    
    private void initPd() throws IOException {
		/*String name = getResources().getString(R.string.app_name);
		pdService.initAudio(-1, -1, -1, -1);
		pdService.startAudio(new Intent(this, PitchDetectionActivity.class), 
				             R.drawable.musicdroid_launcher, name, "Return to " 
		                                                          + name + ".");*/
    	
    	/* here is where we bind the print statement catcher defined below */
    	  PdBase.setReceiver(myDispatcher);
    	  /* here we are adding the listener for various messages
    	     from Pd sent to "GUI", i.e., anything that goes into the object
    	     [s GUI] will send to the listener defined below */

    	  
    	  myDispatcher.addListener("pitch-midi", myListener);
    	  
				
		
	}
    
    
    
    @Override
	public void onDestroy() {
		super.onDestroy();
		unbindService(pdConnection);
	}
    	
    	
}

