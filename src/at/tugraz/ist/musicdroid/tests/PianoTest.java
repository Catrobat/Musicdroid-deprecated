package at.tugraz.ist.musicdroid.tests;

import java.io.File;

import android.os.Environment;
import android.test.ActivityInstrumentationTestCase2;
import at.tugraz.ist.musicdroid.PianoActivity;
import com.jayway.android.robotium.solo.Solo;

public class PianoTest extends ActivityInstrumentationTestCase2<PianoActivity> {
	
	private Solo solo;
	public PianoTest() {
		super("at.tugraz.ist.musicdroid", PianoActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
	}
	
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
		super.tearDown();
	}
    
	public void testDir(){
		File directory = new File(Environment.getExternalStorageDirectory()+File.separator+"records"+File.separator+"piano_midi_sounds");
		assertTrue("File does not exist",directory.exists());
	}
	
	public void testMidiSound(){
		
	}
	
}