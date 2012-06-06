package at.tugraz.ist.musicdroid;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
import android.view.View.OnClickListener;
import android.widget.Button;


public class RecToFrequencyActivity extends Activity implements OnClickListener {

	private static final int PRERECORD = 0;
	private static final int RECORD = 1;
	private static final int POSTRECORD = 2;
	private int state = 0;
	private PdService pdService = null;
	private final static String Appname = "rec_to_frequency";
	private String path;
	private File dir;
	private Button StopRecordButton;
    private Button StartRecordButton;
    private Button SaveFileButton;
    private Button NextNoteButton;
	private final ServiceConnection pdConnection = new ServiceConnection() {
    	@Override
    	public void onServiceConnected(ComponentName name, IBinder service) {
    		Log.i("onServiceConnected", "ServiceConnection");
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
            setContentView(R.layout.record_to_frequency);
            Log.i("test", "test");
            StopRecordButton = (Button)findViewById(R.id.stopRecordButton);
            StopRecordButton.setOnClickListener(this);
            StartRecordButton = (Button)findViewById(R.id.startRecordButton);
            StartRecordButton.setOnClickListener(this);
            SaveFileButton = (Button)findViewById(R.id.saveFileButton);
            SaveFileButton.setOnClickListener(this);
            NextNoteButton = (Button)findViewById(R.id.nextNoteButton);
            NextNoteButton.setOnClickListener(this);
			StartRecordButton.setVisibility(View.VISIBLE);
			StopRecordButton.setVisibility(View.GONE);
			NextNoteButton.setVisibility(View.GONE);
			SaveFileButton.setVisibility(View.GONE);
            bindService(new Intent(this, PdService.class),pdConnection,BIND_AUTO_CREATE);
            Log.i("bindService", "bindService");
        }
        
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
      }
    }

    /* When we receive a symbol from Pd */
    public void receiveSymbol(String source, String symbol) {
      Log.i("receiveSymbol", symbol);

    }
    /* When we receive a float from Pd */
    public void receiveFloat(String source, float x) {
    	if(state == RECORD)
    	{
    		Log.i("receiveFloat", ((Float)x).toString());
    		//values.add( ((Float)x).toString()); 
    	}
    }
    /* When we receive a bang from Pd */
    public void receiveBang(String source) {
      Log.i("receiveBang", "bang!");
     
    }
  };
        
        private void loadPatch() throws IOException {
        	Log.e("loadPatch", "loadPatch");
    		dir = getFilesDir();
    		IoUtils.extractZipResource(getResources().openRawResource(R.raw.fiddle),
    				dir, true);
    		File patchFile = new File(dir, "fiddle.pd");
    		path = patchFile.getAbsolutePath();		
    		PdBase.openPatch(patchFile.getAbsolutePath());	

        }
        
        private void initPd() throws IOException {
    		String name = getResources().getString(R.string.app_name);
    		Log.i("initPd", "initPd");
    		pdService.initAudio(-1, 1, -1, -1);
    		Log.i("initAudio", "initAudio");
    		pdService.startAudio(new Intent(this, RecToFrequencyActivity.class), 
    				             R.drawable.musicdroid_launcher, name, "Return to " 
    		                                                         + name + "."); 
    		Log.i("startAudio", "startAudio");		
    		/* here is where we bind the print statement catcher defined below */
      	  PdBase.setReceiver(myDispatcher);
      	  /* here we are adding the listener for various messages
      	     from Pd sent to "GUI", i.e., anything that goes into the object
      	     [s GUI] will send to the listener defined below */
      	  myDispatcher.addListener("micro", myListener);
    	}
        @Override
    	public void onDestroy() {
    		super.onDestroy();
    		unbindService(pdConnection);
    	}
        
        public void writeMidiFile(ArrayList<Integer> values) {   
    	    
        	MidiFile mf = new MidiFile();  
        	
        	if(values.size() <= 0) return;
        	try
        	{
    	    	mf.progChange(76);  //select instrument
    	    	for(int i=0;i< values.size();i++)
    		    {
    	    		mf.noteOnOffNow(MidiFile.QUAVER, values.get(i), 127);
    		    }
    		    File f = new File(path);
    		    String filename = f.getParentFile() + File.separator + "test.midi";
    		    mf.writeToFile(filename);
        	}
        	catch (Exception e) {
    			Log.e("Midi", e.getMessage());
    		}    	
        }
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if (arg0 == StartRecordButton)
			{
				if(state == PRERECORD)
				{
					// start record
					state = RECORD;
					StartRecordButton.setVisibility(View.GONE);
					StopRecordButton.setVisibility(View.VISIBLE);
					NextNoteButton.setVisibility(View.VISIBLE);
					SaveFileButton.setVisibility(View.GONE);
				}
				else if(state == POSTRECORD)
				{
					//verwerfen?
					
					//ja:
					//start record
					state = RECORD;
					StartRecordButton.setVisibility(View.GONE);
					StopRecordButton.setVisibility(View.VISIBLE);
					NextNoteButton.setVisibility(View.VISIBLE);
					SaveFileButton.setVisibility(View.GONE);
					
					//nein:nix tun
				}
				else
				{
					Log.e("StartRecord-Error", "StartRecordButton clicked in wrong state");
				}
			}
			if (arg0 == StopRecordButton)
			{
				if(state == RECORD)
				{
					// Stop record
					state = POSTRECORD;
					StartRecordButton.setVisibility(View.VISIBLE);
					StopRecordButton.setVisibility(View.GONE);
					NextNoteButton.setVisibility(View.GONE);
					SaveFileButton.setVisibility(View.VISIBLE);
				}
				else
				{
					Log.e("StopRecord-Error", "StopRecordButton clicked in wrong state");
				}
			}
			if (arg0 == SaveFileButton)
			{
				if(state == POSTRECORD)
				{
					// Stop record
					state = PRERECORD;
					StartRecordButton.setVisibility(View.VISIBLE);
					StopRecordButton.setVisibility(View.GONE);
					NextNoteButton.setVisibility(View.GONE);
					SaveFileButton.setVisibility(View.GONE);
				}
				else
				{
					Log.e("SaveFileButton-Error", "SaveFileButton clicked in wrong state");
				}
			}
			if (arg0 == NextNoteButton)
			{
				if(state == RECORD)
				{
					// Stop record
				}
				else
				{
					Log.e("NextNoteButton-Error", "NextNoteButton clicked in wrong state");
				}
			}
			
		}
}
