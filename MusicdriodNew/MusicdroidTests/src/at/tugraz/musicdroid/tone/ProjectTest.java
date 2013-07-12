package at.tugraz.musicdroid.tone;

import junit.framework.TestCase;

public class ProjectTest extends TestCase {

	public void testAddTrack() {
		Project project = new Project();
		
		project.addTrack(new Track());
		
		assertEquals(1, project.size());
	}

	public void testRemoveTrack() {
		Project project = new Project();
		
		Track track = new Track();
		project.addTrack(track);
		project.removeTrack(track);
		
		assertEquals(0, project.size());
	}
}
