package org.catrobat.musicdroid.drums;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

import org.catrobat.musicdroid.DrumsActivity;
import org.catrobat.musicdroid.R;
import org.catrobat.musicdroid.dialog.ExportDrumSoundDialog;
import org.catrobat.musicdroid.dialog.listener.ExportDrumSoundDialogListener;
import org.catrobat.musicdroid.types.DrumType;

import java.util.ArrayList;
import java.util.HashMap;

public class LayoutDrums implements OnClickListener {
	private static final String TAG = LayoutDrums.class.getSimpleName();
	private Context context = null;
	private RelativeLayout soundRowBox = null;
	private RelativeLayout addDrumsToSoundMixerBox = null;
	private int localId = 5010;
	private boolean unsavedChanges = false;
	private ArrayList<DrumSoundRow> drumSoundRowsArray;
	private HashMap<String, DrumType> stringToDrumTypeMap;
	private ExportDrumSoundDialog addToSoundMixerDialog = null;

	public LayoutDrums(Context c) {
		this.context = c;
		this.soundRowBox = (RelativeLayout) ((DrumsActivity) context).findViewById(R.id.drums_drum_row_box);
		this.drumSoundRowsArray = new ArrayList<DrumSoundRow>();
		this.stringToDrumTypeMap = new HashMap<String, DrumType>();
		this.addDrumsToSoundMixerBox = (RelativeLayout) ((DrumsActivity) context)
				.findViewById(R.id.drums_add_to_sound_mixer_box);
		this.addToSoundMixerDialog = new ExportDrumSoundDialog(new ExportDrumSoundDialogListener((DrumsActivity) c));

		populateDrumToDrumSoundMap();
		initializeDrumSoundRows();
		addDrumsToSoundMixerBox.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.drums_add_to_sound_mixer_box) {
			handleOnAddToSoundmixerClick();
		}
	}

	public void loadPresetToDrumLayout(DrumPreset preset) {
		Log.i(TAG, "loadPresetToDrumLayout");
		if (preset.getDrumSoundRowsArray().size() != drumSoundRowsArray.size()) {
			Log.e(TAG, "Error at loading preset: to few/much drum rows");
		}
		for (int rowIndex = 0; rowIndex < drumSoundRowsArray.size(); rowIndex++) {
			DrumSoundRow presetRow = preset.getDrumSoundRowsArray().get(rowIndex);
			drumSoundRowsArray.get(rowIndex).updateRow(presetRow);
		}
	}

	public void resetLayout() {
		soundRowBox.removeAllViews();
		drumSoundRowsArray.clear();
		initializeDrumSoundRows();
	}

	private void handleOnAddToSoundmixerClick() {
		addToSoundMixerDialog.show(((DrumsActivity) context).getFragmentManager(), null);
	}

	private void populateDrumToDrumSoundMap() {
		stringToDrumTypeMap.put(context.getResources().getString(DrumType.BASE_DRUM.getNameResource()),
				DrumType.BASE_DRUM);
		stringToDrumTypeMap.put(context.getResources().getString(DrumType.SNARE_DRUM.getNameResource()),
				DrumType.SNARE_DRUM);
		stringToDrumTypeMap.put(context.getResources().getString(DrumType.SNARE_DRUM_HARD.getNameResource()),
				DrumType.SNARE_DRUM_HARD);
		stringToDrumTypeMap.put(context.getResources().getString(DrumType.HIGH_HAT_CLOSED.getNameResource()),
				DrumType.HIGH_HAT_CLOSED);
		stringToDrumTypeMap.put(context.getResources().getString(DrumType.HIGH_HAT_OPEN.getNameResource()),
				DrumType.HIGH_HAT_OPEN);
		stringToDrumTypeMap.put(context.getResources().getString(DrumType.TOM_HIGH.getNameResource()),
				DrumType.TOM_HIGH);
		stringToDrumTypeMap.put(context.getResources().getString(DrumType.TOM_LOW.getNameResource()), DrumType.TOM_LOW);
		stringToDrumTypeMap.put(context.getResources().getString(DrumType.TAMBOURINE.getNameResource()),
				DrumType.TAMBOURINE);
		stringToDrumTypeMap.put(context.getResources().getString(DrumType.CRASH_ONE.getNameResource()),
				DrumType.CRASH_ONE);
		stringToDrumTypeMap.put(context.getResources().getString(DrumType.CRASH_TWO.getNameResource()),
				DrumType.CRASH_TWO);
	}

	private void initializeDrumSoundRows() {
		DrumSoundPositionRow position = new DrumSoundPositionRow(context);
		LayoutDrumSoundPositionRow positionLayout = position.getLayout();
		positionLayout.setId(getNewId());
		((DrumsActivity) context).addObserverToEventHandler(position);
		soundRowBox.addView(positionLayout);

		int idBaseDrum = addNewDrumSoundRow(DrumType.BASE_DRUM, positionLayout.getId());
		int idSnareDrum = addNewDrumSoundRow(DrumType.SNARE_DRUM, idBaseDrum);
		int idHighHatClosed = addNewDrumSoundRow(DrumType.HIGH_HAT_CLOSED, idSnareDrum);
		int idHightHatOpen = addNewDrumSoundRow(DrumType.HIGH_HAT_OPEN, idHighHatClosed);
		int idHighTom = addNewDrumSoundRow(DrumType.TOM_HIGH, idHightHatOpen);
		int idLowTom = addNewDrumSoundRow(DrumType.TOM_LOW, idHighTom);
	}

	private int addNewDrumSoundRow(DrumType type, int alignBelowId) {
		DrumSoundRow soundRow = new DrumSoundRow(context, this, type);
		LayoutDrumSoundRow soundRowLayout = soundRow.getLayout();
		soundRowLayout.setId(getNewId());
		soundRowLayout.align(RelativeLayout.BELOW, alignBelowId);
		soundRowBox.addView(soundRowLayout);
		drumSoundRowsArray.add(soundRow);
		((DrumsActivity) context).addObserverToEventHandler(soundRow);
		return soundRowLayout.getId();
	}

	private int getNewId() {
		localId = localId + 1;
		return localId;
	}

	public ArrayList<DrumSoundRow> getDrumSoundRowsArray() {
		return drumSoundRowsArray;
	}

	public DrumType getDrumTypeByString(String str) {
		return stringToDrumTypeMap.get(str);
	}

	public HashMap<String, DrumType> getStringToDrumTypeMap() {
		return stringToDrumTypeMap;
	}

	public boolean hasUnsavedChanges() {
		return unsavedChanges;
	}

	public void setUnsavedChanges(boolean state) {
		unsavedChanges = state;
	}

}
