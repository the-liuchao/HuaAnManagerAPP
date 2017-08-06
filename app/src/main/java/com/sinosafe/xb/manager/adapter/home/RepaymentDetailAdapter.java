package com.sinosafe.xb.manager.adapter.home;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sinosafe.xb.manager.R;
import com.sinosafe.xb.manager.adapter.base.BaseRecyclerViewAdapter;
import com.sinosafe.xb.manager.adapter.base.BaseRecyclerViewHolder;
import com.sinosafe.xb.manager.module.home.loanmanager.MyRepayDetail;

import luo.library.base.utils.DensityUtil;


/**
 * 还款明细适配器
 */
public class RepaymentDetailAdapter extends BaseRecyclerViewAdapter<MyRepayDetail, BaseRecyclerViewHolder>
    implements View.OnClickListener{

    private int currentIndex = -1;
    private Context mcContext;
    public RepaymentDetailAdapter(Context context) {
        super(context);
        mcContext = context;
    }

    @Override
    protected void bindDataToItemView(BaseRecyclerViewHolder baseRecyclerViewHolder, int position, MyRepayDetail item) {

        TextView tvPosition = baseRecyclerViewHolder.getView(R.id.tvPosition);
        TextView tvPosition2 = baseRecyclerViewHolder.getView(R.id.tvPosition2);
        //应还款时间
        TextView tvTime = baseRecyclerViewHolder.getView(R.id.tvTime);

        //中间明细item
        LinearLayout middleItem = baseRecyclerViewHolder.getView(R.id.middleItem);
        //整个item
        RelativeLayout itemLayout = baseRecyclerViewHolder.getView(R.id.itemLayout);

        //应还款金额
        TextView tvCorpusDes = baseRecyclerViewHolder.getView(R.id.tvCorpusDes);;
        TextView tvCorpus = baseRecyclerViewHolder.getView(R.id.tvCorpus);
        //明细
        TextView tvRepayment = baseRecyclerViewHolder.getView(R.id.tvRepayment);
        //底部描述
        TextView tvBottom = baseRecyclerViewHolder.getView(R.id.tvBottom);

        //是否上报征信
        TextView tvCredit = baseRecyclerViewHolder.getView(R.id.tvCredit);
        //逾期
        TextView tvDes = baseRecyclerViewHolder.getView(R.id.tvDes);
        //逾期金额
        TextView tvOverdueAmount = baseRecyclerViewHolder.getView(R.id.tvOverdueAmount);


        itemLayout.setOnClickListener(this);
        itemLayout.setTag(position);

        if(item.isCanOpen()){
            //打开
            if(item.isOpenFlag()){
                middleItem.setVisibility(View.VISIBLE);
                tvPosition2.setVisibility(View.GONE);
                tvPosition.setVisibility(View.VISIBLE);
                tvCorpusDes.setVisibility(View.GONE);
                tvCorpus.setVisibility(View.GONE);
                tvOverdueAmount.setVisibility(View.VISIBLE);
            }else{
                middleItem.setVisibility(View.GONE);
                tvPosition2.setVisibility(View.VISIBLE);
                tvPosition.setVisibility(View.INVISIBLE);

                tvCorpusDes.setVisibility(View.VISIBLE);
                tvCorpus.setVisibility(View.VISIBLE);
                tvOverdueAmount.setVisibility(View.GONE);
            }

            //如果未还金额为0，则不显示
            if("0.00".equals(item.getDueAmount())){
                tvCorpusDes.setVisibility(View.GONE);
                tvCorpus.setVisibility(View.GONE);
                tvOverdueAmount.setVisibility(View.GONE);
            }

        }else{
            middleItem.setVisibility(View.GONE);
            tvPosition2.setVisibility(View.VISIBLE);
            tvPosition.setVisibility(View.INVISIBLE);

            tvCorpusDes.setVisibility(View.VISIBLE);
            tvCorpus.setVisibility(View.VISIBLE);

            //如果未还金额为0，则不显示
            if("0.00".equals(item.getDueAmount())){
                tvCorpusDes.setVisibility(View.GONE);
                tvCorpus.setVisibility(View.GONE);
                tvOverdueAmount.setVisibility(View.GONE);
            }
        }

        //逾期
        if(Float.valueOf(item.getDueAmount())>0){
            tvDes.setVisibility(View.VISIBLE);
            itemLayout.setBackgroundColor(Color.parseColor("#FFE7E5"));
            middleItem.setBackgroundColor(Color.parseColor("#FFE7E5"));

            middleItem.setPadding(DensityUtil.dp2px(mcContext,36),0,DensityUtil.dp2px(mcContext,10),0);

            if(item.getIS_CREDIT_REPORT().equals("1"))
                tvCredit.setVisibility(View.VISIBLE);
            else{
                tvCredit.setVisibility(View.GONE);
            }
        }
        //其他
        else{

            middleItem.setPadding(DensityUtil.dp2px(mcContext,36),DensityUtil.dp2px(mcContext,8),DensityUtil.dp2px(mcContext,10),DensityUtil.dp2px(mcContext,8));

            tvDes.setVisibility(View.GONE);
            itemLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
            middleItem.setBackgroundColor(Color.parseColor("#E7E9F9"));
            tvCredit.setVisibility(View.GONE);
        }

        tvPosition2.setText(item.getIssueNumber()+"");
        tvPosition.setText(item.getIssueNumber()+"");
        tvTime.setText(item.getRepaymentTime());
        tvBottom.setText(item.getSurvey());
        tvRepayment.setText(item.getDetailed());
        tvCorpus.setText(item.getDueAmount());
        tvOverdueAmount.setText("逾期金额:"+ item.getDueAmount());
    }


    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseRecyclerViewHolder(inflateItemView(parent, R.layout.repayment_detail_item));
    }


    @Override
    public void onClick(View v) {
        currentIndex = Integer.valueOf(v.getTag().toString());
        boolean open = getDatas().get(currentIndex).isOpenFlag();
        if(open){
            getDatas().get(currentIndex).setOpenFlag(false);
        }else {
            getDatas().get(currentIndex).setOpenFlag(true);
        }
        notifyDataSetChanged();
    }
}