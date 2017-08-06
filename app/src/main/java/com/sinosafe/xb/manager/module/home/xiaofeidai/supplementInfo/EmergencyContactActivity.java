package com.sinosafe.xb.manager.module.home.xiaofeidai.supplementInfo;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.sinosafe.xb.manager.APP;
import com.sinosafe.xb.manager.BaseMainActivity;
import com.sinosafe.xb.manager.R;
import com.sinosafe.xb.manager.adapter.home.EmergencyContactAdapter;
import com.sinosafe.xb.manager.adapter.home.UpLoadGridAdapter;
import com.sinosafe.xb.manager.api.rxjava.RxHttpBaseResultFunc;
import com.sinosafe.xb.manager.api.rxjava.RxSchedulersHelper;
import com.sinosafe.xb.manager.api.rxjava.RxSubscriber;
import com.sinosafe.xb.manager.bean.BaseEntity;
import com.sinosafe.xb.manager.bean.LoanUserInfo;
import com.sinosafe.xb.manager.model.ClientModel;
import com.sinosafe.xb.manager.module.home.xiaofeidai.XiaoFeiDaiApplyActivity;
import com.sinosafe.xb.manager.module.home.xiaofeidai.authorization.AuthorizationActivity;
import com.sinosafe.xb.manager.module.imagePreview.MyImagePreviewDelActivity;
import com.sinosafe.xb.manager.utils.MyAMapLocUtils;
import com.sinosafe.xb.manager.utils.MyUtils;
import com.sinosafe.xb.manager.utils.T;
import com.sinosafe.xb.manager.utils.fileupload.FileUploadPresenter;
import com.sinosafe.xb.manager.widget.recycleview.HeaderAndFooterRecyclerViewAdapter;

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
import luo.library.base.widget.FullyLinearLayoutManager;
import luo.library.base.widget.NoScrollGridView;
import luo.library.base.widget.dialog.DialogMessage;

import static com.lzy.imagepicker.ImagePicker.REQUEST_CODE_PREVIEW;
import static com.lzy.imagepicker.ImagePicker.RESULT_CODE_ITEMS;

/**
 * 类名称：   com.sinosafe.xb.manager.module.home.xiaofeidai.supplementInfo
 * 内容摘要： //紧急联系人。
 * 修改备注：
 * 创建时间： 2017/6/10 0010
 * 公司：     深圳市华移科技股份有限公司
 * 作者：     Mr 张
 */
public class EmergencyContactActivity extends BaseFragmentActivity {

    @BindView(R.id.rv_contact)
    RecyclerView mRvContact;
    @BindView(R.id.gvImage)
    NoScrollGridView mGvImage;
    @BindView(R.id.btn_next)
    Button mBtnNext;

    EmergencyContactAdapter emergencyContactAdapter;
    private List<LoanUserInfo.UserContactor> contacts = new ArrayList<>();

    /**影像以及授权书*/
    private List<ImageItem> images = new ArrayList<>();
    private UpLoadGridAdapter imagesAdapter;
    private final static int MAX_IMAGE_NUM = 20;
    private final static int REQUEST_CODE_SELECT = 110;
    protected MyAMapLocUtils aMapLocUtils;
    private LoanUserInfo loanUserInfo;
    private FileUploadPresenter fileUploadPresenter;
    private boolean hasUpload = false;
    private boolean hasApplying = false;
    //需要授权时，是否授权成功
    private boolean authorizationFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consumption_apply_emergency_contact);

        ButterKnife.bind(this);
        initView();
        fileUploadPresenter = new FileUploadPresenter(this);
    }

    /**
     * 初始化
     */
    private void initView() {

        setTitleText("补全资料");
        loanUserInfo = XiaoFeiDaiApplyActivity.loanUserInfo;
        setImageSelectLimit(MAX_IMAGE_NUM);
        imagesAdapter = new UpLoadGridAdapter(this);
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

        //不为空
        if(loanUserInfo.getUser_contactor()!=null&&loanUserInfo.getUser_contactor().size()>0){
            contacts = loanUserInfo.getUser_contactor();
            if(contacts.size()<5)
                contacts.add(new LoanUserInfo.UserContactor());
        }else{
            contacts = new ArrayList<>();
            loanUserInfo.setUser_contactor(contacts);
            contacts.add(new LoanUserInfo.UserContactor());
        }
        emergencyContactAdapter = new EmergencyContactAdapter(this,contacts,loanUserInfo.getUser_detail().getUser_id());
        HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter
                = new HeaderAndFooterRecyclerViewAdapter(emergencyContactAdapter);
        mRvContact.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
        mRvContact.setLayoutManager(new FullyLinearLayoutManager(this));
        mRvContact.setNestedScrollingEnabled(false);
        emergencyContactAdapter.update(contacts);

        //定位
        aMapLocUtils = new MyAMapLocUtils(this);
        showWithStatus("");
        aMapLocUtils.startLocation();
    }


    @OnClick(R.id.btn_next)
    public void onViewClicked() {

        //紧急联系人至少三个
        if(emergencyContactAdapter.getDatas().size()<3){
            T.showShortBottom("至少填写三个紧急联系人");
            return ;
        }
        if(images.size()==0){
            T.showShortBottom("请上传影像资料");
            return ;
        }

        if(APP.lat == 0 || APP.lng == 0){
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
        //如果需要授权，且已经授权了。
        if((loanUserInfo.isGjjAuth()||loanUserInfo.isSheBaoAuth())&&authorizationFlag){
            showWithStatus("申请中...");
            if(hasApplying)
                imageDocUploadReceipt();
            else
                setManagerLoanApplyMap();
        }
        //如果需要授权，影像已上传，且还没授权了。
        else if((loanUserInfo.isGjjAuth()||loanUserInfo.isSheBaoAuth())&&hasUpload&&!authorizationFlag){
            mHandler.sendEmptyMessage(1);
        }
        //不需要授权
        else{
            nextBtn();
        }
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
                if(images2!=null)
                    images.addAll(images2);
                imagesAdapter.update(images);
            }
        }

        //授权成功回来
        else if(requestCode == 1000 &&resultCode==RESULT_OK){
            authorizationFlag = true;
            showWithStatus("申请中...");
            setManagerLoanApplyMap();
        }

        //读取联系人回来
        else if(requestCode==10000&&resultCode==RESULT_OK){
            if (data != null) {
                Uri uri = data.getData();
                if (uri != null) {
                    Cursor cursor = getContentResolver()
                            .query(uri,
                                    new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME},
                                    null, null, null);
                    String name = "", number = "";
                    while (cursor.moveToNext()) {
                        number = cursor.getString(0);
                        name = cursor.getString(1);
                    }
                    if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(number)) {
                        if (!MyUtils.isPhone(number)) {
                            T.showShortBottom("该号码不是正确的手机号！");
                            return;
                        }
                        emergencyContactAdapter.showDialogAddContact(name,number);
                    }
                }else{
                    emergencyContactAdapter.showDialogAddContact("","");
                }
            }
        }
    }

    /**
     * 下一步提交
     */
    private void nextBtn(){
        //已上传
        if(hasUpload){
            //要授权
            if((loanUserInfo.isGjjAuth()||loanUserInfo.isSheBaoAuth())){
                mHandler.sendEmptyMessage(1);
            }
            //不需要授权
            else{
                showWithStatus("申请中...");
                if(hasApplying)
                    imageDocUploadReceipt();
                else
                    setManagerLoanApplyMap();
            }
        }else{
            //待上传
            if(exitImageToUpLoad()){
                showWithStatus("上传中...");
                upLoadCertificatePhoto();
            }
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
     * 影像资料&征信材料
     */
    private void upLoadCertificatePhoto(){
        List<String> upfiles = new ArrayList<>();
        List<String> desList = new ArrayList<>();
        List<String> photoDes = new ArrayList<>();
        for(int i=0;i<images.size();i++){
            ImageItem imageItem = images.get(i);
            if(!imageItem.path.startsWith("http")){
                upfiles.add(imageItem.path);
                desList.add("XFD_00301");
                photoDes.add("影像资料+征信授权书"+i);
            }
        }
        String serno = loanUserInfo.getUser_detail().getSerno();
        String prdCode = "XFD";
        fileUploadPresenter.fileUpLoad(upfiles, desList, prdCode, serno,photoDes);
    }

    @Override
    public void uploadSuccess(List<String> fileIds) {
        super.uploadSuccess(fileIds);
        hasUpload = true;
        if((loanUserInfo.isGjjAuth()||loanUserInfo.isSheBaoAuth())){
            closeSVProgressHUD();
            mHandler.sendEmptyMessageDelayed(1,300);
        }
        else{
            setManagerLoanApplyMap();
        }
    }

    private void setManagerLoanApplyMap(){

        Map<String,String> map = new HashMap<>();
        map.put("token", BaseMainActivity.loginUserBean.getToken());

        map.put("terminal_type","01");
        map.put("credit_coordinate",APP.lat+","+APP.lng);
        map.put("coordinate_county",APP.cityName);
        map.put("coordinate_city",APP.areaName);
        map.put("user_detail_id",loanUserInfo.getUser_detail().getDetail_id());
        map.put("serno",loanUserInfo.getUser_detail().getSerno());
        managerLoanApply(map);
    }

    /**
     * 申请消费贷
     */
    private void managerLoanApply(Map<String,String> map ){
        ClientModel.managerLoanApply(map)
            .timeout(20, TimeUnit.SECONDS)
            .compose(RxSchedulersHelper.<BaseEntity>io_main())
            .map(new RxHttpBaseResultFunc())
            .subscribe(new RxSubscriber<BaseEntity>() {
                @Override
                public void _onNext(BaseEntity baseEntity) {
                    hasApplying = true;
                   imageDocUploadReceipt();
                }
                @Override
                public void _onError(String msg) {
                    T.showShortBottom("申请失败");
                    closeSVProgressHUD();
                }});
    }



    /**
     * 上传影像资料回执
     */
    private void imageDocUploadReceipt(){
        Map<String,String> map = new HashMap<>();
        map.put("token",BaseMainActivity.loginUserBean.getToken());
        map.put("serno",loanUserInfo.getUser_detail().getSerno());
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
                    T.showShortBottom("影像回执成功");
                    closeSVProgressHUD();
                    MyLog.e("上传影像回执_onNext===="+entity.getMsg());
                    mHandler.sendEmptyMessageDelayed(0,300);
                }
                @Override
                public void _onError(String msg) {
                    T.showShortBottom("影像回执失败");
                    closeSVProgressHUD();
                    MyLog.e("上传影像回执_onError===="+msg);
                }});
    }



    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    Bundle bundle = new Bundle();
                    bundle.putString("serno",loanUserInfo.getUser_detail().getSerno());
                    bundle.putString("prdId",loanUserInfo.getUser_detail().getPrd_id());
                    bundle.putString("prdType", "0");
                    IntentUtil.gotoActivity(EmergencyContactActivity.this,LoadApplyResultActivity.class,bundle);
                    break;
                case 1:
                    IntentUtil.gotoActivityForResult(EmergencyContactActivity.this,AuthorizationActivity.class,1000);
                    break;
            }
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
        aMapLocUtils.stopLocation();
    }

}
