package com.sinosafe.xb.manager.module.home.loanmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sinosafe.xb.manager.BaseMainActivity;
import com.sinosafe.xb.manager.R;
import com.sinosafe.xb.manager.adapter.base.OnItemClickListener;
import com.sinosafe.xb.manager.adapter.yewu.YeWuAdapter;
import com.sinosafe.xb.manager.api.APIManager;
import com.sinosafe.xb.manager.api.xutilshttp.OnResponseListener;
import com.sinosafe.xb.manager.api.xutilshttp.XutilsBaseHttp;
import com.sinosafe.xb.manager.bean.BaseEntity;
import com.sinosafe.xb.manager.bean.YeWuBean;
import com.sinosafe.xb.manager.module.home.mianqian.MianQianDetailActivity;
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
import luo.library.base.utils.GsonUtil;
import luo.library.base.utils.IntentUtil;
import luo.library.base.utils.NetworkUtils;
import luo.library.base.widget.EmptyLayout;

/**
 * 贷后管理列表
 */
public class LoanManagerActivity extends BaseFragmentActivity {


    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.layoutEmpty)
    EmptyLayout mErrorLayout;

    private YeWuAdapter yeWuAdapter;
    private LoanManagerActivity instance;

    private int pageNum = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mianqian_list);
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

        setTitleText("贷后管理列表");
        yeWuAdapter = new YeWuAdapter(this,3);

        HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter
                = new HeaderAndFooterRecyclerViewAdapter(yeWuAdapter);
        mRecyclerView.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRefreshLayout.setColorSchemeResources(R.color.status_bar_bg, R.color.orange, R.color.green);
        mRefreshLayout.setOnRefreshListener(mOnRefreshListener);

        mRecyclerView.addOnScrollListener(mOnScrollListener);
        yeWuAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(View view, int position, Object item) {

                yeWuAdapter.setCurrentPosition(position);
                Bundle bundle = new Bundle();
                bundle.putInt("type",1);
                bundle.putSerializable("yeWuBean",yeWuAdapter.getDatas().get(position));
                IntentUtil.gotoActivityForResult(instance,MianQianDetailActivity.class,bundle,10000);
            }
        });

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //补充资料完成后、刷新数据
        if(requestCode==10000&&resultCode==RESULT_OK){
            yeWuAdapter.getDatas().remove(yeWuAdapter.getCurrentPosition());
            yeWuAdapter.notifyDataSetChanged();

            if(yeWuAdapter.getDatas().size()==0){
                mErrorLayout.setErrorType(EmptyLayout.NODATA);
            }else{
                mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
            }
        }
    }

    /**
     * 查询待受理业务
     */
    protected void getData(){

        RequestParams params = new RequestParams(APIManager.SINOSAFE_URL+"manager/business/checkTask");
        params.addQueryStringParameter("token", BaseMainActivity.loginUserBean.getToken());
        params.addQueryStringParameter("cus_mgr", BaseMainActivity.loginUserBean.getActorno());
        params.addQueryStringParameter("task_type",  "50");
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
            if(yeWuAdapter.getDatas().size()==0)
                mErrorLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
        }

        @Override
        public void onCommonRequestCallback(String result) {
            if(pageNum==1){
                mRefreshLayout.setRefreshing(false);
                yeWuAdapter.getDatas().clear();
            }
            BaseEntity entity = GsonUtil.GsonToBean(result,BaseEntity.class);
            List<YeWuBean> yeWuBeens = GsonUtil.GsonToList(entity.getResult().toString(),YeWuBean.class);
            if(yeWuBeens!=null){
                MyUtils.TheEndOrNomal(yeWuBeens,mRecyclerView);
                yeWuAdapter.update(yeWuBeens);
            }
            if(yeWuAdapter.getDatas().size()==0){
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
