package qimonjy.cn.swfgame.zjmmc;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.dom4j.Document;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import qimonjy.cn.commonlibrary.FileIOandOperation;
import qimonjy.cn.commonlibrary.PermissionUtils;
import qimonjy.cn.commonlibrary.XmlUtils;
import qimonjy.cn.swfgame.zjmmc.data.ConfigData;
import qimonjy.cn.swfgame.zjmmc.data.GoalData;
import qimonjy.cn.swfgame.zjmmc.data.LetterData;
import qimonjy.cn.swfgame.zjmmc.data.MyRect;
import qimonjy.cn.swfgame.zjmmc.utils.SoundPlayUtils;



public class ZJMMCGameActivity extends Activity {
        private static final int[] bodyPositoinList = new int[]{R.id.body_1_1, R.id.body_1_2, R.id.body_1_3, R.id.body_1_4, R.id.body_1_5, R.id.body_1_6, R.id.body_1_7, R.id.body_1_8,
            R.id.body_2_1, R.id.body_2_2, R.id.body_2_3, R.id.body_2_4, R.id.body_2_5, R.id.body_2_6, R.id.body_2_7};
    private static final int[] bodyBackground = new int[]{R.drawable.caterpillar_body_1, R.drawable.caterpillar_body_2};
    private static final String CONFIG_FILE_PATH = "/sdcard/Android/data/com.qimon.studentcircle/cache/config.xml";//"/sdcard/config.xml";//
    @BindView(R2.id.time)
    TextView time;
    @BindView(R2.id.progress)
    TextView progress;
    @BindView(R2.id.chiness)
    TextView chiness;
    @BindView(R2.id.closeVoice)
    CheckBox closeVoice;
    @BindView(R2.id.playVoice)
    ImageView playVoice;
    @BindView(R2.id.cancel)
    ImageView cancel;
    LinearLayout caterpillarLayout;
    TextView dragText;
    LinearLayout drag;
    /**
     * 读取的配置参数
     */
//    private String config;

    private ArrayList<ConfigData> dataList = new ArrayList<>();
    /**
     * 当前的位置。
     */
    private int currentStep;
    /**
     * 获取状态栏高度
     */
    int statusBarHeight1 = 0;
    /**
     * 媒体播放
     */
    private MediaPlayer mediaPlayer;
    /**
     * 是否播放背景音乐。
     */
    private boolean isPlayBgm = true;
    /**
     * 读取的配置参数
     */
    private String config;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zjmmc_activity_game);
        ButterKnife.bind(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                mediaPlayer = MediaPlayer.create(ZJMMCGameActivity.this, R.raw.game_activity_bgm);
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
        dataList = (ArrayList<ConfigData>) getIntent().getSerializableExtra(ZJMMCMainActivity.DATA_LIST);
        initView();
//        if (!PermissionUtils.checkPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 100)) {
//            return;//没有读取文件的权限
//        }
        buildData();
        soundPlayUtils = SoundPlayUtils.init(getApplicationContext(), dataList);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }

    private boolean first = true;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (first) {
            first = false;
            buildView();
            startTimer();
        }
    }

    Timer timer = new Timer();
    private int ALL_TIME = 30;
    private int T = 1000;
    private int timeNumber;

    private void startTimer() {
//        timer.cancel();
        timer = new Timer();
        timeNumber = ALL_TIME;
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
//                time = time - 1;
                Message message = new Message();
                message.what = 4;
                message.arg1 = --timeNumber;
                handler.sendMessage(message);
            }
        };
        timer.schedule(task, 0, T);
    }

    private ImageView winAnimalImage, badAnimalImage;
    private AnimationDrawable winAnimationDrawable, badAnimationDrawable;
    private SoundPlayUtils soundPlayUtils;

    private void initView() {
        playVoice.setSoundEffectsEnabled(false);
        closeVoice.setSoundEffectsEnabled(false);
//        cancel.setSoundEffectsEnabled(false);
        closeVoice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isPlayBgm = !isChecked;
                if (!isChecked) {
                    if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
                        mediaPlayer.start();
                    }
                } else {
                    if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                    }
                }
            }
        });
        caterpillarLayout = (LinearLayout) findViewById(R.id.caterpillarLayout);
        dragText = (TextView) findViewById(R.id.dragText);
        drag = (LinearLayout) findViewById(R.id.drag);
        winAnimalImage = (ImageView) findViewById(R.id.animation_win);
        badAnimalImage = (ImageView) findViewById(R.id.animation_bad);
        new Thread(new Runnable() {
            @Override
            public void run() {
                winAnimationDrawable = (AnimationDrawable) getResources().getDrawable(
                        R.drawable.win_animal);
                badAnimationDrawable = (AnimationDrawable) getResources().getDrawable(
                        R.drawable.bad_animal);
                handler.sendEmptyMessage(1);
            }
        }).start();
        //获取status_bar_height资源的ID
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight1 = getResources().getDimensionPixelSize(resourceId);
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
                for (Iterator it = root.elementIterator(); it.hasNext(); ) {
                    Element element = (Element) it.next();
                    String chinese = element.attribute("chinese").getText();
                    String mp3Url = element.attribute("mp3Url").getText();
                    String text = element.getText();
                    ConfigData data = new ConfigData();
                    data.setChinese(chinese);
                    data.setMp3Url(mp3Url);
                    data.setText(text);
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

    private ConfigData currentConfigData;
    private ArrayList<LetterData> currentLetterList;
    private LetterData currentLetterData;
    private MyRect dragLetterRect;

    private void buildView() {
        goalInNumber = 0;
        if (dataList.size() > currentStep) {
            currentConfigData = dataList.get(currentStep);
            currentLetterList = currentConfigData.getLetterData();
            ArrayList<LetterData> letterDatas = currentConfigData.getLetterData();
            for (int i = 0; i < letterDatas.size(); i++) {
                LetterData l = letterDatas.get(i);
                int position = l.getPositionID();
                int background = l.getBackgroundID();
                LinearLayout item = (LinearLayout) findViewById(position);
                //AutoMeasureView.measureView(item);
                int[] screen = new int[2];
                item.getLocationOnScreen(screen);
                System.out.println("view rect " + item.getMeasuredWidth() + "   " + item.getMeasuredHeight() + "  " + screen[0] + "  " + (screen[1] - statusBarHeight1));
                l.setEar(new MyRect(screen[0], screen[1] - statusBarHeight1, screen[0] + item.getMeasuredWidth(), screen[1] + item.getMeasuredHeight() - statusBarHeight1));
                item.setBackgroundResource(background);
                ((TextView) item.getChildAt(0)).setText(l.getLetter() + "");
                item.setVisibility(View.VISIBLE);
            }
            buildBodys(letterDatas.size());
            chiness.setText(currentConfigData.getChinese());
            progress.setText((currentStep + 1) + "/" + dataList.size());

//            soundPlayUtils.setWordSound(currentConfigData.getMp3Url());
            soundPlayUtils.playWordSound(currentConfigData.getIndex());
        }
    }

    private ArrayList<GoalData> goalDataArrayList = new ArrayList<>();

    private void buildBodys(int length) {
        if (length <= 0) {
            return;
        }
        goalDataArrayList.clear();
        for (int i = 1; i <= length; i++) {
            GoalData goalData = new GoalData();
            LinearLayout layout = getBody();
            layout.setBackgroundResource(R.drawable.body_ear);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.caterpillar_tail_width), getResources().getDimensionPixelSize(R.dimen.caterpillar_tail_height));
            params.setMargins(getResources().getDimensionPixelSize(R.dimen.caterpillar_body_left_margin), 0, 0, 0);
            layout.setLayoutParams(params);
            caterpillarLayout.addView(layout, i);

            goalData.setLayout(layout);
            goalDataArrayList.add(goalData);
        }
    }

    private LinearLayout getBody() {
        return (LinearLayout) LayoutInflater.from(this).inflate(R.layout.caterpillar_body_layout, null);
    }

    /**
     * 隐藏当前显示的字母View
     *
     * @param configData
     */
    private void disappearView(ConfigData configData) {

        ArrayList<LetterData> letterDatas = configData.getLetterData();
        if (caterpillarLayout.getChildCount() >= letterDatas.size() + 2) {// 首尾，各增加1
            for (int i = 0; i < letterDatas.size(); i++) {
                LetterData position = letterDatas.get(i);
                LinearLayout item = (LinearLayout) findViewById(position.getPositionID());
                item.setVisibility(View.INVISIBLE);

                caterpillarLayout.removeViewAt(1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 100:
                if (PermissionUtils.verifyPermissions(grantResults)) {
                    // Permission Granted
                    // do you action
                    buildData();
                    buildView();
                } else {
                    // Permission Denied
                    Toast.makeText(this, "部分权限被拒绝,程序可能会出现异常", Toast.LENGTH_SHORT).show();
                    this.finish();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @OnClick({R2.id.playVoice})
    public void onPlayVoiceClicked() {
        soundPlayUtils.playWordSound(currentConfigData.getIndex());
    }

    @OnClick({R2.id.cancel})
    public void onCancelClicked() {
        soundPlayUtils.playButtonSound();
        disappearView(dataList.get(currentStep));
        buildView();
    }

    private float downX, downY;
    /**
     * 已经选择了几个单词
     */
    private int goalInNumber;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                //按下
                downX = event.getRawX();
                downY = event.getRawY();
                findTouchLetter(event);
                buildGoalViewRect();
                break;
            case MotionEvent.ACTION_MOVE:
                //移动
                if (currentLetterData != null) {
                    drag.layout((int) (dragLetterRect.left + event.getRawX() - downX), (int) (dragLetterRect.top + event.getRawY() - downY), (int) (dragLetterRect.right + event.getRawX() - downX), (int) (dragLetterRect.bottom + event.getRawY() - downY));
//                    System.out.println("  letter " + (int) (dragLetterRect.left + event.getRawX() - downX) + "  " + (int) (dragLetterRect.top + event.getRawY() - downY));
                }
                break;
            case MotionEvent.ACTION_UP:
                //松开
                if (currentLetterData != null) {
                    showTouchLetter(false, 0, "", null);
                    if (!checkGoalIn(event)) {
                        findViewById(currentLetterData.getPositionID()).setVisibility(View.VISIBLE);
                    } else {
                        soundPlayUtils.playChooseSound();
                        ++goalInNumber;
                        if (currentConfigData.getLetterData().size() <= goalInNumber) {
                            // 判断正误
                            StringBuffer react = new StringBuffer();
                            for (int i = 1; i <= goalInNumber; i++) {
                                react.append(((TextView) caterpillarLayout.getChildAt(i).findViewById(R.id.letterText)).getText().toString().trim());
                            }
                            if (currentConfigData.getText().equals(react.toString().trim())) {
                                currentConfigData.setCorrect(true);
                                timer.cancel();
                                soundPlayUtils.playWinSound();
                                // 正确
                                winAnimalImage.setVisibility(View.VISIBLE);
                                winAnimationDrawable.start();
                                int duration = 0;
                                for (int i = 0; i < winAnimationDrawable.getNumberOfFrames(); i++) {
                                    duration += winAnimationDrawable.getDuration(i);
                                }
                                Message m = new Message();
                                m.what = 3;
                                handler.sendMessageDelayed(m, duration);
                                startTranslateAnimation();
                            } else {
                                wrang();
                            }
                        }
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    private void wrang() {
        currentConfigData.setCorrect(false);
        timer.cancel();
        soundPlayUtils.playBadSound();
        // 错误
        badAnimalImage.setVisibility(View.VISIBLE);
        badAnimationDrawable.start();
        int duration = 0;
        for (int i = 0; i < badAnimationDrawable.getNumberOfFrames(); i++) {
            duration += badAnimationDrawable.getDuration(i);
        }
        Message m = new Message();
        m.what = 2;
        handler.sendMessageDelayed(m, duration);
    }

    private void findTouchLetter(MotionEvent event) {
        float x = event.getRawX();
        float y = event.getRawY() - statusBarHeight1;
        if (currentLetterList != null && currentLetterList.size() > 0) {
            int i = 0;
            for (; i < currentLetterList.size(); i++) {
                LetterData letterData = currentLetterList.get(i);
                if (letterData.getEar().contains((int) x, (int) y)) {
                    currentLetterData = letterData;
                    dragLetterRect = currentLetterData.getEar();
                    findViewById(currentLetterData.getPositionID()).setVisibility(View.INVISIBLE);
                    showTouchLetter(true, currentLetterData.getBackgroundID(), currentLetterData.getLetter() + "", currentLetterData.getEar());
                    return;
                }
            }
            if (currentLetterList.size() <= i) {
                currentLetterData = null;
                showTouchLetter(false, 0, "", null);
            }
        }
    }

    private void showTouchLetter(boolean isShow, int backgroundId, String letter, MyRect rect) {
        if (isShow) {
            drag.setBackgroundResource(backgroundId);
            dragText.setText(letter);
            drag.layout(rect.left, rect.top, rect.right, rect.bottom);
            drag.setVisibility(View.VISIBLE);
            System.out.println("  letter " + letter + "   " + rect.left + "  " + rect.top);
        } else {
            drag.setVisibility(View.INVISIBLE);
        }
    }

    private void buildGoalViewRect() {
        if (goalDataArrayList != null && goalDataArrayList.size() > 0) {
            GoalData goalData = goalDataArrayList.get(0);
            if (goalData.getEar() == null) {
                for (int i = 0; i < goalDataArrayList.size(); i++) {
                    goalData = goalDataArrayList.get(i);
                    LinearLayout layout = goalData.getLayout();
                    int[] screen = new int[2];
                    layout.getLocationOnScreen(screen);
                    System.out.println("layout view rect " + layout.getMeasuredWidth() + "   " + layout.getMeasuredHeight() + "  " + screen[0] + "  " + (screen[1] - statusBarHeight1));
                    goalData.setEar(new Rect(screen[0], screen[1] - statusBarHeight1, screen[0] + layout.getMeasuredWidth(), screen[1] + layout.getMeasuredHeight() - statusBarHeight1));
                }
            }
        }
    }

    private boolean checkGoalIn(MotionEvent event) {
        float x = event.getRawX();
        float y = event.getRawY() - statusBarHeight1;
        if (goalDataArrayList != null && goalDataArrayList.size() > 0) {
            for (int i = 0; i < goalDataArrayList.size(); i++) {
                GoalData data = goalDataArrayList.get(i);
                TextView textView = (TextView) data.getLayout().findViewById(R.id.letterText);
                String letter = textView.getText().toString().trim();
                if (data.getEar().contains((int) x, (int) y) && TextUtils.isEmpty(letter)) {
                    setGoalView(data, currentLetterData);
                    return true;
                }
            }
        }
        return false;
    }

    private void setGoalView(GoalData data, LetterData letterData) {
        data.getLayout().setBackgroundResource(letterData.getBackgroundID());
        ((TextView) data.getLayout().findViewById(R.id.letterText)).setText(letterData.getLetter() + "");
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    winAnimalImage.setBackground(winAnimationDrawable);
                    badAnimalImage.setBackground(badAnimationDrawable);
                    break;
                case 2:
                    badAnimationDrawable.stop();
                    badAnimalImage.setVisibility(View.INVISIBLE);
                    disappearView(dataList.get(currentStep));
                    ++currentStep;
                    if (dataList.size() > currentStep) {
                        buildView();
                        startTimer();
                    } else {
                        goOverActivity();
                    }
                    break;
                case 3:
                    winAnimationDrawable.stop();
                    winAnimalImage.setVisibility(View.INVISIBLE);
                    disappearView(dataList.get(currentStep));
                    ++currentStep;
                    if (dataList.size() > currentStep) {
                        buildView();
                        startTimer();
                    } else {
                        goOverActivity();
                    }
                    break;
                case 4:
                    time.setText(msg.arg1 + "");
                    if (msg.arg1 <= 0) {
                        wrang();
                    }
                    break;
            }
        }
    };

    private void goOverActivity() {
        Intent intent = new Intent(this, GameOverActivity.class);
        intent.putExtra(ZJMMCMainActivity.DATA_LIST, dataList);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (data != null) {
                dataList.clear();
                dataList.addAll((Collection<? extends ConfigData>) data.getSerializableExtra(ZJMMCMainActivity.DATA_LIST));
                currentStep = 0;
                if (dataList.size() > currentStep) {
                    buildView();
                    startTimer();
                } else {
                    Toast.makeText(this, "没有题目需要完成。", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (requestCode == 1 && resultCode == RESULT_CANCELED) {
            this.finish();
        }
    }

    private void startTranslateAnimation() {
        TranslateAnimation translateAnimation = new TranslateAnimation(
                //X轴初始位置
                Animation.RELATIVE_TO_SELF, 0.0f,
                //X轴移动的结束位置
                Animation.RELATIVE_TO_SELF, 1.5f,
                //y轴开始位置
                Animation.RELATIVE_TO_SELF, 0.0f,
                //y轴移动后的结束位置
                Animation.RELATIVE_TO_SELF, 0f);
        translateAnimation.setDuration(1400);//动画持续的时间为1.75s
        caterpillarLayout.startAnimation(translateAnimation);
    }
}
