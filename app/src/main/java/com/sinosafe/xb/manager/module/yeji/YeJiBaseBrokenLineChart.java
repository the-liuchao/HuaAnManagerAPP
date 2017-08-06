package com.sinosafe.xb.manager.module.yeji;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.RadioButton;

import com.sinosafe.xb.manager.BaseMainActivity;
import com.sinosafe.xb.manager.R;
import com.sinosafe.xb.manager.adapter.yeji.YeJiTrendAdapter;
import com.sinosafe.xb.manager.api.rxjava.RxHttpBaseResultFunc;
import com.sinosafe.xb.manager.api.rxjava.RxSchedulersHelper;
import com.sinosafe.xb.manager.api.rxjava.RxSubscriber;
import com.sinosafe.xb.manager.bean.BaseEntity;
import com.sinosafe.xb.manager.bean.YeJiTrendBean;
import com.sinosafe.xb.manager.model.ClientModel;
import com.sinosafe.xb.manager.module.yeji.bean.YeJiDay;
import com.sinosafe.xb.manager.module.yeji.bean.YeJiMonth;
import com.sinosafe.xb.manager.module.yeji.bean.YeJiWeek;
import com.sinosafe.xb.manager.module.yeji.bean.YeJiYear;
import com.sinosafe.xb.manager.module.yeji.bean.YejiTendency;
import com.sinosafe.xb.manager.utils.T;
import com.sinosafe.xb.manager.widget.BrokenLineChartView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import luo.library.base.base.BaseFragmentActivity;
import luo.library.base.utils.GsonUtil;
import luo.library.base.utils.MyLog;
import luo.library.base.widget.EmptyLayout;

/**
 * 类名称：   com.sinosafe.xb.manager.module.yeji
 * 内容摘要： //业绩趋势图抽象类。
 * 修改备注：
 * 创建时间： 2017/7/14 0014
 * 公司：     深圳市华移科技股份有限公司
 * 作者：     Mr 张
 */
public abstract class YeJiBaseBrokenLineChart extends BaseFragmentActivity {

    @BindView(R.id.dayRadio)
    protected RadioButton mDayRadio;
    @BindView(R.id.weekRadio)
    protected RadioButton mWeekRadio;
    @BindView(R.id.monthRadio)
    protected RadioButton mMonthRadio;
    @BindView(R.id.yearRadio)
    protected RadioButton mYearRadio;
    @BindView(R.id.brokenLineChart)
    protected BrokenLineChartView mBrokenLineChart;
    @BindView(R.id.recyclerView)
    protected RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    protected SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.layoutEmpty)
    protected EmptyLayout mErrorLayout;
    //类型
    protected int type = -1;
    protected int timeType = 0;
    protected YeJiTrendAdapter yeJiTrendAdapter;

    //列表展示数据封装
    protected List<YeJiTrendBean> jiTrendBeanList = new ArrayList<>();

    //放款笔数003、保费收入004
    protected String method;
    protected int pageNum = 1;
    //左右滑动 TRUE：左；false：右
    protected boolean leftAndRight = false;

    protected void setQueryTendencyMap(){
        startProgressDialog2("加载中...");
        Map<String,String> map = new HashMap<>();
        map.put("token", BaseMainActivity.loginUserBean.getToken());
        map.put("method",method);
        map.put("report_type","00"+(timeType+1));
        map.put("page_count",pageNum+"");
        queryTendency(map);
    }

    /**
     * 查询经理放款笔数、保费收入数据，用于趋势图展示
     */
    private void queryTendency(Map<String,String> map ){
        ClientModel.queryTendency(map)
                .timeout(20, TimeUnit.SECONDS)
                .compose(RxSchedulersHelper.<BaseEntity>io_main())
                .map(new RxHttpBaseResultFunc())
                .subscribe(new MyRxSubscriber());
    }

    class  MyRxSubscriber extends RxSubscriber<BaseEntity>{

        @Override
        public void _onNext(BaseEntity baseEntity) {

            if(mRefreshLayout!=null)
                mRefreshLayout.setRefreshing(false);
            if(timeType==0){
                if(pageNum==1)
                    setQueryTendencyAllMap();
                else{
                    stopProgressDialog();
                }
            }else{
                stopProgressDialog();
            }
            if(baseEntity.getCode()==1){
                yeJiDataHandle(baseEntity.getResult().toString());
            }
            else{
                stopProgressDialog();
                T.showShortBottom(baseEntity.getMsg());
            }
            if(timeType!=0){
                if(yeJiTrendAdapter.getDatas().size()==0){
                    mErrorLayout.setErrorType(EmptyLayout.NODATA);
                }else{
                    mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
                }
            }
        }

        @Override
        public void _onError(String msg) {
            stopProgressDialog();
            if(mRefreshLayout!=null)
                mRefreshLayout.setRefreshing(false);
            if(leftAndRight){
                pageNum ++;
            }else{
                pageNum--;
            }
            MyLog.e("查询业绩失败原因===="+msg);
            if(pageNum==1){
                if(timeType!=0){
                    if(yeJiTrendAdapter.getDatas().size()==0)
                        mErrorLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
                }
            }
        }
    }

    /**
     * 业绩数据分类处理、封装
     * @param result
     */
    private void yeJiDataHandle(String result){

        jiTrendBeanList.clear();
        //业绩趋势图
        YejiTendency yejiTendency = new YejiTendency();
        List<YejiTendency.TendencyItem> tendencyItems = new ArrayList<>();
        //放款笔数

        //日
        if(timeType==0){
            YeJiDay yeJiDay = GsonUtil.GsonToBean(result,YeJiDay.class);
            if(yeJiDay!=null){

                yejiTendency.setAvg(yeJiDay.getAvg());
                yejiTendency.setMax(yeJiDay.getMax());

                List<YeJiDay.DayArray> array = yeJiDay.getArray();
                for(int i=0;i<array.size();i++){
                    YeJiTrendBean yeJiTrendBean = new YeJiTrendBean();
                    //每一年的集合
                    YeJiDay.DayArray dayArray = array.get(i);

                    //年
                    String yearStr = dayArray.getYear();
                    if(yearStr.endsWith(".0"))
                        yearStr = yearStr.replace(".0"," ").trim();
                    yeJiTrendBean.setDate(yearStr+"年");
                    //时间节点
                    yeJiTrendBean.setFlag(0);
                    jiTrendBeanList.add(yeJiTrendBean);

                    List<YeJiDay.DayItem> dayItems = dayArray.getData();
                    for(int j=0;j<dayItems.size();j++){
                        YeJiDay.DayItem dayItem = dayItems.get(j);
                        yeJiTrendBean = new YeJiTrendBean();
                        yeJiTrendBean.setDate(dayItem.getTIME());
                        yeJiTrendBean.setResult(dayItem.getRESULT());

                        jiTrendBeanList.add(yeJiTrendBean);

                        //封装趋势图数据
                        YejiTendency.TendencyItem tendencyItem = new YejiTendency.TendencyItem();
                        String timeArr[] = dayItem.getTIME().split("-");
                        tendencyItem.setDate(timeArr[1]+"/"+timeArr[2]);
                        tendencyItem.setData(dayItem.getRESULT());
                        tendencyItems.add(tendencyItem);
                    }
                    yejiTendency.setTendencyItem(tendencyItems);
                }
            }
        }

        //周
        else if(timeType==1){

            YeJiWeek yeJiWeek = GsonUtil.GsonToBean(result,YeJiWeek.class);
            if(yeJiWeek!=null){

                yejiTendency.setAvg(yeJiWeek.getAvg());
                yejiTendency.setMax(yeJiWeek.getMax());

                List<YeJiWeek.WeekItem> array = yeJiWeek.getData();
                if(array!=null&&array.size()>0){
                    for(int i=0;i<array.size();i++){
                        YeJiWeek.WeekItem weekItem = array.get(i);
                        YeJiTrendBean yeJiTrendBean = new YeJiTrendBean();

                        String startTimeArr[] = weekItem.getSTART_DAY().split("-");
                        String startTime = startTimeArr[0]+"/"+startTimeArr[1]+"/"+startTimeArr[2];
                        String endTimeArr[] = weekItem.getEND_DAY().split("-");
                        String endTime = endTimeArr[0]+"/"+endTimeArr[1]+"/"+endTimeArr[2];

                        String weekStr = weekItem.getWEEK();
                        if(weekStr.endsWith(".0"))
                            weekStr = weekStr.replace(".0"," ").trim();
                        yeJiTrendBean.setDate("第"+weekStr+"周("+startTime+"-"+endTime+")");
                        yeJiTrendBean.setResult(weekItem.getRESULT());

                        jiTrendBeanList.add(yeJiTrendBean);

                        //封装趋势图数据
                        YejiTendency.TendencyItem tendencyItem = new YejiTendency.TendencyItem();

                        tendencyItem.setDate("第"+weekStr+"周");
                        tendencyItem.setData(weekItem.getRESULT());
                        tendencyItems.add(tendencyItem);
                    }
                }
                yejiTendency.setTendencyItem(tendencyItems);
            }
        }

        //月
        else if(timeType==2){

            YeJiMonth yeJiMonth = GsonUtil.GsonToBean(result,YeJiMonth.class);
            if(yeJiMonth!=null){

                yejiTendency.setAvg(yeJiMonth.getAvg());
                yejiTendency.setMax(yeJiMonth.getMax());

                List<YeJiMonth.MonthArray> array = yeJiMonth.getArray();
                if(array!=null&&array.size()>0) {
                    for (int i = 0; i < array.size(); i++) {
                        YeJiTrendBean yeJiTrendBean = new YeJiTrendBean();
                        //每一年的集合
                        YeJiMonth.MonthArray monthArray = array.get(i);

                        //年
                       /* String yearStr = monthArray.getYear();
                        if (yearStr.endsWith(".0"))
                            yearStr = yearStr.replace(".0", " ").trim();
                        yeJiTrendBean.setDate(yearStr + "年");
                        yeJiTrendBean.setFlag(0);
                        jiTrendBeanList.add(yeJiTrendBean);*/

                        List<YeJiMonth.MonthItem> monthItems = monthArray.getData();
                        for (int j = 0; j < monthItems.size(); j++) {
                            YeJiMonth.MonthItem monthItem = monthItems.get(j);
                            String timeArr[] = monthItem.getTIME().split("-");
                            yeJiTrendBean = new YeJiTrendBean();
                            yeJiTrendBean.setDate(timeArr[0]+"年"+timeArr[1] + "月");
                            yeJiTrendBean.setResult(monthItem.getRESULT());

                            jiTrendBeanList.add(yeJiTrendBean);

                            //封装趋势图数据
                            YejiTendency.TendencyItem tendencyItem = new YejiTendency.TendencyItem();

                            tendencyItem.setDate(timeArr[1] + "");
                            tendencyItem.setData(monthItem.getRESULT());
                            tendencyItems.add(tendencyItem);
                        }
                        yejiTendency.setTendencyItem(tendencyItems);
                    }
                }
            }
        }

        //年
        else if(timeType==3){

            YeJiYear yeJiYear = GsonUtil.GsonToBean(result,YeJiYear.class);
            if(yeJiYear!=null){

                yejiTendency.setAvg(yeJiYear.getAvg());
                yejiTendency.setMax(yeJiYear.getMax());

                List<YeJiYear.YearItem> array = yeJiYear.getArray();
                if(array!=null&&array.size()>0){
                    for(int i=0;i<array.size();i++){
                        YeJiYear.YearItem yearItem = array.get(i);
                        YeJiTrendBean yeJiTrendBean = new YeJiTrendBean();

                        yeJiTrendBean.setDate(yearItem.getTIME()+"年");
                        yeJiTrendBean.setResult(yearItem.getRESULT());
                        jiTrendBeanList.add(yeJiTrendBean);

                        //封装趋势图数据
                        YejiTendency.TendencyItem tendencyItem = new YejiTendency.TendencyItem();
                        tendencyItem.setDate(yearItem.getTIME());
                        tendencyItem.setData(yearItem.getRESULT());
                        tendencyItems.add(tendencyItem);
                    }
                }
                yejiTendency.setTendencyItem(tendencyItems);
            }
        }
        //刷新数据
        mBrokenLineChart.drawBrokenLine(type,timeType,yejiTendency);
        if(timeType!=0){
            yeJiTrendAdapter.getDatas().clear();
            yeJiTrendAdapter.update(jiTrendBeanList);
        }
    }


    /**
     * 设置查询全部的数据请求参数
     */
    protected void setQueryTendencyAllMap(){

        Map<String,String> map = new HashMap<>();
        map.put("token", BaseMainActivity.loginUserBean.getToken());
        map.put("method",method);
        map.put("report_type","00"+(timeType+1));
        queryTendencyAll(map);
    }

    /**
     * 查询经理放款笔数、保费收入全部数据
     */
    private void queryTendencyAll(Map<String,String> map ){
        ClientModel.queryTendencyAll(map)
                .timeout(20, TimeUnit.SECONDS)
                .compose(RxSchedulersHelper.<BaseEntity>io_main())
                .map(new RxHttpBaseResultFunc())
                .subscribe(new MyRxSubscriber2());
    }

    class  MyRxSubscriber2 extends RxSubscriber<BaseEntity>{

        @Override
        public void _onNext(BaseEntity baseEntity) {
            if(baseEntity.getCode()==1){
                yeJiDataHandle2(baseEntity.getResult().toString());
            }
            else{
                T.showShortBottom(baseEntity.getMsg());
            }
            if(yeJiTrendAdapter.getDatas().size()==0){
                mErrorLayout.setErrorType(EmptyLayout.NODATA);
            }else{
                mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
            }
            stopProgressDialog();
            if(mRefreshLayout!=null)
                mRefreshLayout.setRefreshing(false);
        }
        @Override
        public void _onError(String msg) {
            stopProgressDialog();
            if(mRefreshLayout!=null)
                mRefreshLayout.setRefreshing(false);
            MyLog.e("查询业绩失败原因===="+msg);
            if(yeJiTrendAdapter.getDatas().size()==0)
                mErrorLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
        }
    }

    /**
     * 业绩数据分类处理、封装
     * @param result
     */
    private void yeJiDataHandle2(String result){

        jiTrendBeanList.clear();
        //日
        if(timeType==0){
            List<YeJiDay.DayItem> dayItems = GsonUtil.GsonToList(result,YeJiDay.DayItem.class);
            if(dayItems!=null){
                for(int j=0;j<dayItems.size();j++){
                    YeJiDay.DayItem dayItem = dayItems.get(j);
                    YeJiTrendBean yeJiTrendBean = new YeJiTrendBean();

                    String startTimeArr[] = dayItem.getTIME().split("-");
                    String startTime = startTimeArr[0]+"/"+startTimeArr[1]+"/"+startTimeArr[2];
                    yeJiTrendBean.setDate(startTime);
                    yeJiTrendBean.setResult(dayItem.getRESULT());

                    jiTrendBeanList.add(yeJiTrendBean);
                }
            }
        }

       /* //周
        else if(timeType==1){

            List<YeJiWeek.WeekItem> weekItems = GsonUtil.GsonToList(result,YeJiWeek.WeekItem.class);
            if(weekItems!=null){
                for(int i=0;i<weekItems.size();i++){

                    YeJiWeek.WeekItem weekItem = weekItems.get(i);
                    YeJiTrendBean yeJiTrendBean = new YeJiTrendBean();

                    String startTimeArr[] = weekItem.getSTART_DAY().split("-");
                    String startTime = startTimeArr[0]+"/"+startTimeArr[1]+"/"+startTimeArr[2];
                    String endTimeArr[] = weekItem.getEND_DAY().split("-");
                    String endTime = endTimeArr[0]+"/"+endTimeArr[1]+"/"+endTimeArr[2];

                    String weekStr = weekItem.getWEEK();
                    if(weekStr.endsWith(".0"))
                        weekStr = weekStr.replace(".0"," ").trim();
                    yeJiTrendBean.setDate("第"+weekStr+"周("+startTime+"-"+endTime+")");
                    yeJiTrendBean.setResult(weekItem.getRESULT());

                    jiTrendBeanList.add(yeJiTrendBean);
                }
            }
        }

        //月
        else if(timeType==2){

            List<YeJiMonth.MonthItem> monthItems = GsonUtil.GsonToList(result,YeJiMonth.MonthItem.class);
            if(monthItems!=null){
                for (int j = 0; j < monthItems.size(); j++) {
                    YeJiMonth.MonthItem monthItem = monthItems.get(j);
                    String timeArr[] = monthItem.getTIME().split("-");
                    YeJiTrendBean yeJiTrendBean = new YeJiTrendBean();
                    yeJiTrendBean.setDate(timeArr[0]+"年-"+timeArr[1] + "月");
                    yeJiTrendBean.setResult(monthItem.getRESULT());

                    jiTrendBeanList.add(yeJiTrendBean);
                }
            }
        }

        //年
        else if(timeType==3){

            List<YeJiYear.YearItem> yearItems = GsonUtil.GsonToList(result,YeJiYear.YearItem.class);
            if(yearItems!=null){
                for(int i=0;i<yearItems.size();i++){
                    YeJiYear.YearItem yearItem = yearItems.get(i);
                    YeJiTrendBean yeJiTrendBean = new YeJiTrendBean();

                    yeJiTrendBean.setDate(yearItem.getTIME()+"年");
                    yeJiTrendBean.setResult(yearItem.getRESULT());
                    jiTrendBeanList.add(yeJiTrendBean);
                }
            }
        }*/
        //刷新数据
        yeJiTrendAdapter.getDatas().clear();
        yeJiTrendAdapter.update(jiTrendBeanList);
    }

}
