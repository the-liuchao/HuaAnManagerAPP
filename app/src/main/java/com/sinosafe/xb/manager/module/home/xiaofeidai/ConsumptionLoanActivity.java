package com.sinosafe.xb.manager.module.home.xiaofeidai;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.View;

import com.sinosafe.xb.manager.R;
import com.sinosafe.xb.manager.adapter.base.OnItemClickListener;
import com.sinosafe.xb.manager.adapter.home.RecommendLoanAdapter;
import com.sinosafe.xb.manager.api.rxjava.RxHttpResultFunc;
import com.sinosafe.xb.manager.api.rxjava.RxSchedulersHelper;
import com.sinosafe.xb.manager.api.rxjava.RxSubscriber;
import com.sinosafe.xb.manager.bean.BaseEntity;
import com.sinosafe.xb.manager.bean.LoanProductBean;
import com.sinosafe.xb.manager.model.ClientModel;
import com.sinosafe.xb.manager.widget.recycleview.HeaderAndFooterRecyclerViewAdapter;
import com.sinosafe.xb.manager.widget.recycleview.LoadingFooter;
import com.sinosafe.xb.manager.widget.recycleview.RecyclerViewStateUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import luo.library.base.base.BaseFragmentActivity;
import luo.library.base.utils.MyLog;
import luo.library.base.widget.EmptyLayout;

/**
 * Created by Administrator on 2017/6/16.
 * 消费贷 推荐贷款
 */

public class ConsumptionLoanActivity extends BaseFragmentActivity{


    @BindView(R.id.rv_recommend_loan)
    RecyclerView mRecycler;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.layoutEmpty)
    EmptyLayout mErrorLayout;
    private RecommendLoanAdapter adapter;
    //0：消费贷，1：微贷
    private int type = -1;
    //产品列表
    private String prdType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumption_loan);
        ButterKnife.bind(this);

        initView();
    }

    /**
     * 初始化
     */
    protected void initView() {
        type = getIntent().getIntExtra("type",0);
        if(type==0) {
            setTitleText("消费贷");
            prdType = "0";
        }
        else{
            setTitleText("微贷");
            prdType = "2";
        }
        initRecommendLoan();
        mRefreshLayout.setRefreshing(true);
        getProduct();
    }

    private void initRecommendLoan() {

        ((SimpleItemAnimator) mRecycler.getItemAnimator()).setSupportsChangeAnimations(false);

        adapter = new RecommendLoanAdapter(this);
        HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter
                = new HeaderAndFooterRecyclerViewAdapter(adapter);
        mRecycler.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));

        mRefreshLayout.setColorSchemeResources(R.color.status_bar_bg, R.color.orange, R.color.green);
        mRefreshLayout.setOnRefreshListener(mOnRefreshListener);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(View view, int position, Object item) {

                LoanProductBean loan = adapter.getDatas().get(position);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("loan", loan);
                mBundle.putInt("type",type);
                Intent intent = new Intent(ConsumptionLoanActivity.this, XiaoFeiDaiActivity.class);
                intent.putExtras(mBundle);
                startActivity(intent);

            }
        });

        mErrorLayout.setOnLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(RecyclerViewStateUtils.getFooterViewState(mRecycler) == LoadingFooter.State.Loading) {
                    return;
                }
                mErrorLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
                mRefreshLayout.setRefreshing(true);
                getProduct();
            }
        });
    }


    /**
     * 刷新
     */
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mRefreshLayout.setRefreshing(true);
            getProduct();
        }
    };


    /**
     * 获取消费贷列表
     */
    private void getProduct() {
        Map<String, String> maps = new HashMap<>();
        maps.put("type", prdType);
        getProductList(maps);
    }


    /**
     * 获取消费贷列表
     * @param maps
     */
    public void getProductList(Map<String, String> maps) {
        ClientModel.getProductList(maps)
            .timeout(20, TimeUnit.SECONDS)
            .compose(RxSchedulersHelper.<BaseEntity<List<LoanProductBean>>>io_main())
            .map(new RxHttpResultFunc<List<LoanProductBean>>())
            .subscribe(new RxSubscriber<List<LoanProductBean>>() {
                @Override
                public void _onNext(List<LoanProductBean> list) {

                    showProductList(list);
                    if(adapter.getDatas().size()==0){
                        mErrorLayout.setErrorType(EmptyLayout.NODATA);
                    }else{
                        mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
                    }
                    mRefreshLayout.setRefreshing(false);
                }
                @Override
                public void _onError(String msg) {
                    mRefreshLayout.setRefreshing(false);
                    if(adapter.getDatas().size()==0)
                        mErrorLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
                    MyLog.e(msg);
                }});
    }

    /**
     * 显示消费贷列表
     * @param products
     */
    public void showProductList(List<LoanProductBean> products) {
        if (products == null)
            return;
        adapter.getDatas().clear();
        adapter.update(products);
    }
}
