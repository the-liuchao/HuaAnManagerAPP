package com.sinosafe.xb.manager.adapter.yeji;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinosafe.xb.manager.R;
import com.sinosafe.xb.manager.adapter.base.BaseRecyclerViewAdapter;
import com.sinosafe.xb.manager.adapter.base.BaseRecyclerViewHolder;
import com.sinosafe.xb.manager.module.yeji.bean.YeJiRanking;

import luo.library.base.base.BaseImage;
import luo.library.base.utils.DensityUtil;
import luo.library.base.widget.MoneyView;

/**
 * 业绩排行榜适配器
 */
public class YeJiSortAdapter extends BaseRecyclerViewAdapter<YeJiRanking.RankingItem, BaseRecyclerViewHolder> {

    private int type = -1;
    private Context mContext;

    public YeJiSortAdapter(Context context, int type) {
        super(context);
        this.type = type;
        this.mContext = context;
    }

    @Override
    protected void bindDataToItemView(BaseRecyclerViewHolder baseRecyclerViewHolder, int position, YeJiRanking.RankingItem item) {

        MoneyView tvMoney = baseRecyclerViewHolder.getView(R.id.tvMoney);
        TextView tvSort = baseRecyclerViewHolder.getView(R.id.tvSort);
        TextView tvName = baseRecyclerViewHolder.getView(R.id.tvName);
        TextView tvBiShu = baseRecyclerViewHolder.getView(R.id.tvBiShu);
        ImageView ivImage = baseRecyclerViewHolder.getView(R.id.ivImage);

        View line = baseRecyclerViewHolder.getView(R.id.line);
        View thickLine = baseRecyclerViewHolder.getView(R.id.thickLine);

        tvName.setText(item.getCUS_NAME());
        tvBiShu.setText(item.getORGANSHORTFORM());

        BaseImage.getInstance().displayCricleImage(mContext,
                item.getIMAGE_URL(), ivImage, R.mipmap.icon_default_avatar);

        if(position==0 && item.getSEQ()!=-1){
            setTextViewParam(tvSort,0);
            thickLine.setVisibility(View.VISIBLE);
            line.setVisibility(View.GONE);
            tvSort.setBackgroundResource(R.drawable.shape_blue_conner8);
            tvSort.setText(item.getSEQ()+"");

            if(item.getSEQ()==1){
                setTextViewParam(tvSort,1);
                tvSort.setBackgroundResource(R.mipmap.icon_first);
                tvSort.setText("");
            }

        }else {
            thickLine.setVisibility(View.GONE);
            line.setVisibility(View.VISIBLE);
            setTextViewParam(tvSort,1);

            if(item.getSEQ()==1){
                tvSort.setBackgroundResource(R.mipmap.icon_first);
                tvSort.setText("");
            }else if(item.getSEQ()==2){
                tvSort.setBackgroundResource(R.mipmap.icon_second);
                tvSort.setText("");
            }else if(item.getSEQ()==3){
                tvSort.setBackgroundResource(R.mipmap.icon_third);
                tvSort.setText("");
            }else{
                tvSort.setBackgroundResource(R.drawable.shape_blue_oval);
                tvSort.setText(item.getSEQ()+"");
            }
        }
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseRecyclerViewHolder(inflateItemView(parent, R.layout.yeji_sort_item));
    }


    private void setTextViewParam(TextView textView,int type){

        ViewGroup.LayoutParams params = textView.getLayoutParams();
        if(type==1){
            params.height = DensityUtil.dp2px(mContext,30);
            params.width = DensityUtil.dp2px(mContext,30);
        }else{
            params.height = DensityUtil.dp2px(mContext,20);
            params.width = DensityUtil.dp2px(mContext,30);
        }
        textView.setLayoutParams(params);
    }

}