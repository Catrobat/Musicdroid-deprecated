package org.catrobat.musicdroid.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.catrobat.musicdroid.R;
import org.catrobat.musicdroid.tools.InputValidator;

abstract class CheckFilenameBaseDialog extends DialogFragment {
	protected EditText editText = null;

	@Override
	public void onStart() {
		super.onStart();
		AlertDialog d = (AlertDialog) getDialog();
		if (d != null) {
			Button positiveButton = d.getButton(DialogInterface.BUTTON_NEGATIVE);
			positiveButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					String filename = editText.getText() + "";

					if (InputValidator.isValidFilenameWithoutPath(filename)) {
						//            	     ((DrumsActivity)getActivity()).saveCurrentPreset(filename);
						Log.i("CheckFilenameBaseDialog", "Filename = " + filename);
						performOnClick(filename);
						dismiss();
					} else
						Toast.makeText(getActivity(), R.string.invalid_filename, Toast.LENGTH_LONG).show();
				}
			});
		}
	}

	abstract void performOnClick(String str);
}
