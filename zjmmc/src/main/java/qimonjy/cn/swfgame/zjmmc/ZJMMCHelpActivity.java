package qimonjy.cn.swfgame.zjmmc;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static qimonjy.cn.swfgame.zjmmc.ZJMMCMainActivity.DATA_LIST;

public class ZJMMCHelpActivity extends AppCompatActivity {
    private ImageView close;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zjmmc_activity_help);
        ButterKnife.bind(this);
        close = (ImageView) findViewById(R.id.close2);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ZJMMCHelpActivity.this.finish();
            }
        });
    }

    @OnClick({R2.id.start})
    public void onStartClicked() {
//        startActivity(new Intent(this, ZJMMCGameActivity.class));
//        ZJMMCHelpActivity.this.finish();
        Intent intent = new Intent(this, ZJMMCGameActivity.class);
        intent.putExtra(DATA_LIST, getIntent().getSerializableExtra(DATA_LIST));
        startActivity(intent);
        ZJMMCHelpActivity.this.finish();
    }

}
