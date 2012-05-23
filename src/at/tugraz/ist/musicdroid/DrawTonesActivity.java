package at.tugraz.ist.musicdroid;

import android.app.Activity;
import android.graphics.Canvas;
import android.os.Bundle;

public class DrawTonesActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		// setContentView(R.layout.draw_tones);

		
		DrawTonesView toneView = new DrawTonesView(this);
		setContentView(toneView);

	}
}
