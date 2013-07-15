package at.tugraz.musicdroid.tone;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Project implements Serializable {

	private static final long serialVersionUID = 7396763540934053009L;
	
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
	
	public Track getTrack(int location) {
		return tracks.get(location);
	}
	
	public int size() {
		return tracks.size();
	}
	
	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof Project)) {
			return false;
		}
		
		Project project = (Project) obj;
		
		if (size() == project.size()) {
			for (int i = 0; i < size(); i++) {
				if (!getTrack(i).equals(project.getTrack(i))) {
					return false;
				}
			}
			
			return true;
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		return "[Project] trackCount=" + size();
	}
}
