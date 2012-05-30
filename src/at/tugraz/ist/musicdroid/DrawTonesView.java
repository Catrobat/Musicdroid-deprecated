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
	private List tones = new ArrayList<Tone>();

	public DrawTonesView(Context context) {
		super(context);
		this.paint = new Paint();
		this.setBackgroundColor(Color.WHITE);
		// TODO Auto-generated constructor stub

		for (int i = 0; i < 20; i++) {
			addElement(10 + i);
		}

		invalidate();
	}

	@Override
	public void onDraw(Canvas canvas) {

		this.paint.setColor(Color.BLACK);
		paint.setStyle(Paint.Style.FILL);
		paint.setAntiAlias(true);
		drawLines(canvas);

		for (int i = 0; i < tones.size(); i++) {
			((Tone) (tones.get(i))).draw(canvas);
		}

	}

	private void drawLines(Canvas canvas) {

		for (int i = 0; i < 5; i++) {
			canvas.drawLine(this.getLeft(), 120 + i * 8 * 2, this.getRight(),
					120 + i * 8 * 2, paint);
		}
		drawViolinschluessel();

	}

	public void addElement(int midi) {
		int i = tones.size();
		int x = 0;
		if (i > 0)
			x = ((Tone) (tones.get(i - 1))).getX() + 60;
		else
			x = 40;

		tones.add(new Tone(super.getContext(), midi, x, 120, paint));
	}

	public void deleteElement(int i) {
		if (i > tones.size()) {
			return;
		}

		tones.remove(i);
		for (int j = i; j < tones.size(); j++) {
			int x_value = ((Tone) (tones.get(j))).getX();
			((Tone) (tones.get(j))).setX(x_value - 40);
		}
	}

	public void clearList() {
		if (!tones.isEmpty()) {
			tones.clear();
		}

	}
	
	private void drawViolinschluessel(Canvas canvas) {

		
		

	}

}
