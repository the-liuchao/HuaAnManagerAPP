package com.sinosafe.xb.manager.adapter.home;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.sinosafe.xb.manager.R;
import com.sinosafe.xb.manager.adapter.base.BaseRecyclerViewAdapter;
import com.sinosafe.xb.manager.adapter.base.BaseRecyclerViewHolder;
import com.sinosafe.xb.manager.bean.LoanProductBean;
import com.sinosafe.xb.manager.utils.MyUtils;


/**
 * 消费贷列表适配器
 */
public class RecommendLoanAdapter extends BaseRecyclerViewAdapter<LoanProductBean, BaseRecyclerViewHolder> {


    public RecommendLoanAdapter(Context context) {
        super(context);
    }

    @Override
    protected void bindDataToItemView(BaseRecyclerViewHolder baseRecyclerViewHolder, int position, LoanProductBean item) {

        TextView tv_title = baseRecyclerViewHolder.getView(R.id.tv_title);
        TextView mMaximumAmount = baseRecyclerViewHolder.getView(R.id.tv_maximum_amount);
        ImageView mLoanIcon = baseRecyclerViewHolder.getView(R.id.iv_loan_icon);
        TextView tv_interest_rate = baseRecyclerViewHolder.getView(R.id.tv_interest_rate);
        TextView tv_speed = baseRecyclerViewHolder.getView(R.id.tv_speed);

        tv_title.setText(item.getPrd_name());
        mMaximumAmount.setText(MyUtils.keepTwoDecimal(item.getSub_amt_max()/10000));
        tv_interest_rate.setText(MyUtils.keepTwoDecimal(item.getRate_min()));
        tv_speed.setText(item.getLoan_speed());
        if (item.getPrd_ico() != null)
            Glide.with(mLoanIcon.getContext())
                    .load(item.getPrd_ico())
                    .error(R.mipmap.default_image)
                    .placeholder(R.mipmap.default_image)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(mLoanIcon);
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseRecyclerViewHolder(inflateItemView(parent, R.layout.item_recommend_loan));
    }


}