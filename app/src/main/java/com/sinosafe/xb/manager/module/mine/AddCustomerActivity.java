package com.sinosafe.xb.manager.module.mine;

import android.Manifest;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.sinosafe.xb.manager.BaseMainActivity;
import com.sinosafe.xb.manager.R;
import com.sinosafe.xb.manager.api.rxjava.RxHttpBaseResultFunc;
import com.sinosafe.xb.manager.api.rxjava.RxSchedulersHelper;
import com.sinosafe.xb.manager.api.rxjava.RxSubscriber;
import com.sinosafe.xb.manager.bean.BaseEntity;
import com.sinosafe.xb.manager.bean.MyCustomerBean;
import com.sinosafe.xb.manager.model.ClientModel;
import com.sinosafe.xb.manager.utils.Constant;
import com.sinosafe.xb.manager.utils.FileUtil;
import com.sinosafe.xb.manager.utils.MyUtils;
import com.sinosafe.xb.manager.utils.T;
import com.sinosafe.xb.manager.utils.fileupload.FileUploadPresenter;
import com.sinosafe.xb.manager.widget.edit.ClearEditText;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import luo.library.base.base.BaseFragmentActivity;
import luo.library.base.base.BaseImage;
import luo.library.base.utils.MyLog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 新建客户/修改客户
 * Created by john lee on 2017/5/10.
 */

public class AddCustomerActivity extends BaseFragmentActivity {


    @BindView(R.id.iv_headPhoto)
    ImageView mIvHeadPhoto;
    @BindView(R.id.et_customerName)
    ClearEditText mEtCustomerName;
    @BindView(R.id.et_customerPhone)
    ClearEditText mEtCustomerPhone;
    @BindView(R.id.btn_confirm_add)
    Button mBtnConfirmAdd;
    @BindView(R.id.rootView)
    LinearLayout mRootView;

    private final int RESULT_REQUEST_PHOTO = 1005;
    private final int RESULT_REQUEST_PHOTO_CROP = 1006;
    private Uri fileCropUri;
    private Uri fileUri;

    //0:添加；1:修改
    private int type = -1;
    //客户头像
    private String cusPhoto = "";
    private MyCustomerBean myCustomerBean;
    private FileUploadPresenter fileUploadPresenter;
    //保存旧头像
    private String oldHeadPhoto = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_customer);
        ButterKnife.bind(this);
        initView();
    }

    protected void initView() {

        type = getIntent().getIntExtra("type",0);
        myCustomerBean = (MyCustomerBean) getIntent().getSerializableExtra("myCustomerBean");
        if(type==0)
            setTitleText("新建客户");
        else{
            setTitleText("编辑客户");
            mBtnConfirmAdd.setText("确认修改");
        }
        if(myCustomerBean!=null){
            mEtCustomerName.setText(myCustomerBean.getCus_name());
            mEtCustomerPhone.setText(myCustomerBean.getCus_phone());
            BaseImage.getInstance().displayCricleImage(this,myCustomerBean.getCus_photo(),mIvHeadPhoto,R.mipmap.icon_user_image);
            oldHeadPhoto = myCustomerBean.getCus_photo();
        }
        fileUploadPresenter = new FileUploadPresenter(this);
    }

    @OnClick({R.id.iv_headPhoto, R.id.btn_confirm_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_headPhoto:
                chooseHeadPortrait();
                break;
            case R.id.btn_confirm_add:

                if(isNull(mEtCustomerName)){
                    T.showShortBottom("请输入客户姓名");
                    return;
                }
                if(isNull(mEtCustomerPhone)){
                    T.showShortBottom("请输入客户电话号码");
                    return;
                }else{
                   if(!MyUtils.isPhone(mEtCustomerPhone.getText().toString())) {
                       T.showShortBottom("请输入正确的手机号码");
                       return;
                   }
                }

                Map<String,String> map = new HashMap<>();
                map.put("token", BaseMainActivity.loginUserBean.getToken());
                map.put("cus_name", mEtCustomerName.getText().toString());
                map.put("cus_phone", mEtCustomerPhone.getText().toString());
                if(!"".equals(cusPhoto)){
                    map.put("cus_photo", cusPhoto);
                }
                map.put("type", type+"");
                if(type==1){
                    map.put("cus_status", myCustomerBean.getCus_status());
                    map.put("cus_id", myCustomerBean.getCus_id()+"");
                    showWithStatus("修改中...");
                }else {
                    showWithStatus("添加中...");
                }
                addAndEditCustomerById(map);
                break;
        }
    }


    PopupWindow window;
    /**
     * 选择头像
     */
    private void chooseHeadPortrait() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popupwindow__head_portrait, null);
        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()

        view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.popshow_anim));
        LinearLayout ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
        ll_popup.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.pophidden_anim));

        if (window == null) {
            window = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);
            // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
            window.setFocusable(true);
            window.setOutsideTouchable(true);
            // 实例化一个ColorDrawable颜色为半透明
            window.setBackgroundDrawable(new BitmapDrawable());
            // 设置popWindow的显示和消失动画
            window.setAnimationStyle(R.style.popupwindow_anim);
        }
        // 在底部显示
        show(mRootView);

        view.findViewById(R.id.user_head_popupwindows_ll_root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });
        // 相机
        view.findViewById(R.id.btn_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera();
                window.dismiss();
            }
        });
        // 相册
        view.findViewById(R.id.btn_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photo();
                window.dismiss();
            }
        });
        // 取消
        view.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });

        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setWindowAlpa(false);
            }
        });
    }

    private void show(View v) {
        if (window != null && !window.isShowing()) {
            window.showAtLocation(v, Gravity.BOTTOM, 0, 0);
        }
        setWindowAlpa(true);
    }

    /**
     * 动态设置Activity背景透明度
     *
     * @param isopen
     */
    public void setWindowAlpa(boolean isopen) {
        if (Build.VERSION.SDK_INT < 11) {
            return;
        }
        final Window window = getWindow();
        final WindowManager.LayoutParams lp = window.getAttributes();
        window.setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND, WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        ValueAnimator animator;
        if (isopen) {
            animator = ValueAnimator.ofFloat(1.0f, 0.5f);
        } else {
            animator = ValueAnimator.ofFloat(0.5f, 1.0f);
        }
        animator.setDuration(400);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float alpha = (float) animation.getAnimatedValue();
                lp.alpha = alpha;
                window.setAttributes(lp);
            }
        });
        animator.start();
    }


    /**
     * 拍照
     */
    private void camera() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            fileUri = MyUtils.getOutputMediaFileUri(this);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            startActivityForResult(intent, RESULT_REQUEST_PHOTO);
        } else {
            EasyPermissions.requestPermissions(this, "", RC_CAMERA_PERM, Manifest.permission.CAMERA);
        }
    }

    /**
     * 相册
     */
    private void photo() {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_REQUEST_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_REQUEST_PHOTO) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    fileUri = data.getData();
                }
                fileCropUri = MyUtils.getOutputMediaFileUri(this);
                MyUtils.cropImageUri(this, fileUri, fileCropUri, 640, 640, RESULT_REQUEST_PHOTO_CROP);
            }

        } else if (requestCode == RESULT_REQUEST_PHOTO_CROP) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    String filePath = FileUtil.getPath(this, fileCropUri);
                    BaseImage.getInstance().displayCricleImage(this,new File(filePath),mIvHeadPhoto);
                    //生成文件上传
                    //生成文件上传
                    String serno = BaseMainActivity.loginUserBean.getActorno();
                    MyLog.e("serno==============================="+serno);
                    List<String> upfiles = Arrays.asList(filePath);
                    List<String> desList = Arrays.asList("XFD_00802");
                    List<String> photoDes = Arrays.asList("");
                    String prdCode = "XFD";
                    fileUploadPresenter.fileUpLoad(upfiles, desList, prdCode, serno,photoDes);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void uploadSuccess(List<String> fileIds) {
        super.uploadSuccess(fileIds);
        String actorno = BaseMainActivity.loginUserBean.getActorno();
        cusPhoto = Constant.getImagePath(fileIds.get(0),actorno);
        closeSVProgressHUD();
    }


    /**
     * 添加或者修改我的客户
     * @param filter
     */
    private void addAndEditCustomerById(Map<String,String> filter){

        ClientModel.addAndEditCustomerById(filter)
                .timeout(20, TimeUnit.SECONDS)
                .compose(RxSchedulersHelper.<BaseEntity<BaseEntity>>io_main())
                .map(new RxHttpBaseResultFunc())
                .subscribe(new RxSubscriber<BaseEntity>() {
                    @Override
                    public void _onNext(BaseEntity t) {

                        closeSVProgressHUD();
                        T.showShortBottom(t.getMsg());
                        //返回刷新
                        if(type==0){
                            setResult(RESULT_OK);
                            finish();
                        }
                        //修改后同步
                        else{
                            oldHeadPhoto = cusPhoto;
                            //delHeadPhoto();
                            Intent intent = new Intent();
                            myCustomerBean.setCus_name(mEtCustomerName.getText().toString());
                            myCustomerBean.setCus_phone(mEtCustomerPhone.getText().toString());
                            if(!"".equals(cusPhoto)){
                                myCustomerBean.setCus_photo(cusPhoto);
                            }
                            intent.putExtra("myCustomerBean",myCustomerBean);
                            setResult(RESULT_OK,intent);
                            finish();
                        }

                    }
                    @Override
                    public void _onError(String msg) {
                        closeSVProgressHUD();
                        showErrorWithStatus("服务器开小差了.");
                    }});
    }

    /**
     * 删除用户历史头像
     */
    public void delHeadPhoto(){

        if(myCustomerBean==null)
            return;
        if(oldHeadPhoto==null||"".equals(oldHeadPhoto))
            return;
        String separator = File.separator;
        Map<String, String> maps = new HashMap<>();
        maps.put("token", BaseMainActivity.loginUserBean.getToken());
        maps.put("func_code", "XFD");
        maps.put("serno", BaseMainActivity.loginUserBean.getActorno());
        maps.put("file_type", "XFD_00802");
        maps.put("user_name", BaseMainActivity.loginUserBean.getActorname());
        maps.put("org_code", BaseMainActivity.loginUserBean.getOrgid());
        String arr[] = oldHeadPhoto.replace("&Type=1","").split("=");
        oldHeadPhoto = arr[arr.length-1];
        maps.put("file_name", oldHeadPhoto);
        ClientModel.delHeadPhoto(maps)
                .timeout(20, TimeUnit.SECONDS)
                .compose(RxSchedulersHelper.<BaseEntity>io_main())
                .map(new RxHttpBaseResultFunc())
                .subscribe(new RxSubscriber<BaseEntity>() {
                    @Override
                    public void _onNext(BaseEntity entity) {
                        MyLog.e("删除头像反馈====="+entity.getMsg());
                    }
                    @Override
                    public void _onError(String msg) {
                        MyLog.e("删除头像反馈====="+msg);
                    }});
    }
}
