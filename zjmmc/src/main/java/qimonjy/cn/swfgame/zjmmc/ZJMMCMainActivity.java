package qimonjy.cn.swfgame.zjmmc;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import org.dom4j.Document;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import butterknife.ButterKnife;
import butterknife.OnClick;
import qimonjy.cn.commonlibrary.FileIOandOperation;
import qimonjy.cn.commonlibrary.XmlUtils;
import qimonjy.cn.swfgame.zjmmc.data.ConfigData;
import qimonjy.cn.swfgame.zjmmc.data.LetterData;
import qimonjy.cn.swfgame.zjmmc.utils.SoundPlayUtils;


public class ZJMMCMainActivity extends AppCompatActivity {
    private static final int[] bodyPositoinList = new int[]{R.id.body_1_1, R.id.body_1_2, R.id.body_1_3, R.id.body_1_4, R.id.body_1_5, R.id.body_1_6, R.id.body_1_7, R.id.body_1_8,
            R.id.body_2_1, R.id.body_2_2, R.id.body_2_3, R.id.body_2_4, R.id.body_2_5, R.id.body_2_6, R.id.body_2_7};
    private static final int[] bodyBackground = new int[]{R.drawable.caterpillar_body_1, R.drawable.caterpillar_body_2};
    private static final String CONFIG_FILE_PATH = "/sdcard/Android/data/com.qimon.studentcircle/cache/config.xml";//"/sdcard/config.xml";//

    /**
     * 读取的配置参数
     */
    private String config;

    private ArrayList<ConfigData> dataList = new ArrayList<>();
    /**
     * 媒体播放
     */
    private MediaPlayer mediaPlayer;
    /**
     * 是否播放背景音乐。
     */
    private boolean isPlayBgm = true;
    private SoundPlayUtils soundPlayUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zjmmc_activity_main);
        ButterKnife.bind(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                mediaPlayer = MediaPlayer.create(ZJMMCMainActivity.this, R.raw.welcom_activity_bgm);
                mediaPlayer.setLooping(true);
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        if (!mediaPlayer.isPlaying() && isPlayBgm) {
                            mediaPlayer.start();
                        }
                    }
                });
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                buildData();
                soundPlayUtils = SoundPlayUtils.init(getApplicationContext(), dataList);
            }
        }).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mediaPlayer != null && !mediaPlayer.isPlaying() && isPlayBgm) {
            mediaPlayer.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    /**
     * 读取并构建数据对象列表。
     */
    private void buildData() {
        config = FileIOandOperation.readFile(CONFIG_FILE_PATH, "UTF-8").toString();
        if (!TextUtils.isEmpty(config)) {
            Document document = XmlUtils.getDocument(config);
            if (document != null) {
                Element root = document.getRootElement();
                dataList.clear();
                int i = 1;
                for (Iterator it = root.elementIterator(); it.hasNext(); ) {
                    Element element = (Element) it.next();
                    String chinese = element.attribute("chinese").getText();
                    String mp3Url = element.attribute("mp3Url").getText();
                    String text = element.getText();
                    ConfigData data = new ConfigData();
                    data.setChinese(chinese);
                    data.setMp3Url(mp3Url);
                    data.setText(text);
                    data.setIndex(i);
                    i++;
                    if (!TextUtils.isEmpty(text)) {
                        char[] letter = text.toCharArray();
                        if (letter != null && letter.length > 0) {
                            ArrayList<Integer> position = new ArrayList<>();
                            for (Integer p : bodyPositoinList) {
                                position.add(p);
                            }
                            for (char l : letter) {
                                LetterData letterData = new LetterData();
                                letterData.setLetter(l);
                                int number = new Random().nextInt(2);
                                letterData.setBackgroundID(bodyBackground[number]);
                                number = new Random().nextInt(17);
                                letterData.setPositionID(position.remove(number % position.size()));
//                                System.out.println("build data : " + chinese + "  " + mp3Url + "  " + text + "  " + data.getBackgroundID() + "  " + data.getPositionID());
                                data.addLetterData(letterData);
                            }
                            dataList.add(data);
                        }
                    }
                }
            }
        }
    }

    public static final String DATA_LIST = "DATA_LIST";

    @OnClick({R2.id.help})
    public void onHelpClicked() {
//        soundPlayUtils.playButtonSound();
        Intent intent = new Intent(this, ZJMMCHelpActivity.class);
        intent.putExtra(DATA_LIST, dataList);
        startActivity(intent);
    }

    @OnClick({R2.id.start})
    public void onStartClicked() {
//        soundPlayUtils.playButtonSound();
        Intent intent = new Intent(this, ZJMMCGameActivity.class);
        intent.putExtra(DATA_LIST, dataList);
        startActivity(intent);
    }
}
