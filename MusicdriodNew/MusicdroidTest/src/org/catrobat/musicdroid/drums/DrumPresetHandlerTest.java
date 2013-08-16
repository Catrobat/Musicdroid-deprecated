package org.catrobat.musicdroid.drums;

import java.io.File;
import java.util.ArrayList;

import android.test.ActivityInstrumentationTestCase2;
import org.catrobat.musicdroid.DrumsActivity;
import org.catrobat.musicdroid.MainActivity;
import org.catrobat.musicdroid.R;
import org.catrobat.musicdroid.types.DrumType;
import org.catrobat.musicdroid.types.SoundType;

import com.jayway.android.robotium.solo.Solo;

public class DrumPresetHandlerTest extends ActivityInstrumentationTestCase2<MainActivity>{
	protected Solo solo = null;
	DrumPresetHandler presetHandler = null;
	DrumsLayout layoutManager = null;
	String filename = "test";
	
	public DrumPresetHandlerTest() {
		super(MainActivity.class);
	}
	
	@Override
	protected void setUp() {
		 solo = new Solo(getInstrumentation(), getActivity());
		 solo.clickOnView(getActivity().findViewById(R.id.btn_add));
		 solo.waitForText(solo.getString(R.string.dialog_add_sound_title), 1, 10000, true);
	     solo.sleep(100);
	     solo.clickOnText(solo.getString(SoundType.DRUMS.getNameResource()));
	     solo.sleep(2000);
	     
	     presetHandler = new DrumPresetHandler();
		 layoutManager = ((DrumsActivity)solo.getCurrentActivity()).getDrumsLayoutManager();
	}
	
	@Override
	protected void tearDown()
	{
		File testFile = new File(presetHandler.path+filename+".xml");
		if(testFile.exists() && testFile.isFile())
		{
			testFile.delete();
		}
		solo.finishOpenedActivities();	
	}
	
	
	public void testWriteDrumLoopToPreset()
	{
		File testFile = new File(presetHandler.path+filename+".xml");
		assertFalse(testFile.exists());
	
		presetHandler.writeDrumLoopToPreset(filename, layoutManager.getDrumSoundRowsArray());
		assertTrue(testFile.exists());
	}
	
	public void testReadPresetByName()
	{
		int changedRowId = 0;
		int toggledPosition = 4;
		
		ArrayList<DrumSoundRow> drumRows = layoutManager.getDrumSoundRowsArray();
		drumRows.get(changedRowId).togglePosition(toggledPosition);
		String expectedName = drumRows.get(changedRowId).getSoundRowName();
		assertTrue(presetHandler.writeDrumLoopToPreset(filename, layoutManager.getDrumSoundRowsArray()));

		DrumPreset preset = presetHandler.readPresetByName(filename);
		
		assertTrue(preset != null);
		
		ArrayList<DrumSoundRow> readDrumRows = preset.getDrumSoundRowsArray();
		assertTrue(readDrumRows.get(changedRowId).getBeatArrayValueAtPosition(toggledPosition) == 1);
		assertTrue(readDrumRows.get(changedRowId).getSoundRowName().equals(expectedName));
		
	}
	
	public void testWriteInvalidPreset()
	{
		ArrayList<DrumSoundRow> drumRows = layoutManager.getDrumSoundRowsArray();
		drumRows.add(new DrumSoundRow(getActivity(), layoutManager, DrumType.BASE_DRUM));
		assertFalse(presetHandler.writeDrumLoopToPreset(filename, drumRows));
	}
	
	
	
	
	
	
}