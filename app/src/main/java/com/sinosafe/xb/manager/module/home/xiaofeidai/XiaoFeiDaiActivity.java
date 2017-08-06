package com.sinosafe.xb.manager.module.home.xiaofeidai;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.sinosafe.xb.manager.R;
import com.sinosafe.xb.manager.api.rxjava.RxHttpResultFunc;
import com.sinosafe.xb.manager.api.rxjava.RxSchedulersHelper;
import com.sinosafe.xb.manager.api.rxjava.RxSubscriber;
import com.sinosafe.xb.manager.bean.BaseEntity;
import com.sinosafe.xb.manager.bean.LoanIntroduce;
import com.sinosafe.xb.manager.bean.LoanProductBean;
import com.sinosafe.xb.manager.bean.LoanRateListBean;
import com.sinosafe.xb.manager.model.ClientModel;
import com.sinosafe.xb.manager.utils.T;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import luo.library.base.base.BaseFragmentActivity;
import luo.library.base.bean.WheelViewBean;
import luo.library.base.utils.DensityUtil;
import luo.library.base.utils.IntentUtil;
import luo.library.base.utils.MyLog;
import luo.library.base.widget.SimpleTextWatcher;
import luo.library.base.widget.dialog.DialogWheelView;


/**
 * 类名称：   XiaoFeiDaiActivity
 * 内容摘要： //消费贷/微贷。
 * 修改备注：
 * 创建时间： 2017/5/10
 * 公司：     深圳市华移科技股份有限公司
 * 作者：     Mr 张
 */
public class XiaoFeiDaiActivity extends BaseFragmentActivity {

    @BindView(R.id.iv_loan_icon)
    ImageView mLoanIcon;
    @BindView(R.id.iv_common_problem)
    ImageView mLoanProblemIcon;
    @BindView(R.id.edtTerm)
    TextView edtTerm;
    @BindView(R.id.edtAccount)
    EditText edtAccount;
    @BindView(R.id.tv_minimum_daily_rate)
    TextView mTvMinimumDailyRate;
    @BindView(R.id.tv_loan_title)
    TextView mTvLoanTitle;
    @BindView(R.id.ll_limit)
    LinearLayout mLlLimit;
    @BindView(R.id.tvMonthPay)
    TextView mTvMonthPay;
    @BindView(R.id.btn_apply_immediately)
    Button mBtnApplyImmediately;
    @BindView(R.id.tvApplyConditions)
    TextView mTvApplyConditions;
    @BindView(R.id.ivApplyConditions)
    ImageView mIvApplyConditions;
    @BindView(R.id.tvApplyProcess)
    TextView mTvApplyProcess;
    @BindView(R.id.ivApplyProcess)
    ImageView mIvApplyProcess;
    @BindView(R.id.tvProductIntro)
    TextView mTvProductIntro;
    @BindView(R.id.ivProductIntro)
    ImageView mIvProductIntro;
    @BindView(R.id.tvCommonProblem)
    TextView mTvCommonProblem;
    @BindView(R.id.ivCommonProblem)
    ImageView mIvCommonProblem;

    private int resIds[] = {R.mipmap.ic_apply_conditions,R.mipmap.ic_apply_process,
            R.mipmap.ic_product_intro,R.mipmap.icon_changjianwenti};
    private  int index = -1;

    //期限
    private List<WheelViewBean> listWheel = new ArrayList<>();
    private DialogWheelView dialogWheelView;

    //0：消费贷，1：微贷
    private int type = -1;
    private LoanProductBean loan;
    private float prd_cost_rate;//产品费率
    private float maximumLimit;//最大额度
    private float amount = 0;//金额
    private int term = 0;//期数


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiaofeidai);
        ButterKnife.bind(this);

        initView();

        showWithStatus("");
        getProductRateList(loan.getPrd_pk());
    }

    /**
     * 初始化view值
     */
    private void initView() {

        loan = (LoanProductBean) getIntent().getSerializableExtra("loan");
        type = getIntent().getIntExtra("type", -1);
        if (type == 0) {
            setTitleText("公积金贷款");
        } else {
            setTitleText("微贷");
            mTvLoanTitle.setText("微贷");
        }
        dialogWheelView = new DialogWheelView(this, listWheel, new DialogWheelView.OnConfirmListener() {
            @Override
            public void onConfirm(WheelViewBean item) {
                term = Integer.parseInt(item.getName().replace("个月"," ").trim());
                edtTerm.setText(String.valueOf(term));
                prd_cost_rate = Float.parseFloat(item.getType());
                getProductRate();
            }
        });
        changeView();
        edtAccount.addTextChangedListener(textWatcher);

        //点击清除内容
        edtAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtAccount.setText("");
            }
        });

        /*int screenWidth = DensityUtil.widthPixels(this);
        //申请条件
        int widthAndHeigth[] = getBitmapWidthAndHeigth(R.mipmap.icon_apply_condition);
        ViewGroup.LayoutParams params = mIvApplyConditions.getLayoutParams();
        params.height = screenWidth*widthAndHeigth[1]/widthAndHeigth[0];
        mIvApplyConditions.setLayoutParams(params);

        //申请流程
        widthAndHeigth = getBitmapWidthAndHeigth(R.mipmap.icon_apply_process);
        params = mIvApplyProcess.getLayoutParams();
        params.height = screenWidth*widthAndHeigth[1]/widthAndHeigth[0];
        mIvApplyProcess.setLayoutParams(params);

        //产品介绍
        widthAndHeigth = getBitmapWidthAndHeigth(R.mipmap.icon_product_introduce);
        params = mIvProductIntro.getLayoutParams();
        params.height = screenWidth*widthAndHeigth[1]/widthAndHeigth[0];
        mIvProductIntro.setLayoutParams(params);

        //常见问题
        widthAndHeigth = getBitmapWidthAndHeigth(R.mipmap.icon_common_problem);
        params = mIvCommonProblem.getLayoutParams();
        params.height = screenWidth*widthAndHeigth[1]/widthAndHeigth[0];
        mIvCommonProblem.setLayoutParams(params);*/
    }


    /**
     * 返回图片的宽高
     * @param resId
     * @return
     */
    private int[] getBitmapWidthAndHeigth(int resId){
        int widthAndHeigth[] = new int[2];
        BitmapFactory.Options options = new BitmapFactory.Options();
        /**
         * 最关键在此，把options.inJustDecodeBounds = true;
         * 这里再decodeFile()，返回的bitmap为空，但此时调用options.outHeight时，已经包含了图片的高了
         */
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(),resId, options); // 此时返回的bitmap为null
        widthAndHeigth[0] = options.outWidth;
        widthAndHeigth[1] = options.outHeight;

        return widthAndHeigth;
    }

    /**
     * 显示消费贷详情
     */
    private void changeView() {
        if (loan == null) return;
        setTitleText(loan.getPrd_name());
        mTvLoanTitle.setText(loan.getPrd_name());
        maximumLimit = loan.getSub_amt_max();//最高额度
        amount = maximumLimit;//
        edtAccount.setText((maximumLimit / 10000) + "");
        edtAccount.setSelection(edtAccount.length());

        if (loan.getPrd_ico() != null)
            Glide.with(this).load(loan.getPrd_ico()).asBitmap().placeholder(R.mipmap.icon_gongjijin)
                    .error(R.mipmap.icon_gongjijin).into(mLoanIcon);
    }

    @OnClick({R.id.btn_apply_immediately, R.id.edtTerm, R.id.iv_common_problem,
            R.id.tvApplyConditions, R.id.tvApplyProcess, R.id.tvProductIntro, R.id.tvCommonProblem})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            //立即申请
            case R.id.btn_apply_immediately:
                if (term == 0) {
                    T.showShortBottom("请选择期限");
                    return;
                }
                if (!judgeAmount())
                    return;
                Bundle bundle = new Bundle();
                bundle.putInt("type", type);
                bundle.putString("prd_id", loan.getPrd_pk());
                bundle.putString("prd_name", loan.getPrd_name());
                bundle.putString("period", edtTerm.getText().toString());
                bundle.putString("amount", edtAccount.getText().toString());
                IntentUtil.gotoActivity(this, XiaoFeiDaiApplyActivity.class, bundle);
                break;

            //期限
            case R.id.edtTerm:
                if (listWheel.size() > 0)
                    dialogWheelView.show();
                break;

            //常见问题
            case R.id.iv_common_problem:

                if (loan == null) return;
                Bundle bundle1 = new Bundle();
                bundle1.putString("prd_pk", loan.getPrd_pk());
                IntentUtil.gotoActivity(this, LoanProblemActivity.class, bundle1);
                break;

            case R.id.tvApplyConditions:
                index = 0;
                setImageVisibleOrNot(mIvApplyConditions,mTvApplyConditions);
                break;
            case R.id.tvApplyProcess:
                index = 1;
                setImageVisibleOrNot(mIvApplyProcess,mTvApplyProcess);
                break;
            case R.id.tvProductIntro:
                index = 2;
                setImageVisibleOrNot(mIvProductIntro,mTvProductIntro);
                break;
            case R.id.tvCommonProblem:
                index = 3;
                setImageVisibleOrNot(mIvCommonProblem,mTvCommonProblem);
                break;
        }
    }

    private void setImageVisibleOrNot(ImageView imageView,TextView textView){

        if(imageView.getVisibility()==View.GONE){
            imageView.setVisibility(View.VISIBLE);
            setTextViewRightImage(textView,R.mipmap.icon_shouqi);
        }else{
            imageView.setVisibility(View.GONE);
            setTextViewRightImage(textView,R.mipmap.icon_zhankai);
        }
    }

    private void setTextViewRightImage(TextView textView,int resId){

        Drawable right = getResources().getDrawable(resId);
        right.setBounds(0, 0, right.getIntrinsicWidth(),
                right.getIntrinsicHeight());

        Drawable left = getResources().getDrawable(resIds[index]);
        left.setBounds(0, 0, left.getIntrinsicWidth(),
                left.getIntrinsicHeight());

        textView.setCompoundDrawables(left, null, right, null);
    }


    /**
     * 获取汇率
     */
    private void getProductRate() {

        //最小利率
        float rate_min = loan.getRate_min();
        //如果没有产品费率或没有最低利率，不计算最低日费率
        if (term == 0 || rate_min == 0 || amount == 0) return;
        //先算保费
        float premium = amount * prd_cost_rate;
        //再算利息
        float X = (rate_min / 12 + 1);
        for (int i = 0; i < term - 1; i++) {
            X = X * (rate_min / 12 + 1);
        }
        //总利率
        float interest = ((amount * X * rate_min / 12) / (X - 1)) * term - amount;
        //最后算最小日费率
        float dailyInterest = (premium + interest) / (amount * 30 * term);
        //月供
        float monthAmount = (amount * X * rate_min / 12) / (X - 1);

        //更新最小日费率
        DecimalFormat df1 = new DecimalFormat("#0.000");
        mTvMinimumDailyRate.setText(df1.format(dailyInterest * 100) + "%");
        //更新月供
        DecimalFormat df2 = new DecimalFormat("#.00");
        mTvMonthPay.setText("月供:" + df2.format(monthAmount) + "元/月");
        generateData(interest, monthAmount);
    }


    /**
     * 更新环形图
     */
    private void generateData(float interest, float monthAmount) {

        /*int[] COLORS = new int[]{getResources().getColor(R.color.color_loan_amount),
                getResources().getColor(R.color.color_total_interest_rate), getResources().getColor(R.color.color_monthly)};

        List<SliceValue> values = new ArrayList<SliceValue>();
        //贷款额
        SliceValue amountValue = new SliceValue(amount, COLORS[0]);
        values.add(amountValue);
        //总利率
        SliceValue interestValue = new SliceValue(interest, COLORS[1]);
        values.add(interestValue);
        //月供
        SliceValue monthValue = new SliceValue(monthAmount, COLORS[2]);
        values.add(monthValue);

        data = new PieChartData(values);
        data.setHasLabels(hasLabels);
        data.setHasLabelsOnlyForSelected(hasLabelForSelected);
        data.setHasLabelsOutside(hasLabelsOutside);
        data.setHasCenterCircle(hasCenterCircle);
        // 设置不显示数据的背景颜色
        data.setValueLabelBackgroundEnabled(false);

        if (isExploded) {
            data.setSlicesSpacing(24);
        }
        if (hasCenterText1) {
            data.setCenterText1(amount + "元");
            data.setCenterText1FontSize(ChartUtils.px2sp(getResources().getDisplayMetrics().scaledDensity,
                    (int) getResources().getDimension(R.dimen.font_12)));
            data.setCenterText1Color(Color.parseColor("#44444F"));
        }
        if (hasCenterText2) {
            data.setCenterText2(term + "个月");
            data.setCenterText2FontSize(ChartUtils.px2sp(getResources().getDisplayMetrics().scaledDensity,
                    (int) getResources().getDimension(R.dimen.font_12)));
            data.setCenterText2Color(Color.parseColor("#44444F"));
        }
        mPieChart.setPieChartData(data);*/
    }

    /**
     * EditView输入监听
     */
    TextWatcher textWatcher = new SimpleTextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {
            if (judgeAmount())
                getProductRate();
        }
    };


    /**
     * 检查
     */
    private boolean judgeAmount() {
        String amountText = edtAccount.getText().toString();
        if (TextUtils.isEmpty(amountText)) {
            T.showShortBottom("请输入申请金额");
            return false;
        }
        amount = Float.valueOf(amountText) * 10000;//金额万元
        if (amount <= 0) {
            T.showShortBottom("申请金额必须大于0");
            return false;
        }
        //系列产品最高申请金额
        if (amount > maximumLimit) {
            T.showShortBottom("申请金额不能超过最高额度" + maximumLimit + "元");
            return false;
        }
        return true;
    }


    /**
     * 获取消费贷期限列表
     *
     * @param prd_id
     */
    private void getProductRateList(final String prd_id) {

        ClientModel.getProductRateList(prd_id)
            .timeout(20, TimeUnit.SECONDS)
            .compose(RxSchedulersHelper.<BaseEntity<List<LoanRateListBean>>>io_main())
            .map(new RxHttpResultFunc<List<LoanRateListBean>>())
            .subscribe(new RxSubscriber<List<LoanRateListBean>>() {
                @Override
                public void _onNext(List<LoanRateListBean> rateListBeen) {
                    //closeSVProgressHUD();
                    getProductById(prd_id);
                    showProductRateList(rateListBeen);
                }

                @Override
                public void _onError(String msg) {
                    closeSVProgressHUD();
                        showErrorWithStatus(msg);
                    }
                });
    }

    /**
     * @param rateList
     */
    public void showProductRateList(List<LoanRateListBean> rateList) {
        if (rateList != null && rateList.size() > 0) {
            LoanRateListBean loanRate = rateList.get(0);
            term = Integer.parseInt(loanRate.getPeriod());
            prd_cost_rate = Float.parseFloat(loanRate.getPrd_cost_rate());
            edtTerm.setText(String.valueOf(term));
            for (LoanRateListBean rate : rateList) {
                MyLog.e(rate.toString());
                listWheel.add(new WheelViewBean(rate.getPrd_cost_rate(), rate.getPeriod()+"个月"));
            }
            dialogWheelView.update(listWheel);
            getProductRate();
        }
    }

    /**
     * 获取贷款相关信息，包括申请条件，申请流程，常见问题
     * @param prd_id
     */
    private void getProductById(String prd_id) {
        ClientModel.getProductById(prd_id)
            .timeout(20, TimeUnit.SECONDS)
            .compose(RxSchedulersHelper.<BaseEntity<LoanIntroduce>>io_main())
            .map(new RxHttpResultFunc<LoanIntroduce>())
            .subscribe(new RxSubscriber<LoanIntroduce>() {
                @Override
                public void _onNext(LoanIntroduce loanIntroduce) {
                    showLoanProductInfo(loanIntroduce);
                    closeSVProgressHUD();
                }
                @Override
                public void _onError(String msg) {
                    closeSVProgressHUD();
                    showErrorWithStatus(msg);
                }
            });
    }

    int screenWidth = 0 ;
    //展示产品介绍详情
    private void showLoanProductInfo(LoanIntroduce loanIntroduce){

        screenWidth = DensityUtil.widthPixels(this);
        //申请条件
        Glide.with(this).load(loanIntroduce.getPrd_condition()).asBitmap().placeholder(R.mipmap.default_image).dontAnimate()
                .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                        ViewGroup.LayoutParams params = mIvApplyConditions.getLayoutParams();
                        params.height = screenWidth*resource.getHeight()/resource.getWidth();
                        mIvApplyConditions.setLayoutParams(params);
                        mIvApplyConditions.setImageBitmap(resource);
                    }
                });


        //申请流程
        Glide.with(this).load(loanIntroduce.getPrd_process()).asBitmap().placeholder(R.mipmap.default_image).dontAnimate()
                .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                        ViewGroup.LayoutParams params = mIvApplyProcess.getLayoutParams();
                        params.height = screenWidth*resource.getHeight()/resource.getWidth();
                        mIvApplyProcess.setLayoutParams(params);
                        mIvApplyProcess.setImageBitmap(resource);
                    }
                });

        //产品介绍
        Glide.with(this).load(loanIntroduce.getPrd_introduce()).asBitmap().placeholder(R.mipmap.default_image).dontAnimate()
                .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                        ViewGroup.LayoutParams params = mIvProductIntro.getLayoutParams();
                        params.height = screenWidth*resource.getHeight()/resource.getWidth();
                        mIvProductIntro.setLayoutParams(params);
                        mIvProductIntro.setImageBitmap(resource);
                    }
                });

        //常见问题
        Glide.with(this).load(loanIntroduce.getPrd_problems()).asBitmap().placeholder(R.mipmap.default_image).dontAnimate()
                .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                        ViewGroup.LayoutParams params = mIvCommonProblem.getLayoutParams();
                        params.height = screenWidth*resource.getHeight()/resource.getWidth();
                        mIvCommonProblem.setLayoutParams(params);
                        mIvCommonProblem.setImageBitmap(resource);
                    }
                });
    }
}
