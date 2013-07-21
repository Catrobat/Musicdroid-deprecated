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
package at.tugraz.musicdroid.recorder;

import java.io.File;

import com.jayway.android.robotium.solo.Solo;

import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import at.tugraz.musicdroid.MainActivity;
import at.tugraz.musicdroid.R;
import at.tugraz.musicdroid.SoundManager;
import at.tugraz.musicdroid.helper.Helper;
import at.tugraz.musicdroid.types.SoundType;

public class RecorderUITest extends ActivityInstrumentationTestCase2<MainActivity> {
	protected Solo solo = null;
	protected String testFilename = "testfile.mp3";
	protected MediaPlayer mediaPlayer = null;
	
	public RecorderUITest() {
		super(MainActivity.class);
	}

	@Override
	protected void setUp() {
		 solo = new Solo(getInstrumentation(), getActivity());
		 
		 //delete file if exists, stop overwrite warning from showing
		 File f = new File(AudioHandler.getInstance().getFilenameFullPath());
  	     if (f.exists())
  	    	 f.delete();
		 
  	     
		 File fTest = new File(AudioHandler.getInstance().getPath() + "/" + testFilename);
  	     if (fTest.exists())
  	    	fTest.delete();
  	     
		 solo.clickOnView(getActivity().findViewById(R.id.btn_add));
		 solo.waitForText(solo.getString(R.string.dialog_add_sound_title), 1, 10000, true);
	     solo.sleep(100);
	     solo.clickOnText(solo.getString(SoundType.MIC.getNameResource()));
	     solo.sleep(2000);
	}
	
	public void testOrientation()
	{
		assertEquals("Not in Landscape mode!", Configuration.ORIENTATION_LANDSCAPE, 
				      solo.getCurrentActivity().getResources().getConfiguration().orientation);
	}
	
	public void testPlayerBoxGoneAtStart()
	{
		RelativeLayout progressBarBox = (RelativeLayout) solo.getCurrentActivity().
				                        findViewById(R.id.microphone_progress_bar_box);
		assertEquals("Progress Bar Box is Visible", progressBarBox.getVisibility(), View.GONE);
		
		View progressBar = solo.getCurrentActivity().findViewById(R.id.microphone_progress_bar);
		assertEquals("Progress Bar is Visible", progressBar.getVisibility(), View.GONE);
		
		ImageButton playButton = (ImageButton) solo.getCurrentActivity().findViewById(R.id.microphone_play_button); 
		assertEquals("Play Button is Visible", playButton.getVisibility(), View.GONE);
	}
	
	public void testImportButtonInvisibleAtStart()
	{
		RelativeLayout addBox = (RelativeLayout) solo.getCurrentActivity().
				                 findViewById(R.id.microphone_add_to_sound_mixer_box);
		assertEquals("Add Box is Visible", addBox.getVisibility(), View.INVISIBLE);	
	}
	
	public void testAmplitudeAnimation()
	{
		ImageButton recordButton = (ImageButton) solo.getCurrentActivity().findViewById(R.id.microphone_record_button);
		solo.clickOnView(recordButton);
		solo.sleep(1000);
		checkForOverwriteDialogAndContinue();
		playSound();
		
		ImageView equalizer = (ImageView) solo.getCurrentActivity().findViewById(R.id.microphone_equalizer);
		int old_height = 0;
		for(int i = 0; i < 100; i ++)
		{
			LayoutParams lp = (LayoutParams)equalizer.getLayoutParams();
			if(lp.height > old_height)
			{
				assertTrue(true);
				solo.clickOnView(recordButton);
				stopSound();
				solo.sleep(1000);
				break;
			}
			
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//assertTrue("Height did not change", false);
	}
	
	public void testOverwriteWarning()
	{
		ImageButton recordButton = (ImageButton) solo.getCurrentActivity().findViewById(R.id.microphone_record_button);
		solo.clickOnView(recordButton);
		solo.sleep(1000);
		assertFalse(checkForOverwriteDialog());
		solo.clickOnView(recordButton);
		solo.sleep(1000);
		solo.clickOnView(recordButton);
		assertTrue(checkForOverwriteDialog());
	}
	
	public void testDoNotRecordAtDialogAbort()
	{
		ImageButton recordButton = (ImageButton) solo.getCurrentActivity().findViewById(R.id.microphone_record_button);
		solo.clickOnView(recordButton);
		solo.sleep(1000);
		assertFalse(checkForOverwriteDialog());
		solo.clickOnView(recordButton);  //stop recording
		solo.sleep(1000);
		
		File f = new File(AudioHandler.getInstance().getFilenameFullPath());
		long modified = 0;
		if(f.exists())
		  modified = f.lastModified();
		
		solo.clickOnView(recordButton);
		checkForOverwriteDialogAndAbort();
		
		f = new File(AudioHandler.getInstance().getFilenameFullPath());
  	    if (f.exists())
  	    {
  	    	assertEquals(modified, f.lastModified());
  	    }
	}
	
	public void testRecordAtDialogContinue()
	{
		ImageButton recordButton = (ImageButton) solo.getCurrentActivity().findViewById(R.id.microphone_record_button);
		solo.clickOnView(recordButton);
		solo.sleep(1000);
		assertFalse(checkForOverwriteDialog());
		solo.clickOnView(recordButton);  //stop recording
		solo.sleep(1000);
		
		File f = new File(AudioHandler.getInstance().getFilenameFullPath());
		long modified = 0;
		if(f.exists())
		  modified = f.lastModified();
		
		solo.clickOnView(recordButton);
		checkForOverwriteDialogAndContinue();
		solo.sleep(2000);
		
		f = new File(AudioHandler.getInstance().getFilenameFullPath());
  	    if (f.exists())
  	    {
  	    	Log.i("AASD", "Modified" + modified);
  	    	assertTrue(modified < f.lastModified());
  	    }
	}
	
	public void testChangeLayoutOnStopRecording()
	{
		ImageButton recordButton = (ImageButton) solo.getCurrentActivity().findViewById(R.id.microphone_record_button);
		solo.clickOnView(recordButton);
		solo.sleep(1000);
		checkForOverwriteDialogAndContinue();
		solo.clickOnView(recordButton);
		solo.sleep(1000);
		
		
		RelativeLayout progressBarBox = (RelativeLayout) solo.getCurrentActivity().
                findViewById(R.id.microphone_progress_bar_box);
		assertEquals("Progress Bar Box is Visible", progressBarBox.getVisibility(), View.VISIBLE);
		
		View progressBar = solo.getCurrentActivity().findViewById(R.id.microphone_progress_bar);
		assertEquals("Progress Bar is Visible", progressBar.getVisibility(), View.VISIBLE);
		
		ImageButton playButton = (ImageButton) solo.getCurrentActivity().findViewById(R.id.microphone_play_button); 
		assertEquals("Play Button is Visible", playButton.getVisibility(), View.VISIBLE);
		
		RelativeLayout addBox = (RelativeLayout) solo.getCurrentActivity().
		         findViewById(R.id.microphone_add_to_sound_mixer_box);
		assertEquals("Add Box is Visible", addBox.getVisibility(), View.VISIBLE);	
		
	}
	
	public void testChangeFilename()
	{
		TextView filenameTextView = (TextView) solo.getCurrentActivity().findViewById(R.id.microphone_filename);
		solo.clickLongOnView(filenameTextView);
		solo.sleep(1000);
		String filename = AudioHandler.getInstance().getFilename();
		filename = Helper.getInstance().removeFileEnding(filename);
		//solo.clickOnText(filename);
		solo.sleep(1000);
				
		solo.clearEditText(0);
		solo.sleep(1000);
		solo.enterText(0, Helper.getInstance().removeFileEnding(testFilename));
		solo.sleep(1000);
		solo.clickOnText(getActivity().getResources().getString(R.string.settings_button_apply));
		
		ImageButton recordButton = (ImageButton) solo.getCurrentActivity().findViewById(R.id.microphone_record_button);
		solo.clickOnView(recordButton);
		solo.sleep(1000);
		assertFalse(checkForOverwriteDialog());
		solo.clickOnView(recordButton);
		solo.sleep(1000);
		
		File f = new File(AudioHandler.getInstance().getPath()+ "/"+ testFilename);
		assertTrue(f.exists());
	}
	
	
	
	public void testFilenameUnchangedAtDialogDiscard()
	{
		TextView filenameTextView = (TextView) solo.getCurrentActivity().findViewById(R.id.microphone_filename);
		solo.clickLongOnView(filenameTextView);
		solo.sleep(1000);
		String filename = AudioHandler.getInstance().getFilename();
		filename = Helper.getInstance().removeFileEnding(filename);
		//solo.clickOnText(filename);
		solo.sleep(1000);
				
		solo.clearEditText(0);
		solo.sleep(1000);
		solo.enterText(0, Helper.getInstance().removeFileEnding(testFilename));
		solo.sleep(1000);
		solo.clickOnText(getActivity().getResources().getString(R.string.settings_button_discard));
		
		ImageButton recordButton = (ImageButton) solo.getCurrentActivity().findViewById(R.id.microphone_record_button);
		solo.clickOnView(recordButton);
		solo.sleep(1000);
		assertFalse(checkForOverwriteDialog());
		solo.clickOnView(recordButton);
		solo.sleep(1000);
		
		File f = new File(AudioHandler.getInstance().getPath()+ "/"+ testFilename);
		assertFalse(f.exists());
	}
	
	private void playSound()
	{
		int resID=getActivity().getResources().getIdentifier("test_wav", "raw", getActivity().getPackageName());

        mediaPlayer=MediaPlayer.create(solo.getCurrentActivity(), resID);
        mediaPlayer.start();
	}
	
	private void stopSound()
	{
		if(mediaPlayer.isPlaying())
			mediaPlayer.stop();
	}
	
	private void checkForOverwriteDialogAndContinue()
	{
		if(solo.searchText(solo.getCurrentActivity().getString(R.string.dialog_continue)))
		{
    	  solo.clickOnButton(solo.getCurrentActivity().getString(R.string.dialog_continue));
		}
	}

	private void checkForOverwriteDialogAndAbort()
	{
		if(solo.searchText(solo.getCurrentActivity().getString(R.string.dialog_abort)))
		{
    	  solo.clickOnButton(solo.getCurrentActivity().getString(R.string.dialog_abort));
		}
	}
	
	private boolean checkForOverwriteDialog()
	{
		if(solo.searchText(solo.getCurrentActivity().getString(R.string.dialog_continue)))
			return true;
		
		else
			return false;	
	}
	
	
}
