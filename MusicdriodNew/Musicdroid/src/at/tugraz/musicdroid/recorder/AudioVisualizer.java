package at.tugraz.musicdroid.recorder;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import at.tugraz.musicdroid.R;
import at.tugraz.musicdroid.RecorderActivity;
import at.tugraz.musicdroid.helper.Helper;


public class AudioVisualizer extends Handler {
	private static final int MAX_AMPLITUDE = 32800;
	private Context context = null;
	private View equalizerView = null;
	private ImageView microphoneImageView = null;
	
	
	public AudioVisualizer(Context context)
	{
		this.context = context;
		equalizerView = (View)((RecorderActivity)context).findViewById(R.id.microphone_equalizer);
		microphoneImageView = (ImageView)((RecorderActivity)context).findViewById(R.id.microphone);
	}
	
	@Override
	public void handleMessage(Message msg) {
		Bundle b = msg.getData();
		int amplitude = b.getInt("amplitude");
	    int newHeight = microphoneImageView.getHeight()*amplitude/MAX_AMPLITUDE;
	    LayoutParams params = (LayoutParams)equalizerView.getLayoutParams();
	    params.height = newHeight;
	    params.width = microphoneImageView.getWidth();
	    equalizerView.setLayoutParams(params);
	}
}
