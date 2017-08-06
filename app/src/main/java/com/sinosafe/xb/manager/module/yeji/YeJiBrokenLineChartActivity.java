package com.sinosafe.xb.manager.module.yeji;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.sinosafe.xb.manager.R;
import com.sinosafe.xb.manager.adapter.yeji.YeJiTrendAdapter;
import com.sinosafe.xb.manager.utils.Constant;
import com.sinosafe.xb.manager.utils.T;
import com.sinosafe.xb.manager.widget.recycleview.EndlessRecyclerOnScrollListener;
import com.sinosafe.xb.manager.widget.recycleview.HeaderAndFooterRecyclerViewAdapter;
import com.sinosafe.xb.manager.widget.recycleview.LoadingFooter;
import com.sinosafe.xb.manager.widget.recycleview.RecyclerViewStateUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;
import luo.library.base.utils.DensityUtil;
import luo.library.base.utils.MyLog;
import luo.library.base.utils.NetworkUtils;
import luo.library.base.widget.EmptyLayout;

/**
 * 业绩折线图
 */
public class YeJiBrokenLineChartActivity extends YeJiBaseBrokenLineChart implements GestureDetector.OnGestureListener{


    private YeJiBrokenLineChartActivity instance;
    private GestureDetector gestureDetector;
    private int verticalMinDistance = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yeji_brokenline_chart);

        instance = this;
        ButterKnife.bind(this);
        initView();

        //mRefreshLayout.setRefreshing(true);
        setQueryTendencyMap();
    }

    private void initView() {

        type = getIntent().getIntExtra("type", 0);
        if (type == 0) {
            setTitleText("放款笔数趋势图");
            method = "003";
        }
        else if (type == 1) {
            setTitleText("保费收入趋势图");
            method = "004";
        } else if (type == 2) {
            setTitleText("逾期笔数趋势图");
        } else if (type == 3) {
            setTitleText("逾期金额趋势图");
        }

        mBrokenLineChart.setRightDownx(DensityUtil.widthPixels(this));
        //mBrokenLineChart.drawBrokenLine(type,timeType);

        yeJiTrendAdapter = new YeJiTrendAdapter(this,type);
        HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter
                = new HeaderAndFooterRecyclerViewAdapter(yeJiTrendAdapter);
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
                setQueryTendencyMap();
            }
        });

        gestureDetector = new GestureDetector(this,this);
        mBrokenLineChart.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });
    }

    @OnClick({R.id.dayRadio, R.id.weekRadio, R.id.monthRadio, R.id.yearRadio})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.dayRadio:
                timeType = 0;
                break;
            case R.id.weekRadio:
                timeType = 1;
                break;
            case R.id.monthRadio:
                timeType = 2;
                break;
            case R.id.yearRadio:
                timeType = 3;
                break;
        }

        pageNum = 1;
        //mRefreshLayout.setRefreshing(true);
        setQueryTendencyMap();
    }



    /**
     * 刷新
     */
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            pageNum = 1;
            setQueryTendencyMap();
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
                setQueryTendencyMap();

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
            setQueryTendencyMap();
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        instance = null;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        float minMove = 120;         //最小滑动距离
        float minVelocity = 0;      //最小滑动速度
        float beginX = e1.getX();
        float endX = e2.getX();
        float beginY = e1.getY();
        float endY = e2.getY();

        if(beginX-endX>minMove&&Math.abs(velocityX)>minVelocity){   //左滑

            MyLog.e("向左手势");
            if(pageNum ==1){
                T.showShortBottom("首页");
                return true;
            }
            leftAndRight = true;
            pageNum --;
            setQueryTendencyMap();

        }else if(endX-beginX>minMove&&Math.abs(velocityX)>minVelocity){   //右滑
            leftAndRight = false;
            MyLog.e("向右手势");
            pageNum ++;
            setQueryTendencyMap();
        }

        return false;
    }
}
