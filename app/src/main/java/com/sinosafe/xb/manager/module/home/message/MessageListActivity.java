package com.sinosafe.xb.manager.module.home.message;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.sinosafe.xb.manager.BaseMainActivity;
import com.sinosafe.xb.manager.R;
import com.sinosafe.xb.manager.adapter.base.OnItemClickListener;
import com.sinosafe.xb.manager.adapter.base.OnItemLongClickListener;
import com.sinosafe.xb.manager.adapter.home.MessageAdapter;
import com.sinosafe.xb.manager.api.rxjava.RxHttpBaseResultFunc;
import com.sinosafe.xb.manager.api.rxjava.RxHttpResultFunc;
import com.sinosafe.xb.manager.api.rxjava.RxSchedulersHelper;
import com.sinosafe.xb.manager.api.rxjava.RxSubscriber;
import com.sinosafe.xb.manager.bean.BaseEntity;
import com.sinosafe.xb.manager.bean.MessageBean;
import com.sinosafe.xb.manager.model.ClientModel;
import com.sinosafe.xb.manager.module.yewu.weidai.MicroCreditDetailActivity;
import com.sinosafe.xb.manager.module.yewu.xiaofeidai.ConsumeDetailActivity;
import com.sinosafe.xb.manager.utils.Constant;
import com.sinosafe.xb.manager.utils.MyUtils;
import com.sinosafe.xb.manager.widget.recycleview.EndlessRecyclerOnScrollListener;
import com.sinosafe.xb.manager.widget.recycleview.HeaderAndFooterRecyclerViewAdapter;
import com.sinosafe.xb.manager.widget.recycleview.LoadingFooter;
import com.sinosafe.xb.manager.widget.recycleview.RecyclerViewStateUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import luo.library.base.base.BaseFragmentActivity;
import luo.library.base.utils.IntentUtil;
import luo.library.base.utils.MyLog;
import luo.library.base.utils.NetworkUtils;
import luo.library.base.widget.EmptyLayout;
import luo.library.base.widget.WebViewActivity;
import luo.library.base.widget.dialog.DialogMessage;

import static com.sinosafe.xb.manager.APP.bundle;

/**
 * 类名称：   LoginActivity
 * 内容摘要： //消息列表。
 * 修改备注：
 * 创建时间： 2017/5/10
 * 公司：     深圳市华移科技股份有限公司
 * 作者：     Mr 张
 */
public class MessageListActivity extends BaseFragmentActivity {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.layoutEmpty)
    EmptyLayout mErrorLayout;
    private MessageAdapter messageAdapter;
    private MessageListActivity instance;
    private int pageNum = 1;
    private  int currentPosition = -1;

    //0：读取；1：删除
    private int type = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_list);

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

        setTitleText("消息");

        messageAdapter = new MessageAdapter(this);

        HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter
                = new HeaderAndFooterRecyclerViewAdapter(messageAdapter);
        mRecyclerView.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRefreshLayout.setColorSchemeResources(R.color.status_bar_bg, R.color.orange, R.color.green);
        mRefreshLayout.setOnRefreshListener(mOnRefreshListener);

        mRecyclerView.addOnScrollListener(mOnScrollListener);
        messageAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(View view, int position, Object item) {
                currentPosition = position;
                type = 0;
                MessageBean messageBean = messageAdapter.getDatas().get(position);
                if(messageBean.getIs_read()==1)
                    jump(position);
                else{
                    startProgressDialog("");
                    readOrDelMesg(messageBean.getId()+"","0");
                }
            }
        });

        messageAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public void onLongClick(View view, int position, Object item) {
                currentPosition = position;
                type = 1;
                showDeleMessDialog();
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


    private void jump(int position) {

        MessageBean messageBean = messageAdapter.getDatas().get(position);
        if (messageBean.getMess_type() == 0) {//系统消息
            if (messageBean.getMess_json() != null && !TextUtils.isEmpty(messageBean.getMess_json())){
                Bundle bundle = new Bundle();
                bundle.putString("html",messageBean.getMess_json());
                bundle.putString("title",messageBean.getMess_title());
                IntentUtil.gotoActivity(this,WebViewActivity.class,bundle);
            }
        } else {
            try {
                JSONObject jsonObject = new JSONObject(messageBean.getMess_json());
                int prd_type = jsonObject.getInt("prd_type");
                String serno = jsonObject.getString("serno");
                String prd_id = jsonObject.getString("prd_id");
                int loan_type = jsonObject.getInt("loan_type");
                if (prd_type == 0) {//消费贷 微贷
                    Bundle bundle = new Bundle();
                    bundle.putString("serno",serno);
                    bundle.putString("prdId",prd_id);
                    IntentUtil.gotoActivity(MessageListActivity.this,ConsumeDetailActivity.class,bundle);
                }else if(prd_type == 2){
                    Bundle bundle = new Bundle();
                    bundle.putString("serno",serno);
                    bundle.putString("prdId",prd_id);
                    IntentUtil.gotoActivity(MessageListActivity.this,MicroCreditDetailActivity.class,bundle);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取消息列表
     */
    private void getData(){
        Map<String, String> maps = new HashMap<>();
        maps.put("token", BaseMainActivity.loginUserBean.getToken());
        maps.put("curPage", pageNum+"");
        maps.put("pageSize", "10");
        ClientModel.getMessages(maps)
            .timeout(20, TimeUnit.SECONDS)
            .compose(RxSchedulersHelper.<BaseEntity<List<MessageBean>>>io_main())
            .map(new RxHttpResultFunc<List<MessageBean>>())
            .subscribe(new MyRxSubscriber());

    }

    class MyRxSubscriber extends  RxSubscriber<List<MessageBean>>{

            @Override
            public void _onNext(List<MessageBean> messageBeens) {
                //第一页停止刷新
                if(pageNum==1){
                    mRefreshLayout.setRefreshing(false);
                    messageAdapter.getDatas().clear();
                }
                if(messageBeens!=null){
                    MyUtils.TheEndOrNomal(messageBeens,mRecyclerView);
                    messageAdapter.update(messageBeens);
                }
                if(messageAdapter.getDatas().size()==0){
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
                if(messageAdapter.getDatas().size()==0)
                    mErrorLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
                MyLog.e("获取消息列表异常：===="+msg);
            }
    }

    /**
     * 删除消息提示
     */
    private void showDeleMessDialog(){

        new DialogMessage(this).setMess("亲，您确定要删除该条消息吗?")
                .setConfirmListener(new DialogMessage.OnConfirmListener() {
                    @Override
                    public void onConfirm() {
                        startProgressDialog("删除中...");
                        MessageBean messageBean = messageAdapter.getDatas().get(currentPosition);
                        readOrDelMesg(messageBean.getId(),"1");
                    }
                }).show();
    }

    /**
     * 删除、已读消息
     */
    private void readOrDelMesg(String msg_id,String type){
        Map<String, String> maps = new HashMap<>();
        maps.put("token", BaseMainActivity.loginUserBean.getToken());
        maps.put("msg_id", msg_id);
        maps.put("type", type);
        ClientModel.readOrDelMesg(maps)
            .timeout(20, TimeUnit.SECONDS)
            .compose(RxSchedulersHelper.<BaseEntity>io_main())
            .map(new RxHttpBaseResultFunc())
            .subscribe(new ReadOrDelMesgRxSubscriber());

    }


    class ReadOrDelMesgRxSubscriber extends  RxSubscriber<BaseEntity>{

        @Override
        public void _onNext(BaseEntity baseEntity) {
            //读取
            if(type==0){
                messageAdapter.getDatas().get(currentPosition).setIs_read(1);
                messageAdapter.notifyDataSetChanged();
                stopProgressDialog();
                jump(currentPosition);
            }
            //删除
            else{
                stopProgressDialog();
                messageAdapter.getDatas().remove(currentPosition);
                messageAdapter.notifyDataSetChanged();
            }
        }
        @Override
        public void _onError(String msg) {
            MyLog.e("删除、已读消息异常：===="+msg);
            stopProgressDialog();
            if(type==0){
                jump(currentPosition);
            }
            //删除
            else{
                showErrorWithStatus("删除失败.");
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        instance = null;
    }
}
