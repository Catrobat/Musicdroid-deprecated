package at.tugraz.musicdroid.types;

import at.tugraz.musicdroid.R;

public enum SoundType {
	PIANO(
			R.string.menu_item_piano, R.drawable.piano_button,
			R.string.help_menu_new_piano, R.color.sound_view_piano_color), 
	DRUMS(
			R.string.menu_item_drums, R.drawable.drum_button,
			R.string.help_menu_new_drums, R.color.sound_view_drums_color),
	MIC(
			R.string.menu_item_mic, R.drawable.mic_button,
			R.string.help_menu_new_recording, R.color.sound_view_mic_color);
	

	private int nameResource;
	private int imageResouce;
	private int helpTextResource;
	private int colorResource;

	private SoundType(int nameResource, int imageResource, int helpTextResource, int colorResource) {
		this.nameResource = nameResource;
		this.imageResouce = imageResource;
		this.helpTextResource = helpTextResource;
		this.colorResource = colorResource;
	}

	public int getNameResource() {
		return nameResource;
	}

	public int getImageResource() {
		return imageResouce;
	}

	public int getHelpTextResource() {
		return helpTextResource;
	}
	
	public int getColorResource() {
		return colorResource;
	}

}
