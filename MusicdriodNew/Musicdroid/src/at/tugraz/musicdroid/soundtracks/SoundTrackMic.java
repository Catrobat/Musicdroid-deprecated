package at.tugraz.musicdroid.soundtracks;

import android.util.Log;
import at.tugraz.musicdroid.R;
import at.tugraz.musicdroid.SoundManager;
import at.tugraz.musicdroid.helper.Helper;
import at.tugraz.musicdroid.types.SoundType;

public class SoundTrackMic extends SoundTrack {
	
	public SoundTrackMic() {
		type = SoundType.MIC;
		name = "SoundfileMic";
		soundfileRawId = R.raw.test_wav;
		duration =  SoundManager.getSoundfileDuration(soundfileRawId);
		soundpoolId = SoundManager.loadSound(soundfileRawId);
		Log.i("SoundTrackMIC", "SoundpoolID = " + soundpoolId);
	}
	
	public SoundTrackMic(String path) {
		type = SoundType.MIC;
		name = Helper.getInstance().getFilenameFromPath(path);
		soundpoolId = SoundManager.addSoundByPath(path);
		duration = SoundManager.getSoundfileDurationByPath(path);		
	}
	
	public SoundTrackMic(SoundTrackMic stm)
	{
		Log.e("Calling copy constr", "mic");
	}

}
