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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;
import at.tugraz.musicdroid.R;
import at.tugraz.musicdroid.soundmixer.SoundMixer;

public class SoundLenghtDialog extends DialogFragment {
	private NumberPicker pickerMin = null;
	private NumberPicker pickerSec = null;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		LayoutInflater inflater = getActivity().getLayoutInflater();

		// Inflate and set the layout for the dialog
		// Pass null as the parent view because its going in the dialog layout
		View view = inflater.inflate(R.layout.settings_menu, null);
		builder.setView(view);

		builder.setTitle(R.string.settings_default_length_title)
				.setNegativeButton(R.string.settings_button_apply,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								int minutes = pickerMin.getValue();
								int seconds = pickerSec.getValue();

								// PreferenceManager.getInstance().setPreference(PreferenceManager.SOUNDTRACK_DEFAULT_LENGTH_KEY,
								// minutes*60+seconds);
								SoundMixer.getInstance()
										.setSoundTrackLengthAndResizeTracks(
												minutes, seconds);
							}
						})
				.setPositiveButton(R.string.settings_button_discard,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
							}
						});

		AlertDialog dialog = builder.create();

		int currentLength = SoundMixer.getInstance().getSoundMixerLength();

		pickerMin = (NumberPicker) view.findViewById(R.id.numberpicker_min);
		pickerMin.setMinValue(0);
		pickerMin.setMaxValue(9);
		pickerMin.setValue(currentLength / 60);

		pickerSec = (NumberPicker) view.findViewById(R.id.numberpicker_sec);
		pickerSec.setMinValue(0);
		pickerSec.setMaxValue(59);
		pickerSec.setValue(currentLength % 60);

		return dialog;
	}
}
