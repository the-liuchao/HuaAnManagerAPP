package com.sinosafe.xb.manager.module.home.xiaofeidai;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sinosafe.xb.manager.R;
import com.sinosafe.xb.manager.adapter.home.ConsumptionLoanProblemAdapter;
import com.sinosafe.xb.manager.api.rxjava.RxHttpResultFunc;
import com.sinosafe.xb.manager.api.rxjava.RxSchedulersHelper;
import com.sinosafe.xb.manager.api.rxjava.RxSubscriber;
import com.sinosafe.xb.manager.bean.BaseEntity;
import com.sinosafe.xb.manager.bean.LoanProblemBean;
import com.sinosafe.xb.manager.model.ClientModel;
import com.sinosafe.xb.manager.widget.recycleview.HeaderAndFooterRecyclerViewAdapter;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import luo.library.base.base.BaseFragmentActivity;
import luo.library.base.utils.MyLog;
import luo.library.base.widget.EmptyLayout;

/**
 * Created by Administrator on 2017/6/17.
 * 消费贷常见问题
 */

public class LoanProblemActivity extends BaseFragmentActivity{

    @BindView(R.id.rv_common_problem)
    RecyclerView mRecycler;
    @BindView(R.id.layoutEmpty)
    EmptyLayout mErrorLayout;
    private ConsumptionLoanProblemAdapter adapter;
    private String prd_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumption_loan_problem);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {


        setTitleText("常见问题");
        prd_id = "1";getIntent().getStringExtra("prd_pk");
        initRecycler();
        mErrorLayout.setOnLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mErrorLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
                getProductProblemList(prd_id);
            }
        });

        showWithStatus("");
        getProductProblemList(prd_id);
    }


    /**
     * 初始化RecyclerView等等
     */
    private void initRecycler() {

        adapter = new ConsumptionLoanProblemAdapter(this);
        HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter
                = new HeaderAndFooterRecyclerViewAdapter(adapter);
        mRecycler.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
    }


    /**
     * 显示请求结果
     * @param problemLists
     */
    public void showProductProblemList(List<LoanProblemBean> problemLists) {
        if (problemLists != null && problemLists.size() > 0) {
            mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
            adapter.update(problemLists);
        } else {
            mErrorLayout.setErrorType(EmptyLayout.NODATA);
        }
    }


    /**
     * 获取消费贷问题列表
     * @param prd_id
     */
    public void getProductProblemList(String prd_id) {

        ClientModel.getProductProblemList(prd_id)
            .timeout(20, TimeUnit.SECONDS)
            .compose(RxSchedulersHelper.<BaseEntity<List<LoanProblemBean>>>io_main())
            .map(new RxHttpResultFunc<List<LoanProblemBean>>())
            .subscribe(new RxSubscriber<List<LoanProblemBean>>() {
                @Override
                public void _onNext(List<LoanProblemBean> list) {

                    showProductProblemList(list);
                    closeSVProgressHUD();
                }
                @Override
                public void _onError(String msg) {
                    closeSVProgressHUD();
                    MyLog.e(msg);
                }});
    }
}
