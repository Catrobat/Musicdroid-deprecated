package at.tugraz.musicdroid;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.Log;

public class SoundManager {
	 
    static private SoundManager _instance;
    private static SoundPool mSoundPool;
    private static HashMap<Integer, Integer> mSoundPoolMap;
    private static HashMap<Integer, Integer> mSoundPlayMap;
    private static AudioManager  mAudioManager;
    private static Context mContext;

    private SoundManager(){}

    /**
     * Requests the instance of the Sound Manager and creates it
     * if it does not exist.
     *
     * @return Returns the single instance of the SoundManager
     */
    static synchronized public SoundManager getInstance()
    {
        if (_instance == null)
          _instance = new SoundManager();
        return _instance;
     }

    /**
     * Initialises the storage for the sounds
     *
     * @param theContext The Application context
     */
    public static  void initSounds(Context theContext)
    {
         mContext = theContext;
         mSoundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
         mSoundPoolMap = new HashMap<Integer, Integer>();
         mSoundPlayMap = new HashMap<Integer, Integer>();
         mAudioManager = (AudioManager)mContext.getSystemService(Context.AUDIO_SERVICE);
    } 

    /**
     * Add a new Sound to the SoundPool
     *
     * @param Index - The Sound Index for Retrieval
     * @param SoundID - The Android ID for the Sound asset.
     */
    public static void addSound(int Index,int SoundID)
    {
            mSoundPoolMap.put(Index, mSoundPool.load(mContext, SoundID, 1));
    }

    
    public static int loadSound(int raw_id)
    {
    	int position = mSoundPoolMap.size()+1;
    	mSoundPoolMap.put(position, mSoundPool.load(mContext, raw_id, 1));
    	return position;
    }    
    /**
     * Loads the various sound assets
     * Currently hardcoded but could easily be changed to be flexible.
     */
    public static void loadSounds()
    {
            mSoundPoolMap.put(1, mSoundPool.load(mContext, R.raw.test_midi, 1));
            mSoundPoolMap.put(2, mSoundPool.load(mContext, R.raw.test_wav, 1));
    }

    public static void playSoundByRawId(int raw_id, float speed)
    {
        Iterator<Entry<Integer, Integer>> it = mSoundPoolMap.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry<Integer, Integer> pairs = (Entry<Integer, Integer>)it.next();
            if(pairs.getValue() == raw_id)
            {
              playSound(pairs.getKey(), speed);
              return;
            }
        }
    }
    
    public static void stopSoundByRawId(int raw_id)
    {
        Iterator<Entry<Integer, Integer>> it = mSoundPoolMap.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry<Integer, Integer> pairs = (Entry<Integer, Integer>)it.next();
            if(pairs.getValue() == raw_id)
            {
              stopSound(pairs.getKey());
              return;
            }
        }
    }
    
    /**
     * Plays a Sound
     *
     * @param index - The Index of the Sound to be played
     * @param speed - The Speed to play not, not currently used but included for compatibility
     */
    public static void playSound(int index,float speed)
    {
            float streamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            streamVolume = streamVolume / mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            Integer stream_id = mSoundPool.play(mSoundPoolMap.get(index), streamVolume, streamVolume, 1, 0, speed);
            Log.e("PUT: ", "" + index + " " + stream_id);
            mSoundPlayMap.put(index, stream_id);
    }

    
    public static void stopAllSounds()
    {
        Iterator<Entry<Integer, Integer>> it = mSoundPlayMap.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry<Integer, Integer> pairs = (Entry<Integer, Integer>)it.next();
            mSoundPool.stop(pairs.getValue());
        }
        mSoundPlayMap.clear();
 
    }
    
    /**
     * Stop a Sound
     * @param index - index of the sound to be stopped
     */
    public static void stopSound(int index)
    {
            mSoundPool.stop(mSoundPlayMap.get(index));
            mSoundPlayMap.remove(index);
    }

    /**
     * Deallocates the resources and Instance of SoundManager
     */
    public static void cleanup()
    {
        mSoundPool.release();
        mSoundPool = null;
        mSoundPoolMap.clear();
        mAudioManager.unloadSoundEffects();
        _instance = null;
    }
    
    
    public static int getSoundfileDuration(int soundfile_id)
    {
		MediaPlayer player = MediaPlayer.create(mContext, soundfile_id);
		int duration = player.getDuration();
		return duration/1000; 	
    }

}