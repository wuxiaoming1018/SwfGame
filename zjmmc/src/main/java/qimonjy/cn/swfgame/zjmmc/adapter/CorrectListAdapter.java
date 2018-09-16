package qimonjy.cn.swfgame.zjmmc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import qimonjy.cn.swfgame.zjmmc.R;
import qimonjy.cn.swfgame.zjmmc.data.ConfigData;


public class CorrectListAdapter extends BaseAdapter {
    private ArrayList<ConfigData> dataArrayList = new ArrayList<>();
    private Context context;

    public CorrectListAdapter(Context context, ArrayList<ConfigData> dataArrayList) {
        this.context = context;
        this.dataArrayList = dataArrayList;
    }

    @Override
    public int getCount() {
        return dataArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHold viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHold();
            convertView = LayoutInflater.from(context).inflate(R.layout.correct_list_item, null);
            //对viewHolder的属性进行赋值
            viewHolder.correct = (ImageView) convertView.findViewById(R.id.isCorrect);
            viewHolder.word = (TextView) convertView.findViewById(R.id.word);
            //通过setTag将convertView与viewHolder关联
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHold) convertView.getTag();
        }
        // 取出bean对象
        ConfigData bean = dataArrayList.get(position);

        // 设置控件的数据
        if (bean.isCorrect()) {
            viewHolder.correct.setImageResource(R.drawable.zjmmc_correct);
        } else {
            viewHolder.correct.setImageResource(R.drawable.zjmmc_wrang);
        }
        viewHolder.word.setText(bean.getText());
        return convertView;
    }

    static class ViewHold {
        public TextView word;
        public ImageView correct;
    }
}
