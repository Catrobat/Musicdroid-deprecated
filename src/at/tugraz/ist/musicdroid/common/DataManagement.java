package at.tugraz.ist.musicdroid.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Vector;

import android.content.Intent;
import android.net.Uri;

public class DataManagement {

	public void copyFile(File input, File output) throws IOException {
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

	public void LoadSoundFile(Intent data) {
		try {
			Uri sound_file_uri = data.getData();

			File input = new File(sound_file_uri.getPath());
			File output = new File(Constants.MAIN_DIRECTORY
					+ Constants.SOUND_FILE_SUB_DIRECTORY + input.getName());

			copyFile(input, output);

			Projekt.getInstance().addSoundFile(output.getAbsolutePath());
			System.out.println(output.getAbsolutePath());
			System.out.println(Projekt.getInstance().getLastSoundFile());

		} catch (Exception e) {
		}
	};

	public void deleteFile(String filename) {

	}

	public Vector<String> LoadDirectory(String directoryPath) {
		return new Vector<String>();
	}

	public Vector<String> LoadXML(String filename) {
		return new Vector<String>();
	}
}
