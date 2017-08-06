package com.sinosafe.xb.manager.widget;

import android.content.Context;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.sinosafe.xb.manager.BaseMainActivity;
import com.sinosafe.xb.manager.R;
import com.sinosafe.xb.manager.api.rxjava.RxHttpBaseResultFunc;
import com.sinosafe.xb.manager.api.rxjava.RxHttpResultFunc;
import com.sinosafe.xb.manager.api.rxjava.RxSchedulersHelper;
import com.sinosafe.xb.manager.api.rxjava.RxSubscriber;
import com.sinosafe.xb.manager.bean.BaseEntity;
import com.sinosafe.xb.manager.bean.SMScode;
import com.sinosafe.xb.manager.model.ClientModel;
import com.sinosafe.xb.manager.utils.MyUtils;
import com.sinosafe.xb.manager.utils.T;
import com.sinosafe.xb.manager.widget.edit.ClearEditText;

import java.util.concurrent.TimeUnit;

import luo.library.base.base.BaseFragmentActivity;
import luo.library.base.utils.MyLog;
import luo.library.base.utils.NetworkUtils;

import static com.sinosafe.xb.manager.module.home.xiaofeidai.XiaoFeiDaiApplyActivity.loanUserInfo;

/**
 * 获取验证码 按钮
 * Created by john lee on 2017/5/30.
 */

public class VerificationCodeButton extends Button {

    private String phoneNumber = "";
    private String smsCode ;
    private int sendType = 0;//0: 已注册发送验证码 1：电子签署
    private OnBackCodeListener backCodeListener;
    private ClearEditText mEtPhone;
    private Context mContext;
    public static String userId ;
    public static String serno;

    public void setEtPhone(ClearEditText mEtPhone){
        this.mEtPhone = mEtPhone;
    }

    public String getSmsCode(){
        return smsCode;
    }

    public void setOnBackCodeListener(OnBackCodeListener backCodeListener) {
        this.backCodeListener = backCodeListener;
    }

    public interface OnBackCodeListener {
        void OnBackCode(String code);
    }


    public VerificationCodeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setOnClickListener();
        mContext = context;
    }

    private void setOnClickListener() {

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                smsCode = null;
                if(sendType==2){
                    sendPhoneMessage2();
                }else
                    sendPhoneMessage();
            }
        });
    }


    public void setPhoneString(String phone) {
        phoneNumber = phone;
    }

    public void setSendType(int sendType) {
        this.sendType = sendType;
    }

    public void startTimer() {
        countDownTimer.cancel();
        countDownTimer.start();
    }

    private CountDownTimer countDownTimer = new CountDownTimer(60000, 1000) {

        public void onTick(long millisUntilFinished) {
            setText(String.format("%d秒", millisUntilFinished / 1000));
            setEnabled(false);
        }

        public void onFinish() {
            setEnabled(true);
            setText(R.string.get_verification_code);
            //onStop();
        }
    };

    public void onStop() {
        countDownTimer.cancel();
        countDownTimer.onFinish();
    }

    void sendPhoneMessage() {

        if (mEtPhone != null) {
            phoneNumber = mEtPhone.getText().toString();
        }
        if (sendType==0&&TextUtils.isEmpty(phoneNumber)) {
            T.showShortBottom("请输入手机号");
            return;
        }
        if (sendType==0&&!MyUtils.isPhone(phoneNumber)) {
            T.showShortBottom("手机号码格式错误");
            return;
        }
        if(!NetworkUtils.isNetAvailable(mContext)){
            T.showShortBottom("亲,最遥远的距离就是没有网络,请检查!");
            return;
        }
        if(sendType==0) {
            ((BaseFragmentActivity)mContext).showWithStatus("正在获取...");
            sendCode();
        }
        else{
            /*if(FileUtil.getBitmap(mContext,SIGNATURE_NAME)==null){
                T.showShortBottom("请先进行电子签署");
                return;
            }*/
            ((BaseFragmentActivity)mContext).startProgressDialog("正在获取...");
            sendSignMobileCode();
        }
    }


    void sendPhoneMessage2() {

        if (mEtPhone != null) {
            phoneNumber = mEtPhone.getText().toString();
        }
        if(!NetworkUtils.isNetAvailable(mContext)){
            T.showShortBottom("亲,最遥远的距离就是没有网络,请检查!");
            return;
        }
        /*if(FileUtil.getBitmap(mContext,SIGNATURE_NAME)==null){
            T.showShortBottom("请先进行电子签署");
            return;
        }*/
        ((BaseFragmentActivity)mContext).startProgressDialog("正在获取...");
        sendSignMobileCode2();
    }

    /**
     * 检查手机是否注册
     *
     * @param phoneNumber
     */
    private void checkUser(String phoneNumber) {

    }

    /**
     * 发送验证码(一般业务)
     */
    private void sendCode() {

        ClientModel.sendCode(phoneNumber)
            .timeout(20, TimeUnit.SECONDS)
            .compose(RxSchedulersHelper.<BaseEntity<SMScode>>io_main())
            .map(new RxHttpResultFunc<SMScode>())
            .subscribe(new RxSubscriber<SMScode>() {
                @Override
                public void _onNext(SMScode t) {
                    smsCode = t.getSmsCode();
                    ((BaseFragmentActivity)mContext).closeSVProgressHUD();
                    T.showShortBottom("验证码已发送,请注意查收!");
                    startTimer();
                }
                @Override
                public void _onError(String msg) {
                    ((BaseFragmentActivity)mContext).closeSVProgressHUD();
                    ((BaseFragmentActivity)mContext).showErrorWithStatus(msg);
                }});
    }


    /**
     * 发送验证码(征信授权书)
     */
    private void sendSignMobileCode() {

        String token = BaseMainActivity.loginUserBean.getToken();
        String userId = loanUserInfo.getUser_detail().getUser_id();
        String serno = loanUserInfo.getUser_detail().getSerno();
        ClientModel.sendSignMobileCode3rd(token,userId,serno,"0")
            .timeout(20, TimeUnit.SECONDS)
            .compose(RxSchedulersHelper.<BaseEntity>io_main())
            .map(new RxHttpBaseResultFunc())
            .subscribe(new RxSubscriber<BaseEntity>() {
                @Override
                public void _onNext(BaseEntity entity) {
                    ((BaseFragmentActivity)mContext).stopProgressDialog();
                    if(entity.getCode()==1) {
                        startTimer();
                        smsCode = "110120";
                    }
                    T.showShortBottom(entity.getMsg());
                }
                @Override
                public void _onError(String msg) {
                    MyLog.e("获取电子签署验证码异常======"+msg);
                    T.showShortBottom(msg);
                    ((BaseFragmentActivity)mContext).stopProgressDialog();
                }});
    }


    /**
     * 发送验证码(投保单)
     */
    private void sendSignMobileCode2() {
        String token = BaseMainActivity.loginUserBean.getToken();
        ClientModel.sendSignMobileCode3rd(token,userId,serno,"1")
            .timeout(20, TimeUnit.SECONDS)
            .compose(RxSchedulersHelper.<BaseEntity>io_main())
            .map(new RxHttpBaseResultFunc())
            .subscribe(new RxSubscriber<BaseEntity>() {
                @Override
                public void _onNext(BaseEntity entity) {
                    ((BaseFragmentActivity)mContext).stopProgressDialog();
                    if(entity.getCode()==1) {
                        startTimer();
                        smsCode = "110120";
                    }
                    T.showShortBottom(entity.getMsg());
                }
                @Override
                public void _onError(String msg) {
                    MyLog.e("获取电子签署验证码异常======"+msg);
                    T.showShortBottom(msg);
                    ((BaseFragmentActivity)mContext).stopProgressDialog();
                }});
    }
}
