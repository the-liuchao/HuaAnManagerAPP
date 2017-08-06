package com.sinosafe.xb.manager.module.mine;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.sinosafe.xb.manager.BaseMainActivity;
import com.sinosafe.xb.manager.R;
import com.sinosafe.xb.manager.api.rxjava.RxHttpResultFunc;
import com.sinosafe.xb.manager.api.rxjava.RxSchedulersHelper;
import com.sinosafe.xb.manager.api.rxjava.RxSubscriber;
import com.sinosafe.xb.manager.bean.BaseEntity;
import com.sinosafe.xb.manager.bean.LoginUserBean;
import com.sinosafe.xb.manager.model.ClientModel;
import com.sinosafe.xb.manager.utils.MyUtils;
import com.sinosafe.xb.manager.utils.T;
import com.sinosafe.xb.manager.widget.VerificationCodeButton;
import com.sinosafe.xb.manager.widget.edit.ClearEditText;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import luo.library.base.base.BaseFragmentActivity;

/**
 * 绑定手机号码
 * Created by john lee on 2017/5/10.
 */

public class BindPhoneActivity extends BaseFragmentActivity {


    @BindView(R.id.et_phone)
    ClearEditText mEtPhone;
    @BindView(R.id.et_code)
    ClearEditText mEtCode;
    @BindView(R.id.btn_get_code)
    VerificationCodeButton mBtnGetCode;
    @BindView(R.id.btn_confirm_bind)
    Button mBtnConfirmBind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bind_phone);
        ButterKnife.bind(this);
        initView();
    }

    protected void initView() {

        setTitleText("绑定手机号码");
        mBtnGetCode.setEtPhone(mEtPhone);

    }

    @OnClick(R.id.btn_confirm_bind)
    public void onViewClicked() {

        if(isNull(mEtPhone)){
            T.showShortBottom("请输入手机号");
            return;
        }
        if (!MyUtils.isPhone(mEtPhone.getText().toString())) {
            T.showShortBottom("手机号码格式错误");
            return;
        }

        if(isNull(mBtnGetCode.getSmsCode())){
            T.showShortBottom("请先获取验证码");
            return;
        }

        if(isNull(mEtCode)){
            T.showShortBottom("请输入验证码");
            return;
        }

        if(!mBtnGetCode.getSmsCode().equals(mEtCode.getText().toString())){
            T.showShortBottom("验证码错误");
            return;
        }

        showWithStatus("绑定中...");
        bindNewMobileNumber();

    }

    /**
     * 绑定新手机号码
     */
    private void bindNewMobileNumber() {

        String token = BaseMainActivity.loginUserBean.getToken();
        final String mobileStr = mEtPhone.getText().toString();
        String code = mBtnGetCode.getSmsCode();
        ClientModel.editManagerById(token,mobileStr,code,"","","")
                .timeout(20, TimeUnit.SECONDS)
                .compose(RxSchedulersHelper.<BaseEntity<LoginUserBean>>io_main())
                .map(new RxHttpResultFunc<LoginUserBean>())
                .subscribe(new RxSubscriber<LoginUserBean>() {
                    @Override
                    public void _onNext(LoginUserBean t) {
                        closeSVProgressHUD();
                        T.showShortBottom("绑定成功");
                        Intent intent = new Intent();
                        intent.putExtra("mobile",mobileStr);
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                    @Override
                    public void _onError(String msg) {
                        closeSVProgressHUD();
                        showErrorWithStatus(msg);
                    }});
    }
}
