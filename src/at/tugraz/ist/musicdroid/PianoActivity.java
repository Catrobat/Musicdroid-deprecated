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
                if(mapped_key == 24) 
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

}
