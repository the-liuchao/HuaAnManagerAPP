package com.sinosafe.xb.manager.module.home.gonggao;

import android.os.Bundle;
import android.widget.TextView;

import com.sinosafe.xb.manager.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import luo.library.base.base.BaseActivity;

/**
 * 公告详情
 * Created by john lee on 2017/5/31.
 */
public class GonggaoDetailActivity extends BaseActivity {


    @BindView(R.id.tvContent)
    TextView mTvContent;
    @BindView(R.id.tvTitle)
    TextView mTvTitle;
    @BindView(R.id.tvTime)
    TextView mTvTime;
    private String detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gonggao_detail);
        ButterKnife.bind(this);
        initView();
    }

    protected void initView() {

        setTitleText("公告详情");
        detail = getIntent().getStringExtra("detail");
        String title = getIntent().getStringExtra("title");
        String time = getIntent().getStringExtra("time");
        //ViewGroup.LayoutParams  params = mTvContent.getLayoutParams();
        //params.height = DensityUtil.heightPixels(this);
        ///mTvContent.setLayoutParams(params);

        mTvContent.setText(detail);
        mTvTime.setText(time);
        mTvTitle.setText(title);
    }
}
