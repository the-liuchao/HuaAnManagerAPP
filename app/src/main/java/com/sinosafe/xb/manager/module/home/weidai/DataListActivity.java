package com.sinosafe.xb.manager.module.home.weidai;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.google.gson.Gson;
import com.sinosafe.xb.manager.APP;
import com.sinosafe.xb.manager.BaseMainActivity;
import com.sinosafe.xb.manager.MainActivity;
import com.sinosafe.xb.manager.R;
import com.sinosafe.xb.manager.api.rxjava.RxHttpBaseResultFunc;
import com.sinosafe.xb.manager.api.rxjava.RxSchedulersHelper;
import com.sinosafe.xb.manager.api.rxjava.RxSubscriber;
import com.sinosafe.xb.manager.bean.BaseEntity;
import com.sinosafe.xb.manager.model.ClientModel;
import com.sinosafe.xb.manager.module.home.weidai.bean.ApplyingBean;
import com.sinosafe.xb.manager.module.home.weidai.bean.DataListBean;
import com.sinosafe.xb.manager.module.home.weidai.bean.ImagesInfoBean;
import com.sinosafe.xb.manager.module.home.xiaofeidai.supplementInfo.LoadApplyResultActivity;
import com.sinosafe.xb.manager.utils.Constant;
import com.sinosafe.xb.manager.utils.FileUtil;
import com.sinosafe.xb.manager.utils.MyAMapLocUtils;
import com.sinosafe.xb.manager.utils.MyUtils;
import com.sinosafe.xb.manager.utils.T;
import com.sinosafe.xb.manager.utils.fileupload.FileUploadPresenter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.OnClick;
import luo.library.base.base.BaseDb;
import luo.library.base.utils.GsonUtil;
import luo.library.base.utils.IntentUtil;
import luo.library.base.utils.MyLog;
import luo.library.base.utils.NetworkUtils;
import luo.library.base.widget.dialog.DialogMessage;

import static com.sinosafe.xb.manager.module.home.xiaofeidai.XiaoFeiDaiApplyActivity.loanUserInfo;
import static luo.library.base.base.BaseDb.saveOrUpdate;

/**
 * 资料清单
 */
public class DataListActivity extends DataListBase {


    //当前图片文件中文描述
    private String currentPhotoDes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_list);
        ButterKnife.bind(this);

        fileUploadPresenter = new FileUploadPresenter(this);
        initView();
    }

    private void initView() {
        setTitleText("补充资料");
        textViews.add(mTvIDCard);
        textViews.add(mTvMarriage);
        textViews.add(mTvFamilyRegister);
        textViews.add(mTvResidenceCertificate);
        textViews.add(mTvVehicleCertification);
        textViews.add(mTvManagerPlaceContract);
        textViews.add(mTvCustomerContract);
        textViews.add(mTvManagerPlaceBill);
        textViews.add(mTvResponsibilityGuarantee);
        textViews.add(mTvGuaranteeIDCardNum);
        textViews.add(mTvGuaranteeWorkCertificate);
        textViews.add(mTvPledgeData);
        textViews.add(mTvOtherAssetCertificate);
        textViews.add(mTvSignCredit);
        textViews.add(mTvFieldInvestigation);
        textViews.add(mTvGuaranteePhoto);
        textViews.add(mTvSignPhoto);
        textViews.add(mTvPersonalCredit);
        textViews.add(mTvBusinessRelated);
        textViews.add(mTvGuarantorOfBank);
        textViews.add(mTvBusinessLicense);
        textViews.add(mTvTaxRegistrationCertificate);
        textViews.add(mTvOrganizationCodeCertificate);
        textViews.add(mTvSpecialIndustryLicense);
        textViews.add(mTvArticlesOfAssociation);
        textViews.add(mTvCapitalVerificationReport);

        textViews222.add(mTv110);
        textViews222.add(tvMarriageStatus);
        textViews222.add(mTv112);
        textViews222.add(tvResidence);
        textViews222.add(tvCarStatus);
        textViews222.add(mTv115);
        textViews222.add(mTv116);
        textViews222.add(mTv117);
        textViews222.add(mTv118);
        textViews222.add(mTv119);
        textViews222.add(mTv120);
        textViews222.add(mTv121);
        textViews222.add(mTv122);
        textViews222.add(mTv123);
        textViews222.add(mTv124);
        textViews222.add(mTv125);
        textViews222.add(mTv126);
        textViews222.add(mTv127);
        textViews222.add(mTv128);
        textViews222.add(mTv129);
        textViews222.add(mTv130);
        textViews222.add(mTv131);
        textViews222.add(mTv132);
        textViews222.add(mTv133);
        textViews222.add(mTv134);
        textViews222.add(mTv135);

        //赋值
        for (int j = 0; j < 26; j++)
            imagesInfoBeens.add(new ImagesInfoBean());

        dataListBean = (DataListBean) getIntent().getSerializableExtra("dataListBean");
        applyingBean = (ApplyingBean) getIntent().getSerializableExtra("applyingBean");
        if (applyingBean != null || dataListBean != null) {
            detail_id = applyingBean.getDetail_id();
            prd_id = applyingBean.getPrdId();
            serno = applyingBean.getSerno();

            String marriageStatus = applyingBean.getIndiv_mar_st();
            if ("20".equals(marriageStatus)||"21".equals(marriageStatus)||"23".equals(marriageStatus)) {
                maritalStatus = true;
            }
            if ("1".equals(applyingBean.getCarl_st())) {
                carStatus = true;
            }
            if ("1".equals(applyingBean.getHouse_st())) {
                houseStatus = true;
            }
            if (dataListBean != null) {
                List<ImagesInfoBean> imageList = GsonUtil.GsonToList(dataListBean.getDataList(), ImagesInfoBean.class);
                //imagesInfoBeens.addAll(imageList);
                for (int i = 0; i < imageList.size(); i++) {
                    ImagesInfoBean imagesInfoBean = imageList.get(i);
                    type = imagesInfoBean.getType();
                    //判断是否存在
                    String paths = fileExit(imagesInfoBean.getPaths());
                    imagesInfoBean.setPaths(paths);
                    if (!"".equals(paths))
                        setTipsTextColor(1);
                    else
                        setTipsTextColor(0);

                    imagesInfoBeens.remove(imagesInfoBeens.get(type-1));
                    imagesInfoBeens.add(type-1,imagesInfoBean);
                }
            }
        } else {
            detail_id = loanUserInfo.getUser_detail().getDetail_id();
            prd_id = loanUserInfo.getUser_detail().getPrd_id();
            serno = loanUserInfo.getUser_detail().getSerno();

            String marriageStatus = loanUserInfo.getUser_detail().getIndiv_mar_st();

            if ("20".equals(marriageStatus)||"21".equals(marriageStatus)||"23".equals(marriageStatus)) {
                maritalStatus = true;
            }
            if ("1".equals(loanUserInfo.getUser_detail().getCarl_st())) {
                carStatus = true;
            }
            if ("1".equals(loanUserInfo.getUser_detail().getHouse_st())) {
                houseStatus = true;
            }
        }
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
        tips.add("经营相关近一年银行流水");
        tips.add("担保人近半年银行流水");
        tips.add("营业执照");
        tips.add("税务登记证");



        Drawable drawable = getResources().getDrawable(R.mipmap.icon_red_star);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        if (maritalStatus) {
            tvMarriageStatus.setCompoundDrawables(drawable, null, null, null);
        }
        if (carStatus) {
            tvCarStatus.setCompoundDrawables(drawable, null, null, null);
        }
        if (houseStatus) {
            tvResidence.setCompoundDrawables(drawable, null, null, null);
        }

        /*//赋值
        if (imagesInfoBeens.size() == 0) {
            for (int j = 0; j < 26; j++)
                imagesInfoBeens.add(new ImagesInfoBean());
        }
        //
        else {
            for (int j = imagesInfoBeens.size(); j < 26; j++)
                imagesInfoBeens.add(new ImagesInfoBean());
        }*/
        //定位
        aMapLocUtils = new MyAMapLocUtils(this);
        showWithStatus("");
        aMapLocUtils.startLocation();
    }

    /**
     * 判断文件是否合法存在
     *
     * @param paths
     * @return
     */
    private String fileExit(String paths) {

        if (paths == null && "".equals(paths))
            return "";
        String pathArr[] = paths.split(",");
        String pathsTemp = "";
        for (int j = 0; j < pathArr.length; j++) {
            String path = pathArr[j];
            if (new File(path).exists())
                pathsTemp += path + ",";
        }
        if (pathsTemp.endsWith(","))
            pathsTemp = pathsTemp.substring(0, pathsTemp.length() - 1);

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
            R.id.capitalVerificationReportLayout, R.id.btn_save, R.id.btn_submit})
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
            //保存
            case R.id.btn_save:
                type = -1;
                showWithStatus("保存中...");
                Map<String, String> map = new HashMap<>();
                map.put("token", BaseMainActivity.loginUserBean.getToken());
                map.put("serno", serno);
                saveLoanMsg(map);
                break;
            //提交
            case R.id.btn_submit:
                nextBtn();
                break;
        }
        MyLog.e("点击选择图片 索引type===" + type);
        if (type != -1) {
            bundle.putString("path", findImagesPathByType());
            bundle.putInt("type", 1);
            IntentUtil.gotoActivityForResult(this, ImageSelectActivity.class, bundle, 1000);
        }
    }

    /**
     * 提交
     */
    private void nextBtn() {
        if (APP.lat == 0 || APP.lng == 0) {
            new DialogMessage(this).setMess("亲,定位失败了~请重新定位.")
                    .setConfirmListener(new DialogMessage.OnConfirmListener() {
                        @Override
                        public void onConfirm() {
                            showWithStatus("");
                            aMapLocUtils.startLocation();
                        }
                    }).show();
            return;
        }
        type = -1;
        if (!upLoadSuccess)
            beforeUploadMicroCreditImage();
        else {
            showWithStatus("申请中...");
            if (hasApplying)
                imageDocUploadReceipt();
            else
                setManagerLoanApplyMap();
        }
    }


    private String findImagesPathByType() {

        String pathsStr = "";
        for (int i = 0; i < imagesInfoBeens.size(); i++) {
            ImagesInfoBean imagesInfoBean = imagesInfoBeens.get(i);
            if (imagesInfoBean != null && imagesInfoBean.getType() == type) {
                this.imagesInfoBean = imagesInfoBean;
                pathsStr += imagesInfoBean.getPaths();
                isExit = true;
                break;
            } else {
                isExit = false;
            }
        }
        return pathsStr;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == RESULT_OK) {
            String pathsStr = data.getStringExtra("path");
            MyLog.e("图片存在isExit===" + isExit);
            //已存在
            if (isExit) {
                imagesInfoBean.setPaths(pathsStr);
                imagesInfoBean.setPhotoDes(currentPhotoDes);
                imagesInfoBeens.remove(imagesInfoBean);
                imagesInfoBeens.add(type - 1, imagesInfoBean);
                if ("".equals(pathsStr)) {
                    setTipsTextColor(0);
                } else {
                    setTipsTextColor(1);
                }
            }
            //还没添加
            else {
                MyLog.e("保存图片索引/类型type===" + (type - 1));
                ImagesInfoBean imagesInfoBeanNew = new ImagesInfoBean();
                imagesInfoBeanNew.setPaths(pathsStr);
                imagesInfoBeanNew.setPhotoDes(currentPhotoDes);
                imagesInfoBeanNew.setType(type);
                imagesInfoBeanNew.setFileType(fileType);
                //先移除当前
                imagesInfoBeens.remove(type - 1);
                imagesInfoBeens.add(type - 1, imagesInfoBeanNew);
                if ("".equals(pathsStr)) {
                    setTipsTextColor(0);
                } else {
                    setTipsTextColor(1);
                }
                //每次添加回来都要清理下连续之间出现的null
                //imagesDataReassembly();
            }
        }
    }


    /**
     * 图片数据重装，防止顺序间出现null的现象，笨方法
     */
    private void imagesDataReassembly() {
        temp.clear();
        temp.addAll(imagesInfoBeens);
        imagesInfoBeens.clear();
        //移除空的对象
        List<ImagesInfoBean> nullArr = new ArrayList<ImagesInfoBean>();
        nullArr.add(null);
        temp.removeAll(nullArr);
        imagesInfoBeens.addAll(temp);
        for (int j = imagesInfoBeens.size(); j < 26; j++)
            imagesInfoBeens.add(null);
    }

    /**
     * 更新添加提示
     *
     * @param flag
     */
    private void setTipsTextColor(int flag) {

        //未添加
        if (flag == 0) {
            textViews.get(type - 1).setText("点击添加");
            textViews.get(type - 1).setTextColor(Color.parseColor("#b5b7c4"));
        }
        //已添加
        else {
            textViews.get(type - 1).setText("已添加");
            textViews.get(type - 1).setTextColor(Color.parseColor("#545564"));
        }
    }

    /**
     * 保存图片、申请信息、跳转
     */
    private void saveImages() {
        savingData();
        T.showShortBottom("保存成功");

        IntentUtil.gotoActivityAndFinish(this, MainActivity.class);
        Intent intent = new Intent(Constant.TO_LOAD_APPLYING);
        //申请保存
        if (loanUserInfo != null)
            intent.putExtra("type", 1);
        sendBroadcast(intent);
    }

    /**
     * 保存图片数据
     */
    private void savingData() {
        //申请信息
        if (dataListBean == null) {
            dataListBean = new DataListBean();
            dataListBean.setSerno(serno);
        }
        //资料是否完整
        if (beforeSaveMicroCreditImage())
            dataListBean.setComplete(1);
        else {
            dataListBean.setComplete(0);
        }
        //移除无效的对象
        MyUtils.removeInvalidData(imagesInfoBeens);

        Gson gson = new Gson();
        String jsonStr = gson.toJson(imagesInfoBeens);
        dataListBean.setDataList(jsonStr);
        //已存在，更新
        if (dataListBean.getId() != -1) {
            saveOrUpdate(dataListBean);
        }
        //不存在，保存
        else {
            BaseDb.add(dataListBean);
            List<DataListBean> dataList = BaseDb.find(DataListBean.class);
            tempDataListBean = dataList.get(dataList.size()-1);
        }
    }


    /**
     * 微贷影像资料上传前
     */
    private void beforeUploadMicroCreditImage() {
        //type 1--18必填
        if (imagesInfoBeens.size() > 0 && hasSelect()) {

            //精细验证必选是否全部添加
            for (int i = 0; i < imagesInfoBeens.size(); i++) {
                ImagesInfoBean imagesInfoBean = imagesInfoBeens.get(i);
                if (imagesInfoBean.getType() == -1) {
                    Drawable drawables[] = textViews222.get(i).getCompoundDrawables();
                    if (drawables[0] != null) {
                        T.showShortBottom("请添加" + tips.get(i));
                        return;
                    }
                }
                //还没选择
                if ((imagesInfoBean.getType() - 1 != i || imagesInfoBean.getPaths() == null || "".equals(imagesInfoBean.getPaths()))) {

                    Drawable drawables[] = textViews222.get(i).getCompoundDrawables();
                    if (drawables[0] != null) {
                        T.showShortBottom("请添加" + tips.get(i));
                        return;
                    }
                }
            }
            //粗略验证必选是否全部添加
           /* if(hasSelectNum()<18){
                T.showShortBottom("请添加必选资料");
                return;
            }*/

            //使用网络提示
            if (!NetworkUtils.isWifi(this)) {
                showNetTipsDialog();
            } else {
                startToUploadingImages();
            }
        } else {
            T.showShortBottom("亲,您还没选择资料哦!");
        }
    }

    /**
     * 上传前网络提示
     */
    private void showNetTipsDialog() {
        String filesSize = FileUtil.getFileOrFilesSize(imagesInfoBeens);
        new DialogMessage(this)
                .setMess("您正在使用非WiFi网络，资料大约" + filesSize +
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
                        startToUploadingImages();
                    }
                }).show();
    }

    /**
     * 真正上传图片
     */
    private void startToUploadingImages() {
        //通过、上传
        showWithStatus("上传中...");
        //保存数据
        savingData();
        //移除无效的对象
        MyUtils.removeInvalidData(imagesInfoBeens);
        uploadingMicroCreditImage();
    }


    private int hasSelectNum() {
        int hasSelect = 0;
        for (int i = 0; i < imagesInfoBeens.size(); i++) {
            ImagesInfoBean imagesInfoBean = imagesInfoBeens.get(i);
            if (i < 18 && imagesInfoBean != null && imagesInfoBean.getType() - 1 == i && imagesInfoBean.getPaths() != null && !"".equals(imagesInfoBean.getPaths())) {
                hasSelect++;
            }
        }
        return hasSelect;
    }


    private boolean hasSelect() {
        boolean hasSelect = false;
        for (int i = 0; i < imagesInfoBeens.size(); i++) {
            ImagesInfoBean imagesInfoBean = imagesInfoBeens.get(i);
            if (imagesInfoBean != null && imagesInfoBean.getType() != -1 && imagesInfoBean.getPaths() != null && !"".equals(imagesInfoBean.getPaths())) {
                hasSelect = true;
                break;
            }
        }
        return hasSelect;
    }

    /**
     * 正在上传微贷影像资料
     */
    private void uploadingMicroCreditImage() {

        //上传完成
        if (currentIndex == imagesInfoBeens.size()) {
            T.showShortBottom("恭喜您,资料上传完成!");
            upLoadSuccess = true;
            closeSVProgressHUD();
            showWithStatus("申请中...");
            setManagerLoanApplyMap();
        } else {
            ImagesInfoBean imagesInfoBean = imagesInfoBeens.get(currentIndex);
            //是否上传成功
            if (!imagesInfoBean.isUploadSuccess()) {
                List<String> upfiles = new ArrayList<>();
                List<String> desList = new ArrayList<>();
                List<String> photoDes = new ArrayList<>();
                String pathArr[] = imagesInfoBean.getPaths().split(",");
                for (int i = 0; i < pathArr.length; i++) {
                    upfiles.add(pathArr[i]);
                    desList.add(imagesInfoBean.getFileType());
                    photoDes.add(imagesInfoBean.getPhotoDes()+i);
                }
                String prdCode = "WDCP";
                int index = imagesInfoBean.getType() - 1;

                fileUploadPresenter.fileUpLoad(upfiles, desList, prdCode, serno,photoDes);
            } else {
                currentIndex++;
                uploadingMicroCreditImage();
            }
        }
    }

    @Override
    public void uploadSuccess(List<String> fileIds) {
        super.uploadSuccess(fileIds);
        imagesInfoBeens.get(currentIndex).setUploadSuccess(true);
        currentIndex++;
        if(tempDataListBean!=null){
            //保存进度
            tempDataListBean.setProgress(currentIndex);
            //保存更新进度
            Gson gson = new Gson();
            String jsonStr = gson.toJson(imagesInfoBeens);
            tempDataListBean.setDataList(jsonStr);
            //保存最新上传进度信息
            boolean saveFlag = BaseDb.saveOrUpdate(tempDataListBean);
        }else{
            //保存进度
            dataListBean.setProgress(currentIndex);
            //保存更新进度
            Gson gson = new Gson();
            String jsonStr = gson.toJson(imagesInfoBeens);
            dataListBean.setDataList(jsonStr);
            //保存最新上传进度信息
            boolean saveFlag = BaseDb.saveOrUpdate(dataListBean);
        }
        uploadingMicroCreditImage();
    }

    @Override
    public void uploadFail() {
        super.uploadFail();
        imagesInfoBeens.get(currentIndex).setUploadSuccess(false);

        if(tempDataListBean!=null){
            //保存更新进度
            Gson gson = new Gson();
            String jsonStr = gson.toJson(imagesInfoBeens);
            tempDataListBean.setDataList(jsonStr);
            //保存最新上传进度信息
            BaseDb.saveOrUpdate(tempDataListBean);
        }else{
            //保存更新进度
            Gson gson = new Gson();
            String jsonStr = gson.toJson(imagesInfoBeens);
            dataListBean.setDataList(jsonStr);
            //保存最新上传进度信息
            BaseDb.saveOrUpdate(dataListBean);
        }
    }

    /**
     * 请求参数配置
     */
    private void setManagerLoanApplyMap() {

        Map<String, String> map = new HashMap<>();
        map.put("token", BaseMainActivity.loginUserBean.getToken());
        map.put("terminal_type", "01");
        map.put("credit_coordinate", APP.lat + "," + APP.lng);
        map.put("coordinate_county", APP.cityName);
        map.put("coordinate_city", APP.areaName);
        map.put("user_detail_id", detail_id);
        map.put("serno", serno);
        managerLoanApply(map);
    }

    /**
     * 申请微贷
     */
    private void managerLoanApply(Map<String, String> map) {
        ClientModel.managerLoanApply(map)
                .timeout(20, TimeUnit.SECONDS)
                .compose(RxSchedulersHelper.<BaseEntity>io_main())
                .map(new RxHttpBaseResultFunc())
                .subscribe(new RxSubscriber<BaseEntity>() {
                    @Override
                    public void _onNext(BaseEntity baseEntity) {
                        T.showShortBottom("申请成功");
                        hasApplying = true;
                        imageDocUploadReceipt();
                    }

                    @Override
                    public void _onError(String msg) {
                        T.showShortBottom("申请失败");
                        closeSVProgressHUD();
                    }
                });
    }


    /**
     * 申请微贷保存。。。。。
     */
    private void saveLoanMsg(Map<String, String> map) {
        ClientModel.saveLoanMsg(map)
                .timeout(20, TimeUnit.SECONDS)
                .compose(RxSchedulersHelper.<BaseEntity>io_main())
                .map(new RxHttpBaseResultFunc())
                .subscribe(new RxSubscriber<BaseEntity>() {
                    @Override
                    public void _onNext(BaseEntity baseEntity) {
                        closeSVProgressHUD();
                        saveImages();
                    }

                    @Override
                    public void _onError(String msg) {
                        T.showShortBottom("保存失败");
                        MyLog.e("保存失败========" + msg);
                        closeSVProgressHUD();
                    }
                });
    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = new Bundle();
            bundle.putString("serno", serno);
            bundle.putString("prdId", prd_id);
            bundle.putString("prdType", "2");
            IntentUtil.gotoActivity(DataListActivity.this, LoadApplyResultActivity.class, bundle);
        }
    };

    /**
     * 上传影像资料回执
     */
    private void imageDocUploadReceipt() {
        Map<String, String> map = new HashMap<>();
        map.put("token", BaseMainActivity.loginUserBean.getToken());
        map.put("serno", serno);
        map.put("image_doc_type", "0002");
        map.put("terminal_type", "01");
        map.put("status", "0");
        ClientModel.imageDocUploadReceipt(map)
                .timeout(20, TimeUnit.SECONDS)
                .compose(RxSchedulersHelper.<BaseEntity>io_main())
                .map(new RxHttpBaseResultFunc())
                .subscribe(new RxSubscriber<BaseEntity>() {
                    @Override
                    public void _onNext(BaseEntity entity) {
                        T.showShortBottom("影像回执成功");
                        closeSVProgressHUD();
                        mHandler.sendEmptyMessageDelayed(0, 300);
                        MyLog.e("上传影像回执_onNext====" + entity.getMsg());
                    }

                    @Override
                    public void _onError(String msg) {
                        T.showShortBottom("回执失败,请重试");
                        closeSVProgressHUD();
                        MyLog.e("上传影像回执_onError====" + msg);
                    }
                });
    }


    /**
     * 判断资料是否填写完整
     */
    private boolean beforeSaveMicroCreditImage() {
        //type 1--18必填
        if (imagesInfoBeens.size() > 0 && hasSelect()) {
            //精细验证必选是否全部添加
            for (int i = 0; i < imagesInfoBeens.size(); i++) {
                ImagesInfoBean imagesInfoBean = imagesInfoBeens.get(i);
                //还没选择
                if (imagesInfoBean.getType() == -1 ) {
                    Drawable drawables[] = textViews222.get(i).getCompoundDrawables();
                    if (drawables[0] != null) {
                        return false;
                    }
                }
                if ((imagesInfoBean.getType() - 1 != i || imagesInfoBean.getPaths() == null || "".equals(imagesInfoBean.getPaths()))) {
                    Drawable drawables[] = textViews222.get(i).getCompoundDrawables();
                    if (drawables[0] != null) {
                        return false;
                    }
                }
            }
            /*//粗略验证必选是否全部添加
            if (hasSelectNum() < 18) {
                return false;
            }*/
        } else {
            return false;
        }

        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        aMapLocUtils.stopLocation();
        mHandler.removeCallbacksAndMessages(null);
    }
}
