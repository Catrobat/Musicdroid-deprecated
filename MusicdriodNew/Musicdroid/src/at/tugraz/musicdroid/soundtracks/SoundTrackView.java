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
	private boolean displayPlayButton = true;
	
	protected ImageView soundTypeImageView = null;
	protected View verticalSeperatorView = null;
	protected TextView soundtrackDescriptionTextView = null;
	protected View horizontalSeperatorView = null;
	protected ImageButton playImageButton = null;
	protected ImageButton lockImageButton = null;
	
	
	public SoundTrackView(Context context, SoundTrack st) {
		super(context);
		soundTrack = st;
		helper = Helper.getInstance();
		factory = new SoundTrackComponentFactory(context);
		initSoundTrackView();
	}
	
	public SoundTrackView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public void initSoundTrackView() {
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
		soundTypeImageView = factory.newSoundTypeImageComponent(id);
		verticalSeperatorView = factory.newVerticalSeperator(soundTypeImageView.getId());
		soundtrackDescriptionTextView = factory.newSoundFileTitleText(name, duration, verticalSeperatorView.getId());
		horizontalSeperatorView = factory.newHorizontalSeperator(verticalSeperatorView.getId(), soundtrackDescriptionTextView.getId());
		playImageButton = factory.newImageButton(R.drawable.play_button_sound_track, horizontalSeperatorView.getId(), verticalSeperatorView.getId());
		lockImageButton = factory.newImageButton(R.drawable.lock_locked, horizontalSeperatorView.getId(), playImageButton.getId());

		soundTypeImageView.setPadding(10, 0, 10, 0);
		
		LayoutParams button_params = (LayoutParams) lockImageButton.getLayoutParams();
		button_params.setMargins(-30, 0,0,0); //dirty but don't know how to bring play and lock button closer together		
		lockImageButton.setLayoutParams(button_params);

        addView(soundTypeImageView);
		addView(verticalSeperatorView);
		addView(soundtrackDescriptionTextView);
		addView(horizontalSeperatorView);
		addView(playImageButton);
		addView(lockImageButton);

		
		this.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//TODO Do something to signal that this button is clickable
			}
		});
		
		soundTypeImageView.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				((MainActivity)getContext()).startActionMode(getId(), soundTrack);
				return true;
			}
		});
		
		playImageButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(displayPlayButton)
				{
					displayPlayButton = false;
					playImageButton.setImageResource(R.drawable.pause_button_sound_track);
					SoundManager.playSound(soundTrack.getSoundPoolId(), 1);	
				}
				else
				{
					displayPlayButton = true;
					playImageButton.setImageResource(R.drawable.play_button_sound_track);
					SoundManager.stopSound(soundTrack.getSoundPoolId());
					
				}
			}
		});
		
		lockImageButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(moveable_locked) {
				  lockImageButton.setImageResource(R.drawable.lock_unlocked);
				  moveable_locked = false;
				}
				else {
				  lockImageButton.setImageResource(R.drawable.lock_locked);
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
	    soundTypeImageView.setColorFilter(R.color.abs__bright_foreground_disabled_holo_dark);
	    playImageButton.setColorFilter(R.color.abs__bright_foreground_disabled_holo_dark);
	    lockImageButton.setColorFilter(R.color.abs__bright_foreground_disabled_holo_dark);
	    soundtrackDescriptionTextView.setTextColor(getResources().getColor(R.color.abs__bright_foreground_disabled_holo_light));
	    horizontalSeperatorView.setBackgroundColor(getResources().getColor(R.color.abs__bright_foreground_disabled_holo_light));
	    verticalSeperatorView.setBackgroundColor(getResources().getColor(R.color.abs__bright_foreground_disabled_holo_light));
		playImageButton.setEnabled(false);
		lockImageButton.setEnabled(false);
	}
	
	public void enableView()
	{
	    setBackgroundColor(getResources().getColor(soundTrack.getType().getColorResource()));
	    soundTypeImageView.setColorFilter(Color.WHITE);
	    playImageButton.setColorFilter(Color.WHITE);
	    lockImageButton.setColorFilter(Color.WHITE);
		soundtrackDescriptionTextView.setTextColor(getResources().getColor(R.color.custom_background_color));
	    horizontalSeperatorView.setBackgroundColor(Color.WHITE);
	    verticalSeperatorView.setBackgroundColor(Color.WHITE);
		playImageButton.setEnabled(true);
		lockImageButton.setEnabled(true);
	}
	
	
	public SoundTrack getSoundTrack()
	{
		return soundTrack;
	}
	
	public ImageView getSoundTypeImage()
	{
		return soundTypeImageView;
	}
	
	public ImageButton getPlayButton()
	{
		return playImageButton;
	}
	
	public ImageButton getLock()
	{
		return lockImageButton;
	}


}
