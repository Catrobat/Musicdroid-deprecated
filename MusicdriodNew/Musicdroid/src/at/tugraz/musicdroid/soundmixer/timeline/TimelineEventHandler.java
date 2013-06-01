package at.tugraz.musicdroid.soundmixer.timeline;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout.LayoutParams;
import at.tugraz.musicdroid.soundmixer.SoundMixer;

public class TimelineEventHandler extends Handler {
	private static TimelineEventHandler instance = null;
	private View trackPositionView = null;
	private Timeline timeline = null;
	
	public static TimelineEventHandler getInstance() {
        if (instance == null) {
            instance = new TimelineEventHandler();
        }
        return instance;
    }
	
	public void init(Timeline t) {
		this.timeline = t;
	}
	
	@Override
	public void handleMessage(Message msg) {
		Bundle b = msg.getData();
		if(b.containsKey("position"))
		{
			trackPositionView = timeline.getTrackPositionView();
			int key = b.getInt("position");
			Log.i("RecorderLayout", "MessageDuration = " + key);
			LayoutParams params = (LayoutParams) trackPositionView.getLayoutParams();
			params.width = key * SoundMixer.getInstance().getPixelPerSecond();
			trackPositionView.setLayoutParams(params);
		}

	}
}
