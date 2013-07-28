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
package at.tugraz.musicdroid.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import at.tugraz.musicdroid.R;
import at.tugraz.musicdroid.preferences.PreferenceManager;

public class MetronomQuickSettingsDialog extends DialogFragment implements
		OnSeekBarChangeListener {
	private SeekBar bpm = null;
	private Spinner state = null;
	private TextView bpmText = null;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		LayoutInflater inflater = getActivity().getLayoutInflater();

		View view = inflater.inflate(R.layout.metronom_quick_settings_menu,
				null);
		builder.setView(view);

		builder.setTitle(R.string.settings_metronom_quick_title)
				.setNegativeButton(R.string.settings_button_apply,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								int bpmVal = bpm.getProgress() + 54;
								PreferenceManager.getInstance().setPreference(
										PreferenceManager.METRONOM_BPM_KEY,
										bpmVal);
								PreferenceManager
										.getInstance()
										.setPreference(
												PreferenceManager.METRONOM_VISUALIZATION_KEY,
												state.getSelectedItemPosition());
							}
						})
				.setPositiveButton(R.string.settings_button_discard,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// User cancelled the dialog
							}
						});

		AlertDialog dialog = builder.create();

		bpmText = (TextView) view.findViewById(R.id.bpm_text);
		bpmText.setText(PreferenceManager.getInstance().getPreference(
				PreferenceManager.METRONOM_BPM_KEY)
				+ " BPM");

		bpm = (SeekBar) view.findViewById(R.id.seekbar_bpm);
		bpm.setMax(150);
		bpm.setProgress(PreferenceManager.getInstance().getPreference(
				PreferenceManager.METRONOM_BPM_KEY) - 54);
		bpm.incrementProgressBy(2);
		bpm.setOnSeekBarChangeListener(this);

		state = (Spinner) view.findViewById(R.id.states_spinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				getActivity(), R.array.metronom_states_array,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		state.setAdapter(adapter);
		state.setSelection(PreferenceManager.getInstance().getPreference(
				PreferenceManager.METRONOM_VISUALIZATION_KEY));

		return dialog;
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.i("MetronomQuickSettings", "RESUME");
		bpm.setProgress(PreferenceManager.getInstance().getPreference(
				PreferenceManager.METRONOM_BPM_KEY) - 54);
		bpmText.setText(PreferenceManager.getInstance().getPreference(
				PreferenceManager.METRONOM_BPM_KEY)
				+ " BPM");
		state.setSelection(PreferenceManager.getInstance().getPreference(
				PreferenceManager.METRONOM_VISUALIZATION_KEY));
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		bpmText.setText(progress + 54 + " BPM");
		Log.i("MetronomQuickSettings", "Progress = " + progress + 54);
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}
}
