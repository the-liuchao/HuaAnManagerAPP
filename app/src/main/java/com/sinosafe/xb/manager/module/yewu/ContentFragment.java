package com.sinosafe.xb.manager.module.yewu;


import android.content.BroadcastReceiver;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sinosafe.xb.manager.BaseMainActivity;
import com.sinosafe.xb.manager.R;
import com.sinosafe.xb.manager.adapter.base.OnItemClickListener;
import com.sinosafe.xb.manager.adapter.base.OnItemLongClickListener;
import com.sinosafe.xb.manager.adapter.yewu.WeiDaiAdapter;
import com.sinosafe.xb.manager.adapter.yewu.YeWuAdapter;
import com.sinosafe.xb.manager.api.APIManager;
import com.sinosafe.xb.manager.api.xutilshttp.OnResponseListener;
import com.sinosafe.xb.manager.api.xutilshttp.XutilsBaseHttp;
import com.sinosafe.xb.manager.bean.BaseEntity;
import com.sinosafe.xb.manager.bean.YeWuBean;
import com.sinosafe.xb.manager.module.home.mianqian.MianQianDetailActivity;
import com.sinosafe.xb.manager.module.yewu.weidai.MicroCreditDetailActivity;
import com.sinosafe.xb.manager.module.yewu.xiaofeidai.ConsumeDetailActivity;
import com.sinosafe.xb.manager.utils.Constant;
import com.sinosafe.xb.manager.utils.MyUtils;
import com.sinosafe.xb.manager.utils.T;
import com.sinosafe.xb.manager.widget.recycleview.EndlessRecyclerOnScrollListener;
import com.sinosafe.xb.manager.widget.recycleview.HeaderAndFooterRecyclerViewAdapter;
import com.sinosafe.xb.manager.widget.recycleview.LoadingFooter;
import com.sinosafe.xb.manager.widget.recycleview.RecyclerViewStateUtils;

import org.xutils.http.RequestParams;

import java.util.List;

import butterknife.ButterKnife;
import luo.library.base.utils.GsonUtil;
import luo.library.base.utils.IntentUtil;
import luo.library.base.utils.NetworkUtils;
import luo.library.base.widget.EmptyLayout;


/**
 * 业务内容片
 */
public class ContentFragment extends ContentBaseFragment {


    public static ContentFragment newInstance(int position) {

        ContentFragment applyFragment = new ContentFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", position);
        applyFragment.setArguments(bundle);
        return applyFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getInt("type",0);
        if(type==0){
            taskType = "10";
            IntentFilter filter = new IntentFilter();
            filter.addAction(Constant.LOAD_YEWU_DATA_OF_APPLY);
            filter.addAction(Constant.REFRESH_APPLYING_DATA);
            filter.addAction(Constant.APPLY_AFTER_AUTHORIZATION);
            getActivity().registerReceiver(receiver,filter);
        }else if(type==1){
            taskType = "60";
            IntentFilter filter = new IntentFilter();
            filter.addAction(Constant.REFRESH_YEWU_DATA);
            getActivity().registerReceiver(receiver,filter);
        }else if(type==2){
            taskType = "70";
            IntentFilter filter = new IntentFilter();
            filter.addAction(Constant.REFRESH_YEWU_DATA);
            getActivity().registerReceiver(receiver,filter);
        }else if(type==3){
            taskType = "80";
            IntentFilter filter = new IntentFilter();
            filter.addAction(Constant.REFRESH_YEWU_REPAYMENT_DATA);
            getActivity().registerReceiver(receiver,filter);
        }else if(type==4){
            taskType = "90";
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.yewu_content, container, false);
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

    /**
     * 初始化
     */
    private void initView() {

        if(type!=0){
            yeWuAdapter = new YeWuAdapter(getActivity(),type);
            HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter
                    = new HeaderAndFooterRecyclerViewAdapter(yeWuAdapter);
            mRecyclerView.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            mRefreshLayout.setColorSchemeResources(R.color.status_bar_bg, R.color.orange, R.color.green);
            mRefreshLayout.setOnRefreshListener(mOnRefreshListener);
            mRecyclerView.addOnScrollListener(mOnScrollListener);

            yeWuAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onClick(View view, int position, Object item) {

                    yeWuAdapter.setCurrentPosition(position);
                    YeWuBean yeWuBean = yeWuAdapter.getDatas().get(position);
                    Bundle bundle = new Bundle();
                    //待面签
                    if("112".equals(yeWuBean.getNEW_APPROVE_STATUS())){
                        bundle.putSerializable("yeWuBean",yeWuBean);
                        String prdType = yeWuBean.getPRD_TYPE();
                        if("2".equals(prdType))
                            IntentUtil.gotoActivityForResult(getActivity(),MicroCreditDetailActivity.class,bundle,10000);
                        else
                            IntentUtil.gotoActivityForResult(getActivity(),MianQianDetailActivity.class,bundle,10000);
                        //IntentUtil.gotoActivityForResult(getActivity(),MianQianDetailActivity.class,bundle,10000);
                    }
                    //补充资料
                    else if("090".equals(yeWuBean.getNEW_APPROVE_STATUS())){
                        bundle.putInt("type",5);
                        bundle.putString("serno",yeWuBean.getSERNO());
                        bundle.putString("prdId",yeWuBean.getPRD_ID());
                        String prdType = yeWuBean.getPRD_TYPE();
                        if("2".equals(prdType))
                            IntentUtil.gotoActivityForResult(getActivity(),MicroCreditDetailActivity.class,bundle,10000);
                        else
                            IntentUtil.gotoActivityForResult(getActivity(),ConsumeDetailActivity.class,bundle,10000);
                        //IntentUtil.gotoActivityForResult(getActivity(),ConsumeDetailActivity.class,bundle,10000);
                    }
                    //其他状态
                    else{
                        bundle.putString("serno",yeWuBean.getSERNO());
                        bundle.putString("prdId",yeWuBean.getPRD_ID());
                        bundle.putString("isLoanCheck",yeWuBean.getIS_LOAN_CHECK());
                        bundle.putInt("type",type);
                        String prdType = yeWuBean.getPRD_TYPE();
                        if("2".equals(prdType))
                            IntentUtil.gotoActivity(getActivity(),MicroCreditDetailActivity.class,bundle);
                        else
                            IntentUtil.gotoActivity(getActivity(),ConsumeDetailActivity.class,bundle);
                        //IntentUtil.gotoActivity(getActivity(),ConsumeDetailActivity.class,bundle);
                    }
                }
            });

            yeWuAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
                @Override
                public void onLongClick(View view, int position, Object item) {
                    ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                    cm.setText(yeWuAdapter.getDatas().get(position).getSERNO());
                    T.showShortBottom(yeWuAdapter.getDatas().get(position).getSERNO());
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

        //微贷本地数据
        else{

            weiDaiAdapter = new WeiDaiAdapter(getActivity());
            HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter
                    = new HeaderAndFooterRecyclerViewAdapter(weiDaiAdapter);
            mRecyclerView.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            mRefreshLayout.setColorSchemeResources(R.color.status_bar_bg, R.color.orange, R.color.green);
            mRefreshLayout.setOnRefreshListener(mOnRefreshListener);
            mErrorLayout.setOnLayoutClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mErrorLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
                    mRefreshLayout.setRefreshing(true);
                    getApplyingData();
                }
            });
        }
    }


    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            //没有数据时，刷新申请中的数据
            if(intent.getAction().equals(Constant.LOAD_YEWU_DATA_OF_APPLY)){
                if(getUserVisibleHint()&&type==0){
                    if(weiDaiAdapter.getDatas().size()==0){
                        mRefreshLayout.setRefreshing(true);
                        getApplyingData();
                    }else{
                        weiDaiAdapter.getLocalDataList();
                        weiDaiAdapter.notifyDataSetChanged();
                    }
                }
            }
            //保存微贷数据后，刷新列表数据
           else if(intent.getAction().equals(Constant.REFRESH_APPLYING_DATA)){
                if(getUserVisibleHint()&&type==0){
                    mRefreshLayout.setRefreshing(true);
                    getApplyingData();
                }
            }
            //授权回来自动继续申请
            else if(intent.getAction().equals(Constant.APPLY_AFTER_AUTHORIZATION)){
                weiDaiAdapter.consumptionApplying();
            }
            //刷新审批中、待放款数据
            else if(intent.getAction().equals(Constant.REFRESH_YEWU_DATA)){

                if(yeWuAdapter==null||yeWuAdapter.getCurrentPosition()==-1)
                    return;
                //面签：112；090：补充资料；995：待缴费；091：在线签投保单
                YeWuBean yeWuBean = yeWuAdapter.getDatas().get(yeWuAdapter.getCurrentPosition());
                String approve_status = yeWuBean.getNEW_APPROVE_STATUS();
                if("995".equals(yeWuBean.getNEW_APPROVE_STATUS())){
                    yeWuAdapter.getDatas().get(yeWuAdapter.getCurrentPosition()).setNEW_APPROVE_STATUS("1001");
                    yeWuAdapter.notifyDataSetChanged();
                }
                else if("091".equals(yeWuBean.getNEW_APPROVE_STATUS())){
                    yeWuAdapter.getDatas().get(yeWuAdapter.getCurrentPosition()).setNEW_APPROVE_STATUS("995");
                    yeWuAdapter.notifyDataSetChanged();
                }
                else if("112".equals(yeWuBean.getNEW_APPROVE_STATUS())){
                    yeWuAdapter.getDatas().get(yeWuAdapter.getCurrentPosition()).setNEW_APPROVE_STATUS("111");
                    yeWuAdapter.notifyDataSetChanged();
                }
                else if("090".equals(yeWuBean.getNEW_APPROVE_STATUS())){
                    yeWuAdapter.getDatas().get(yeWuAdapter.getCurrentPosition()).setNEW_APPROVE_STATUS("112");
                    yeWuAdapter.notifyDataSetChanged();
                }
            }
            //刷新还款中数据
            else if(intent.getAction().equals(Constant.REFRESH_YEWU_REPAYMENT_DATA)){

                yeWuAdapter.getDatas().get(yeWuAdapter.getCurrentPosition()).setIS_LOAN_CHECK("0");
                yeWuAdapter.notifyDataSetChanged();
            }
        }
    };


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint() && isViewInitiated) {
            if(type!=0&&yeWuAdapter.getDatas().size()==0){
                mRefreshLayout.setRefreshing(true);
                getData();
            }
            else{
                //微贷
                if(type==0&&weiDaiAdapter.getDatas().size()==0){
                    mRefreshLayout.setRefreshing(true);
                    getApplyingData();
                }
            }
        }
    }


    /**
     * 刷新
     */
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            if(type != 0){
                pageNum = 1;
                getData();
            }
            else{
                pageNum = 1;
                mRefreshLayout.setRefreshing(true);
                weiDaiAdapter.getLocalDataList();
                getApplyingData();
            }
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
            if(NetworkUtils.isNetAvailable(getActivity())){ //有网状态
                if(!RecyclerViewStateUtils.setFooterViewState(getActivity(), mRecyclerView,
                        Constant.REQUEST_COUNT, LoadingFooter.State.Loading, null)){
                    pageNum --;
                    return;
                }
                if(type!=0)
                    getData();
                else
                    getApplyingData();
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
            if(type!=0)
                getData();
            else
                getApplyingData();
        }
    };


    /**
     * 查询待受理业务
     */
    protected void getData(){

        RequestParams params = new RequestParams(APIManager.SINOSAFE_URL+"manager/business/checkTask");
        params.addQueryStringParameter("token", BaseMainActivity.loginUserBean.getToken());
        params.addQueryStringParameter("cus_mgr", BaseMainActivity.loginUserBean.getActorno());
        params.addQueryStringParameter("task_type",  taskType);
        params.addQueryStringParameter("curPage",  pageNum+"");
        XutilsBaseHttp.requestType = -1;
        XutilsBaseHttp.get(params, onResponseListener);

    }


    private OnResponseListener onResponseListener = new OnResponseListener() {
        @Override
        public void onRequestFailedCallback(String var1) {
            if(pageNum==1){
                mRefreshLayout.setRefreshing(false);
                if(yeWuAdapter.getDatas().size()==0)
                    mErrorLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
            }
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
    public void onDestroy() {
        super.onDestroy();
        if(type==0||type==1||type==2||type==3)
            getActivity().unregisterReceiver(receiver);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
