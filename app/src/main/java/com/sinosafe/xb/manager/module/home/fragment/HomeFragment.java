package com.sinosafe.xb.manager.module.home.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.sinosafe.xb.manager.BaseMainActivity;
import com.sinosafe.xb.manager.R;
import com.sinosafe.xb.manager.bean.GongGaoBean;
import com.sinosafe.xb.manager.bean.WaitProcess;
import com.sinosafe.xb.manager.bean.YeWuBean;
import com.sinosafe.xb.manager.module.home.NotSignedListActivity;
import com.sinosafe.xb.manager.module.home.loanmanager.LoanManagerActivity;
import com.sinosafe.xb.manager.module.home.PayListActivity;
import com.sinosafe.xb.manager.module.home.gonggao.GongGaoActivity;
import com.sinosafe.xb.manager.module.home.message.MessageListActivity;
import com.sinosafe.xb.manager.module.home.mianqian.MianQianListActivity;
import com.sinosafe.xb.manager.module.home.shouliyewu.CustomerDetailActivity;
import com.sinosafe.xb.manager.module.home.shouliyewu.DaiShouLiYewuActivity;
import com.sinosafe.xb.manager.module.home.supplyInfo.SupplyInfoListActivity;
import com.sinosafe.xb.manager.module.home.xiaofeidai.ConsumptionLoanActivity;
import com.sinosafe.xb.manager.utils.Constant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import luo.library.base.base.BaseImage;
import luo.library.base.utils.IntentUtil;
import luo.library.base.widget.banner.CycleViewPager;
import luo.library.base.widget.banner.CycleVpEntity;
import luo.library.base.widget.banner.ViewFactory;


/**
 * 首页片
 */
public class HomeFragment extends HomeBaseFragment implements View.OnClickListener{


    //@BindView(R.id.adViewpager)
    CycleViewPager mAdViewpager;
    @BindView(R.id.tv_xiaofeidai)
    TextView mTvXiaofeidai;
    @BindView(R.id.tv_weidai)
    TextView mTvWeidai;
    @BindView(R.id.daiShouLiLayout)
    RelativeLayout mDaiShouLiLayout;
    @BindView(R.id.itemLayout)
    LinearLayout mItemLayout;
    @BindView(R.id.daibanLayout)
    RelativeLayout mDaibanLayout;
    @BindView(R.id.tvMianQianNum)
    TextView mTvMianQianNum;
    @BindView(R.id.mianqianLayout)
    RelativeLayout mMianqianLayout;
    @BindView(R.id.tvBuChongNum)
    TextView mTvBuChongNum;
    @BindView(R.id.buchongLayout)
    RelativeLayout mBuchongLayout;
    @BindView(R.id.tvJiaofeiNum)
    TextView mTvJiaofeiNum;
    @BindView(R.id.jiaofeiLayout)
    RelativeLayout mJiaofeiLayout;
    @BindView(R.id.tvDaiHouNum)
    TextView mTvDaiHouNum;
    @BindView(R.id.daihouLayout)
    RelativeLayout mDaihouLayout;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.notSignedLayout)
    RelativeLayout notSignedLayout;
    @BindView(R.id.tvNotSignedNum)
    TextView tvNotSignedNum;
    Unbinder unbinder;

    @BindView(R.id.vFlipper)
    ViewFlipper vFlipper;
    protected boolean isViewInitiated;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statusBarType = 1;

        //注册跳转到待放款的广播
        IntentFilter filter = new IntentFilter();
        //刷新数据
        filter.addAction(Constant.REFRESH_HOME_PAGE);
        getActivity().registerReceiver(receiver,filter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        // 三句话 调用轮播广告
        mAdViewpager = (CycleViewPager) getActivity().getFragmentManager()
                .findFragmentById(R.id.adViewpager);
        mRefreshLayout.setColorSchemeResources(R.color.status_bar_bg, R.color.orange, R.color.green);
        mRefreshLayout.setOnRefreshListener(mOnRefreshListener);
        setTitleText("华安保险");
        hideBack();
        setRightImg(R.mipmap.icon_xiaoxi_no, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseMainActivity.hasNewMess = false;
                IntentUtil.gotoActivity(getActivity(),MessageListActivity.class);
                setRightImage();
            }
        });

        //请求数据
        mRefreshLayout.setRefreshing(true);
        getSysPics();
        checkTask();
    }

    //消息图标
    public void setRightImage(){
        if(BaseMainActivity.hasNewMess)
            setRightImg(R.mipmap.icon_xiaoxi_has);
        else{
            setRightImg(R.mipmap.icon_xiaoxi_no);
        }
    }

    @OnClick({R.id.tv_xiaofeidai, R.id.tv_weidai, R.id.daiShouLiLayout,
             R.id.mianqianLayout, R.id.buchongLayout,
            R.id.jiaofeiLayout, R.id.daihouLayout,R.id.notSignedLayout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //消费贷
            case R.id.tv_xiaofeidai:
                Bundle bundle = new Bundle();
                bundle.putInt("type",0);
                IntentUtil.gotoActivity(getActivity(), ConsumptionLoanActivity.class,bundle);
                break;
            //微贷
            case R.id.tv_weidai:
                Bundle bundle2 = new Bundle();
                bundle2.putInt("type",1);
                IntentUtil.gotoActivity(getActivity(), ConsumptionLoanActivity.class,bundle2);
                break;
            //待受理业务
            case R.id.daiShouLiLayout:
                IntentUtil.gotoActivity(getActivity(), DaiShouLiYewuActivity.class);
                break;
            //跳转到待办事项
            case R.id.daibanLayout:
                //((MainActivity)getActivity()).toIndexPage(1);
                //getActivity().sendBroadcast(new Intent(Constant.TO_DAI_BAN_SHI_XIANG));
                break;
            //面签
            case R.id.mianqianLayout:
                IntentUtil.gotoActivity(getActivity(), MianQianListActivity.class);

                break;
            //补充资料
            case R.id.buchongLayout:
                IntentUtil.gotoActivity(getActivity(), SupplyInfoListActivity.class);
                break;
            //缴费
            case R.id.jiaofeiLayout:
                IntentUtil.gotoActivity(getActivity(), PayListActivity.class);
                break;
            //贷后管理
            case R.id.daihouLayout:

                IntentUtil.gotoActivity(getActivity(), LoanManagerActivity.class);
                break;

            //不需要面签列表
            case R.id.notSignedLayout:

                IntentUtil.gotoActivity(getActivity(), NotSignedListActivity.class);
                break;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint() && isViewInitiated&&BaseMainActivity.loginUserBean!=null) {
            //请求数据
            mRefreshLayout.setRefreshing(true);
            checkTask();
            setRightImage();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setRightImage();
    }

    @Override
    public void onClick(View v) {

        int position = Integer.valueOf(v.getTag().toString());
        Bundle bundle = new Bundle();
        bundle.putSerializable("yeWuBean",yeWuBeens.get(position));
        IntentUtil.gotoActivity(getActivity(),CustomerDetailActivity.class,bundle);
    }


    @Override
    protected void requestFail() {
        if(mRefreshLayout==null)
            return;
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void checkTaskRequestSucess(List<YeWuBean> yeWuBeens) {

        if(yeWuBeens!=null){
            mItemLayout.removeAllViews();
            LayoutInflater inflater = getActivity().getLayoutInflater();
            int size = 0;
            if(yeWuBeens.size()>4)
                size = 4;
            else
            {
                size = yeWuBeens.size();
            }
            for (int i = 0; i < size; i++)
            {
                YeWuBean.User user = yeWuBeens.get(i).getUser();
                View view = inflater.inflate(R.layout.home_daishouli_item, null);
                ImageView ivImage = (ImageView) view.findViewById(R.id.ivImage);
                TextView tvName = (TextView) view.findViewById(R.id.tvName);
                TextView tvType = (TextView) view.findViewById(R.id.tvType);

                tvName.setText(yeWuBeens.get(i).getCUS_NAME());
                tvType.setText(yeWuBeens.get(i).getPRD_NAME());
                if(user!=null){
                    BaseImage.getInstance().displayCricleImage(getActivity(),
                            user.getHead_photo(),ivImage,R.mipmap.icon_default_avatar);
                }
                view.setTag(i);
                view.setOnClickListener(this);
                mItemLayout.addView(view);
            }
        }

    }

    @Override
    protected void waitProcessRequestSucess(WaitProcess waitProcess) {

        if(waitProcess!=null){

            if(isNull(waitProcess.getTaskType20()))
                mTvMianQianNum.setText("0");
            else
                mTvMianQianNum.setText(waitProcess.getTaskType20());
            if(isNull(waitProcess.getTaskType30()))
                mTvBuChongNum.setText("0");
            else
                mTvBuChongNum.setText(waitProcess.getTaskType30());
            if(isNull(waitProcess.getTaskType40()))
                mTvJiaofeiNum.setText("0");
            else
                mTvJiaofeiNum.setText(waitProcess.getTaskType40());
            if(isNull(waitProcess.getTaskType50()))
                mTvDaiHouNum.setText("0");
            else
                mTvDaiHouNum.setText(waitProcess.getTaskType50());

            if(isNull(waitProcess.getTaskType100()))
                tvNotSignedNum.setText("0");
            else
                tvNotSignedNum.setText(waitProcess.getTaskType100());

            isViewInitiated = true;
        }
    }

    @Override
    protected void bannerRequestSucess(List<CycleVpEntity> vpEntities) {

        ViewFactory.initialize(getActivity(),mAdViewpager,vpEntities);
    }

    @Override
    protected void gongGaoRequestSucess(List<GongGaoBean> gongGaoBeens) {
        if (gongGaoBeens != null && gongGaoBeens.size() > 0) {
            for (GongGaoBean notice : gongGaoBeens) {
                View view = View.inflate(getActivity(), R.layout.layout_home_notice, null);
                TextView tv_notice = (TextView) view.findViewById(R.id.tv_notice);
                tv_notice.setText(notice.getPost_title());
                tv_notice.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        IntentUtil.gotoActivity(getActivity(), GongGaoActivity.class);
                    }
                });
                vFlipper.addView(view);
            }
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
            getSysPics();
            checkTask();
        }
    };

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mRefreshLayout.setRefreshing(true);
            checkTask();
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(receiver);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
