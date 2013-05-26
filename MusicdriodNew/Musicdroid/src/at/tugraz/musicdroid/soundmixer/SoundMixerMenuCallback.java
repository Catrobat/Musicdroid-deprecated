package at.tugraz.musicdroid.soundmixer;

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import at.tugraz.musicdroid.MainActivity;
import at.tugraz.musicdroid.R;
import at.tugraz.musicdroid.dialog.SoundLenghtDialog;

public class SoundMixerMenuCallback implements ActionMode.Callback {
		MainActivity parent = null;
		private SoundLenghtDialog settingsDialog = null;
	
		public SoundMixerMenuCallback(MainActivity p)
		{
			parent = p;
			settingsDialog = new SoundLenghtDialog();
		}
	
        /** Invoked whenever the action mode is shown. This is invoked immediately after onCreateActionMode */
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        /** Called when user exits action mode */
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            SoundMixer.getInstance().enableUnselectedViews();
        }

        /** This is called when the action mode is created. This is called by startActionMode() */
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            parent.getMenuInflater().inflate(R.menu.sound_mixer_menu, menu);
            return true;
        }

        /** This is called when an item in the context menu is selected */
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch(item.getItemId()){
            case R.id.soundmixer_context_bpm:
                mode.finish();    // Automatically exists the action mode, when the user selects this action
                break;
            case R.id.soundmixer_context_length:
            	settingsDialog.show(parent.getFragmentManager(), null);
				mode.finish();
                break;
            }
            return false;
        }
}
