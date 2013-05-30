package at.tugraz.musicdroid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;
import android.widget.Toast;
import at.tugraz.musicdroid.soundmixer.SoundMixer;

public class MenuFileActivity extends FragmentActivity { 
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_item_load_song:
			break;
		case R.id.menu_item_new_song:
			onNewSong();
			break;
		case R.id.menu_item_save_song:;
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}
	
	private void onNewSong()
	{
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder
				.setMessage(R.string.dialog_warning_new_song)
				.setCancelable(true)
				.setPositiveButton(R.string.yes,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								createNewSoundMixer();
							}
						})
				.setNegativeButton(R.string.no,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
								Toast.makeText(getApplicationContext(), R.string.save_suggestion, Toast.LENGTH_LONG).show();
							}
						});
		AlertDialog alertNewImage = alertDialogBuilder.create();
		alertNewImage.show();
	}
    
	private void createNewSoundMixer()
	{
		SoundMixer.getInstance().resetSoundMixer();
	}


}
