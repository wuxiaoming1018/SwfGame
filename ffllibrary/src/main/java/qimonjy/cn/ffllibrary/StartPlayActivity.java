package qimonjy.cn.ffllibrary;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.florent37.viewanimator.AnimationListener;
import com.github.florent37.viewanimator.ViewAnimator;

import org.dom4j.Document;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import qimonjy.cn.commonlibrary.FileIOandOperation;
import qimonjy.cn.commonlibrary.XmlUtils;
import qimonjy.cn.ffllibrary.end.FinishActivity;
import qimonjy.cn.ffllibrary.game.FflBean;
import qimonjy.cn.ffllibrary.game.SoundPlayUtils;

public class StartPlayActivity extends Activity implements View.OnClickListener {
    private ImageView mmuteView, mhelpView, mJumpview, timecountiv;
    private TextView timeViewText, timedownText;
    private FrameLayout helpFramelayout, timeFramelayout;
    //    private FrameLayout cardlaout11, cardlaout12, cardlaout13, cardlaout14, cardlaout15;
    /*isPlayBgm=false表示是静音，为真则不是静音*/
    private Boolean isPlayBgm = true;
    /*可选择帮助的次数*/
    private int helpcount = 3;
    private Timer mCheckTimer;
    /*帮助时间*/
    private int timecount = 8;
    private long TOTAL_TIME = 61 * 1000;//倒计时的总时间 ms
    /*倒计时是否开始*/
    private Boolean isStart = false;

    private ArrayList<FflBean> datas = new ArrayList<>(10);

    private List<TextView> dataTexts = new ArrayList<>(10);
    private List<ImageView> dataImages1 = new ArrayList<>(10);
    private List<ImageView> dataImages2 = new ArrayList<>(10);
    private List<ImageView> HeartImages2 = new ArrayList<>(3);

    /*是否在动画*/
    private Boolean inAnim = false;
    /*红心数*/
    private int heartcount = 3;
    private ViewAnimator animaCount;
    /**
     * 读取的配置参数
     */
    private String config;
    /**
     * 媒体播放
     */
    private MediaPlayer mediaPlayer;
    private SoundPlayUtils soundPlayUtils;
    private static final String CONFIG_FILE_PATH = "/sdcard/config.xml";//"/sdcard/config.xml";//

    private List<View> dataFrams = new ArrayList<>(10);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_play);
        initView();
        initData();
        makeData();
//        test();
        //    buildData();
        test();
        new Thread(new Runnable() {
            @Override
            public void run() {
                mediaPlayer = MediaPlayer.create(StartPlayActivity.this, R.raw.game_bgm);
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
        soundPlayUtils = SoundPlayUtils.init(getApplicationContext());
    }

    private void makeData() {
        datas.add(new FflBean().setMp3("").setWord1("111111111111111111111").setWord2("211111111111").setWrong(true));
        datas.add(new FflBean().setMp3("").setWord1("12").setWord2("22").setWrong(true));
        datas.add(new FflBean().setMp3("").setWord1("13").setWord2("23").setWrong(true));
        datas.add(new FflBean().setMp3("").setWord1("14").setWord2("24").setWrong(true));
        datas.add(new FflBean().setMp3("").setWord1("15").setWord2("25").setWrong(true));
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
                datas.clear();
                for (Iterator it = root.elementIterator(); it.hasNext(); ) {
                    Element element = (Element) it.next();
                    String word = element.attribute("word").getText();
                    String mp3Url = element.attribute("mp3Url").getText();
                    String text = element.getText();
                    FflBean data = new FflBean();
                    data.setWord1(word);
                    data.setMp3(mp3Url);
                    data.setWord2(text);
                    data.setWrong(true);
                    datas.add(data);
                }
            }
        }
    }

    private void test() {
        List<Integer> indexs = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            indexs.add(i);
        }
        for (int i = 0; i < datas.size(); i++) {
            FflBean fflBean = datas.get(i);
            //1
            int index1 = randomIndex(indexs);
            fflBean.setIndex1(indexs.get(index1));
            indexs.remove(index1);
            //2
            int index2 = randomIndex(indexs);
            fflBean.setIndex2(indexs.get(index2));
            indexs.remove(index2);
        }
        for (int i = 0; i < datas.size(); i++) {
            FflBean fflBean = datas.get(i);
            dataTexts.get(fflBean.getIndex1()).setText(fflBean.getWord1());
            dataTexts.get(fflBean.getIndex2()).setText(fflBean.getWord2());
            dataTexts.get(fflBean.getIndex1()).setTag(fflBean);
            dataTexts.get(fflBean.getIndex2()).setTag(fflBean);
        }
    }

    private int randomIndex(List<Integer> indexs) {
        int random = new Random().nextInt(indexs.size());
        return random;
    }

    private void initView() {
        mmuteView = (ImageView) findViewById(R.id.mute_iv);
        mmuteView.setOnClickListener(this);
        mhelpView = (ImageView) findViewById(R.id.helpc_iv);
        helpFramelayout = (FrameLayout) findViewById(R.id.helpframe_layout);
        helpFramelayout.setOnClickListener(this);
        timeViewText = (TextView) findViewById(R.id.timecount_tv);
        timeFramelayout = (FrameLayout) findViewById(R.id.timeframe_layout);
        mJumpview = (ImageView) findViewById(R.id.jump_iv);
        mJumpview.setOnClickListener(this);
        timedownText = (TextView) findViewById(R.id.timedown_txt);
        //10个
        dataTexts.add((TextView) findViewById(R.id.card_tx_11));
        dataTexts.add((TextView) findViewById(R.id.card_tx_12));
        dataTexts.add((TextView) findViewById(R.id.card_tx_13));
        dataTexts.add((TextView) findViewById(R.id.card_tx_14));
        dataTexts.add((TextView) findViewById(R.id.card_tx_15));
        dataTexts.add((TextView) findViewById(R.id.card_tx_21));
        dataTexts.add((TextView) findViewById(R.id.card_tx_22));
        dataTexts.add((TextView) findViewById(R.id.card_tx_23));
        dataTexts.add((TextView) findViewById(R.id.card_tx_24));
        dataTexts.add((TextView) findViewById(R.id.card_tx_25));
        //
        View fram1 = findViewById(R.id.cardlayout_11);
        dataFrams.add(fram1);
        fram1.setOnClickListener(this);
        View fram2 = findViewById(R.id.cardlayout_12);
        dataFrams.add(fram2);
        fram2.setOnClickListener(this);
        View fram3 = findViewById(R.id.cardlayout_13);
        dataFrams.add(fram3);
        fram3.setOnClickListener(this);
        View fram4 = findViewById(R.id.cardlayout_14);
        dataFrams.add(fram4);
        fram4.setOnClickListener(this);
        View fram5 = findViewById(R.id.cardlayout_15);
        dataFrams.add(fram5);
        fram5.setOnClickListener(this);
        View fram21 = findViewById(R.id.cardlayout_21);
        dataFrams.add(fram21);
        fram21.setOnClickListener(this);
        View fram22 = findViewById(R.id.cardlayout_22);
        dataFrams.add(fram22);
        fram22.setOnClickListener(this);
        View fram23 = findViewById(R.id.cardlayout_23);
        dataFrams.add(fram23);
        fram23.setOnClickListener(this);
        View fram24 = findViewById(R.id.cardlayout_24);
        dataFrams.add(fram24);
        fram24.setOnClickListener(this);
        View fram25 = findViewById(R.id.cardlayout_25);
        dataFrams.add(fram25);
        fram25.setOnClickListener(this);

        dataImages1.add((ImageView) findViewById(R.id.card_11));
        dataImages1.add((ImageView) findViewById(R.id.card_12));
        dataImages1.add((ImageView) findViewById(R.id.card_13));
        dataImages1.add((ImageView) findViewById(R.id.card_14));
        dataImages1.add((ImageView) findViewById(R.id.card_15));
        dataImages1.add((ImageView) findViewById(R.id.card_21));
        dataImages1.add((ImageView) findViewById(R.id.card_22));
        dataImages1.add((ImageView) findViewById(R.id.card_23));
        dataImages1.add((ImageView) findViewById(R.id.card_24));
        dataImages1.add((ImageView) findViewById(R.id.card_25));

        dataImages2.add((ImageView) findViewById(R.id.cardiv_11));
        dataImages2.add((ImageView) findViewById(R.id.cardiv_12));
        dataImages2.add((ImageView) findViewById(R.id.cardiv_13));
        dataImages2.add((ImageView) findViewById(R.id.cardiv_14));
        dataImages2.add((ImageView) findViewById(R.id.cardiv_15));
        dataImages2.add((ImageView) findViewById(R.id.cardiv_21));
        dataImages2.add((ImageView) findViewById(R.id.cardiv_22));
        dataImages2.add((ImageView) findViewById(R.id.cardiv_23));
        dataImages2.add((ImageView) findViewById(R.id.cardiv_24));
        dataImages2.add((ImageView) findViewById(R.id.cardiv_25));
        HeartImages2.add((ImageView) findViewById(R.id.heart_iv3));
        HeartImages2.add((ImageView) findViewById(R.id.heart_iv2));
        HeartImages2.add((ImageView) findViewById(R.id.heart_iv1));
        for (int i = 0; i < 10; i++) {
            dataImages1.get(i).setVisibility(View.INVISIBLE);
            dataImages2.get(i).setVisibility(View.VISIBLE);
            dataTexts.get(i).setVisibility(View.VISIBLE);
        }
        timecountiv = (ImageView) findViewById(R.id.timecount_iv);
        animaCount = ViewAnimator.animate(timecountiv).rotation(360)
                .repeatCount(-1)
                .repeatMode(ValueAnimator.RESTART)
                .duration(5000)
                .start();
    }

    private void initData() {
        if (mCheckTimer != null) {
            mCheckTimer.cancel();
        }
        mCheckTimer = new Timer();
        mCheckTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {      // UI thread
                    @Override
                    public void run() {
                        timecount--;
                        if (timecount >= 0) {
                            soundPlayUtils.playCountDownSound();
                            timeViewText.setText("" + timecount);
                        } else {
                            if (isStart == false) {
                                timedownText.setVisibility(View.VISIBLE);
                                countDownTimer.start();
                            }
                            for (int i = 0; i < 10; i++) {
                                soundPlayUtils.playTurnCardSound();
                                animF2(dataImages2.get(i), dataImages1.get(i), dataTexts.get(i));
                            }
                            timeFramelayout.setVisibility(View.INVISIBLE);
                            mJumpview.setVisibility(View.INVISIBLE);
                            mCheckTimer.cancel();

                        }
                    }
                });
            }
        }, 0, 1000);
    }

    /**
     * CountDownTimer 实现倒计时
     */
    private CountDownTimer countDownTimer = new CountDownTimer(TOTAL_TIME, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            isStart = true;
            String value = String.valueOf((int) (millisUntilFinished / 1000));
            timedownText.setText(value);
        }

        @Override
        public void onFinish() {
            isStart = false;
            timedownText.setVisibility(View.INVISIBLE);
            // startActivity(new Intent(getBaseContext(), FinishActivity.class));
            soundPlayUtils.playFinishSound();
            finish();
            startActivity(new Intent(getBaseContext(), FinishActivity.class).putParcelableArrayListExtra("datas", datas));
        }
    };

    private int lastIndex = -1;

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.mute_iv) {
            soundPlayUtils.playButtonSound();
            if (!isPlayBgm) {
                isPlayBgm = true;
                mmuteView.setImageResource(R.drawable.muteopen);
                if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                }
            } else {
                isPlayBgm = false;
                mmuteView.setImageResource(R.drawable.muteclose);
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                }
            }
        } else if (i == R.id.helpframe_layout) {
            if (timecount >= 0)
                return;
            helpcount--;
            if (helpcount == 2) {
                mhelpView.setImageResource(R.drawable.try_2);
                timeFramelayout.setVisibility(View.VISIBLE);
                mJumpview.setVisibility(View.VISIBLE);
                timecount = 5;
                for (int a = 0; a < 10; a++) {
                    animF(dataImages1.get(a), dataImages2.get(a), dataTexts.get(a));
                }
                soundPlayUtils.playTurnCardSound();
                initData();
            } else if (helpcount == 1) {
                timeFramelayout.setVisibility(View.VISIBLE);
                mhelpView.setImageResource(R.drawable.try_1);
                mJumpview.setVisibility(View.VISIBLE);
                timecount = 5;
                for (int a = 0; a < 10; a++) {
                    animF(dataImages1.get(a), dataImages2.get(a), dataTexts.get(a));
                }
                soundPlayUtils.playTurnCardSound();
                initData();
            } else if (helpcount == 3) {
                mhelpView.setImageResource(R.drawable.try_3);
            } else if (helpcount == 0) {
                mhelpView.setVisibility(View.INVISIBLE);
                timeFramelayout.setVisibility(View.VISIBLE);
                mJumpview.setVisibility(View.VISIBLE);
                timecount = 5;
                initData();
                for (int a = 0; a < 10; a++) {
                    animF(dataImages1.get(a), dataImages2.get(a), dataTexts.get(a));
                }
                soundPlayUtils.playTurnCardSound();
            } else {
                mhelpView.setVisibility(View.INVISIBLE);
            }
        } else if (i == R.id.jump_iv) {
            soundPlayUtils.playButtonSound();
            if (mCheckTimer != null) {
                mCheckTimer.cancel();
                timecount = -1;
            }
            for (int a = 0; a < 10; a++) {
 /*               dataImages1.get(a).setVisibility(View.VISIBLE);
                dataImages2.get(a).setVisibility(View.INVISIBLE);
                dataTexts.get(a).setVisibility(View.INVISIBLE);*/
                soundPlayUtils.playTurnCardSound();
                animF2(dataImages2.get(a), dataImages1.get(a), dataTexts.get(a));
            }
            if (isStart) {
                mJumpview.setVisibility(View.INVISIBLE);
                timeFramelayout.setVisibility(View.INVISIBLE);


            } else {
                mJumpview.setVisibility(View.INVISIBLE);
                timeFramelayout.setVisibility(View.INVISIBLE);
                timedownText.setVisibility(View.VISIBLE);
                countDownTimer.start();
            }
        } else if (v.getTag() != null) {
            if (v.getTag() instanceof String) {
                if (lastClickView != null && lastClickView == v)
                    return;
                if (timecount >= 0)
                    return;
                if (inAnim)
                    return;
                String indexStr = (String) v.getTag();
                final int index = Integer.parseInt(indexStr);
                final View framView = dataFrams.get(index);
                if (framView.getVisibility() == View.VISIBLE) {
                    soundPlayUtils.playTurnCardSound();
                    animF(dataImages1.get(index), dataImages2.get(index), dataTexts.get(index));
                    final FflBean fflBean = (FflBean) dataTexts.get(index).getTag();
   /*                 dataImages1.get(index).setVisibility(View.INVISIBLE);
                    dataImages2.get(index).setVisibility(View.VISIBLE);
                    dataTexts.get(index).setVisibility(View.VISIBLE);*/
                    inAnim = true;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            inAnim = false;
                            if (lastFflBean != null) {
                                if (lastFflBean == fflBean) {
                                    fflBean.setWrong(false);
                                    lastFflBean = null;
                                    framView.setVisibility(View.INVISIBLE);
                                    lastClickView.setVisibility(View.INVISIBLE);
                                    lastClickView = null;
                                } else {
                                    if (heartcount > 1) {
                                        HeartImages2.get(heartcount - 1).setVisibility(View.INVISIBLE);
                                    } else {
                                        HeartImages2.get(heartcount - 1).setVisibility(View.INVISIBLE);
                                        countDownTimer.cancel();
                                        soundPlayUtils.playFinishSound();
                                        finish();
                                        startActivity(new Intent(getBaseContext(), FinishActivity.class).putParcelableArrayListExtra("datas", datas));
                                    }
                                    heartcount--;
                                    soundPlayUtils.playbadTurnCardSound();
                                    animF2(dataImages2.get(index), dataImages1.get(index), dataTexts.get(index));
            /*                        dataImages1.get(index).setVisibility(View.VISIBLE);
                                    dataImages2.get(index).setVisibility(View.INVISIBLE);
                                    dataTexts.get(index).setVisibility(View.INVISIBLE);*/
                                }
                            } else {
                                lastFflBean = fflBean;
                                lastClickView = framView;
                            }
                        }
                    }, 2000);

                }
            }
        }
    }

    private FflBean lastFflBean = null;
    private View lastClickView = null;

    private ViewAnimator anima1, anima2;

    private void animF(final View view1, final View view2, final TextView view3) {
        anima1 = ViewAnimator
                .animate(view1, view2)
                .alpha(1, 0)
                .rotationY(0, 180)
                .andAnimate(view2)
                .alpha(0, 1)
                .rotationY(0, 180)
                .andAnimate(view3)
                .alpha(0, 1)
                .duration(1000)
                .onStart(new AnimationListener.Start() {
                    @Override
                    public void onStart() {
                        view1.setVisibility(View.VISIBLE);
                        view2.setVisibility(View.VISIBLE);
                        view3.setVisibility(View.VISIBLE);
                    }
                })
                .onStop(new AnimationListener.Stop() {
                    @Override
                    public void onStop() {
                        view1.setVisibility(View.INVISIBLE);
                        view2.setVisibility(View.VISIBLE);
                        view3.setVisibility(View.VISIBLE);
                    }
                })
                .start();

    }

    private void animF2(final View view1, final View view2, final TextView view3) {
        int animTime = 1000;
        anima2 = ViewAnimator
                .animate(view1)
                .alpha(1, 0)
                .rotationY(180, 0)
                .andAnimate(view2)
                .alpha(0, 1)
                .rotationY(0, 180)
                .duration(animTime)
                .onStart(new AnimationListener.Start() {
                    @Override
                    public void onStart() {
                        view1.setVisibility(View.VISIBLE);
                        view2.setVisibility(View.VISIBLE);
                        view3.setVisibility(View.INVISIBLE);
                    }
                })
                .onStop(new AnimationListener.Stop() {
                    @Override
                    public void onStop() {
                        view1.setVisibility(View.INVISIBLE);
                        view2.setVisibility(View.VISIBLE);
                    }
                })
                .start();

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
        if (anima1 != null)
            anima1.cancel();
        if (anima2 != null)
            anima2.cancel();
        if (mCheckTimer != null)
            mCheckTimer.cancel();
        if (countDownTimer != null)
            countDownTimer.cancel();
    }
}
