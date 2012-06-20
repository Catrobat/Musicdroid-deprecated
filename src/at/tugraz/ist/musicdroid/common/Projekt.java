package at.tugraz.ist.musicdroid.common;

import java.util.ArrayList;
import java.util.Vector;

import android.content.Context;
import android.util.Pair;

public class Projekt {

	Context context;
	public static Projekt instance = null;

	private Projekt() {

	}

	public static Projekt getInstance() {
		if (instance == null)
			return new Projekt();
		else
			return instance;
	}

	public boolean LoadProject(String project_name) {
		// TODO: LoadDirectory(), LoadXML aus DataManagement aufrufen.
		project_name_ = project_name;
		return true;
	}

	public boolean CreateProject(String project_name) {
		return true;
	}

	public boolean SaveProject() {
		return true;
	}

	public boolean DeleteProject() {
		return true;
	}

	public void addMidi(MidiFile midi) {

		midis_.add(midi);
	}

	public void addSoundFile(String filename) {
		imported_files_.add(filename);
	}

	public String getLastSoundFile() {
		return imported_files_.get(imported_files_.size() - 1);
	}

	public void addRecord(String file) {

	}

	public void fillSlot(int index, Object input, int type_code) {

	}

	public void releaseSlot(int index) {

	}

	public void deleteMidi(MidiFile midi) {
		midis_.remove(midi);
	}

	public void deleteFile(String filename) {

	}

	public void deleteRecord(String filename) {

	}

	public Pair<Integer, Object> getSlotDataAt(int index) {

		return new Pair<Integer, Object>(0, 0);
	}

	public static ArrayList<MidiFile> midis_;
	public static ArrayList<String> imported_files_ = new ArrayList<String>();
	public static ArrayList<String> records_ = new ArrayList<String>();
	public static Vector<Pair<Integer, Object>> slots_;
	public static String project_name_;

}
