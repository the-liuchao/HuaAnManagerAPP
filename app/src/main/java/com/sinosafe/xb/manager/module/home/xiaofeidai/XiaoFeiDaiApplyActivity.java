package com.sinosafe.xb.manager.module.home.xiaofeidai;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.megvii.demo.BankCardScanActivity;
import com.megvii.demo.util.Util;
import com.megvii.idcardlib.IDCardScanActivity;
import com.megvii.livenesslib.LivenessActivity;
import com.megvii.livenesslib.help.CodeHelp;
import com.sinosafe.xb.manager.APP;
import com.sinosafe.xb.manager.BaseMainActivity;
import com.sinosafe.xb.manager.R;
import com.sinosafe.xb.manager.api.rxjava.RxHttpBaseResultFunc;
import com.sinosafe.xb.manager.api.rxjava.RxHttpResultFunc;
import com.sinosafe.xb.manager.api.rxjava.RxSchedulersHelper;
import com.sinosafe.xb.manager.api.rxjava.RxSubscriber;
import com.sinosafe.xb.manager.api.xutilshttp.OnResponseListener;
import com.sinosafe.xb.manager.api.xutilshttp.XutilsBaseHttp;
import com.sinosafe.xb.manager.bean.BaseEntity;
import com.sinosafe.xb.manager.bean.LoanUserInfo;
import com.sinosafe.xb.manager.bean.OcridcardBean;
import com.sinosafe.xb.manager.bean.VerifyResultBean;
import com.sinosafe.xb.manager.model.ClientModel;
import com.sinosafe.xb.manager.module.home.weidai.FamilyInfoActivity;
import com.sinosafe.xb.manager.module.home.xiaofeidai.supplementInfo.PersonInfoActivity;
import com.sinosafe.xb.manager.utils.FileUtil;
import com.sinosafe.xb.manager.utils.MyAMapLocUtils;
import com.sinosafe.xb.manager.utils.MyFaceNetWorkWarranty;
import com.sinosafe.xb.manager.utils.MyUtils;
import com.sinosafe.xb.manager.utils.T;
import com.sinosafe.xb.manager.widget.DialogPowerOfAttorney;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.OnClick;
import luo.library.base.utils.GsonUtil;
import luo.library.base.utils.IDCardUtil;
import luo.library.base.utils.IntentUtil;
import luo.library.base.utils.MyEasyPermissions;
import luo.library.base.utils.MyLog;
import luo.library.base.utils.Result;
import luo.library.base.widget.SimpleTextWatcher;
import luo.library.base.widget.WebViewActivity;
import luo.library.base.widget.dialog.DialogMessage;
import pub.devrel.easypermissions.EasyPermissions;

import static com.sinosafe.xb.manager.widget.ElectronicSignatureView.SIGNATURE_NAME;
import static luo.library.base.utils.GsonUtil.GsonToBean;


/**
 * 消费贷/微贷申请
 */

public class XiaoFeiDaiApplyActivity extends XiaoFeiDaiApplyBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiaofeidai_apply);
        ButterKnife.bind(this);

        initView();
    }

    /**
     * 初始化
     */
    private void initView() {

        type = getIntent().getIntExtra("type",-1);
        prd_id = getIntent().getStringExtra("prd_id");
        period = getIntent().getStringExtra("period");
        amount = getIntent().getStringExtra("amount");
        prd_name = getIntent().getStringExtra("prd_name");

        if(type==0)
            setTitleText("消费贷申请");
        else{
            setTitleText("微贷申请");
        }
        MyUtils.setHtml(mTvAgreement,AGREEMENT_TIP);
        //mBtnGetCode.setEtPhone(mEtPhone);
        mEtIDCardNumber.addTextChangedListener(textWatcher);
        //删除历史电子签名文件
        FileUtil.deleteImage(this,SIGNATURE_NAME);

        myFaceNetWorkWarranty = new MyFaceNetWorkWarranty();
        //定位
        aMapLocUtils = new MyAMapLocUtils(this);
        showWithStatus("");
        aMapLocUtils.startLocation();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //银行卡扫码回来
        if (requestCode == INTO_BANKCARDSCAN_PAGE && resultCode == RESULT_OK) {
            String bankNum = data.getStringExtra("bankNum");
            mEtCardNumber.setText(bankNum);
        }
        //身份证扫码回来
        else if(requestCode == INTO_IDCARDSCAN_PAGE && resultCode == RESULT_OK){

            showWithStatus("请稍后");
            int side = data.getIntExtra("side", 0);
            byte[] idCardImgData = data.getByteArrayExtra("idcardImg");
            //Bitmap idCardBmp = BitmapFactory.decodeByteArray(idCardImgData, 0, idCardImgData.length);
            String filePath = CodeHelp.saveJPGFile(this, idCardImgData, "IDCardPositive");
            uploadImage(filePath);

        }
        //人脸识别回来
        else if (requestCode == PAGE_INTO_LIVENESS && resultCode == RESULT_OK) {
            showWithStatus("请稍后");
            String result = data.getStringExtra("result");
            faceResult(result);
        }
        //电子签名回来
        else if(requestCode == ELECTRONIC_SIGNA && resultCode == RESULT_OK){
            dialogPowerOfAttorney.showSignatureImage();
        }
    }


    @OnClick({R.id.iv_scan_idCard, R.id.iv_scan_bankcard, R.id.btn_confirm_add,R.id.tv_agreement})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            //身份证扫描
            case R.id.iv_scan_idCard:

                if(!MyFaceNetWorkWarranty.isIdCardNetWorkWarranty()){
                    showIdCardNetWorkWarrantyDialog();
                    return;
                }
                if (!EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
                    EasyPermissions.requestPermissions(this, "", RC_CAMERA_PERM, Manifest.permission.CAMERA);
                    return;
                }
                openIDCardScan(0);
                break;
            //扫码银行卡
            case R.id.iv_scan_bankcard:
                if (!EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
                    EasyPermissions.requestPermissions(this, "", RC_CAMERA_PERM, Manifest.permission.CAMERA);
                    return;
                }
                Intent intent = new Intent(this,
                        BankCardScanActivity.class);
                intent.putExtra(Util.KEY_ISDEBUGE, isDebuge);
                intent.putExtra(Util.KEY_ISALLCARD, isAllCard);
                startActivityForResult(intent, INTO_BANKCARDSCAN_PAGE);
                break;
            //下一步
            case R.id.btn_confirm_add:
                //首次申请保存客户信息
                if(!check())
                    return;
                if(saveCurrencyFlag) {

                    //判断是否授权成功
                    if(!MyFaceNetWorkWarranty.isFaceNetWorkWarranty()){
                        showFaceNetWorkWarrantyDialog();
                        return;
                    }

                    if (!MyEasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
                        MyLog.e("没有相机权限，需要相机权限");
                        EasyPermissions.requestPermissions(this ,"需要相机权限", 0x03, Manifest.permission.CAMERA);
                        return;
                    }
                    IntentUtil.gotoActivityForResult(this, LivenessActivity.class, PAGE_INTO_LIVENESS);
                    //showSignatureAuthorization();
                }
                else{
                    //首次保存
                    showWithStatus("正在验证...");
                    //getCardNosBank();
                    getCardNosBank();
                    //setSaveCurrencyMap();
                }
                break;

            //用户协议
            case R.id.tv_agreement:

                Bundle bundle = new Bundle();
                bundle.putString("html","file:///android_asset/userAgreement.html");
                bundle.putInt("type",1);
                bundle.putString("title","用户协议");
                IntentUtil.gotoActivity(this,WebViewActivity.class,bundle);
                break;
        }
    }

    /**
     * 检验用户输入信息情况
     */
    private boolean check(){

        if(isNull(mEtCustomerName)){
            T.showShortBottom("请输入客户姓名");
            return false;
        }
        if(isNull(mEtIDCardNumber)){
            T.showShortBottom("请输入身份证号码");
            return false;
        }
       /* if(isNull(mEtCardHolder)){
            T.showShortBottom("请输入持卡人姓名");
            return false;
        }*/
        if(isNull(mEtCardNumber)){
            T.showShortBottom("请输入银行卡号");
            return false;
        }
        if(isNull(mEtPhone)){
            T.showShortBottom("请输入银行卡预留手机号码");
            return false;
        }

       /* if(mBtnGetCode.getSmsCode()==null){
            T.showShortBottom("请先获取验证码");
            return false;
        }*/
        /*if(isNull(mEtCode)){
            T.showShortBottom("请输入验证码");
            return false;
        }*/
        /*if(!mBtnGetCode.getSmsCode().equals(mEtCode.getText().toString())){
            T.showShortBottom("验证码错误");
            return false;
        }*/

        if(!mCbAgreement.isChecked()){
            T.showShortBottom("请同意用户协议");
            return false;
        }

        return true;
    }

    /**
     * 打开身份证识别界面
     *
     * @param side
     */
    private void openIDCardScan(int side) {
        Intent intent = new Intent(this, IDCardScanActivity.class);
        intent.putExtra("side", side);
        intent.putExtra("isvertical", false);
        startActivityForResult(intent, INTO_IDCARDSCAN_PAGE);
    }

    /**
     * 验证身份证正面 返回身份证姓名和号码
     */
    private void uploadImage(String filePath) {
        File file = new File(filePath);
        if (file == null && !file.exists()) return;

        RequestParams params = new RequestParams("https://api.megvii.com/faceid/v1/ocridcard");
        params.addBodyParameter("api_key", "InKdhB0QTKRbUXKDZgYBkyxs2TssltsN");
        params.addBodyParameter("api_secret", "7z4JdvTKRrj82oLRJ0ov_LFx1cWTS4kJ");
        params.addBodyParameter("image",  file);
        XutilsBaseHttp.requestType = 0;
        XutilsBaseHttp.post(params, onResponseListener);
    }


    /**
     * 人脸识别结果处理
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
                params.addBodyParameter("idcard_name",  mEtCustomerName.getText().toString());
                params.addBodyParameter("idcard_number",  mEtIDCardNumber.getBankCardText());
                params.addBodyParameter("comparison_type",  "1");
                params.addBodyParameter("face_image_type",  "meglive");
                params.addBodyParameter("delta",  delta);
                params.addBodyParameter("image_best",  file);
                XutilsBaseHttp.requestType = 1;
                XutilsBaseHttp.post(params, onResponseListener);
            } else if (resID == R.string.liveness_detection_failed_not_video ||
                    resID == R.string.liveness_detection_failed_timeout ||
                    resID == R.string.liveness_detection_failed) {
                T.showShortBottom(result.getString("result"));
            }

        } catch (Exception e) {
            closeSVProgressHUD();
            T.showShortBottom("人脸识别失败");
            e.printStackTrace();
        }
    }


    private OnResponseListener onResponseListener = new OnResponseListener() {
        @Override
        public void onRequestFailedCallback(String var1) {
            closeSVProgressHUD();
            MyLog.e("身份证、人脸识别失败======"+var1);
            if(XutilsBaseHttp.requestType ==0){
                T.showShortBottom("识别身份证失败");
            }
            else if(XutilsBaseHttp.requestType ==1){
                T.showShortBottom("人脸识别失败");
            }
        }

        @Override
        public void onIDCardSuccessListCallback(String result) {
            OcridcardBean ocridcardBean = GsonToBean(result, OcridcardBean.class);
            mEtIDCardNumber.setText(ocridcardBean.getId_card_number());
            mEtCustomerName.setText(ocridcardBean.getName());
            closeSVProgressHUD();
        }

        @Override
        public void onFaceSuccessListCallback(String result) {
            closeSVProgressHUD();
            VerifyResultBean verifyResultBean = GsonUtil.GsonToBean(result, VerifyResultBean.class);
            double e_6 = 79.9;
            double confidence = verifyResultBean.getResult_faceid().getConfidence();
            if (confidence >= e_6) {
                T.showShortBottom("人脸识别成功");
                showSignatureAuthorization();
            } else {
                T.showShortBottom("人脸识别失败");
            }
        }
    };


    /**
     * 电子签名
     */
    private void showSignatureAuthorization(){

        dialogPowerOfAttorney = new DialogPowerOfAttorney(this, new DialogPowerOfAttorney.OnSignatureListener() {
            //电子签名
            @Override
            public void onSignature() {
                IntentUtil.gotoActivityForResult(XiaoFeiDaiApplyActivity.this,
                        ElectronicSignatureActivity.class,ELECTRONIC_SIGNA);
            }
        }, new DialogPowerOfAttorney.OnConfirmListener() {
            //同意
            @Override
            public void onConfirm() {
                if(!locationFail())
                    return;
                /*if(FileUtil.getBitmap(XiaoFeiDaiApplyActivity.this,SIGNATURE_NAME)==null){
                    T.showShortBottom("请先进行电子签署");
                    return;
                }*/
                setElectronicSignatureMap();
            }
        }, new DialogPowerOfAttorney.OnRefuseListener() {
            //不同意
            @Override
            public void refuse() {
                dialogPowerOfAttorney.destroyWebView();
                finish();
            }
        },0);
        dialogPowerOfAttorney.show();
    }


    /**
     * 定位失败情况
     * @return
     */
    private boolean locationFail(){
        if(APP.lat == 0 || APP.lng == 0){
            new DialogMessage(this).setMess("亲,定位失败了~请重新定位.")
                    .setConfirmListener(new DialogMessage.OnConfirmListener() {
                        @Override
                        public void onConfirm() {
                            showWithStatus("");
                            aMapLocUtils.startLocation();
                        }
                    }).show();
            return false;
        }
        return true;
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    //消费贷
                    if(type==0) {
                        IntentUtil.gotoActivity(XiaoFeiDaiApplyActivity.this, PersonInfoActivity.class);
                    }
                    //微贷
                    else{
                        IntentUtil.gotoActivity(XiaoFeiDaiApplyActivity.this, FamilyInfoActivity.class);
                    }
                    break;

                //人脸识别
                case 1:
                    //判断是否授权成功
                    if(!MyFaceNetWorkWarranty.isFaceNetWorkWarranty()){
                        showFaceNetWorkWarrantyDialog();
                        return;
                    }
                    if (!MyEasyPermissions.hasPermissions(XiaoFeiDaiApplyActivity.this, Manifest.permission.CAMERA)) {
                        MyLog.e("没有相机权限，需要相机权限");
                        EasyPermissions.requestPermissions(XiaoFeiDaiApplyActivity.this ,"需要相机权限", 0x03, Manifest.permission.CAMERA);
                        return;
                    }
                    IntentUtil.gotoActivityForResult(XiaoFeiDaiApplyActivity.this, LivenessActivity.class,PAGE_INTO_LIVENESS);
                    break;

                case 2:
                    enter = false;
                    break;
            }
        }
    };

    /**
     * EditView输入监听
     */
    TextWatcher textWatcher = new SimpleTextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {
            String text = mEtIDCardNumber.getBankCardText();
            if(text.length()==15||text.length()==18)
                checkIdCardAndRequestCustomerInfo();
        }
    };


    boolean enter = false;
    private void checkIdCardAndRequestCustomerInfo(){
        String text = mEtIDCardNumber.getBankCardText();
        Result result = IDCardUtil.validateIDNum(text);
        if(result.isLegal()){
            if(!enter){
                enter = true;
                mHandler.sendEmptyMessageDelayed(2,2000);
            }else {
                return;
            }
            showWithStatus("");
            queryUserByCertCode(text.trim());
        }
        else{
            T.showShortBottom("身份证号码不正确");
        }
    }


    /**
     * 通过身份证号查询客户信息
     */
    private void queryUserByCertCode(String idCardNo){

        String token = BaseMainActivity.loginUserBean.getToken();
        ClientModel.queryUserByCertCode(token,idCardNo,prd_id)
            .timeout(20, TimeUnit.SECONDS)
            .compose(RxSchedulersHelper.<BaseEntity<LoanUserInfo>>io_main())
            .map(new RxHttpResultFunc<LoanUserInfo>())
            .subscribe(new RxSubscriber<LoanUserInfo>() {
                @Override
                public void _onNext(LoanUserInfo loanUserInfo) {
                    closeSVProgressHUD();
                    XiaoFeiDaiApplyActivity.this.loanUserInfo = loanUserInfo;
                    LoanUserInfo.UserDetail userDetail = loanUserInfo.getUser_detail();
                    mEtCustomerName.setText(userDetail.getUser_name());
                    mEtPhone.setText(userDetail.getMobile());
                    mEtCardHolder.setText(userDetail.getCard_belongs());
                    mEtCardNumber.setText(userDetail.getCard_code());
                    //mEtCode.setText("");
                    if(type==0)
                        XiaoFeiDaiApplyActivity.this.loanUserInfo.setPrdType("0");
                    else{
                        XiaoFeiDaiApplyActivity.this.loanUserInfo.setPrdType("2");
                    }
                    userDetail.setCert_code(mEtIDCardNumber.getBankCardText());
                    queryAuthByCertCode();
                }
                @Override
                public void _onError(String msg) {
                    //T.showShortBottom(msg);
                    closeSVProgressHUD();
                }});
    }




    /**
     * 查询产品是否需要授权
     */
    private void queryAuthByCertCode(){
        Map<String,String> map = new HashMap<>();
        map.put("token", BaseMainActivity.loginUserBean.getToken());
        map.put("prd_id",prd_id);
        map.put("cert_code",mEtIDCardNumber.getBankCardText());
        ClientModel.queryAuthByCertCode(map)
        .timeout(30*2, TimeUnit.SECONDS)
        .compose(RxSchedulersHelper.<BaseEntity>io_main())
        .map(new RxHttpBaseResultFunc())
        .subscribe(new RxSubscriber<BaseEntity>() {
            @Override
            public void _onNext(BaseEntity entity) {
                handleApplyAuth(entity.getResult().toString());
            }
            @Override
            public void _onError(String msg) {
                MyLog.e("获取是否需要授权_onError===="+msg);
            }});
    }

    //判断是否需要授权申请
    private void handleApplyAuth(String resultStr){

        try{
            MyLog.e("获取是否需要授权_onNext===="+resultStr);
            JSONObject jsonObject = new JSONObject(resultStr);
            boolean isGjjAuth = jsonObject.getBoolean("isGjjAuth");
            boolean isSheBaoAuth = jsonObject.getBoolean("isSheBaoAuth");
            if(loanUserInfo!=null){
                loanUserInfo.setGjjAuth(isGjjAuth);
                loanUserInfo.setSheBaoAuth(isSheBaoAuth);
            }
        }catch (Exception e){}
    }


    /**
     * 设置电子签署请求参数
     */
    private void setElectronicSignatureMap(){
        startProgressDialog("保存中...");
        Map<String,String> map = new HashMap<>();
        map.put("token",BaseMainActivity.loginUserBean.getToken());
        if(loanUserInfo!=null&&loanUserInfo.getUser_detail()!=null){
            map.put("user_id",loanUserInfo.getUser_detail().getUser_id());
        }
        map.put("terminal_type","01");
        //map.put("personSealData",MyUtils.getPersonSealData(this));
        map.put("code",dialogPowerOfAttorney.getUserInputSmsCode());
        map.put("prd_type",(type==0?0:2)+"");
        map.put("text_type","0");
        map.put("prd_id",prd_id);
        map.put("period",period);
        map.put("serno",loanUserInfo.getUser_detail().getSerno());
        map.put("amount",(Double.valueOf(amount)*10000)+"");

        electronicSignature(map);
    }

    /**
     * 保存电子授权书
     * @param map
     */
    private void electronicSignature(Map<String,String> map ){
        ClientModel.electronicSignature(map)
            .timeout(20, TimeUnit.SECONDS)
            .compose(RxSchedulersHelper.<BaseEntity>io_main())
            .map(new RxHttpBaseResultFunc())
            .subscribe(new RxSubscriber<BaseEntity>() {
                @Override
                public void _onNext(BaseEntity baseEntity) {
                    stopProgressDialog();
                    if(baseEntity.getCode()==1){
                        dialogPowerOfAttorney.destroyWebView();
                        dialogPowerOfAttorney.dismiss();
                        T.showShortBottom(baseEntity.getMsg());
                        try {
                            JSONObject object = new JSONObject(baseEntity.getResult().toString());
                            loanUserInfo.getUser_detail().setSerno(object.getString("serno"));
                            mHandler.sendEmptyMessageDelayed(0,300);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else{
                        T.showShortBottom(baseEntity.getMsg());
                    }
                }
                @Override
                public void _onError(String msg) {
                    stopProgressDialog();
                    MyLog.e("保存电子授权书信息反馈："+msg);
                    T.showShortBottom(msg);
                }});
    }

    @Override
    protected void saveApplyInformation(){
        showWithStatus("保存中...");
        setSaveCurrencyMap();
    }

    @Override
    protected void openIDCardScan() {
        if (!EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
            EasyPermissions.requestPermissions(this, "", RC_CAMERA_PERM, Manifest.permission.CAMERA);
            return;
        }
        openIDCardScan(0);
    }


    private void setSaveCurrencyMap(){
        Map<String,String> map = new HashMap<>();
        map.put("token",BaseMainActivity.loginUserBean.getToken());
        map.put("cert_code",mEtIDCardNumber.getBankCardText());
        map.put("user_name",mEtCustomerName.getText().toString());
        //map.put("card_name",mEtCardHolder.getText().toString());
        map.put("mobile",mEtPhone.getText().toString());
        //map.put("code",mEtCode.getText().toString());
        map.put("card_no",mEtCardNumber.getBankCardText());
        map.put("terminal_type","01");
        map.put("register_latitude", APP.lat+","+APP.lng);

        if(loanUserInfo!=null&&loanUserInfo.getUser_detail()!=null){
            map.put("cus_id",loanUserInfo.getUser_detail().getUser_id());
            map.put("card_id",loanUserInfo.getUser_detail().getCard_id()+"");
        }
        saveCurrency(map);
    }

    /**
     * 贷款首次申请保存用户
     */
    private void saveCurrency(Map<String,String> map ){
        ClientModel.saveCurrency(map)
            .timeout(20, TimeUnit.SECONDS)
            .compose(RxSchedulersHelper.<BaseEntity>io_main())
            .map(new RxHttpBaseResultFunc())
            .subscribe(new RxSubscriber<BaseEntity>() {
                @Override
                public void _onNext(BaseEntity baseEntity) {
                    if(baseEntity.getCode()==1){
                        handleSaveCurrencyResult(baseEntity);
                    }
                   else{
                        T.showShortBottom(baseEntity.getMsg());
                    }
                    closeSVProgressHUD();
                }
                @Override
                public void _onError(String msg) {
                    closeSVProgressHUD();
                    MyLog.e("首次保存用户信息反馈："+msg);
                    T.showShortBottom(msg);
                }});
    }

    /**
     * 处理首次保存返回的结果
     * @param baseEntity
     */
    private void handleSaveCurrencyResult(BaseEntity baseEntity) {

        LoanUserInfo.UserDetail userDetail;
        if(XiaoFeiDaiApplyActivity.this.loanUserInfo==null) {
            XiaoFeiDaiApplyActivity.this.loanUserInfo = new LoanUserInfo();
            userDetail = new LoanUserInfo.UserDetail();
            loanUserInfo.setUser_detail(userDetail);
        }else {
            userDetail = loanUserInfo.getUser_detail();
            if(userDetail==null){
                userDetail = new LoanUserInfo.UserDetail();
                loanUserInfo.setUser_detail(userDetail);
            }
        }
        try {
            JSONObject object = new JSONObject(baseEntity.getResult().toString());
            userDetail.setCard_id(object.getInt("card_id"));
            userDetail.setUser_id(object.getString("cus_id"));
            userDetail.setSerno(object.getString("serno"));
            userDetail.setUser_name(mEtCustomerName.getText().toString());
            userDetail.setMobile(mEtPhone.getText().toString());
            userDetail.setCert_code(mEtIDCardNumber.getBankCardText());
            userDetail.setPrd_id(prd_id);
            saveCurrencyFlag = true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //showSignatureAuthorization();
        mHandler.sendEmptyMessageDelayed(1,300);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myFaceNetWorkWarranty.myDestory();
        mHandler.removeCallbacksAndMessages(null);
        loanUserInfo = null;
    }
}
