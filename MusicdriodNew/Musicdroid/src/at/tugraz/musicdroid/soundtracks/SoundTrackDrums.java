package at.tugraz.musicdroid.soundtracks;

import at.tugraz.musicdroid.R;
import at.tugraz.musicdroid.SoundManager;
import at.tugraz.musicdroid.types.SoundType;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

public class SoundTrackDrums extends SoundTrack {
	
	public SoundTrackDrums() {
		type = SoundType.DRUMS;
		name = "SoundfileDrums";
		soundfile_raw_id = R.raw.test_midi;
		duration =  SoundManager.getSoundfileDuration(soundfile_raw_id);
		soundpool_id = SoundManager.loadSound(soundfile_raw_id);
	}

	public SoundTrackDrums(SoundTrackDrums stm)
	{
		Log.e("Calling copy constr", "drums");
	}
}
