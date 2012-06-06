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

	boolean LoadProject(String project_name) {
		// TODO: LoadDirectory(), LoadXML aus DataManagement aufrufen.
		project_name_ = project_name;
		return true;
	}

	boolean CreateProject(String project_name) {
		return true;
	}

	boolean SaveProject() {
		return true;
	}

	boolean DeleteProject() {
		return true;
	}

	void addMidi(MidiFile midi) {

		midis_.add(midi);
	}

	void addSoundFile(String filename) {
		imported_files_.add(filename);
	}

	void addRecord(String file) {

	}

	void fillSlot(int index, Object input, int type_code) {

	}

	void releaseSlot(int index) {

	}

	void deleteMidi(MidiFile midi) {
		midis_.remove(midi);
	}

	void deleteFile(String filename) {

	}

	void deleteRecord(String filename) {

	}

	Pair<Integer, Object> getSlotDataAt(int index) {

		return new Pair<Integer, Object>(0, 0);
	}

	ArrayList<MidiFile> midis_;
	ArrayList<String> imported_files_;
	ArrayList<String> records_;
	Vector<Pair<Integer, Object>> slots_;
	String project_name_;

}
