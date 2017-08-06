package com.sinosafe.xb.manager.module.home.weidai;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import com.sinosafe.xb.manager.APP;
import com.sinosafe.xb.manager.BaseMainActivity;
import com.sinosafe.xb.manager.R;
import com.sinosafe.xb.manager.api.rxjava.RxHttpBaseResultFunc;
import com.sinosafe.xb.manager.api.rxjava.RxSchedulersHelper;
import com.sinosafe.xb.manager.api.rxjava.RxSubscriber;
import com.sinosafe.xb.manager.bean.BaseEntity;
import com.sinosafe.xb.manager.bean.LoanUserInfo;
import com.sinosafe.xb.manager.model.ClientModel;
import com.sinosafe.xb.manager.utils.T;
import com.sinosafe.xb.manager.widget.edit.ClearEditText;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import luo.library.base.base.BaseFragmentActivity;
import luo.library.base.utils.IntentUtil;
import luo.library.base.utils.MyLog;

import static com.sinosafe.xb.manager.module.home.xiaofeidai.XiaoFeiDaiApplyActivity.loanUserInfo;

/**
 * 生意信息
 */
public class BusinessinfoActivity extends BaseFragmentActivity {


    @BindView(R.id.et_companyName)
    ClearEditText mEtCompanyName;
    @BindView(R.id.et_companyCertificates)
    ClearEditText mEtCompanyCertificates;
    @BindView(R.id.btn_next)
    Button mBtnNext;

    private LoanUserInfo.UserDetail userDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_info);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        setTitleText("补充资料");
        if(loanUserInfo.getUser_detail()!=null){
            userDetail = loanUserInfo.getUser_detail();
            mEtCompanyName.setText(userDetail.getBusin_name());
            mEtCompanyCertificates.setText(userDetail.getOper_no());
        }
    }

    @OnClick({R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //下一步
            case R.id.btn_next:
                if(isNull(mEtCompanyName)){
                    T.showShortBottom("请输入企业/生意名称");
                    return;
                }
                if(isNull(mEtCompanyCertificates)){
                    T.showShortBottom("统一社会信用代码");
                    return;
                }
                showWithStatus("保存中...");
                setSaveOrEditUserMap();
                break;
        }
    }


    private void setSaveOrEditUserMap(){

        Map<String,String> map = new HashMap<>();
        map.put("token", BaseMainActivity.loginUserBean.getToken());
        map.put("is_register","N");
        map.put("cus_id",userDetail.getUser_id());
        map.put("register_latitude", APP.lat+","+APP.lng);
        map.put("device_type","01");
        map.put("user_name",userDetail.getUser_name());
        map.put("mobile",userDetail.getMobile());
        map.put("cert_code",userDetail.getCert_code());
        map.put("detail_id",userDetail.getDetail_id());

        map.put("busin_name", mEtCompanyName.getText().toString());
        map.put("oper_no",mEtCompanyCertificates.getText().toString());

        saveOrEditUser(map);
    }

    /**
     * 保存或者编辑客户信息
     */
    private void saveOrEditUser(Map<String,String> map ){
        ClientModel.saveOrEditUser(map)
                .timeout(20, TimeUnit.SECONDS)
                .compose(RxSchedulersHelper.<BaseEntity>io_main())
                .map(new RxHttpBaseResultFunc())
                .subscribe(new RxSubscriber<BaseEntity>() {
                    @Override
                    public void _onNext(BaseEntity baseEntity) {
                        closeSVProgressHUD();
                        synLocalCustomerInfo();
                    }
                    @Override
                    public void _onError(String msg) {
                        closeSVProgressHUD();
                        MyLog.e("企业信息保存反馈====="+msg);
                        T.showShortBottom("保存失败");
                    }});
    }
    private void synLocalCustomerInfo(){

        userDetail.setOper_no(mEtCompanyCertificates.getText().toString());
        userDetail.setBusin_name(mEtCompanyName.getText().toString());
        mHandler.sendEmptyMessageDelayed(0,500);
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            IntentUtil.gotoActivity(BusinessinfoActivity.this,DataListActivity.class);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
