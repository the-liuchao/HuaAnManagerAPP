package com.sinosafe.xb.manager.module.home.loanmanager;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sinosafe.xb.manager.BaseMainActivity;
import com.sinosafe.xb.manager.R;
import com.sinosafe.xb.manager.adapter.home.RepaymentPlansAdapter;
import com.sinosafe.xb.manager.api.APIManager;
import com.sinosafe.xb.manager.api.xutilshttp.OnResponseListener;
import com.sinosafe.xb.manager.api.xutilshttp.XutilsBaseHttp;
import com.sinosafe.xb.manager.bean.BaseEntity;
import com.sinosafe.xb.manager.utils.MyUtils;
import com.sinosafe.xb.manager.widget.recycleview.HeaderAndFooterRecyclerViewAdapter;
import com.sinosafe.xb.manager.widget.recycleview.LoadingFooter;
import com.sinosafe.xb.manager.widget.recycleview.RecyclerViewStateUtils;

import org.xutils.http.RequestParams;

import butterknife.BindView;
import butterknife.ButterKnife;
import luo.library.base.base.BaseFragment;
import luo.library.base.utils.GsonUtil;
import luo.library.base.utils.MyLog;
import luo.library.base.widget.EmptyLayout;

/**
 * 还款流水
 */
public class RepaymentStreamFragment extends BaseFragment {


    private static RepaymentStreamFragment progressFragment;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.layoutEmpty)
    EmptyLayout mLayoutEmpty;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout mRefreshLayout;
    private TextView mTvRepayMode;
    RepaymentPlansAdapter repaymentPlansAdapter;
    protected boolean isViewInitiated;

    public static RepaymentStreamFragment newInstance() {
        if (progressFragment == null)
            progressFragment = new RepaymentStreamFragment();
        return progressFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repayment_history_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewInitiated = true;
        initView();
    }

    /**
     * 初始化
     */
    public void initView() {

        repaymentPlansAdapter = new RepaymentPlansAdapter(getActivity());
        HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter
                = new HeaderAndFooterRecyclerViewAdapter(repaymentPlansAdapter);
        mRecyclerView.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        View view = getActivity().getLayoutInflater().inflate(R.layout.repayment_mode,null);
        mTvRepayMode = (TextView) view.findViewById(R.id.tvRepayMode);
        mTvRepayMode.setText(MyUtils.getRepaymentModel(((PaymentHistoryActivity)getActivity()).getPayType()));
        mHeaderAndFooterRecyclerViewAdapter.addHeaderView(view);

        mRefreshLayout.setColorSchemeResources(R.color.status_bar_bg, R.color.orange, R.color.green);
        mRefreshLayout.setOnRefreshListener(mOnRefreshListener);

        mLayoutEmpty.setOnLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(RecyclerViewStateUtils.getFooterViewState(mRecyclerView) == LoadingFooter.State.Loading) {
                    return;
                }
                mLayoutEmpty.setErrorType(EmptyLayout.NETWORK_LOADING);
                mRefreshLayout.setRefreshing(true);
                getData();
            }
        });

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && isViewInitiated && repaymentPlansAdapter.getDatas().size()==0) {
            mRefreshLayout.setRefreshing(true);
            getData();
        }
    }

    /**
     * 刷新
     */
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mRefreshLayout.setRefreshing(true);
            getData();
        }
    };


    private void getData(){

        RequestParams params = new RequestParams(APIManager.SINOSAFE_URL+"manager/business/queryRepayDetail");
        params.addQueryStringParameter("token", BaseMainActivity.loginUserBean.getToken());
        params.addQueryStringParameter("serno", ((PaymentHistoryActivity)getActivity()).getSerno());//
        params.addQueryStringParameter("prd_id",  ((PaymentHistoryActivity)getActivity()).getPrd_id());
        XutilsBaseHttp.requestType = -1;
        XutilsBaseHttp.get(params, onResponseListener);
    }

    private OnResponseListener onResponseListener = new OnResponseListener() {
        @Override
        public void onRequestFailedCallback(String var1) {
            mRefreshLayout.setRefreshing(false);
            if(repaymentPlansAdapter.getDatas().size()==0)
                mLayoutEmpty.setErrorType(EmptyLayout.NETWORK_ERROR);
        }

        @Override
        public void onCommonRequestCallback(String result) {
            mRefreshLayout.setRefreshing(false);
            repaymentPlansAdapter.getDatas().clear();
            BaseEntity entity = GsonUtil.GsonToBean(result,BaseEntity.class);
            try {
                RepaymentDetails repaymentDetails = GsonUtil.GsonToBean(entity.getResult().toString(),RepaymentDetails.class);
                if(repaymentDetails!=null&&repaymentDetails.getAccMtdPsNote()!=null){
                    MyUtils.TheEndOrNomal(repaymentDetails.getAccMtdPsNote(),mRecyclerView);
                    repaymentPlansAdapter.update(repaymentDetails.getAccMtdPsNote());

                    String repayment_mode = repaymentDetails.getAccMtdPlan().get(0).getREPAYMENT_MODE();
                    mTvRepayMode.setText(MyUtils.getRepaymentModel(repayment_mode));
                }
            }catch (Exception e){
                MyLog.e("还款记录明细====="+e.getMessage()+"     "+entity.getMsg());
            }
            if(repaymentPlansAdapter.getDatas().size()==0){
                mLayoutEmpty.setErrorType(EmptyLayout.NODATA);
            }else{
                mLayoutEmpty.setErrorType(EmptyLayout.HIDE_LAYOUT);
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        progressFragment = null;
    }
}
