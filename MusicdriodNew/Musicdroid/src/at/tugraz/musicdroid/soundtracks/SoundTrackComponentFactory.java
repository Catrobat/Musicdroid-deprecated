package at.tugraz.musicdroid.soundtracks;

import at.tugraz.musicdroid.R;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

public class SoundTrackComponentFactory {
	private Context context = null;
	private int local_id = 4321;
	
	public SoundTrackComponentFactory(Context c)
	{
		this.context = c;
	}
	
	public ImageView newSoundTypeImageComponent(int res_id)
	{
        ImageView imageView = new ImageView(context);
        RelativeLayout.LayoutParams vp = 
            new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, 
                            LayoutParams.WRAP_CONTENT);
        vp.addRule(RelativeLayout.CENTER_VERTICAL);
        imageView.setLayoutParams(vp);
        
        // To Add a Menu_Triangle. (not working properly right now)  		
		/*Drawable[] layers = new Drawable[2];
		layers[0] = context.getResources().getDrawable(res_id);
		layers[1] = context.getResources().getDrawable(R.drawable.menu_triangle);
		LayerDrawable layerDrawable = new LayerDrawable(layers);
		imageView.setImageDrawable(layerDrawable);
 		*/
 		
        imageView.setImageResource(res_id);
        imageView.setId(getNewId());
        return imageView;
	}

	
	public ImageButton newImageButton(int image_ressource, int align_below_id, int align_right_of_id )
	{
		ImageButton play_button = new ImageButton(context);
		play_button.setImageResource(image_ressource);
		LayoutParams button_params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		//LayoutParams button_params = new RelativeLayout.LayoutParams(getHeight()/3, getHeight()/3);
		button_params.addRule(RelativeLayout.BELOW, align_below_id);
		button_params.addRule(RelativeLayout.RIGHT_OF, align_right_of_id);
		play_button.setLayoutParams(button_params);
		play_button.setBackgroundColor(Color.TRANSPARENT);
		play_button.setId(getNewId());
		return play_button;
	}
	
	public View newVerticalSeperator(int align_right_of_id)
	{		
		View seperator = new View(context);
		LayoutParams seperator_params = new RelativeLayout.LayoutParams(2, LayoutParams.WRAP_CONTENT);
		seperator_params.addRule(RelativeLayout.CENTER_HORIZONTAL);
		seperator_params.addRule(RelativeLayout.ALIGN_RIGHT, align_right_of_id);
		seperator.setLayoutParams(seperator_params);
		int reference_id = getNewId();
		seperator.setId(reference_id);
		seperator.setBackgroundColor(Color.WHITE);
		return seperator;
	}
	
	public LinearLayout newRelativeLayout(int width, int height)
	{
		LinearLayout relativeLayout = new LinearLayout(context);
        return relativeLayout;
	}
	
	public View newHorizontalSeperator(int align_left_of_id, int align_bottom_id)
	{		
		View seperator = new View(context);
		LayoutParams seperator_params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,2);
		seperator_params.addRule(RelativeLayout.ALIGN_LEFT, align_left_of_id);
		seperator_params.addRule(RelativeLayout.ALIGN_BOTTOM, align_bottom_id);
		seperator.setLayoutParams(seperator_params);
		seperator.setId(getNewId());
		seperator.setBackgroundColor(Color.WHITE);
		return seperator;
	}
	
	
	public TextView newSoundFileTitleText(String str,int duration, int align_right_of_id)
	{
		TextView text = new TextView(context);
		
		int minutes = duration/60;
	    int seconds = duration%60;
	    String min = "" + minutes; 
	    
	    if(minutes < 10)
	      min = "0" + min;
	    	
		
		text.setText(str + " | " + min + ":" + seconds);
		text.setTextColor(context.getResources().getColor(R.color.custom_background_color));
		LayoutParams text_params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		text_params.addRule(RelativeLayout.RIGHT_OF, align_right_of_id);
		text_params.setMargins(10, 0, 0, 0);
		text.setId(getNewId());
		text.setLayoutParams(text_params);
		return text;
	}

	private int getNewId()
	{
	  local_id = local_id+1;
	  return local_id;
	}
}
