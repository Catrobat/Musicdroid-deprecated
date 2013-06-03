package at.tugraz.musicdroid.types;

import at.tugraz.musicdroid.R;

public enum SoundType {
	PIANO(
			R.string.menu_item_piano, R.drawable.piano_button,
			R.string.help_menu_new_piano, R.color.sound_view_piano_color,
			R.color.sound_view_piano_expanded_color), 
	DRUMS(
			R.string.menu_item_drums, R.drawable.drum_button,
			R.string.help_menu_new_drums, R.color.sound_view_drums_color,
			R.color.sound_view_drums_expanded_color),
	MIC(
			R.string.menu_item_mic, R.drawable.mic_button,
			R.string.help_menu_new_recording, R.color.sound_view_mic_color,
			R.color.sound_view_mic_expanded_color);
	

	private int nameResource;
	private int imageResouce;
	private int helpTextResource;
	private int colorResource;
	private int expandedColorResource;

	private SoundType(int nameResource, int imageResource, int helpTextResource, int colorResource, int expandedColorResource) {
		this.nameResource = nameResource;
		this.imageResouce = imageResource;
		this.helpTextResource = helpTextResource;
		this.colorResource = colorResource;
		this.expandedColorResource = expandedColorResource;
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
	
	public int getExpandedColorResource() {
		return expandedColorResource;
	}

}
