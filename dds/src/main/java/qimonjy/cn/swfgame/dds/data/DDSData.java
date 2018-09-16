package qimonjy.cn.swfgame.dds.data;

import java.io.Serializable;
import java.util.ArrayList;

public class DDSData implements Serializable {
    /**
     * 发音文件
     */
    private String mp3Url;
    /**
     * 正确答案
     */
    private String correct;
    /**
     * 单词列表
     */
    private ArrayList<WordData> wordList = new ArrayList<>();

    public String getMp3Url() {
        return mp3Url;
    }

    public DDSData setMp3Url(String mp3Url) {
        this.mp3Url = mp3Url;
        return this;
    }

    public String getCorrect() {
        return correct;
    }

    public DDSData setCorrect(String correct) {
        this.correct = correct;
        return this;
    }

    public ArrayList<WordData> getWordList() {
        return wordList;
    }

    public DDSData addWordList(WordData wordList) {
        this.wordList.add(wordList);
        return this;
    }

}
