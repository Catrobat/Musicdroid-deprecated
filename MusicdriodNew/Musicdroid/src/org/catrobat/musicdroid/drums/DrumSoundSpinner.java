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
package org.catrobat.musicdroid.drums;

import java.util.ArrayList;

import org.catrobat.musicdroid.R;
import org.catrobat.musicdroid.types.DrumType;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * @author matthias schlesinger
 *
 */
public class DrumSoundSpinner extends Spinner {
	private ArrayAdapter<String> adapter = null;
	
	public DrumSoundSpinner(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
	}

	public void initialize(Context context, DrumSoundRow drumSoundRow) 
	{
		ArrayList<String> spinnerArray = DrumType.getTypeArray(context);
		adapter = new ArrayAdapter<String>(context, R.layout.custom_simple_spinner_item, spinnerArray);
		adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
		setAdapter(adapter);
		
		setEnabled(false);
		setClickable(false);
		setOnItemSelectedListener(new OnSpinnerClickListener(drumSoundRow));
	}
	
	public void setSelectionByName(String itemName)
	{
		setSelection(adapter.getPosition(itemName));
	}

	public void performCustomClick() 
	{
		setEnabled(true);
	    this.setClickable(true);
	    this.performClick();
	    this.setEnabled(false);
	    this.setClickable(false);	
    }

}
