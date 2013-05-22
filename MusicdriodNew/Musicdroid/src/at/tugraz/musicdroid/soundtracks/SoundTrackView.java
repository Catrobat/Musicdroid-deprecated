package at.tugraz.musicdroid.soundtracks;

import at.tugraz.musicdroid.R;
import at.tugraz.musicdroid.MainActivity;
import at.tugraz.musicdroid.SoundManager;
import at.tugraz.musicdroid.SoundMixer;
import at.tugraz.musicdroid.helper.Helper;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;

public class SoundTrackView extends RelativeLayout implements OnClickListener, View.OnTouchListener{ 
	private SoundTrackComponentFactory factory = null;
	private Helper helper = null;
	private SoundTrack soundTrack = null;
	private int xDelta;
	private boolean moveable_locked = true;
	private boolean display_play_button = true;
	
	protected ImageView sound_type_img = null;
	protected View seperator_vert = null;
	protected TextView text = null;
	protected View seperator_hor = null;
	protected ImageButton play_button = null;
	protected ImageButton lock = null;
	
	
	public SoundTrackView(Context context, SoundTrack st) {
		super(context);
		soundTrack = st;
		helper = Helper.getInstance();
		factory = new SoundTrackComponentFactory(context);
		init();
	}
	
	public SoundTrackView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public void init() {
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(computeWidthRelativeToDuration(), 
				 																   helper.getScreenHeight()/6);
	    setLayoutParams(layoutParams);
	    setBackgroundColor(getResources().getColor(soundTrack.getType().getColorResource()));
	    setOnClickListener(this);
	    setOnTouchListener(this);
	    setRessources(soundTrack.getType().getImageResource(), soundTrack.getName(), soundTrack.getDuration());
	}
	
	private int computeWidthRelativeToDuration()
	{
		int width_total = helper.getScreenWidth();
		int duration = soundTrack.getDuration();
		
		return duration*width_total/Math.max(SoundMixer.getInstance().getDurationLongestTrack(), soundTrack.getDuration());
	}
	
	protected void setRessources(int id, String name, int duration)
	{
		sound_type_img = factory.newSoundTypeImageComponent(id);
		seperator_vert = factory.newVerticalSeperator(sound_type_img.getId());
		text = factory.newSoundFileTitleText(name, duration, seperator_vert.getId());
		seperator_hor = factory.newHorizontalSeperator(seperator_vert.getId(), text.getId());
		play_button = factory.newImageButton(R.drawable.play_button_sound_track, seperator_hor.getId(), seperator_vert.getId());
		lock = factory.newImageButton(R.drawable.lock_locked, seperator_hor.getId(), play_button.getId());

		sound_type_img.setPadding(10, 0, 10, 0);
		
		LayoutParams button_params = (LayoutParams) lock.getLayoutParams();
		button_params.setMargins(-30, 0,0,0); //dirty but don't know how to bring play and lock button closer together		
		lock.setLayoutParams(button_params);

        addView(sound_type_img);
		addView(seperator_vert);
		addView(text);
		addView(seperator_hor);
		addView(play_button);
		addView(lock);

		
		this.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//TODO Do something to signal that this button is clickable
			}
		});
		
		sound_type_img.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				((MainActivity)getContext()).startActionMode(getId(), soundTrack);
				return true;
			}
		});
		
		play_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(display_play_button)
				{
					display_play_button = false;
					play_button.setImageResource(R.drawable.pause_button_sound_track);
					SoundManager.playSound(soundTrack.getSoundPoolId(), 1);	
				}
				else
				{
					display_play_button = true;
					play_button.setImageResource(R.drawable.play_button_sound_track);
					SoundManager.stopSound(soundTrack.getSoundPoolId());
					
				}
			}
		});
		
		lock.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(moveable_locked) {
				  lock.setImageResource(R.drawable.lock_unlocked);
				  moveable_locked = false;
				}
				else {
				  lock.setImageResource(R.drawable.lock_locked);
				  moveable_locked = true;	
				}
				  
			}
		});
	}
	
	
	@Override
	public void onClick(View v) {
			Toast.makeText(getContext().getApplicationContext(), "You clicked - here's a Toast", Toast.LENGTH_SHORT).show();
	}
	
	
	/*
     * Move SoundTracks on Touch  
	 */
	public boolean onTouch(View view, MotionEvent event) {
	    final int X = (int) event.getRawX();
	    boolean ret = false;
	    switch (event.getAction() & MotionEvent.ACTION_MASK) {
	        case MotionEvent.ACTION_DOWN:
	            RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
	            xDelta = X - lParams.leftMargin;
	            break;
	        case MotionEvent.ACTION_MOVE:
	        	if(moveable_locked) break;
	            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
	            int old_margin = layoutParams.leftMargin;
	            int margin = X - xDelta;
	            
	            if(margin < 0) margin = 0;
	            if(margin > helper.getScreenWidth()-layoutParams.width) margin = helper.getScreenWidth()-layoutParams.width;
	            
	            if(margin != old_margin) {
	              layoutParams.leftMargin = margin;
	              int start_point = SoundMixer.getInstance().getStartPointByPixel(margin);
	              soundTrack.setStartPoint(start_point);
	              SoundMixer.getInstance().updateTimelineOnMove(getId(), margin, start_point);
	              view.setLayoutParams(layoutParams);
	    		}	
	            ret = false;
	            break;
	    }
	    invalidate();
	    //SoundMixer.getInstance().hideTimelineMovePosition();
	    return ret;
	}
	
	
	public void resize()
	{
		RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) getLayoutParams();
		lParams.width = computeWidthRelativeToDuration(); 
		lParams.height = helper.getScreenHeight()/6;
				
		setLayoutParams(lParams);
	}
	
	public void disableView()
	{
		setBackgroundColor(Color.GRAY);
	    sound_type_img.setColorFilter(R.color.abs__bright_foreground_disabled_holo_dark);
	    play_button.setColorFilter(R.color.abs__bright_foreground_disabled_holo_dark);
	    lock.setColorFilter(R.color.abs__bright_foreground_disabled_holo_dark);
	    text.setTextColor(getResources().getColor(R.color.abs__bright_foreground_disabled_holo_light));
	    seperator_hor.setBackgroundColor(getResources().getColor(R.color.abs__bright_foreground_disabled_holo_light));
	    seperator_vert.setBackgroundColor(getResources().getColor(R.color.abs__bright_foreground_disabled_holo_light));
		play_button.setEnabled(false);
		lock.setEnabled(false);
	}
	
	public void enableView()
	{
	    setBackgroundColor(getResources().getColor(soundTrack.getType().getColorResource()));
	    sound_type_img.setColorFilter(Color.WHITE);
	    play_button.setColorFilter(Color.WHITE);
	    lock.setColorFilter(Color.WHITE);
		text.setTextColor(getResources().getColor(R.color.custom_background_color));
	    seperator_hor.setBackgroundColor(Color.WHITE);
	    seperator_vert.setBackgroundColor(Color.WHITE);
		play_button.setEnabled(true);
		lock.setEnabled(true);
	}
	
	
	public SoundTrack getSoundTrack()
	{
		return soundTrack;
	}
	
	public ImageView getSoundTypeImage()
	{
		return sound_type_img;
	}
	
	public ImageButton getPlayButton()
	{
		return play_button;
	}
	
	public ImageButton getLock()
	{
		return lock;
	}


}
