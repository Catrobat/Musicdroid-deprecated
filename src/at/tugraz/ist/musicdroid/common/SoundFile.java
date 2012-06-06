package at.tugraz.ist.musicdroid.common;

import java.io.File;
import at.tugraz.ist.musicdroid.common.Constants;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class SoundFile {

	public static final int REQUEST_CODE = 0;
	ArrayList<String> file_list_ = new ArrayList<String>();
	DataManagement management_ = new DataManagement();

	private SoundFile() {

	}

	

	public String getFilePath() {
		return file_list_.get(file_list_.size() - 1);
	}
}
