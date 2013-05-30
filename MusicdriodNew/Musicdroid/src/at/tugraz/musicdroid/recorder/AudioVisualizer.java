package at.tugraz.musicdroid.recorder;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


public class AudioVisualizer extends Handler {
	@Override
	public void handleMessage(Message msg) {
		Bundle b = msg.getData();
		int key = b.getInt("amplitude");
		Log.i("NewMessage", ""+key);
	}
}
