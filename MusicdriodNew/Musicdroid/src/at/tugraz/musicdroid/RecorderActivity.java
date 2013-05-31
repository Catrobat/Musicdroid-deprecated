package at.tugraz.musicdroid;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import at.tugraz.musicdroid.recorder.AudioHandler;
import at.tugraz.musicdroid.recorder.RecorderLayout;

public class RecorderActivity extends FragmentActivity {
	private RecorderLayout layout = null;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recorder);

		layout = new RecorderLayout(this);
		AudioHandler.getInstance().init(this, layout);
		
	}
	
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.recorder_menu, menu);
		return true;
	}



    
}
