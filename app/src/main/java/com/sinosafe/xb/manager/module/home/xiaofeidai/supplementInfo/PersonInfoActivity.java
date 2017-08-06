package com.sinosafe.xb.manager.module.home.xiaofeidai.supplementInfo;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.sinosafe.xb.manager.utils.Constant;
import com.sinosafe.xb.manager.utils.MyFaceNetWorkWarranty;
import com.sinosafe.xb.manager.utils.MyUtils;
import com.sinosafe.xb.manager.utils.T;
import com.sinosafe.xb.manager.utils.fileupload.FileUploadPresenter;
import com.sinosafe.xb.manager.widget.DialogResidentialAddress;
import com.sinosafe.xb.manager.widget.edit.ClearEditText;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import luo.library.base.base.BaseFragmentActivity;
import luo.library.base.base.BaseImage;
import luo.library.base.bean.WheelViewBean;
import luo.library.base.utils.IntentUtil;
import luo.library.base.utils.MyLog;
import luo.library.base.widget.dialog.DialogMessage;
import luo.library.base.widget.dialog.DialogWheelView;
import pub.devrel.easypermissions.EasyPermissions;

import static com.sinosafe.xb.manager.module.home.xiaofeidai.XiaoFeiDaiApplyActivity.loanUserInfo;
import static luo.library.base.utils.GsonUtil.GsonToBean;

/**
 * 个人信息
 */
public class PersonInfoActivity extends BaseFragmentActivity {

    private static final int INTO_IDCARDSCAN_PAGE = 100;
    @BindView(R.id.tvMyName)
    TextView mTvMyName;
    @BindView(R.id.tvMyPhone)
    TextView mTvMyPhone;
    @BindView(R.id.tv_residentialAddress)
    TextView mTvResidentialAddress;
    @BindView(R.id.et_detailedAddress)
    ClearEditText mEtDetailedAddress;
    @BindView(R.id.tv_educationStatus)
    TextView mTvEducationStatus;
    @BindView(R.id.tv_marriageStatus)
    TextView mTvMarriageStatus;
    //@BindView(R.id.rb_unmarried)
    //RadioButton mRbUnmarried;
    //@BindView(R.id.rb_married)
    //RadioButton mRbMarried;
    @BindView(R.id.et_spousePhone)
    ClearEditText mEtSpousePhone;
    @BindView(R.id.iv_spouseCard_positive)
    ImageView mIvSpouseCardPositive;
    @BindView(R.id.btn_next)
    Button mBtnNext;
    @BindView(R.id.spouseInfoLayout)
    LinearLayout spouseInfoLayout;

    private DialogResidentialAddress dialogAddress;

    private List<WheelViewBean> educations = new ArrayList<>();
    private DialogWheelView dialogWheelView;
    private LoanUserInfo.UserDetail userDetail;
    //配偶身份证
    private String spouseIdCardPhoto = "";
    private FileUploadPresenter fileUploadPresenter;
    private MyFaceNetWorkWarranty myFaceNetWorkWarranty;

    private List<WheelViewBean> marriageStatus = new ArrayList<>();
    private DialogWheelView marriageWheelView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {

        myFaceNetWorkWarranty = new MyFaceNetWorkWarranty();
        setTitleText("补充资料");
        getEducation();
        getMarriageStatus();
        fileUploadPresenter = new FileUploadPresenter(this);
        dialogAddress = new DialogResidentialAddress(this, new DialogResidentialAddress.OnAddressSelectListener() {
            @Override
            public void onAddressSelect(String areaCode, String area, String address) {
                mTvResidentialAddress.setText(address);
                userDetail.setIndiv_rsd_ple(address);
                userDetail.setIndiv_rsd_ple_id(areaCode);
            }
        });
        dialogWheelView = new DialogWheelView(this, educations, new DialogWheelView.OnConfirmListener() {
            @Override
            public void onConfirm(WheelViewBean item) {
                mTvEducationStatus.setText(item.getName());
                userDetail.setIndiv_edt(item.getType());
            }
        });

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
            mTvMyName.setText(userDetail.getUser_name());
            mTvMyPhone.setText(userDetail.getMobile());
            mEtDetailedAddress.setText(userDetail.getIndiv_rsd_addr());
            mTvResidentialAddress.setText(userDetail.getIndiv_rsd_ple());
            mTvEducationStatus.setText(getEducationText(userDetail.getIndiv_edt()));
            if(userDetail.getIndiv_mar_st()==null){
                //mRbMarried.setChecked(true);
                spouseInfoLayout.setVisibility(View.GONE);
            }else{
                //已婚
                if(userDetail.getIndiv_mar_st().equals("20")||userDetail.getIndiv_mar_st().equals("21")||userDetail.getIndiv_mar_st().equals("23")){
                    spouseInfoLayout.setVisibility(View.VISIBLE);
                }else{
                    spouseInfoLayout.setVisibility(View.GONE);
                }
                mTvMarriageStatus.setText(MyUtils.getMarriageStatus(userDetail.getIndiv_mar_st(),marriageStatus));
            }
            mEtSpousePhone.setText(userDetail.getIndiv_sps_mphn());
        }

        //退出申请提示
        LinearLayout backTv = (LinearLayout) findViewById(R.id.ly_base_back);
        backTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                showExitApplyDialog();
            }
        });
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

    /**
     * 获取教育程度
     */
    private void getEducation() {
        educations.add(new WheelViewBean("00", "博士及以上"));
        educations.add(new WheelViewBean("10", "研究生"));
        educations.add(new WheelViewBean("20", "本科"));
        educations.add(new WheelViewBean("30", "大专"));
        educations.add(new WheelViewBean("40", "中专或技校"));
        educations.add(new WheelViewBean("60", "高中"));
        educations.add(new WheelViewBean("70", "初中"));
        educations.add(new WheelViewBean("70", "小学"));
        educations.add(new WheelViewBean("90", "文盲或半文盲"));
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

    /**
     * 根据教育程度编号获取教育程度文本
     *
     * @param type
     * @return
     */
    private String getEducationText(String type) {
        String educationText = "";
        for (WheelViewBean education : educations) {
            if (education.getType().equals(type))
                educationText = education.getName();
        }
        return educationText;
    }

    @OnClick({R.id.tv_residentialAddress, R.id.tv_educationStatus, R.id.tv_marriageStatus,
            R.id.iv_spouseCard_positive, R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //居住地址
            case R.id.tv_residentialAddress:
                dialogAddress.show();
                break;
            //教育程度
            case R.id.tv_educationStatus:
                dialogWheelView.show();
                break;
            //婚姻
            case R.id.tv_marriageStatus:
                marriageWheelView.show();
                break;
            //配偶身份证
            case R.id.iv_spouseCard_positive:

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
                nextBtn();
                break;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //身份证扫码回来
        if(requestCode == INTO_IDCARDSCAN_PAGE && resultCode == RESULT_OK){

            int side = data.getIntExtra("side", 0);
            byte[] idCardImgData = data.getByteArrayExtra("idcardImg");
            //Bitmap idCardBmp = BitmapFactory.decodeByteArray(idCardImgData, 0, idCardImgData.length);
            spouseIdCardPhoto = CodeHelp.saveJPGFile(this, idCardImgData, "IDCardPositive");
            startProgressDialog("请稍候...");
            uploadImage(spouseIdCardPhoto);
        }
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
            stopProgressDialog();
            T.showShortBottom("识别身份证失败");
            spouseIdCardPhoto = "";
        }

        @Override
        public void onIDCardSuccessListCallback(String result) {
            OcridcardBean ocridcardBean = GsonToBean(result, OcridcardBean.class);
            stopProgressDialog();
            //性别判断
            String sex = MyUtils.judgeSexByCerdCode(loanUserInfo.getUser_detail().getCert_code());
            if(loanUserInfo.getUser_detail().getCert_code().equals(ocridcardBean.getId_card_number())){
                T.showShortBottom("借款人与配偶不能为同一个人");
                spouseIdCardPhoto = "";
                return;
            }
            if(sex.equals(ocridcardBean.getGender())){
                T.showShortBottom("借款人与配偶性别不能相同");
                spouseIdCardPhoto = "";
            }else{
                BaseImage.getInstance().displayImage(PersonInfoActivity.this,new File(spouseIdCardPhoto),mIvSpouseCardPositive);
            }
        }
    };


    /**
     * 下一步
     */
    private void nextBtn(){

        showWithStatus("保存中...");
        if(spouseInfoLayout.getVisibility()==View.VISIBLE&&spouseIdCardPhoto!=null){

            if(hasUpload||spouseIdCardPhoto.startsWith("http"))
                setSaveOrEditUserMap();
            else{
                upLoadSpouseIDCardPhoto();
            }
        }
        else{
            setSaveOrEditUserMap();
        }
    }

    /**
     * 上传配偶身份证正面照
     */
    private void upLoadSpouseIDCardPhoto(){
        if(spouseInfoLayout.getVisibility()==View.VISIBLE&&spouseIdCardPhoto!=null){
            String serno = userDetail.getSerno();
            List<String> upfiles = Arrays.asList(spouseIdCardPhoto);
            List<String> desList = Arrays.asList("XFD_00201");
            List<String> idCardPhoto = Arrays.asList("配偶身份证");
            String prdCode = "XFD";
            fileUploadPresenter.fileUpLoad(upfiles, desList, prdCode, serno,idCardPhoto);
        }
    }

    boolean hasUpload = false;
    @Override
    public void uploadSuccess(List<String> fileIds) {
        super.uploadSuccess(fileIds);
        String actorno = userDetail.getSerno();
        String avatar = Constant.getImagePath(fileIds.get(0),actorno);
        userDetail.setIndiv_sps_idcard(avatar);
        hasUpload = true;
        setSaveOrEditUserMap();
    }

    private boolean check(){

        if(isNull(mTvResidentialAddress)){
            T.showShortBottom("请选择居住地址");
            return false;
        }
        if(isNull(mEtDetailedAddress)){
            T.showShortBottom("请输入详细地址");
            return false;
        }
        if(isNull(mTvEducationStatus)){
            T.showShortBottom("请选择教育程度");
            return false;
        }
        //已婚
        if(spouseInfoLayout.getVisibility()==View.VISIBLE){
            if(isNull(mEtSpousePhone)){
                T.showShortBottom("请输入配偶手机号码");
                return false;
            }
            if(!MyUtils.isPhone(mEtSpousePhone.getText().toString())){
                T.showShortBottom("配偶手机号码错误");
                return false;
            }
            if(isNull(spouseIdCardPhoto)){
                T.showShortBottom("请上传配偶身份证正面照");
                return false;
            }
        }
        return true;
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

    private void openIDCardScan() {
        if (!EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
            EasyPermissions.requestPermissions(this, "", RC_CAMERA_PERM, Manifest.permission.CAMERA);
            return;
        }
        openIDCardScan(0);
    }

    private void setSaveOrEditUserMap(){

        Map<String,String> map = new HashMap<>();
        map.put("token",BaseMainActivity.loginUserBean.getToken());
        if(userDetail.getDetail_id()==null)
            map.put("is_register","Y");
        else {
            map.put("is_register","N");
            map.put("detail_id",userDetail.getDetail_id()+"");
        }
        map.put("cus_id",userDetail.getUser_id());
        map.put("register_latitude", APP.lat+","+APP.lng);
        map.put("device_type","01");
        map.put("user_name",userDetail.getUser_name());
        map.put("mobile",userDetail.getMobile());
        map.put("terminal_type","01");
        map.put("indiv_edt",userDetail.getIndiv_edt());
        map.put("cert_code",userDetail.getCert_code());
        if(spouseInfoLayout.getVisibility()==View.VISIBLE) {
            map.put("indiv_mar_st", userDetail.getIndiv_mar_st());
            map.put("indiv_sps_mphn", mEtSpousePhone.getText().toString());
            map.put("indiv_sps_idcard",userDetail.getIndiv_sps_idcard());
        }
        else{
            map.put("indiv_mar_st",userDetail.getIndiv_mar_st());
        }

        map.put("indiv_rsd_ple_id",userDetail.getIndiv_rsd_ple_id());
        map.put("indiv_rsd_ple",mTvResidentialAddress.getText().toString());
        map.put("indiv_rsd_addr",mEtDetailedAddress.getText().toString());

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
                    MyLog.e("个人信息保存反馈====="+msg);
                    T.showShortBottom(msg);
                }});
    }

    private void synLocalCustomerInfo(String baseInfo){
        try {
            JSONObject object = new JSONObject(baseInfo);
            userDetail.setDetail_id(object.getString("detail_id").split("\\.")[0]);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        userDetail.setIndiv_rsd_ple(mTvResidentialAddress.getText().toString());
        userDetail.setIndiv_rsd_addr(mEtDetailedAddress.getText().toString());
        userDetail.setIndiv_sps_mphn(mEtSpousePhone.getText().toString());
        mHandler.sendEmptyMessageDelayed(0,500);
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
                    IntentUtil.gotoActivityAndFinish(PersonInfoActivity.this,MainActivity.class);
                }
                @Override
                public void _onError(String msg) {
                    closeSVProgressHUD();
                    T.showShortBottom(msg);
                }});
    }


    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            IntentUtil.gotoActivity(PersonInfoActivity.this,CareerInfoActivity.class);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myFaceNetWorkWarranty.myDestory();
        mHandler.removeCallbacksAndMessages(null);
    }
}
