package com.sinosafe.xb.manager.module.home.shouliyewu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinosafe.xb.manager.BaseMainActivity;
import com.sinosafe.xb.manager.R;
import com.sinosafe.xb.manager.api.rxjava.RxHttpBaseResultFunc;
import com.sinosafe.xb.manager.api.rxjava.RxSchedulersHelper;
import com.sinosafe.xb.manager.api.rxjava.RxSubscriber;
import com.sinosafe.xb.manager.bean.BaseEntity;
import com.sinosafe.xb.manager.bean.YeWuBean;
import com.sinosafe.xb.manager.model.ClientModel;
import com.sinosafe.xb.manager.utils.Constant;
import com.sinosafe.xb.manager.utils.MyUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import luo.library.base.base.BaseFragmentActivity;
import luo.library.base.base.BaseImage;
import luo.library.base.utils.MyLog;
import luo.library.base.widget.MoneyView;
import luo.library.base.widget.dialog.DialogMessage;

/**
 * 客户详情
 */
public class CustomerDetailActivity extends BaseFragmentActivity {

    @BindView(R.id.ivPhoto)
    ImageView mIvPhoto;
    @BindView(R.id.tvDistance)
    TextView mTvDistance;
    @BindView(R.id.tvName)
    TextView mTvName;
    @BindView(R.id.tvPhone)
    TextView mTvPhone;
    @BindView(R.id.tvAddress)
    TextView mTvAddress;
    @BindView(R.id.tvProductName)
    TextView mTvProductName;
    @BindView(R.id.tvApplyDate)
    TextView mTvApplyDate;
    @BindView(R.id.tvApplyAccount)
    MoneyView mTvApplyAccount;
    @BindView(R.id.tvMoneyType)
    TextView mTvMoneyType;
    @BindView(R.id.tvInterestRates)
    TextView mTvInterestRates;
    @BindView(R.id.tvTermType)
    TextView mTvTermType;
    @BindView(R.id.tvApplyMonth)
    TextView mTvApplyMonth;
    @BindView(R.id.tvPayType)
    TextView mTvPayType;
    @BindView(R.id.tvGuaranteeType)
    TextView mTvGuaranteeType;
    @BindView(R.id.tvBankAccount)
    TextView mTvBankAccount;
    @BindView(R.id.btn_confirm_add)
    Button mBtnConfirmAdd;

    private YeWuBean yeWuBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_detail);

        ButterKnife.bind(this);

        initView();

    }

    private void initView() {

        setTitleText("客户详情");
        yeWuBean = (YeWuBean) getIntent().getSerializableExtra("yeWuBean");
        //位置、距离
        MyUtils.setDistance(mTvDistance,yeWuBean.getCREDIT_COORDINATE());
        MyUtils.regeocodeSearched(this,mTvAddress,yeWuBean.getCREDIT_COORDINATE());
        mTvName.setText(yeWuBean.getCUS_NAME());
        mTvPhone.setText(yeWuBean.getPHONE());
        YeWuBean.User user = yeWuBean.getUser();
        if(user!=null){
            BaseImage.getInstance().displayCricleImage(this,
                    user.getHead_photo(),mIvPhoto,R.mipmap.icon_default_avatar);
        }
        mTvProductName.setText(yeWuBean.getPRD_NAME());
        mTvApplyDate.setText(yeWuBean.getAPPLY_DATE());
        mTvApplyAccount.setMoneyText(MyUtils.keepTwoDecimal(yeWuBean.getAMOUNT()));
        mTvMoneyType.setText("人民币");
        mTvInterestRates.setText(yeWuBean.getUSING_IR()+"");
        mTvTermType.setText("月");
        mTvApplyMonth.setText(yeWuBean.getTERM()+"个月");

        mTvPayType.setText(MyUtils.getRepaymentModel(yeWuBean.getREPAYMENT_MODE()));
        mTvGuaranteeType.setText(MyUtils.getGuaranteeModel(yeWuBean.getASSURE_MEANS_MAIN()));
        mTvBankAccount.setText(yeWuBean.getBANK_CARD_NO());
    }

    @OnClick(R.id.btn_confirm_add)
    public void onViewClicked() {

        showBussinessAcceptDialog();
    }


    private void showBussinessAcceptDialog(){

        new DialogMessage(this).setMess("您确定要受理该贷款事项吗?")
                .setConfirmListener(new DialogMessage.OnConfirmListener() {
                    @Override
                    public void onConfirm() {

                        Map<String,String> map = new HashMap<>();
                        map.put("cus_mgr", BaseMainActivity.loginUserBean.getActorno());
                        map.put("token", BaseMainActivity.loginUserBean.getToken());
                        map.put("serno", yeWuBean.getSERNO());
                        showWithStatus("受理中...");
                        bussinessAccept(map);
                    }
                }).show();
    }

    /**
     * 受理待受理业务
     * @param map
     */
    private void bussinessAccept(Map<String,String> map){
        ClientModel.bussinessAccept(map)
            .timeout(20, TimeUnit.SECONDS)
            .compose(RxSchedulersHelper.<BaseEntity>io_main())
            .map(new RxHttpBaseResultFunc())
            .subscribe(new RxSubscriber<BaseEntity>() {
                @Override
                public void _onNext(BaseEntity entity) {
                    closeSVProgressHUD();
                    if(entity.getCode()==1){
                        bussinessAcceptSuccess();
                    }else{
                        MyLog.e("受理失败原因111111："+entity.getMsg());
                        showErrorWithStatus(entity.getMsg());
                    }
                }
                @Override
                public void _onError(String msg) {
                    closeSVProgressHUD();
                    showErrorWithStatus(msg);
                    MyLog.e("受理失败原因222222："+msg);
                }});
    }

    /**
     * 受理成功提示
     */
    private void bussinessAcceptSuccess(){

        DialogMessage messageDialog = new DialogMessage(this,
                new DialogMessage.OnConfirmListener() {
                    @Override
                    public void onConfirm() {
                        sendBroadcast(new Intent(Constant.REFRESH_HOME_PAGE));
                        setResult(RESULT_OK);
                        finish();
                    }
                }, 1);

        messageDialog.setMess("您已成功受理，请及时联系客户！");
        messageDialog.setConfirmTips("知道了");
        messageDialog.setCanceledOnTouchOutside(false);
        messageDialog.setCancelable(false);
        messageDialog.show();
    }
}
