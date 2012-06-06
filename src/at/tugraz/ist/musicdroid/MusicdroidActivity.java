package at.tugraz.ist.musicdroid;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MusicdroidActivity extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
    
    private Button OpenRecorderButton;
    private Button OpenPlayerButton;
    private Button OpenProjectButton;
    private Button NewProjectButton;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        OpenRecorderButton = (Button)findViewById(R.id.soundRecorderButton);
        OpenRecorderButton.setOnClickListener(this);
        OpenPlayerButton = (Button)findViewById(R.id.soundPlayerButton);
        OpenPlayerButton.setOnClickListener(this);
        OpenProjectButton = (Button)findViewById(R.id.openProjectButton);
        OpenProjectButton.setOnClickListener(this);
        NewProjectButton = (Button)findViewById(R.id.newProjectButton);
        NewProjectButton.setOnClickListener(this);
        
    }
    
    public void onPlaySound(View v){
    	startActivity(new Intent(this, PlaySoundActivity.class));
    	/*TextView tv=new TextView(this);
    	tv.setText("hugo");
    	setContentView(tv);
    	*/
    	
    }
    
    
    
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if (arg0 == NewProjectButton)
		{
		// new project
		}
		if (arg0 == OpenProjectButton)
		{
		// open project
		}
		if (arg0 == OpenPlayerButton)
		{
		// open player
			
			//pl.initFile("mnt/sdcard/bluetooth/test.midi");
			String filename="mnt/sdcard/bluetooth/Iris.mp3";
			Intent intent=new Intent(MusicdroidActivity.this,PlaySoundActivity.class);
			intent.putExtra("filename", filename);
			//PlaySoundActivity.getInstance().initFile(filename);
			//PlaySoundActivity.getInstanceCount().initFile(filename);
			//startActivity(new Intent(this, PlaySoundActivity.class));
			System.out.println("Vor StartActivity");
			startActivity(intent);
		}
		if (arg0 == OpenRecorderButton)
		{
		// open Recorder
		}
	}
}
