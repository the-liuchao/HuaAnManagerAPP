package com.sinosafe.xb.manager.adapter.home;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sinosafe.xb.manager.R;
import com.sinosafe.xb.manager.adapter.base.BaseRecyclerViewAdapter;
import com.sinosafe.xb.manager.adapter.base.BaseRecyclerViewHolder;
import com.sinosafe.xb.manager.bean.GongGaoBean;
import com.sinosafe.xb.manager.module.home.gonggao.GonggaoDetailActivity;

import luo.library.base.utils.IntentUtil;


/**
 * 公告栏适配器
 */
public class GongGaoAdapter extends BaseRecyclerViewAdapter<GongGaoBean, BaseRecyclerViewHolder> implements View.OnClickListener{

    private Context mContext;
    public GongGaoAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected void bindDataToItemView(BaseRecyclerViewHolder baseRecyclerViewHolder, int position, GongGaoBean item) {

        TextView tvName = baseRecyclerViewHolder.getView(R.id.tvName);
        TextView tvContent = baseRecyclerViewHolder.getView(R.id.tvContent);
        RelativeLayout itemLayout = baseRecyclerViewHolder.getView(R.id.itemLayout);

        tvName.setText(item.getPost_title());
        tvContent.setText(item.getCreate_date());

        itemLayout.setTag(position);
        itemLayout.setOnClickListener(this);
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseRecyclerViewHolder(inflateItemView(parent, R.layout.gonggao_item));
    }


    @Override
    public void onClick(View v) {

        int index = Integer.valueOf(v.getTag().toString());
        String detail = getDatas().get(index).getPost_content();
        String title = getDatas().get(index).getPost_title();
        String time = getDatas().get(index).getCreate_date();

        Bundle bundle = new Bundle();
        bundle.putString("detail",detail);
        bundle.putString("title",title);
        bundle.putString("time",time);
        IntentUtil.gotoActivity(mContext, GonggaoDetailActivity.class,bundle);
    }
}