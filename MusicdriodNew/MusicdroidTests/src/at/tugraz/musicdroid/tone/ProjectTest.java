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
	
	public void testEquals1() {
		Track track1 = new Track();
		track1.addSymbol(new Break(NoteLength.HALF));
		
		Track track2 = new Track();
		track2.addSymbol(new Break(NoteLength.HALF));
		
		assertTrue(track1.equals(track2));
	}
	
	public void testEquals2() {
		Project project1 = new Project();
		project1.addTrack(new Track());
		
		Project project2 = new Project();
		project2.addTrack(new Track());
		
		assertTrue(project1.equals(project2));
	}
	
	public void testEquals3() {
		Project project1 = new Project();
		project1.addTrack(new Track());
		
		Project project2 = new Project();
		
		assertFalse(project1.equals(project2));
	}
	
	public void testEquals4() {
		Project project = new Project();
		
		assertFalse(project.equals(null));
	}
	
	public void testEquals5() {
		Project project = new Project();
		
		assertFalse(project.equals(""));
	}
	
	public void testToString() {
		Project project = new Project();
		
		assertEquals("[Project] trackCount=" + project.size(), project.toString());
	}
}
