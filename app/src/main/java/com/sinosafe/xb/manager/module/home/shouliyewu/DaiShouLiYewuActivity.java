package com.sinosafe.xb.manager.module.home.shouliyewu;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sinosafe.xb.manager.BaseMainActivity;
import com.sinosafe.xb.manager.R;
import com.sinosafe.xb.manager.adapter.base.OnItemClickListener;
import com.sinosafe.xb.manager.adapter.base.OnItemLongClickListener;
import com.sinosafe.xb.manager.adapter.home.DaiShouliYewuAdapter;
import com.sinosafe.xb.manager.api.APIManager;
import com.sinosafe.xb.manager.api.rxjava.RxHttpBaseResultFunc;
import com.sinosafe.xb.manager.api.rxjava.RxSchedulersHelper;
import com.sinosafe.xb.manager.api.rxjava.RxSubscriber;
import com.sinosafe.xb.manager.api.xutilshttp.OnResponseListener;
import com.sinosafe.xb.manager.api.xutilshttp.XutilsBaseHttp;
import com.sinosafe.xb.manager.bean.BaseEntity;
import com.sinosafe.xb.manager.bean.YeWuBean;
import com.sinosafe.xb.manager.model.ClientModel;
import com.sinosafe.xb.manager.utils.Constant;
import com.sinosafe.xb.manager.utils.MyUtils;
import com.sinosafe.xb.manager.utils.T;
import com.sinosafe.xb.manager.widget.recycleview.EndlessRecyclerOnScrollListener;
import com.sinosafe.xb.manager.widget.recycleview.HeaderAndFooterRecyclerViewAdapter;
import com.sinosafe.xb.manager.widget.recycleview.LoadingFooter;
import com.sinosafe.xb.manager.widget.recycleview.RecyclerViewStateUtils;

import org.xutils.http.RequestParams;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import luo.library.base.base.BaseFragmentActivity;
import luo.library.base.utils.GsonUtil;
import luo.library.base.utils.IntentUtil;
import luo.library.base.utils.NetworkUtils;
import luo.library.base.widget.EmptyLayout;
import luo.library.base.widget.dialog.DialogMessage;

/**
 * 待受理业务列表
 */
public class DaiShouLiYewuActivity extends BaseFragmentActivity {

    private static final int REQUEST_CODE = 1003;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.layoutEmpty)
    EmptyLayout mErrorLayout;

    private DaiShouliYewuAdapter daiShouliYewuAdapter;
    private DaiShouLiYewuActivity instance;
    private int pageNum = 1;
    private int currentPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daishouli_yewu_list);
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

        setTitleText("待受理业务");
        daiShouliYewuAdapter = new DaiShouliYewuAdapter(this);

        HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter
                = new HeaderAndFooterRecyclerViewAdapter(daiShouliYewuAdapter);
        mRecyclerView.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRefreshLayout.setColorSchemeResources(R.color.status_bar_bg, R.color.orange, R.color.green);
        mRefreshLayout.setOnRefreshListener(mOnRefreshListener);

        mRecyclerView.addOnScrollListener(mOnScrollListener);
        daiShouliYewuAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(View view, int position, Object item) {

                currentPosition = position;
                Bundle bundle = new Bundle();
                bundle.putSerializable("yeWuBean",daiShouliYewuAdapter.getDatas().get(position));
                IntentUtil.gotoActivityForResult(instance,CustomerDetailActivity.class,bundle,REQUEST_CODE);
            }
        });

        daiShouliYewuAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public void onLongClick(View view, int position, Object item) {
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(daiShouliYewuAdapter.getDatas().get(position).getSERNO());
                T.showShortBottom(daiShouliYewuAdapter.getDatas().get(position).getSERNO());
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==REQUEST_CODE&&resultCode==RESULT_OK){

            daiShouliYewuAdapter.getDatas().remove(currentPosition);
            daiShouliYewuAdapter.notifyDataSetChanged();
            if(daiShouliYewuAdapter.getDatas().size()==0){
                mErrorLayout.setErrorType(EmptyLayout.NODATA);
            }else{
                mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
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

        RequestParams params = new RequestParams(APIManager.SINOSAFE_URL+"manager/business/checkTask");
        params.addQueryStringParameter("token", BaseMainActivity.loginUserBean.getToken());
        params.addQueryStringParameter("cus_mgr", BaseMainActivity.loginUserBean.getActorno());
        params.addQueryStringParameter("task_type",  "10");
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
            if(daiShouliYewuAdapter.getDatas().size()==0)
                mErrorLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
        }

        @Override
        public void onCommonRequestCallback(String result) {
            if(pageNum==1){
                mRefreshLayout.setRefreshing(false);
                daiShouliYewuAdapter.getDatas().clear();
            }
            BaseEntity entity = GsonUtil.GsonToBean(result,BaseEntity.class);
            List<YeWuBean> yeWuBeens = GsonUtil.GsonToList(entity.getResult().toString(),YeWuBean.class);
            if(yeWuBeens!=null){
                MyUtils.TheEndOrNomal(yeWuBeens,mRecyclerView);
                daiShouliYewuAdapter.update(yeWuBeens);
            }

            if(daiShouliYewuAdapter.getDatas().size()==0){
                mErrorLayout.setErrorType(EmptyLayout.NODATA);
            }else{
                mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
            }
        }
    };





    /**
     * 受理待受理业务
     * @param map
     */
    public void bussinessAccept(Map<String,String> map,int currentPosition){
        this.currentPosition = currentPosition;
        showWithStatus("受理中...");
        ClientModel.bussinessAccept(map)
            .timeout(20, TimeUnit.SECONDS)
            .compose(RxSchedulersHelper.<BaseEntity>io_main())
            .map(new RxHttpBaseResultFunc())
            .subscribe(new RxSubscriber<BaseEntity>() {
                @Override
                public void _onNext(BaseEntity entity) {
                    closeSVProgressHUD();
                    if(entity.getCode()==1){
                        bussinessAcceptSuccess();
                        sendBroadcast(new Intent(Constant.REFRESH_HOME_PAGE));
                    }else{
                        showErrorWithStatus(entity.getMsg());
                    }
                }
                @Override
                public void _onError(String msg) {
                    closeSVProgressHUD();
                    showErrorWithStatus(msg);
                }});
    }

    /**
     * 受理成功提示
     */
    private void bussinessAcceptSuccess(){

        DialogMessage messageDialog = new DialogMessage(this,
                new DialogMessage.OnConfirmListener() {
            @Override
            public void onConfirm() {
                daiShouliYewuAdapter.getDatas().remove(currentPosition);
                daiShouliYewuAdapter.notifyDataSetChanged();
            }
        }, 1);

        messageDialog.setMess("您已成功受理，请及时联系客户！");
        messageDialog.setConfirmTips("知道了");
        messageDialog.setCanceledOnTouchOutside(false);
        messageDialog.setCancelable(false);
        messageDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        instance = null;
    }

}
