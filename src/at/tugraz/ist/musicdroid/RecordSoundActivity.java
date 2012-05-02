package at.tugraz.ist.musicdroid;

import android.app.Activity;
import android.media.MediaRecorder;
import android.media.AudioRecord;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RecordSoundActivity extends Activity {
	private Button recordButton;
	private TextView testoutput;
	
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.record);
        
        recordButton = (Button) findViewById(R.id.button1);
        testoutput = (TextView) findViewById(R.id.textView1);	
        recordButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
		    	 testoutput.setText("I'm recording now");
				 recordSoundFile();  
		    }
        });
        //
       // getResources().)
    }

    public void recordSoundFile() {
      MediaRecorder recorder = new MediaRecorder();
      recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
      //recorder.setOutputFormat(MediaRecorder.OutputFormat.);
      ExtAudioRecorder extAudioRecorder = ExtAudioRecorder.getInstanse(false);
    }
    	
    	
}

