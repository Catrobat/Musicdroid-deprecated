package at.tugraz.musicdroid.tone;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Track implements Serializable {

	private static final long serialVersionUID = 7483021689872527955L;
	
	// TODO fw Instrument
	private Key key;
	private Time time;
	private List<Symbol> symbols;
	
	public Track() {
		this.symbols = new ArrayList<Symbol>();
		this.key = Key.VIOLIN;
		this.time = new Time();
	}
	
	public Track(Key key, Time time) {
		this.symbols = new ArrayList<Symbol>();
		this.key = key;
		this.time = time;
	}
	
	public Key getKey() {
		return key;
	}
	
	public Time getTime() {
		return time;
	}
	
	public void addSymbol(Symbol symbol) {
		symbols.add(symbol);
	}
	
	public void removeSymbol(Symbol symbol) {
		symbols.remove(symbol);
	}
	
	public Symbol getSymbol(int location) {
		return symbols.get(location);
	}
	
	public int size() {
		return symbols.size();
	}
	
	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof Track)) {
			return false;
		}
		
		Track track = (Track) obj;
		
		if ((key.equals(track.getKey())) && (time.equals(track.getTime())) && (size() == track.size())) {
			for (int i = 0; i < size(); i++) {
				if (!getSymbol(i).equals(track.getSymbol(i))) {
					return false;
				}
			}
			
			return true;
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		return "[Track] key=" + key + " symbolCount=" + size();
	}
}
