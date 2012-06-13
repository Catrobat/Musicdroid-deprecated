package at.tugraz.ist.musicdroid;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Gallery;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

public class DrawTonesActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.draw_tones);

		Resources r = getResources();
		int radius = r.getInteger(R.integer.radius);
		int topline = r.getInteger(R.integer.topmarginlines);

		DrawTonesView toneView = new DrawTonesView(this, R.drawable.violine,
				radius, topline);
		setContentView(toneView);

	}

}