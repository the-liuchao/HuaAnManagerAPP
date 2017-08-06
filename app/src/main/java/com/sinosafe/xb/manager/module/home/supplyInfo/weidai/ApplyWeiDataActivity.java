package com.sinosafe.xb.manager.module.home.supplyInfo.weidai;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sinosafe.xb.manager.BaseMainActivity;
import com.sinosafe.xb.manager.R;
import com.sinosafe.xb.manager.api.rxjava.RxHttpBaseResultFunc;
import com.sinosafe.xb.manager.api.rxjava.RxSchedulersHelper;
import com.sinosafe.xb.manager.api.rxjava.RxSubscriber;
import com.sinosafe.xb.manager.bean.BaseEntity;
import com.sinosafe.xb.manager.model.ClientModel;
import com.sinosafe.xb.manager.module.home.weidai.ImageSelectActivity;
import com.sinosafe.xb.manager.module.home.weidai.bean.ImagesInfoBean;
import com.sinosafe.xb.manager.utils.FileUtil;
import com.sinosafe.xb.manager.utils.MyUtils;
import com.sinosafe.xb.manager.utils.T;
import com.sinosafe.xb.manager.utils.fileupload.FileUploadPresenter;

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
import luo.library.base.utils.IntentUtil;
import luo.library.base.utils.MyLog;
import luo.library.base.utils.NetworkUtils;
import luo.library.base.widget.dialog.DialogMessage;

/**
 * 微贷补充资料
 */
public class ApplyWeiDataActivity extends BaseFragmentActivity {


    @BindView(R.id.tvIDCard)
    TextView mTvIDCard;
    @BindView(R.id.idCardLayout)
    RelativeLayout mIdCardLayout;
    @BindView(R.id.tvMarriage)
    TextView mTvMarriage;
    @BindView(R.id.marriageCertificateLayout)
    RelativeLayout mMarriageCertificateLayout;
    @BindView(R.id.tvFamilyRegister)
    TextView mTvFamilyRegister;
    @BindView(R.id.familyRegisterLayout)
    RelativeLayout mFamilyRegisterLayout;
    @BindView(R.id.tvResidenceCertificate)
    TextView mTvResidenceCertificate;
    @BindView(R.id.residenceCertificateLayout)
    RelativeLayout mResidenceCertificateLayout;
    @BindView(R.id.tvVehicleCertification)
    TextView mTvVehicleCertification;
    @BindView(R.id.vehicleCertificationLayout)
    RelativeLayout mVehicleCertificationLayout;
    @BindView(R.id.tvManagerPlaceContract)
    TextView mTvManagerPlaceContract;
    @BindView(R.id.managerPlaceContractLayout)
    RelativeLayout mManagerPlaceContractLayout;
    @BindView(R.id.tvCustomerContract)
    TextView mTvCustomerContract;
    @BindView(R.id.customerContractLayout)
    RelativeLayout mCustomerContractLayout;
    @BindView(R.id.tvManagerPlaceBill)
    TextView mTvManagerPlaceBill;
    @BindView(R.id.managerPlaceBillLayout)
    RelativeLayout mManagerPlaceBillLayout;
    @BindView(R.id.tvResponsibilityGuarantee)
    TextView mTvResponsibilityGuarantee;
    @BindView(R.id.responsibilityGuaranteeLayout)
    RelativeLayout mResponsibilityGuaranteeLayout;
    @BindView(R.id.tvGuaranteeIDCardNum)
    TextView mTvGuaranteeIDCardNum;
    @BindView(R.id.GuaranteeIDCardNumLayout)
    RelativeLayout mGuaranteeIDCardNumLayout;
    @BindView(R.id.tvGuaranteeWorkCertificate)
    TextView mTvGuaranteeWorkCertificate;
    @BindView(R.id.GuaranteeWorkCertificateLayout)
    RelativeLayout mGuaranteeWorkCertificateLayout;
    @BindView(R.id.tvPledgeData)
    TextView mTvPledgeData;
    @BindView(R.id.pledgeDataLayout)
    RelativeLayout mPledgeDataLayout;
    @BindView(R.id.tvOtherAssetCertificate)
    TextView mTvOtherAssetCertificate;
    @BindView(R.id.otherAssetCertificateLayout)
    RelativeLayout mOtherAssetCertificateLayout;
    @BindView(R.id.tvSignCredit)
    TextView mTvSignCredit;
    @BindView(R.id.signCreditLayout)
    RelativeLayout mSignCreditLayout;
    @BindView(R.id.tvFieldInvestigation)
    TextView mTvFieldInvestigation;
    @BindView(R.id.fieldInvestigationLayout)
    RelativeLayout mFieldInvestigationLayout;
    @BindView(R.id.tvGuaranteePhoto)
    TextView mTvGuaranteePhoto;
    @BindView(R.id.guaranteePhotoLayout)
    RelativeLayout mGuaranteePhotoLayout;
    @BindView(R.id.tvSignPhoto)
    TextView mTvSignPhoto;
    @BindView(R.id.signPhotoLayout)
    RelativeLayout mSignPhotoLayout;
    @BindView(R.id.tvPersonalCredit)
    TextView mTvPersonalCredit;
    @BindView(R.id.personalCreditLayout)
    RelativeLayout mPersonalCreditLayout;
    @BindView(R.id.tvBusinessRelated)
    TextView mTvBusinessRelated;
    @BindView(R.id.businessRelatedLayout)
    RelativeLayout mBusinessRelatedLayout;
    @BindView(R.id.tvGuarantorOfBank)
    TextView mTvGuarantorOfBank;
    @BindView(R.id.guarantorOfBankLayout)
    RelativeLayout mGuarantorOfBankLayout;
    @BindView(R.id.tvBusinessLicense)
    TextView mTvBusinessLicense;
    @BindView(R.id.businessLicenseLayout)
    RelativeLayout mBusinessLicenseLayout;
    @BindView(R.id.tvTaxRegistrationCertificate)
    TextView mTvTaxRegistrationCertificate;
    @BindView(R.id.taxRegistrationCertificateLayout)
    RelativeLayout mTaxRegistrationCertificateLayout;
    @BindView(R.id.tvOrganizationCodeCertificate)
    TextView mTvOrganizationCodeCertificate;
    @BindView(R.id.organizationCodeCertificateLayout)
    RelativeLayout mOrganizationCodeCertificateLayout;
    @BindView(R.id.tvSpecialIndustryLicense)
    TextView mTvSpecialIndustryLicense;
    @BindView(R.id.specialIndustryLicenseLayout)
    RelativeLayout mSpecialIndustryLicenseLayout;
    @BindView(R.id.tvArticlesOfAssociation)
    TextView mTvArticlesOfAssociation;
    @BindView(R.id.articlesOfAssociationLayout)
    RelativeLayout mArticlesOfAssociationLayout;
    @BindView(R.id.tvCapitalVerificationReport)
    TextView mTvCapitalVerificationReport;
    @BindView(R.id.capitalVerificationReportLayout)
    RelativeLayout mCapitalVerificationReportLayout;
    @BindView(R.id.btn_submit)
    Button mBtnSubmit;

    //图片类型
    int type = -1;
    private String fileType;
    //该类型图片是否已添加
    boolean isExit = false;
    //当前类型图片
    private ImagesInfoBean imagesInfoBean;
    private List<ImagesInfoBean> imagesInfoBeens = new ArrayList<>();
    private List<ImagesInfoBean> temp = new ArrayList<>();
    private List<TextView> textViews = new ArrayList<>();
    private List<String> tips = new ArrayList<>();

    private FileUploadPresenter fileUploadPresenter;
    //当前上传的图片索引
    private int currentIndex = 0;
    //资料是否上传完成
    private boolean upLoadSuccess = false;
    private String serno;
    //当前图片文件中文描述
    private String currentPhotoDes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weidai_data_list);
        ButterKnife.bind(this);

        fileUploadPresenter = new FileUploadPresenter(this);
        initView();
    }

    private void initView() {
        setTitleText("补充资料");
        textViews.add(mTvIDCard);textViews.add(mTvMarriage);textViews.add(mTvFamilyRegister);
        textViews.add(mTvResidenceCertificate);textViews.add(mTvVehicleCertification);textViews.add(mTvManagerPlaceContract);
        textViews.add(mTvCustomerContract);textViews.add(mTvManagerPlaceBill);textViews.add(mTvResponsibilityGuarantee);
        textViews.add(mTvGuaranteeIDCardNum);textViews.add(mTvGuaranteeWorkCertificate);textViews.add(mTvPledgeData);
        textViews.add(mTvOtherAssetCertificate);textViews.add(mTvSignCredit);textViews.add(mTvFieldInvestigation);
        textViews.add(mTvGuaranteePhoto);textViews.add(mTvSignPhoto);textViews.add(mTvPersonalCredit);
        textViews.add(mTvBusinessRelated);textViews.add(mTvGuarantorOfBank);textViews.add(mTvBusinessLicense);
        textViews.add(mTvTaxRegistrationCertificate);textViews.add(mTvOrganizationCodeCertificate);textViews.add(mTvSpecialIndustryLicense);
        textViews.add(mTvArticlesOfAssociation);textViews.add(mTvCapitalVerificationReport);


        tips.add("借款人身份证");
        tips.add("婚姻证明文件");
        tips.add("借款人户口本");
        tips.add("居住证明");
        tips.add("车辆证明");
        tips.add("经营场所租赁合同/产权证明/转让合同");
        tips.add("客户经营台账、单据、合同等");
        tips.add("经营场所水电费单等固定支出单据");
        tips.add("连带责任担保书");
        tips.add("担保人身份证");
        tips.add("担保人工作证明/营业执照");
        tips.add("抵质押资料");
        tips.add("其他资产证明");
        tips.add("签署征信授权书照片");
        tips.add("现场调查照片");
        tips.add("担保人照片");
        tips.add("签约照片");
        tips.add("个人征信授权书");

        serno = getIntent().getStringExtra("serno");
        //赋值
        if(imagesInfoBeens.size()==0){
            for(int j=0;j<26;j++)
                imagesInfoBeens.add(new ImagesInfoBean());
        }else{
            for(int j=imagesInfoBeens.size();j<26;j++)
                imagesInfoBeens.add(new ImagesInfoBean());
        }
    }

    /**
     * 判断文件是否合法存在
     * @param paths
     * @return
     */
    private String fileExit(String paths){

        if(paths==null&&"".equals(paths))
            return "";
        String pathArr[] = paths.split(",");
        String pathsTemp = "";
        for (int j=0;j<pathArr.length;j++){
            String path = pathArr[j];
            if(new File(path).exists())
                pathsTemp += path+",";
        }
        if(pathsTemp.endsWith(","))
            pathsTemp = pathsTemp.substring(0,pathsTemp.length()-1);

        return pathsTemp;
    }


    @OnClick({R.id.idCardLayout, R.id.marriageCertificateLayout, R.id.familyRegisterLayout,
            R.id.residenceCertificateLayout, R.id.vehicleCertificationLayout,
            R.id.managerPlaceContractLayout, R.id.customerContractLayout, R.id.managerPlaceBillLayout,
            R.id.responsibilityGuaranteeLayout, R.id.GuaranteeIDCardNumLayout, R.id.GuaranteeWorkCertificateLayout,
            R.id.pledgeDataLayout, R.id.otherAssetCertificateLayout, R.id.signCreditLayout,
            R.id.fieldInvestigationLayout, R.id.guaranteePhotoLayout, R.id.signPhotoLayout,
            R.id.personalCreditLayout, R.id.businessRelatedLayout, R.id.guarantorOfBankLayout,
            R.id.businessLicenseLayout, R.id.taxRegistrationCertificateLayout, R.id.organizationCodeCertificateLayout,
            R.id.specialIndustryLicenseLayout, R.id.articlesOfAssociationLayout,
            R.id.capitalVerificationReportLayout,R.id.btn_submit})
    public void onViewClicked(View view) {

        Bundle bundle = new Bundle();
        switch (view.getId()) {
            //借款人身份证
            case R.id.idCardLayout:
                type = 1;
                fileType = "WDCP_00201";
                bundle.putString("title", "借款人身份证");
                currentPhotoDes = "借款人身份证";
                break;
            //婚姻证明文件
            case R.id.marriageCertificateLayout:
                type = 2;
                fileType = "WDCP_00201";
                bundle.putString("title", "婚姻证明文件");
                currentPhotoDes = "婚姻证明文件";
                break;
            //借款人户口本
            case R.id.familyRegisterLayout:
                type = 3;
                fileType = "WDCP_00201";
                bundle.putString("title", "借款人户口本");
                currentPhotoDes = "借款人户口本";
                break;
            //居住证明
            case R.id.residenceCertificateLayout:
                type = 4;
                fileType = "WDCP_00201";
                bundle.putString("title", "居住证明");
                currentPhotoDes = "居住证明";
                break;
            //车辆证明
            case R.id.vehicleCertificationLayout:
                type = 5;
                fileType = "WDCP_00201";
                bundle.putString("title", "车辆证明");
                currentPhotoDes = "车辆证明";
                break;
            //经营场所租赁合同/产权证明/转让合同
            case R.id.managerPlaceContractLayout:
                type = 6;
                fileType = "WDCP_00401";
                bundle.putString("title", "经营场所租赁合同/产权证明/转让合同");
                currentPhotoDes = "经营场所租赁合同+产权证明+转让合同";
                break;
            //客户经营台账、单据、合同等
            case R.id.customerContractLayout:
                type = 7;
                fileType = "WDCP_00401";
                bundle.putString("title", "客户经营台账、单据、合同等");
                currentPhotoDes = "客户经营台账+单据+合同等";
                break;
            //经营场所水电费单等固定支出单据
            case R.id.managerPlaceBillLayout:
                type = 8;
                fileType = "WDCP_00401";
                bundle.putString("title", "经营场所水电费单等固定支出单据");
                currentPhotoDes = "经营场所水电费单等固定支出单据";
                break;
            //连带责任担保书
            case R.id.responsibilityGuaranteeLayout:
                type = 9;
                fileType = "WDCP_00501";
                bundle.putString("title", "连带责任担保书");
                currentPhotoDes = "连带责任担保书";
                break;
            //担保人身份证
            case R.id.GuaranteeIDCardNumLayout:
                type = 10;
                fileType = "WDCP_00501";
                bundle.putString("title", "担保人身份证");
                currentPhotoDes = "担保人身份证";
                break;
            //担保人工作证明/营业执照
            case R.id.GuaranteeWorkCertificateLayout:
                type = 11;
                fileType = "WDCP_00501";
                bundle.putString("title", "担保人工作证明/营业执照");
                currentPhotoDes = "担保人工作证明+营业执照";
                break;
            //抵质押资料
            case R.id.pledgeDataLayout:
                type = 12;
                fileType = "WDCP_00501";
                bundle.putString("title", "抵质押资料");
                currentPhotoDes = "抵质押资料";
                break;
            //其他资产证明
            case R.id.otherAssetCertificateLayout:
                type = 13;
                fileType = "WDCP_00501";
                bundle.putString("title", "其他资产证明");
                currentPhotoDes = "其他资产证明";
                break;
            //签署征信授权书照片
            case R.id.signCreditLayout:
                type = 14;
                fileType = "WDCP_00801";
                bundle.putString("title", "签署征信授权书照片");
                currentPhotoDes = "签署征信授权书照片";
                break;
            //现场调查照片
            case R.id.fieldInvestigationLayout:
                type = 15;
                fileType = "WDCP_00801";
                bundle.putString("title", "现场调查照片");
                currentPhotoDes = "现场调查照片";
                break;
            //担保人照片
            case R.id.guaranteePhotoLayout:
                type = 16;
                fileType = "WDCP_00801";
                bundle.putString("title", "担保人照片");
                currentPhotoDes = "担保人照片";
                break;
            //签约照片
            case R.id.signPhotoLayout:
                type = 17;
                fileType = "WDCP_00801";
                bundle.putString("title", "签约照片");
                currentPhotoDes = "签约照片";
                break;
            //个人征信授权书
            case R.id.personalCreditLayout:
                type = 18;
                fileType = "WDCP_00301";
                bundle.putString("title", "个人征信授权书");
                currentPhotoDes = "个人征信授权书";
                break;
            //经营相关近一年银行流水
            case R.id.businessRelatedLayout:
                type = 19;
                fileType = "WDCP_00401";
                bundle.putString("title", "经营相关近一年银行流水");
                currentPhotoDes = "经营相关近一年银行流水";
                break;
            //担保人近半年银行流水
            case R.id.guarantorOfBankLayout:
                type = 20;
                fileType = "WDCP_00501";
                bundle.putString("title", "担保人近半年银行流水");
                currentPhotoDes = "担保人近半年银行流水";
                break;
            //营业执照
            case R.id.businessLicenseLayout:
                type = 21;
                fileType = "WDCP_00201";
                bundle.putString("title", "营业执照");
                currentPhotoDes = "营业执照";
                break;
            //税务登记证
            case R.id.taxRegistrationCertificateLayout:
                type = 22;
                fileType = "WDCP_00201";
                bundle.putString("title", "税务登记证");
                currentPhotoDes = "税务登记证";
                break;
            //组织机构代码证
            case R.id.organizationCodeCertificateLayout:
                type = 23;
                fileType = "WDCP_00201";
                bundle.putString("title", "组织机构代码证");
                currentPhotoDes = "组织机构代码证";
                break;
            //特殊行业许可证
            case R.id.specialIndustryLicenseLayout:
                type = 24;
                fileType = "WDCP_00201";
                bundle.putString("title", "特殊行业许可证");
                currentPhotoDes = "特殊行业许可证";
                break;
            //公司章程
            case R.id.articlesOfAssociationLayout:
                type = 25;
                fileType = "WDCP_00201";
                bundle.putString("title", "公司章程");
                currentPhotoDes = "公司章程";
                break;
            //验资报告/询证函
            case R.id.capitalVerificationReportLayout:
                type = 26;
                fileType = "WDCP_00201";
                bundle.putString("title", "验资报告/询证函");
                currentPhotoDes = "验资报告+询证函";
                break;
            //提交
            case R.id.btn_submit:
                nextBtn();
                break;
        }
        if(type!=-1){
            bundle.putString("path",findImagesPathByType());
            IntentUtil.gotoActivityForResult(this,ImageSelectActivity.class,bundle,1000);
        }
    }

    /**
     * 提交
     */
    private void nextBtn() {

        type = -1;
        if(!upLoadSuccess)
            beforeUploadMicroCreditImage();
        else {
            showWithStatus("保存中...");
            imageDocUploadReceipt();
        }
    }


    private String findImagesPathByType(){

        String pathsStr = "";
        for (int i=0;i<imagesInfoBeens.size();i++){
            ImagesInfoBean imagesInfoBean = imagesInfoBeens.get(i);
            if(imagesInfoBean!=null && imagesInfoBean.getType()==type){
                this.imagesInfoBean = imagesInfoBean;
                pathsStr += imagesInfoBean.getPaths();
                isExit = true;
                break;
            }else{
                isExit = false;
            }
        }
        return pathsStr;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1000&&resultCode==RESULT_OK){
            String pathsStr = data.getStringExtra("path");
            //已存在
            if(isExit){
                imagesInfoBean.setPaths(pathsStr);
                imagesInfoBean.setPhotoDes(currentPhotoDes);
                imagesInfoBeens.remove(imagesInfoBean);
                imagesInfoBeens.add(type-1,imagesInfoBean);
                if("".equals(pathsStr)){
                    setTipsTextColor(0);
                }else{
                    setTipsTextColor(1);
                }
            }
            //还没添加
            else {
                ImagesInfoBean imagesInfoBeanNew = new ImagesInfoBean();
                imagesInfoBeanNew.setPaths(pathsStr);
                imagesInfoBeanNew.setPhotoDes(currentPhotoDes);
                imagesInfoBeanNew.setType(type);
                imagesInfoBeanNew.setFileType(fileType);
                imagesInfoBeens.remove(type-1);
                imagesInfoBeens.add(type-1,imagesInfoBeanNew);
                if("".equals(pathsStr)){
                    setTipsTextColor(0);
                }else{
                    setTipsTextColor(1);
                }
                //imagesDataReassembly();
            }
        }
    }


    /**
     * 图片数据重装，防止顺序间出现null的现象，笨方法
     */
    private void imagesDataReassembly(){
        temp.clear();
        temp.addAll(imagesInfoBeens);
        imagesInfoBeens.clear();
        //移除空的对象
        List<ImagesInfoBean> nullArr = new ArrayList<ImagesInfoBean>();
        nullArr.add(null);
        temp.removeAll(nullArr);
        imagesInfoBeens.addAll(temp);
        for(int j=imagesInfoBeens.size();j<26;j++)
            imagesInfoBeens.add(null);
    }

    /**
     * 更新添加提示
     * @param flag
     */
    private void setTipsTextColor(int flag){

        //未添加
        if(flag==0){
            textViews.get(type-1).setText("点击添加");
            textViews.get(type-1).setTextColor(Color.parseColor("#b5b7c4"));
        }
        //已添加
        else {
            textViews.get(type-1).setText("已添加");
            textViews.get(type-1).setTextColor(Color.parseColor("#545564"));
        }
    }

    /**
     * 微贷影像资料上传前
     */
    private void beforeUploadMicroCreditImage(){

        //type 1--18必填
        if(hasSelect()){
            //移除无效的对象
            MyUtils.removeInvalidData(imagesInfoBeens);
            //使用网络提示
            if(!NetworkUtils.isWifi(this)){
                showNetTipsDialog();
            }else{
                showWithStatus("上传中...");
                uploadingMicroCreditImage();
            }
        }
        else{
            T.showShortBottom("亲,您还没选择待补充资料哦!");
        }
    }

    /**
     * 上传前网络提示
     */
    private void showNetTipsDialog(){
        String filesSize = FileUtil.getFileOrFilesSize(imagesInfoBeens);
        new DialogMessage(this)
                .setMess("您正在使用非WiFi网络，资料大约"+filesSize +
                        "，上传将产生流量费用，是否继续上传?")
                .setConfirmTips("稍后处理")
                .setOtherConfirmTips("继续上传")
                .setConfirmListener(new DialogMessage.OnConfirmListener() {
                    @Override
                    public void onConfirm() {

                    }
                })
                .setOtherConfirmListener(new DialogMessage.OnOtherConfirmListener() {
                    @Override
                    public void onOtherConfirm() {
                        showWithStatus("上传中...");
                        uploadingMicroCreditImage();
                    }
                }).show();
    }


    private boolean hasSelect(){
        boolean hasSelect = false;
        for(int i=0;i<imagesInfoBeens.size();i++){
            ImagesInfoBean imagesInfoBean = imagesInfoBeens.get(i);
            if(imagesInfoBean!=null&&imagesInfoBean.getPaths()!=null&&!"".equals(imagesInfoBean.getPaths())){
                hasSelect = true;
                break;
            }
        }

       return hasSelect;
    }

    /**
     * 正在上传微贷影像资料
     */
    private void uploadingMicroCreditImage(){

        //上传完成
        if(currentIndex==imagesInfoBeens.size()){
            T.showShortBottom("恭喜您,资料上传完成!");
            upLoadSuccess = true;
            imageDocUploadReceipt();
        }else{
            ImagesInfoBean imagesInfoBean = imagesInfoBeens.get(currentIndex);
            //是否上传成功
            if(!imagesInfoBean.isUploadSuccess()){
                List<String> upfiles = new ArrayList<>();
                List<String> desList = new ArrayList<>();
                List<String> photoDes = new ArrayList<>();
                String pathArr[] = imagesInfoBean.getPaths().split(",");
                for(int i=0;i<pathArr.length;i++){
                    upfiles.add(pathArr[i]);
                    desList.add(imagesInfoBean.getFileType());
                    photoDes.add(imagesInfoBean.getPhotoDes());
                }
                String prdCode = "WDCP";
                int index = imagesInfoBean.getType() - 1;
               /* if(index<10)
                    fileUploadPresenter.setFileName("00"+index);
                else
                    fileUploadPresenter.setFileName("0"+index);*/

                fileUploadPresenter.fileUpLoad(upfiles, desList, prdCode, serno,photoDes);
            }
            else{
                currentIndex ++;
                uploadingMicroCreditImage();
            }
        }
    }

    @Override
    public void uploadSuccess(List<String> fileIds) {
        super.uploadSuccess(fileIds);
        imagesInfoBeens.get(currentIndex).setUploadSuccess(true);
        currentIndex ++;
        uploadingMicroCreditImage();
    }




    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            setResult(RESULT_OK);
            finish();
        }
    };

    /**
     * 上传影像资料回执
     */
    private void imageDocUploadReceipt(){
        Map<String,String> map = new HashMap<>();
        map.put("token",BaseMainActivity.loginUserBean.getToken());
        map.put("serno",serno);
        map.put("image_doc_type","0002");
        map.put("terminal_type","01");
        map.put("status","0");
        ClientModel.imageDocUploadReceipt(map)
            .timeout(20, TimeUnit.SECONDS)
            .compose(RxSchedulersHelper.<BaseEntity>io_main())
            .map(new RxHttpBaseResultFunc())
            .subscribe(new RxSubscriber<BaseEntity>() {
                @Override
                public void _onNext(BaseEntity entity) {
                    T.showShortBottom("回执成功");
                    closeSVProgressHUD();
                    mHandler.sendEmptyMessageDelayed(0,200);
                    MyLog.e("上传影像回执_onNext===="+entity.getMsg());
                }
                @Override
                public void _onError(String msg) {
                    T.showShortBottom("回执失败,请重试");
                    closeSVProgressHUD();
                    MyLog.e("上传影像回执_onError===="+msg);
                }});
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
