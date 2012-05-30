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
	private int help_value;
	private boolean cross = false;
	private Paint paint;
	private int y_line;

	public Tone(Context context, int midiVal, int x, int y, Paint paint) {
		// bekomme y = oberste Notenzeile
		super(context);
		this.midiVal = midiVal;
		this.x = x;
		this.y = y + 4 * 8;
		this.y_line = y;
		calculateCoordinates();
		this.paint = paint;

	}

	private void calculateCoordinates() {
		// g2=43 x+r, y-r
		int octave = midiVal / 12 - 1;
		int value = midiVal % 12; // 0=C

		int distance[] = { 6, 6, 5, 5, 4, 3, 3, 2, 2, 1, 1, 0 };

		help_value = distance[value];
		
		if(value == 1 || value == 3 || value == 6 || value == 8 || value == 10)
			cross = true;

		y += help_value * 8 - (octave - 1) * 2 * 8 * 3.5;
	}

	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		//if (cross)
			//x += 20;
		RectF oval = new RectF(x - r - 2, y - r, x + r + 2, y + r);

		canvas.drawOval(oval, paint);
		if (this.midiVal < 36)
			canvas.drawLine(x + r + 2, y, x + r + 2, y - 20, paint);// nach
																	// unten
																	// klappen
		else {
			canvas.drawLine(x - r - 2, y, x - r - 2, y + 20, paint);
		}
		int helpline = y_line + 10 * 8;
		while (helpline <= y) {
			canvas.drawLine(x - 15, helpline, x + 15, helpline, paint);
			helpline += 2 * 8;
		}
		helpline = y_line - 2 * 8;
		while (helpline >= y) {
			canvas.drawLine(x - 15, helpline, x + 15, helpline, paint);
			helpline -= 2 * 8;
		}

		if (cross) {
			canvas.drawLine(x - 24, y - 15, x - 24, y + 15, paint);
			canvas.drawLine(x - 16, y - 15, x - 16, y + 15, paint);
			canvas.drawLine(x - 27, y - 5, x - 12, y - 6, paint);
			canvas.drawLine(x - 27, y + 5, x - 12, y + 4, paint);
		}
	}

	public int getX() {
		return x;
	}

	public void setX(int new_x) {
		x = new_x;
	}
}
