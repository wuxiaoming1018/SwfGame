package qimonjy.cn.swfgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;
import qimonjy.cn.swfgame.dds.DDSMainActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.testButton)
    public void onViewClicked() {
        startActivity(new Intent(this, DDSMainActivity.class));
    }
}
