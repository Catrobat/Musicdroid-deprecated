package org.catrobat.musicdroid.drums;

import com.jayway.android.robotium.solo.Solo;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import org.catrobat.musicdroid.MainActivity;
import org.catrobat.musicdroid.DrumsActivity;
import org.catrobat.musicdroid.R;
import org.catrobat.musicdroid.types.DrumType;
import org.catrobat.musicdroid.types.SoundType;

public class DrumSoundRowTest  extends ActivityInstrumentationTestCase2<MainActivity>{
	protected Solo solo = null;
	DrumsLayout layoutManager = null;
	DrumSoundRow row = null;
	DrumSoundRowLayout layout = null;
	
	public DrumSoundRowTest() {
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
	     

		 layoutManager = ((DrumsActivity)solo.getCurrentActivity()).getDrumsLayoutManager();
		 row = layoutManager.getDrumSoundRowsArray().get(0);
		 layout = row.getLayout();
	}
	
	@Override
	protected void tearDown()
	{
		solo.finishOpenedActivities();
	}	
	
	public void testBeatButtonChangesOnClick()
	{	
		assertTrue(row != null);
		
		DrumButton testButton = (DrumButton)layout.findViewById(R.id.drum_button_1_2);
		assertTrue(
				testButton.getDrawable().getConstantState()
				.equals(getActivity().getResources().getDrawable(R.drawable.drum_button_unclicked_dark).getConstantState()));
		solo.clickOnView(testButton);
		solo.sleep(1000);
		assertTrue(
				testButton.getDrawable().getConstantState()
				.equals(getActivity().getResources().getDrawable(R.drawable.drum_button_clicked).getConstantState()));
		solo.clickOnView(testButton);
		solo.sleep(1000);
		assertTrue(
				testButton.getDrawable().getConstantState()
				.equals(getActivity().getResources().getDrawable(R.drawable.drum_button_unclicked_dark).getConstantState()));
				
	}
	
	
	public void testClearButton()
	{
		DrumButton testButton = (DrumButton)layout.findViewById(R.id.drum_button_1_2);
		solo.clickOnView(testButton);
		solo.sleep(1000);
		assertTrue(
				testButton.getDrawable().getConstantState()
				.equals(getActivity().getResources().getDrawable(R.drawable.drum_button_clicked).getConstantState()));

		View clear = solo.getView(R.id.drums_context_clear_preset);
		solo.clickOnView(clear);
		
		layoutManager = ((DrumsActivity)solo.getCurrentActivity()).getDrumsLayoutManager();
		row = layoutManager.getDrumSoundRowsArray().get(0);
		layout = row.getLayout();
		testButton = (DrumButton)layout.findViewById(R.id.drum_button_1_2);
		solo.clickOnView(testButton);
		solo.sleep(1000);
		assertTrue(
				testButton.getDrawable().getConstantState()
				.equals(getActivity().getResources().getDrawable(R.drawable.drum_button_unclicked_dark).getConstantState()));
	}
	
	
	public void testSpinnerTextChangesOnLongClick()
	{
		assertTrue(row != null && layout != null);
		RelativeLayout spinnerBox = (RelativeLayout) layout.findViewById(R.id.drum_row_descriptor_box);
		Spinner spinner = (Spinner) layout.findViewById(R.id.drum_sound_spinner);
		
		String textBeforeSpinnerAction = (String) spinner.getSelectedItem();
		
		assertFalse(spinner.isEnabled());
		
		solo.clickLongOnView(spinnerBox);
		solo.sleep(1000);
		solo.scrollToTop();
		solo.clickOnText(getActivity().getResources().getString(R.string.tambourine));
		solo.sleep(1000);
		
		assertFalse(textBeforeSpinnerAction.equals((String) spinner.getSelectedItem()));
		assertTrue(((String)spinner.getSelectedItem()).equals(getActivity().getResources().getString(R.string.tambourine)));
	}
	
	
	public void testSoundpoolIdChangesOnSpinnerClick()
	{
		assertTrue(row != null && layout != null);
		RelativeLayout spinnerBox = (RelativeLayout) layout.findViewById(R.id.drum_row_descriptor_box);
		Spinner spinner = (Spinner) layout.findViewById(R.id.drum_sound_spinner);
		
		assertFalse(spinner.isEnabled());
		int oldSoundpoolId = row.getSoundPoolId();
		
		solo.clickLongOnView(spinnerBox);
		solo.sleep(1000);
		solo.scrollToTop();
		solo.clickOnText(getActivity().getResources().getString(DrumType.TAMBOURINE.getNameResource()));
		solo.sleep(5000);
		
		assertTrue(row.getSoundPoolId() != oldSoundpoolId);
	}
	
	
	
	public void testBeatArray()
	{
		int[] beatArray = row.getBeatArray();
		assertTrue(!contains(beatArray, 1));
		
		DrumButton testButton = (DrumButton)layout.findViewById(R.id.drum_button_1_2);
		solo.clickOnView(testButton);
		solo.sleep(1000);
		
		beatArray = row.getBeatArray();
		assertTrue(contains(beatArray, 1));
		
		solo.clickOnView(testButton);
		solo.sleep(1000);
		
		beatArray = row.getBeatArray();
		assertTrue(!contains(beatArray, 1));
	}
	
	
	private boolean contains(int[] array, int value)
	{
		for(int i = 0; i < array.length; i++)
		{
			if(array[i] == value)
				return true;
		}
		return false;
	}
	
}
