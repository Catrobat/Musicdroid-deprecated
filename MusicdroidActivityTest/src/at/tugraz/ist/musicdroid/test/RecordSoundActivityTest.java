package at.tugraz.ist.musicdroid.test;

import java.io.File;

import com.jayway.android.robotium.solo.Solo;

import android.os.Environment;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import at.tugraz.ist.musicdroid.MusicdroidActivity;
import at.tugraz.ist.musicdroid.R;
import at.tugraz.ist.musicdroid.RecordSoundActivity;


public class RecordSoundActivityTest extends ActivityInstrumentationTestCase2<RecordSoundActivity> { 
    private
     Solo solo; 
	
	public RecordSoundActivityTest() {
		super("at.tugraz.ist.musicdroid", RecordSoundActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
	}

	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
		super.tearDown();
	}

	
	public void testButtonEnabled() {
		Button startbutton = (Button) solo.getView(R.id.stopButton);
		Button playbutton = (Button) solo.getView(R.id.playButton);
		assertFalse("Button is enabeld", startbutton.isEnabled());
		assertFalse("Button is enabeld", playbutton.isEnabled());
		
		solo.clickOnButton(0); 
		assertTrue("Button is disabled", startbutton.isEnabled());
		assertTrue("Button is disabled", playbutton.isEnabled());
	}
	
	public void testFileSaved() {
		File directory;
		Button startbutton = (Button) solo.getView(R.id.stopButton);
		Button playbutton = (Button) solo.getView(R.id.playButton);
		solo.clickOnButton(0); 
		solo.sleep(3000);
		solo.clickOnButton(2);
		//File file = getContext().getFileStreamPath("test.wav");
		String file = android.os.Environment.getExternalStorageDirectory().getPath() + "/records/test.wav";
		File newFile = new File(file);
  	    
		assertTrue("Failed to write file", newFile.exists());	
		//solo.clickOnButton(0); 
		//solo.clickOnButton(2);
	}
	
	/*public void testRecordFile() {
	    solo.clickOnButton(solo.getString(R.string.recordButtonTextField));
	    assertTrue("Text didn't change", solo.searchText("I'm recording now"));
	    solo.clickOnButton(solo.getString(R.string.stopRecordButtonTextField));
	    assertTrue("Text didn't change", solo.searchText("Stopped Recording"));   
	}
	
	public void testChronometer() {
		solo.clickOnButton(0);
		//assertTrue("Chronometer did not start", solo.)
	} */
	
}
