package at.tugraz.musicdroid.soundtracks;

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import at.tugraz.musicdroid.MainActivity;
import at.tugraz.musicdroid.R;
import at.tugraz.musicdroid.soundmixer.SoundMixer;

public class SoundTrackViewMenuCallback implements ActionMode.Callback {
		MainActivity parent = null;
	
		public SoundTrackViewMenuCallback(MainActivity p)
		{
			parent = p;
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
            parent.getMenuInflater().inflate(R.menu.sound_track_dialog_menu, menu);
            SoundMixer.getInstance().disableUnselectedViews();
            
            String name = null;
            if((name = SoundMixer.getInstance().getCallingTrack().getName()) != null)
            	mode.setTitle(name);
            else
            	mode.setTitle("Fix this");
            return true;
        }

        /** This is called when an item in the context menu is selected */
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch(item.getItemId()){
            case R.id.soundtrack_context_edit:
                Toast.makeText(parent.getBaseContext(), "Editing of tracks not yet implemented ", Toast.LENGTH_LONG).show();
                mode.finish();    // Automatically exists the action mode, when the user selects this action
                break;
            case R.id.soundtrack_context_copy:
				SoundMixer.getInstance().handleCopy();
				mode.finish();
                break;
            case R.id.soundtrack_context_delete:
				SoundMixer.getInstance().deleteCallingTrack();
                mode.finish();
                break;
            }
            return false;
        }
}
