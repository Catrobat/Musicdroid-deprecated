package at.tugraz.ist.musicdroid;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import android.util.Log;

public class NoteMapper {
  private NavigableMap<Integer,Integer> positionToWhiteKey = new TreeMap<Integer, Integer>();
  private NavigableMap<Integer,Integer> positionToBlackKey = new TreeMap<Integer, Integer>();
  private int interval_;

  
  public Boolean initializeNoteMap(int interval)
  {
	  interval_ = interval;  
	  initializeWhiteKeyMap(interval);
	  initializeBlackKeyMap(interval/2);

      return true;
  }
  
  private void initializeWhiteKeyMap(int interval)
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
  }
  
  private void initializeBlackKeyMap(int interval)
  {
	int position = interval;  
	int oct = 0;
	for(int octave = 0; octave < 5; octave++)
	{	
		positionToBlackKey.put(position,oct+1);   //Cis
		position += interval*2;
		positionToBlackKey.put(position,oct+3);   //Dis
		position += interval*3;
		positionToBlackKey.put(position,oct+6);   //Fis
		position += interval*2;
		positionToBlackKey.put(position,oct+8);   //Gis
		position += interval*2;
		positionToBlackKey.put(position,oct+10);  //Ais
		position += interval*3;
		oct += 12;
	}  
  }
  
  public Integer getWhiteKeyFromPosition(int position)
  {
	return positionToWhiteKey.floorEntry(position).getValue();
  }
  
  //returns true if position is not inbetween 2 black keys 
  public int getBlackKeyFromPosition(int position)
  {
	int pos = positionToBlackKey.floorEntry(position).getKey();
	int key = positionToBlackKey.floorEntry(position).getValue();
	//String pos = String.valueOf(key) + " " + String.valueOf(position) + " " + String.valueOf(interval_/2);
	//Log.e("black", pos);
	if(position < pos+interval_/2) return key;
	else return -1;
  }
	
}
