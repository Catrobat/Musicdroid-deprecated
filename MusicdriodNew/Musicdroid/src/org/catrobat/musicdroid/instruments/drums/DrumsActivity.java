/**
 *  Catroid: An on-device visual programming system for Android devices
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
 *  http://developer.catrobat.org/license_additional_term
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU Affero General Public License for more details.
 *  
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.catrobat.musicdroid.instruments.drums;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import org.catrobat.musicdroid.R;
import org.catrobat.musicdroid.dialog.ChangeBeatsPerMinuteDialog;
import org.catrobat.musicdroid.dialog.ExportDrumSoundDialog;
import org.catrobat.musicdroid.dialog.listener.ChangeBeatsPerMinuteDialogListener;
import org.catrobat.musicdroid.dialog.listener.ExportDrumSoundDialogListener;
import org.catrobat.musicdroid.instruments.Instrument;
import org.catrobat.musicdroid.types.SpecialEvent;

/**
 * @author musicdroid
 * 
 */
public class DrumsActivity extends Instrument {

	private DrumTrackView drumTrackView;
	private DrumView drumView;
	private int beatsPerMinute = ChangeBeatsPerMinuteDialog.BEATS_PER_MINUTE_DEFAULT;
	private ExportDrumSoundDialog exportDrumSoundDialog = null;
	private ChangeBeatsPerMinuteDialog changeBeatsPerMinuteDialog = null;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.drums_menu, menu);
		return super.onCreateOptionsMenu(menu);

	}

	public DrumTrackView getDrumTrackView() {
		return drumTrackView;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
			case R.id.drum_settings_btn:
				//DrumMenuSettingsCallback drumMenuSettingsCallback = new DrumMenuSettingsCallback(this);
				//startActionMode(drumMenuSettingsCallback);
				break;
			case R.id.drum_track_delete_btn:
				drumTrackView.clearRows();
				break;
			case R.id.drum_track_export_btn:
				exportDrumSoundDialog.show(getFragmentManager(), null);
				break;
			case R.id.drum_track_bpm_btn:
				changeBeatsPerMinuteDialog.show(getFragmentManager(), null);
				break;
		}
		return true;

	}

	public void returnToMainActivity(int num_loops) {
		Intent returnIntent = new Intent();
		returnIntent.putExtra("drums_filename", "temp.xml");
		returnIntent.putExtra("num_loops", num_loops);
		//returnIntent.putExtra("edit_mode", editMode);
		setResult(RESULT_OK, returnIntent);
		finish();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		drumTrackView = new DrumTrackView(this);
		drumView = new DrumView(this);

		exportDrumSoundDialog = new ExportDrumSoundDialog(new ExportDrumSoundDialogListener(this));
		changeBeatsPerMinuteDialog = new ChangeBeatsPerMinuteDialog(new ChangeBeatsPerMinuteDialogListener(this));

		LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,
				1.0f);

		HorizontalScrollView horizontalTrackScrollView = new HorizontalScrollView(this);

		horizontalTrackScrollView.setLayoutParams(layoutParams);
		drumView.setLayoutParams(layoutParams);
		drumTrackView.setLayoutParams(layoutParams);

		horizontalTrackScrollView.addView(drumTrackView);

		LinearLayout mainLayout = new LinearLayout(this);

		mainLayout.addView(horizontalTrackScrollView);
		mainLayout.addView(drumView);

		mainLayout.setOrientation(1);
		setContentView(mainLayout);
	}

	@Override
	protected void doAfterAddAnEvent(SpecialEvent drumEvent) {
	}

	public void addDrumEvent(DrumEvent drumEvent) {
		drumTrackView.updateView(drumEvent);
	}

	/**
	 * @return the beatsPerMinute
	 */
	public int getBeatsPerMinute() {
		return beatsPerMinute;
	}

	/**
	 * @param beatsPerMinute
	 *            the beatsPerMinute to set
	 */
	public void setBeatsPerMinute(int beatsPerMinute) {
		this.beatsPerMinute = beatsPerMinute;
	}

}
