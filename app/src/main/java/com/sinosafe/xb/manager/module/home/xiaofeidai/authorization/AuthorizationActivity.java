package com.sinosafe.xb.manager.module.home.xiaofeidai.authorization;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sinosafe.xb.manager.APP;
import com.sinosafe.xb.manager.BaseMainActivity;
import com.sinosafe.xb.manager.R;
import com.sinosafe.xb.manager.api.rxjava.RxHttpBaseResultFunc;
import com.sinosafe.xb.manager.api.rxjava.RxSchedulersHelper;
import com.sinosafe.xb.manager.api.rxjava.RxSubscriber;
import com.sinosafe.xb.manager.bean.BaseEntity;
import com.sinosafe.xb.manager.model.ClientModel;
import com.sinosafe.xb.manager.utils.Constant;
import com.sinosafe.xb.manager.utils.T;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import luo.library.base.base.BaseFragmentActivity;
import luo.library.base.utils.IntentUtil;
import luo.library.base.utils.MyLog;
import luo.library.base.widget.dialog.DialogMessage;

import static com.sinosafe.xb.manager.APP.bundle;
import static com.sinosafe.xb.manager.module.home.xiaofeidai.XiaoFeiDaiApplyActivity.loanUserInfo;


/**
 * 消费贷 授权
 * Created by john lee on 2017/5/19.
 */

public class AuthorizationActivity extends BaseFragmentActivity {


    @BindView(R.id.tv_providentfund_auth)
    TextView mTvProvidentfundAuth;
    @BindView(R.id.tv_socialsecurity_auth)
    TextView mTvSocialsecurityAuth;

    private String url ;
    private int type = 0;
    //0：公积金；1：社保
    private int clickFlag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumption_loan_authorization);

        ButterKnife.bind(this);
        initView();
    }

    protected void initView() {
        setTitleText("授权");
        type = getIntent().getIntExtra("type",0);
        setAuthorizationBtn();
        LinearLayout backTv = (LinearLayout) findViewById(R.id.ly_base_back);
        backTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if(!exitFlag()){
                    showExitPrompt();
                    return;
                }
                finish();
            }
        });
    }


    /**
     * 设置授权按钮
     */
    private void setAuthorizationBtn(){
        if(loanUserInfo!=null){
            if(loanUserInfo.isSheBaoAuth())
                mTvSocialsecurityAuth.setEnabled(true);
            else{
                mTvSocialsecurityAuth.setEnabled(false);
            }

            if(loanUserInfo.isGjjAuth())
                mTvProvidentfundAuth.setEnabled(true);
            else{
                mTvProvidentfundAuth.setEnabled(false);
            }
        }

        else {
            boolean isGjjAuth = APP.bundle.getBoolean("isGjjAuth");
            boolean isSheBaoAuth = APP.bundle.getBoolean("isSheBaoAuth");

            if(isGjjAuth)
                mTvProvidentfundAuth.setEnabled(true);
            else{
                mTvProvidentfundAuth.setEnabled(false);
            }

            if(isSheBaoAuth)
                mTvSocialsecurityAuth.setEnabled(true);
            else{
                mTvSocialsecurityAuth.setEnabled(false);
            }
        }
    }


    @OnClick({R.id.tv_providentfund_auth, R.id.tv_socialsecurity_auth})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //公积金
            case R.id.tv_providentfund_auth:
                showWithStatus("");
                authPage("GJJ");
                clickFlag = 0;
                break;
            //社保
            case R.id.tv_socialsecurity_auth:
                showWithStatus("");
                authPage("SHE_BAO");
                clickFlag = 1;
                break;
        }
    }

    /**
     * 社保公积金页面跳转
     */
    private void authPage(String channel_type){
        Map<String, String> maps = new HashMap<>();
        maps.put("token", BaseMainActivity.loginUserBean.getToken());
        maps.put("channel_type", channel_type);
        if(loanUserInfo!=null) {
            maps.put("cus_id", loanUserInfo.getUser_detail().getUser_id());
            maps.put("real_name", loanUserInfo.getUser_detail().getUser_name());
            maps.put("identity_code", loanUserInfo.getUser_detail().getCert_code());
        }
        else{
            maps.put("cus_id", bundle.getString("cus_id"));
            maps.put("real_name", bundle.getString("real_name"));
            maps.put("identity_code", bundle.getString("identity_code"));
        }
        ClientModel.authPage(maps)
            .timeout(20, TimeUnit.SECONDS)
            .compose(RxSchedulersHelper.<BaseEntity>io_main())
            .map(new RxHttpBaseResultFunc())
            .subscribe(new RxSubscriber<BaseEntity>() {
                @Override
                public void _onNext(BaseEntity entity) {
                    closeSVProgressHUD();
                    if(entity.getCode()==1){
                        url = entity.getMsg();
                        mHandler.sendEmptyMessageDelayed(0,300);
                    }else {
                        T.showShortBottom(entity.getMsg());
                    }
                }
                @Override
                public void _onError(String msg) {
                    T.showShortBottom(msg);
                    closeSVProgressHUD();
                    MyLog.e("社保公积金页面跳转_onError===="+msg);
                }});
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Bundle bundle = new Bundle();
            bundle.putString("url",url);
            IntentUtil.gotoActivityForResult(AuthorizationActivity.this,AuthorizationWebView.class,bundle,1000);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1000&&resultCode==RESULT_OK){
            if(type==0) {
                //已授权
                if (clickFlag == 0)
                    loanUserInfo.setGjjAuth(false);
                if (clickFlag == 1)
                    loanUserInfo.setSheBaoAuth(false);
            }
            else{
                if (clickFlag == 0)
                    APP.bundle.putBoolean("isGjjAuth",false);
                if (clickFlag == 1)
                    APP.bundle.putBoolean("isSheBaoAuth",false);
            }
            needAuthorization();
        }
    }

    /**
     * 是否需要授权
     */
    private void needAuthorization(){
        setAuthorizationBtn();
        if(type==0) {
           if(!loanUserInfo.isSheBaoAuth()&&!loanUserInfo.isGjjAuth()){
               setResult(RESULT_OK);
               finish();
           }
        }
        else{

            boolean isGjjAuth = APP.bundle.getBoolean("isGjjAuth");
            boolean isSheBaoAuth = APP.bundle.getBoolean("isSheBaoAuth");
            if(!isGjjAuth&&!isSheBaoAuth){

                sendBroadcast(new Intent(Constant.APPLY_AFTER_AUTHORIZATION));
                finish();
            }
        }
    }


    /**
     * 退出时，判断提示
     * @return
     */
    private boolean exitFlag(){
        if(type==0) {
            if(loanUserInfo.isSheBaoAuth()||loanUserInfo.isGjjAuth()){
                return false;
            }
        }
        else{
            boolean isGjjAuth = APP.bundle.getBoolean("isGjjAuth");
            boolean isSheBaoAuth = APP.bundle.getBoolean("isSheBaoAuth");
            if(isGjjAuth||isSheBaoAuth){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(!exitFlag()){
                showExitPrompt();
                return false;
            }
        }
        return true;
    }


    private void showExitPrompt(){
        if(clickFlag==0)
        new DialogMessage(this).setMess("亲，您还没进行相关授权，确定要退出吗?")
        .setConfirmListener(new DialogMessage.OnConfirmListener() {
            @Override
            public void onConfirm() {
                finish();
            }
        }).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
