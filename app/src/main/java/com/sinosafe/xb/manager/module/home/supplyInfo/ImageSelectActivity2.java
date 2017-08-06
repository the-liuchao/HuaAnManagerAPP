package com.sinosafe.xb.manager.module.home.supplyInfo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;

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
import com.sinosafe.xb.manager.model.ClientModel;
import com.sinosafe.xb.manager.module.imagePreview.MyImagePreviewDelActivity;
import com.sinosafe.xb.manager.utils.Constant;
import com.sinosafe.xb.manager.utils.T;
import com.sinosafe.xb.manager.utils.fileupload.FileUploadPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import luo.library.base.base.BaseFragmentActivity;
import luo.library.base.utils.MyLog;
import luo.library.base.widget.NoScrollGridView;

import static com.lzy.imagepicker.ImagePicker.REQUEST_CODE_PREVIEW;
import static com.lzy.imagepicker.ImagePicker.RESULT_CODE_ITEMS;

/**
 * 图片选择(补充资料)
 */
public class ImageSelectActivity2 extends BaseFragmentActivity {


    @BindView(R.id.gvImage)
    NoScrollGridView mGvImage;

    private List<ImageItem> images = new ArrayList<>();
    private UpLoadGridAdapter imagesAdapter;
    private final static int REQUEST_CODE_SELECT = 110;
    private final static int MAX_IMAGE_NUM = 9;
    private FileUploadPresenter fileUploadPresenter;
    //功能类型 1：贷后凭证
    private int type = -1;
    //业务类型 0:贷后管理收集贷后凭证，1：还款中收集贷后凭证
    private int serviceType = 0;
    //产品类型
    private String prdType;
    private String serno;
    private boolean hasUpload = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_images);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        String titleStr = getIntent().getStringExtra("title");
        type = getIntent().getIntExtra("type",0);
        serviceType = getIntent().getIntExtra("serviceType",0);
        prdType = getIntent().getStringExtra("prdType");
        serno = getIntent().getStringExtra("serno");
        setTitleText(titleStr);

        fileUploadPresenter = new FileUploadPresenter(this);
        initListener();
        setImageSelectLimit(9);
        setRightButtonText("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userUploadOperator();
            }
        });
        setRightButtonTextColor(R.color.main_title_color);
    }


    private void userUploadOperator(){
        //收集贷款凭证
        if(type==1){
            if(images.size()==0){
                T.showShortBottom("请先选择贷后凭证");
                return;
            }

            showWithStatus("保存中...");
            if(hasUpload){
                imageDocUploadReceipt();
            }else{
                upLoadCertificatePhoto();
            }
        }
    }


    /**
     * 上传贷后凭证
     */
    private void upLoadCertificatePhoto(){
        List<String> upfiles = new ArrayList<>();
        List<String> desList = new ArrayList<>();
        List<String> photoDes = new ArrayList<>();
        String prdCode = "XFD";
        String desStr = "XFD_00901";
        //消费贷
        if("0".equals(prdType)){
            prdCode = "XFD";
            desStr = "XFD_00901";
        }else if("2".equals(prdType)){
            prdCode = "WDCP";
            desStr = "WDCP_00901";
        }
        for(int i=0;i<images.size();i++){
            ImageItem imageItem = images.get(i);
            upfiles.add(imageItem.path);
            desList.add(desStr);
            photoDes.add("贷后凭证"+i);
        }

        fileUploadPresenter.fileUpLoad(upfiles, desList, prdCode, serno,photoDes);
    }

    @Override
    public void uploadSuccess(List<String> fileIds) {
        super.uploadSuccess(fileIds);
        hasUpload = true;
        imageDocUploadReceipt();
    }


    /**
     * 监听
     */
    private void initListener() {

        String titleStr = getIntent().getStringExtra("title");
        setTitleText(titleStr);
        imagesAdapter = new UpLoadGridAdapter(this,3);
        mGvImage.setAdapter(imagesAdapter);

        mGvImage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position <= images.size() - 1) {
                    openImagePreviewActivity(position, images);
                    return;
                }
                setImageSelectLimit(MAX_IMAGE_NUM - images.size());
                openImageGridActivity();
            }
        });
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
        Intent intent = new Intent(this, ImageGridActivity.class);
        // 是否是直接打开相机
        //intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS,true);
        startActivityForResult(intent, REQUEST_CODE_SELECT);
    }

    /**
     * 预览选中的相册
     */
    private void openImagePreviewActivity(int position, List<ImageItem> images) {
        //打开预览
        Intent intentPreview = new Intent(this, MyImagePreviewDelActivity.class);
        // 但采用弱引用会导致预览弱引用直接返回空指针
        //DataHolder.getInstance().save(DataHolder.DH_CURRENT_IMAGE_FOLDER_ITEMS, images);
        //据说这样会导致大量图片崩溃
        intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) images);
        intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
        intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
        intentPreview.putExtra("needDel",1);
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
        else if (requestCode == REQUEST_CODE_PREVIEW && resultCode == ImagePicker.RESULT_CODE_BACK) {
            if (data != null) {
                ArrayList<ImageItem> images2 = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                images.clear();
                if (images2 != null)
                    images.addAll(images2);
                imagesAdapter.update(images);
            }
        }
    }

    /**
     * 上传影像资料回执
     */
    private void imageDocUploadReceipt(){
        Map<String,String> map = new HashMap<>();
        map.put("token", BaseMainActivity.loginUserBean.getToken());
        map.put("serno",serno);
        map.put("image_doc_type","0006");
        map.put("terminal_type","01");
        map.put("status","0");
        ClientModel.imageDocUploadReceipt(map)
            .timeout(20, TimeUnit.SECONDS)
            .compose(RxSchedulersHelper.<BaseEntity>io_main())
            .map(new RxHttpBaseResultFunc())
            .subscribe(new RxSubscriber<BaseEntity>() {
                @Override
                public void _onNext(BaseEntity entity) {
                    T.showShortBottom("影像回执成功");
                    mHandler.sendEmptyMessageDelayed(1,200);
                }
                @Override
                public void _onError(String msg) {
                    T.showShortBottom(msg);
                    closeSVProgressHUD();
                    MyLog.e("上传影像回执_onError===="+msg);
                }});
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(serviceType==0){
                setResult(RESULT_OK);
                finish();
            }
            else if(serviceType==1){
                Intent intent = new Intent(Constant.REFRESH_YEWU_REPAYMENT_DATA);
                sendBroadcast(intent);
                finish();
            }
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
