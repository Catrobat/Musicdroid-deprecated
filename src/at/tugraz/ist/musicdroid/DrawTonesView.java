package at.tugraz.ist.musicdroid;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class DrawTonesView extends View {
	private Paint paint;

	public DrawTonesView(Context context) {
		super(context);
		this.paint = new Paint();
		this.setBackgroundColor(Color.WHITE);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onDraw(Canvas canvas) {

		this.paint.setColor(Color.BLACK);
		paint.setStyle(Paint.Style.FILL);
		paint.setAntiAlias(true);
		drawLines(canvas);
		
		List tones = new ArrayList<Tone>();
		
		
		for(int i = 0 ; i < 30; i++)
		{
			tones.add (new Tone(super.getContext(), 20+i, 20 + i* 40, 120, paint));
			((Tone)(tones.get(i))).draw(canvas);
		}
		
		invalidate();

	}

	private void drawLines(Canvas canvas) {

		for (int i = 0; i < 5; i++) {
			canvas.drawLine(this.getLeft(), 120 + i * 8 * 2, this.getRight(),
					120 + i * 8 * 2, paint);
		}

	}

}
