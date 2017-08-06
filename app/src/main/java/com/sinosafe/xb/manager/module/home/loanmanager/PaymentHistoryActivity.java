package com.sinosafe.xb.manager.module.home.loanmanager;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;

import com.sinosafe.xb.manager.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import luo.library.base.base.BaseFragmentActivity;
import luo.library.base.widget.SegmentView;

/**
 * 类名称：   com.sinosafe.xb.manager.module.home.loanmanager
 * 内容摘要： //还款记录。
 * 修改备注：
 * 创建时间： 2017/7/1 0001
 * 公司：     深圳市华移科技股份有限公司
 * 作者：     Mr 张
 */
public class PaymentHistoryActivity extends BaseFragmentActivity implements SegmentView.onSegmentViewClickListener {


    @BindView(R.id.ly_base_back)
    LinearLayout mLyBaseBack;
    @BindView(R.id.segmentView)
    SegmentView mSegmentView;

    private FragmentTransaction tx;
    RepaymentDetailsFragment repaymentDetailsFragment;
    RepaymentStreamFragment repaymentStreamFragment;

    //流水号、产品编号、还款方式
    private String serno,prd_id,payType;
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_history);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {

        serno = getIntent().getStringExtra("serno");
        prd_id = getIntent().getStringExtra("prd_id");
        payType = getIntent().getStringExtra("payType");

        mSegmentView.setSegmentText("还款明细", 0);
        mSegmentView.setSegmentText("还款流水", 1);
        mSegmentView.setOnSegmentViewClickListener(this);

        if (tx == null) {
            tx = getSupportFragmentManager().beginTransaction();
            repaymentDetailsFragment = RepaymentDetailsFragment.newInstance();
            repaymentStreamFragment = RepaymentStreamFragment.newInstance();

            tx.add(R.id.viewPager, repaymentDetailsFragment);
            tx.add(R.id.viewPager, repaymentStreamFragment);

            tx.hide(repaymentDetailsFragment);
            tx.hide(repaymentStreamFragment);
            tx.show(repaymentDetailsFragment);
            tx.commit();
        }
    }

    @OnClick(R.id.ly_base_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onSegmentViewClick(View v, int position) {
        FragmentTransaction tx = getSupportFragmentManager()
                .beginTransaction();
        if (position == 0) {
            if(currentIndex==0)
                return;
            tx.hide(repaymentStreamFragment);
            tx.show(repaymentDetailsFragment);
            tx.commit();
            currentIndex = 0;
        } else if (position == 1) {
            if(currentIndex==1)
                return;
            tx.hide(repaymentDetailsFragment);
            tx.show(repaymentStreamFragment);
            tx.commit();
            currentIndex = 1;
        }
    }

    public String getSerno() {
        return serno;
    }

    public String getPrd_id() {
        return prd_id;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public String getPayType() {
        return payType;
    }
}
