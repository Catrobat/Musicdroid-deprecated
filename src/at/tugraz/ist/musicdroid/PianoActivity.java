package at.tugraz.ist.musicdroid;

import java.lang.Object;
import java.util.*;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
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
	
	public void onCreate(Bundle savedInstanceState) {
 
        super.onCreate(savedInstanceState);
        setContentView(R.layout.piano);
        init();
        piano.setOnTouchListener(new OnTouchListener()    {

            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                // TODO Auto-generated method stub
            	String pos = String.valueOf(event.getX()) + "x" + String.valueOf(event.getY());
            	Log.e("position", pos);
                int x = piano.getWidth(); //- Ti.Platform.displayCaps.platformWidth/2;
                int key_width = (x/35); 
                int c = key_width*15-key_width/2;
            	
                String hit = (c - key_width/2) + "x" + (c + key_width/2);
                Log.e("c-pos", hit);
                
                String mapped_key = mapper.getWhiteKeyFromPosition(round(event.getX()));
                Log.e("mappedkey", mapped_key);
                
                
                //TODO instead of toast -> play midi; change NoteMapper
                if(mapped_key.equals("c") || mapped_key.equals("c1")) 
                {
                	Context context = getApplicationContext();
    	    		int duration = Toast.LENGTH_SHORT;
    	    		Toast toast = Toast.makeText(context, "you hit a C", duration);
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
        int x = scroll.getWidth()/2; //- Ti.Platform.displayCaps.platformWidth/2; 
        int y = scroll.getHeight()/2; // - Ti.Platform.displayCaps.platformHeight/2;
        //subtract platform width/height so that the center is centered and not in the corner
        mapper = new NoteMapper();
        
        scroll.postDelayed(new Runnable() {
            public void run() {
                int x = piano.getWidth(); //- Ti.Platform.displayCaps.platformWidth/2;
                int key_width = (x/35); 
                int position = key_width*10;//+key_width/2; 
                String pos = ""+x; 
                Log.e("position", pos); 
                //int y = piano.getHeight()/2; // - Ti.Platform.displayCaps.platformHeight/2;
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

}
