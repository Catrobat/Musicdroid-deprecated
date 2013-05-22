package at.tugraz.musicdroid.soundtracks;

import at.tugraz.musicdroid.R;
import at.tugraz.musicdroid.SoundManager;
import at.tugraz.musicdroid.types.SoundType;

import android.content.Context;
import android.util.Log;

public class SoundTrackPiano extends SoundTrack {
	
	public SoundTrackPiano() {
		type = SoundType.PIANO;
		name = "SoundfilePiano";
		soundfile_raw_id = R.raw.test_midi;
		duration =  SoundManager.getSoundfileDuration(soundfile_raw_id);
		soundpool_id = SoundManager.loadSound(soundfile_raw_id);
	}
	
	public SoundTrackPiano(SoundTrackPiano stm)
	{
		Log.e("Calling copy constr", "piano");
	}

}
