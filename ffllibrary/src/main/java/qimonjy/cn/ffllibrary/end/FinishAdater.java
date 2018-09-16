package qimonjy.cn.ffllibrary.end;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import qimonjy.cn.ffllibrary.R;
import qimonjy.cn.ffllibrary.game.FflBean;

/**
 * Created by Administrator on 2018/9/10 0010.
 */

public class FinishAdater extends BaseQuickAdapter<FflBean, BaseViewHolder> {
    FinishAdater(List<FflBean> data) {
        super(R.layout.item_ffl_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FflBean item) {
        helper.setText(R.id.text_1, item.getWord1());
        helper.setText(R.id.text_2, item.getWord2());
        if (item.getWrong())
            helper.setImageResource(R.id.image_iv,R.drawable.ffl_wrang);
        else
            helper.setImageResource(R.id.image_iv,R.drawable.ffl_correct);
    }
}