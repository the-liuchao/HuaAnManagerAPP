package com.sinosafe.xb.manager.module.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sinosafe.xb.manager.MainActivity;
import com.sinosafe.xb.manager.R;
import com.sinosafe.xb.manager.api.rxjava.RxHttpBaseResultFunc;
import com.sinosafe.xb.manager.api.rxjava.RxHttpResultFunc;
import com.sinosafe.xb.manager.api.rxjava.RxSchedulersHelper;
import com.sinosafe.xb.manager.api.rxjava.RxSubscriber;
import com.sinosafe.xb.manager.bean.BaseEntity;
import com.sinosafe.xb.manager.bean.LoginUserBean;
import com.sinosafe.xb.manager.model.ClientModel;
import com.sinosafe.xb.manager.utils.T;
import com.sinosafe.xb.manager.widget.edit.ClearEditText;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import luo.library.base.base.BaseDb;
import luo.library.base.base.BaseFragmentActivity;
import luo.library.base.utils.IntentUtil;
import luo.library.base.utils.MyLog;

/**
 * 类名称：   LoginActivity
 * 内容摘要： //登陆。
 * 修改备注：
 * 创建时间： 2017/5/10
 * 公司：     深圳市华移科技股份有限公司
 * 作者：     Mr 张
 */
public class LoginActivity extends BaseFragmentActivity {


    @BindView(R.id.et_username)
    ClearEditText mEtUsername;
    @BindView(R.id.et_usePassword)
    ClearEditText mEtUsePassword;
    @BindView(R.id.btn_login)
    Button mBtnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //statusBarType = 1;
        super.onCreate(savedInstanceState);

        //渗入状态栏
       /* getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }*/

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_login)
    public void onViewClicked(View view) {

        if(view==mBtnLogin){

            if(isNull(mEtUsername)){
                T.showShortBottom("请输入用户名");
                return;
            }
            if(isNull(mEtUsePassword)){
                T.showShortBottom("请输入密码");
                return;
            }
            showWithStatus("正在登录...");
            login();
        }
    }

    /**
     * 登录
     */
    private void login(){

        String userName = mEtUsername.getText().toString().trim();
        String userPassword = mEtUsePassword.getText().toString();
        ClientModel.login(userName,userPassword)
                .timeout(20, TimeUnit.SECONDS)
                .compose(RxSchedulersHelper.<BaseEntity<LoginUserBean>>io_main())
                .map(new RxHttpResultFunc<LoginUserBean>())
                .subscribe(new MyRxSubscriber());
    }

    class MyRxSubscriber extends RxSubscriber<LoginUserBean>{
        @Override
        public void _onNext(LoginUserBean t) {
            updateChannelId(t.getToken());
            BaseDb.add(t);
            closeSVProgressHUD();
            IntentUtil.gotoActivityAndFinish(LoginActivity.this, MainActivity.class);
        }
        @Override
        public void _onError(String msg) {
            closeSVProgressHUD();
            if(msg!=null&&(msg.startsWith("http")||msg.startsWith("HTTP")))
                showErrorWithStatus("服务器开小差了.");

            else if(msg!=null&&msg.startsWith("账号")){
                showErrorWithStatus(msg);
            }
            else{
                showErrorWithStatus("服务器开小差了.");
            }
        }
    }

    /**
     * 保存极光推送channel_id
     */
    private void updateChannelId(String token){
        Map<String, String> maps = new HashMap<>();
        maps.put("channel_id", JPushInterface.getRegistrationID(this)+"");
        maps.put("token", token);
        ClientModel.updateChannelId(maps)
                .timeout(20, TimeUnit.SECONDS)
                .compose(RxSchedulersHelper.<BaseEntity>io_main())
                .map(new RxHttpBaseResultFunc())
                .subscribe(new PushRxSubscriber());
    }


    class PushRxSubscriber extends RxSubscriber<BaseEntity>{
        @Override
        public void _onNext(BaseEntity baseEntity) {

            MyLog.e("保存channelId成功"+baseEntity.getMsg());
        }
        @Override
        public void _onError(String msg) {
            MyLog.e("保存channelId失败"+msg);
        }
    }

}
