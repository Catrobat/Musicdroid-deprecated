/*******************************************************************************
 * Catroid: An on-device visual programming system for Android devices
 *  Copyright (C) 2010-2013 The Catrobat Team
 *  (<http://developer.catrobat.org/credits>)
 *  
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 * 
 *  An additional term exception under section 7 of the GNU Affero
 *  General Public License, version 3, is available at
 *  http://www.catroid.org/catroid/licenseadditionalterm
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU Affero General Public License for more details.
 * 
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package org.catrobat.musicdroid.menutest;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.ImageButton;
import org.catrobat.musicdroid.MainActivity;
import org.catrobat.musicdroid.R;
import org.catrobat.musicdroid.soundmixer.SoundMixer;
import org.catrobat.musicdroid.soundmixer.Statusbar;
import org.catrobat.musicdroid.types.SoundType;

import com.jayway.android.robotium.solo.Solo;

public class StatusbarMainMenuTest extends ActivityInstrumentationTestCase2<MainActivity> {
	protected Solo solo = null;
	protected ImageButton playImageButton = null;
	protected ImageButton rewindImageButton = null;
	protected UITestHelper helper = null;
	
	public StatusbarMainMenuTest() {
		super(MainActivity.class);
	}
	
	@Override
	protected void setUp() {
		solo = new Solo(getInstrumentation(), getActivity());
		helper = new UITestHelper(solo, getActivity());
		playImageButton = (ImageButton) getActivity().findViewById(R.id.btn_play);
		rewindImageButton = (ImageButton) getActivity().findViewById(R.id.btn_rewind);
	}
	
	@Override
	protected void tearDown()
	{
		solo.finishOpenedActivities();
	}
	
	public void testButtonChangesOnClick()
	{
		helper.addDummyTrack(this);
		solo.clickOnView(playImageButton);
		solo.sleep(1000);
		assertFalse(Statusbar.getInstance().getDisplayPlayButton());
		solo.clickOnView(playImageButton);
		solo.sleep(1000);
		assertTrue(Statusbar.getInstance().getDisplayPlayButton());
	}
	
	public void testRewindButtonAppearsOnPlayButtonClick()
	{
		helper.addDummyTrack(this);
		assertTrue(rewindImageButton.getVisibility() == View.INVISIBLE);
		solo.clickOnView(playImageButton);
		solo.sleep(5000);
		solo.clickOnView(playImageButton);
		assertTrue(rewindImageButton.getVisibility() == View.VISIBLE);
	}
	
	public void testRewindButton()
	{
		testRewindButtonAppearsOnPlayButtonClick();
		assertTrue(SoundMixer.getInstance().getStopPointFromEventHandler() > 4);
		solo.clickOnView(rewindImageButton);
		assertTrue(SoundMixer.getInstance().getStopPointFromEventHandler() == 0);	
	}
	
	

}
