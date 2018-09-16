package qimonjy.cn.ffllibrary.game;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;

import qimonjy.cn.ffllibrary.R;

public class SoundPlayUtils {
    // SoundPool对象
//    public static SoundPool mSoundPlayer = new SoundPool(10,
//            AudioManager.STREAM_SYSTEM, 5);
    private SoundPool mSoundPlayer;
    private static SoundPlayUtils soundPlayUtils;
    // 上下文
    private static Context mContext;
    private int maxStreams = 50;
    //    private int bgSoundId;
//    private int wordSoundId;
    private int buttonSoundId;
    private int turnCardSounId;
    private int countDownSounId;
    private int badturnSoundId;
//    结束游戏
private int finishSoundId;

    private SoundPlayUtils(Context context) {
        mContext = context;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            SoundPool.Builder builder = new SoundPool.Builder();
            builder.setMaxStreams(maxStreams);
            AudioAttributes.Builder audioBuild = new AudioAttributes.Builder();
            audioBuild.setLegacyStreamType(AudioManager.STREAM_MUSIC);
            AudioAttributes audioAttributes = audioBuild.build();
            builder.setAudioAttributes(audioAttributes);
            mSoundPlayer = builder.build();
        } else {
            mSoundPlayer = new SoundPool(maxStreams, AudioManager.STREAM_MUSIC, 0);
        }
        buttonSoundId = mSoundPlayer.load(mContext, R.raw.helpsonund, 1);
//        bgSoundId = mSoundPlayer.load(mContext, R.raw.game_activity_bgm, 2);
        turnCardSounId = mSoundPlayer.load(mContext, R.raw.turncardsound, 1);
        countDownSounId = mSoundPlayer.load(mContext, R.raw.countdownsound, 1);
        badturnSoundId = mSoundPlayer.load(mContext, R.raw.badturnsound, 1);
        finishSoundId= mSoundPlayer.load(mContext, R.raw.statissound, 1);

    }

    private float getVolume() {
        float current = ((AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE)).getStreamVolume(AudioManager.STREAM_MUSIC);
        float max = ((AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE)).getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        return current / max;
    }
    /**
     * 初始化
     *
     * @param context
     */
    public static SoundPlayUtils init(Context context) {
        if (soundPlayUtils == null) {
            soundPlayUtils = new SoundPlayUtils(context);
        }
        return soundPlayUtils;
    }
    /**
     * 播放Button声音
     */
    public void playButtonSound() {
        mSoundPlayer.play(buttonSoundId, getVolume(), getVolume(), 0, 0, 1);
    }
    /*转牌的声音*/
    public void playTurnCardSound() {
        mSoundPlayer.play(turnCardSounId, getVolume(), getVolume(), 0, 0, 1);
    }
    /*转错牌的声音*/
    public void playbadTurnCardSound() {
        mSoundPlayer.play(badturnSoundId, getVolume(), getVolume(), 0, 0, 1);
    }
    /*倒计时声音*/
    public void playCountDownSound() {
        mSoundPlayer.play(countDownSounId, getVolume(), getVolume(), 0, 0, 1);
    }
    /**
     * 结束游戏声音
     */
    public void playFinishSound() {
        mSoundPlayer.play(finishSoundId, getVolume(), getVolume(), 0, 0, 1);
    }
}