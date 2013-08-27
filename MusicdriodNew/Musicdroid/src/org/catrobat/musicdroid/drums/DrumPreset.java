package org.catrobat.musicdroid.drums;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

@Root
public class DrumPreset {
	@Element
	private String name;
	@ElementList
	private ArrayList<DrumSoundRow> drumSoundRowsArray;

	public DrumPreset() {
	}

	public DrumPreset(String n, ArrayList<DrumSoundRow> drumSoundRowsArray) {
		this.name = n;
		this.drumSoundRowsArray = drumSoundRowsArray;
	}

	public ArrayList<DrumSoundRow> getDrumSoundRowsArray() {
		return drumSoundRowsArray;
	}

	public String getName() {
		return name;
	}
}
