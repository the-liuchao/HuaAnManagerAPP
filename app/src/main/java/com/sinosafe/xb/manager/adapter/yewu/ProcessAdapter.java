package com.sinosafe.xb.manager.adapter.yewu;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinosafe.xb.manager.R;
import com.sinosafe.xb.manager.module.yewu.xiaofeidai.LoanDetail;

import java.util.List;

/**
 * 类名称：   com.sinosafe.xb.manager.adapter.yewu
 * 内容摘要： //消费贷审核进度 适配器。
 * 修改备注：
 * 创建时间： 2017/6/7 0007
 * 公司：     深圳市华移科技股份有限公司
 * 作者：     Mr 张
 */
public class ProcessAdapter extends RecyclerView.Adapter<ProcessAdapter.ProcessHolder> {
    private List<LoanDetail.LoadSchedual> mDataSet;

    public ProcessAdapter(List<LoanDetail.LoadSchedual> models) {
        mDataSet = models;
    }


    @Override
    public ProcessHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_process_line, viewGroup, false);
        return new ProcessHolder(v, viewType);
    }

    @Override
    public void onBindViewHolder(ProcessHolder timeLineViewHolder, int i) {

        timeLineViewHolder.setData(mDataSet.get(i), i);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }


    class ProcessHolder extends RecyclerView.ViewHolder {
        private TextView mName;
        private TextView tv_time;
        private ImageView mOrder;
        private View line1;
        private View line2;


        public ProcessHolder(View itemView, int type) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.tv_process);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            mOrder = (ImageView) itemView.findViewById(R.id.tv_order);
            line1 = (View) itemView.findViewById(R.id.line1);
            line2 = (View) itemView.findViewById(R.id.line2);
        }

        public void setData(LoanDetail.LoadSchedual data, int i) {

            //第一个
            if(i==0){
                line1.setVisibility(View.INVISIBLE);
            }else {
                line1.setVisibility(View.VISIBLE);
            }

            //最后一个
            if(i==mDataSet.size()-1)
                line2.setVisibility(View.INVISIBLE);
            else{
                line2.setVisibility(View.VISIBLE);
            }

            mName.setText(data.getNODENAME());
            tv_time.setText(data.getOPER_TIME());
            if(i == mDataSet.size()-1){

                mOrder.setImageResource(R.mipmap.icon_jinduyuan2);
                mName.setTextColor(Color.parseColor("#747ff3"));
            }
           else{
                mOrder.setImageResource(R.mipmap.icon_jinduyuan);
                mName.setTextColor(Color.parseColor("#282932"));
            }

        }
    }
}
