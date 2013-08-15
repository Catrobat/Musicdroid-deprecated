package org.catrobat.musicdroid.drums;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.os.Environment;

public class DrumPresetHandler {
	public final static String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/presets/";
	HashMap<Integer, DrumPreset> presetMap = null;
	
	public DrumPresetHandler()
	{
		presetMap = new HashMap<Integer, DrumPreset>();
		checkPathExistsAndCreateDirectory();
	}
	
	public boolean writeDrumLoopToPreset(String name, ArrayList<DrumSoundRow> drumSoundRowsArray)
	{
		if(drumSoundRowsArray.size() != 6)
			return false;
		
		String filename = path + name;
		if(!filename.endsWith(".xml")) filename = filename + ".xml";
		Serializer serializer = new Persister();
		DrumPreset test = new DrumPreset(name, drumSoundRowsArray);
		checkPathExistsAndCreateDirectory();
		File result = new File(filename);

		try {
			serializer.write(test, result);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public DrumPreset readPresetByName(String name)
	{
		Serializer serializer = new Persister();
		String filename = path+"/"+name;
		if(!filename.endsWith(".xml")) filename = filename + ".xml";
		File source = new File(filename);

		try {
			DrumPreset preset = serializer.read(DrumPreset.class, source);
			if(checkValidPreset(preset))
			  return preset;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private void checkPathExistsAndCreateDirectory()
	{
		File f = new File(path);
		if(!f.exists())
			f.mkdir();
	}
	
	
	private boolean checkValidPreset(DrumPreset preset)
	{
		if(preset.getDrumSoundRowsArray().size() != 6)
		{
			return false;
		}
		return true;
					
			
	}
	
	public void writeTestPreset()
	{ 
		Serializer serializer = new Persister();
		DrumPreset test = new DrumPreset();
		File result = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/example.xml");

		try {
			serializer.write(test, result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Serializer serializer2 = new Persister();
		File source = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/example.xml");

		try {
			DrumPreset example = serializer2.read(DrumPreset.class, source);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
}
