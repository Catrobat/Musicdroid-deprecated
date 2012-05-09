package at.tugraz.ist.musicdroid;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MusicdroidActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Intent i = new Intent(MusicdroidActivity.this, RecordSoundActivity.class);
        startActivity(i);
        
        // Seventh change!!! (Bianca ;-) )
        
    }
}

