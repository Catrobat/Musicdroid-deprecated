package org.catrobat.musicdroid.drums;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import com.jayway.android.robotium.solo.Solo;

import android.test.ActivityInstrumentationTestCase2;
import org.catrobat.musicdroid.MainActivity;
import org.catrobat.musicdroid.DrumsActivity;
import org.catrobat.musicdroid.R;
import org.catrobat.musicdroid.types.DrumType;
import org.catrobat.musicdroid.types.SoundType;

public class DrumsLayoutManagerTest  extends ActivityInstrumentationTestCase2<MainActivity>{
	protected Solo solo = null;
	protected DrumsLayout layoutManager = null;
	
	public DrumsLayoutManagerTest() {
		super(MainActivity.class);
	}
	
	@Override
	protected void setUp() {
		 solo = new Solo(getInstrumentation(), getActivity());
		 solo.sleep(2000);
		 solo.clickOnView(getActivity().findViewById(R.id.btn_add));
		 solo.waitForText(solo.getString(R.string.dialog_add_sound_title), 1, 5000, true);
	     solo.sleep(1000);
	     solo.clickOnText(solo.getString(SoundType.DRUMS.getNameResource()));
	     solo.sleep(2000);
	     
	     layoutManager = ((DrumsActivity)solo.getCurrentActivity()).getDrumsLayoutManager();
	}
	
	@Override
	protected void tearDown()
	{
		solo.finishOpenedActivities();
	}	
	
	public void testMapIsPopulatedCorrectly()
	{
		HashMap<String, DrumType> stringToDrumTypeMap = layoutManager.getStringToDrumTypeMap();
		assertTrue(stringToDrumTypeMap.containsKey(getActivity().getResources().getString(DrumType.BASE_DRUM.getNameResource())));
		assertTrue(stringToDrumTypeMap.containsKey(getActivity().getResources().getString(DrumType.SNARE_DRUM.getNameResource())));
		assertTrue(stringToDrumTypeMap.containsKey(getActivity().getResources().getString(DrumType.SNARE_DRUM_HARD.getNameResource())));
		assertTrue(stringToDrumTypeMap.containsKey(getActivity().getResources().getString(DrumType.HIGH_HAT_CLOSED.getNameResource())));
		assertTrue(stringToDrumTypeMap.containsKey(getActivity().getResources().getString(DrumType.HIGH_HAT_OPEN.getNameResource())));
		assertTrue(stringToDrumTypeMap.containsKey(getActivity().getResources().getString(DrumType.TAMBOURINE.getNameResource())));
		assertTrue(stringToDrumTypeMap.containsKey(getActivity().getResources().getString(DrumType.TOM_HIGH.getNameResource())));
		assertTrue(stringToDrumTypeMap.containsKey(getActivity().getResources().getString(DrumType.TOM_LOW.getNameResource())));
		assertTrue(stringToDrumTypeMap.containsKey(getActivity().getResources().getString(DrumType.CRASH_ONE.getNameResource())));
		assertTrue(stringToDrumTypeMap.containsKey(getActivity().getResources().getString(DrumType.CRASH_TWO.getNameResource())));
	}
	
	public void testMappingIsCorrect()
	{
		HashMap<String, DrumType> stringToDrumTypeMap = layoutManager.getStringToDrumTypeMap();
		DrumType type = stringToDrumTypeMap.get(getActivity().getResources().getString(DrumType.BASE_DRUM.getNameResource()));
		assertTrue(type.getClass() == DrumType.BASE_DRUM.getClass());
		
		type = stringToDrumTypeMap.get(getActivity().getResources().getString(DrumType.SNARE_DRUM.getNameResource()));
		assertTrue(type.getClass() == DrumType.SNARE_DRUM.getClass());
		
		type = stringToDrumTypeMap.get(getActivity().getResources().getString(DrumType.SNARE_DRUM_HARD.getNameResource()));
		assertTrue(type.getClass() == DrumType.SNARE_DRUM_HARD.getClass());
		
		type = stringToDrumTypeMap.get(getActivity().getResources().getString(DrumType.HIGH_HAT_CLOSED.getNameResource()));
		assertTrue(type.getClass() == DrumType.HIGH_HAT_CLOSED.getClass());
		
		type = stringToDrumTypeMap.get(getActivity().getResources().getString(DrumType.HIGH_HAT_OPEN.getNameResource()));
		assertTrue(type.getClass() == DrumType.HIGH_HAT_OPEN.getClass());
		
		type = stringToDrumTypeMap.get(getActivity().getResources().getString(DrumType.TAMBOURINE.getNameResource()));
		assertTrue(type.getClass() == DrumType.TAMBOURINE.getClass());
		
		type = stringToDrumTypeMap.get(getActivity().getResources().getString(DrumType.TOM_HIGH.getNameResource()));
		assertTrue(type.getClass() == DrumType.TOM_HIGH.getClass());
		
		type = stringToDrumTypeMap.get(getActivity().getResources().getString(DrumType.TOM_LOW.getNameResource()));
		assertTrue(type.getClass() == DrumType.TOM_LOW.getClass());
		
		type = stringToDrumTypeMap.get(getActivity().getResources().getString(DrumType.CRASH_ONE.getNameResource()));
		assertTrue(type.getClass() == DrumType.CRASH_ONE.getClass());
		
		type = stringToDrumTypeMap.get(getActivity().getResources().getString(DrumType.CRASH_TWO.getNameResource()));
		assertTrue(type.getClass() == DrumType.CRASH_TWO.getClass());
	}

}
