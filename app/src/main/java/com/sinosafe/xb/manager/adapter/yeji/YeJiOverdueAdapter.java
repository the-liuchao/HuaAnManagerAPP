package com.sinosafe.xb.manager.adapter.yeji;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinosafe.xb.manager.R;
import com.sinosafe.xb.manager.adapter.base.BaseRecyclerViewAdapter;
import com.sinosafe.xb.manager.adapter.base.BaseRecyclerViewHolder;
import com.sinosafe.xb.manager.module.home.loanmanager.PaymentHistoryActivity;
import com.sinosafe.xb.manager.module.yeji.bean.OverdueBean;
import com.sinosafe.xb.manager.utils.MyUtils;

import luo.library.base.base.BaseFragmentActivity;
import luo.library.base.base.BaseImage;
import luo.library.base.utils.IntentUtil;
import luo.library.base.widget.MoneyView;

/**
 * 逾期金额、笔数适配器
 */
public class YeJiOverdueAdapter extends BaseRecyclerViewAdapter<OverdueBean, BaseRecyclerViewHolder> implements View.OnClickListener {

    private int type = -1;
    private Context mContext;
    private int currentPosition = -1;

    public YeJiOverdueAdapter(Context context, int type) {
        super(context);
        this.type = type;
        this.mContext = context;
    }

    @Override
    protected void bindDataToItemView(BaseRecyclerViewHolder baseRecyclerViewHolder, int position, OverdueBean item) {
        MoneyView tvMoney = baseRecyclerViewHolder.getView(R.id.tvMoney);
        TextView tvStatus = baseRecyclerViewHolder.getView(R.id.tvStatus);
        TextView tvPhone = baseRecyclerViewHolder.getView(R.id.tvPhone);
        ImageView ivImage = baseRecyclerViewHolder.getView(R.id.ivImage);
        TextView tvName = baseRecyclerViewHolder.getView(R.id.tvName);
        TextView tvDay = baseRecyclerViewHolder.getView(R.id.tvDay);
        TextView tv2 = baseRecyclerViewHolder.getView(R.id.tv2);


        tv2.setText("逾期金额");
        tvMoney.setMoneyText(MyUtils.keepTwoDecimal(item.getOVERDUE_AMT()));
        tvDay.setText(item.getOVERDUE_DAY()+"天");
        tvName.setText(item.getCUS_NAME());

        tvPhone.setText(Html.fromHtml("<u>"+item.getMOBILE()+"</u>"));
        BaseImage.getInstance().displayCricleImage(mContext,
                item.getHead_photo(), ivImage, R.mipmap.icon_default_avatar);
        tvStatus.setTag(position);
        tvStatus.setOnClickListener(this);
        tvPhone.setTag(position);
        tvPhone.setOnClickListener(this);
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseRecyclerViewHolder(inflateItemView(parent, R.layout.yeji_overdue_item));
    }

    @Override
    public void onClick(View v) {
        currentPosition = Integer.valueOf(v.getTag().toString());
        Bundle bundle = new Bundle();
        if(v.getId()==R.id.tvStatus){
            OverdueBean overdueBean = getDatas().get(currentPosition);
            bundle.putString("serno", overdueBean.getIQP_LOAN_SERNO());
            bundle.putString("prd_id", "");
            bundle.putString("payType", "");
            IntentUtil.gotoActivity(mContext, PaymentHistoryActivity.class,bundle);
        }

        else if(v.getId()==R.id.tvPhone){
            ((BaseFragmentActivity)mContext).callPhoneTips(getDatas().get(currentPosition).getMOBILE());
        }
    }
}