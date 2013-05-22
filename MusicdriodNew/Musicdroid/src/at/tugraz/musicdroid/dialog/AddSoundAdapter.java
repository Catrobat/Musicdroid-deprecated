package at.tugraz.musicdroid.dialog;



import java.util.ArrayList;

import at.tugraz.musicdroid.R;
import at.tugraz.musicdroid.R.id;
import at.tugraz.musicdroid.R.layout;
import at.tugraz.musicdroid.types.SoundType;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AddSoundAdapter extends BaseAdapter {

	private Context mContext;

	private ArrayList<SoundType> mButtonsList;

	public AddSoundAdapter(Context context, boolean fromCatrobat) {
		this.mContext = context;
		initButtons();
	}

	private void initButtons() {
		mButtonsList = new ArrayList<SoundType>();
		mButtonsList.add(SoundType.PIANO);
		mButtonsList.add(SoundType.DRUMS);
		mButtonsList.add(SoundType.MIC);
	}

	@Override
	public int getCount() {
		return mButtonsList.size();
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
		return mButtonsList.get(position);
	}

	public ArrayList<SoundType> getSoundTypes() {
		return mButtonsList;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		if (rowView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			rowView = inflater.inflate(R.layout.add_sound_button, null);
			ImageView imageView = (ImageView) rowView
					.findViewById(R.id.add_sound_button_image);
			imageView.setImageResource(mButtonsList.get(position)
					.getImageResource());
			TextView textView = (TextView) rowView
					.findViewById(R.id.add_sound_button_text);
			textView.setText(mButtonsList.get(position).getNameResource());
		}
		return rowView;
	}

}
