package at.tugraz.ist.musicdroid.test;

import com.jayway.android.robotium.solo.Solo;

import android.test.ActivityInstrumentationTestCase2;
import at.tugraz.ist.musicdroid.MusicdroidActivity;
import at.tugraz.ist.musicdroid.R;

public class MusicdroidActivityTest extends ActivityInstrumentationTestCase2<MusicdroidActivity> { //aktivity gibt an, wo test startet
    private
     Solo solo; 
	
	public MusicdroidActivityTest() {
		super("at.tugraz.ist.musicdroid", MusicdroidActivity.class);
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
	
	public void testMainScreen(){
	  //assertTrue("TextView Load Project not found", solo.searchText(solo.getString(R.string.load_project))); 
	assertTrue("Open Sound-Recorder Button not found", solo.searchButton(solo.getString(R.string.load_sound_recorder), true));
	assertTrue("Open Sound-Player Button not found", solo.searchButton(solo.getString(R.string.load_sound_player), true));
	assertTrue("New Project Button not found", solo.searchButton(solo.getString(R.string.new_project), true));
	assertTrue("Open Project Button not found", solo.searchButton(solo.getString(R.string.open_soundfile), true));
	}

}
