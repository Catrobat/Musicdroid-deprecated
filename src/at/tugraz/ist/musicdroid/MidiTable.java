package at.tugraz.ist.musicdroid;

import java.util.HashMap;

public class MidiTable {
	
	HashMap<Integer, String> NoteMap = new HashMap<Integer, String>();
	
	public MidiTable() {
		
		for(int i=0;i<=9;i++)
		{
			NoteMap.put(12 + i*12, "C"+i);
			NoteMap.put(13 + i*12, "C#/Db"+i);
			NoteMap.put(14 + i*12, "D"+i);
			NoteMap.put(15 + i*12, "D#/Eb"+i);
			NoteMap.put(16 + i*12, "E"+i);
			NoteMap.put(17 + i*12, "F"+i);
			NoteMap.put(18 + i*12, "F#/Gb"+i);
			NoteMap.put(19 + i*12, "G"+i);
			NoteMap.put(20 + i*12, "G#/Ab"+i);
			NoteMap.put(21 + i*12, "A"+i);
			NoteMap.put(22 + i*12, "A#/Bb"+i);
			NoteMap.put(23 + i*12, "B"+i);			
		}
		
	}
	
	public String midiToName(int midi)
	{
		if(NoteMap.containsKey(midi))
		  return NoteMap.get(midi).toString();
		else
		  return "invalid value";
	}
	
	

}
