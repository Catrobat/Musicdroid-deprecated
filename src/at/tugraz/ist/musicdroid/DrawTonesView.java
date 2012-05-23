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
	}

	@Override
	public void onDraw(Canvas canvas) {

		this.paint.setColor(Color.BLACK);
		paint.setStyle(Paint.Style.FILL);
		paint.setAntiAlias(true);
		drawLines(canvas);		
		
		for(int i = 0 ; i < 30; i++)
		{
			tones.add (new Tone(super.getContext(), 40+i, 20 + i* 40, 120, paint));
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
	
	public void addElement(int tone){
		int i = tones.size() + 1;
		tones.add(new Tone(super.getContext(), tone, 20 + i*40, 120, paint));
	}
	
	public void deleteElement(int i) {
		tones.remove(i);
		for(int j = i; j < tones.size() ; j++)
		{
			int x_value = ((Tone)(tones.get(j))).getX();
			((Tone)(tones.get(j))).setX(x_value - 40);
		}
	}
	
	public void clearList() {
		tones.clear();
	}

}
