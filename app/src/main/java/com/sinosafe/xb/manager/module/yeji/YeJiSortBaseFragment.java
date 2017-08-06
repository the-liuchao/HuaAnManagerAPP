package com.sinosafe.xb.manager.module.yeji;


import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.sinosafe.xb.manager.BaseMainActivity;
import com.sinosafe.xb.manager.R;
import com.sinosafe.xb.manager.adapter.yeji.YeJiSortAdapter;
import com.sinosafe.xb.manager.api.rxjava.RxHttpResultFunc;
import com.sinosafe.xb.manager.api.rxjava.RxSchedulersHelper;
import com.sinosafe.xb.manager.api.rxjava.RxSubscriber;
import com.sinosafe.xb.manager.bean.BaseEntity;
import com.sinosafe.xb.manager.model.ClientModel;
import com.sinosafe.xb.manager.module.home.fragment.MeBaseFragment;
import com.sinosafe.xb.manager.module.yeji.bean.YeJiRanking;
import com.sinosafe.xb.manager.utils.MyUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.Unbinder;
import luo.library.base.utils.MyLog;
import luo.library.base.widget.EmptyLayout;


/**
 * 业绩排行榜片
 */
public class YeJiSortBaseFragment extends MeBaseFragment{


    @BindView(R.id.recyclerView)
    protected RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    protected SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.layoutEmpty)
    protected EmptyLayout mErrorLayout;
    Unbinder unbinder;
    //顺序是日、周、月、年
    protected int type = -1;
    //业绩类型
    protected int yejiType = -1;
    protected int pageNum = 1;
    protected String history_type;
    //业务适配器
    protected YeJiSortAdapter yeJiSortAdapter;


    /**
     * 查询历史数据
     * @param history_type
     */
    public void setQueryHistoryReportRankingMap(String history_type){

        this.history_type = history_type;
        if(mRefreshLayout!=null)
            mRefreshLayout.setRefreshing(true);
        setQueryReportRankingMap();
    }

    /**
     * 设置排行榜分类排名查询请求参数
     */
    protected void setQueryReportRankingMap(){

        Map<String,String> map = new HashMap<>();
        map.put("token", BaseMainActivity.loginUserBean.getToken());
        map.put("method", "00"+(yejiType+6));
        map.put("report_type","00"+(type+1));
        if(history_type!=null)
            map.put("history_type",history_type);
        map.put("curPage",pageNum+"");
        map.put("pageSize",10+"");
        queryReportRanking(map);
    }

    /**
     * 查询经理保费收入、业绩提成、放款笔数、放款金额的排行榜
     */
    protected void queryReportRanking(Map<String,String> map){

        ClientModel.queryReportRanking(map)
        .timeout(20, TimeUnit.SECONDS)
        .compose(RxSchedulersHelper.<BaseEntity<YeJiRanking>>io_main())
        .map(new RxHttpResultFunc<YeJiRanking>())
        .subscribe(new RxSubscriber<YeJiRanking>() {
            @Override
            public void _onNext(YeJiRanking yeJiRanking) {
                if(pageNum==1){
                    mRefreshLayout.setRefreshing(false);
                    yeJiSortAdapter.getDatas().clear();
                }
                if(yeJiRanking!=null){
                    MyUtils.TheEndOrNomal(yeJiRanking.getData(),mRecyclerView);
                    if(yeJiRanking.getSelf()!=null)
                        yeJiRanking.getData().add(0,yeJiRanking.getSelf());
                    else{
                        //yeJiRanking.getData().add(0,new YeJiRanking.RankingItem());
                    }
                    yeJiSortAdapter.update(yeJiRanking.getData());
                }
                if(yeJiSortAdapter.getDatas().size()==0){
                    mErrorLayout.setErrorType(EmptyLayout.NODATA);
                }else{
                    mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
                }
            }
            @Override
            public void _onError(String msg) {
                if(pageNum==1){
                    mRefreshLayout.setRefreshing(false);
                    if(yeJiSortAdapter.getDatas().size()==0)
                        mErrorLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
                }
                MyLog.e("请求业绩排行列表情况反馈==="+msg);
            }});
    }
}
