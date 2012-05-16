package at.tugraz.ist.musicdroid.common;

import java.io.File;
import at.tugraz.ist.musicdroid.common.Constants;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class SoundFile {

	private Context context;
	private static SoundFile instance = null;
	public static final int REQUEST_CODE = 0;
	ArrayList<String> file_list_ = new ArrayList<String>();

	private SoundFile() {
	}

	public static SoundFile GetInstance() {
		if (instance == null)
			return new SoundFile();
		else
			return instance;
	}

	public void LoadFile(Intent data) {
		try {
			Uri sound_file_uri = data.getData();
			file_list_.add(sound_file_uri.getPath());

			File input = new File(file_list_.get(file_list_.size() - 1));
			File output = new File(Constants.MAIN_DIRECTORY
					+ Constants.SOUND_FILE_SUB_DIRECTORY + input.getName());

			SaveFile(input, output);

		} catch (Exception e) {
		}
	};

	public void SaveFile(File input, File output) throws IOException {
		checkDirectory(output.getPath());
		try {
			checkDirectory(output.getPath());

			FileChannel inChannel = new FileInputStream(input).getChannel();
			FileChannel outChannel = new FileOutputStream(output).getChannel();

			inChannel.transferTo(0, inChannel.size(), outChannel);
		} catch (IOException e) {
			e.printStackTrace();
		}
	};

	public void checkDirectory(String directory_path) {
		File directory;
		String new_path = "";
		String[] splittArray = directory_path.split("/");

		for (int i = 0; i < splittArray.length - 1; i++) {
			new_path += splittArray[i] + "/";
			directory = new File(new_path);
			if (!(directory.exists() && directory.isDirectory() && directory
					.canWrite())) {
				directory.mkdir();
			}
		}
	}

	public void AppendFile() {
	};

	public void AppendToFile() {
	};

	public void Split() {
	};

	public void Play() {
	};

	public String getFilePath() {
		return file_list_.get(file_list_.size() - 1);
	}
}
