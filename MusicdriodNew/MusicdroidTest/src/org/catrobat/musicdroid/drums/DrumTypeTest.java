package org.catrobat.musicdroid.drums;

import java.util.ArrayList;

import android.test.ActivityInstrumentationTestCase2;
import org.catrobat.musicdroid.MainActivity;
import org.catrobat.musicdroid.types.DrumType;

public class DrumTypeTest extends ActivityInstrumentationTestCase2<MainActivity> {
	public DrumTypeTest() {
		super(MainActivity.class);
	}	
	
	@Override
	protected void tearDown()
	{
		getActivity().finish();
	}
	
	public void testGetTypeArray()
	{
		ArrayList<String> list = DrumType.getTypeArray(getActivity());
		DrumType[] typeArray = DrumType.class.getEnumConstants();
		assertTrue(typeArray != null);
		
		for(int i = 0; i < typeArray.length; i++)
		{
			assertTrue(list.contains(getActivity().getResources().getString(typeArray[i].getNameResource())));
		}
		
	}
}
