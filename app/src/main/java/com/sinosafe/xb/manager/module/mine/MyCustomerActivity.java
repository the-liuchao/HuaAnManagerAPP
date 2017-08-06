package com.sinosafe.xb.manager.module.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sinosafe.xb.manager.BaseMainActivity;
import com.sinosafe.xb.manager.R;
import com.sinosafe.xb.manager.adapter.base.OnItemClickListener;
import com.sinosafe.xb.manager.adapter.mine.MyCustomerAdapter;
import com.sinosafe.xb.manager.api.rxjava.RxHttpBaseResultFunc;
import com.sinosafe.xb.manager.api.rxjava.RxHttpResultFunc;
import com.sinosafe.xb.manager.api.rxjava.RxSchedulersHelper;
import com.sinosafe.xb.manager.api.rxjava.RxSubscriber;
import com.sinosafe.xb.manager.bean.BaseEntity;
import com.sinosafe.xb.manager.bean.MyCustomerBean;
import com.sinosafe.xb.manager.model.ClientModel;
import com.sinosafe.xb.manager.utils.Constant;
import com.sinosafe.xb.manager.utils.MyUtils;
import com.sinosafe.xb.manager.utils.T;
import com.sinosafe.xb.manager.widget.recycleview.EndlessRecyclerOnScrollListener;
import com.sinosafe.xb.manager.widget.recycleview.HeaderAndFooterRecyclerViewAdapter;
import com.sinosafe.xb.manager.widget.recycleview.LoadingFooter;
import com.sinosafe.xb.manager.widget.recycleview.RecyclerViewStateUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import luo.library.base.base.BaseFragmentActivity;
import luo.library.base.utils.IntentUtil;
import luo.library.base.utils.NetworkUtils;
import luo.library.base.widget.EmptyLayout;

/**
 * 我的客户列表
 */
public class MyCustomerActivity extends BaseFragmentActivity {


    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.layoutEmpty)
    EmptyLayout mErrorLayout;

    private MyCustomerAdapter myCustomerAdapter;
    private MyCustomerActivity instance;
    private int pageNum = 1;
    private int currentPosition = -1;
    private final static int ADD_CUSTOMER_REQUEST = 1000;
    private final static int UPDATE_CUSTOMER_REQUEST = 1001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycustomer_list);
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

        setTitleText("我的客户");
        setRightImg(R.mipmap.icon_tianjiax, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("type",0);
                IntentUtil.gotoActivityForResult(MyCustomerActivity.this,
                        AddCustomerActivity.class,bundle,ADD_CUSTOMER_REQUEST);
            }
        });

        myCustomerAdapter = new MyCustomerAdapter(this);

        HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter
                = new HeaderAndFooterRecyclerViewAdapter(myCustomerAdapter);
        mRecyclerView.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRefreshLayout.setColorSchemeResources(R.color.status_bar_bg, R.color.orange, R.color.green);
        mRefreshLayout.setOnRefreshListener(mOnRefreshListener);

        mRecyclerView.addOnScrollListener(mOnScrollListener);
        myCustomerAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(View view, int position, Object item) {

                currentPosition = position;
                MyCustomerBean myCustomerBean = myCustomerAdapter.getDatas().get(position);
                Bundle bundle = new Bundle();
                bundle.putInt("type",1);
                bundle.putSerializable("myCustomerBean",myCustomerBean);
                IntentUtil.gotoActivityForResult(MyCustomerActivity.this,
                        AddCustomerActivity.class,bundle,UPDATE_CUSTOMER_REQUEST);
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

        //添加成功
        if(requestCode==ADD_CUSTOMER_REQUEST&&resultCode==RESULT_OK){
            pageNum = 1;
            mRefreshLayout.setRefreshing(true);
            getData();
        }
        //修改成功
        else if(requestCode==UPDATE_CUSTOMER_REQUEST&&resultCode==RESULT_OK){

            MyCustomerBean myCustomerBean = (MyCustomerBean)data.getSerializableExtra("myCustomerBean");
            MyCustomerBean myCustomerBean2 = myCustomerAdapter.getDatas().get(currentPosition);
            //Collections.replaceAll(myCustomerAdapter.getDatas(), myCustomerBean2, myCustomerBean);
            myCustomerAdapter.getDatas().set(currentPosition,myCustomerBean);
            myCustomerAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 测试数据
     */
    private void getData(){

        String token = BaseMainActivity.loginUserBean.getToken();
        ClientModel.getMyCusListById(token,pageNum+"")
                .timeout(20, TimeUnit.SECONDS)
                .compose(RxSchedulersHelper.<BaseEntity<List<MyCustomerBean>>>io_main())
                .map(new RxHttpResultFunc<List<MyCustomerBean>>())
                .subscribe(new RxSubscriber<List<MyCustomerBean>>() {
                    @Override
                    public void _onNext(List<MyCustomerBean> t) {
                        //第一页停止刷新
                        if(pageNum==1){
                            mRefreshLayout.setRefreshing(false);
                            myCustomerAdapter.getDatas().clear();
                        }
                        if(t!=null){
                            MyUtils.TheEndOrNomal(t,mRecyclerView);
                            myCustomerAdapter.update(t);
                        }
                        if(myCustomerAdapter.getDatas().size()==0){
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
                        if(myCustomerAdapter.getDatas().size()==0)
                            mErrorLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
                    }});

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
     * 添加或者修改我的客户
     * @param filter
     */
    public void addAndEditCustomerById(Map<String,String> filter){

        showWithStatus("激活中...");
        ClientModel.addAndEditCustomerById(filter)
                .timeout(20, TimeUnit.SECONDS)
                .compose(RxSchedulersHelper.<BaseEntity<BaseEntity>>io_main())
                .map(new RxHttpBaseResultFunc())
                .subscribe(new RxSubscriber<BaseEntity>() {
                    @Override
                    public void _onNext(BaseEntity t) {
                        closeSVProgressHUD();
                        T.showShortBottom(t.getMsg());
                        myCustomerAdapter.synCustomerBeans();
                    }
                    @Override
                    public void _onError(String msg) {
                        closeSVProgressHUD();
                        showErrorWithStatus(msg);
                    }});
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        instance = null;
    }

}
