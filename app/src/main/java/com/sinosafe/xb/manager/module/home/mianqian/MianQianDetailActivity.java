package com.sinosafe.xb.manager.module.home.mianqian;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.megvii.livenesslib.LivenessActivity;
import com.sinosafe.xb.manager.BaseMainActivity;
import com.sinosafe.xb.manager.R;
import com.sinosafe.xb.manager.api.rxjava.RxHttpBaseResultFunc;
import com.sinosafe.xb.manager.api.rxjava.RxSchedulersHelper;
import com.sinosafe.xb.manager.api.rxjava.RxSubscriber;
import com.sinosafe.xb.manager.api.xutilshttp.OnResponseListener;
import com.sinosafe.xb.manager.api.xutilshttp.XutilsBaseHttp;
import com.sinosafe.xb.manager.bean.BaseEntity;
import com.sinosafe.xb.manager.bean.VerifyResultBean;
import com.sinosafe.xb.manager.bean.YeWuBean;
import com.sinosafe.xb.manager.model.ClientModel;
import com.sinosafe.xb.manager.module.home.loanmanager.PaymentHistoryActivity;
import com.sinosafe.xb.manager.module.home.supplyInfo.ImageSelectActivity2;
import com.sinosafe.xb.manager.utils.MyFaceNetWorkWarranty;
import com.sinosafe.xb.manager.utils.MyUtils;
import com.sinosafe.xb.manager.utils.T;

import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import luo.library.base.base.BaseFragmentActivity;
import luo.library.base.base.BaseImage;
import luo.library.base.utils.IntentUtil;
import luo.library.base.widget.MoneyView;
import luo.library.base.widget.dialog.DialogInput;
import luo.library.base.widget.dialog.DialogMessage;
import pub.devrel.easypermissions.EasyPermissions;

import static com.sinosafe.xb.manager.R.id.btn_confirm_add;
import static luo.library.base.utils.GsonUtil.GsonToBean;


/**
 * 客户详情
 */
public class MianQianDetailActivity extends BaseFragmentActivity {

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
    @BindView(btn_confirm_add)
    Button mBtnConfirmAdd;
    @BindView(R.id.paymentHistoryLayout)
    LinearLayout paymentHistoryLayout;
    @BindView(R.id.btnTouBaodan)
    Button mBtnTouBaodan;
    @BindView(R.id.btnInterview)
    Button mBtnInterview;
    @BindView(R.id.buttonLayout)
    LinearLayout mButtonLayout;

    private YeWuBean yeWuBean;
    //事项类型0：默认；1：贷后管理
    private int type = 0;
    private DialogInput dialogInput;
    private MyFaceNetWorkWarranty myFaceNetWorkWarranty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_detail);

        ButterKnife.bind(this);

        initView();
    }

    private void initView() {

        setTitleText("客户详情");
        myFaceNetWorkWarranty = new MyFaceNetWorkWarranty();
        mBtnConfirmAdd.setText("办理面签");

        yeWuBean = (YeWuBean) getIntent().getSerializableExtra("yeWuBean");
        type = getIntent().getIntExtra("type", 0);
        mTvName.setText(yeWuBean.getCUS_NAME());
        mTvPhone.setText(Html.fromHtml("<u>"+yeWuBean.getPHONE()+"</u>"));
        YeWuBean.User user = yeWuBean.getUser();

        //待面签
        if("112".equals(yeWuBean.getNEW_APPROVE_STATUS())){
            mBtnConfirmAdd.setVisibility(View.GONE);
            mButtonLayout.setVisibility(View.VISIBLE);
        }

        if (user != null) {
            BaseImage.getInstance().displayCricleImage(this,
                    user.getHead_photo(), mIvPhoto, R.mipmap.icon_default_avatar);
        }
        //面签位置、距离
        MyUtils.setDistance(mTvDistance, yeWuBean.getCREDIT_COORDINATE());
        MyUtils.regeocodeSearched(this, mTvAddress, yeWuBean.getCREDIT_COORDINATE());

        mTvProductName.setText(yeWuBean.getPRD_NAME());
        mTvApplyDate.setText(yeWuBean.getAPPLY_DATE());
        mTvApplyAccount.setMoneyText(MyUtils.keepTwoDecimal(yeWuBean.getAMOUNT()));
        mTvMoneyType.setText("人民币");
        mTvInterestRates.setText(yeWuBean.getUSING_IR() + "");
        mTvTermType.setText("月");
        mTvApplyMonth.setText(yeWuBean.getTERM() + "个月");

        mTvPayType.setText(MyUtils.getRepaymentModel(yeWuBean.getREPAYMENT_MODE()));
        mTvGuaranteeType.setText(MyUtils.getGuaranteeModel(yeWuBean.getASSURE_MEANS_MAIN()));
        mTvBankAccount.setText(yeWuBean.getBANK_CARD_NO());

        if (type == 1) {
            mBtnConfirmAdd.setText("收集贷后凭证");
            paymentHistoryLayout.setVisibility(View.VISIBLE);
        }
        //paymentHistoryLayout.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.btn_confirm_add, R.id.paymentHistoryLayout,
            R.id.btnTouBaodan, R.id.btnInterview,R.id.tvPhone})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm_add:

                nextOperator();
                break;

            case R.id.paymentHistoryLayout:
                Bundle bundle = new Bundle();
                bundle.putString("serno", yeWuBean.getSERNO());
                bundle.putString("prd_id", yeWuBean.getPRD_ID());
                bundle.putString("payType", yeWuBean.getREPAYMENT_MODE());
                IntentUtil.gotoActivityForResult(this, PaymentHistoryActivity.class, PAGE_INTO_LIVENESS);
                break;
            //打印投保单
            case R.id.btnTouBaodan:
                setCommonEmail();
                break;
            //办理面签
            case R.id.btnInterview:
                nextOperator();
                break;

            //拨打电话
            case R.id.tvPhone:
                callPhoneTips(yeWuBean.getPHONE());
                break;
        }
    }

    //启动面签
    private void nextOperator() {
        if (type == 0) {

            //判断是否授权成功
            if(!MyFaceNetWorkWarranty.isFaceNetWorkWarranty()){
                showFaceNetWorkWarrantyDialog();
                return;
            }

            if (!EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
                EasyPermissions.requestPermissions(this, "", 0x03, Manifest.permission.CAMERA);
                return;
            }
            //mHandler.sendEmptyMessageDelayed(0, 100);
            IntentUtil.gotoActivityForResult(this, LivenessActivity.class,PAGE_INTO_LIVENESS);
        }
        //贷后管理
        else if (type == 1) {
            Bundle bundle = new Bundle();
            bundle.putInt("type", 1);
            bundle.putString("title", "收集贷后凭证");
            bundle.putString("prdType", yeWuBean.getPRD_TYPE());
            bundle.putString("serno", yeWuBean.getSERNO());
            IntentUtil.gotoActivityForResult(this, ImageSelectActivity2.class, bundle, 10000);
        }
    }

    /**
     * 授权失败提示
     */
    private void showFaceNetWorkWarrantyDialog(){
        new DialogMessage(this).setMess("人脸识别联网授权失败，请重新授权!")
                .setConfirmListener(new DialogMessage.OnConfirmListener() {
                    @Override
                    public void onConfirm() {
                        faceNetGrant();
                    }
                }).show();
    }

    /**
     * 联网授权
     */
    private void faceNetGrant(){
        startProgressDialog("授权中...");
        myFaceNetWorkWarranty.faceNetWorkWarranty(new MyFaceNetWorkWarranty.NetWorkWarrantyCallback() {
            @Override
            public void grantResult(boolean result) {
                stopProgressDialog();
                if(result){
                    nextOperator();
                }else{
                    showFaceNetWorkWarrantyDialog();
                }
            }
        });
    }

    private static final int PAGE_INTO_LIVENESS = 200;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PAGE_INTO_LIVENESS && resultCode == RESULT_OK) {
            String result = data.getStringExtra("result");
            showWithStatus("请稍候");
            faceResult(result);
        }

        //收集完贷后凭证回来
        else if (requestCode == 10000 && resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            finish();
        }
    }


    /**
     * 人脸识别结果处理
     *
     * @param jsonResult
     */
    private void faceResult(String jsonResult) {
        try {
            JSONObject result = new JSONObject(jsonResult);
            int resID = result.getInt("resultcode");
            String filePath = result.getString("filePath");
            String delta = result.getString("delta");

            if (resID == R.string.verify_success) {
                File file = new File(filePath);
                if (file == null && !file.exists()) {
                    closeSVProgressHUD();
                    T.showShortBottom("人脸识别失败");
                    return;
                }
                RequestParams params = new RequestParams("https://api.megvii.com/faceid/v2/verify");
                params.addBodyParameter("api_key", "InKdhB0QTKRbUXKDZgYBkyxs2TssltsN");
                params.addBodyParameter("api_secret", "7z4JdvTKRrj82oLRJ0ov_LFx1cWTS4kJ");
                params.addBodyParameter("idcard_name", yeWuBean.getCUS_NAME());
                params.addBodyParameter("idcard_number", yeWuBean.getCERT_CODE());
                params.addBodyParameter("comparison_type", "1");
                params.addBodyParameter("face_image_type", "meglive");
                params.addBodyParameter("delta", delta);
                params.addBodyParameter("image_best", file);
                XutilsBaseHttp.requestType = 1;
                XutilsBaseHttp.post(params, onResponseListener);
            } else if (resID == R.string.liveness_detection_failed_not_video ||
                    resID == R.string.liveness_detection_failed_timeout ||
                    resID == R.string.liveness_detection_failed) {
                T.showShortBottom(result.getString("result"));
            }

        } catch (Exception e) {
            T.showShortBottom("人脸识别失败");
            closeSVProgressHUD();
            e.printStackTrace();
        }
    }

    private OnResponseListener onResponseListener = new OnResponseListener() {
        @Override
        public void onRequestFailedCallback(String var1) {
            closeSVProgressHUD();
            T.showShortBottom("人脸识别失败");
            //mHandler.sendEmptyMessageDelayed(0,400);
        }

        @Override
        public void onFaceSuccessListCallback(String result) {
            closeSVProgressHUD();
            VerifyResultBean verifyResultBean = GsonToBean(result, VerifyResultBean.class);
            double e_6 = 79.9;
            double confidence = verifyResultBean.getResult_faceid().getConfidence();
            if (confidence >= e_6) {
                T.showShortBottom("人脸识别成功");
                mHandler.sendEmptyMessageDelayed(0, 400);
            } else {
                T.showShortBottom("人脸识别失败");
                //mHandler.sendEmptyMessageDelayed(0,400);
            }
        }
    };

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Bundle bundle = new Bundle();
            bundle.putSerializable("yeWuBean", yeWuBean);
            IntentUtil.gotoActivity(MianQianDetailActivity.this, BanLiMianQianActivity.class, bundle);
        }
    };


    /**
     * 设置常用邮箱
     */
    private void setCommonEmail() {
        dialogInput = new DialogInput(this, new DialogInput.OnTextBackListener() {
            @Override
            public void onTextBack(String text) {
                if (!MyUtils.isEmail(text)) {
                    T.showShortBottom("请输入常用邮箱");
                    return;
                }
                dialogInput.dismiss();
                startProgressDialog("发送中...");
                printApplicationForm(text);

            }
        });
        dialogInput.setEditText(BaseMainActivity.loginUserBean.getUsermail());
        dialogInput.setTitle("打印投保单").setTextInputType().
                setHint("请输入常用邮箱").show();
    }


    /**
     * 打印投保单
     */
    private void printApplicationForm(String emailStr) {

        Map<String, String> map = new HashMap<>();
        map.put("token", BaseMainActivity.loginUserBean.getToken());
        map.put("serno", yeWuBean.getSERNO());
        map.put("email", emailStr);
        map.put("cert_code", yeWuBean.getCERT_CODE());
        map.put("username", yeWuBean.getCUS_NAME());
        map.put("device_type", "01");
        ClientModel.sendInsuranceEmail(map)
                .timeout(20, TimeUnit.SECONDS)
                .compose(RxSchedulersHelper.<BaseEntity>io_main())
                .map(new RxHttpBaseResultFunc())
                .subscribe(new EmailRxSubscriber());
    }


    class EmailRxSubscriber extends RxSubscriber<BaseEntity> {
        @Override
        public void _onNext(BaseEntity entity) {
            stopProgressDialog();
            if (entity.getCode() == 1) {
                T.showShortBottom(entity.getMsg());
                //getActivity().setResult(RESULT_OK);
                //getActivity().finish();
            } else {
                showErrorWithStatus(entity.getMsg());
            }
        }

        @Override
        public void _onError(String msg) {
            stopProgressDialog();
            showErrorWithStatus(msg);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myFaceNetWorkWarranty.myDestory();
        mHandler.removeCallbacksAndMessages(null);
    }
}
