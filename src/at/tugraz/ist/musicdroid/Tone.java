package at.tugraz.ist.musicdroid;

import java.util.Dictionary;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

public class Tone extends View {
	private static int r = 8;
	private int x;
	private int y;
	private int midiVal;
	private boolean cross = false;
	private int helpline = 0;
	private Paint paint;
	private int y_line;

	public Tone(Context context, int midiVal, int x, int y, Paint paint) {
		// bekomme y = oberste Notenzeile
		super(context);
		this.midiVal = midiVal;
		this.x = x;
		this.y = y;
		this.y_line = y;
		calculateCoordinates();
		this.paint = paint;

	}

	private void calculateCoordinates() {
		// g2=43 x+r, y-r
		int octave = midiVal / 12 - 1;
		int value = midiVal % 12; // 0=C

		int distance[] = { 3, 3, 2, 2, 1, 0, 0, 6, 6, 5, 5, 4 };
		int help_value = distance[value];
		cross = true;

		y += help_value * 8;
		int oct;

		if (midiVal >= 43) { // nach oben

			if (value > 6)
				oct = 1;
			else
				oct = 2;
			y -= 7 * (octave - oct) * 8;
			helpline = (int) ((octave - 2) * 3.5);

		} else if (midiVal < 31) {

			if (value > 6)
				oct = 1;
			else
				oct = 2;

			helpline = (int) ((octave - 1) * 3.5);
			y += 7 * (oct - octave) * 8;
		}

	}

	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		RectF oval = new RectF(x - r - 2, y - r, x + r + 2, y + r);
		canvas.drawOval(oval, paint);
		if (this.midiVal < 36)
			canvas.drawLine(x + r + 2, y, x + r + 2, y - 20, paint);// nach
																	// unten
		// klappen
		else {
			canvas.drawLine(x - r - 2, y, x - r - 2, y + 20, paint);
		}

		if (helpline > 0) { // Linien nach oben
			for (int i = 0; i < helpline; i++) {
				canvas.drawLine(x - 15, (y_line) - 8 * 2 * i, x + 15, (y_line)
						- 8 * 2 * i, paint);// radius
			}
		}
		if (helpline < 0) {
			for (int i = 0; i < helpline * (-1); i++) {
				canvas.drawLine(x - 15, (y_line) + 8 * 2 * i + 3 * 8 * 2,
						x + 15, (y_line) + 8 * 2 * i + 3 * 8 * 2, paint);// radius
			}
		}
		invalidate();
	}
}
