package at.tugraz.musicdroid.recorder;

import java.io.IOException;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

public class Player {
	private RecorderLayout layout = null;
	private MediaPlayer mediaPlayer = null;
	private boolean stop = false;
	
	public Player(RecorderLayout layout)
	{
		this.layout = layout;
	}
	
	public void playRecordedFile()
	{
		Log.i("Player", "playRecorderFile");
		mediaPlayer = new MediaPlayer();
		
		try {
			mediaPlayer.setDataSource(AudioHandler.getInstance().getFilenameFullPath());
			mediaPlayer.prepare();
		} catch (IllegalArgumentException e) {
			Log.i("Player-Exception", "IllegalArgumentException");
			e.printStackTrace();
		} catch (SecurityException e) {
			Log.i("Player-Exception", "SecurityException");
			e.printStackTrace();
		} catch (IllegalStateException e) {
			Log.i("Player-Exception", "IllegalStateException");
			e.printStackTrace();
		} catch (IOException e) {
			Log.i("Player-Exception", "IOException");
			e.printStackTrace();
		}

		
		mediaPlayer.setOnPreparedListener(new OnPreparedListener() {

            public void onPrepared(MediaPlayer mp) {
            	Log.i("Player", "Prepared");
                int duration = mediaPlayer.getDuration();
        		layout.setTrackDuration(duration/1000);
        		stop = false;
                mediaPlayer.start();
                startCommunicationThread();
            }
        });
		
		mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
		    public void onCompletion(MediaPlayer mp) {
		    	stopPlaying();
		    }
		});
	}
	
	public void stopPlaying()
	{
		stop = true;
		mediaPlayer.stop();
		layout.handlePlaySoundComplete();
	}
	
	
	private void startCommunicationThread()
	{
		Thread background=new Thread(new Runnable() {
			 @Override
			 public void run() {
			    int counter = 1;
			    while(!stop)
			    {	
			      try {
			        Thread.sleep(1000);
				    sendTrackPositionMessage(counter);
			        counter = counter + 1;
 
			      } catch (Exception e) {
			          Log.v("Error", e.toString());
			      }
			    }
			}
		});
        background.start();
	}
	
	private void sendTrackPositionMessage(int counter)
	{
		Message msg = new Message();
        Bundle b = new Bundle();
        b.putInt("trackposition", counter);
        msg.setData(b);
	    layout.sendMessage(msg);
	}
}
