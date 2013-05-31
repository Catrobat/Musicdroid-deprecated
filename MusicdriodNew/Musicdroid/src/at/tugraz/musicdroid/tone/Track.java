package at.tugraz.musicdroid.tone;

import java.util.ArrayList;
import java.util.List;

public class Track {

	// TODO fw Instrument
	private List<Symbol> symbols;
	private Time time;
	
	public Track() {
		this.symbols = new ArrayList<Symbol>();
		this.time = Time.FOUR_FOUR;
	}
	
	public Track(Time time) {
		this();
		this.time = time;
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
