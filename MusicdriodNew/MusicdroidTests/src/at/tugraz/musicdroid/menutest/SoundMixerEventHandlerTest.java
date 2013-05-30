package at.tugraz.musicdroid.menutest;

import java.util.Observable;
import java.util.Observer;

import com.jayway.android.robotium.solo.Solo;

import android.test.ActivityInstrumentationTestCase2;
import at.tugraz.musicdroid.MainActivity;
import at.tugraz.musicdroid.R;
import at.tugraz.musicdroid.soundmixer.SoundMixer;
import at.tugraz.musicdroid.soundmixer.SoundMixerEventHandler;
import at.tugraz.musicdroid.soundmixer.timeline.Timeline;
import at.tugraz.musicdroid.types.SoundType;

public class SoundMixerEventHandlerTest extends ActivityInstrumentationTestCase2<MainActivity> implements Observer {
	protected SoundMixerEventHandler eventHandler = null;
	protected UITestHelper uiHelper = null;
	protected Solo solo = null;
	
	public SoundMixerEventHandlerTest(Class<MainActivity> activityClass) {
		super(activityClass);
	}

	public SoundMixerEventHandlerTest() {
		super(MainActivity.class);
	}
	
	@Override
	protected void setUp() {
		solo = new Solo(getInstrumentation(), getActivity());
		uiHelper = new UITestHelper(solo, getActivity());
		eventHandler = SoundMixer.getInstance().getEventHandler();
		eventHandler.addObserver(this);
	}

	public void testObserver()
	{
		uiHelper.addTrack(SoundType.DRUMS);
		solo.clickOnView(getActivity().findViewById(R.id.btn_play));
		solo.sleep(25000);
		
		solo.clickOnView(getActivity().findViewById(R.id.btn_rewind));
		
		int[] timelineLocation = {0,0};
		Timeline timeline = (Timeline) getActivity().findViewById(R.id.timeline);
		timeline.getLocationOnScreen(timelineLocation);
		
		int clickXPosition = timelineLocation[0]+200;
		uiHelper.addTimelineMarker(clickXPosition, timelineLocation[1], R.string.timeline_menu_entry_start_point);
		
		solo.clickOnView(getActivity().findViewById(R.id.btn_play));
		solo.sleep(10000);
		
	}
	
	@Override
	public void update(Observable observable, Object data) {
		int i = (Integer) data;
		assertTrue(i >= eventHandler.getStartPoint() && i <= eventHandler.getEndPoint());
	}
}
