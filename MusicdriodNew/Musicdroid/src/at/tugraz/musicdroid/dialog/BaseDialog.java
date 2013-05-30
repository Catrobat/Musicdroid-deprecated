package at.tugraz.musicdroid.dialog;

import android.app.Dialog;
import android.content.Context;
import at.tugraz.musicdroid.R;

public class BaseDialog extends Dialog {
	public BaseDialog(Context context) {
		super(context, R.style.CustomMusicdroidDialog);
	}
}
