package at.tugraz.ist.musicdroid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.crypto.spec.IvParameterSpec;

import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGestureListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Movie;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ScrollView;

public class DrawTonesView extends View {
	private Paint paint;
	private List tones = new ArrayList<Tone>();
	private Context context;
	private int first_line_;
	private int id_;
	private int scrollx_ = 0;
	private int scrolldis_ = 200;
	private int radius_;
	static int t = 0;
	private int distance_between_notes_;
	double downx = 0, downy = 0, upx = 0, upy = 0;

	public OnClickListener onclick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			System.out.println("vielleicht so");
		}

	};

	public OnTouchListener touchlis = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			int action = event.getAction();

			switch (action) {
			case MotionEvent.ACTION_DOWN:
				downx = event.getX();
				downy = event.getY();
				break;
			case MotionEvent.ACTION_MOVE:
				// System.out.println(event.getX()+":"+event.getY());
				break;
			case MotionEvent.ACTION_UP:
				upx = event.getX();
				upy = event.getY();

				double scrolldist = downx - upx;

				if (scrolldist > 0) {
					scrollx_ += scrolldis_;
					if (scrollx_ > ((Tone) (tones.get(tones.size() - 1)))
							.getX() + 50 - v.getWidth())
						scrollx_ = ((Tone) (tones.get(tones.size() - 1)))
								.getX() + 50 - v.getWidth();
					v.scrollTo(scrollx_, 0);
				} else if (scrolldist < 0) {
					scrollx_ -= scrolldis_;
					if (scrollx_ < 0)
						scrollx_ = 0;
					v.scrollTo(scrollx_, 0);
				}
				invalidate();
				break;
			}

			return true;
		}

	};

	public DrawTonesView(Context context, int id, int radius, int firstline) {
		super(context);
		// super.setOnClickListener(onclick);
		super.setOnTouchListener(touchlis);
		radius_ = radius;
		this.context = context;
		first_line_ = firstline;
		this.paint = new Paint();
		distance_between_notes_ = radius_ * 6;
		this.setBackgroundColor(Color.WHITE);
		this.id_ = id;

		for (int i = 0; i < 20; i++) {
			addElement(60+i);
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

		int last_x = ((Tone) (tones.get(tones.size() - 1))).getX() + 50;

		if (last_x < this.getRight())
			last_x = this.getRight();

		drawViolinschluessel(canvas);
		for (int i = 0; i < 5; i++) {
			canvas.drawLine(this.getLeft(), first_line_ + i * radius_ * 2,
					last_x, first_line_ + i * radius_ * 2, paint);
		}

		Paint p_ = new Paint(paint);
		p_.setStrokeWidth(10);

		canvas.drawLine(last_x - 10, first_line_, last_x - 10, first_line_ + 8
				* radius_, paint);
		canvas.drawLine(last_x, first_line_ - 1, last_x, first_line_ + 8
				* radius_ + 1, p_);

		canvas.drawLine(1, first_line_ - 1, 1, first_line_ + 8 * radius_ + 1,
				p_);
		canvas.drawLine(10, first_line_, 10, first_line_ + 8 * radius_, paint);
	}

	public void addElement(ArrayList<Integer> midi) {
		int i = tones.size();
		int x = 0;
		if (i > 0)
			x = ((Tone) (tones.get(i - 1))).getX() + distance_between_notes_;
		else
			x = 11 * radius_;

		tones.add(new Tone(super.getContext(), midi, x, first_line_, paint));
	}

	public void addElement(int midi) {
		int i = tones.size();
		int x = 0;
		if (i > 0)
			x = ((Tone) (tones.get(i - 1))).getX() + distance_between_notes_;
		else
			x = 12 * radius_;
		ArrayList<Integer> i_list = new ArrayList();
		i_list.add(midi);
		tones.add(new Tone(super.getContext(), i_list, x, first_line_, paint));
		invalidate();
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
		invalidate();
	}

	public void clearList() {
		if (!tones.isEmpty()) {
			tones.clear();
		}
		invalidate();

	}

	private void drawViolinschluessel(Canvas canvas) {
		Resources res = context.getResources();
		Bitmap bm = BitmapFactory.decodeResource(res, id_);
		// canvas.drawBitmap(bm, 10, 120, null);
		Rect dst_rct = new Rect(20, first_line_ - 60, 7 * radius_ + 10,
				first_line_ + 8 * radius_ + 60);
		canvas.drawBitmap(bm, null, dst_rct, null);
	}

}
