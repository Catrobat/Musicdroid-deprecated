package at.tugraz.musicdroid;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import at.tugraz.musicdroid.recorder.Recorder;

public class RecorderActivity extends FragmentActivity implements OnClickListener {
	private Recorder recorder = null;
	private ImageButton recordImageButton = null; 
	private TextView recordDurationTextView = null;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recorder);
		findViewById(R.id.microphone_record_button).setBackgroundColor(Color.RED);
		
		recordImageButton = (ImageButton) findViewById(R.id.microphone_record_button);
		recordImageButton.setOnClickListener(this);
		
		recordDurationTextView = (TextView) findViewById(R.id.microphone_duration_text);
		recordDurationTextView.setText("00:00");
		
		
		recorder = new Recorder("myv.mp3");
	}
	
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.recorder_menu, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.microphone_record_button)
		{
			Log.i("ONCLICK", "RECORD");
			recorder.record();
		}
	}


    
}
