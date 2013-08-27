package org.catrobat.musicdroid.drums;

import android.os.Environment;

import org.catrobat.musicdroid.tools.FileExtensionMethods;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.util.ArrayList;

public class DrumPresetHandler {
	public final static String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/presets/";

	public DrumPresetHandler() {
		FileExtensionMethods.checkPathExistsAndCreateDirectory(path);
	}

	public boolean writeDrumLoopToPreset(String name, ArrayList<DrumSoundRow> drumSoundRowsArray) {
		if (drumSoundRowsArray.size() != 6)
			return false;

		String filename = path + name;
		if (!filename.endsWith(".xml"))
			filename = filename + ".xml";
		Serializer serializer = new Persister();
		DrumPreset test = new DrumPreset(name, drumSoundRowsArray);
		FileExtensionMethods.checkPathExistsAndCreateDirectory(path);
		File result = new File(filename);

		try {
			serializer.write(test, result);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public DrumPreset readPresetByName(String name) {
		Serializer serializer = new Persister();
		String filename = path + "/" + name;
		if (!filename.endsWith(".xml"))
			filename = filename + ".xml";
		File source = new File(filename);

		try {
			DrumPreset preset = serializer.read(DrumPreset.class, source);
			if (isValidPreset(preset))
				return preset;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private boolean isValidPreset(DrumPreset preset) {
		if (preset.getDrumSoundRowsArray().size() != 6) {
			return false;
		}
		return true;
	}

}
