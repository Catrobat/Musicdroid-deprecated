package at.tugraz.ist.musicdroid;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MusicdroidActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        // Seventh change!!! (Bianca ;-) )
        
    }
    
    public void onPlaySound(View v){
    	startActivity(new Intent(this, PlaySoundActivity.class));
    	/*TextView tv=new TextView(this);
    	tv.setText("hugo");
    	setContentView(tv);
    	*/
    	
    }
    
    
    
}