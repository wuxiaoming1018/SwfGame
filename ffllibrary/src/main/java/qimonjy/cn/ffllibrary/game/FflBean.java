package qimonjy.cn.ffllibrary.game;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2018/9/7 0007.
 */

public class FflBean implements Parcelable {


    private String mp3;
    private String word1,word2;
    private int index1,index2;

    public Boolean getWrong() {
        return wrong;
    }

    public FflBean setWrong(Boolean wrong) {
        this.wrong = wrong;
        return this;
    }

    private Boolean wrong;

    public int getIndex1() {
        return index1;
    }

    public FflBean setIndex1(int index1) {
        this.index1 = index1;
        return this;
    }

    public int getIndex2() {
        return index2;
    }

    public FflBean setIndex2(int index2) {
        this.index2 = index2;
        return this;
    }

    public String getMp3() {
        return mp3;
    }

    public FflBean setMp3(String mp3) {
        this.mp3 = mp3;
        return this;
    }

    public String getWord1() {
        return word1;
    }

    public FflBean setWord1(String word1) {
        this.word1 = word1;
        return this;
    }

    public String getWord2() {
        return word2;
    }

    public FflBean setWord2(String word2) {
        this.word2 = word2;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mp3);
        dest.writeString(this.word1);
        dest.writeString(this.word2);
        dest.writeInt(this.index1);
        dest.writeInt(this.index2);
        dest.writeValue(this.wrong);
    }

    public FflBean() {
    }

    protected FflBean(Parcel in) {
        this.mp3 = in.readString();
        this.word1 = in.readString();
        this.word2 = in.readString();
        this.index1 = in.readInt();
        this.index2 = in.readInt();
        this.wrong = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }

    public static final Parcelable.Creator<FflBean> CREATOR = new Parcelable.Creator<FflBean>() {
        @Override
        public FflBean createFromParcel(Parcel source) {
            return new FflBean(source);
        }

        @Override
        public FflBean[] newArray(int size) {
            return new FflBean[size];
        }
    };
}
