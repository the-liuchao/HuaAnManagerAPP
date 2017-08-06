package com.sinosafe.xb.manager.module.yeji;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sinosafe.xb.manager.R;
import com.sinosafe.xb.manager.adapter.yeji.YeJiSortAdapter;
import com.sinosafe.xb.manager.utils.Constant;
import com.sinosafe.xb.manager.widget.recycleview.EndlessRecyclerOnScrollListener;
import com.sinosafe.xb.manager.widget.recycleview.HeaderAndFooterRecyclerViewAdapter;
import com.sinosafe.xb.manager.widget.recycleview.LoadingFooter;
import com.sinosafe.xb.manager.widget.recycleview.RecyclerViewStateUtils;

import butterknife.ButterKnife;
import luo.library.base.utils.NetworkUtils;
import luo.library.base.widget.EmptyLayout;


/**
 * 业绩排行榜片
 */
public class YeJiSortFragment extends YeJiSortBaseFragment {

    private boolean isViewInitiated;

    public static YeJiSortFragment newInstance(int position,int yejiType) {

        YeJiSortFragment applyFragment = new YeJiSortFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", position);
        bundle.putInt("yejiType", yejiType);
        applyFragment.setArguments(bundle);
        return applyFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getInt("type",0);
        yejiType = getArguments().getInt("yejiType",0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_yeji_sort, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initView();
        isViewInitiated = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint() && isViewInitiated) {
            if(yeJiSortAdapter.getDatas().size()==0){
                mRefreshLayout.setRefreshing(true);
                history_type = null;
                setQueryReportRankingMap();
            }
        }
    }

    /**
     * 初始化
     */
    private void initView() {

        yeJiSortAdapter = new YeJiSortAdapter(getActivity(),type);

        HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter
                = new HeaderAndFooterRecyclerViewAdapter(yeJiSortAdapter);
        mRecyclerView.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mRefreshLayout.setColorSchemeResources(R.color.status_bar_bg, R.color.orange, R.color.green);
        mRefreshLayout.setOnRefreshListener(mOnRefreshListener);
        //mRecyclerView.addOnScrollListener(mOnScrollListener);


        mErrorLayout.setOnLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(RecyclerViewStateUtils.getFooterViewState(mRecyclerView) == LoadingFooter.State.Loading) {
                    return;
                }
                mErrorLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
                mRefreshLayout.setRefreshing(true);
                history_type = null;
                setQueryReportRankingMap();
            }
        });
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (getUserVisibleHint() && isViewInitiated) {
            if(yeJiSortAdapter.getDatas().size()==0){
                mRefreshLayout.setRefreshing(true);
                history_type = null;
                setQueryReportRankingMap();
            }
        }
    }

    /**
     * 刷新
     */
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            pageNum = 1;
            //history_type = null;
            setQueryReportRankingMap();
        }
    };

    /**
     * RecycleView的滑动监听(加载更多)
     */
    private EndlessRecyclerOnScrollListener mOnScrollListener = new EndlessRecyclerOnScrollListener() {

        @Override
        public void onLoadNextPage(View view) {
            if(RecyclerViewStateUtils.getFooterViewState(mRecyclerView) == LoadingFooter.State.Loading) {
                return;
            }
            pageNum++;
            if(NetworkUtils.isNetAvailable(getActivity())){ //有网状态
                if(!RecyclerViewStateUtils.setFooterViewState(getActivity(), mRecyclerView,
                        Constant.REQUEST_COUNT, LoadingFooter.State.Loading, null)){
                    pageNum --;
                    return;
                }
                history_type = null;
                setQueryReportRankingMap();
            } else{
                RecyclerViewStateUtils.setFooterViewState(getActivity(), mRecyclerView,
                        Constant.REQUEST_COUNT, LoadingFooter.State.NetWorkError, mFooterClick);
            }
        }
    };

    /**
     * 联网失败后点击重新加载事件
     */
    private View.OnClickListener mFooterClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            history_type = null;
            setQueryReportRankingMap();
        }
    };


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
