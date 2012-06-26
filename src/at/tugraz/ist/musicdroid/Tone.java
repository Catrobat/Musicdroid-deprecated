package at.tugraz.ist.musicdroid;

import java.util.ArrayList;
import java.util.Dictionary;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;

public class Tone extends View {
	private Resources res = getResources();
	private int r;
	private int x;
	private int c_2_y;
	private ArrayList<Integer> y_list = new ArrayList();
	private ArrayList<Integer> midiVal = new ArrayList();
	private int help_value;
	private ArrayList<Boolean> cross = new ArrayList();
	private Paint paint_;
	private int y_line;
	private boolean move_;

	public Tone(Context context, ArrayList<Integer> midiVal, int x, int y,
			Paint paint, int radius) {
		// bekomme y = oberste Notenzeile

		super(context);
		r = radius;
		this.midiVal = midiVal;
		this.x = x;
		this.c_2_y = y + 4 * r;
		this.y_line = y;
		calculateCoordinates();
		this.paint_ = new Paint(paint);
		invalidate();
	}

	private void calculateCoordinates() {
		// g2=43 x+r, y-r
		for (int i = 0; i < midiVal.size(); i++) {
			int octave = midiVal.get(i) / 12 - 4;
			int value = midiVal.get(i) % 12; // 0=C

			int distance[] = { 6, 6, 5, 5, 4, 3, 3, 2, 2, 1, 1, 0 };

			help_value = distance[value];

			if (value == 1 || value == 3 || value == 6 || value == 8
					|| value == 10) {
				if (cross.size() > i)
					cross.set(i, true);
				else
					cross.add(true);
			} else {
				if (cross.size() > i)
					cross.set(i, false);
				else
					cross.add(false);
			}
			if (y_list.size() > i)
				y_list.set(i, (int) (c_2_y + help_value * r - (octave - 1) * 2
						* r * 3.5));
			else
				y_list.add((int) (c_2_y + help_value * r - (octave - 1) * 2 * r
						* 3.5));
		}
	}

	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		for (int i = 0; i < midiVal.size(); i++) {

			int y = y_list.get(i);

			RectF oval = new RectF(x - r - r / 4, y - r, x + r + r / 4, y + r);

			canvas.drawOval(oval, paint_);
			if (this.midiVal.get(i) < 72)
				canvas.drawLine(x + r + r / 4, y, x + r + r / 4, y - 3 * r,
						paint_);

			else {
				canvas.drawLine(x - r - r / 4, y, x - r - r / 4, y + 3 * r,
						paint_);
			}
			int helpline = y_line + 10 * r;
			while (helpline <= y) {
				canvas.drawLine(x - 2 * r, helpline, x + 2 * r, helpline,
						paint_);
				helpline += 2 * r;
			}
			helpline = y_line - 2 * r;
			while (helpline >= y) {
				canvas.drawLine(x - r * 2, helpline, x + r * 2, helpline,
						paint_);
				helpline -= 2 * r;
			}

			if (cross.get(i)) {
				canvas.drawLine(x - 3 * r, y - 2 * r, x - 3 * r, y + 2 * r - 1,
						paint_);
				canvas.drawLine(x - 2 * r, y - 2 * r, x - 2 * r, y + 2 * r - 1,
						paint_);

				canvas.drawLine(x - 4 * r, y, x - 3 / 2 * r, y - r, paint_);

				canvas.drawLine(x - 4 * r, y + r, x - 3 / 2 * r, y, paint_);
			}
		}
	}

	public int getX() {
		return x;
	}

	public void setX(int new_x) {
		x = new_x;
	}

	public ArrayList<Integer> getY() {
		return y_list;
	}

	public void setY(ArrayList<Integer> y_values) {
		y_list = y_values;
	}

	public void setMove() {
		move_ = !move_;
		if (move_)
			paint_.setColor(Color.RED);
		else
			paint_.setColor(Color.BLACK);

	}

	public boolean isMarked() {
		return move_;
	}

	public void moveMidiVal(boolean up) {
		if (move_) {

			for (int i = 0; i < midiVal.size(); i++) {
				int act_midi = midiVal.get(i);
				if (up)
					act_midi++;
				else
					act_midi--;
				midiVal.set(i, act_midi);
				calculateCoordinates();
			}
		}
		invalidate();

	}

}
