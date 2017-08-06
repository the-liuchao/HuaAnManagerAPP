package com.sinosafe.xb.manager.module.home.supplyInfo.xiaofeidai;

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
 * 微贷提供资料
 */
public class ApplyXiaoDataActivity extends BaseFragmentActivity {

    @BindView(R.id.tvIDCard)
    TextView mTvIDCard;
    @BindView(R.id.spouseIdCardLayout)
    RelativeLayout mSpouseIdCardLayout;
    @BindView(R.id.tvWorkCard)
    TextView mTvWorkCard;
    @BindView(R.id.workCardLayout)
    RelativeLayout mWorkCardLayout;
    @BindView(R.id.tvVehicle)
    TextView mTvVehicle;
    @BindView(R.id.vehicleLayout)
    RelativeLayout mVehicleLayout;
    @BindView(R.id.tvHouseProperty)
    TextView mTvHouseProperty;
    @BindView(R.id.housePropertyLayout)
    RelativeLayout mHousePropertyLayout;
    @BindView(R.id.tvImageData)
    TextView mTvImageData;
    @BindView(R.id.imageDataLayout)
    RelativeLayout mImageDataLayout;
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

    private FileUploadPresenter fileUploadPresenter;
    //当前上传的图片索引
    private int currentIndex = 0;
    //资料是否上传完成
    private boolean upLoadSuccess = false;
    //流水号
    private String serno;
    //当前图片文件中文描述
    private String currentPhotoDes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiaofeidai_list);
        ButterKnife.bind(this);

        fileUploadPresenter = new FileUploadPresenter(this);
        initView();
    }

    private void initView() {
        setTitleText("补充资料");
        textViews.add(mTvIDCard);
        textViews.add(mTvWorkCard);
        textViews.add(mTvVehicle);
        textViews.add(mTvHouseProperty);
        textViews.add(mTvImageData);
        serno = getIntent().getStringExtra("serno");

        //赋值
        if(imagesInfoBeens.size()==0){
            for(int j=0;j<5;j++)
                imagesInfoBeens.add(new ImagesInfoBean());
        }
    }


    @OnClick({R.id.spouseIdCardLayout, R.id.workCardLayout,R.id.vehicleLayout, R.id.housePropertyLayout,
            R.id.imageDataLayout, R.id.btn_submit})
    public void onViewClicked(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {

            //配偶身份证
            case R.id.spouseIdCardLayout:
                type = 1;
                fileType = "XFD_00201";
                bundle.putString("title","配偶身份证");
                currentPhotoDes = "配偶身份证";
                break;
            //工牌
            case R.id.workCardLayout:
                type = 2;
                fileType = "XFD_00501";
                bundle.putString("title","上传工牌");
                currentPhotoDes = "工牌";
                break;
            //机动车
            case R.id.vehicleLayout:
                type = 3;
                fileType = "XFD_00701";
                bundle.putString("title","机动车登记证明");
                currentPhotoDes = "机动车登记证明";
                break;
            //房产证
            case R.id.housePropertyLayout:
                type = 4;
                fileType = "XFD_00701";
                bundle.putString("title","房产所有权证");
                currentPhotoDes = "房产所有权证";
                break;
            //影像资料
            case R.id.imageDataLayout:
                type = 5;
                fileType = "XFD_00301";
                bundle.putString("title","影像资料+征信授权书");
                currentPhotoDes = "影像资料+征信授权书";
                break;
            //提交
            case R.id.btn_submit:
                type = -1;
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
        if(!hasSelect()){
            T.showShortBottom("请先添加需补充的资料");
            return;
        }
        //上传完，影像回执
        if (upLoadSuccess){
            showWithStatus("保存中...");
            imageDocUploadReceipt();
        }
        //继续上传，回执
        else {
            showWithStatus("上传中...");
            //移除无效的对象
            MyUtils.removeInvalidData(imagesInfoBeens);

            //使用网络提示
            if(!NetworkUtils.isWifi(this)){
                showNetTipsDialog();
            }else{
                uploadingMicroCreditImage();
            }
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


    private String findImagesPathByType() {

        String pathsStr = "";
        for (int i = 0; i < imagesInfoBeens.size(); i++) {
            ImagesInfoBean imagesInfoBean = imagesInfoBeens.get(i);
            if (imagesInfoBean!=null && imagesInfoBean.getType() == type) {
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
        if (requestCode == 1000 && resultCode == RESULT_OK) {
            String pathsStr = data.getStringExtra("path");
            //已存在
            if (isExit) {
                imagesInfoBean.setPaths(pathsStr);
                imagesInfoBean.setPhotoDes(currentPhotoDes);
                imagesInfoBeens.remove(imagesInfoBean);
                imagesInfoBeens.add(type-1,imagesInfoBean);
                if ("".equals(pathsStr)) {
                    setTipsTextColor(0);
                } else {
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
                imagesInfoBeens.add(type - 1, imagesInfoBeanNew);
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
        for(int j=imagesInfoBeens.size();j<5;j++)
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
     * 正在上传微贷影像资料
     */
    private void uploadingMicroCreditImage() {

        //上传完成
        if (currentIndex == imagesInfoBeens.size()) {
            T.showShortBottom("恭喜您,资料上传完成!");
            upLoadSuccess = true;
            //closeSVProgressHUD();
            imageDocUploadReceipt();
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
                String prdCode = "XFD";
                int index = imagesInfoBean.getType() - 1;
                /*if (index < 10)
                    fileUploadPresenter.setFileName("00" + index);
                else
                    fileUploadPresenter.setFileName("0" + index);*/

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
        uploadingMicroCreditImage();
    }


    private Handler mHandler = new Handler() {
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

                    MyLog.e("上传影像回执_onNext====" + entity.getMsg());
                    if(entity.getCode()==1){
                        T.showShortBottom("影像回执成功");
                        closeSVProgressHUD();
                        mHandler.sendEmptyMessageDelayed(0,200);
                    }else{
                        T.showShortBottom("影像回执失败");
                    }
                }

                @Override
                public void _onError(String msg) {
                    T.showShortBottom("回执失败,请重试");
                    closeSVProgressHUD();
                    MyLog.e("上传影像回执_onError====" + msg);
                }
            });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
