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
package at.tugraz.musicdroid.tone;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Track implements Serializable {

	private static final long serialVersionUID = 7483021689872527955L;

	// TODO fw Instrument
	private Key key;
	private Time time;
	private List<Symbol> symbols;

	public Track() {
		this.symbols = new ArrayList<Symbol>();
		this.key = Key.VIOLIN;
		this.time = new Time();
	}

	public Track(Key key, Time time) {
		this.symbols = new ArrayList<Symbol>();
		this.key = key;
		this.time = time;
	}

	public Key getKey() {
		return key;
	}

	public Time getTime() {
		return time;
	}

	public void addSymbol(Symbol symbol) {
		symbols.add(symbol);
	}

	public void removeSymbol(Symbol symbol) {
		symbols.remove(symbol);
	}

	public Symbol getSymbol(int location) {
		return symbols.get(location);
	}

	public int size() {
		return symbols.size();
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof Track)) {
			return false;
		}

		Track track = (Track) obj;

		if ((key.equals(track.getKey())) && (time.equals(track.getTime()))
				&& (size() == track.size())) {
			for (int i = 0; i < size(); i++) {
				if (!getSymbol(i).equals(track.getSymbol(i))) {
					return false;
				}
			}

			return true;
		}

		return false;
	}

	@Override
	public String toString() {
		return "[Track] key=" + key + " symbolCount=" + size();
	}
}
