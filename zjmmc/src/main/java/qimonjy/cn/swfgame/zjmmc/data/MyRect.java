package qimonjy.cn.swfgame.zjmmc.data;

import java.io.Serializable;

public class MyRect implements Serializable  {
    public int left;
    public int top;
    public int right;
    public int bottom;

    public MyRect(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }
    public boolean contains(int x, int y) {
        return left < right && top < bottom  // check for empty first
                && x >= left && x < right && y >= top && y < bottom;
    }
}
