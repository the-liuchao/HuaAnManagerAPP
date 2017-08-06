package com.sinosafe.xb.manager.adapter.yewu;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sinosafe.xb.manager.APP;
import com.sinosafe.xb.manager.BaseMainActivity;
import com.sinosafe.xb.manager.R;
import com.sinosafe.xb.manager.adapter.base.BaseRecyclerViewAdapter;
import com.sinosafe.xb.manager.adapter.base.BaseRecyclerViewHolder;
import com.sinosafe.xb.manager.api.rxjava.RxHttpBaseResultFunc;
import com.sinosafe.xb.manager.api.rxjava.RxSchedulersHelper;
import com.sinosafe.xb.manager.api.rxjava.RxSubscriber;
import com.sinosafe.xb.manager.bean.BaseEntity;
import com.sinosafe.xb.manager.model.ClientModel;
import com.sinosafe.xb.manager.module.home.weidai.DataListActivity;
import com.sinosafe.xb.manager.module.home.weidai.bean.ApplyingBean;
import com.sinosafe.xb.manager.module.home.weidai.bean.DataListBean;
import com.sinosafe.xb.manager.module.home.weidai.bean.ImagesInfoBean;
import com.sinosafe.xb.manager.module.home.xiaofeidai.authorization.AuthorizationActivity;
import com.sinosafe.xb.manager.module.yewu.weidai.MicroCreditDetailActivity;
import com.sinosafe.xb.manager.module.yewu.xiaofeidai.ConsumeDetailActivity;
import com.sinosafe.xb.manager.utils.FileUtil;
import com.sinosafe.xb.manager.utils.MyAMapLocUtils;
import com.sinosafe.xb.manager.utils.MyUtils;
import com.sinosafe.xb.manager.utils.T;
import com.sinosafe.xb.manager.utils.fileupload.FileUploadPresenter2;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import luo.library.base.base.BaseDb;
import luo.library.base.base.BaseFragmentActivity;
import luo.library.base.base.BaseImage;
import luo.library.base.utils.GsonUtil;
import luo.library.base.utils.IntentUtil;
import luo.library.base.utils.MyLog;
import luo.library.base.utils.NetworkUtils;
import luo.library.base.widget.MoneyView;
import luo.library.base.widget.dialog.DialogMessage;

/**
 * 业务--申请中适配器
 */
public class WeiDaiAdapter extends BaseRecyclerViewAdapter<ApplyingBean, BaseRecyclerViewHolder>
        implements View.OnClickListener,FileUploadPresenter2.FileUploadCallback{
    private Context mContext;
    private int currentPosition = -1;
    private FileUploadPresenter2 fileUploadPresenter2;
    //本地微贷数据
    protected List<DataListBean> dataListBeens = new ArrayList<>();
    //正在上传的图片
    private List<ImagesInfoBean> imagesInfos;
    //正在上传的图片所有信息(进度、成功上传的图片情况等等)
    private DataListBean dataListBean;
    //正在上传的索引
    private int currentIndex;
    //当前申请流水号
    private String serno;
    private String prdId,cusId;
    protected MyAMapLocUtils aMapLocUtils;
    //贷款类型：0：微贷；1：消费贷
    private int loanType = 0;
    //当前数据bean
    private ApplyingBean currentApplyingBean;

    public WeiDaiAdapter(Context context) {
        super(context);
        this.mContext = context;
        getLocalDataList();
        fileUploadPresenter2 = new FileUploadPresenter2(this);
        aMapLocUtils = new MyAMapLocUtils(mContext);
    }



    /**
     * 获取本地微贷的数据列表
     */
    public void getLocalDataList(){

        List<DataListBean> dataList = BaseDb.find(DataListBean.class);
        dataListBeens.clear();
        if(dataList!=null&&dataList.size()>0){
            dataListBeens.addAll(dataList);
        }
    }

    @Override
    protected void bindDataToItemView(BaseRecyclerViewHolder baseRecyclerViewHolder, int position, ApplyingBean item) {

        MoneyView tvMoney = baseRecyclerViewHolder.getView(R.id.tvMoney);
        TextView tvStatus = baseRecyclerViewHolder.getView(R.id.tvStatus);
        TextView tvPhone = baseRecyclerViewHolder.getView(R.id.tvPhone);
        ImageView ivImage = baseRecyclerViewHolder.getView(R.id.ivImage);
        TextView tvName = baseRecyclerViewHolder.getView(R.id.tvName);
        TextView tvTime = baseRecyclerViewHolder.getView(R.id.tvTime);
        TextView tvType = baseRecyclerViewHolder.getView(R.id.tvType);
        ProgressBar progressBar = baseRecyclerViewHolder.getView(R.id.progressBar);

        tvMoney.setMoneyText(MyUtils.keepTwoDecimal(item.getAmount()));
        tvType.setText(item.getPrdName());
        tvTime.setText(item.getApplyDate());
        tvName.setText(item.getUser_name());
        tvPhone.setText(Html.fromHtml("<u>"+item.getMobile()+"</u>"));
        BaseImage.getInstance().displayCricleImage(mContext,
                item.getHead_photo(), ivImage, R.mipmap.icon_default_avatar);
        tvStatus.setText("继续申请");
        tvStatus.setTag(position);
        tvStatus.setOnClickListener(this);

        tvPhone.setTag(position);
        tvPhone.setOnClickListener(this);

        //微贷
        if("2".equals(item.getPrd_type())){
            DataListBean dataListBean = getDataListBean(item.getSerno());
            if(dataListBean!=null){
                //资料不完整
                if(dataListBean.getComplete()==0) {
                    tvStatus.setText("继续申请");
                    tvStatus.setBackgroundResource(R.drawable.shape_blue_oval);
                    progressBar.setVisibility(View.GONE);
                }
                else{
                    //资料完整 未上传
                    if(dataListBean.getProgress()==0){
                        tvStatus.setText("上传影像资料");
                        tvStatus.setBackgroundResource(R.drawable.shape_blue_oval);
                        progressBar.setVisibility(View.GONE);
                    }else{
                        progressBar.setVisibility(View.VISIBLE);
                        //正在上传
                        if(dataListBean.isUpload()){
                            int progress = (int)((float)dataListBean.getProgress() / (float) imagesInfos.size() * 100);
                            tvStatus.setText("上传中"+progress+"%");
                            progressBar.setMax(imagesInfos.size());
                            progressBar.setProgress(dataListBean.getProgress());
                            tvStatus.setBackgroundResource(0);
                        }
                        //暂停上传
                        else{
                            List<ImagesInfoBean> imagesInfos = GsonUtil.GsonToList(dataListBean.getDataList(),ImagesInfoBean.class);
                            int progress = (int)((float)dataListBean.getProgress() / (float) imagesInfos.size() * 100);
                            tvStatus.setText("继续上传("+progress+"%)");
                            tvStatus.setBackgroundResource(R.drawable.shape_blue_oval);
                        }
                    }
                }
            }
            //没有本地记录的情况
            else{
                tvStatus.setBackgroundResource(R.drawable.shape_blue_oval);
                tvStatus.setText("继续申请");
                progressBar.setVisibility(View.GONE);
            }
        }
        //消费贷
        else{
            tvStatus.setBackgroundResource(R.drawable.shape_blue_oval);
            tvStatus.setText("继续申请");
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseRecyclerViewHolder(inflateItemView(parent, R.layout.yewu_content_item));
    }


    @Override
    public void onClick(View v) {
        currentPosition = Integer.valueOf(v.getTag().toString());
        ApplyingBean applyingBean = getDatas().get(currentPosition);
        switch (v.getId()){
            case R.id.tvPhone:
                ((BaseFragmentActivity)mContext).callPhoneTips(applyingBean.getMobile());
                break;

            case R.id.tvStatus:
                //微贷
                if("2".equals(applyingBean.getPrd_type())){
                    DataListBean dataListBean = getDataListBean(applyingBean.getSerno());
                    if(dataListBean!=null){
                        handleUserOperator(dataListBean);
                    }
                    //本地没有保存记录
                    else{
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("applyingBean",getDatas().get(currentPosition));
                        IntentUtil.gotoActivity(mContext, DataListActivity.class,bundle);
                    }
                }
                else{
                    if(APP.lat == 0 || APP.lng == 0){
                        new DialogMessage(mContext).setMess("亲,定位失败了~请重新定位.")
                                .setConfirmListener(new DialogMessage.OnConfirmListener() {
                                    @Override
                                    public void onConfirm() {
                                        ((BaseFragmentActivity)mContext).showWithStatus("");
                                        aMapLocUtils.startLocation();
                                    }
                                }).show();
                        return;
                    }
                    serno = applyingBean.getSerno();
                    prdId = applyingBean.getPrdId();
                    cusId = applyingBean.getUser_id();
                    APP.bundle.clear();
                    APP.bundle.putString("cus_id",cusId);
                    APP.bundle.putString("real_name",applyingBean.getUser_name());
                    APP.bundle.putString("identity_code",applyingBean.getCert_code());
                    showConsumptionApplyTipDialog();
                }
                break;
        }
    }

    /**
     * 提示申请dialog
     */
    private void showConsumptionApplyTipDialog(){
        new DialogMessage(mContext).setMess("亲，即将开始继续申请操作，确定继续吗?")
                .setConfirmListener(new DialogMessage.OnConfirmListener() {
                    @Override
                    public void onConfirm() {

                    ((BaseFragmentActivity)mContext).startProgressDialog("");
                    applyAuth();

                    }
                }).show();
    }

    /**
     * 消费贷申请
     */
    public void consumptionApplying (){
        ((BaseFragmentActivity)mContext).startProgressDialog("申请中...");
        loanType = 1;
        setManagerLoanApplyMap();
    }

    /**
     * 分类处理操作
     * @param dataListBean
     */
    private void handleUserOperator(DataListBean dataListBean){
        //资料不完整--继续申请
        if(dataListBean.getComplete()==0) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("dataListBean",dataListBean);
            bundle.putSerializable("applyingBean",getDatas().get(currentPosition));
            IntentUtil.gotoActivity(mContext, DataListActivity.class,bundle);
        }
        else{
            //还未上传 开始上传影像资料
            if(dataListBean.getProgress()==0){
                netTips(dataListBean);
            }else{
                //正在上传
                if(dataListBean.isUpload()){
                    T.showShortBottom("uploading");
                }
                //继续上传
                else{
                    netTips(dataListBean);
                }
            }
        }
    }

    /**
     * 获取相关进度信息
     * @param serno
     * @return
     */
    private DataListBean getDataListBean(String serno){

        for(int i=0;i<dataListBeens.size();i++){
            DataListBean dataListBean = dataListBeens.get(i);
            if(dataListBean.getSerno().equals(serno))
                return dataListBean;
        }

        return null;
    }

    /**
     * 影像资料上网络提示
     */
    private void netTips(DataListBean dataListBean){
        this.dataListBean = dataListBean;
        if(APP.lat == 0 || APP.lng == 0){
            new DialogMessage(mContext).setMess("亲,定位失败了~请重新定位.")
                    .setConfirmListener(new DialogMessage.OnConfirmListener() {
                        @Override
                        public void onConfirm() {
                            ((BaseFragmentActivity)mContext).showWithStatus("");
                            aMapLocUtils.startLocation();
                        }
                    }).show();
            return;
        }
        String filesSize = FileUtil.getFileOrFilesSize(dataListBean);
        if(!NetworkUtils.isWifi(mContext)){
            new DialogMessage(mContext)
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
                        beforeUpLoadFiles();
                    }
                }).show();
        }else{
            beforeUpLoadFiles();
        }
    }

    /**
     * 上传文件前
     */
    private void beforeUpLoadFiles(){
        if(dataListBean!=null&&!dataListBean.isUpload()){
            //正在上传
            dataListBean.setUpload(true);
            serno = dataListBean.getSerno();
            imagesInfos = GsonUtil.GsonToList(dataListBean.getDataList(),ImagesInfoBean.class);

            ((BaseFragmentActivity)mContext).startProgressDialog("");
            //上传前更新进度情况
            notifyDataSetChanged();
            uploadingMicroCreditImage();
        }
    }


    /**
     * 正在上传微贷影像资料
     */
    private void uploadingMicroCreditImage(){

        //上传完成
        if(currentIndex==imagesInfos.size()){
            //回执
            loanType = 0;
            imageDocUploadReceipt();
        }else{
            ImagesInfoBean imagesInfoBean = imagesInfos.get(currentIndex);
            //是否上传成功
            if(!imagesInfoBean.isUploadSuccess()){
                List<String> upfiles = new ArrayList<>();
                List<String> desList = new ArrayList<>();
                List<String> photoDes = new ArrayList<>();
                String pathArr[] = imagesInfoBean.getPaths().split(",");
                for(int i=0;i<pathArr.length;i++){
                    upfiles.add(pathArr[i]);
                    desList.add(imagesInfoBean.getFileType());
                    photoDes.add(imagesInfoBean.getPhotoDes()+i);
                }
                String prdCode = "WDCP";
                fileUploadPresenter2.fileUpLoad(upfiles, desList, prdCode, serno,photoDes);
            }
            else{
                currentIndex ++;
                uploadingMicroCreditImage();
            }
        }
    }


    @Override
    public void uploadSuccess() {
        imagesInfos.get(currentIndex).setUploadSuccess(true);
        currentIndex ++;
        //保存进度
        dataListBean.setProgress(currentIndex);
        //刷新
        Gson gson =new Gson();
        String jsonStr = gson.toJson(imagesInfos);
        dataListBean.setDataList(jsonStr);
        notifyDataSetChanged();
        //保存最新上传进度信息
        BaseDb.saveOrUpdate(dataListBean);
        uploadingMicroCreditImage();
    }

    @Override
    public void uploadFail() {

        dataListBean.setUpload(false);
        //保存进度
        //dataListBean.setProgress(currentIndex);
        //刷新
        Gson gson =new Gson();
        String jsonStr = gson.toJson(imagesInfos);
        dataListBean.setDataList(jsonStr);
        notifyDataSetChanged();
        BaseDb.saveOrUpdate(dataListBean);
    }


    /**
     * 申请中数据列表数据是否授权，只有当在申请中，且需要继续提交申请时调用
     */
    private void applyAuth(){
        Map<String,String> map = new HashMap<>();
        map.put("token", BaseMainActivity.loginUserBean.getToken());
        map.put("prdId",prdId);
        map.put("user_id",cusId);
        ClientModel.applyAuth(map)
            .timeout(35, TimeUnit.SECONDS)
            .compose(RxSchedulersHelper.<BaseEntity>io_main())
            .map(new RxHttpBaseResultFunc())
            .subscribe(new RxSubscriber<BaseEntity>() {
                @Override
                public void _onNext(BaseEntity entity) {
                    handleApplyAuth(entity.getResult().toString());
                    MyLog.e("获取是否需要授权_onNext===="+entity.getMsg());
                }
                @Override
                public void _onError(String msg) {
                    T.showShortBottom(msg);
                    ((BaseFragmentActivity)mContext).stopProgressDialog();
                    MyLog.e("获取是否需要授权_onError===="+msg);
                }});
    }

    //判断是否需要授权申请
    private void handleApplyAuth(String resultStr){

        try{
            ((BaseFragmentActivity)mContext).stopProgressDialog();
            JSONObject jsonObject = new JSONObject(resultStr);
            boolean isGjjAuth = jsonObject.getBoolean("isGjjAuth");
            boolean isSheBaoAuth = jsonObject.getBoolean("isSheBaoAuth");
            APP.bundle.putBoolean("isGjjAuth",isGjjAuth);
            APP.bundle.putBoolean("isSheBaoAuth",isSheBaoAuth);
            if(isGjjAuth||isSheBaoAuth){
                Bundle bundle = new Bundle();
                bundle.putInt("type",1);
                IntentUtil.gotoActivity(mContext,AuthorizationActivity.class,bundle);
            }else {
                consumptionApplying();
            }
        }catch (Exception e){}
    }

    /**
     * 请求参数配置
     */
    private void setManagerLoanApplyMap(){

        Map<String,String> map = new HashMap<>();
        map.put("token", BaseMainActivity.loginUserBean.getToken());
        map.put("terminal_type","01");
        map.put("credit_coordinate", APP.lat+","+APP.lng);
        map.put("coordinate_county",APP.cityName);
        map.put("coordinate_city",APP.areaName);
        map.put("user_detail_id",getDatas().get(currentPosition).getDetail_id());
        map.put("serno",serno);
        managerLoanApply(map);
    }

    /**
     * 申请微贷
     */
    private void managerLoanApply(Map<String,String> map ){
        ClientModel.managerLoanApply(map)
            .timeout(20, TimeUnit.SECONDS)
            .compose(RxSchedulersHelper.<BaseEntity>io_main())
            .map(new RxHttpBaseResultFunc())
            .subscribe(new RxSubscriber<BaseEntity>() {
                @Override
                public void _onNext(BaseEntity baseEntity) {
                    if(loanType==0) {
                        synLocalData();
                        ((BaseFragmentActivity)mContext).stopProgressDialog();
                    }
                    else{
                        ((BaseFragmentActivity)mContext).stopProgressDialog();
                        currentApplyingBean = getDatas().remove(currentPosition);
                        notifyDataSetChanged();
                    }
                    showApplySuccessDialog();
                }
                @Override
                public void _onError(String msg) {
                    T.showShortBottom("申请失败");
                    ((BaseFragmentActivity)mContext).stopProgressDialog();
                }});
    }

    /**
     * 上传影像资料回执
     */
    private void imageDocUploadReceipt(){
        Map<String,String> map = new HashMap<>();
        map.put("token", BaseMainActivity.loginUserBean.getToken());
        map.put("serno",serno);
        map.put("image_doc_type","0001");
        map.put("terminal_type","01");
        map.put("status","0");
        ClientModel.imageDocUploadReceipt(map)
            .timeout(20, TimeUnit.SECONDS)
            .compose(RxSchedulersHelper.<BaseEntity>io_main())
            .map(new RxHttpBaseResultFunc())
            .subscribe(new RxSubscriber<BaseEntity>() {
                @Override
                public void _onNext(BaseEntity entity) {
                    T.showShortBottom(entity.getMsg());
                    setManagerLoanApplyMap();
                    MyLog.e("上传影像回执_onNext===="+entity.getMsg());
                }
                @Override
                public void _onError(String msg) {
                    T.showShortBottom(msg);
                    ((BaseFragmentActivity)mContext).stopProgressDialog();
                    MyLog.e("上传影像回执_onError===="+msg);
                }});
    }

    /**
     * 同步数据
     */
    private void synLocalData(){
        dataListBeens.remove(dataListBean);
        BaseDb.delete(dataListBean);
        currentApplyingBean = getDatas().remove(currentPosition);
        notifyDataSetChanged();
    }


    /**
     * 申请成功提示dialog
     */
    private void showApplySuccessDialog(){
        new DialogMessage(mContext).setMess("恭喜您，您的申请已成功提交!")
            .setConfirmTips("查看进度")
            .setConfirmListener(new DialogMessage.OnConfirmListener() {
                @Override
                public void onConfirm() {
                    goToLoanDetail();
                }
            }).show();
    }

    /**
     * 查看贷款详情
     */
    private void goToLoanDetail(){
        Bundle bundle = new Bundle();
        bundle.putString("serno",currentApplyingBean.getSerno());
        bundle.putString("prdId",currentApplyingBean.getPrdId());
        String prdType = currentApplyingBean.getPrd_type();
        if("2".equals(prdType))
            IntentUtil.gotoActivity(mContext,MicroCreditDetailActivity.class,bundle);
        else
            IntentUtil.gotoActivity(mContext,ConsumeDetailActivity.class,bundle);
    }


}