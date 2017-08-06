package com.sinosafe.xb.manager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.sinosafe.xb.manager.api.rxjava.RxSchedulersHelper;
import com.sinosafe.xb.manager.bean.LoginUserBean;
import com.sinosafe.xb.manager.module.login.LoginActivity;
import com.sinosafe.xb.manager.module.mine.CreateGestureActivity;
import com.sinosafe.xb.manager.module.mine.GestureLoginActivity;
import com.sinosafe.xb.manager.utils.MyUtils;
import com.sinosafe.xb.manager.utils.RxHelper;
import com.star.lockpattern.util.ACache;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import luo.library.base.base.BaseActivity;
import luo.library.base.base.BaseDb;
import luo.library.base.widget.DialogHelper;
import rx.Subscriber;

/**
 * 启动页 广告
 * Created by john lee on 2017/5/31.
 */
public class SplashADActivity extends BaseActivity {

    @BindView(R.id.btn_skip)
    Button mSbSkip;

    private boolean mIsSkip = false;
    private ACache aCache;
    private String gesturePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_ad);
        ButterKnife.bind(this);
        initView();
    }

    protected void initView() {
        init();
    }

    private void init() {
        //禁止模拟器运行
        if(MyUtils.CheckEmulatorBuild(this)){
            DialogHelper.getMessageDialog(this, "温馨提示", "华安信宝客户端禁止模拟器运行，谢谢您的支持！", "退出",
                    false, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    }).show();
        }
        else{
            aCache = ACache.get(this);
            gesturePassword = aCache.getAsString(CreateGestureActivity.GESTURE_PASSWORD);
            countdown();
        }
    }


    private void countdown() {
        RxHelper.countdown(3)
                .compose(RxSchedulersHelper.<Integer>io_main())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        _doSkip();
                    }

                    @Override
                    public void onError(Throwable e) {
                        _doSkip();
                    }

                    @Override
                    public void onNext(Integer integer) {
                        mSbSkip.setText("跳过 " + integer);
                    }
                });
    }

    private void _doSkip() {
        if (!mIsSkip) {
            mIsSkip = true;
            Intent intent;

            if(BaseDb.findOne(LoginUserBean.class)!=null){
                if (gesturePassword == null || "".equals(gesturePassword)) {
                    intent = new Intent(this, MainActivity.class);
                } else {
                    intent = new Intent(this, GestureLoginActivity.class);
                }
            }else{
                intent = new Intent(this, LoginActivity.class);
            }
            startActivity(intent);
            overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
            finish();
        }
    }

    @OnClick(R.id.btn_skip)
    public void onClick() {
        _doSkip();
    }

}
