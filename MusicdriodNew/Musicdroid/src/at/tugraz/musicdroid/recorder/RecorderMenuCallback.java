package at.tugraz.musicdroid.recorder;

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import at.tugraz.musicdroid.R;
import at.tugraz.musicdroid.RecorderActivity;
import at.tugraz.musicdroid.dialog.SoundLenghtDialog;

public class RecorderMenuCallback implements ActionMode.Callback {
		RecorderActivity parent = null;
		private SoundLenghtDialog settingsDialog = null;
	
		public RecorderMenuCallback(RecorderActivity p)
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
        	MenuItem item = mode.getMenu().getItem(0);
        	if(item.getIcon().getConstantState() == parent.getResources().getDrawable(R.drawable.checkbox_unchecked).getConstantState())
        	  AudioHandler.getInstance().setPlayPlayback(false);
        	else
        	  AudioHandler.getInstance().setPlayPlayback(true);
        }

        /** This is called when the action mode is created. This is called by startActionMode() */
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            parent.getMenuInflater().inflate(R.menu.recorder_callback_menu, menu);
            mode.setTitle(R.string.recorder_context_title);

            MenuItem item = mode.getMenu().getItem(0);
        	if(AudioHandler.getInstance().getPlayPlayback())
        		item.setIcon(R.drawable.checkbox_checked);
        	else
        		item.setIcon(R.drawable.checkbox_unchecked);
            
            return true;
        }

        /** This is called when an item in the context menu is selected */
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch(item.getItemId()){
            case R.id.recorder_context_playback:
            	if(item.getIcon().getConstantState() == parent.getResources().getDrawable(R.drawable.checkbox_unchecked).getConstantState())
            	  item.setIcon(R.drawable.checkbox_checked);
            	else
            	  item.setIcon(R.drawable.checkbox_unchecked);
                break;
            }
            return false;
        }
}
