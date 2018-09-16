package qimonjy.cn.swfgame.zjmmc.utils;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.ArrayList;

import qimonjy.cn.swfgame.zjmmc.R;
import qimonjy.cn.swfgame.zjmmc.data.ConfigData;

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
    private int winSoundId;
    private int badSoundId;
    private int chSoundId;

    private SoundPlayUtils(Context context, ArrayList<ConfigData> dataList) {
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
        // 初始化声音
        if (dataList != null && dataList.size() > 0) {
            for (int i = 0; i < dataList.size(); i++) {
                mSoundPlayer.load(dataList.get(i).getMp3Url().substring(7), 1);
            }
        }
        buttonSoundId = mSoundPlayer.load(mContext, R.raw.press_button_sound, 1);
//        bgSoundId = mSoundPlayer.load(mContext, R.raw.game_activity_bgm, 2);
        winSoundId = mSoundPlayer.load(mContext, R.raw.game_activity_win_sound, 1);
        badSoundId = mSoundPlayer.load(mContext, R.raw.game_activity_bad_sound, 1);
        chSoundId = mSoundPlayer.load(mContext, R.raw.game_activity_choose_sound, 1);

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
    public static SoundPlayUtils init(Context context, ArrayList<ConfigData> dataList) {
        if (soundPlayUtils == null) {
            soundPlayUtils = new SoundPlayUtils(context, dataList);
        }
        return soundPlayUtils;
    }

    /**
     * 播放Button声音
     */
    public void playButtonSound() {
        mSoundPlayer.play(buttonSoundId, getVolume(), getVolume(), 0, 0, 1);
    }

    /**
     * 设置单词声音
     *
     * @param path
     */
//    public void setWordSound(String path) {
//        wordSoundId = mSoundPlayer.load(path, 1);
//    }

    /**
     * 单词id从1开始
     * 播放Word声音
     */
    public void playWordSound(int wordSoundId) {
        mSoundPlayer.play(wordSoundId, getVolume(), getVolume(), 0, 0, 1);
    }

//    /**
//     * 播放背景声音
//     */
//    public void playBgSound() {
//        mSoundPlayer.play(bgSoundId, getVolume(), getVolume(), 0, -1, 1);
//    }
//
//    /**
//     * 停止背景声音
//     */
//    public void stopBgSound() {
//        mSoundPlayer.stop(bgSoundId);
//    }

    /**
     * 播放正确声音
     */
    public void playWinSound() {
        mSoundPlayer.play(winSoundId, getVolume(), getVolume(), 0, 0, 1);
    }

    /**
     * 播放错误声音
     */
    public void playBadSound() {
        mSoundPlayer.play(badSoundId, getVolume(), getVolume(), 0, 0, 1);
    }

    /**
     * 播放选择声音
     */
    public void playChooseSound() {
        mSoundPlayer.play(chSoundId, getVolume(), getVolume(), 0, 0, 1);
    }
}