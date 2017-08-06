package com.sinosafe.xb.manager.module.home.mianqian;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.sinosafe.xb.manager.BaseMainActivity;
import com.sinosafe.xb.manager.MainActivity;
import com.sinosafe.xb.manager.R;
import com.sinosafe.xb.manager.adapter.home.UpLoadGridAdapter;
import com.sinosafe.xb.manager.api.rxjava.RxHttpBaseResultFunc;
import com.sinosafe.xb.manager.api.rxjava.RxSchedulersHelper;
import com.sinosafe.xb.manager.api.rxjava.RxSubscriber;
import com.sinosafe.xb.manager.bean.BaseEntity;
import com.sinosafe.xb.manager.bean.YeWuBean;
import com.sinosafe.xb.manager.model.ClientModel;
import com.sinosafe.xb.manager.utils.Constant;
import com.sinosafe.xb.manager.utils.FileUtil;
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

import static com.lzy.imagepicker.ImagePicker.REQUEST_CODE_PREVIEW;
import static com.lzy.imagepicker.ImagePicker.RESULT_CODE_ITEMS;

/**
 * 办理面签
 */
public class BanLiMianQianActivity extends BaseFragmentActivity {

    private final static int REQUEST_CODE_SELECT = 110;
    private final static int MAX_IMAGE_NUM = 9;
    /**影像*/
    @BindView(R.id.gvImage)
    GridView mGvImage;
    /**合照*/
    @BindView(R.id.gvGroom)
    GridView mGvGroom;
    @BindView(R.id.btnSubmit)
    Button mBtnSubmit;
    /**影像*/
    private List<ImageItem> images = new ArrayList<>();
    /**合照*/
    private List<ImageItem> grooms = new ArrayList<>();
    //全部图片集合
    private List<ImageItem> imageItems = new ArrayList<>();
    private UpLoadGridAdapter imagesAdapter,groomsAdapter;
    //0：影像；1：合照
    private int selectType = -1;
    private YeWuBean yeWuBean;
    private String prdCode ,desListStr;

    private FileUploadPresenter fileUploadPresenter;
    private boolean hasUpload = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banli_mianqian);

        ButterKnife.bind(this);

        setTitleText("办理面签");
        initListener();
    }

    /**
     * 监听
     */
    private void initListener() {
        yeWuBean = (YeWuBean) getIntent().getSerializableExtra("yeWuBean");
        //消费贷
        if("0".equals(yeWuBean.getPRD_TYPE())){
            prdCode = "XFD";
            desListStr = "XFD_00801";
        }
        //微贷
        else{
            prdCode = "WDCP";
            desListStr = "WDCP_00801";
        }
        fileUploadPresenter = new FileUploadPresenter(this);

        imagesAdapter = new UpLoadGridAdapter(this);
        groomsAdapter = new UpLoadGridAdapter(this);
        mGvImage.setAdapter(imagesAdapter);
        mGvGroom.setAdapter(groomsAdapter);

        mGvImage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectType = 0;
                if (position <= images.size()-1) {
                    openImagePreviewActivity(position,images);
                    return;
                }
                setImageSelectLimit(MAX_IMAGE_NUM - images.size());
                openImageGridActivity();
            }
        });

        mGvGroom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                selectType = 1;
                if(position <= grooms.size()-1) {
                    openImagePreviewActivity(position,grooms);
                    return;
                }
                setImageSelectLimit(MAX_IMAGE_NUM - grooms.size());
                openImageGridActivity();
            }
        });
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
        intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS,true);
        startActivityForResult(intent, REQUEST_CODE_SELECT);
    }

    /**
     * 预览选中的相册
     */
    private void openImagePreviewActivity(int position,List<ImageItem> images){
        //打开预览
        Intent intentPreview = new Intent(this, ImagePreviewDelActivity.class);
        // 但采用弱引用会导致预览弱引用直接返回空指针
        //DataHolder.getInstance().save(DataHolder.DH_CURRENT_IMAGE_FOLDER_ITEMS, images);
        //据说这样会导致大量图片崩溃
        intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>)images );
        intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
        intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
        startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
    }

    @OnClick(R.id.btnSubmit)
    public void onViewClicked() {

        if(images.size()==0){
            T.showShortBottom("请上传影像资料");
            return;
        }

        if(grooms.size()==0){
            T.showShortBottom("请上传客户合照");
            return;
        }
        //使用网络提示
        if (!NetworkUtils.isWifi(this)) {
            showNetTipsDialog();
        } else {
            showWithStatus("上传中...");
            upLoadBadgePhoto();
        }
    }

    /**
     * 上传前网络提示
     */
    private void showNetTipsDialog() {

        imageItems.clear();
        imageItems.addAll(images);
        imageItems.addAll(grooms);

        String filesSize = FileUtil.getFileOrFilesSize(imageItems);
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
                    showWithStatus("上传中...");
                    upLoadBadgePhoto();
                }
            }).show();
    }

    /**
     * 查看进度
     */
    private void toApplyProgress(){
        IntentUtil.gotoActivityAndFinish(this,MainActivity.class);
        Intent intent = new Intent(Constant.LOAD_APPLY_PROGRESS);
        intent.putExtra("serno",yeWuBean.getSERNO());
        intent.putExtra("prdId",yeWuBean.getPRD_ID());
        intent.putExtra("prdType",yeWuBean.getPRD_TYPE());
        sendBroadcast(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //选择回来
        if (requestCode == REQUEST_CODE_SELECT && resultCode == RESULT_CODE_ITEMS) {
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                ArrayList<ImageItem> images2 = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if(selectType==0){
                    images.addAll(images2);
                    imagesAdapter.update(images);
                }
                else{
                    grooms.addAll(images2);
                    groomsAdapter.update(grooms);
                }
            }
        }

        //预览回来
        else if(requestCode == REQUEST_CODE_PREVIEW &&resultCode==ImagePicker.RESULT_CODE_BACK){
            if (data != null){

                ArrayList<ImageItem> images2 = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                if(selectType==0){
                    images.clear();
                    if(images2!=null)
                        images.addAll(images2);
                    imagesAdapter.update(images);
                }
                else{
                    grooms.clear();
                    if(images2!=null)
                        grooms.addAll(images2);
                    groomsAdapter.update(grooms);
                }
            }
        }
    }

    /**
     * 上传影像资料、客户合照
     */
    private void upLoadBadgePhoto(){
        List<String> upfiles = new ArrayList<>();
        List<String> desList = new ArrayList<>();
        List<String> photoDes = new ArrayList<>();
        //影像
        for(int i=0;i<images.size();i++){
            ImageItem imageItem = images.get(i);
            upfiles.add(images.get(i).path);
            desList.add(desListStr);
            photoDes.add("影像资料"+i);
        }
        //合照
        for(int i=0;i<grooms.size();i++){
            ImageItem imageItem = grooms.get(i);
            upfiles.add(grooms.get(i).path);
            desList.add(desListStr);
            photoDes.add("客户合照"+i);
        }
        String serno = yeWuBean.getSERNO();
        fileUploadPresenter.fileUpLoad(upfiles, desList, prdCode, serno,photoDes);
    }

    @Override
    public void uploadSuccess(List<String> fileIds) {
        super.uploadSuccess(fileIds);
        imageDocUploadReceipt();
        closeSVProgressHUD();
        showResultDialog();
    }


    /**
     * 上传影像资料回执
     */
    private void imageDocUploadReceipt(){
        Map<String,String> map = new HashMap<>();
        map.put("token", BaseMainActivity.loginUserBean.getToken());
        map.put("serno",yeWuBean.getSERNO());
        map.put("image_doc_type","0003");
        map.put("status","0");
        map.put("terminal_type","01");
        ClientModel.imageDocUploadReceipt(map)
            .timeout(20, TimeUnit.SECONDS)
            .compose(RxSchedulersHelper.<BaseEntity>io_main())
            .map(new RxHttpBaseResultFunc())
            .subscribe(new RxSubscriber<BaseEntity>() {
                @Override
                public void _onNext(BaseEntity entity) {
                    MyLog.e("上传影像回执_onNext===="+entity.getMsg());
                    T.showShortBottom(entity.getMsg());
                }
                @Override
                public void _onError(String msg) {
                    T.showShortBottom(msg);
                    MyLog.e("上传影像回执_onError===="+msg);
                }});
    }

    /**
     * 显示操作结果
     */
    private void showResultDialog(){
        new DialogMessage(this)
            .setMess("提交成功")
            .setConfirmTips("查看进度")
            .setOtherConfirmTips("返回首页")
            .setBottomImage(R.mipmap.icon_tijiaochneggong)
            .setConfirmListener(new DialogMessage.OnConfirmListener() {
                @Override
                public void onConfirm() {
                    toApplyProgress();
                }
            })
            .setOtherConfirmListener(new DialogMessage.OnOtherConfirmListener() {
                @Override
                public void onOtherConfirm() {
                    IntentUtil.gotoActivityAndFinish(BanLiMianQianActivity.this, MainActivity.class);
                }
            }).show();
    }
}
