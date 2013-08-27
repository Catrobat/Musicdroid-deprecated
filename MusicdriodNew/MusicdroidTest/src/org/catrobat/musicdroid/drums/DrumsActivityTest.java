package org.catrobat.musicdroid.drums;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.jayway.android.robotium.solo.Solo;

import org.catrobat.musicdroid.DrumsActivity;
import org.catrobat.musicdroid.MainActivity;
import org.catrobat.musicdroid.R;
import org.catrobat.musicdroid.types.SoundType;

public class DrumsActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
	protected Solo solo = null;
	protected LayoutDrums layoutManager = null;
	protected DrumSoundRow row = null;
	protected LayoutDrumSoundRow layout = null;

	public DrumsActivityTest() {
		super(MainActivity.class);
	}

	@Override
	protected void setUp() {
		solo = new Solo(getInstrumentation(), getActivity());
		enterDrumActivity();
	}

	@Override
	protected void tearDown() {
		solo.finishOpenedActivities();
	}

	public void testOnBackPressedYes() {
		testOnBackPressedClickOnSoundRow();
		solo.clickOnText(getActivity().getResources().getString(R.string.yes));
		solo.sleep(3000);
		assertTrue(solo.getCurrentActivity().getClass() == MainActivity.class);
	}

	public void testOnBackPressedNo() {
		testOnBackPressedClickOnSoundRow();
		solo.clickOnText(getActivity().getResources().getString(R.string.no));
		solo.sleep(3000);
		assertTrue(solo.getCurrentActivity().getClass() == DrumsActivity.class);
	}

	public void testOnBackPressedClickOnSoundRow() {
		if (solo.getCurrentActivity().getClass() == MainActivity.class)
			enterDrumActivity();

		DrumButton testButton = (DrumButton) layout.findViewById(R.id.drum_button_1_2);
		assertTrue(testButton
				.getDrawable()
				.getConstantState()
				.equals(getActivity().getResources().getDrawable(R.drawable.drum_button_unclicked_dark)
						.getConstantState()));
		solo.clickOnView(testButton);
		solo.sleep(1000);

		solo.goBack();
		solo.sleep(1000);
		assertTrue(solo.getCurrentActivity().getClass() == DrumsActivity.class);
	}

	public void testOnBackPressedChangeSpinnerItem() {
		if (solo.getCurrentActivity().getClass() == MainActivity.class)
			enterDrumActivity();

		RelativeLayout spinnerBox = (RelativeLayout) layout.findViewById(R.id.drum_row_descriptor_box);
		Spinner spinner = (Spinner) layout.findViewById(R.id.drum_sound_spinner);

		assertFalse(spinner.isEnabled());

		solo.clickLongOnView(spinnerBox);
		solo.sleep(1000);
		solo.scrollToTop();
		solo.clickOnText(getActivity().getResources().getString(R.string.tambourine));
		solo.sleep(1000);

		solo.goBack();
		solo.sleep(1000);
		assertTrue(solo.getCurrentActivity().getClass() == DrumsActivity.class);
	}

	private void enterDrumActivity() {
		solo.clickOnView(getActivity().findViewById(R.id.btn_add));
		solo.waitForText(solo.getString(R.string.dialog_add_sound_title), 1, 10000, true);
		solo.sleep(100);
		solo.clickOnText(solo.getString(SoundType.DRUMS.getNameResource()));
		solo.sleep(1000);

		layoutManager = ((DrumsActivity) solo.getCurrentActivity()).getDrumsLayoutManager();
		row = layoutManager.getDrumSoundRowsArray().get(0);
		layout = row.getLayout();
	}
}
