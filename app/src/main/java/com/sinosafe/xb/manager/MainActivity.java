package com.sinosafe.xb.manager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.megvii.livenesslib.util.ConUtil;
import com.sinosafe.xb.manager.bean.LoginUserBean;
import com.sinosafe.xb.manager.utils.Constant;
import com.sinosafe.xb.manager.utils.MyAMapLocUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import luo.library.base.base.BaseDb;
import luo.library.base.utils.MyLog;
import luo.library.base.widget.NoScrollViewPager;

/**
 * 主activity
 */
public class MainActivity extends BaseMainActivity {


    @BindView(R.id.homeRadio)
    RadioButton mHomeRadio;
    @BindView(R.id.yeWuRadio)
    RadioButton mYeWuRadio;
    @BindView(R.id.yeJiRadio)
    RadioButton mYeJiRadio;
    @BindView(R.id.meRadio)
    RadioButton mMeRadio;
    @BindView(R.id.menu_group)
    RadioGroup mMenuGroup;
    @BindView(R.id.bottomMenu)
    LinearLayout mBottomMenu;
    @BindView(R.id.viewpager)
    NoScrollViewPager mViewpager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        statusBarType = 1;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        APP.getApplication().setMainActivity(this);
        uuid = ConUtil.getUUIDString(this);
        initView();

        registerMessageReceiver();
    }

    private void initView() {
        mViewpager.setOffscreenPageLimit(4);
        adapter = new FragmentAdapter(getSupportFragmentManager());
        mViewpager.setAdapter(adapter);

        //获取登录用户信息
        loginUserBean = BaseDb.findOne(LoginUserBean.class);

        //定位
        aMapLocUtils = new MyAMapLocUtils(this);
        aMapLocUtils.startLocation();
    }


    @OnClick({R.id.homeRadio, R.id.yeWuRadio, R.id.yeJiRadio, R.id.meRadio})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.homeRadio:
                mViewpager.setCurrentItem(TAB_page1);
                break;
            case R.id.yeWuRadio:
                mViewpager.setCurrentItem(TAB_page2);
                sendBroadcast(new Intent(Constant.LOAD_YEWU_DATA_OF_APPLY));
                break;
            case R.id.yeJiRadio:
                mViewpager.setCurrentItem(TAB_page3);
                break;
            case R.id.meRadio:
                mViewpager.setCurrentItem(TAB_page4);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==10000&&resultCode==RESULT_OK) {
            MyLog.e("==============================================================");
            sendBroadcast(new Intent(Constant.REFRESH_YEWU_DATA));
        }
    }

    /**
     * 跳转到某一页
     */
    public void toIndexPage(int index){

        mYeWuRadio.setChecked(true);
        mViewpager.setCurrentItem(index);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        aMapLocUtils.stopLocation();
        // 确保完全退出，释放所有资源
        if(exitOver) {
            //unregisterReceiver(mMessageReceiver);
            //APP.getApplication().setMainActivity(null);
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }
}
