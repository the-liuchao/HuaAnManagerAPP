package com.sinosafe.xb.manager.module.yewu;


import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.sinosafe.xb.manager.BaseMainActivity;
import com.sinosafe.xb.manager.R;
import com.sinosafe.xb.manager.adapter.yewu.WeiDaiAdapter;
import com.sinosafe.xb.manager.adapter.yewu.YeWuAdapter;
import com.sinosafe.xb.manager.api.rxjava.RxHttpResultFunc;
import com.sinosafe.xb.manager.api.rxjava.RxSchedulersHelper;
import com.sinosafe.xb.manager.api.rxjava.RxSubscriber;
import com.sinosafe.xb.manager.bean.BaseEntity;
import com.sinosafe.xb.manager.model.ClientModel;
import com.sinosafe.xb.manager.module.home.fragment.MeBaseFragment;
import com.sinosafe.xb.manager.module.home.weidai.bean.ApplyingBean;
import com.sinosafe.xb.manager.utils.MyUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.Unbinder;
import luo.library.base.widget.EmptyLayout;


/**
 * 业务内容片
 */
public class ContentBaseFragment extends MeBaseFragment {

    @BindView(R.id.recyclerView)
    protected RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    protected SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.layoutEmpty)
    protected EmptyLayout mErrorLayout;
    protected Unbinder unbinder;
    protected int type = -1;
    protected int pageNum = 1;
    protected String taskType = "";
    protected boolean isViewInitiated;

    //业务适配器
    protected YeWuAdapter yeWuAdapter;

    //微贷适配
    protected WeiDaiAdapter weiDaiAdapter;

    /**
     * 查看申请中的数据列表
     */
    protected void getApplyingData(){

        String token = BaseMainActivity.loginUserBean.getToken();
        ClientModel.queryApplyingList(token,pageNum+"")
            .timeout(20, TimeUnit.SECONDS)
            .compose(RxSchedulersHelper.<BaseEntity<List<ApplyingBean>>>io_main())
            .map(new RxHttpResultFunc<List<ApplyingBean>>())
            .subscribe(new RxSubscriber<List<ApplyingBean>>() {
                @Override
                public void _onNext(List<ApplyingBean> t) {
                    //第一页停止刷新
                    if(pageNum==1){
                        mRefreshLayout.setRefreshing(false);
                        weiDaiAdapter.getDatas().clear();
                    }
                    if(t!=null){
                        MyUtils.TheEndOrNomal(t,mRecyclerView);
                        weiDaiAdapter.update(t);
                    }
                    if(weiDaiAdapter.getDatas().size()==0){
                        mErrorLayout.setErrorType(EmptyLayout.NODATA);
                    }else{
                        mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
                    }
                }
                @Override
                public void _onError(String msg) {
                    if(pageNum==1){
                        mRefreshLayout.setRefreshing(false);
                    }
                    if(weiDaiAdapter.getDatas().size()==0)
                        mErrorLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
                }});

    }
}
