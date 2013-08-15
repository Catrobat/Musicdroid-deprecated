package org.catrobat.musicdroid.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import org.catrobat.musicdroid.R;
import org.catrobat.musicdroid.dialog.listener.ExportDrumSoundDialogListener;
import org.catrobat.musicdroid.preferences.PreferenceManager;


@SuppressLint("ValidFragment")
public class ExportDrumSoundDialog extends DialogFragment implements OnSeekBarChangeListener {
	private SeekBar num_loops = null;
	private TextView duration_in_seconds = null;
	private int number_of_loops = NUMBER_OF_LOOPS_DEFAULT;
	private ExportDrumSoundDialogListener listener = null;
	public static final int NUMBER_OF_LOOPS_DEFAULT = 20;
	public static final int NUMBER_OF_BEATS_PER_LOOP = 4;
	
	public ExportDrumSoundDialog(ExportDrumSoundDialogListener listener)
	{
		this.listener = listener;
	}
	
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.export_drum_sound_menu, null);
        builder.setView(view);
                
        builder.setTitle(R.string.drum_sound_export_dialog_title)
               .setNegativeButton(R.string.dialog_export, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   listener.onIntChanged(number_of_loops);
                   }
               })
               .setPositiveButton(R.string.settings_button_discard, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // User cancelled the dialog
                   }
               });
        
        
        AlertDialog dialog = builder.create();
       
        num_loops = (SeekBar) view.findViewById(R.id.seekbar_num_loops);
        num_loops.setMax(100);
        num_loops.setProgress(NUMBER_OF_LOOPS_DEFAULT);
        num_loops.incrementProgressBy(2);
        num_loops.setOnSeekBarChangeListener(this);

        duration_in_seconds = (TextView) view.findViewById(R.id.drum_sound_export_duration_text);
        duration_in_seconds.setText(buildDurationString(NUMBER_OF_LOOPS_DEFAULT));
        
        return dialog;
    }
    
    @Override
    public void onResume()
    {
    	super.onResume();
    	Log.i("ExportDrumSound", "RESUME");
    }

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		if(progress == 0)
			progress = 1;
		number_of_loops = progress;
		duration_in_seconds.setText(buildDurationString(progress));
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}
	
	private int secondsByBPM(int progress)
	{
		return NUMBER_OF_BEATS_PER_LOOP*progress*60/
					PreferenceManager.getInstance().getPreference(PreferenceManager.METRONOM_BPM_KEY);
	}
	
	private String buildDurationString(int progress)
	{
		return "Number of Loops: " + progress + " / Duration: " + secondsByBPM(progress) + " Sec";
	}
}
