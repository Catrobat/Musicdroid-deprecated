 package at.tugraz.ist.musicdroid;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
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
import android.content.res.Resources;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class PitchDetectionActivity extends Activity {
	private final static String Appname = "Pitchdetection";

	private File dir;
	private PdService pdService = null;
	private String path;
	
	private int patchID = 0;
	private Handler myProgressHandler = new Handler();
	private String input_wav;
	private DrawTonesView toneView;
	
	ArrayList<Integer> values;
	
	private final ServiceConnection pdConnection = new ServiceConnection() {

    	public void onServiceConnected(ComponentName name, IBinder service) {
    		pdService = ((PdService.PdBinder)service).getService();
    		try {
    			initPd();
    			loadPatch();
    			doAnalyze();
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
    }
  }

  /* When we receive a symbol from Pd */
  public void receiveSymbol(String source, String symbol) {
    Log.i("receiveSymbol", symbol);

  }
  /* When we receive a float from Pd */
  public void receiveFloat(String source, float x) {
    Log.i("receiveFloat", ((Float)x).toString());
    
    if(x < 0)
    {
    	printResults();
    	return;
    }
    
    values.add( (int)x); 
    
  }
  /* When we receive a bang from Pd */
  public void receiveBang(String source) {
    Log.i("receiveBang", "bang!");
   
  }
};
    
	
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.pitchdetection);
       
        values = new ArrayList<Integer>();
        Bundle b = getIntent().getExtras();
        path = b.getString("path");
        
        
        File inputFile = new File(path);
    	if(!inputFile.exists())
    	{
    		Toast.makeText(this, "Sound-file not found!", Toast.LENGTH_LONG);
    		Log.e("Pitchdet","Sound-file not found!");
    		return;
    	} 
    	input_wav = inputFile.getAbsolutePath();
        
    	calculateWavLength();
    	
    	
    	Resources r = getResources();
		int radius = r.getInteger(R.integer.radius);
		int topline = r.getInteger(R.integer.topmarginlines);
		
		LinearLayout layout =  (LinearLayout)findViewById(R.id.parentLayout);
		
	  toneView = new DrawTonesView(this, R.drawable.violine, radius , topline); 
	  toneView.clearList();
	  toneView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,300));	        
	  layout.addView(toneView,0);
		  

        bindService(new Intent(this, PdService.class),pdConnection,BIND_AUTO_CREATE);       	
    }
    
    private void calculateWavLength()
    {
    	MediaPlayer mp = MediaPlayer.create(this, Uri.parse(input_wav));    	
    	int duration_msec = mp.getDuration();
    	int duration_sec = duration_msec / 1000;
    	mp.release();
    	
    	TextView t = (TextView)findViewById(R.id.progressText);
    	
    	DecimalFormat df =   new DecimalFormat  ( "00" );
    	
    	if(duration_sec < 60)
    		t.setText("00:" + df.format(duration_sec));
    	else
    	{
    		int temp = duration_sec;
    		int min = 0;
    		while(temp > 60)
    		{
    			min++;
    			temp = temp - 60;
    		}
    		t.setText(df.format(min) + ":" + df.format(temp));
    	}
    	
    	ProgressBar p = (ProgressBar)findViewById(R.id.progressBar);
    	p.setMax(duration_msec/100);
    	p.setProgress(0);       	
    }
    
    public void doAnalyze() {
    	
    	try {
    		
    	ProgressBar p = (ProgressBar)findViewById(R.id.progressBar);
    	p.setProgress(0); 
    	
    	toneView.clearList();
    	toneView.invalidate();
    	values.clear();
    	TextView t = (TextView)findViewById(R.id.outTextView);
	    t.setText("");
	    
	    Button b = (Button)findViewById(R.id.synthesizeButton);
	    b.setEnabled(false);
    	
	    File f = new File(input_wav);
	    
	    if(!f.exists())
	    {
	    	Toast.makeText(this, "Sound-file not found!", Toast.LENGTH_LONG);
    		Log.e("Pitchdet","Sound-file not found!");
    		return;
	    }
	    
    	int ret = PdBase.sendSymbol("input-wav", input_wav);
    	if(ret != 0)
    	  Log.i("PitchDet", "PdBase.sendSymbol() returns " + Integer.toString(ret));
    	
    	myProgressHandler.removeCallbacks(myHandlerTask);
    	myProgressHandler.postDelayed(myHandlerTask, 100);   	
    	
    	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("pitchdet", e.getMessage());
		}

    }
    
    private void printResults()
    {
    	myProgressHandler.removeCallbacks(myHandlerTask);
    	ProgressBar p = (ProgressBar)findViewById(R.id.progressBar);
    	p.setProgress(p.getMax());
    	
    	String out = "";
	    MidiTable midi = new MidiTable();
	    for(int i=0;i< values.size();i++)
	    {
	    	out += /*values.get(i).toString() +
	    			" -> " +*/
	    			(i+1) + ": \t" + 
	    			midi.midiToName(values.get(i))	+ 
	    			"\n";
	    }
	    
	    TextView t = (TextView)findViewById(R.id.outTextView);
	    t.setText(out);
	    
	    doDraw();
	    
	    if(values.size() > 0){
		    Button b = (Button)findViewById(R.id.synthesizeButton);
		    b.setEnabled(true);
	    }
    }
    
    
    private void writeMidiFile(int instrument)
    {
    	if(instrument <= 0) return;
    	
    	MidiFile mf = new MidiFile();  
    	
    	try
    	{
	    	mf.progChange(instrument);  //select instrument
	    	for(int i=0;i< values.size();i++)
		    {
	    		mf.noteOnOffNow(MidiFile.QUAVER, values.get(i), 127);
		    }
		    File f = new File(path);
		    String filename = f.getParentFile() + File.separator + "synt.mid";
		    mf.writeToFile(filename);
		    
		    f = new File(filename);
		    if(!f.exists()) throw new Exception("Midi file could not be created!");
		    
		    playfile(f.getAbsolutePath());
    	}
    	catch (Exception e) {
			Log.e("Midi", e.getMessage());
		}    	
    }
    
    public void onMidiClick(View view) {   

    	if(values.size() <= 0) return;
    	
    	registerForContextMenu(view); 
        openContextMenu(view);
        unregisterForContextMenu(view);     
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.layout.context_menu_instruments, menu);
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.instrument_piano:
            	writeMidiFile(1);
                return true;
            case R.id.instrument_guitar:
            	writeMidiFile(25);
                return true;
            case R.id.instrument_violin:
            	writeMidiFile(41);
                return true;
            case R.id.instrument_panflute:
            	writeMidiFile(76);
                return true;
            case R.id.instrument_accordion:
            	writeMidiFile(22);
                return true;
            case R.id.instrument_sax:
            	writeMidiFile(66);
                return true;
            case R.id.instrument_trumpet:
            	writeMidiFile(57);
                return true;
            case R.id.instrument_xylophone:
            	writeMidiFile(14);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
    
    public void playfile(String filename) {
    	
	    File f = new File(filename);
	    
	    if(!f.exists()) return;
	    
    	Uri myUri = Uri.fromFile(f);
    	MediaPlayer mediaPlayer = new MediaPlayer();
    	
    	try {
    		mediaPlayer.reset();
        	mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    		mediaPlayer.setDataSource(getApplicationContext(), myUri);
    		mediaPlayer.prepare();
    		mediaPlayer.start();
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    		Log.e("playfile()", e.getMessage());
    	} 
    	
    	
    }

    private void loadPatch() throws IOException {
		dir = getFilesDir();
		IoUtils.extractZipResource(getResources().openRawResource(R.raw.pitchdet),
				dir, true);
		File patchFile = new File(dir, "pitchdet.pd");
		patchID = PdBase.openPatch(patchFile.getAbsolutePath());	
    }
    
    
    
    private void initPd() throws IOException {
		String name = getResources().getString(R.string.app_name);	
		
		pdService.initAudio(-1, 0, -1, -1);
		pdService.startAudio();		
		
    	  PdBase.setReceiver(myDispatcher);
    	  myDispatcher.addListener("pitch-midi", myListener);
	}
    
    
    
    @Override
	public void onDestroy() {
    	PdBase.closePatch(patchID);
    	unbindService(pdConnection);
    	super.onDestroy();		
	}
    
    public void doDraw()
    {	
		toneView.clearList();
		
		for(int i=0;i< values.size();i++)
	    {
			toneView.addElement(values.get(i));
	    }   	
		
    }
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {	
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.layout.menu, menu);
        menu.getItem(1).setVisible(false);
        return true;
    }
    
    
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuAnalyze:     
            	doAnalyze();
                                break;
        }
        return true;
    }
    
    
    
    private Runnable myHandlerTask = new Runnable() {
    	   public void run() {
    		    ProgressBar p = (ProgressBar)findViewById(R.id.progressBar);
    	    	if(p.getProgress() < p.getMax() - 1)
    	    	{
    	    		p.incrementProgressBy(1);  
    	    		myProgressHandler.postDelayed(myHandlerTask, 100);
    	    	}
    	    	
    	   }

    	};
    	
    	
}

