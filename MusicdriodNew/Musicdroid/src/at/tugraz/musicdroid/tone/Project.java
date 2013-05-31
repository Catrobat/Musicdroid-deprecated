package at.tugraz.musicdroid.tone;

import java.util.LinkedList;
import java.util.List;

public class Project {

	private List<Track> tracks;
	
	public Project() {
		this.tracks = new LinkedList<Track>();
	}
	
	public void addTrack(Track track) {
		tracks.add(track);
	}
	
	public void removeTrack(Track track) {
		tracks.remove(track);
	}
	
	public int size() {
		return tracks.size();
	}
}
