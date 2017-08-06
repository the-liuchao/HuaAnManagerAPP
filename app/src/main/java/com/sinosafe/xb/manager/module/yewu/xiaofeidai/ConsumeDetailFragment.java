package com.sinosafe.xb.manager.module.yewu.xiaofeidai;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.sinosafe.xb.manager.BaseMainActivity;
import com.sinosafe.xb.manager.R;
import com.sinosafe.xb.manager.adapter.yewu.GridImageAdapter;
import com.sinosafe.xb.manager.api.rxjava.RxHttpBaseResultFunc;
import com.sinosafe.xb.manager.api.rxjava.RxSchedulersHelper;
import com.sinosafe.xb.manager.api.rxjava.RxSubscriber;
import com.sinosafe.xb.manager.bean.BaseEntity;
import com.sinosafe.xb.manager.bean.YeWuBean;
import com.sinosafe.xb.manager.model.ClientModel;
import com.sinosafe.xb.manager.module.BaseWebViewActivity;
import com.sinosafe.xb.manager.module.PDFViewActivity;
import com.sinosafe.xb.manager.module.home.supplyInfo.ImageSelectActivity2;
import com.sinosafe.xb.manager.module.home.supplyInfo.weidai.ApplyWeiDataActivity;
import com.sinosafe.xb.manager.module.home.supplyInfo.xiaofeidai.ApplyXiaoDataActivity;
import com.sinosafe.xb.manager.module.home.xiaofeidai.ElectronicSignatureActivity;
import com.sinosafe.xb.manager.module.imagePreview.MyImagePreviewDelActivity;
import com.sinosafe.xb.manager.utils.MyUtils;
import com.sinosafe.xb.manager.utils.T;
import com.sinosafe.xb.manager.widget.DialogPowerOfAttorney;
import com.sinosafe.xb.manager.widget.VerificationCodeButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import luo.library.base.base.BaseFragment;
import luo.library.base.utils.IntentUtil;
import luo.library.base.utils.MyLog;
import luo.library.base.widget.MoneyView;
import luo.library.base.widget.NoScrollGridView;
import luo.library.base.widget.dialog.DialogInput;

import static android.app.Activity.RESULT_OK;


/**
 * 消费贷详情片
 */
public class ConsumeDetailFragment extends BaseFragment {


    private ConsumeDetailFragment detailFragment;

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
    @BindView(R.id.tvSalesmanName)
    TextView mTvSalesmanName;
    @BindView(R.id.tvSalesmanNum)
    TextView mTvSalesmanNum;
    @BindView(R.id.tvSalesmanPhone)
    TextView mTvSalesmanPhone;
    @BindView(R.id.gvImage)
    NoScrollGridView mGvImage;
    @BindView(R.id.btnOperate)
    Button mBtnOperate;
    Unbinder unbinder;
    @BindView(R.id.tvBaodan)
    TextView mTvBaodan;
    @BindView(R.id.tvTouBaodan)
    TextView mTvTouBaodan;
    @BindView(R.id.tvZhengxin)
    TextView mTvZhengxin;
    @BindView(R.id.bottonLayout)
    LinearLayout mBottonLayout;


    @BindView(R.id.ll_apply_pass)
    LinearLayout mApplyPass;//批复结结果 通过
    //审核通过
    @BindView(R.id.tvMoney)
    MoneyView mTvMoney;//金额
    @BindView(R.id.tv_loan_deadline)
    TextView mLoanDeadline;//贷款期限
    @BindView(R.id.tv_pass_repayment_methods)
    TextView mPassRepaymentMethods;//还款方式
    @BindView(R.id.tv_premium_rate)
    TextView mPremiumRate;//保险费率
    @BindView(R.id.tv_lending_rate)
    TextView mLendingRate;//贷款利率

    private GridImageAdapter imageAdapter;
    private List<ImageItem> images = new ArrayList<>();
    private YeWuBean yeWuBean;
    private DialogInput dialogInput;

    //电子签署
    private DialogPowerOfAttorney dialogPowerOfAttorney;
    private static final int ELECTRONIC_SIGNA = 400;

    public ConsumeDetailFragment newInstance() {

        if (detailFragment == null)
            detailFragment = new ConsumeDetailFragment();
        return detailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_consume_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        //initView();
        imageAdapter = new GridImageAdapter(getActivity(), 3);
        mGvImage.setAdapter(imageAdapter);
        mGvImage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                openImagePreviewActivity(position, images);
            }
        });
    }

    /**
     * 初始化
     */
    public void initView(YeWuBean yeWuBean) {
        this.yeWuBean = yeWuBean;

        mTvProductName.setText(yeWuBean.getPRD_NAME());
        mTvApplyDate.setText(yeWuBean.getAPPLY_DATE());

        mTvApplyAccount.setMoneyText(MyUtils.keepTwoDecimal(yeWuBean.getAMOUNT()));
        mTvMoneyType.setText("人民币");

        mTvInterestRates.setText(yeWuBean.getUSING_IR() + "");
        mTvTermType.setText("月");

        mTvApplyMonth.setText(yeWuBean.getTERM() + "个月");

        mTvPayType.setText(MyUtils.getRepaymentModel(yeWuBean.getREPAYMENT_MODE()));
        mTvGuaranteeType.setText(MyUtils.getGuaranteeModel(yeWuBean.getASSURE_MEANS_MAIN()));

        mTvSalesmanName.setText(yeWuBean.getBUSINESS_PERSION_NAME());
        mTvSalesmanNum.setText(yeWuBean.getBUSINESS_PERSION_ID());
        mTvSalesmanPhone.setText(yeWuBean.getBUSINESS_PERSION_PHONE());

        //事项类型1：审批中；2：待放款；3：还款中；4：已拒绝；5：提供资料；6：去缴费
        if (((ConsumeDetailActivity) getActivity()).getType() == 1) {

            //补充资料
            if("090".equals(yeWuBean.getNEW_APPROVE_STATUS())){
                mBtnOperate.setText("补充资料");
                mBtnOperate.setVisibility(View.VISIBLE);
            }
            //其他状态
            else{
                mBottonLayout.setVisibility(View.GONE);
                mBtnOperate.setVisibility(View.GONE);
            }
        } else if (((ConsumeDetailActivity) getActivity()).getType() == 2) {
            //待缴费
            if("995".equals(yeWuBean.getNEW_APPROVE_STATUS())){
                mBtnOperate.setText("待缴费");
                mBottonLayout.setVisibility(View.GONE);
            }
            //在线签投保单
            else if("091".equals(yeWuBean.getNEW_APPROVE_STATUS())){
                mBtnOperate.setText("在线签投保单");
                mBottonLayout.setVisibility(View.GONE);
            }
            //其他状态
            else{
                mBottonLayout.setVisibility(View.VISIBLE);
                mBtnOperate.setVisibility(View.INVISIBLE);
            }
        } else if (((ConsumeDetailActivity) getActivity()).getType() == 3) {

            if("0".equals(((ConsumeDetailActivity)getActivity()).getIsLoanCheck())){
                mBtnOperate.setText("收集贷后凭证");
            }else{
                mBtnOperate.setVisibility(View.GONE);
            }
            mBottonLayout.setVisibility(View.VISIBLE);
        } else if (((ConsumeDetailActivity) getActivity()).getType() == 4) {

            mBtnOperate.setVisibility(View.GONE);
            mBottonLayout.setVisibility(View.GONE);

        }
        //补充资料
        else if (((ConsumeDetailActivity) getActivity()).getType() == 5) {
            mBtnOperate.setText("上传影像资料");
            mBottonLayout.setVisibility(View.GONE);
        }
        else if (((ConsumeDetailActivity) getActivity()).getType() == 6) {

            mBtnOperate.setText("去缴费");
            mBtnOperate.setVisibility(View.VISIBLE);
            mBottonLayout.setVisibility(View.GONE);
        }

        else if (((ConsumeDetailActivity) getActivity()).getType() == 9) {

            mBtnOperate.setText("在线签投保单");
            mBtnOperate.setVisibility(View.VISIBLE);
            mBottonLayout.setVisibility(View.GONE);
        }
        //其他
        else {
            mBtnOperate.setVisibility(View.GONE);
            mBottonLayout.setVisibility(View.GONE);
        }
    }

    /**
     * 批复结果
     * @param loanResult
     */
    public void showLoanResult(LoanDetail.LoanResult loanResult){
        /**
         * 991	拿回
         998	否决(不同意)
         997	通过
         111	审批中
         992	打回
         000	待发起
         112	审批待面签
         993	取消
         1000	缴费失败
         994	待出单
         996	已出单
         995	待缴费
         00	正在审批
         1002	待代扣保费
         090	待补充资料
         091	待在线签署投保单
         1001	待放款
         1003	已放款
         */
        String status = yeWuBean.getNEW_APPROVE_STATUS();

        if (status.equals("1001") || status.equals("1003")) {//审批通过
            approve(loanResult);
        } else if (status.equals("995")) {//待在线支付保费
            approve(loanResult);
        }else if (status.equals("1001")) {//待放款
            approve(loanResult);
        } else if (status.equals("091")) {//需要在线签署投保单
            approve(loanResult);
        }
    }

    /**
     * 批复结果
     */
    private void approve(LoanDetail.LoanResult loanResult) {

        mApplyPass.setVisibility(View.VISIBLE);
        mTvMoney.setMoneyText(MyUtils.keepTwoDecimal(loanResult.getAPPROVE_AMOUNT()));//贷款金额
        mLoanDeadline.setText(loanResult.getAPPROVE_TERM() + "月");//贷款期限
        if (loanResult.getAPPROVE_REPAYMODE() != null)
            mPassRepaymentMethods.setText(MyUtils.getRepaymentModel(loanResult.getAPPROVE_REPAYMODE()));//批准的还款方式
        mPremiumRate.setText(loanResult.getCOST_RATE() + "%");//保险费率
        mLendingRate.setText(loanResult.getAPPROVE_RATE() + "%");//贷款利率

    }

    /**
     * 刷新显示影像图片
     *
     * @param images
     */
    public void initLoanImageFile(List<ImageItem> images) {

        this.images.clear();
        this.images.addAll(images);
        imageAdapter.update(this.images);

    }

    @OnClick(R.id.btnOperate)
    public void onViewClicked() {

        if (yeWuBean == null)
            return;
        Bundle bundle = new Bundle();
        //5.补充资料             6.缴费     7.贷后管理
        //0:申请中；1:审批中；2:待放款;3:还款中；4:已拒绝；8:面签；9：不需要面签列表--在线签投保单
        //补充资料
        if (((ConsumeDetailActivity) getActivity()).getType() == 1) {

            //补充资料
            if("090".equals(yeWuBean.getNEW_APPROVE_STATUS())){
                if (yeWuBean.getPRD_TYPE().equals("0")) {
                    bundle.putString("serno", yeWuBean.getSERNO());
                    IntentUtil.gotoActivityForResult(getActivity(), ApplyXiaoDataActivity.class, bundle, 10000);
                } else {
                    bundle.putString("serno", yeWuBean.getSERNO());
                    IntentUtil.gotoActivityForResult(getActivity(), ApplyWeiDataActivity.class, bundle, 10000);
                }
            }
            //其他状态
            else{

            }

        }
        //待放款
        else if (((ConsumeDetailActivity) getActivity()).getType() == 2) {
            //待缴费
            if("995".equals(yeWuBean.getNEW_APPROVE_STATUS())){
                bundle.putString("serno", yeWuBean.getSERNO());
                IntentUtil.gotoActivityForResult(getActivity(), BaseWebViewActivity.class, bundle, 10000);
            }
            //在线签投保单
            else if("091".equals(yeWuBean.getNEW_APPROVE_STATUS())){
                showSignatureAuthorization();
            }
            //其他状态
            else{

            }

        }
        //还款中
        else if (((ConsumeDetailActivity) getActivity()).getType() == 3) {
            bundle.putInt("type", 1);
            bundle.putInt("serviceType",0);
            bundle.putString("title", "收集贷后凭证");
            bundle.putString("prdType", yeWuBean.getPRD_TYPE());
            bundle.putString("serno", yeWuBean.getSERNO());
            IntentUtil.gotoActivityForResult(getActivity(), ImageSelectActivity2.class, bundle, 10000);
            //mBtnOperate.setVisibility(View.INVISIBLE);
        }
        //已拒绝
        else if (((ConsumeDetailActivity) getActivity()).getType() == 4) {

        }
        //补充资料
        else if (((ConsumeDetailActivity) getActivity()).getType() == 5) {

            if (yeWuBean.getPRD_TYPE().equals("0")) {
                bundle.putString("serno", yeWuBean.getSERNO());
                IntentUtil.gotoActivityForResult(getActivity(), ApplyXiaoDataActivity.class, bundle, 10000);
            } else {
                bundle.putString("serno", yeWuBean.getSERNO());
                IntentUtil.gotoActivityForResult(getActivity(), ApplyWeiDataActivity.class, bundle, 10000);
            }

        }
        //缴费
        else if (((ConsumeDetailActivity) getActivity()).getType() == 6) {

            bundle.putString("serno", yeWuBean.getSERNO());
            IntentUtil.gotoActivityForResult(getActivity(), BaseWebViewActivity.class, bundle, 10000);
        }

        //在线签投保单
        else if (((ConsumeDetailActivity) getActivity()).getType() == 9) {

            showSignatureAuthorization();
        }
    }


    /**
     * 预览选中的相册
     */
    private void openImagePreviewActivity(int position, List<ImageItem> images) {
        //打开预览
        Intent intentPreview = new Intent(getActivity(), MyImagePreviewDelActivity.class);
        // 但采用弱引用会导致预览弱引用直接返回空指针
        //DataHolder.getInstance().save(DataHolder.DH_CURRENT_IMAGE_FOLDER_ITEMS, images);
        //据说这样会导致大量图片崩溃
        intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) images);
        intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
        intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
        startActivity(intentPreview);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //电子签名回来
        if(requestCode == ELECTRONIC_SIGNA && resultCode == RESULT_OK){
            dialogPowerOfAttorney.showSignatureImage();
        }
    }

    /**
     * 电子签名
     */
    private void showSignatureAuthorization(){

        VerificationCodeButton.userId = yeWuBean.getCUS_ID();
        VerificationCodeButton.serno = yeWuBean.getSERNO();
        dialogPowerOfAttorney = new DialogPowerOfAttorney(getActivity(), new DialogPowerOfAttorney.OnSignatureListener() {
            //电子签名
            @Override
            public void onSignature() {
                startActivityForResult(new Intent(getActivity(),ElectronicSignatureActivity.class),ELECTRONIC_SIGNA);
            }
        }, new DialogPowerOfAttorney.OnConfirmListener() {
            //同意
            @Override
            public void onConfirm() {
                /*if(FileUtil.getBitmap(getActivity(),SIGNATURE_NAME)==null){
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
            }
        },1);
        dialogPowerOfAttorney.setSendType(2);
        dialogPowerOfAttorney.show();
    }

    /**
     * 设置 在线签投保单事项签署投保单 请求参数
     */
    private void setElectronicSignatureMap(){
        startProgressDialog("保存中...");
        Map<String,String> map = new HashMap<>();
        map.put("token",BaseMainActivity.loginUserBean.getToken());
        map.put("serno",yeWuBean.getSERNO());
        map.put("user_id",yeWuBean.getCUS_ID());
        //map.put("personSealData",MyUtils.getPersonSealData(getActivity()));
        map.put("code",dialogPowerOfAttorney.getUserInputSmsCode());
        map.put("prd_type",yeWuBean.getPRD_TYPE());
        map.put("text_type","1");
        electronicSignature(map);
    }

    /**
     * 在线签投保单事项签署投保单
     * @param map
     */
    private void electronicSignature(Map<String,String> map ){
        ClientModel.electronicSignature2(map)
            .timeout(20, TimeUnit.SECONDS)
            .compose(RxSchedulersHelper.<BaseEntity>io_main())
            .map(new RxHttpBaseResultFunc())
            .subscribe(new RxSubscriber<BaseEntity>() {
                @Override
                public void _onNext(BaseEntity baseEntity) {
                    stopProgressDialog();
                    if(baseEntity.getCode()==1){
                        signInsurance();
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


    /**
     * 签署投保单，信保系统生成投保单
     */
    private void signInsurance(){
        Map<String,String> map = new HashMap<>();
        map.put("token",BaseMainActivity.loginUserBean.getToken());
        map.put("serno",yeWuBean.getSERNO());
        map.put("device_type","01");
        ClientModel.signInsurance(map)
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
                        getActivity().setResult(Activity.RESULT_OK);
                        getActivity().finish();
                    }else{
                        T.showShortBottom(baseEntity.getMsg());
                    }
                }
                @Override
                public void _onError(String msg) {
                    stopProgressDialog();
                    MyLog.e("签署投保单，信保系统生成投保单信息反馈："+msg);
                    T.showShortBottom(msg);
                }});
    }


    /**
     * 设置常用邮箱
     */
    private void setCommonEmail() {
        dialogInput = new DialogInput(getActivity(), new DialogInput.OnTextBackListener() {
            @Override
            public void onTextBack(String text) {
                if (!MyUtils.isEmail(text)) {
                    T.showShortBottom("请输入常用邮箱");
                    return;
                }
                dialogInput.dismiss();
                startProgressDialog("");
                printApplicationForm(text);

            }
        });
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
        map.put("device_type", "01");
        ClientModel.sendInsuranceEmail(map)
                .timeout(20, TimeUnit.SECONDS)
                .compose(RxSchedulersHelper.<BaseEntity>io_main())
                .map(new RxHttpBaseResultFunc())
                .subscribe(rxSubscriber);
    }


    RxSubscriber rxSubscriber = new RxSubscriber<BaseEntity>() {
        @Override
        public void _onNext(BaseEntity entity) {
            stopProgressDialog();
            if (entity.getCode() == 1) {
                T.showShortBottom(entity.getMsg());
                getActivity().setResult(RESULT_OK);
                getActivity().finish();
            } else {
                showErrorWithStatus(entity.getMsg());
            }
        }

        @Override
        public void _onError(String msg) {
            stopProgressDialog();
            showErrorWithStatus(msg);
        }
    };


    @Override
    public void onDestroy() {
        super.onDestroy();
        detailFragment = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tvBaodan, R.id.tvTouBaodan, R.id.tvZhengxin})
    public void onViewClicked(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("serno",yeWuBean.getSERNO());
        switch (view.getId()) {
            case R.id.tvBaodan:
                bundle.putInt("type",0);
                break;
            case R.id.tvTouBaodan:
                bundle.putInt("type",1);
                break;
            case R.id.tvZhengxin:
                bundle.putInt("type",2);
                break;
        }
        IntentUtil.gotoActivity(getActivity(), PDFViewActivity.class,bundle);
    }
}
