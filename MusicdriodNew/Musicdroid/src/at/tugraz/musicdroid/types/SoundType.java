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

public enum SoundType {
	PIANO(R.string.menu_item_piano, R.drawable.piano_button,
			R.string.help_menu_new_piano, R.color.sound_view_piano_color,
			R.color.sound_view_piano_expanded_color), DRUMS(
			R.string.menu_item_drums, R.drawable.drum_button,
			R.string.help_menu_new_drums, R.color.sound_view_drums_color,
			R.color.sound_view_drums_expanded_color), MIC(
			R.string.menu_item_mic, R.drawable.mic_button,
			R.string.help_menu_new_recording, R.color.sound_view_mic_color,
			R.color.sound_view_mic_expanded_color);

	private int nameResource;
	private int imageResouce;
	private int helpTextResource;
	private int colorResource;
	private int expandedColorResource;

	private SoundType(int nameResource, int imageResource,
			int helpTextResource, int colorResource, int expandedColorResource) {
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
