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
package at.tugraz.musicdroid.dialog;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import at.tugraz.musicdroid.R;
import at.tugraz.musicdroid.types.SoundType;

public class AddSoundAdapter extends BaseAdapter {

	private Context context;

	private ArrayList<SoundType> buttonsList;

	public AddSoundAdapter(Context context, boolean fromCatrobat) {
		this.context = context;
		initButtons();
	}

	private void initButtons() {
		buttonsList = new ArrayList<SoundType>();
		buttonsList.add(SoundType.PIANO);
		buttonsList.add(SoundType.DRUMS);
		buttonsList.add(SoundType.MIC);
	}

	@Override
	public int getCount() {
		return buttonsList.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	public SoundType getSoundType(int position) {
		return buttonsList.get(position);
	}

	public ArrayList<SoundType> getSoundTypes() {
		return buttonsList;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		if (rowView == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			rowView = inflater.inflate(R.layout.add_sound_button, null);
			ImageView imageView = (ImageView) rowView
					.findViewById(R.id.add_sound_button_image);
			imageView.setImageResource(buttonsList.get(position)
					.getImageResource());
			TextView textView = (TextView) rowView
					.findViewById(R.id.add_sound_button_text);
			textView.setText(buttonsList.get(position).getNameResource());
		}
		return rowView;
	}

}
