package at.tugraz.ist.musicdroid.tests;

import com.jayway.android.robotium.solo.Solo;

import android.media.MediaPlayer;
import android.test.ActivityInstrumentationTestCase2;
import at.tugraz.ist.musicdroid.MusicdroidActivity;
import at.tugraz.ist.musicdroid.PlaySoundActivity;
import at.tugraz.ist.musicdroid.R;
import android.view.View;


public class PlaySoundActivityTest extends ActivityInstrumentationTestCase2<PlaySoundActivity> { //aktivity gibt an, wo test startet
    private
     Solo solo; 
	
	public PlaySoundActivityTest() {
		super("at.tugraz.ist.musicdroid", PlaySoundActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
	}

	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
		super.tearDown();
	}

	
	public void testPlay(){
		//PlaySoundActivity.initFile();
		
		solo.clickOnButton(solo.getString(R.string.start_text));
		MediaPlayer mp = PlaySoundActivity.getMP();
		assertTrue("MediaPlayer is not playing", mp.isPlaying());			
	}
	
	public void testPauseResume(){
		//PlaySoundActivity.initFile();
		
		solo.clickOnButton(solo.getString(R.string.start_text));
		solo.sleep(10000);
		
		solo.clickOnButton(solo.getString(R.string.pause_text));
		MediaPlayer mp = PlaySoundActivity.getMP();
		assertFalse("MediaPlayer is still playing after SoundManager was paused", mp.isPlaying());

		solo.clickOnButton(solo.getString(R.string.resume_text));
	    mp = PlaySoundActivity.getMP();
		assertTrue("MediaPlayer is not playing after resume", mp.isPlaying());

	}
	
	public void testStop() {
		//PlaySoundActivity.initFile();
		solo.clickOnButton(solo.getString(R.string.start_text));
		solo.sleep(10000);
		solo.clickOnButton(solo.getString(R.string.stop_text));
		MediaPlayer mp = PlaySoundActivity.getMP();
		
		assertFalse("MediaPlayer is still playing after pressing Stop", mp.isPlaying());
	}
	
	public void testStopWithoutPlaying(){
		
		solo.clickOnButton(solo.getString(R.string.stop_text));
		MediaPlayer mp = PlaySoundActivity.getMP();
		assertFalse("MediaPlayer spinnt", mp.isPlaying());
	}
	
	public void testPlayPauseResumeStopPlay(){
		
		solo.clickOnButton(solo.getString(R.string.start_text));
		solo.sleep(10000);
		solo.clickOnButton(solo.getString(R.string.pause_text));
		solo.clickOnButton(solo.getString(R.string.resume_text));
		solo.sleep(10000);
		solo.clickOnButton(solo.getString(R.string.stop_text));
		solo.clickOnButton(solo.getString(R.string.start_text));
		MediaPlayer mp = PlaySoundActivity.getMP();
		assertTrue("Mediaplayer spielt brav", mp.isPlaying());
		
	}
}

