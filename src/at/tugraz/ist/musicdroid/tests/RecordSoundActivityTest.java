package at.tugraz.ist.musicdroid.tests;

import com.jayway.android.robotium.solo.Solo;

import android.test.ActivityInstrumentationTestCase2;
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
		super.tearDown();
		try {
			solo.finalize(); 
			
		} catch(Throwable e) {
			e.printStackTrace();
		}
		getActivity().finish(); 
	}
	
	public void testRecordSoundScreen(){
	  assertTrue("TextView Record not found", solo.searchButton(solo.getString(R.string.recordButtonTextField)));
	  //assertTrue("TextView Record not found", solo.searchText(solo.getString(R.string.recordButtonTextField))); 
	}

	public void testRecordButtonFunctionality() {
	  	solo.clickOnButton(solo.getString(R.string.recordButtonTextField)); 
	  	assertTrue("Text didn't change", solo.searchText("I'm recording now")); 
	}
	
	public void testRecordFile() {
	    solo.clickOnButton(solo.getString(R.string.recordButtonTextField));
	    assertTrue("Text didn't change", solo.searchText("I'm recording now"));
	    solo.clickOnButton(solo.getString(R.string.stopRecordButtonTextField));
	    assertTrue("Text didn't change", solo.searchText("Stopped Recording"));
	     
	}
	
}
