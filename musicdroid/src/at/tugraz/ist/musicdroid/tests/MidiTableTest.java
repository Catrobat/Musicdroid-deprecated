package at.tugraz.ist.musicdroid.tests;

import at.tugraz.ist.musicdroid.MidiTable;

import junit.framework.TestCase;

public class MidiTableTest extends TestCase {
	
	private String[] midi = new String[132];
	
	public MidiTableTest(String name) {
		
		super(name);
		
		midi[12] = "C0";
		midi[13] = "C#/Db0";
		midi[14] = "D0";
		midi[15] = "D#/Eb0";
		midi[16] = "E0";
		midi[17] = "F0";
		midi[18] = "F#/Gb0";
		midi[19] = "G0";
		midi[20] = "G#/Ab0";
		midi[21] = "A0";
		midi[22] = "A#/Bb0";
		midi[23] = "B0";
		midi[24] = "C1";
		midi[25] = "C#/Db1";
		midi[26] = "D1";
		midi[27] = "D#/Eb1";
		midi[28] = "E1";
		midi[29] = "F1";
		midi[30] = "F#/Gb1";
		midi[31] = "G1";
		midi[32] = "G#/Ab1";
		midi[33] = "A1";
		midi[34] = "A#/Bb1";
		midi[35] = "B1";
		midi[36] = "C2";
		midi[37] = "C#/Db2";
		midi[38] = "D2";
		midi[39] = "D#/Eb2";
		midi[40] = "E2";
		midi[41] = "F2";
		midi[42] = "F#/Gb2";
		midi[43] = "G2";
		midi[44] = "G#/Ab2";
		midi[45] = "A2";
		midi[46] = "A#/Bb2";
		midi[47] = "B2";
		midi[48] = "C3";
		midi[49] = "C#/Db3";
		midi[50] = "D3";
		midi[51] = "D#/Eb3";
		midi[52] = "E3";
		midi[53] = "F3";
		midi[54] = "F#/Gb3";
		midi[55] = "G3";
		midi[56] = "G#/Ab3";
		midi[57] = "A3";
		midi[58] = "A#/Bb3";
		midi[59] = "B3";
		midi[60] = "C4";
		midi[61] = "C#/Db4";
		midi[62] = "D4";
		midi[63] = "D#/Eb4";
		midi[64] = "E4";
		midi[65] = "F4";
		midi[66] = "F#/Gb4";
		midi[67] = "G4";
		midi[68] = "G#/Ab4";
		midi[69] = "A4";
		midi[70] = "A#/Bb4";
		midi[71] = "B4";
		midi[72] = "C5";
		midi[73] = "C#/Db5";
		midi[74] = "D5";
		midi[75] = "D#/Eb5";
		midi[76] = "E5";
		midi[77] = "F5";
		midi[78] = "F#/Gb5";
		midi[79] = "G5";
		midi[80] = "G#/Ab5";
		midi[81] = "A5";
		midi[82] = "A#/Bb5";
		midi[83] = "B5";
		midi[84] = "C6";
		midi[85] = "C#/Db6";
		midi[86] = "D6";
		midi[87] = "D#/Eb6";
		midi[88] = "E6";
		midi[89] = "F6";
		midi[90] = "F#/Gb6";
		midi[91] = "G6";
		midi[92] = "G#/Ab6";
		midi[93] = "A6";
		midi[94] = "A#/Bb6";
		midi[95] = "B6";
		midi[96] = "C7";
		midi[97] = "C#/Db7";
		midi[98] = "D7";
		midi[99] = "D#/Eb7";
		midi[100] = "E7";
		midi[101] = "F7";
		midi[102] = "F#/Gb7";
		midi[103] = "G7";
		midi[104] = "G#/Ab7";
		midi[105] = "A7";
		midi[106] = "A#/Bb7";
		midi[107] = "B7";
		midi[108] = "C8";
		midi[109] = "C#/Db8";
		midi[110] = "D8";
		midi[111] = "D#/Eb8";
		midi[112] = "E8";
		midi[113] = "F8";
		midi[114] = "F#/Gb8";
		midi[115] = "G8";
		midi[116] = "G#/Ab8";
		midi[117] = "A8";
		midi[118] = "A#/Bb8";
		midi[119] = "B8";
		midi[120] = "C9";
		midi[121] = "C#/Db9";
		midi[122] = "D9";
		midi[123] = "D#/Eb9";
		midi[124] = "E9";
		midi[125] = "F9";
		midi[126] = "F#/Gb9";
		midi[127] = "G9";
		midi[128] = "G#/Ab9";
		midi[129] = "A9";
		midi[130] = "A#/Bb9";
		midi[131] = "B9";

	}
	
	public void testMidiToNote() {
		
		MidiTable mt = new MidiTable();
		
		for (int i = 12; i < 131; i++) {
			assertEquals(midi[i], mt.midiToName(i));
		}
		
		

	}
	
}
