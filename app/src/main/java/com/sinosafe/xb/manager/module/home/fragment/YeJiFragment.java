package com.sinosafe.xb.manager.module.home.fragment;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sinosafe.xb.manager.R;
import com.sinosafe.xb.manager.module.yeji.OverdueActivity;
import com.sinosafe.xb.manager.module.yeji.YeJiBrokenLineChartActivity;
import com.sinosafe.xb.manager.module.yeji.YeJiSortActivity;
import com.sinosafe.xb.manager.module.yeji.bean.RankingList;
import com.sinosafe.xb.manager.module.yeji.bean.YeJiReport;
import com.sinosafe.xb.manager.utils.MyUtils;
import com.sinosafe.xb.manager.widget.circleview.DrawView;
import com.sinosafe.xb.manager.widget.circleview.SectorItem;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import luo.library.base.utils.IntentUtil;
import luo.library.base.widget.MoneyView;
import luo.library.base.widget.SwipeRefreshLayoutCompat;


/**
 * 业绩片
 */
public class YeJiFragment extends YeJiBaseFragment {

    protected boolean isViewInitiated;
    @BindView(R.id.tvMoney)
    MoneyView mTvMoney;
    @BindView(R.id.tvTiCheng)
    MoneyView mTvTiCheng;
    @BindView(R.id.tv_cunFangNum)
    TextView mTvCunFangNum;
    @BindView(R.id.tvBaofei)
    TextView mTvBaofei;
    @BindView(R.id.tv_yuQiNum)
    TextView mTvYuQiNum;
    @BindView(R.id.tvYuQiMoney)
    TextView mTvYuQiMoney;
    @BindView(R.id.tv_cunFangNum2)
    TextView mTvCunFangNum2;
    @BindView(R.id.tvFangKuanMoney)
    MoneyView mTvFangKuanMoney;
    @BindView(R.id.tvBaoFeiMoney)
    MoneyView mTvBaoFeiMoney;
    @BindView(R.id.tv_baoFeiSort)
    TextView mTvBaoFeiSort;
    @BindView(R.id.baoFeiLayout)
    LinearLayout mBaoFeiLayout;
    @BindView(R.id.tv_tiChengSort)
    TextView mTvTiChengSort;
    @BindView(R.id.tiChengLayout)
    LinearLayout mTiChengLayout;
    @BindView(R.id.tv_fangKuanNumSort)
    TextView mTvFangKuanNumSort;
    @BindView(R.id.fangKuanNumLayout)
    LinearLayout mFangKuanNumLayout;
    @BindView(R.id.tv_fangKuanMoneySort)
    TextView mTvFangKuanMoneySort;
    @BindView(R.id.fangKuanMoneyLayout)
    LinearLayout mFangKuanMoneyLayout;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayoutCompat mRefreshLayout;

    @BindView(R.id.bankNumLayout)
    LinearLayout mBankNumLayout;
    @BindView(R.id.premiumLayout)
    LinearLayout mPremiumLayout;
    @BindView(R.id.overdueNumLayout)
    LinearLayout mOverdueNumLayout;
    @BindView(R.id.overdueAmountLayout)
    LinearLayout mOverdueAmountLayout;
    @BindView(R.id.drawView)
    DrawView drawView;
    @BindView(R.id.mvLoanMoney)
    TextView mvLoanMoney;
    @BindView(R.id.mvPaymentMoney)
    TextView mvPaymentMoney;
    @BindView(R.id.tvTheMonthTiCheng)
    MoneyView mTvTheMonthTiCheng;
    @BindView(R.id.tvUpMonthTiCheng)
    MoneyView mTvUpMonthTiCheng;
    @BindView(R.id.tv_repayMentrate)
    TextView mTvRepayMentrate;
    @BindView(R.id.repaymentLayout)
    LinearLayout mRepaymentLayout;

    private boolean hasRequest = false;
    Unbinder unbinder;

    public YeJiFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statusBarType = 1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_yeji, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        mRefreshLayout.setColorSchemeResources(R.color.status_bar_bg, R.color.orange, R.color.green);
        mRefreshLayout.setOnRefreshListener(mOnRefreshListener);
        setTitleText("业绩");
        hideBack();
        mTiChengLayout.setClickable(false);
        isViewInitiated = true;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint() && isViewInitiated && !hasRequest) {
            mRefreshLayout.setRefreshing(true);
            queryTotalReport();
            queryMgrRanking();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.baoFeiLayout, R.id.tiChengLayout, R.id.fangKuanNumLayout, R.id.fangKuanMoneyLayout
            , R.id.bankNumLayout, R.id.premiumLayout, R.id.overdueNumLayout, R.id.overdueAmountLayout,
            R.id.repaymentLayout})
    public void onViewClicked(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            //保费收入
            case R.id.baoFeiLayout:
                bundle.putInt("type", 0);
                IntentUtil.gotoActivity(getActivity(), YeJiSortActivity.class, bundle);
                break;
            //业绩提成
            case R.id.tiChengLayout:
                bundle.putInt("type", 1);
                IntentUtil.gotoActivity(getActivity(), YeJiSortActivity.class, bundle);
                break;
            //放款笔数
            case R.id.fangKuanNumLayout:
                bundle.putInt("type", 2);
                IntentUtil.gotoActivity(getActivity(), YeJiSortActivity.class, bundle);
                break;
            //放款额数
            case R.id.fangKuanMoneyLayout:
                bundle.putInt("type", 3);
                IntentUtil.gotoActivity(getActivity(), YeJiSortActivity.class, bundle);
                break;

            //回款率
            case R.id.repaymentLayout:
                bundle.putInt("type", 4);
                IntentUtil.gotoActivity(getActivity(), YeJiSortActivity.class, bundle);
                break;

            //存款笔数
            case R.id.bankNumLayout:
                bundle.putInt("type", 0);
                IntentUtil.gotoActivity(getActivity(), YeJiBrokenLineChartActivity.class, bundle);
                break;
            //保费收入
            case R.id.premiumLayout:
                bundle.putInt("type", 1);
                IntentUtil.gotoActivity(getActivity(), YeJiBrokenLineChartActivity.class, bundle);
                break;
            //逾期笔数
            case R.id.overdueNumLayout:
                bundle.putInt("type", 0);
                IntentUtil.gotoActivity(getActivity(), OverdueActivity.class, bundle);
                break;
            //逾期金额
            case R.id.overdueAmountLayout:
                bundle.putInt("type", 1);
                IntentUtil.gotoActivity(getActivity(), OverdueActivity.class, bundle);
                break;
        }
    }

    /**
     * 刷新
     */
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            //请求数据
            mRefreshLayout.setRefreshing(true);
            queryTotalReport();
            queryMgrRanking();
        }
    };

    //整体情况
    @Override
    protected void requestSuccessToalReport(YeJiReport yeJiReport) {

        //mTvFangKuanMoney.setMoneyText(MyUtils.keepTwoDecimal(yeJiReport.getLoanFund()));
        mTvTiCheng.setMoneyText(MyUtils.keepTwoDecimal(yeJiReport.getAMOUNT()));
        mTvCunFangNum.setText(NumberFormat.getIntegerInstance(Locale.getDefault()).format(yeJiReport.getLoanAmount())+ "");
        mTvBaofei.setText(MyUtils.keepTwoDecimal(yeJiReport.getSalary()));
        mTvYuQiNum.setText(NumberFormat.getIntegerInstance(Locale.getDefault()).format(yeJiReport.getDelayAmount()) + "");
        mTvYuQiMoney.setText(MyUtils.keepTwoDecimal(yeJiReport.getDelayFund()/10000));

        mvLoanMoney.setText(MyUtils.keepTwoDecimal(yeJiReport.getLoanFund()));
        mvPaymentMoney.setText(MyUtils.keepTwoDecimal(yeJiReport.getBackFund()));
        mTvTheMonthTiCheng.setMoneyText(MyUtils.keepTwoDecimal(yeJiReport.getMonthBonus()));
        mTvUpMonthTiCheng.setMoneyText(MyUtils.keepTwoDecimal(yeJiReport.getLastMonthBonus()));

        int endAngle = -89;
        if(yeJiReport.getLoanFund()>0)
            endAngle = (int) (360*yeJiReport.getBackFund()/yeJiReport.getLoanFund()) - 90;
        if(yeJiReport.getBackFund()==0)
            endAngle = -89;
        generateData(endAngle);
    }

    //每日情况
    @Override
    protected void requestSuccessTodayReport(YeJiReport yeJiReport) {

        mTvCunFangNum2.setText(yeJiReport.getLoanAmount() + "");
        mTvFangKuanMoney.setMoneyText(MyUtils.keepTwoDecimal(yeJiReport.getLoanFund()));
        mTvBaoFeiMoney.setMoneyText(MyUtils.keepTwoDecimal(yeJiReport.getSalary()));
        isViewInitiated = true;
    }

    //排行榜
    @Override
    protected void requestSuccessRankingList(RankingList rankingList) {

        if(rankingList.getSalary()>0)
            mTvBaoFeiSort.setText(rankingList.getSalary() + "名");
        else
            mTvBaoFeiSort.setText("无");

        if(rankingList.getBonus()>0)
            mTvTiChengSort.setText(rankingList.getBonus() + "名");
        else
            mTvTiChengSort.setText("无");

        if(rankingList.getLoanAmount()>0)
            mTvFangKuanNumSort.setText(rankingList.getLoanAmount() + "名");
        else
            mTvFangKuanNumSort.setText("无");

        if(rankingList.getLoanFund()>0)
            mTvFangKuanMoneySort.setText(rankingList.getLoanFund() + "名");
        else
            mTvFangKuanMoneySort.setText("无");

        if(rankingList.getBackPaymentRate()>0)
            mTvRepayMentrate.setText(rankingList.getBackPaymentRate() + "名");
        else
            mTvRepayMentrate.setText("无");
    }

    @Override
    protected void requestFail() {
        if(mRefreshLayout!=null)
            mRefreshLayout.setRefreshing(false);
    }


    private void generateData(int endAngle) {
        drawView.stop();
        List<SectorItem> list = new ArrayList<SectorItem>();
        SectorItem item = new SectorItem();
        item.setStartAngle(-90);
        item.setEndAngle(endAngle);
        list.add(item);
        drawView.setData(list);
    }

}
