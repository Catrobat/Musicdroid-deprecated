package at.tugraz.musicdroid.dialog;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;

import at.tugraz.musicdroid.MainActivity;
import at.tugraz.musicdroid.R;
import at.tugraz.musicdroid.soundtracks.*;
import at.tugraz.musicdroid.types.SoundType;

public class AddSoundDialog extends BaseDialog implements OnItemClickListener,
OnItemLongClickListener {
	
	private static AddSoundDialog instance;
	private MainActivity mParent;
	private AddSoundAdapter mAddSoundButtonAdapter;
	
	
	private AddSoundDialog(Context context) {
		super(context);
		mParent = (MainActivity) context;
		mAddSoundButtonAdapter = new AddSoundAdapter(context,
				false);
	}

	public static AddSoundDialog getInstance() {
		if (instance == null) {
			throw new IllegalStateException("Error: Not initialized");
		}
		return instance;
	}

	public static void init(MainActivity mainActivity) {
		instance = new AddSoundDialog(mainActivity);
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_sound_menu);
		setTitle(R.string.dialog_add_sound_title);
		setCanceledOnTouchOutside(true);
		GridView gridView = (GridView) findViewById(R.id.gridview_add_sound_menu);
		gridView.setAdapter(mAddSoundButtonAdapter);
		gridView.setOnItemClickListener(this);
		gridView.setOnItemLongClickListener(this);
	}
	
	@Override
	public void onItemClick(AdapterView<?> adapterView, View button,
			int position, long id) {
		SoundType toolType = mAddSoundButtonAdapter.getSoundType(position);
		
		switch(toolType) {
			case DRUMS:
				SoundTrackDrums stvd = new SoundTrackDrums();
				mParent.addSoundTrack(new SoundTrackView(mParent, stvd));
				break;
			case PIANO:
				SoundTrackPiano stvp = new SoundTrackPiano();
				mParent.addSoundTrack(new SoundTrackView(mParent, stvp));
				break;
			case MIC:
				SoundTrackMic stvm = new SoundTrackMic();
				mParent.addSoundTrack(new SoundTrackView(mParent, stvm));
				break;		
			default:
				break;
		}
		dismiss();
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> adapterView, View button,
			int position, long id) {
		//Display help message
		return true;
	}
}
