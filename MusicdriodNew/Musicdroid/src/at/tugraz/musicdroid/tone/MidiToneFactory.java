package at.tugraz.musicdroid.tone;

public class MidiToneFactory {

	private MidiToneFactory() {
	}
	
	public static MidiTone createC1() {
		return new MidiTone(24);
	}
}
