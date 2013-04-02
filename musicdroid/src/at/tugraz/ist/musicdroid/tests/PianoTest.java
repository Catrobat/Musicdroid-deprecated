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
	
	private String getMidiNote(int value){
		switch (value){
			case 0:
				return "C";
			case 1:
				return "Cis";
			case 2:
				return "D";
			case 3:
				return "Dis";
			case 4: 
				return "E";
			case 5:
			    return "F";
			case 6:
				return "Fis";
			case 7:
				return "G";
			case 8:
				return "Gis";
			case 9:
				return "A";
			case 10:
				return "Ais";
			case 11:
				return "H";
	
		}
		
		return "W";			
			
	}
	public void testMidiSound(){
		int counter = 0;
		String midi_note;
		String file_name;
		int midi_value = 0;
		for(midi_value = 36; midi_value < 96; midi_value++ ){
			midi_note = getMidiNote(counter);
			file_name = midi_note + "_" + midi_value + ".mid";
			File directory = new File(Environment.getExternalStorageDirectory()+File.separator+"records"+File.separator+"piano_midi_sounds"+File.separator+file_name);
			assertTrue("File does not exist",directory.exists());
			counter++;
			if (counter == 12)
     			counter = 0;
		}
	}
	
}