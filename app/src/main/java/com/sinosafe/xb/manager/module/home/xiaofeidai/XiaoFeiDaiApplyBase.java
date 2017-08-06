package com.sinosafe.xb.manager.module.home.xiaofeidai;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.megvii.livenesslib.LivenessActivity;
import com.sinosafe.xb.manager.BaseMainActivity;
import com.sinosafe.xb.manager.R;
import com.sinosafe.xb.manager.api.APIManager;
import com.sinosafe.xb.manager.api.xutilshttp.OnResponseListener;
import com.sinosafe.xb.manager.api.xutilshttp.XutilsBaseHttp;
import com.sinosafe.xb.manager.bean.BankOwnerShip;
import com.sinosafe.xb.manager.bean.LoanUserInfo;
import com.sinosafe.xb.manager.module.home.xiaofeidai.bean.YaLianMengResult;
import com.sinosafe.xb.manager.utils.MyAMapLocUtils;
import com.sinosafe.xb.manager.utils.MyFaceNetWorkWarranty;
import com.sinosafe.xb.manager.utils.T;
import com.sinosafe.xb.manager.widget.DialogPowerOfAttorney;
import com.sinosafe.xb.manager.widget.edit.BandCardEditText;
import com.sinosafe.xb.manager.widget.edit.ClearEditText;

import org.xutils.http.RequestParams;

import butterknife.BindView;
import luo.library.base.base.BaseFragmentActivity;
import luo.library.base.utils.GsonUtil;
import luo.library.base.utils.IntentUtil;
import luo.library.base.utils.MyLog;
import luo.library.base.widget.dialog.DialogMessage;

/**
 * 类名称：   com.sinosafe.xb.manager.module.home.xiaofeidai
 * 内容摘要： //消费贷、微贷申请基类。
 * 修改备注：
 * 创建时间： 2017/7/27 0027
 * 公司：     深圳市华移科技股份有限公司
 * 作者：     Mr 张
 */
public abstract class XiaoFeiDaiApplyBase extends BaseFragmentActivity {

    public static String AGREEMENT_TIP = "同意<font color=\"#7380F3\">《用户协议》</font>";
    protected static final int INTO_IDCARDSCAN_PAGE = 100;
    protected static final int INTO_BANKCARDSCAN_PAGE = 200;
    protected static final int PAGE_INTO_LIVENESS = 300;
    protected static final int ELECTRONIC_SIGNA = 400;
    protected boolean isDebuge = false, isAllCard = true;
    protected static final String KEY_ISDEBUGE = "KEY_ISDEBUGE";
    protected static final String KEY_ISALLCARD = "KEY_ISALLCARD";

    @BindView(R.id.et_customerName)
    protected ClearEditText mEtCustomerName;
    @BindView(R.id.et_iDCardNumber)
    protected BandCardEditText mEtIDCardNumber;
    @BindView(R.id.iv_scan_idCard)
    protected ImageView mIvScanIdCard;
    @BindView(R.id.et_card_holder)
    protected ClearEditText mEtCardHolder;
    @BindView(R.id.et_card_number)
    protected BandCardEditText mEtCardNumber;
    @BindView(R.id.iv_scan_bankcard)
    protected ImageView mIvScanBankcard;
    @BindView(R.id.et_phone)
    protected ClearEditText mEtPhone;
    //@BindView(R.id.et_code)
    //protected ClearEditText mEtCode;
    //@BindView(R.id.btn_get_code)
    //protected VerificationCodeButton mBtnGetCode;
    @BindView(R.id.cb_agreement)
    protected CheckBox mCbAgreement;
    @BindView(R.id.tv_agreement)
    protected TextView mTvAgreement;
    @BindView(R.id.btn_confirm_add)
    protected Button mBtnConfirmAdd;

    //0：消费贷，1：微贷
    protected int type = -1;
    //贷款用户信息
    public static LoanUserInfo loanUserInfo;
    //消费贷产品ID,贷款期限，贷款金额，产品名字
    protected String prd_id,period,amount,prd_name;
    protected DialogPowerOfAttorney dialogPowerOfAttorney;
    //首次保存标记
    protected boolean saveCurrencyFlag = false;
    protected MyAMapLocUtils aMapLocUtils;
    protected MyFaceNetWorkWarranty myFaceNetWorkWarranty;

    // RSA 签名方式
    /*private String sign;
    private String retmsg = "";

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            closeSVProgressHUD();
            String strRet = (String) msg.obj;
            switch (msg.what) {
                case Constants.RQF_PAY:
                case Constants.RQF_SIGN: {
                    JSONObject objContent = BaseHelper.string2JSON(strRet);
                    String retCode = objContent.optString("ret_code");
                    String retMsg = objContent.optString("ret_msg");
                    // 成功
                    if (Constants.RET_CODE_SUCCESS.equals(retCode)) {
                        saveApplyInformation();
                    } else {
                        showCheckErorInfoDialog(retMsg);
                    }
                }
                break;

                case 10:
                    showCheckErorInfoDialog(retmsg);
                    break;
                case 11:
                    saveApplyInformation();
                    break;
            }
        }
    };*/

    protected abstract void saveApplyInformation();
    protected abstract void openIDCardScan();

    /**
     * 查询银行卡类型
     */
    protected void getCardNosBank(){

        RequestParams params = new RequestParams(APIManager.SINOSAFE_URL+"client/lianpay/getCardNosBank");
        params.addQueryStringParameter("token", BaseMainActivity.loginUserBean.getToken());
        params.addQueryStringParameter("card_no", mEtCardNumber.getBankCardText());
        XutilsBaseHttp.requestType = 10;
        XutilsBaseHttp.get(params, onResponseListener);
    }

    public void showBankOwnerShip(BankOwnerShip ship) {
        if (ship.getSTATUS() == 0) {
            BankOwnerShip.RESULT result = ship.getRESULT();
            if (!result.getRet_code().equals("5001")) {
                if (result.getCard_type().equals("2")) {
                    cardNosBankCheck();
                } else {
                    closeSVProgressHUD();
                    showCheckErorInfoDialog("只能选择储蓄卡");
                }
            } else {
                showCheckErorInfoDialog(result.getRet_msg());
                closeSVProgressHUD();
            }
        } else {
            T.showShortBottom("服务器开小差了");
            closeSVProgressHUD();
        }
    }

    /**//**
     * RSA加密
     *//*
    private void rsaSign() {
        RequestParams params = new RequestParams(APIManager.SINOSAFE_URL+"client/lianpay/paramRSAEncrypt");
        params.addQueryStringParameter("token", BaseMainActivity.loginUserBean.getToken());
        JSONObject signBean = BaseHelper.Object2JSON(constructCreateBillBean());
        params.addQueryStringParameter("param_json_str", signBean.toString());
        XutilsBaseHttp.requestType = -1;
        XutilsBaseHttp.post(params, onResponseListener);
    }

    *//**
     * 银行卡四要素验证
     *//*
    private void sign() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject signBean = BaseHelper.Object2JSON(constructCreateBillBean());
                JSONObject resultObj = HttpRequest.request(getApplicationContext(), signBean, Constants.CREATBILLURL);
                String retcode = resultObj.optString("ret_code", "");
                retmsg = resultObj.optString("ret_msg", "");
                MyLog.e("request: " + resultObj);
                if ("0000".equals(retcode)) {
                    MobileSecurePayer payer = new MobileSecurePayer();
//                            payer.setCAPTCHA_Switch(true);
                    payer.setCallbackHandler(mHandler, Constants.RQF_SIGN);
                    payer.setTestMode(false);
                    boolean bRet = payer.doTokenSign(resultObj, XiaoFeiDaiApplyBase.this);
                } else {
                    mHandler.sendEmptyMessage(10);
                }
            }
        }).start();
    }
    *//**
     * 封装请求参数
     *
     * @return
     *//*
    private EntireFactorBean constructCreateBillBean() {
        String timeString = getTime();
        EntireFactorBean bean = new EntireFactorBean();
        bean.setApi_version(EnvConstants.API_VERSION);//版本号
        bean.setSign_type(PayOrder.SIGN_TYPE_RSA);//加密方式
        bean.setTime_stamp(timeString);//时间戳
        bean.setFlag_chnl("0");//应用渠道
        bean.setOid_partner(EnvConstants.PARTNER);//商户编号
        bean.setUser_id(BaseMainActivity.loginUserBean.getToken());//商户用户唯一编号
        bean.setNo_order(timeString);//EnvConstants.PARTNER);//订单号
        bean.setSign(sign);//RSA加密签名
        bean.setAcct_name(mEtCustomerName.getText().toString());//银行账号姓名
        bean.setBind_mob(mEtPhone.getText().toString());//绑定手机号
        bean.setId_no(mEtIDCardNumber.getBankCardText());//证件号码
        bean.setId_type(EnvConstants.IDTYPE);//证件类型
        bean.setCard_no(mEtCardNumber.getBankCardText());//银行卡号
        bean.setFlag_pay_product("8");//分期付
        bean.setId_type("0");
        return bean;
    }

    *//**
     * 获取时间
     *
     * @return
     *//*
    private String getTime() {
        SimpleDateFormat dataFormat = new SimpleDateFormat(
                "yyyyMMddHHmmss");
        Date date = new Date();
        return dataFormat.format(date);
    }*/

    /**
     * 银行卡校验
     */
    protected void cardNosBankCheck(){

        RequestParams params = new RequestParams(APIManager.SINOSAFE_URL+"client/lianpay/ylmQuery");
        params.addQueryStringParameter("token", BaseMainActivity.loginUserBean.getToken());
        params.addQueryStringParameter("name", mEtCustomerName.getText().toString());
        params.addQueryStringParameter("idcard", mEtIDCardNumber.getBankCardText());
        params.addQueryStringParameter("bankcard", mEtCardNumber.getBankCardText());
        params.addQueryStringParameter("mobile", mEtPhone.getText().toString());

        XutilsBaseHttp.requestType = -1;
        XutilsBaseHttp.post(params, onResponseListener);
    }


    private OnResponseListener onResponseListener = new OnResponseListener() {
        @Override
        public void onRequestFailedCallback(String var1) {
            closeSVProgressHUD();
            T.showShortBottom("服务器开小差了.");
        }

        @Override
        public void onBankcardbinCallback(String result) {
            closeSVProgressHUD();
            BankOwnerShip bankOwnerShip = GsonUtil.GsonToBean(result,BankOwnerShip.class);
            showBankOwnerShip(bankOwnerShip);
        }

        @Override
        public void onCommonRequestCallback(String result) {
            MyLog.e("四要素校验======"+result);
            YaLianMengResult yaLianMengResult = GsonUtil.GsonToBean(result,YaLianMengResult.class);
            if("0000".equals(yaLianMengResult.getResult())){
                YaLianMengResult.Data data = yaLianMengResult.getData();
                if("0".equals(data.getCode())){
                    T.showShortBottom(data.getDesc());
                    saveApplyInformation();
                }else{
                    closeSVProgressHUD();
                    showCheckErorInfoDialog(data.getDesc()+"\n"+data.getOrg_desc());
                }
            }else{
                closeSVProgressHUD();
                showCheckErorInfoDialog(yaLianMengResult.getMsg());
            }
        }
    };




    public void showCheckErorInfoDialog(String errorInfo){

        new DialogMessage(this).setTitle("签约结果")
                .setMess(errorInfo).setConfirmTips("确定").setOtherConfirmTips("取消")
                .setConfirmListener(new DialogMessage.OnConfirmListener() {
                    @Override
                    public void onConfirm() {

                    }
                })
                .setOtherConfirmListener(new DialogMessage.OnOtherConfirmListener() {
                    @Override
                    public void onOtherConfirm() {

                    }
                }).show();
    }

    /**
     * 人脸识别授权失败提示
     */
    protected void showFaceNetWorkWarrantyDialog(){
        new DialogMessage(this).setMess("人脸识别联网授权失败，请重新授权!")
                .setConfirmListener(new DialogMessage.OnConfirmListener() {
                    @Override
                    public void onConfirm() {
                        faceNetGrant();
                    }
                }).show();
    }

    /**
     * 人脸识别联网授权
     */
    protected void faceNetGrant(){
        startProgressDialog("授权中...");
        myFaceNetWorkWarranty.faceNetWorkWarranty(new MyFaceNetWorkWarranty.NetWorkWarrantyCallback() {
            @Override
            public void grantResult(boolean result) {
                stopProgressDialog();
                if(result){
                    T.showShortBottom("联网授权成功");
                    IntentUtil.gotoActivityForResult(XiaoFeiDaiApplyBase.this, LivenessActivity.class,PAGE_INTO_LIVENESS);
                }else{
                    showFaceNetWorkWarrantyDialog();
                }
            }
        });
    }



    /**
     * 身份证授权失败提示
     */
    protected void showIdCardNetWorkWarrantyDialog(){
        new DialogMessage(this).setMess("身份证扫描联网授权失败，请重新授权!")
                .setConfirmListener(new DialogMessage.OnConfirmListener() {
                    @Override
                    public void onConfirm() {
                        idCardNetGrant();
                    }
                }).show();
    }

    /**
     * 身份证联网授权
     */
    protected void idCardNetGrant(){
        startProgressDialog("授权中...");
        myFaceNetWorkWarranty.idCardNetWorkWarranty(new MyFaceNetWorkWarranty.NetWorkWarrantyCallback() {
            @Override
            public void grantResult(boolean result) {
                stopProgressDialog();
                if(result){
                    T.showShortBottom("联网授权成功");
                    openIDCardScan();
                }else{
                    showIdCardNetWorkWarrantyDialog();
                }
            }
        });
    }
}
