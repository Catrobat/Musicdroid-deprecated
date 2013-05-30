package at.tugraz.musicdroid.soundmixer.timeline;

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import at.tugraz.musicdroid.MainActivity;
import at.tugraz.musicdroid.R;
import at.tugraz.musicdroid.soundmixer.SoundMixer;

public class TimelineMenuCallback implements ActionMode.Callback {
		MainActivity parent = null;
		Timeline timeline = null;
	
		public TimelineMenuCallback(MainActivity p, Timeline t)
		{
			timeline = t;
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
            parent.getMenuInflater().inflate(R.menu.timeline_menu, menu);
            mode.setTitle("Timeline");
            return true;
        }

        /** This is called when an item in the context menu is selected */
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch(item.getItemId()){
            case R.id.timeline_context_add_start_point:
            	SoundMixer.getInstance().setStartPoint(timeline.getClickLocation());
            	mode.finish();
                break;
            case R.id.timeline_context_add_end_point:
            	SoundMixer.getInstance().setEndPoint(timeline.getClickLocation());
            	mode.finish();
                break;
            }
            return false;
        }
}
