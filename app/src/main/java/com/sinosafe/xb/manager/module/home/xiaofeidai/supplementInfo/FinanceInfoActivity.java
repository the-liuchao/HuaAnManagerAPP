package com.sinosafe.xb.manager.module.home.xiaofeidai.supplementInfo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.sinosafe.xb.manager.BaseMainActivity;
import com.sinosafe.xb.manager.R;
import com.sinosafe.xb.manager.api.rxjava.RxHttpBaseResultFunc;
import com.sinosafe.xb.manager.api.rxjava.RxSchedulersHelper;
import com.sinosafe.xb.manager.api.rxjava.RxSubscriber;
import com.sinosafe.xb.manager.bean.BaseEntity;
import com.sinosafe.xb.manager.bean.LoanUserInfo;
import com.sinosafe.xb.manager.model.ClientModel;
import com.sinosafe.xb.manager.utils.Constant;
import com.sinosafe.xb.manager.utils.FileUtil;
import com.sinosafe.xb.manager.utils.T;
import com.sinosafe.xb.manager.utils.fileupload.FileUploadPresenter;
import com.sinosafe.xb.manager.widget.edit.ClearEditText;

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
import luo.library.base.base.BaseImage;
import luo.library.base.utils.IntentUtil;
import luo.library.base.utils.MyLog;

import static com.lzy.imagepicker.ImagePicker.RESULT_CODE_ITEMS;
import static com.sinosafe.xb.manager.R.mipmap.icon_jidongche;
import static com.sinosafe.xb.manager.module.home.xiaofeidai.XiaoFeiDaiApplyActivity.loanUserInfo;

/**
 * 财务信息
 */
public class FinanceInfoActivity extends BaseFragmentActivity {


    private final static int REQUEST_CODE_SELECT = 110;
    private final int RESULT_REQUEST_PHOTO_CROP = 1006;
    @BindView(R.id.et_loanUse)
    ClearEditText mEtLoanUse;
    @BindView(R.id.rb_noCar)
    RadioButton mRbNoCar;
    @BindView(R.id.rb_hasCar)
    RadioButton mRbHasCar;
    @BindView(R.id.iv_automobileCertificate)
    ImageView mIvAutomobileCertificate;
    @BindView(R.id.rb_noHouse)
    RadioButton mRbNoHouse;
    @BindView(R.id.rb_hasHouse)
    RadioButton mRbHasHouse;
    @BindView(R.id.iv_houseProperty)
    ImageView mIvHouseProperty;
    @BindView(R.id.btn_next)
    Button mBtnNext;

    @BindView(R.id.automobileCertificateLayout)
    LinearLayout automobileCertificateLayout;
    @BindView(R.id.housePropertyLayout)
    LinearLayout housePropertyLayout;

    //选择图片标记1：机动车证；2：房产证
    private int flag = -1;
    //机动车证图片路径
    private String automobileCertificatePath;
    //房产证图片路径
    private String housePropertyPath;
    private LoanUserInfo.UserDetail userDetail;
    private Uri fileCropUri1,fileCropUri2;
    private FileUploadPresenter fileUploadPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance_info);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setTitleText("补全资料");
        fileUploadPresenter = new FileUploadPresenter(this);
        userDetail = loanUserInfo.getUser_detail();
        //housePropertyPath = userDetail.getHouse_certificate();
        //automobileCertificatePath = userDetail.getCarl_certificate();

        mEtLoanUse.setText(userDetail.getLoan_for());
        if(userDetail.getCarl_st()!=null){
            if(userDetail.getCarl_st().equals("1")){
                mRbHasCar.setChecked(true);
                automobileCertificateLayout.setVisibility(View.VISIBLE);
            }else {
                mRbNoCar.setChecked(true);
                automobileCertificateLayout.setVisibility(View.GONE);
            }
            if(userDetail.getHouse_st().equals("1")){
                mRbHasHouse.setChecked(true);
                housePropertyLayout.setVisibility(View.VISIBLE);
            }else {
                mRbNoHouse.setChecked(true);
                housePropertyLayout.setVisibility(View.GONE);
            }
        }
       /* if(housePropertyPath!=null){
            BaseImage.getInstance().displayImage(this,housePropertyPath,mIvHouseProperty,R.mipmap.icon_fangchan);
        }
        if(automobileCertificatePath!=null){
            BaseImage.getInstance().displayImage(this,automobileCertificatePath,mIvAutomobileCertificate,R.mipmap.icon_jidongche);
        }*/
        setImageSelectLimit(1);
    }


    @OnClick({R.id.iv_automobileCertificate, R.id.iv_houseProperty, R.id.btn_next,
            R.id.rb_noCar,R.id.rb_hasCar,R.id.rb_noHouse,R.id.rb_hasHouse})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //机动车证书
            case R.id.iv_automobileCertificate:
                flag = 1;
                openImageGridActivity();
                break;
            //房产所有权
            case R.id.iv_houseProperty:
                flag = 2;
                openImageGridActivity();
                break;
            case R.id.btn_next:

                if(!check())
                    return;
                nextBtn();
                break;
            //无车
            case R.id.rb_noCar:
                automobileCertificateLayout.setVisibility(View.GONE);
                break;
            //有车
            case R.id.rb_hasCar:
                automobileCertificateLayout.setVisibility(View.VISIBLE);
                break;
            //无房
            case R.id.rb_noHouse:
                housePropertyLayout.setVisibility(View.GONE);
                break;

            //有房
            case R.id.rb_hasHouse:
                housePropertyLayout.setVisibility(View.VISIBLE);
                break;
        }
    }

    /**
     * 下一步
     */
    private void nextBtn() {

        //已上传
        showWithStatus("保存中...");

        if((housePropertyPath!=null&&!housePropertyPath.startsWith("http"))
                ||(automobileCertificatePath!=null&&!automobileCertificatePath.startsWith("http"))){
            upLoadCertificatePhoto();
        }else{
            setSaveOrEditUserMap();
        }
    }


    /**
     * 上传车、房产证明
     */
    private void upLoadCertificatePhoto(){
        List<String> upfiles = new ArrayList<>();
        List<String> desList = new ArrayList<>();
        List<String> photoDes = new ArrayList<>();
        //有车
        if(mRbHasCar.isChecked()&&!automobileCertificatePath.startsWith("http")){
            upfiles.add(automobileCertificatePath);
            desList.add("XFD_00701");
            photoDes.add("机动车登记证明");
        }
        //有房
        if(mRbHasHouse.isChecked()&&!housePropertyPath.startsWith("http")){
            upfiles.add(housePropertyPath);
            desList.add("XFD_00701");
            photoDes.add("房产所有权证");
        }
        String serno = userDetail.getSerno();
        String prdCode = "XFD";
        fileUploadPresenter.fileUpLoad(upfiles, desList, prdCode, serno,photoDes);
    }

    @Override
    public void uploadSuccess(List<String> fileIds) {
        //super.uploadSuccess(fileIds);
        String actorno = userDetail.getSerno();
        if(fileIds.size()==2){
            automobileCertificatePath = Constant.getImagePath(fileIds.get(0),actorno);
            housePropertyPath = Constant.getImagePath(fileIds.get(1),actorno);
            userDetail.setCarl_certificate(automobileCertificatePath);
            userDetail.setHouse_certificate(housePropertyPath);
        }
        else if(fileIds.size()==1){

            if(mRbHasCar.isChecked()&&!automobileCertificatePath.startsWith("http")){
                automobileCertificatePath = Constant.getImagePath(fileIds.get(0),actorno);
                userDetail.setCarl_certificate(automobileCertificatePath);
            }

            if(mRbHasHouse.isChecked()&&!housePropertyPath.startsWith("http")){
                housePropertyPath = Constant.getImagePath(fileIds.get(0),actorno);
                userDetail.setHouse_certificate(housePropertyPath);
            }

        }
        setSaveOrEditUserMap();
    }

    private void setSaveOrEditUserMap(){

        Map<String,String> map = new HashMap<>();
        map.put("token",BaseMainActivity.loginUserBean.getToken());
        map.put("is_register","N");
        map.put("cus_id",userDetail.getUser_id());
        map.put("device_type","01");
        map.put("user_name",userDetail.getUser_name());
        map.put("mobile",userDetail.getMobile());
        map.put("cert_code",userDetail.getCert_code());
        map.put("detail_id",userDetail.getDetail_id());

        map.put("loan_for",mEtLoanUse.getText().toString());
        if(mRbHasHouse.isChecked()){
            map.put("house_st","1");
            map.put("house_certificate",userDetail.getHouse_certificate());
        }else {
            map.put("house_st","0");
        }
        if(mRbHasCar.isChecked()){
            map.put("carl_st","1");
            map.put("carl_certificate",userDetail.getCarl_certificate());
        }else {
            map.put("carl_st","0");
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
                        MyLog.e("财产信息保存反馈====="+msg);
                        T.showShortBottom("保存失败");
                    }});
    }

    private void synLocalCustomerInfo(String baseInfo){

        userDetail.setLoan_for(mEtLoanUse.getText().toString());
        if(mRbHasCar.isChecked()){
            userDetail.setCarl_st("1");
        }else {
            userDetail.setCarl_st("0");
        }
        if(mRbHasHouse.isChecked()){
            userDetail.setHouse_st("1");
        }else {
            userDetail.setHouse_st("0");
        }
        mHandler.sendEmptyMessageDelayed(0,500);
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            IntentUtil.gotoActivity(FinanceInfoActivity.this,EmergencyContactActivity.class);
        }
    };

    private boolean check(){

        if(isNull(mEtLoanUse)){
            T.showShortBottom("请输入贷款用途");
            return false;
        }

        //有车
        if(mRbHasCar.isChecked()){
            if(isNull(automobileCertificatePath)){
                T.showShortBottom("请上传机动车登记证书");
                return false;
            }
        }

        //有房
        if(mRbHasHouse.isChecked()){
            if(isNull(housePropertyPath)){
                T.showShortBottom("请上传房产所有权证");
                return false;
            }
        }
        return true;
    }


    /**
     * 设置还可以选择几张图片
     *
     * @param num
     */
    private void setImageSelectLimit(int num) {
        ImagePicker imagePicker = ImagePicker.getInstance();
        //选中数量限制
        imagePicker.setSelectLimit(num);
    }

    /**
     * 打开相册
     */
    private void openImageGridActivity() {
        setImageSelectLimit(1);
        Intent intent = new Intent(this, ImageGridActivity.class);
        // 是否是直接打开相机
        //intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS,true);
        startActivityForResult(intent, REQUEST_CODE_SELECT);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //选择回来
        if (requestCode == REQUEST_CODE_SELECT && resultCode == RESULT_CODE_ITEMS) {
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                ArrayList<ImageItem> images2 = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if(images2==null||images2.size()==0){
                    if(flag==1) {
                        automobileCertificatePath = userDetail.getCarl_certificate();
                        mIvAutomobileCertificate.setImageResource(icon_jidongche);
                    }else{
                        housePropertyPath = userDetail.getHouse_certificate();
                        mIvHouseProperty.setImageResource(R.mipmap.icon_fangchan);
                    }
                }else{
                    //Uri sourceUri = Uri.fromFile(new File(images2.get(0).path));
                    /*if(flag==1) {
                        fileCropUri1 = MyUtils.getOutputMediaFileUri(this);
                        MyUtils.cropImageUri(this, sourceUri, fileCropUri1, 640,340, RESULT_REQUEST_PHOTO_CROP);
                    }
                    else {
                        fileCropUri2 = MyUtils.getOutputMediaFileUri(this);
                        MyUtils.cropImageUri(this, sourceUri, fileCropUri2, 640, 340, RESULT_REQUEST_PHOTO_CROP);
                    }*/
                    if(flag==1){
                        automobileCertificatePath = images2.get(0).path;
                        BaseImage.getInstance().displayImage(this,new File(automobileCertificatePath),mIvAutomobileCertificate);
                    }else {
                        housePropertyPath = images2.get(0).path;
                        BaseImage.getInstance().displayImage(this,new File(housePropertyPath),mIvHouseProperty);
                    }
                }
            }
        }

        //剪切后的图片
        else if (requestCode == RESULT_REQUEST_PHOTO_CROP) {
            if (resultCode == Activity.RESULT_OK) {
                if(flag==1){
                    automobileCertificatePath = FileUtil.getPath(this, fileCropUri1);
                    BaseImage.getInstance().displayImage(this,new File(automobileCertificatePath),mIvAutomobileCertificate);
                }else {
                    housePropertyPath = FileUtil.getPath(this, fileCropUri2);
                    BaseImage.getInstance().displayImage(this,new File(housePropertyPath),mIvHouseProperty);
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
