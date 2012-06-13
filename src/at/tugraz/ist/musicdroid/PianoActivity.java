package at.tugraz.ist.musicdroid;

import java.io.File;
import java.io.IOException;
import java.lang.Object;
import java.util.*;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.Toast;

public class PianoActivity extends Activity {
	private HorizontalScrollView scroll; 
	private ImageView piano; 
	private NoteMapper mapper;
	private SoundPlayer soundplayer;
	static final int SEMIQUAVER = 4;
	static final int QUAVER = 8;
	static final int CROTCHET = 16;
	static final int MINIM = 32;
	static final int SEMIBREVE = 64;
	private String ActivityName = "PianoActivity";
	private Context context;
	
	public void onCreate(Bundle savedInstanceState) {
 
        super.onCreate(savedInstanceState);
        setContentView(R.layout.piano);
        context = this.getApplicationContext();
        soundplayer = new SoundPlayer(context);
        soundplayer.initSoundpool();
        createMidiSounds();
        init();
        
        piano.setOnTouchListener(new OnTouchListener()    {

            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                // TODO Auto-generated method stub
            	String pos = String.valueOf(event.getX()) + "x" + String.valueOf(event.getY());
            	Log.e("position", pos);
                int x = piano.getWidth();
                int y = piano.getHeight();
                int key_width = (x/35); 
                int c = key_width*15-key_width/2;
            	
                //String hit = (c - key_width/2) + "x" + (c + key_width/2);
                //Log.e("c-pos", hit);
                int mapped_key = 0;
                
                if(event.getY() > 2*y/3)
                {	
                  mapped_key = mapper.getWhiteKeyFromPosition(round(event.getX()));
                  soundplayer.playSound(mapped_key);
                  Log.e("mappedkey", String.valueOf(mapped_key));                  
                } 
                //TODO: blackey map funktioniert noch nicht 
                /*
                else
                {
                  mapped_key = mapper.getBlackKeyFromPosition(round(event.getX()));
                  if(mapped_key >= 0)
                  {
                	Log.e("mappedkey", String.valueOf(mapped_key));
                  }
                  else 
                  {
                	Log.e("nokey", String.valueOf(mapped_key));
                  }
                } */
                
                
                //TODO instead of toast -> play midi :) 
                if(mapped_key == 48) 
                {
                	Context context = getApplicationContext();
    	    		int duration = Toast.LENGTH_SHORT;
    	    		Toast toast = Toast.makeText(context, "you hit a c1", duration);
    	    		toast.show();  	
                }
                return false;
            }
       }); 

	}
	
	private void init()
	{
		scroll = (HorizontalScrollView) findViewById(R.id.scrollView);
        piano = (ImageView) findViewById(R.id.piano); 
        int x = scroll.getWidth()/2; 
        int y = scroll.getHeight()/2;
        
        mapper = new NoteMapper();
        
        scroll.postDelayed(new Runnable() {
            public void run() {
                int x = piano.getWidth();
                int key_width = (x/35); 
                int position = key_width*10; 
                String pos = ""+x; 
                Log.e("position", pos); 
            	scroll.scrollTo(position,0);
            	mapper.initializeNoteMap(round(key_width));
            }
        }, 100L);

	}
	
	private int round(float val)
	{
	  val += 0.5;
	  return (int)val;
	}
	
	private String getMidiNote(int value){
		switch (value){
			case 0:
				return "C";
			case 1:
				return "Cis";
			case 2:
				return "D";
			case 3:
				return "Dis";
			case 4: 
				return "E";
			case 5:
			    return "F";
			case 6:
				return "Fis";
			case 7:
				return "G";
			case 8:
				return "Gis";
			case 9:
				return "A";
			case 10:
				return "Ais";
			case 11:
				return "H";
	
		}
		
		return "W";			
			
	}
	
	
	private boolean createMidiSounds(){
		boolean success= false;
		File directory;
		String midi_note;
		String file_name;
		String path;
		int midi_value = 0;
		int counter = 0;
		directory = new File(Environment.getExternalStorageDirectory()+File.separator+"records"+File.separator+"piano_midi_sounds");
 	    directory.mkdirs();
 	    counter = 0;
 	    for(midi_value = 36; midi_value < 96; midi_value++ ){
 	    	
 	    	midi_note = getMidiNote(counter);
 	    	MidiFile midiFile = new MidiFile();
 	   		midiFile.noteOn(0, midi_value, 127);
 	   		midiFile.noteOff(SEMIBREVE, midi_value);
 	   		file_name = midi_note + "_" + midi_value + ".mid";
 	   		path = directory.getAbsolutePath() + File.separator + file_name;
 	   		soundplayer.setSoundpool(path);
     		try {
	    			midiFile.writeToFile(path); 	
	    		} catch (IOException e) {
 	    			Log.e(ActivityName, e.toString());
 	    			finish();
 	    		}
 	    	
     		counter++;
     		if (counter == 12)
     			counter = 0;
     		
     		
 	    }
 	    return success;
	}

}
