package at.tugraz.musicdroid.recorder;

import java.io.IOException;

import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.util.Log;

public class Recorder {
	private static MediaRecorder recorder = null;
	private AudioVisualizer visualizer = null;
	private String path = null;
	private boolean stop = false;
	
	public Recorder(String p)
	{
		path = p;
		visualizer = new AudioVisualizer();
	}
	
	public void record()
	{
		recorder = new MediaRecorder();
		setPreferences();

		try {
			recorder.prepare();
        } catch (IOException e) {
            Log.e("LOG", "prepare() failed");
        }

		
		recorder.start();
		startCommunicationThread();
	}
	
	public void stopRecording()
	{
		recorder.stop();
		stop = false;
		
	}
	
	private void startCommunicationThread()
	{
		Thread background=new Thread(new Runnable() {
			 @Override
			 public void run() {
			    while(!stop)
			    {
			      try {
			        Thread.sleep(125);
			        Message msg = new Message();
			        Bundle b = new Bundle();
			        b.putInt("amplitude", recorder.getMaxAmplitude());
			        msg.setData(b);
   			        visualizer.sendMessage(msg);
			      } catch (Exception e) {
			        Log.v("Error", e.toString());
			      }
			    }
			}
		});
        background.start();
	}
	
	private void setPreferences()
	{
		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		recorder.setOutputFile(Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+path);
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
	}

	
}
