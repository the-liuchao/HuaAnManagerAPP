package com.sinosafe.xb.manager.module.home.fragment;

import com.sinosafe.xb.manager.BaseMainActivity;
import com.sinosafe.xb.manager.api.rxjava.RxHttpResultFunc;
import com.sinosafe.xb.manager.api.rxjava.RxSchedulersHelper;
import com.sinosafe.xb.manager.api.rxjava.RxSubscriber;
import com.sinosafe.xb.manager.bean.BaseEntity;
import com.sinosafe.xb.manager.model.ClientModel;
import com.sinosafe.xb.manager.module.yeji.bean.RankingList;
import com.sinosafe.xb.manager.module.yeji.bean.YeJiReport;

import java.util.concurrent.TimeUnit;

import luo.library.base.base.BaseFragment;
import luo.library.base.utils.MyLog;

/**
 * 类名称：   com.cnmobi.huaan.manager.fragment
 * 内容摘要： //业绩片。
 * 修改备注：
 * 创建时间： 2017/6/3 0003
 * 公司：     深圳市华移科技股份有限公司
 * 作者：     Mr 张
 */
public abstract class YeJiBaseFragment extends BaseFragment {

    //业绩整体情况
    protected abstract void requestSuccessToalReport(YeJiReport yeJiReport);

    //业绩当天情况
    protected abstract void requestSuccessTodayReport(YeJiReport yeJiReport);

    //排行榜
    protected abstract void requestSuccessRankingList(RankingList rankingList);

    //请求失败
    protected abstract void requestFail();

    /**
     * 查询业绩整体情况
     */
    protected void queryTotalReport(){
        String token = BaseMainActivity.loginUserBean.getToken();
        ClientModel.queryReportTotal(token,"ALL")
            .timeout(20, TimeUnit.SECONDS)
            .compose(RxSchedulersHelper.<BaseEntity<YeJiReport>>io_main())
            .map(new RxHttpResultFunc<YeJiReport>())
            .subscribe(new RxSubscriber<YeJiReport>() {
                @Override
                public void _onNext(YeJiReport yeJiReport) {
                    requestSuccessToalReport(yeJiReport);
                    queryTodayReport();
                }
                @Override
                public void _onError(String msg) {
                    closeSVProgressHUD();
                    requestFail();
                    MyLog.e("请求业绩整体情况反馈==="+msg);
                }});
    }

    /**
     * 查询业绩当日情况
     */
    private void queryTodayReport(){
        String token = BaseMainActivity.loginUserBean.getToken();
        ClientModel.queryReportTotal(token,"001")
            .timeout(20, TimeUnit.SECONDS)
            .compose(RxSchedulersHelper.<BaseEntity<YeJiReport>>io_main())
            .map(new RxHttpResultFunc<YeJiReport>())
            .subscribe(new RxSubscriber<YeJiReport>() {
                @Override
                public void _onNext(YeJiReport yeJiReport) {
                    closeSVProgressHUD();
                    requestFail();
                    requestSuccessTodayReport(yeJiReport);
                }
                @Override
                public void _onError(String msg) {
                    closeSVProgressHUD();
                    requestFail();
                    MyLog.e("请求业绩当天情况反馈==="+msg);
                }});
    }


    /**
     * 排行榜总体排名查询
     */
    protected void queryMgrRanking(){

        String token = BaseMainActivity.loginUserBean.getToken();
        ClientModel.queryMgrRanking(token)
            .timeout(20, TimeUnit.SECONDS)
            .compose(RxSchedulersHelper.<BaseEntity<RankingList>>io_main())
            .map(new RxHttpResultFunc<RankingList>())
            .subscribe(new RxSubscriber<RankingList>() {
                @Override
                public void _onNext(RankingList rankingList) {
                    requestSuccessRankingList(rankingList);
                }
                @Override
                public void _onError(String msg) {
                    MyLog.e("请求排行榜情况反馈==="+msg);
                }});
    }
}
