package com.sinosafe.xb.manager.adapter.home;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sinosafe.xb.manager.R;
import com.sinosafe.xb.manager.adapter.base.BaseRecyclerViewAdapter;
import com.sinosafe.xb.manager.adapter.base.BaseRecyclerViewHolder;
import com.sinosafe.xb.manager.module.home.loanmanager.RepaymentDetails;
import com.sinosafe.xb.manager.utils.MyUtils;


/**
 * 还款计划适配器
 */
public class RepaymentPlansAdapter extends BaseRecyclerViewAdapter<RepaymentDetails.RepaymentNote, BaseRecyclerViewHolder> {


    public RepaymentPlansAdapter(Context context) {
        super(context);
    }

    @Override
    protected void bindDataToItemView(BaseRecyclerViewHolder baseRecyclerViewHolder, int position, RepaymentDetails.RepaymentNote item) {

        TextView tvDate = baseRecyclerViewHolder.getView(R.id.tvDate);
        TextView tvRepayMoney = baseRecyclerViewHolder.getView(R.id.tvRepayMoney);
        ;
        TextView tvTime = baseRecyclerViewHolder.getView(R.id.tvTime);
        ;

        tvRepayMoney.setText("-" + MyUtils.keepTwoDecimal(getRepayments(item)));
        String dateArr[] = item.getPS_REAL_DT().split(" ");
        if (dateArr.length == 1) {
            tvDate.setText(dateArr[0]);
            tvTime.setVisibility(View.GONE);
        } else {
            tvDate.setText(dateArr[0]);
            tvTime.setText(dateArr[1]);
            tvTime.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseRecyclerViewHolder(inflateItemView(parent, R.layout.repayment_plan_item));
    }

    //明细：已还款金额
    private float getRepayments(RepaymentDetails.RepaymentNote repaymentNote){
        float repayments = 0;
        repayments += repaymentNote.getPS_REAL_PRCP_AMT()+repaymentNote.getPS_REAL_INT_AMT()+repaymentNote.getSETL_OD_INC_TAKEN()+repaymentNote.getSETL_COMM_OD_INT();
        return repayments;
    }
}