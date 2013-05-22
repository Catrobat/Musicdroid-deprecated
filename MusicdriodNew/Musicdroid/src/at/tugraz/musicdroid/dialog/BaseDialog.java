package at.tugraz.musicdroid.dialog;

import at.tugraz.musicdroid.R;

import android.app.Dialog;
import android.content.Context;

public class BaseDialog extends Dialog {
	public BaseDialog(Context context) {
		super(context, R.style.CustomMusicdroidDialog);
	}
}
