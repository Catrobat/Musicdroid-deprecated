package at.tugraz.musicdroid.tone;

public enum NoteName {
	C1, D1, E1, F1, G1, A1, H1, C2;
	
	public NoteName next() {
		int index = (this.ordinal() + 1) % values().length;
		return values()[index];
	}
	
	public NoteName previous() {
		int index = (this.ordinal() - 1) % values().length;
		return values()[index];
	}
}
