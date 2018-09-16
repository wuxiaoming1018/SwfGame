package qimonjy.cn.ffllibrary.end;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.github.florent37.viewanimator.ViewAnimator;

import java.util.ArrayList;

import qimonjy.cn.ffllibrary.R;
import qimonjy.cn.ffllibrary.StartPlayActivity;
import qimonjy.cn.ffllibrary.game.FflBean;

public class FinishActivity extends Activity implements View.OnClickListener {
    private RecyclerView mRecyView;
    private ImageView mReplayIv;
    private ImageView rBgView;
    private ViewAnimator anima;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);
        initView();
        initData();
    }

    private void initView() {
        mReplayIv = (ImageView) findViewById(R.id.replay_iv);
        mReplayIv.setOnClickListener(this);
        rBgView = (ImageView) findViewById(R.id.r_bg);
        findViewById(R.id.ffl_close).setOnClickListener(this);
        mRecyView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyView.setLayoutManager(new LinearLayoutManager(this));

        //
        anima = ViewAnimator.animate(rBgView).rotation(360).repeatCount(-1).repeatMode(ValueAnimator.RESTART).duration(5000).start();
    }

    private ArrayList<FflBean> mFflBean;
    private FinishAdater mAdapter;

    private void initData() {
        if (getIntent().hasExtra("datas")) {
            Object test = getIntent().getParcelableArrayListExtra("datas");
            if (test instanceof ArrayList) {
                mFflBean = (ArrayList) getIntent().getParcelableArrayListExtra("datas");
            }
        }
        mAdapter = new FinishAdater(mFflBean);
        mRecyView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.replay_iv) {
            startActivity(new Intent(this, StartPlayActivity.class));
            FinishActivity.this.finish();
        } else if (v.getId() == R.id.ffl_close) {
            FinishActivity.this.finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (anima != null)
            anima.cancel();
    }
}
