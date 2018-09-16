package qimonjy.cn.ffllibrary;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.github.florent37.viewanimator.ViewAnimator;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;

public class HerocraftTwiceActivity extends Activity implements View.OnClickListener {


    private ImageView manimatimage, faniv1, faniv2, faniv3, startiv, helpIv;
    private AnimationDrawable animationDrawable;
    /**
     * 媒体播放
     */
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_herocraft_twice);
        manimatimage = (ImageView) findViewById(R.id.quan_iv);
        faniv1 = (ImageView) findViewById(R.id.fan1_iv);
        faniv2 = (ImageView) findViewById(R.id.fan2_iv);
        faniv3 = (ImageView) findViewById(R.id.fan3_iv);
        startiv = (ImageView) findViewById(R.id.start_iv);
        startiv.setOnClickListener(this);
        helpIv = (ImageView) findViewById(R.id.help_iv);
        helpIv.setOnClickListener(this);
        animationDrawable = (AnimationDrawable) manimatimage.getBackground();
        ViewAnimator
                .animate(faniv1)
                /*.translationY(-1000, 0)
                .alpha(0,1)*/
                .thenAnimate(faniv1)
                .scale(0.1f, 0.5f, 1f)
                .accelerate()
                .duration(1000)
                .start();
        ViewAnimator
                .animate(faniv2)
                /*.translationY(-1000, 0)
                .alpha(0,1)*/
                .thenAnimate(faniv2)
                .scale(0.1f, 0.5f, 1f)
                .accelerate()
                .duration(1000)
                .start();
        ViewAnimator
                .animate(faniv3)
                /*.translationY(-1000, 0)
                .alpha(0,1)*/
                .thenAnimate(faniv3)
                .scale(0.1f, 0.5f, 1f)
                .accelerate()
                .duration(1000)
                .start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                mediaPlayer = MediaPlayer.create(HerocraftTwiceActivity.this, R.raw.startpagebgm);
                mediaPlayer.setLooping(true);
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        if (!mediaPlayer.isPlaying()) {
                            mediaPlayer.start();
                        }
                    }
                });
            }
        }).start();
        // animationDrawable.start();
/*
* <?xml version="1.0" encoding="UTF-8"?>
 <students>
     <student sid="1">
      <sname>码农01</sname>
      <sage>101</sage>
     </student>
	   <student sid="2">
      <sname>码农02</sname>
      <sage>102</sage>
     </student>
	   <student sid="3">
      <sname>码农03</sname>
      <sage>103</sage>
     </student>
*/
   /* InputStream is=connection.getInputStream();//获取读流
   XmlPullParser pullParser= Xml.newPullParser();
                    //进行解析  (参数一：数据源（网络流）；参数二：编码方式)
                    pullParser.setInput(is,"UTF-8");
                    //解析标签类型
                    int type=pullParser.getEventType();
                     while(type!=XmlPullParser.END_DOCUMENT){ //判断不是结束标签
                         switch (type){
                             case XmlPullParser.START_TAG:
                              //获取开始标签的名字
                                 String  starTagName=pullParser.getName();
                                 if("student".equals(starTagName)){
                                     //获取属性值id的值
                                     String sid=pullParser.getAttributeName(0);
                                     Log.i("test",""+sid);
                                 } else if("sname".equals(starTagName)){
                                     String sname=pullParser.nextText();
                                     Log.i("test",""+sname);
                                 } else if("sage".equals(starTagName)){
                                     String sage=pullParser.nextText();
                                     Log.i("test",""+sage);
                                 }
                                      break;
                             case XmlPullParser.END_TAG:
                                 break;
                         }
                         //细节
                         type=pullParser.next();
                     }
*/
        //   InputStream is = getResources().getAssets().open("student.xml");
        //    getAssets().open()

    }

    private void parserXml(InputStream inputStream) throws Exception {

        XmlPullParser pullParser = Xml.newPullParser();
        //进行解析  (参数一：数据源（网络流）；参数二：编码方式)
        pullParser.setInput(inputStream, "UTF-8");
        //解析标签类型
        int type = pullParser.getEventType();
        while (type != XmlPullParser.END_DOCUMENT) { //判断不是结束标签
            switch (type) {
                case XmlPullParser.START_TAG:
                    //获取开始标签的名字
                    String starTagName = pullParser.getName();
                    if ("pair".equals(starTagName)) {
                        //获取属性值id的值
                        String word = pullParser.getAttributeName(0);
                        Log.i("word", "" + word);
                        String mp3Url = pullParser.getAttributeName(1);
                        Log.i("mp3Url", "" + mp3Url);
                    }
                    break;
                case XmlPullParser.END_TAG:
                    break;
            }
            //细节
            type = pullParser.next();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
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
    public void onClick(View v) {
        if (v.getId() == R.id.start_iv) {
            startActivity(new Intent(this, StartPlayActivity.class));
        } else if (v.getId() == R.id.help_iv) {
            startActivity(new Intent(this, HelpActivity.class));
        }

    }
}
