package at.tugraz.ist.musicdroid;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.String;
import java.nio.channels.FileChannel;

import org.puredata.android.io.AudioParameters;
import org.puredata.android.service.PdService;
import org.puredata.android.utils.PdUiDispatcher;
import org.puredata.core.PdBase;
import org.puredata.core.PdListener;
import org.puredata.core.utils.IoUtils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class RecToFrequencyActivity extends Activity {

	private PdService pdService = null;
	private final static String Appname = "rec_to_frequency";
	private String path;
	private File dir;
	
	private final ServiceConnection pdConnection = new ServiceConnection() {
    	@Override
    	public void onServiceConnected(ComponentName name, IBinder service) {
    		pdService = ((PdService.PdBinder)service).getService();
    		try {
    			initPd();
    			loadPatch();
    		} catch (IOException e) {
    			Log.e(Appname, e.toString());
    			finish();
    		} 
    	}
        @Override
        public void onServiceDisconnected(ComponentName name) {
        	
        	
            }
        };
        
        private void loadPatch() throws IOException {
        	Log.e("test", "test");
    		dir = getFilesDir();
    		IoUtils.extractZipResource(getResources().openRawResource(R.raw.fiddle),
    				dir, true);
    		File patchFile = new File(dir, "fiddle.pd");
    		path = patchFile.getAbsolutePath();		
    		PdBase.openPatch(patchFile.getAbsolutePath());	

        }
        
        private void initPd() throws IOException {
    		String name = getResources().getString(R.string.app_name);
    		pdService.initAudio(-1, -1, -1, -1);
    		pdService.startAudio(new Intent(this, RecToFrequencyActivity.class), 
    				             R.drawable.musicdroid_launcher, name, "Retrun to " 
    		                                                          + name + ".");
    				
    		
    	}
}
