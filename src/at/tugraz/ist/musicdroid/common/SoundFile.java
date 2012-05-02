package at.tugraz.ist.musicdroid.common;

import android.content.Context;
import android.content.Intent;
import at.tugraz.ist.musicdroid.R;

public class SoundFile {

	private Context context;

	public SoundFile(Context context) {
		this.context = context;
	};

	public void LoadFile(String filename) {

		tmp_filename_ = filename;
	};

	public void SaveFile() {
	};
	

	public void AppendFile() {
	};

	public void AppendToFile() {
	};

	public void Split() {
	};

	public void Play() {
	};

	private String tmp_filename_;

}
