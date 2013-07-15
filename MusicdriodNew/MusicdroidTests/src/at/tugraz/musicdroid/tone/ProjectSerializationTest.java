package at.tugraz.musicdroid.tone;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import junit.framework.TestCase;

public class ProjectSerializationTest extends TestCase {

	public void testSerialize() throws IOException, ClassNotFoundException {
		Project project = new Project();
		Key key = Key.BASS;
		Time time = new Time(100, NoteLength.WHOLE);
		Track track = new Track(key, time);
		project.addTrack(track);
		File file = new File("projectSerializedTest");
		
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
		out.writeObject(project);
		out.close();
		
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
		Project readProject = (Project) in.readObject();
		in.close();
		
		file.delete();
		
		assertEquals(project, readProject);
	}
}
