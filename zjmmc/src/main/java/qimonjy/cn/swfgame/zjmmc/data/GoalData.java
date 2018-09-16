package qimonjy.cn.swfgame.zjmmc.data;

import android.graphics.Rect;
import android.widget.LinearLayout;

public class GoalData {
    /**
     * 控件
     */
    private LinearLayout layout;
    /**
     * 控件的位置
     */
    private Rect ear;
    /**
     * 填充的字母
     */
    private LetterData letterData;

    public LinearLayout getLayout() {
        return layout;
    }

    public GoalData setLayout(LinearLayout layout) {
        this.layout = layout;
        return this;
    }

    public Rect getEar() {
        return ear;
    }

    public GoalData setEar(Rect ear) {
        this.ear = ear;
        return this;
    }

    public LetterData getLetterData() {
        return letterData;
    }

    public GoalData setLetterData(LetterData letterData) {
        this.letterData = letterData;
        return this;
    }
}
