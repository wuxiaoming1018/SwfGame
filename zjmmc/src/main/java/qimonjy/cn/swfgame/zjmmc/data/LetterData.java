package qimonjy.cn.swfgame.zjmmc.data;

import java.io.Serializable;

public class LetterData implements Serializable {
    /**
     * 单词字母
     */
    private char letter;
    /**
     * 背景图
     */
    private int backgroundID;
    /**
     * 显示位置控件id
     */
    private int positionID;
    /**
     * 字母所属控件的位置
     */
    private MyRect ear;

    public char getLetter() {
        return letter;
    }

    public LetterData setLetter(char letter) {
        this.letter = letter;
        return this;
    }

    public int getBackgroundID() {
        return backgroundID;
    }

    public LetterData setBackgroundID(int backgroundID) {
        this.backgroundID = backgroundID;
        return this;
    }

    public int getPositionID() {
        return positionID;
    }

    public LetterData setPositionID(int positionID) {
        this.positionID = positionID;
        return this;
    }

    public MyRect getEar() {
        return ear;
    }

    public LetterData setEar(MyRect ear) {
        this.ear = ear;
        return this;
    }
}
