package com.sinosafe.xb.manager.adapter.yeji;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sinosafe.xb.manager.R;
import com.sinosafe.xb.manager.adapter.base.BaseRecyclerViewAdapter;
import com.sinosafe.xb.manager.adapter.base.BaseRecyclerViewHolder;
import com.sinosafe.xb.manager.bean.YeJiTrendBean;
import com.sinosafe.xb.manager.utils.MyUtils;

/**
 * 业绩趋势适配器
 */
public class YeJiTrendAdapter extends BaseRecyclerViewAdapter<YeJiTrendBean, BaseRecyclerViewHolder> {

    private int type = -1;
    private Context context;

    public YeJiTrendAdapter(Context context, int type) {
        super(context);
        this.type = type;
        this.context = context;
    }

    @Override
    protected void bindDataToItemView(BaseRecyclerViewHolder baseRecyclerViewHolder, int position, YeJiTrendBean item) {

        TextView tvTime = baseRecyclerViewHolder.getView(R.id.tvTime);
        TextView tvMoney = baseRecyclerViewHolder.getView(R.id.tvMoney);

        if(item.getFlag()==0){
            tvTime.setText(item.getDate());
            tvMoney.setText("");
        }
        else{
            tvTime.setText(item.getDate());
            //放款笔数
            if(type==0){
                tvMoney.setText(item.getResult());
            }else{
                if(item.getResult()==null||"".equals(item.getResult()))
                    tvMoney.setText("0.00");
                else{
                    tvMoney.setText(MyUtils.keepTwoDecimal(Double.valueOf(item.getResult())));
                }
            }
        }
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseRecyclerViewHolder(inflateItemView(parent, R.layout.yeji_trend_item));
    }
}