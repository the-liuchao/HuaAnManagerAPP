package com.sinosafe.xb.manager.module.yeji;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.sinosafe.xb.manager.BaseMainActivity;
import com.sinosafe.xb.manager.R;
import com.sinosafe.xb.manager.adapter.yeji.YeJiOverdueAdapter;
import com.sinosafe.xb.manager.api.APIManager;
import com.sinosafe.xb.manager.api.xutilshttp.OnResponseListener;
import com.sinosafe.xb.manager.api.xutilshttp.XutilsBaseHttp;
import com.sinosafe.xb.manager.bean.BaseEntity;
import com.sinosafe.xb.manager.module.yeji.bean.OverdueBean;
import com.sinosafe.xb.manager.utils.Constant;
import com.sinosafe.xb.manager.utils.MyUtils;
import com.sinosafe.xb.manager.widget.recycleview.EndlessRecyclerOnScrollListener;
import com.sinosafe.xb.manager.widget.recycleview.HeaderAndFooterRecyclerViewAdapter;
import com.sinosafe.xb.manager.widget.recycleview.LoadingFooter;
import com.sinosafe.xb.manager.widget.recycleview.RecyclerViewStateUtils;

import org.xutils.http.RequestParams;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import luo.library.base.base.BaseFragmentActivity;
import luo.library.base.utils.NetworkUtils;
import luo.library.base.widget.EmptyLayout;

import static luo.library.base.utils.GsonUtil.GsonToBean;

/**
 * 类名称：   com.sinosafe.xb.manager.module.yeji
 * 内容摘要： //逾期笔数、逾期金额。
 * 修改备注：
 * 创建时间： 2017/7/1 0001
 * 公司：     深圳市华移科技股份有限公司
 * 作者：     Mr 张
 */
public class OverdueActivity extends BaseFragmentActivity{

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.layoutEmpty)
    EmptyLayout mErrorLayout;


    private YeJiOverdueAdapter yeJiOverdueAdapter;
    private OverdueActivity instance;
    private int pageNum = 1;
    //类型  0：逾期笔数；1：逾期金额
    private int type = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overdue_list);
        instance = this;
        ButterKnife.bind(this);

        initView();
        mRefreshLayout.setRefreshing(true);
        getData();
    }

    /**
     * 初始化
     */
    private void initView() {

        type = getIntent().getIntExtra("type",0);
        if(type==0)
            setTitleText("逾期笔数");
        else{
            setTitleText("逾期金额");
        }

        yeJiOverdueAdapter = new YeJiOverdueAdapter(this,type);
        HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter
                = new HeaderAndFooterRecyclerViewAdapter(yeJiOverdueAdapter);
        mRecyclerView.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

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
                getData();
            }
        });
    }


    /**
     * 刷新
     */
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {

            pageNum = 1;
            getData();
        }
    };

    /**
     * RecycleView的滑动监听(加载更多)
     */
    private EndlessRecyclerOnScrollListener mOnScrollListener = new EndlessRecyclerOnScrollListener() {

        @Override
        public void onLoadNextPage(View view) {
            super.onLoadNextPage(view);
            if(RecyclerViewStateUtils.getFooterViewState(mRecyclerView) == LoadingFooter.State.Loading) {
                return;
            }
            pageNum++;
            if(NetworkUtils.isNetAvailable(instance)){ //有网状态
                if(!RecyclerViewStateUtils.setFooterViewState(instance, mRecyclerView,
                        Constant.REQUEST_COUNT, LoadingFooter.State.Loading, null)){
                    pageNum --;
                    return;
                }
                getData();
            } else{
                RecyclerViewStateUtils.setFooterViewState(instance, mRecyclerView,
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
            getData();
        }
    };



    /**
     * 查询待受理业务
     */
    protected void getData(){

        RequestParams params = new RequestParams(APIManager.SINOSAFE_URL+"manager/business/queryMgrDelay");
        params.addQueryStringParameter("token", BaseMainActivity.loginUserBean.getToken());
        params.addQueryStringParameter("report_type",  "000"+(type+1));
        params.addQueryStringParameter("curPage",  pageNum+"");
        XutilsBaseHttp.requestType = -1;
        XutilsBaseHttp.get(params, onResponseListener);

    }


    private OnResponseListener onResponseListener = new OnResponseListener() {
        @Override
        public void onRequestFailedCallback(String var1) {
            if(pageNum==1){
                mRefreshLayout.setRefreshing(false);
            }
            if(yeJiOverdueAdapter.getDatas().size()==0)
                mErrorLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
        }

        @Override
        public void onCommonRequestCallback(String result) {
            if(pageNum==1){
                mRefreshLayout.setRefreshing(false);
                yeJiOverdueAdapter.getDatas().clear();
            }
            BaseEntity entity = GsonToBean(result,BaseEntity.class);
            //List<OverdueBean> overdueBeen = GsonUtil.GsonToList(entity.getResult().toString(),OverdueBean.class);
            List<OverdueBean> overdueBeen = JSON.parseArray(entity.getResult().toString(),OverdueBean.class);
            if(overdueBeen!=null){
                MyUtils.TheEndOrNomal(overdueBeen,mRecyclerView);
                yeJiOverdueAdapter.update(overdueBeen);
            }
            if(yeJiOverdueAdapter.getDatas().size()==0){
                mErrorLayout.setErrorType(EmptyLayout.NODATA);
            }else{
                mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        instance = null;
    }
}
