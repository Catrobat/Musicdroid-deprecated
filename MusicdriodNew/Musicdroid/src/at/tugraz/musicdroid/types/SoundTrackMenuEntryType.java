package at.tugraz.musicdroid.types;

import at.tugraz.musicdroid.R;
import at.tugraz.musicdroid.R.drawable;
import at.tugraz.musicdroid.R.string;

public enum SoundTrackMenuEntryType {
	EDIT(
			R.string.sound_track_menu_entry_edit, R.string.help_sound_track_menu_entry_edit),
    DELETE(
			R.string.sound_track_menu_entry_delete, R.string.help_sound_track_menu_entry_delete),
	COPY(
			R.string.sound_track_menu_entry_copy, R.string.help_sound_track_menu_entry_copy);
	

	private int nameResource;
	private int helpTextResource;

	private SoundTrackMenuEntryType(int nameResource, int helpTextResource) {
		this.nameResource = nameResource;
		this.helpTextResource = helpTextResource;
	}

	public int getNameResource() {
		return nameResource;
	}

	public int getHelpTextResource() {
		return helpTextResource;
	}

}
