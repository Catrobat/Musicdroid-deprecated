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
package org.catrobat.musicdroid.note;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Project implements Serializable {

	public static final String DEFAULT_NAME = "MusicdroidProject";
	public static final int DEFAULT_BEATS_PER_MINUTE = 120;
	public static final Key DEFAULT_KEY = Key.VIOLIN;
	private static final long serialVersionUID = 7396763540934053008L;

	private String name;
	private int beatsPerMinute;
	private List<Track> tracks;

	public Project(String name, int beatsPerMinute, Key key) {
		this.name = name;
		this.beatsPerMinute = beatsPerMinute;
		this.tracks = new LinkedList<Track>();
	}

	public String getName() {
		return name;
	}

	public int getBeatsPerMinute() {
		return beatsPerMinute;
	}

	public void addTrack(Track track) {
		tracks.add(track);
	}

	public void removeTrack(Track track) {
		tracks.remove(track);
	}

	public Track getTrack(int location) {
		return tracks.get(location);
	}

	public int size() {
		return tracks.size();
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof Project)) {
			return false;
		}

		Project project = (Project) obj;

		if (false == name.equals(project.getName())) {
			return false;
		}

		if (size() == project.size()) {
			for (int i = 0; i < size(); i++) {
				if (!getTrack(i).equals(project.getTrack(i))) {
					return false;
				}
			}

			return true;
		}

		return false;
	}

	@Override
	public String toString() {
		return "[Project] name=" + name + " beatsPerMinute=" + beatsPerMinute + " trackCount=" + size();
	}
}
