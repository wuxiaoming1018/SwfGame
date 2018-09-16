package qimonjy.cn.ffllibrary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

public class HelpActivity extends Activity implements View.OnClickListener {
    private ImageView close, start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        findViewById(R.id.ffl_start).setOnClickListener(this);
        findViewById(R.id.ffl_close2).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ffl_close2) {
            HelpActivity.this.finish();
        } else if (v.getId() == R.id.ffl_start) {
            startActivity(new Intent(this, StartPlayActivity.class));
            HelpActivity.this.finish();
        }
    }
}
