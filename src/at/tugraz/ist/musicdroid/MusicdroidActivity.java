package at.tugraz.ist.musicdroid;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;


public class MusicdroidActivity extends Activity implements OnClickListener {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// Branch: open_soundfile!!!

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.openSoundButton:
			
		}

	}
}