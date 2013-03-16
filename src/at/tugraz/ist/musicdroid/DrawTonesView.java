package at.tugraz.ist.musicdroid;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

public class DrawTonesView extends View {
	private Paint paint_;
	private List<Tone> tones_ = new ArrayList<Tone>();
	private Context context_;
	private int first_line_;
	private int id_;
	private boolean auto_scroll_ = false;
	private int scrollx_ = 0;
	private int scrolldis_ = 200;
	private int radius_;
	private int scroll_counter_ = 0;
	private int distance_between_notes_;
	private int width_ = 0;
	double downx_ = 0, downy_ = 0, upx_ = 0, upy_ = 0, down_help_ = 0;
	
	private static final int MOVE_VALUE = 30;

	
	public OnTouchListener touchListener = new OnTouchListener() {

		//@Override
		public boolean onTouch(View v, MotionEvent event) {
			int action = event.getAction();
			switch (action) {
			case MotionEvent.ACTION_DOWN:
				downx_ = event.getX();
				downy_ = event.getY();
				break;
			case MotionEvent.ACTION_MOVE:
				break;
			case MotionEvent.ACTION_UP:
				upx_ = event.getX();
				upy_ = event.getY();

				double scrolldist = downx_ - upx_;
				double scrolldist_vert = downy_ - upy_;

				if (scrolldist > MOVE_VALUE
						&& (scrolldist_vert < 60 && scrolldist_vert > -60)) {

					scrollx_ += scrolldis_;
					scroll_counter_++;
					if (scrollx_ >= ((Tone) (tones_.get(tones_.size() - 1)))
							.get_X() + 50 - v.getWidth()) {

						scrollx_ = ((Tone) (tones_.get(tones_.size() - 1)))
								.get_X() + 50 - v.getWidth();

						scroll_counter_--;
						down_help_ = (scrollx_ + v.getWidth()) % 200;
					}
					v.scrollTo(scrollx_, 0);

				} else if (scrolldist < -MOVE_VALUE
						&& (scrolldist_vert < 60 && scrolldist_vert > -60)) {
					scrollx_ -= scrolldis_;
					scroll_counter_--;
					if (scrollx_ < 0) {
						down_help_ = 0;
						scrollx_ = 0;
						scroll_counter_ = 0;
					}
					v.scrollTo(scrollx_, 0);

				} else if (scrolldist > -MOVE_VALUE && scrolldist < MOVE_VALUE
						&& scrolldist_vert < MOVE_VALUE && scrolldist_vert > -MOVE_VALUE) {
					downx_ += scroll_counter_ * 200 + down_help_;
					checkNote((int) downx_, (int) downy_);

				} else if (scrolldist_vert > MOVE_VALUE) {
					moveMarkedNotes(true);
				} else if (scrolldist_vert < -MOVE_VALUE) {
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
			
		super.setOnTouchListener(touchListener);
		auto_scroll_ = scroll;
		radius_ = radius;
		this.context_ = context;
		first_line_ = firstline;
		this.paint_ = new Paint();
		distance_between_notes_ = radius_ * 6;
		this.setBackgroundColor(Color.WHITE);
		this.id_ = id;
		
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		width_ = metrics.widthPixels; 
		invalidate();

	}

	@Override
	public void onDraw(Canvas canvas) {

		paint_.setStyle(Paint.Style.FILL);
		paint_.setAntiAlias(true);
		drawLines(canvas);
		for (int i = 0; i < tones_.size(); i++) {
			((Tone) (tones_.get(i))).draw(canvas);
		}

	}

	private void drawLines(Canvas canvas) {
		paint_.setColor(Color.BLACK);
		int last_x = 0;
		if (tones_.size() > 0)
			last_x = ((Tone) (tones_.get(tones_.size() - 1))).get_X() + 50;

		if (last_x < this.getRight())
			last_x = this.getRight();

		drawViolinClef(canvas);
		for (int i = 0; i < 5; i++) {
			canvas.drawLine(this.getLeft(), first_line_ + i * radius_ * 2,
					last_x, first_line_ + i * radius_ * 2, paint_);
		}

		Paint p_ = new Paint(paint_);
		p_.setStrokeWidth(10);

		canvas.drawLine(last_x - 10, first_line_, last_x - 10, first_line_ + 8
				* radius_, paint_);
		canvas.drawLine(last_x, first_line_ - 1, last_x, first_line_ + 8
				* radius_ + 1, p_);

		canvas.drawLine(1, first_line_ - 1, 1, first_line_ + 8 * radius_ + 1,
				p_);
		canvas.drawLine(10, first_line_, 10, first_line_ + 8 * radius_, paint_);
	}

	public void addElement(ArrayList<Integer> midi) {
		int i = tones_.size();
		int x = 0;
		if (i > 0)
			x = ((Tone) (tones_.get(i - 1))).get_X() + distance_between_notes_;
		else
			x = 11 * radius_;
        tones_.add(new Tone(super.getContext(), midi, x, first_line_, paint_,
				radius_));
		invalidate();
		int last_x = ((Tone) (tones_.get(tones_.size() - 1))).get_X() + 50;
		if (last_x > this.getWidth()) {
			if (auto_scroll_) {
				super.scrollTo(last_x - width_, 0);
				scroll_counter_ = last_x / 200;
				down_help_ = last_x % 200;
			}
		}
	}
    
	public List<Tone> getTonesList(){
		return tones_;
	}
	
	public int getTonesSize(){
		return tones_.size();
	}
	
	
	public void addElement(int midi) {
		int i = tones_.size();
		int x = 0;
		if (i > 0)
			x = ((Tone) (tones_.get(i - 1))).get_X() + distance_between_notes_;
		else
			x = 12 * radius_;
		ArrayList<Integer> i_list = new ArrayList<Integer>();
		i_list.add(midi);
		tones_.add(new Tone(super.getContext(), i_list, x, first_line_, paint_,
				radius_));
		invalidate();
		int last_x = ((Tone) (tones_.get(tones_.size() - 1))).get_X() + 50;
		if (last_x > this.getWidth()) {
			if (auto_scroll_) {
				super.scrollTo(last_x - width_, 0);
				scroll_counter_ = last_x / 200;
				down_help_ = last_x % 200;
			}
		}
	}

	public void deleteElement(int i) {
		if (i > tones_.size()) {
			return;
		}

		tones_.remove(i);
		for (int j = i; j < tones_.size(); j++) {
			int x_value = ((Tone) (tones_.get(j))).get_X();
			((Tone) (tones_.get(j))).setX(x_value - distance_between_notes_);
		}
		invalidate();
	}

	public void clearList() {
		if (!tones_.isEmpty()) {
			tones_.clear();
		}
		invalidate();

	}

	private void drawViolinClef(Canvas canvas) {
		Resources res = context_.getResources();
		Bitmap bm = BitmapFactory.decodeResource(res, id_);
		Rect dst_rct = new Rect(20, first_line_ - 60, 7 * radius_ + 10,
				first_line_ + 8 * radius_ + 60);
		canvas.drawBitmap(bm, null, dst_rct, null);
	}

	private void checkNote(int x_value, int y_value) {

		for (int i = 0; i < tones_.size(); i++) {
			int act_x_ = ((Tone) (tones_.get(i))).get_X();
			ArrayList<Integer> y_s_ = ((Tone) (tones_.get(i))).get_Y();

			if (y_s_.size() == 1) {
				if (Math.sqrt(Math.pow(x_value - act_x_, 2)
						+ Math.pow(y_value - y_s_.get(0), 2)) <= 3 * radius_) {
					((Tone) (tones_.get(i))).setMove();
					invalidate();
				}
			} else {
				for (int j = 0; j < y_s_.size(); j++) {
					if (Math.sqrt(Math.pow(x_value - act_x_, 2)
							+ Math.pow(y_value - y_s_.get(j), 2)) <= 3 * radius_) {
						((Tone) (tones_.get(i))).setMove();
						invalidate();
					}
				}
			}
		}

	}

	private void moveMarkedNotes(boolean up) {

		for (int i = 0; i < tones_.size(); i++) {
			if (((Tone) tones_.get(i)).isMarked()) {
				((Tone) tones_.get(i)).moveMidiVal(up);
			}

		}
		invalidate();
	}
}
