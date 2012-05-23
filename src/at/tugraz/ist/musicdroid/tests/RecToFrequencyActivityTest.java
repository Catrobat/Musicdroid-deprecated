package at.tugraz.ist.musicdroid.tests;

import com.jayway.android.robotium.solo.Solo;

import android.test.ActivityInstrumentationTestCase2;
import at.tugraz.ist.musicdroid.MusicdroidActivity;
import at.tugraz.ist.musicdroid.R;
import at.tugraz.ist.musicdroid.RecToFrequencyActivity;

public class RecToFrequencyActivityTest extends ActivityInstrumentationTestCase2<RecToFrequencyActivity> { //aktivity gibt an, wo test startet
    private
     Solo solo; 
	
	public RecToFrequencyActivityTest() {
		super("at.tugraz.ist.musicdroid", RecToFrequencyActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		try {
			solo.finalize(); 
			
		} catch(Throwable e) {
			e.printStackTrace();
		}
		getActivity().finish(); 
	}

	public void PreRecord(){
		assertTrue("Rec-Button not found pre record", solo.getButton(R.id.startRecordButton).isShown());
		assertFalse("Stop-Rec-Button visible pre record", solo.getButton(R.id.stopRecordButton).isShown());
		assertFalse("Next-Note-Button visible pre record", solo.getButton(solo.getString(R.string.rtof_next_note)).isShown());
		assertFalse("Save-File-Button visible pre record", solo.getButton(solo.getString(R.string.rtof_save_file)).isShown());
	}
	
	public void RecordSound(){
		solo.clickOnButton(R.id.startRecordButton);
		assertFalse("Rec-Button visible during record", solo.getButton(R.id.startRecordButton).isShown());
		assertTrue("Stop-Rec-Button not found during record", solo.getButton(R.id.stopRecordButton).isShown());
		assertTrue("Next-Note-Button not found during record", solo.searchButton(solo.getString(R.string.rtof_next_note), true));
		assertFalse("Save-File-Button visible during record", solo.getButton(solo.getString(R.string.rtof_save_file)).isShown());
	}
	public void PostSound(){
		solo.clickOnButton(R.id.startRecordButton);
		solo.clickOnButton(R.id.stopRecordButton);
		assertTrue("Rec-Button not found post record", solo.getButton(R.id.startRecordButton).isShown());
		assertFalse("Stop-Rec-Button visible post record", solo.getButton(R.id.stopRecordButton).isShown());
		assertFalse("Next-Note-Button visible post record", solo.getButton(solo.getString(R.string.rtof_next_note)).isShown());
		assertTrue("Save-File-Button not found post record", solo.searchButton(solo.getString(R.string.rtof_save_file), true));
	}
	
}