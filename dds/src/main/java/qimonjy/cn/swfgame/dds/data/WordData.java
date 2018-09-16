package qimonjy.cn.swfgame.dds.data;

import java.io.Serializable;

public class WordData implements Serializable {
    /**
     * 单词
     */
    private String text;
    /**
     * 对应的布局文件id。
     */
    private int layoutId;
    /**
     * 田鼠图标
     */
    private int voleIcon;
    /**
     * 田鼠图标（被击中后）
     */
    private int voleIcon2;
    /**
     * 有效点击范围
     */
    private MyRect rect;

    public String getText() {
        return text;
    }

    public WordData setText(String text) {
        this.text = text;
        return this;
    }

    public int getLayoutId() {
        return layoutId;
    }

    public WordData setLayoutId(int layoutId) {
        this.layoutId = layoutId;
        return this;
    }

    public int getVoleIcon() {
        return voleIcon;
    }

    public WordData setVoleIcon(int voleIcon) {
        this.voleIcon = voleIcon;
        return this;
    }

    public int getVoleIcon2() {
        return voleIcon2;
    }

    public WordData setVoleIcon2(int voleIcon2) {
        this.voleIcon2 = voleIcon2;
        return this;
    }

    public MyRect getRect() {
        return rect;
    }

    public WordData setRect(MyRect rect) {
        this.rect = rect;
        return this;
    }
}
