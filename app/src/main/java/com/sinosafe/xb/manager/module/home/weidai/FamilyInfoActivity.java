package com.sinosafe.xb.manager.module.home.weidai;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.megvii.idcardlib.IDCardScanActivity;
import com.megvii.livenesslib.help.CodeHelp;
import com.sinosafe.xb.manager.APP;
import com.sinosafe.xb.manager.BaseMainActivity;
import com.sinosafe.xb.manager.MainActivity;
import com.sinosafe.xb.manager.R;
import com.sinosafe.xb.manager.api.rxjava.RxHttpBaseResultFunc;
import com.sinosafe.xb.manager.api.rxjava.RxSchedulersHelper;
import com.sinosafe.xb.manager.api.rxjava.RxSubscriber;
import com.sinosafe.xb.manager.api.xutilshttp.OnResponseListener;
import com.sinosafe.xb.manager.api.xutilshttp.XutilsBaseHttp;
import com.sinosafe.xb.manager.bean.BaseEntity;
import com.sinosafe.xb.manager.bean.LoanUserInfo;
import com.sinosafe.xb.manager.bean.OcridcardBean;
import com.sinosafe.xb.manager.model.ClientModel;
import com.sinosafe.xb.manager.utils.MyFaceNetWorkWarranty;
import com.sinosafe.xb.manager.utils.MyUtils;
import com.sinosafe.xb.manager.utils.T;
import com.sinosafe.xb.manager.widget.edit.BandCardEditText;
import com.sinosafe.xb.manager.widget.edit.ClearEditText;

import org.xutils.http.RequestParams;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import luo.library.base.base.BaseFragmentActivity;
import luo.library.base.bean.WheelViewBean;
import luo.library.base.utils.IDCardUtil;
import luo.library.base.utils.IntentUtil;
import luo.library.base.utils.MyLog;
import luo.library.base.utils.Result;
import luo.library.base.widget.SimpleTextWatcher;
import luo.library.base.widget.dialog.DialogMessage;
import luo.library.base.widget.dialog.DialogWheelView;
import pub.devrel.easypermissions.EasyPermissions;

import static com.sinosafe.xb.manager.module.home.xiaofeidai.XiaoFeiDaiApplyActivity.loanUserInfo;
import static luo.library.base.utils.GsonUtil.GsonToBean;

/**
 * 家庭信息
 */
public class FamilyInfoActivity extends BaseFragmentActivity {


    //@BindView(R.id.rb_unmarried)
    //RadioButton mRbUnmarried;
    //@BindView(R.id.rb_married)
    //RadioButton mRbMarried;
    @BindView(R.id.tv_marriageStatus)
    TextView mTvMarriageStatus;
    @BindView(R.id.et_spouseName)
    ClearEditText mEtSpouseName;
    @BindView(R.id.et_spouseIDCardNumber)
    BandCardEditText mEtSpouseIDCardNumber;
    @BindView(R.id.iv_scan_idCard)
    ImageView mIvScanIdCard;
    @BindView(R.id.btn_next)
    Button mBtnNext;
    @BindView(R.id.spouseInfoLayout)
    LinearLayout spouseInfoLayout;
    private static final int INTO_IDCARDSCAN_PAGE = 100;
    private LoanUserInfo.UserDetail userDetail;
    private MyFaceNetWorkWarranty myFaceNetWorkWarranty;

    private List<WheelViewBean> marriageStatus = new ArrayList<>();
    private DialogWheelView marriageWheelView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_info);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {

        myFaceNetWorkWarranty = new MyFaceNetWorkWarranty();
        setTitleText("补充资料");
        getMarriageStatus();

        marriageWheelView = new DialogWheelView(this, marriageStatus, new DialogWheelView.OnConfirmListener() {
            @Override
            public void onConfirm(WheelViewBean item) {
                mTvMarriageStatus.setText(item.getName());
                userDetail.setIndiv_mar_st(item.getType());
                //已婚
                if(item.getType().equals("20")||item.getType().equals("21")||item.getType().equals("23")){
                    spouseInfoLayout.setVisibility(View.VISIBLE);
                }else{
                    spouseInfoLayout.setVisibility(View.GONE);
                }
            }
        });


        if(loanUserInfo.getUser_detail()!=null){
            userDetail = loanUserInfo.getUser_detail();
            if(userDetail.getIndiv_mar_st()==null){
                spouseInfoLayout.setVisibility(View.GONE);
            }else{
                //已婚
                if(userDetail.getIndiv_mar_st().equals("20")||userDetail.getIndiv_mar_st().equals("21")||userDetail.getIndiv_mar_st().equals("23")){
                    spouseInfoLayout.setVisibility(View.VISIBLE);
                }else{
                    spouseInfoLayout.setVisibility(View.GONE);
                }
                mTvMarriageStatus.setText(MyUtils.getMarriageStatus(userDetail.getIndiv_mar_st(),marriageStatus));
                mEtSpouseName.setText(userDetail.getIndiv_sps_name());
                mEtSpouseIDCardNumber.setText(userDetail.getIndiv_sps_cardno());
            }
        }

        //退出申请提示
        LinearLayout backTv = (LinearLayout) findViewById(R.id.ly_base_back);
        backTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                showExitApplyDialog();
            }
        });
        mEtSpouseIDCardNumber.addTextChangedListener(textWatcher);
    }


    /**
     * 获取婚姻状态
     */
    private void getMarriageStatus() {
        marriageStatus.add(new WheelViewBean("21", "已婚且有子女"));
        marriageStatus.add(new WheelViewBean("22", "离异"));
        marriageStatus.add(new WheelViewBean("10", "未婚"));
        marriageStatus.add(new WheelViewBean("23", "复婚"));
        marriageStatus.add(new WheelViewBean("30", "丧偶"));
        marriageStatus.add(new WheelViewBean("90", "未说明的婚姻状况"));
        marriageStatus.add(new WheelViewBean("20", "已婚无子女"));
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            showExitApplyDialog();
            return false;
        }
        return super.onKeyDown(keyCode,event);
    }

    private void showExitApplyDialog(){
        new DialogMessage(this).setMess("亲，您确定要放弃本次申请吗?")
                .setConfirmListener(new DialogMessage.OnConfirmListener() {
                    @Override
                    public void onConfirm() {
                        startProgressDialog("");
                        delSerno();
                    }
                }).show();

    }

    @OnClick({R.id.tv_marriageStatus, R.id.iv_scan_idCard, R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //婚姻状态
            case R.id.tv_marriageStatus:

                marriageWheelView.show();
                break;

            //扫码身份证
            case R.id.iv_scan_idCard:

                //是否联网授权
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
            //下一步
            case R.id.btn_next:

                if(!check())
                    return;
                showWithStatus("保存中...");
                setSaveOrEditUserMap();
                break;
        }
    }

    /**
     * EditView输入监听
     */
    TextWatcher textWatcher = new SimpleTextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {
            String text = mEtSpouseIDCardNumber.getBankCardText();
            if(text.length()==15||text.length()==18)
                checkIdCardAndRequestCustomerInfo();
        }
    };


    private boolean checkIdCardAndRequestCustomerInfo(){
        String text = mEtSpouseIDCardNumber.getBankCardText();
        Result result = IDCardUtil.validateIDNum(text);
        if(result.isLegal()){
            return true;
        }
        else{
            T.showShortBottom("身份证号码不正确");
            return false;
        }
    }

    private boolean check(){

        if(spouseInfoLayout.getVisibility()==View.VISIBLE){
            if(isNull(mEtSpouseName)){
                T.showShortBottom("请输入配偶姓名");
                return false;
            }
            if(isNull(mEtSpouseIDCardNumber)){
                T.showShortBottom("请输入身份证号码");
                return false;
            }

            if(!checkIdCardAndRequestCustomerInfo()){
                return false;
            }

            //性别判断
            String sex = MyUtils.judgeSexByCerdCode(loanUserInfo.getUser_detail().getCert_code());
            if(loanUserInfo.getUser_detail().getCert_code().equals(mEtSpouseIDCardNumber.getBankCardText())){
                T.showShortBottom("借款人与配偶不能为同一个人");
                return false;
            }
            if(sex.equals(MyUtils.judgeSexByCerdCode(mEtSpouseIDCardNumber.getBankCardText()))){
                T.showShortBottom("借款人与配偶性别不能相同");
                return false;
            }
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //身份证扫码回来
        if(requestCode == INTO_IDCARDSCAN_PAGE && resultCode == RESULT_OK){

            showWithStatus("请稍后");
            int side = data.getIntExtra("side", 0);
            byte[] idCardImgData = data.getByteArrayExtra("idcardImg");
            Bitmap idCardBmp = BitmapFactory.decodeByteArray(idCardImgData, 0,
                    idCardImgData.length);
            String filePath = CodeHelp.saveJPGFile(this, idCardImgData, "IDCardPositive");
            uploadImage(filePath);

        }
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

    private OnResponseListener onResponseListener = new OnResponseListener() {
        @Override
        public void onRequestFailedCallback(String var1) {
            closeSVProgressHUD();
            T.showShortBottom("识别身份证失败");
        }

        @Override
        public void onIDCardSuccessListCallback(String result) {
            OcridcardBean ocridcardBean = GsonToBean(result, OcridcardBean.class);
            mEtSpouseIDCardNumber.setText(ocridcardBean.getId_card_number());
            mEtSpouseName.setText(ocridcardBean.getName());
            closeSVProgressHUD();
        }
    };

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

    private void openIDCardScan() {
        if (!EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
            EasyPermissions.requestPermissions(this, "", RC_CAMERA_PERM, Manifest.permission.CAMERA);
            return;
        }
        openIDCardScan(0);
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

        if(spouseInfoLayout.getVisibility()==View.VISIBLE) {
            map.put("indiv_mar_st", userDetail.getIndiv_mar_st());
            map.put("indiv_sps_cardno", mEtSpouseIDCardNumber.getBankCardText());
            map.put("indiv_sps_name",mEtSpouseName.getText().toString());
        }
        else{
            map.put("indiv_mar_st",userDetail.getIndiv_mar_st());
        }

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
                        synLocalCustomerInfo(baseEntity.getResult().toString());
                    }
                    @Override
                    public void _onError(String msg) {
                        closeSVProgressHUD();
                        MyLog.e("家庭信息保存反馈====="+msg);
                        T.showShortBottom("保存失败");
                    }});
    }

    /**
     * 删除申请记录
     */
    private void delSerno(){
        Map<String,String> map = new HashMap<>();
        map.put("token",BaseMainActivity.loginUserBean.getToken());
        map.put("serno",loanUserInfo.getUser_detail().getSerno());
        ClientModel.delSerno(map)
            .timeout(20, TimeUnit.SECONDS)
            .compose(RxSchedulersHelper.<BaseEntity>io_main())
            .map(new RxHttpBaseResultFunc())
            .subscribe(new RxSubscriber<BaseEntity>() {
                @Override
                public void _onNext(BaseEntity baseEntity) {
                    IntentUtil.gotoActivityAndFinish(FamilyInfoActivity.this,MainActivity.class);
                }
                @Override
                public void _onError(String msg) {
                    closeSVProgressHUD();
                    T.showShortBottom(msg);
                }});
    }

    private void synLocalCustomerInfo(String baseInfo){

        userDetail.setIndiv_sps_cardno(mEtSpouseIDCardNumber.getText().toString());
        userDetail.setIndiv_sps_name(mEtSpouseName.getText().toString());
        mHandler.sendEmptyMessageDelayed(0,500);
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            IntentUtil.gotoActivity(FamilyInfoActivity.this,BusinessinfoActivity.class);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myFaceNetWorkWarranty.myDestory();
        mHandler.removeCallbacksAndMessages(null);
    }
}
