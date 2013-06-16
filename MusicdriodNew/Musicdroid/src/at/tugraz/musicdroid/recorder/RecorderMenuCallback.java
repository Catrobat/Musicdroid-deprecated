package at.tugraz.musicdroid.recorder;

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import at.tugraz.musicdroid.R;
import at.tugraz.musicdroid.RecorderActivity;
import at.tugraz.musicdroid.dialog.MetronomQuickSettingsDialog;
import at.tugraz.musicdroid.dialog.SoundLenghtDialog;
import at.tugraz.musicdroid.preferences.PreferenceManager;

public class RecorderMenuCallback implements ActionMode.Callback {
		RecorderActivity parent = null;
		private MetronomQuickSettingsDialog metronomDialog = null;
	
		public RecorderMenuCallback(RecorderActivity p)
		{
			parent = p;
			metronomDialog = new MetronomQuickSettingsDialog();
		}
	
        /** Invoked whenever the action mode is shown. This is invoked immediately after onCreateActionMode */
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        /** Called when user exits action mode */
        @Override
        public void onDestroyActionMode(ActionMode mode) {
        	MenuItem item = mode.getMenu().getItem(1);
        	if(item.getIcon().getConstantState() == parent.getResources().getDrawable(R.drawable.checkbox_unchecked).getConstantState())
        	  PreferenceManager.getInstance().setPreference(PreferenceManager.PLAY_PLAYBACK_KEY, 0);
        	else
        		PreferenceManager.getInstance().setPreference(PreferenceManager.PLAY_PLAYBACK_KEY, 1);
        }

        /** This is called when the action mode is created. This is called by startActionMode() */
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            parent.getMenuInflater().inflate(R.menu.recorder_callback_menu, menu);
            mode.setTitle(R.string.recorder_context_title);

            MenuItem item = mode.getMenu().getItem(1);
        	if(PreferenceManager.getInstance().getPreference(PreferenceManager.PLAY_PLAYBACK_KEY) == 1)
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
            case R.id.recorder_context_bpm:
            	metronomDialog.show(parent.getFragmentManager(), null);
                mode.finish();   
            }
            return false;
        }
}
