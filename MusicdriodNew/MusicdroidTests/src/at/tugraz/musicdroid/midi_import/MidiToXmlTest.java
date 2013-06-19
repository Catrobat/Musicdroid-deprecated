package at.tugraz.musicdroid.midi_import;

import junit.framework.TestCase;

public class MidiToXmlTest extends TestCase{
	public void testReadMidi(){
		MidiToXml tester=new MidiToXml();
		tester.readMidi();
		assertTrue(tester.getLength()>0);
	}
}
