package at.tugraz.ist.musicdroid;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import android.util.Log;

public class NoteMapper {
  private NavigableMap<Integer,String> positionToWhiteKey = new TreeMap<Integer, String>();
  private NavigableMap<Integer,String> positionToBlackKey = new TreeMap<Integer, String>();

  
  public Boolean initializeNoteMap(int interval)
  {
	//to play midi-sound, change Value of Map from C, D, E...to corresponding midi-value
	int position = 0;  
	positionToWhiteKey.put(position,String.valueOf('C'));
	position += interval;
	positionToWhiteKey.put(position,String.valueOf('D'));
	position += interval;
	positionToWhiteKey.put(position,String.valueOf('E'));
	position += interval;
	positionToWhiteKey.put(position,String.valueOf('F'));
	position += interval;
	positionToWhiteKey.put(position,String.valueOf('G'));
	position += interval;
	positionToWhiteKey.put(position,String.valueOf('A'));
	position += interval;
	positionToWhiteKey.put(position,String.valueOf('H'));
	position += interval;
	positionToWhiteKey.put(position,String.valueOf('c'));
	position += interval;
	positionToWhiteKey.put(position,String.valueOf('d'));
	position += interval;
	positionToWhiteKey.put(position,String.valueOf('e'));
	position += interval;
	positionToWhiteKey.put(position,String.valueOf('f'));
	position += interval;
	positionToWhiteKey.put(position,String.valueOf('g'));
	position += interval;
	positionToWhiteKey.put(position,String.valueOf('a'));
	position += interval;
	positionToWhiteKey.put(position,String.valueOf('h'));
	position += interval;
	positionToWhiteKey.put(position,String.valueOf("c1"));
	position += interval;
	positionToWhiteKey.put(position,String.valueOf("d1"));
	position += interval;
	positionToWhiteKey.put(position,String.valueOf("e1"));
	position += interval;
	positionToWhiteKey.put(position,String.valueOf("f1"));
	position += interval;
	positionToWhiteKey.put(position,String.valueOf("g1"));
	position += interval;
	positionToWhiteKey.put(position,String.valueOf("a1"));
	position += interval;
	positionToWhiteKey.put(position,String.valueOf("h1"));
	position += interval;
	positionToWhiteKey.put(position,String.valueOf("c2"));
	position += interval;
	positionToWhiteKey.put(position,String.valueOf("d2"));
	position += interval;
	positionToWhiteKey.put(position,String.valueOf("e2"));
	position += interval;
	positionToWhiteKey.put(position,String.valueOf("f2"));
	position += interval;
	positionToWhiteKey.put(position,String.valueOf("g2"));
	position += interval;
	positionToWhiteKey.put(position,String.valueOf("a2"));
	position += interval;
	positionToWhiteKey.put(position,String.valueOf("h2"));
	position += interval;
	positionToWhiteKey.put(position,String.valueOf("c3"));
	position += interval;
	positionToWhiteKey.put(position,String.valueOf("d3"));
	position += interval;
	positionToWhiteKey.put(position,String.valueOf("e3"));
	position += interval;
	positionToWhiteKey.put(position,String.valueOf("f3"));
	position += interval;
	positionToWhiteKey.put(position,String.valueOf("g3"));
	position += interval;
	positionToWhiteKey.put(position,String.valueOf("a3"));
	position += interval;
	positionToWhiteKey.put(position,String.valueOf("h3"));
	return true;
  }
  
  public String getWhiteKeyFromPosition(int position)
  {
	return positionToWhiteKey.floorEntry(position).getValue();
  }
	
}
