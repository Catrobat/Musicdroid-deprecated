package at.tugraz.ist.musicdroid.tests;

import com.jayway.android.robotium.solo.Solo;

import android.test.ActivityInstrumentationTestCase2;
import at.tugraz.ist.musicdroid.MusicdroidActivity;
import at.tugraz.ist.musicdroid.PlaySoundActivity;
import at.tugraz.ist.musicdroid.R;

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
		super.tearDown();
		try {
			solo.finalize(); 
			
		} catch(Throwable e) {
			e.printStackTrace();
		}
		getActivity().finish(); 
	}

	public void testfunktion(){
		//assertTrue(“”,solo....);
		PlaySoundActivity soundManager = PlaySoundActivity.getInstance();
		assertNull("SoundManager could not be initialized", soundManager);

	}
}

