package com.sinosafe.xb.manager.module.home.weidai;

import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sinosafe.xb.manager.R;
import com.sinosafe.xb.manager.module.home.weidai.bean.ApplyingBean;
import com.sinosafe.xb.manager.module.home.weidai.bean.DataListBean;
import com.sinosafe.xb.manager.module.home.weidai.bean.ImagesInfoBean;
import com.sinosafe.xb.manager.utils.MyAMapLocUtils;
import com.sinosafe.xb.manager.utils.fileupload.FileUploadPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import luo.library.base.base.BaseFragmentActivity;

/**
 * 类名称：   com.sinosafe.xb.manager.module.home.weidai
 * 内容摘要： //说明主要功能。
 * 修改备注：
 * 创建时间： 2017/7/28 0028
 * 公司：     深圳市华移科技股份有限公司
 * 作者：     Mr 张
 */
public class DataListBase extends BaseFragmentActivity {

    @BindView(R.id.tvIDCard)
    protected TextView mTvIDCard;
    @BindView(R.id.idCardLayout)
    protected RelativeLayout mIdCardLayout;
    @BindView(R.id.tvMarriage)
    protected TextView mTvMarriage;
    @BindView(R.id.marriageCertificateLayout)
    protected RelativeLayout mMarriageCertificateLayout;
    @BindView(R.id.tvFamilyRegister)
    protected TextView mTvFamilyRegister;
    @BindView(R.id.familyRegisterLayout)
    protected RelativeLayout mFamilyRegisterLayout;
    @BindView(R.id.tvResidenceCertificate)
    protected TextView mTvResidenceCertificate;
    @BindView(R.id.residenceCertificateLayout)
    protected RelativeLayout mResidenceCertificateLayout;
    @BindView(R.id.tvVehicleCertification)
    protected TextView mTvVehicleCertification;
    @BindView(R.id.vehicleCertificationLayout)
    protected RelativeLayout mVehicleCertificationLayout;
    @BindView(R.id.tvManagerPlaceContract)
    protected TextView mTvManagerPlaceContract;
    @BindView(R.id.managerPlaceContractLayout)
    protected  RelativeLayout mManagerPlaceContractLayout;
    @BindView(R.id.tvCustomerContract)
    protected TextView mTvCustomerContract;
    @BindView(R.id.customerContractLayout)
    protected RelativeLayout mCustomerContractLayout;
    @BindView(R.id.tvManagerPlaceBill)
    protected TextView mTvManagerPlaceBill;
    @BindView(R.id.managerPlaceBillLayout)
    protected RelativeLayout mManagerPlaceBillLayout;
    @BindView(R.id.tvResponsibilityGuarantee)
    protected TextView mTvResponsibilityGuarantee;
    @BindView(R.id.responsibilityGuaranteeLayout)
    protected RelativeLayout mResponsibilityGuaranteeLayout;
    @BindView(R.id.tvGuaranteeIDCardNum)
    protected TextView mTvGuaranteeIDCardNum;
    @BindView(R.id.GuaranteeIDCardNumLayout)
    protected RelativeLayout mGuaranteeIDCardNumLayout;
    @BindView(R.id.tvGuaranteeWorkCertificate)
    protected TextView mTvGuaranteeWorkCertificate;
    @BindView(R.id.GuaranteeWorkCertificateLayout)
    protected RelativeLayout mGuaranteeWorkCertificateLayout;
    @BindView(R.id.tvPledgeData)
    protected TextView mTvPledgeData;
    @BindView(R.id.pledgeDataLayout)
    protected RelativeLayout mPledgeDataLayout;
    @BindView(R.id.tvOtherAssetCertificate)
    protected TextView mTvOtherAssetCertificate;
    @BindView(R.id.otherAssetCertificateLayout)
    protected RelativeLayout mOtherAssetCertificateLayout;
    @BindView(R.id.tvSignCredit)
    protected TextView mTvSignCredit;
    @BindView(R.id.signCreditLayout)
    protected RelativeLayout mSignCreditLayout;
    @BindView(R.id.tvFieldInvestigation)
    protected TextView mTvFieldInvestigation;
    @BindView(R.id.fieldInvestigationLayout)
    protected RelativeLayout mFieldInvestigationLayout;
    @BindView(R.id.tvGuaranteePhoto)
    protected TextView mTvGuaranteePhoto;
    @BindView(R.id.guaranteePhotoLayout)
    protected RelativeLayout mGuaranteePhotoLayout;
    @BindView(R.id.tvSignPhoto)
    protected TextView mTvSignPhoto;
    @BindView(R.id.signPhotoLayout)
    protected RelativeLayout mSignPhotoLayout;
    @BindView(R.id.tvPersonalCredit)
    protected TextView mTvPersonalCredit;
    @BindView(R.id.personalCreditLayout)
    protected RelativeLayout mPersonalCreditLayout;
    @BindView(R.id.tvBusinessRelated)
    protected TextView mTvBusinessRelated;
    @BindView(R.id.businessRelatedLayout)
    protected  RelativeLayout mBusinessRelatedLayout;
    @BindView(R.id.tvGuarantorOfBank)
    protected TextView mTvGuarantorOfBank;
    @BindView(R.id.guarantorOfBankLayout)
    protected RelativeLayout mGuarantorOfBankLayout;
    @BindView(R.id.tvBusinessLicense)
    protected TextView mTvBusinessLicense;
    @BindView(R.id.businessLicenseLayout)
    protected RelativeLayout mBusinessLicenseLayout;
    @BindView(R.id.tvTaxRegistrationCertificate)
    protected TextView mTvTaxRegistrationCertificate;
    @BindView(R.id.taxRegistrationCertificateLayout)
    protected RelativeLayout mTaxRegistrationCertificateLayout;
    @BindView(R.id.tvOrganizationCodeCertificate)
    protected TextView mTvOrganizationCodeCertificate;
    @BindView(R.id.organizationCodeCertificateLayout)
    protected RelativeLayout mOrganizationCodeCertificateLayout;
    @BindView(R.id.tvSpecialIndustryLicense)
    protected TextView mTvSpecialIndustryLicense;
    @BindView(R.id.specialIndustryLicenseLayout)
    protected RelativeLayout mSpecialIndustryLicenseLayout;
    @BindView(R.id.tvArticlesOfAssociation)
    protected TextView mTvArticlesOfAssociation;
    @BindView(R.id.articlesOfAssociationLayout)
    protected RelativeLayout mArticlesOfAssociationLayout;
    @BindView(R.id.tvCapitalVerificationReport)
    protected TextView mTvCapitalVerificationReport;
    @BindView(R.id.capitalVerificationReportLayout)
    protected RelativeLayout mCapitalVerificationReportLayout;
    @BindView(R.id.btn_save)
    protected Button mBtnSave;
    @BindView(R.id.btn_submit)
    protected Button mBtnSubmit;

    @BindView(R.id.tvMarriageStatus)
    protected TextView tvMarriageStatus;
    @BindView(R.id.tv_residence)
    protected TextView tvResidence;
    @BindView(R.id.tvCarStatus)
    protected TextView tvCarStatus;

    @BindView(R.id.tv110)
    protected TextView mTv110;
    @BindView(R.id.tv112)
    protected TextView mTv112;
    @BindView(R.id.tv115)
    protected TextView mTv115;
    @BindView(R.id.tv116)
    protected TextView mTv116;
    @BindView(R.id.tv117)
    protected TextView mTv117;
    @BindView(R.id.tv118)
    protected TextView mTv118;
    @BindView(R.id.tv119)
    protected TextView mTv119;
    @BindView(R.id.tv120)
    protected TextView mTv120;
    @BindView(R.id.tv121)
    protected TextView mTv121;
    @BindView(R.id.tv122)
    protected TextView mTv122;
    @BindView(R.id.tv123)
    protected TextView mTv123;
    @BindView(R.id.tv124)
    protected TextView mTv124;
    @BindView(R.id.tv125)
    protected TextView mTv125;
    @BindView(R.id.tv126)
    protected TextView mTv126;
    @BindView(R.id.tv127)
    protected TextView mTv127;
    @BindView(R.id.tv128)
    protected TextView mTv128;
    @BindView(R.id.tv129)
    protected TextView mTv129;
    @BindView(R.id.tv130)
    protected TextView mTv130;
    @BindView(R.id.tv131)
    protected  TextView mTv131;
    @BindView(R.id.tv132)
    protected  TextView mTv132;
    @BindView(R.id.tv133)
    protected TextView mTv133;
    @BindView(R.id.tv134)
    protected TextView mTv134;
    @BindView(R.id.tv135)
    protected TextView mTv135;

    //图片类型
    protected int type = -1;
    protected String fileType;
    //该类型图片是否已添加
    protected boolean isExit = false;
    //当前类型图片
    protected ImagesInfoBean imagesInfoBean;
    protected List<ImagesInfoBean> imagesInfoBeens = new ArrayList<>();
    protected List<ImagesInfoBean> temp = new ArrayList<>();
    protected List<TextView> textViews = new ArrayList<>();
    protected List<TextView> textViews222 = new ArrayList<>();
    protected List<String> tips = new ArrayList<>();

    protected FileUploadPresenter fileUploadPresenter;
    //当前上传的图片索引
    protected int currentIndex = 0;
    protected MyAMapLocUtils aMapLocUtils;
    //资料是否上传完成
    protected boolean upLoadSuccess = false;
    protected boolean hasApplying = false;
    //申请资料信息
    protected DataListBean dataListBean;
    //申请资料信息
    protected DataListBean tempDataListBean;
    //申请个人信息
    protected ApplyingBean applyingBean;
    protected String detail_id, prd_id, serno;
    //婚姻状况、车产、房产
    protected boolean maritalStatus, carStatus, houseStatus;
}
