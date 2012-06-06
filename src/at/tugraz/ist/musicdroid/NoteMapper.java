package at.tugraz.ist.musicdroid;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import android.util.Log;

public class NoteMapper {
  private NavigableMap<Integer,Integer> positionToWhiteKey = new TreeMap<Integer, Integer>();
  private NavigableMap<Integer,Integer> positionToBlackKey = new TreeMap<Integer, Integer>();

  
  public Boolean initializeNoteMap(int interval)
  {
		int position = 0;  
		int oct = 0;
		for(int octave = 0; octave < 5; octave++)
		{	
			positionToWhiteKey.put(position,oct+0);   //C
			position += interval;
			positionToWhiteKey.put(position,oct+2);   //D
			position += interval;
			positionToWhiteKey.put(position,oct+4);   //E
			position += interval;
			positionToWhiteKey.put(position,oct+5);   //F
			position += interval;
			positionToWhiteKey.put(position,oct+7);   //G
			position += interval;
			positionToWhiteKey.put(position,oct+9);   //A
			position += interval;
			positionToWhiteKey.put(position,oct+11);  //H
			position += interval;
			oct += 12;
		}	

			return true;
  }
  
  public Integer getWhiteKeyFromPosition(int position)
  {
	return positionToWhiteKey.floorEntry(position).getValue();
  }
	
}
