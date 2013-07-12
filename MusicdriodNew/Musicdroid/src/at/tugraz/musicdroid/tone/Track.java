package at.tugraz.musicdroid.tone;

import java.util.ArrayList;
import java.util.List;

public class Track {

	// TODO fw Instrument
	private List<Symbol> symbols;
	private Key key;
	private Time time;
	
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
	
	public int size() {
		return symbols.size();
	}
}
