package org.catrobat.musicdroid.dialog;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Environment;
import android.util.Log;
import org.catrobat.musicdroid.dialog.listener.DialogListener;

public class OpenFileDialog {
	private Activity activity = null;
	private String path = null;
	private String fileType = null;
	private String[] fileList = null;
	private DialogListener listener = null;
	
	 public OpenFileDialog(Activity activity, DialogListener listener, String path, String fileType) {
         this.activity = activity;
         this.listener = listener;
         this.path = path;
         this.fileType = fileType;
         
         checkPathExistsAndIsDirectory();
     }
	 
	 private void checkPathExistsAndIsDirectory()
	 {
		 File f = new File(path);
		 if(!f.exists() || !f.isDirectory())
		 {
			 if(!f.exists()) Log.i("OpenFileDialog", "File " + path + " does not exists");
			 if(!f.isDirectory()) Log.i("OpenFileDialog", "File " + path + " is no directory");
			 path = Environment.getExternalStorageDirectory().getAbsolutePath();
		 }
	 }
	 
	 public Dialog createFileDialog() {
         Dialog dialog = null;

         loadFileList();
         AlertDialog.Builder builder = new AlertDialog.Builder(activity);

         builder.setTitle(path);

         builder.setItems(fileList, new DialogInterface.OnClickListener() {
             public void onClick(DialogInterface dialog, int which) {
                 String fileChosen = fileList[which];
                 listener.onStringChanged(fileChosen);
             }
         });

         dialog = builder.show();
         return dialog;
     }
	 
	 public void showDialog() {
         createFileDialog().show();
     }

	 
	 private void loadFileList() {
         List<String> r = new ArrayList<String>();
         FilenameFilter filter = new FilenameFilter() {
             public boolean accept(File dir, String filename) {
                 File sel = new File(dir, filename);
                 if (!sel.canRead()) return false;
                 else {
                	 if(fileType != null)
                       return filename.endsWith(fileType);
                	 else
                	   return true;
                 }
             }
         };
         File file = new File(path);
         String[] fileListCurrent = file.list(filter);
         for (String fileName : fileListCurrent) {
             r.add(fileName);
             Log.i("OpenFileDialog", "Filename = " + fileName);
         }
         fileList = (String[]) r.toArray(new String[]{});
     }
	 
//	 private void removeAllFilesy()
//	 {
//		 for(int f = 0; f < fileList.length; f++)
//		 {
//			 File file = new File(path+fileList[f]);
//			 if(!file.exists())
//			 {
//				 Log.i("OpenFileDialog", "Something went wrong at remove File " + path + fileList[f]);
//			 }
//			 else if(!file.isDirectory())
//			 {
//				 file.delete();
//			 }
//			 else
//			 {
//				 Log.i("OpenFileDialog", "File is directory");
//			 }
//		 }
//	 }
	 
}
