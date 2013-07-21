/*******************************************************************************
 * Catroid: An on-device visual programming system for Android devices
 *  Copyright (C) 2010-2013 The Catrobat Team
 *  (<http://developer.catrobat.org/credits>)
 *  
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 * 
 *  An additional term exception under section 7 of the GNU Affero
 *  General Public License, version 3, is available at
 *  http://www.catroid.org/catroid/licenseadditionalterm
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU Affero General Public License for more details.
 * 
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package at.tugraz.musicdroid.types;

import at.tugraz.musicdroid.R;

public enum SoundTrackMenuEntryType {
	EDIT(R.string.sound_track_menu_entry_edit,
			R.string.help_sound_track_menu_entry_edit), DELETE(
			R.string.sound_track_menu_entry_delete,
			R.string.help_sound_track_menu_entry_delete), COPY(
			R.string.sound_track_menu_entry_copy,
			R.string.help_sound_track_menu_entry_copy);

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
