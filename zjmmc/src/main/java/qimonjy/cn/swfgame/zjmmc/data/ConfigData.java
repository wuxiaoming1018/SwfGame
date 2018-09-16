package qimonjy.cn.swfgame.zjmmc.data;

import java.io.Serializable;
import java.util.ArrayList;

public class ConfigData implements Serializable {
    /**
     * 解释
     */
    private String chinese;
    /**
     * 发音
     */
    private String mp3Url;
    /**
     * 单词
     */
    private String text;
    /**
     * 单词字母
     */
    private ArrayList<LetterData> letterData = new ArrayList<>();
    /**
     * 是否做对了。
     */
    private boolean isCorrect;
    /**
     * 序号，从1开始。
     */
    private int index;

    public String getChinese() {
        return chinese;
    }

    public ConfigData setChinese(String chinese) {
        this.chinese = chinese;
        return this;
    }

    public String getMp3Url() {
        return mp3Url;
    }

    public ConfigData setMp3Url(String mp3Url) {
        this.mp3Url = mp3Url;
        return this;
    }

    public String getText() {
        return text;
    }

    public ConfigData setText(String text) {
        this.text = text;
        return this;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public ConfigData setCorrect(boolean correct) {
        isCorrect = correct;
        return this;
    }

    public ArrayList<LetterData> getLetterData() {
        return letterData;
    }

    public ConfigData addLetterData(LetterData letterData) {
        if (this.letterData == null) {
            this.letterData = new ArrayList<>();
        }
        this.letterData.add(letterData);
        return this;
    }

    public int getIndex() {
        return index;
    }

    public ConfigData setIndex(int index) {
        this.index = index;
        return this;
    }
}
