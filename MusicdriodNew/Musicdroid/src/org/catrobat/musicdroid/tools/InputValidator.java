package org.catrobat.musicdroid.tools;

import android.util.Log;

public class InputValidator {
	final static String[] RESERVED_CHARS = { "|", "\\", "?", "*", "<", "\"", ":", ">", "[", "]", "'" };

	public static boolean isValidFilename(String filename) {
		for (String c : RESERVED_CHARS) {
			if (filename.contains(c)) {
				return false;
			}
		}
		return true;
	}

	public static boolean isValidFilenameWithoutPath(String filename) {
		Log.i("InputValidator", "Filename = " + filename);
		return isValidFilename(filename) && !filename.contains("/");
	}

}
