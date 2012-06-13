package at.tugraz.ist.musicdroid.tests;

import java.io.File;

import org.puredata.core.utils.IoUtils;

import android.content.Intent;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import at.tugraz.ist.musicdroid.PitchDetectionActivity;
import at.tugraz.ist.musicdroid.R;

import com.jayway.android.robotium.solo.Solo;

public class PitchDetectionActivityTest extends ActivityInstrumentationTestCase2<PitchDetectionActivity> {
    private Solo solo;
    
    private final String filesDir = "/sdcard/PitDetActTest/";
    private final String LTAG = "PitchDetectionActivityTest";
	
	public PitchDetectionActivityTest() {
		super("at.ist.tugraz.musicdroid", PitchDetectionActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		
		File dir = getFileDir();

		IoUtils.extractZipResource(getInstrumentation().getContext().getResources().openRawResource(R.raw.pitchdet), dir, true);
		File testwav = new File(dir, "Hammond.wav");
		
		Intent i = new Intent();
		Bundle b = new Bundle();
		b.putString("path", testwav.getAbsolutePath());
    	i.putExtras(b);
		setActivityIntent(i);

		
		solo = new Solo(getInstrumentation(), getActivity());
	}
	
	private File getFileDir() {
		
		return new File(filesDir);
	}

	@Override
	protected void tearDown() throws Exception {
		
		solo.finishOpenedActivities();
		
		File dir = getFileDir();
		
		for (File f : dir.listFiles()) {
			f.delete();
			Log.v(LTAG, "file " + f.getName() + " deleted.");
		}
		
		dir.delete();
		Log.v(LTAG, "folder " + dir.getName() + " deleted.");
	}
	
	public void testIt() {
		
		solo.assertCurrentActivity("PitchDetectionActivity not running", PitchDetectionActivity.class);
	
	}
}
