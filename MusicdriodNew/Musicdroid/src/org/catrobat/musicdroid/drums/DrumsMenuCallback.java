package org.catrobat.musicdroid.drums;

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import org.catrobat.musicdroid.DrumsActivity;
import org.catrobat.musicdroid.R;
import org.catrobat.musicdroid.dialog.MetronomQuickSettingsDialog;
import org.catrobat.musicdroid.dialog.OpenFileDialog;
import org.catrobat.musicdroid.dialog.SavePresetDialog;
import org.catrobat.musicdroid.dialog.listener.LoadFileDialogListener;

public class DrumsMenuCallback implements ActionMode.Callback {
		private DrumsActivity parent = null;
		private MetronomQuickSettingsDialog metronomDialog = null;
		private SavePresetDialog savePresetDialog = null;
		private OpenFileDialog openFileDialog = null;
	
		public DrumsMenuCallback(DrumsActivity p)
		{
			parent = p;
			metronomDialog = new MetronomQuickSettingsDialog();
			savePresetDialog = new SavePresetDialog();
			openFileDialog = new OpenFileDialog(parent, new LoadFileDialogListener(parent), DrumPresetHandler.path, ".xml");
			
		}
	
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }
        
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            parent.getMenuInflater().inflate(R.menu.drums_callback_menu, menu);
            mode.setTitle(R.string.drums_context_title);
           
            return true;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch(item.getItemId()){
            case R.id.drums_context_bpm:
            	metronomDialog.show(parent.getFragmentManager(), null);
                mode.finish();
                break;
//            case R.id.drums_context_save_preset:
//            	Log.i("DrumsMenuCallback", "SavePreset");
//            	savePresetDialog.show(parent.getFragmentManager(), null);
//            	//parent.saveCurrentPreset("example");
//            	mode.finish();
//            	break;
//            case R.id.drums_context_load_preset:
//            	Log.i("DrumsMenuCallback", "LoadPreset");
//            	openFileDialog.showDialog();
//            	//parent.loadPresetByName("example");
//            	mode.finish();
//            	break;
            }
            return false;
        }

		@Override
		public void onDestroyActionMode(ActionMode mode) {
			// TODO Auto-generated method stub
			
		}
}
