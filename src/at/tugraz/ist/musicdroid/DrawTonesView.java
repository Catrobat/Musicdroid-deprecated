package at.tugraz.ist.musicdroid;

import java.util.ArrayList;
import java.util.List;

import javax.crypto.spec.IvParameterSpec;

import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Movie;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

public class DrawTonesView extends View {
	private Paint paint;
	private List tones = new ArrayList<Tone>();
	private Context context;
	private int first_line_;
	private int id_;
	private int radius_;
	private int distance_between_notes_;
	

	public DrawTonesView(Context context, int id, int radius, int firstline) {
		super(context);
		radius_ = radius;
		this.context = context;
		first_line_= firstline;
		this.paint = new Paint();
		distance_between_notes_= radius_ * 6;
		this.setBackgroundColor(Color.WHITE);
		this.id_ = id;
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

		drawViolinschluessel(canvas);
		for (int i = 0; i < 5; i++) {
			canvas.drawLine(this.getLeft(), first_line_ + i * radius_ * 2, this.getRight(),
					first_line_ + i * radius_ * 2, paint);
		}
		

	}

	public void addElement(int midi) {
		int i = tones.size();
		int x = 0;
		if (i > 0)
			x = ((Tone) (tones.get(i - 1))).getX() + distance_between_notes_;
		else
			x = 11*radius_;

		tones.add(new Tone(super.getContext(), midi, x, first_line_, paint));
	}

	public void deleteElement(int i) {
		if (i > tones.size()) {
			return;
		}

		tones.remove(i);
		for (int j = i; j < tones.size(); j++) {
			int x_value = ((Tone) (tones.get(j))).getX();
			((Tone) (tones.get(j))).setX(x_value - distance_between_notes_);
		}
	}

	public void clearList() {
		if (!tones.isEmpty()) {
			tones.clear();
		}

	}
	
	private void drawViolinschluessel(Canvas canvas) {
		Resources res = context.getResources();
		Bitmap bm = BitmapFactory.decodeResource(res, id_);
		//canvas.drawBitmap(bm, 10, 120, null);
		Rect dst_rct = new Rect(10,first_line_ - 60, 7 * radius_ , first_line_ + 8 * radius_ + 60);
		canvas.drawBitmap(bm, null, dst_rct, null);
	}

}
