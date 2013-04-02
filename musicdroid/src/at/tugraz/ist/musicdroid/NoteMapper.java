package at.tugraz.ist.musicdroid;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import android.util.Log;

public class NoteMapper {
  private NavigableMap<Integer,Integer> positionToWhiteKey = new TreeMap<Integer, Integer>();
  private NavigableMap<Integer,Integer> positionToBlackKey = new TreeMap<Integer, Integer>();
  private float interval_;

  
  public void initializeWhiteKeyMap(float interval)
  {		
	interval_ = interval;
	float position = 0;  
	int oct = 0;
	for(int octave = 0; octave < 5; octave++)
	{	
		positionToWhiteKey.put(round(position),oct+36);   //C
		position += interval;
		positionToWhiteKey.put(round(position),oct+38);   //D
		position += interval;
		positionToWhiteKey.put(round(position),oct+40);   //E
		position += interval;
		positionToWhiteKey.put(round(position),oct+41);   //F
		position += interval;
		positionToWhiteKey.put(round(position),oct+43);   //G
		position += interval;
		positionToWhiteKey.put(round(position),oct+45);   //A
		position += interval;
		positionToWhiteKey.put(round(position),oct+47);  //H
		position += interval;
		oct += 12;
	}	
  }
  
  public void initializeBlackKeyMap(float interval)
  {
	float position = interval;  
	int oct = 0;
	for(int octave = 0; octave < 5; octave++)
	{	
		positionToBlackKey.put(round(position),oct+37);   //Cis
		position += interval*2;
		positionToBlackKey.put(round(position),oct+39);   //Dis
		position += interval*3;
		positionToBlackKey.put(round(position),oct+42);   //Fis
		position += interval*2;
		positionToBlackKey.put(round(position),oct+44);   //Gis
		position += interval*2;
		positionToBlackKey.put(round(position),oct+46);  //Ais
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
	if(position < pos+interval_/2) return key;
	else return -1;
  }
  
	private int round(float val)
	{
	  val += 0.5;
	  return (int)val;
	}
	
}
