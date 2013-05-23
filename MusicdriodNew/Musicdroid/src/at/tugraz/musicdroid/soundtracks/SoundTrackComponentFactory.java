package at.tugraz.musicdroid.soundtracks;

import at.tugraz.musicdroid.R;
import at.tugraz.musicdroid.helper.Helper;

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
	private int localId = 4321;
	
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
		ImageButton playButton = new ImageButton(context);
		playButton.setImageResource(image_ressource);
		LayoutParams buttonParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		//LayoutParams button_params = new RelativeLayout.LayoutParams(getHeight()/3, getHeight()/3);
		buttonParams.addRule(RelativeLayout.BELOW, align_below_id);
		buttonParams.addRule(RelativeLayout.RIGHT_OF, align_right_of_id);
		playButton.setLayoutParams(buttonParams);
		playButton.setBackgroundColor(Color.TRANSPARENT);
		playButton.setId(getNewId());
		return playButton;
	}
	
	public View newVerticalSeperator(int align_right_of_id)
	{		
		View seperatorView = new View(context);
		LayoutParams seperatorParams = new RelativeLayout.LayoutParams(2, LayoutParams.WRAP_CONTENT);
		seperatorParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		seperatorParams.addRule(RelativeLayout.ALIGN_RIGHT, align_right_of_id);
		seperatorView.setLayoutParams(seperatorParams);
		int reference_id = getNewId();
		seperatorView.setId(reference_id);
		seperatorView.setBackgroundColor(Color.WHITE);
		return seperatorView;
	}
	
	public LinearLayout newRelativeLayout(int width, int height)
	{
		LinearLayout relativeLayout = new LinearLayout(context);
        return relativeLayout;
	}
	
	public View newHorizontalSeperator(int align_left_of_id, int align_bottom_id)
	{		
		View seperator = new View(context);
		LayoutParams seperatorParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,2);
		seperatorParams.addRule(RelativeLayout.ALIGN_LEFT, align_left_of_id);
		seperatorParams.addRule(RelativeLayout.ALIGN_BOTTOM, align_bottom_id);
		seperator.setLayoutParams(seperatorParams);
		seperator.setId(getNewId());
		seperator.setBackgroundColor(Color.WHITE);
		return seperator;
	}
	
	
	public TextView newSoundFileTitleText(String str,int duration, int alignRightOfId)
	{
		TextView text = new TextView(context);
		
		text.setText(str + " | " + Helper.getInstance().durationStringFromInt(duration));
		text.setTextColor(context.getResources().getColor(R.color.custom_background_color));
		LayoutParams textParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		textParams.addRule(RelativeLayout.RIGHT_OF, alignRightOfId);
		textParams.setMargins(10, 0, 0, 0);
		text.setId(getNewId());
		text.setLayoutParams(textParams);
		return text;
	}

	private int getNewId()
	{
	  localId = localId+1;
	  return localId;
	}
}
