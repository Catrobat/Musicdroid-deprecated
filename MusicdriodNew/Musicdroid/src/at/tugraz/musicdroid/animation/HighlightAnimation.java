package at.tugraz.musicdroid.animation;


import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;


public class HighlightAnimation {
	public static HighlightAnimation instance = null;

	
	public static HighlightAnimation getInstance() {
        if (instance == null) {
            instance = new HighlightAnimation();
        }
        return instance;
    }
	
	public void highlightViewAnimation(View v)
	{	
		final Animation animation = new AlphaAnimation(1, 0); 
	    animation.setDuration(750); 
	    animation.setInterpolator(new LinearInterpolator()); 
	    animation.setRepeatCount(5); 
	    animation.setRepeatMode(Animation.REVERSE); 
	    v.startAnimation(animation);
		
	}
}
