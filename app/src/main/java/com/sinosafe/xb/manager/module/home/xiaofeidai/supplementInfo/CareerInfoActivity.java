package com.sinosafe.xb.manager.module.home.xiaofeidai.supplementInfo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.sinosafe.xb.manager.BaseMainActivity;
import com.sinosafe.xb.manager.R;
import com.sinosafe.xb.manager.adapter.home.UpLoadGridAdapter;
import com.sinosafe.xb.manager.api.rxjava.RxHttpBaseResultFunc;
import com.sinosafe.xb.manager.api.rxjava.RxSchedulersHelper;
import com.sinosafe.xb.manager.api.rxjava.RxSubscriber;
import com.sinosafe.xb.manager.bean.BaseEntity;
import com.sinosafe.xb.manager.bean.LoanUserInfo;
import com.sinosafe.xb.manager.model.ClientModel;
import com.sinosafe.xb.manager.module.imagePreview.MyImagePreviewDelActivity;
import com.sinosafe.xb.manager.utils.Constant;
import com.sinosafe.xb.manager.utils.T;
import com.sinosafe.xb.manager.utils.fileupload.FileUploadPresenter;
import com.sinosafe.xb.manager.widget.DialogResidentialAddress;
import com.sinosafe.xb.manager.widget.edit.ClearEditText;

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
import luo.library.base.utils.IntentUtil;
import luo.library.base.utils.MyLog;
import luo.library.base.widget.NoScrollGridView;
import luo.library.base.widget.dialog.DialogWheelView;
import luo.library.base.widget.dialog.DialogYearsWheelView;

import static com.lzy.imagepicker.ImagePicker.REQUEST_CODE_PREVIEW;
import static com.lzy.imagepicker.ImagePicker.RESULT_CODE_ITEMS;
import static com.sinosafe.xb.manager.module.home.xiaofeidai.XiaoFeiDaiApplyActivity.loanUserInfo;

/**
 * 职业信息
 */
public class CareerInfoActivity extends BaseFragmentActivity {


    @BindView(R.id.et_companyName)
    ClearEditText mEtCompanyName;
    @BindView(R.id.et_companyPhone)
    ClearEditText mEtCompanyPhone;
    @BindView(R.id.tv_companyAddress)
    TextView mTvCompanyAddress;
    @BindView(R.id.et_detailedAddress)
    ClearEditText mEtDetailedAddress;
    @BindView(R.id.tv_initiationYear)
    TextView mTvInitiationYear;
    @BindView(R.id.tv_companyNature)
    TextView mTvCompanyNature;
    @BindView(R.id.gvImage)
    NoScrollGridView mGvImage;
    @BindView(R.id.btn_next)
    Button mBtnNext;

    /**工牌照*/
    private List<ImageItem> images = new ArrayList<>();
    private UpLoadGridAdapter imagesAdapter;
    private final static int REQUEST_CODE_SELECT = 110;
    private final static int MAX_IMAGE_NUM = 2;

    private List<WheelViewBean> companyTypes = new ArrayList<>();
    private DialogYearsWheelView yearsWheelView;
    private DialogWheelView dialogWheelView;
    private DialogResidentialAddress dialogAddress;
    private LoanUserInfo.UserDetail userDetail;

    private FileUploadPresenter fileUploadPresenter;
    private boolean hasUpload = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_career_info);
        ButterKnife.bind(this);

        initView();
    }

    /**
     * 初始化
     */
    private void initView() {
        setTitleText("补充资料");
        initListener();
        getCompanyTypes();
        setImageSelectLimit(2);
        fileUploadPresenter = new FileUploadPresenter(this);
        dialogAddress = new DialogResidentialAddress(this, new DialogResidentialAddress.OnAddressSelectListener() {
            @Override
            public void onAddressSelect(String areaCode, String area, String address) {
                mTvCompanyAddress.setText(address);
                userDetail.setIndiv_com_addr_ple_id(areaCode);
            }
        });

        yearsWheelView = new DialogYearsWheelView(this, new DialogYearsWheelView.OnConfirmListener() {
            @Override
            public void onConfirm(String year) {

                userDetail.setIndiv_work_job_y(year);
                mTvInitiationYear.setText(year+"年");
            }
        });

        dialogWheelView = new DialogWheelView(this, companyTypes, new DialogWheelView.OnConfirmListener() {
            @Override
            public void onConfirm(WheelViewBean item) {
                mTvCompanyNature.setText(item.getName());
                userDetail.setIndiv_com_typ(item.getType());
            }
        });

        if(loanUserInfo.getUser_detail()!=null){
            userDetail = loanUserInfo.getUser_detail();
            mEtCompanyName.setText(userDetail.getIndiv_com_name());
            mEtCompanyPhone.setText(userDetail.getIndiv_com_phn());
            mEtDetailedAddress.setText(userDetail.getIndiv_rsd_addr());
            //单位地址
            mTvCompanyAddress.setText(userDetail.getIndiv_com_addr_ple());
            mEtDetailedAddress.setText(userDetail.getIndiv_com_addr());
            if(userDetail.getIndiv_work_job_y()==null||"nulll".equals(userDetail.getIndiv_work_job_y()))
                mTvInitiationYear.setText("");
            else
                mTvInitiationYear.setText(userDetail.getIndiv_work_job_y()+"年");
            mTvCompanyNature.setText(getCompanyName(userDetail.getIndiv_com_typ()));

            //工牌
            String badge = userDetail.getBadge();
            /*if(!isNull(badge)){
                String badgeArr[] = badge.split(",");
                for(String str:badgeArr) {
                    ImageItem imageItem = new ImageItem();
                    imageItem.path = str;
                    images.add(imageItem);
                }
                imagesAdapter.update(images);
            }*/
        }
    }

    /**
     * 获取教育程度
     */
    private void getCompanyTypes() {
        companyTypes.add(new WheelViewBean("100", "党政机关"));
        companyTypes.add(new WheelViewBean("200", "事业单位"));
        companyTypes.add(new WheelViewBean("300", "军队"));
        companyTypes.add(new WheelViewBean("400", "社会团体"));
        companyTypes.add(new WheelViewBean("500", "内资企业"));
        companyTypes.add(new WheelViewBean("510", "国有企业"));
        companyTypes.add(new WheelViewBean("520", "集体企业"));
        companyTypes.add(new WheelViewBean("530", "股份合作企业"));
        companyTypes.add(new WheelViewBean("540", "联营企业"));
        companyTypes.add(new WheelViewBean("550", "有限责任公司"));
        companyTypes.add(new WheelViewBean("560", "股份有限公司"));
        companyTypes.add(new WheelViewBean("570", "私营企业"));
        companyTypes.add(new WheelViewBean("600", "外商投资企业(含港、澳、台)"));
        companyTypes.add(new WheelViewBean("610", "中外合资经营企业(含港、澳、台)"));
        companyTypes.add(new WheelViewBean("620", "中外合作经营企业(含港、澳、台)"));
        companyTypes.add(new WheelViewBean("630", "外资企业(含港、澳、台)"));
        companyTypes.add(new WheelViewBean("640", "外商投资股份有限公司(含港、澳、台)"));
        companyTypes.add(new WheelViewBean("700", "个体经营"));
        companyTypes.add(new WheelViewBean("800", "其他"));
        companyTypes.add(new WheelViewBean("900", "未知"));
        companyTypes.add(new WheelViewBean("901", "科研设计单位"));
        companyTypes.add(new WheelViewBean("902", "高等学校"));
        companyTypes.add(new WheelViewBean("903", "其它事业单位"));
    }

    /**
     * 关系名称
     * @param relation
     * @return
     */
    private String getCompanyName(String relation) {
        String name = "";
        for (WheelViewBean relationShip : companyTypes) {
            if (relationShip.getType().equals(relation))
                name = relationShip.getName();
        }
        return name;
    }

    /**
     * 监听
     */
    private void initListener() {

        imagesAdapter = new UpLoadGridAdapter(this,3);
        imagesAdapter.setImageNum(2);
        mGvImage.setAdapter(imagesAdapter);

        mGvImage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position <= images.size()-1) {
                    openImagePreviewActivity(position,images);
                    return;
                }
                setImageSelectLimit(MAX_IMAGE_NUM - images.size());
                openImageGridActivity();
            }
        });
    }


    @OnClick({R.id.tv_companyAddress, R.id.tv_initiationYear, R.id.tv_companyNature, R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            //单位地址
            case R.id.tv_companyAddress:
                dialogAddress.show();
                break;
            //单位工作初始年
            case R.id.tv_initiationYear:
                yearsWheelView.show();
                break;
            //单位性质
            case R.id.tv_companyNature:
                dialogWheelView.show();
                break;
            //下一步
            case R.id.btn_next:
                if(!check())
                    return;
                showWithStatus("保存中...");
                if(images.size()==0){
                    setSaveOrEditUserMap();
                }else {
                    //已上传
                    if(hasUpload){
                        setSaveOrEditUserMap();
                    }else{
                        //待上传
                        if(exitImageToUpLoad()){
                            upLoadBadgePhoto();
                        }else {
                            setSaveOrEditUserMap();
                        }
                    }
                }
                break;
        }
    }

    private boolean exitImageToUpLoad(){
        for(int i=0;i<images.size();i++){
            ImageItem imageItem = images.get(i);
            if(!imageItem.path.startsWith("http")){
                return true;
            }
        }
        return false;
    }
    /**
     * 上传工牌
     */
    private void upLoadBadgePhoto(){
        List<String> upfiles = new ArrayList<>();
        List<String> desList = new ArrayList<>();
        List<String> photoDes = new ArrayList<>();

        for(int i=0;i<images.size();i++){
            ImageItem imageItem = images.get(i);
            if(!imageItem.path.startsWith("http")){
                upfiles.add(images.get(i).path);
                desList.add("XFD_00501");
                photoDes.add("工牌"+(i+1));
            }
        }
        String serno = userDetail.getSerno();
        String prdCode = "XFD";
        fileUploadPresenter.fileUpLoad(upfiles, desList, prdCode, serno,photoDes);
    }

    @Override
    public void uploadSuccess(List<String> fileIds) {
        super.uploadSuccess(fileIds);
        String actorno = userDetail.getSerno();
        String badgeStr = "";
        for(int i=0;i<fileIds.size();i++){
            String avatar = Constant.getImagePath(fileIds.get(i),actorno);
            badgeStr += avatar+",";
        }
        badgeStr = badgeStr.substring(0,badgeStr.length()-1);

        for(int i=0;i<images.size();i++){
            ImageItem imageItem = images.get(i);
            if(imageItem.path.startsWith("http")){
                badgeStr = badgeStr + ","+imageItem.path;
            }
        }
        userDetail.setBadge(badgeStr);
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

        map.put("indiv_com_name",mEtCompanyName.getText().toString());
        map.put("indiv_com_phn",mEtCompanyPhone.getText().toString());
        map.put("indiv_com_addr_ple_id",userDetail.getIndiv_com_addr_ple_id());
        map.put("indiv_com_addr_ple",mTvCompanyAddress.getText().toString());
        map.put("indiv_com_addr",mEtDetailedAddress.getText().toString());

        map.put("indiv_work_job_y",userDetail.getIndiv_work_job_y());
        map.put("indiv_com_typ",userDetail.getIndiv_com_typ());
        map.put("badge",userDetail.getBadge());

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
                        MyLog.e("职业信息保存反馈====="+msg);
                        T.showShortBottom("保存失败");
                    }});
    }

    private void synLocalCustomerInfo(String baseInfo){

        userDetail.setIndiv_com_name(mEtCompanyName.getText().toString());
        userDetail.setIndiv_com_phn(mEtCompanyPhone.getText().toString());
        userDetail.setIndiv_com_addr(mEtDetailedAddress.getText().toString());
        userDetail.setIndiv_com_addr_ple(mTvCompanyAddress.getText().toString());
        mHandler.sendEmptyMessageDelayed(0,500);
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            IntentUtil.gotoActivity(CareerInfoActivity.this,FinanceInfoActivity.class);
        }
    };

    private boolean check(){

        if(isNull(mEtCompanyName)){
            T.showShortBottom("请输入单位名称");
            return false;
        }
        if(isNull(mEtCompanyPhone)){
            T.showShortBottom("请输入单位电话");
            return false;
        }
        if(isNull(mTvCompanyAddress)){
            T.showShortBottom("请选择单位地址");
            return false;
        }
        if(isNull(mEtDetailedAddress)){
            T.showShortBottom("请输入单位详细地址");
            return false;
        }
        if(isNull(mTvInitiationYear)){
            T.showShortBottom("请选择单位工作起始年");
            return false;
        }
        if(isNull(mTvCompanyNature)){
            T.showShortBottom("请选择单位性质");
            return false;
        }
        if(images.size()==0){
            T.showShortBottom("请选择工牌");
            return false;
        }

        return true;
    }

    /**
     * 设置还可以选择几张图片
     * @param num
     */
    private void setImageSelectLimit(int num){
        ImagePicker imagePicker = ImagePicker.getInstance();
        //选中数量限制
        imagePicker.setSelectLimit(num);
    }

    /**
     * 打开相册
     */
    private void openImageGridActivity(){
        Intent intent = new Intent(this, ImageGridActivity.class);
        // 是否是直接打开相机
        //intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS,true);
        startActivityForResult(intent, REQUEST_CODE_SELECT);
    }

    /**
     * 预览选中的相册
     */
    private void openImagePreviewActivity(int position,List<ImageItem> images){
        //打开预览
        Intent intentPreview = new Intent(this, MyImagePreviewDelActivity.class);
        // 但采用弱引用会导致预览弱引用直接返回空指针
        //DataHolder.getInstance().save(DataHolder.DH_CURRENT_IMAGE_FOLDER_ITEMS, images);
        //据说这样会导致大量图片崩溃
        intentPreview.putExtra("needDel",1);
        intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>)images );
        intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
        intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
        startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //选择回来
        if (requestCode == REQUEST_CODE_SELECT && resultCode == RESULT_CODE_ITEMS) {
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                ArrayList<ImageItem> images2 = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                images.addAll(images2);
                imagesAdapter.update(images);
            }
        }

        //预览回来
        else if(requestCode == REQUEST_CODE_PREVIEW &&resultCode==ImagePicker.RESULT_CODE_BACK){
            if (data != null){
                ArrayList<ImageItem> images2 = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                images.clear();
                if(images2!=null) {
                    images.addAll(images2);
                }
                if(images.size()==0){
                    userDetail.setBadge("");
                }
                imagesAdapter.update(images);
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
