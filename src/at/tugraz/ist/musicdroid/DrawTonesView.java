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
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
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
	private boolean moving_;
	private boolean auto_scroll_ = false;
	private int scrollx_ = 0;
	private int scrolldis_ = 200;
	private int radius_;
	private int scroll_counter_ = 0;
	static int t = 0;
	private int distance_between_notes_;
	private int width_ = 0;
	double downx = 0, downy = 0, upx = 0, upy = 0, down_help_ = 0;

	
	public OnTouchListener touchlis = new OnTouchListener() {

		//@Override
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
				double scrolldist_vert = downy - upy;

				if (scrolldist > 30
						&& (scrolldist_vert < 60 && scrolldist_vert > -60)) {

					scrollx_ += scrolldis_;
					scroll_counter_++;
					if (scrollx_ >= ((Tone) (tones.get(tones.size() - 1)))
							.getX() + 50 - v.getWidth()) {

						scrollx_ = ((Tone) (tones.get(tones.size() - 1)))
								.getX() + 50 - v.getWidth();

						scroll_counter_--;
						down_help_ = (scrollx_ + v.getWidth()) % 200;
					}
					v.scrollTo(scrollx_, 0);

				} else if (scrolldist < -30
						&& (scrolldist_vert < 60 && scrolldist_vert > -60)) {
					scrollx_ -= scrolldis_;
					scroll_counter_--;
					if (scrollx_ < 0) {
						down_help_ = 0;
						scrollx_ = 0;
						scroll_counter_ = 0;
					}
					v.scrollTo(scrollx_, 0);

				} else if (scrolldist > -30 && scrolldist < 30
						&& scrolldist_vert < 30 && scrolldist_vert > -30) {
					downx += scroll_counter_ * 200 + down_help_;
					checkNote((int) downx, (int) downy);

				} else if (scrolldist_vert > 30) {
					moveMarkedNotes(true);
				} else if (scrolldist_vert < -30) {
					moveMarkedNotes(false);
				}

				invalidate();
				break;
			}

			return true;
		}

	};

	public DrawTonesView(Context context, int id, int radius, int firstline,
			boolean scroll) {
		super(context);
		// super.setOnClickListener(onclick);
			
		super.setOnTouchListener(touchlis);
		auto_scroll_ = scroll;
		radius_ = radius;
		this.context = context;
		first_line_ = firstline;
		this.paint = new Paint();
		distance_between_notes_ = radius_ * 6;
		this.setBackgroundColor(Color.WHITE);
		this.id_ = id;
		
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		width_ = metrics.widthPixels; 
		invalidate();

	}

	@Override
	public void onDraw(Canvas canvas) {

		paint.setStyle(Paint.Style.FILL);
		paint.setAntiAlias(true);
		drawLines(canvas);
		for (int i = 0; i < tones.size(); i++) {
			((Tone) (tones.get(i))).draw(canvas);
		}

	}

	private void drawLines(Canvas canvas) {
		paint.setColor(Color.BLACK);
		int last_x = 0;
		if (tones.size() > 0)
			last_x = ((Tone) (tones.get(tones.size() - 1))).getX() + 50;

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
        tones.add(new Tone(super.getContext(), midi, x, first_line_, paint,
				radius_));
		invalidate();
		int last_x = ((Tone) (tones.get(tones.size() - 1))).getX() + 50;
		if (last_x > this.getWidth()) {
			if (auto_scroll_) {
				super.scrollTo(last_x - width_, 0);
				scroll_counter_ = last_x / 200;
				down_help_ = last_x % 200;
			}
		}
	}
    
	public int getTonesSize(){
		return tones.size();
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
		tones.add(new Tone(super.getContext(), i_list, x, first_line_, paint,
				radius_));
		invalidate();
		int last_x = ((Tone) (tones.get(tones.size() - 1))).getX() + 50;
		if (last_x > this.getWidth()) {
			if (auto_scroll_) {
				super.scrollTo(last_x - width_, 0);
				scroll_counter_ = last_x / 200;
				down_help_ = last_x % 200;
			}
		}
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

	private void checkNote(int x_value, int y_value) {

		for (int i = 0; i < tones.size(); i++) {
			int act_x_ = ((Tone) (tones.get(i))).getX();
			ArrayList<Integer> y_s_ = ((Tone) (tones.get(i))).getY();

			if (y_s_.size() == 1) {
				if (Math.sqrt(Math.pow(x_value - act_x_, 2)
						+ Math.pow(y_value - y_s_.get(0), 2)) <= 3 * radius_) {
					((Tone) (tones.get(i))).setMove();
					invalidate();
				}
			} else {
				for (int j = 0; j < y_s_.size(); j++) {
					if (Math.sqrt(Math.pow(x_value - act_x_, 2)
							+ Math.pow(y_value - y_s_.get(j), 2)) <= 3 * radius_) {
						((Tone) (tones.get(i))).setMove();
						invalidate();
					}
				}
			}
		}

	}

	private void moveMarkedNotes(boolean up) {

		for (int i = 0; i < tones.size(); i++) {
			if (((Tone) tones.get(i)).isMarked()) {
				((Tone) tones.get(i)).moveMidiVal(up);
			}

		}
		invalidate();
	}
}
