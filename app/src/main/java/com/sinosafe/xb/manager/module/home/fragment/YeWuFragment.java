package com.sinosafe.xb.manager.module.home.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sinosafe.xb.manager.APP;
import com.sinosafe.xb.manager.BaseMainActivity;
import com.sinosafe.xb.manager.MainActivity;
import com.sinosafe.xb.manager.R;
import com.sinosafe.xb.manager.bean.LoginUserBean;
import com.sinosafe.xb.manager.module.login.LoginActivity;
import com.sinosafe.xb.manager.module.mine.CreateGestureActivity;
import com.sinosafe.xb.manager.module.yewu.weidai.MicroCreditDetailActivity;
import com.sinosafe.xb.manager.module.yewu.xiaofeidai.ConsumeDetailActivity;
import com.sinosafe.xb.manager.utils.Constant;
import com.star.lockpattern.util.ACache;

import butterknife.BindView;
import butterknife.ButterKnife;
import luo.library.base.base.BaseDb;
import luo.library.base.utils.IntentUtil;
import luo.library.base.widget.dialog.DialogMessage;

import static com.sinosafe.xb.manager.R.id.tabLayout;
import static com.sinosafe.xb.manager.module.mine.GestureLoginActivity.GESTURE_NUM;
import static com.sinosafe.xb.manager.utils.Constant.LOAD_APPLY_PROGRESS;
import static com.sinosafe.xb.manager.utils.Constant.TOKEN_INVALID_422;

/**
 * 业务片
 */
public class YeWuFragment extends YeWuBaseFragment {


    @BindView(tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    private boolean isViewInitiated;
    private SimpleFragmentPagerAdapter pagerAdapter;
    private ACache aCache;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statusBarType = 1;

        //注册跳转到待放款的广播
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.TO_DAI_BAN_SHI_XIANG);
        //token失效
        filter.addAction(Constant.TOKEN_INVALID_422);
        //查看贷款申请进度
        filter.addAction(Constant.LOAD_APPLY_PROGRESS);
        //跳转到申请中
        filter.addAction(Constant.TO_LOAD_APPLYING);
        getActivity().registerReceiver(receiver,filter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_yewu, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        setTitleText("业务");
        hideBack();

        initView();
        isViewInitiated = true;
    }


    /**
     * 初始化
     */
    private void initView() {

        // 设置适配器
        pagerAdapter = new SimpleFragmentPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(pagerAdapter);

        //4个选卡16,5个选卡10
        mTabLayout.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(mTabLayout,10,10);
            }
        });
        mViewPager.setOffscreenPageLimit(4);
        // 为TabLayout设置ViewPager
        mTabLayout.setupWithViewPager(mViewPager);

    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent.getAction().equals(Constant.TO_DAI_BAN_SHI_XIANG))
                mViewPager.setCurrentItem(0,true);

            //token 失效
            else if(intent.getAction().equals(TOKEN_INVALID_422)){
                IntentUtil.gotoActivity(getActivity(), MainActivity.class);
                showToken422Dialog();
            }
            //查看贷款申请进度
            else if(intent.getAction().equals(LOAD_APPLY_PROGRESS)){
                Bundle bundle = new Bundle();
                bundle.putString("serno",intent.getStringExtra("serno"));
                bundle.putString("prdId",intent.getStringExtra("prdId"));
                String prdType = intent.getStringExtra("prdType");
                if("2".equals(prdType))
                    IntentUtil.gotoActivity(getActivity(),MicroCreditDetailActivity.class,bundle);
                else
                    IntentUtil.gotoActivity(getActivity(),ConsumeDetailActivity.class,bundle);
                //IntentUtil.gotoActivity(getActivity(),ConsumeDetailActivity.class,bundle);
            }

            //跳转到申请中
            else if(intent.getAction().equals(Constant.TO_LOAD_APPLYING)){
                ((MainActivity)getActivity()).toIndexPage(1);
                mViewPager.setCurrentItem(0,true);
                if(intent.getIntExtra("type",0)==1)
                    getActivity().sendBroadcast(new Intent(Constant.REFRESH_APPLYING_DATA));
                else{
                    getActivity().sendBroadcast(new Intent(Constant.LOAD_YEWU_DATA_OF_APPLY));
                }
            }
        }
    };

    private void showToken422Dialog(){

        DialogMessage dialogMessage = new DialogMessage(getActivity()).setConfirmListener(new DialogMessage.OnConfirmListener() {
            @Override
            public void onConfirm() {
                BaseMainActivity.exitOver = false;
                clearLoginUserInfo();
            }
        }).setOtherConfirmListener(new DialogMessage.OnOtherConfirmListener() {
            @Override
            public void onOtherConfirm() {
                BaseMainActivity.exitOver = true;
                getActivity().finish();
            }
        }).setConfirmTips("重新登录").setOtherConfirmTips("取消");
        dialogMessage.setCanceledOnTouchOutside(false);
        dialogMessage.setCancelable(false);
        dialogMessage.setMess("亲，登录已失效，请重新登录!");
        dialogMessage.show();
    }

    /**
     * 清理退出用户登录信息
     */
    private void clearLoginUserInfo(){
        //finish MainActivity
        aCache = ACache.get(getActivity());
        aCache.remove(CreateGestureActivity.GESTURE_PASSWORD);
        //清除手势密码输入错误次数
        aCache.remove(GESTURE_NUM);
        APP.getApplication().getMainActivity().finish();
        APP.getApplication().setMainActivity(null);
        //删除登录用户信息
        BaseDb.delete(LoginUserBean.class, BaseMainActivity.loginUserBean.getId());
        BaseMainActivity.loginUserBean = null;
        //跳转到登录界面
        IntentUtil.gotoActivityAndFinish(getActivity(), LoginActivity.class);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(receiver);
    }
}
