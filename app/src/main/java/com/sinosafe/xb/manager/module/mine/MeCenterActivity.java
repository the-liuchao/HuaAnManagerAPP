package com.sinosafe.xb.manager.module.mine;

import android.Manifest;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
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
import android.widget.TextView;

import com.sinosafe.xb.manager.APP;
import com.sinosafe.xb.manager.BaseMainActivity;
import com.sinosafe.xb.manager.R;
import com.sinosafe.xb.manager.api.rxjava.RxHttpBaseResultFunc;
import com.sinosafe.xb.manager.api.rxjava.RxHttpResultFunc;
import com.sinosafe.xb.manager.api.rxjava.RxSchedulersHelper;
import com.sinosafe.xb.manager.api.rxjava.RxSubscriber;
import com.sinosafe.xb.manager.bean.BaseEntity;
import com.sinosafe.xb.manager.bean.LoginUserBean;
import com.sinosafe.xb.manager.model.ClientModel;
import com.sinosafe.xb.manager.module.login.LoginActivity;
import com.sinosafe.xb.manager.utils.Constant;
import com.sinosafe.xb.manager.utils.FileUtil;
import com.sinosafe.xb.manager.utils.MyUtils;
import com.sinosafe.xb.manager.utils.T;
import com.sinosafe.xb.manager.utils.fileupload.FileUploadPresenter;
import com.star.lockpattern.util.ACache;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import luo.library.base.base.BaseDb;
import luo.library.base.base.BaseFragmentActivity;
import luo.library.base.base.BaseImage;
import luo.library.base.utils.IntentUtil;
import luo.library.base.utils.MyLog;
import luo.library.base.widget.dialog.DialogInput;
import luo.library.base.widget.dialog.DialogMessage;
import luo.library.base.widget.dialog.DialogQRcode;
import pub.devrel.easypermissions.EasyPermissions;

import static com.sinosafe.xb.manager.module.mine.GestureLoginActivity.GESTURE_NUM;

/**
 * 个人中心
 */
public class MeCenterActivity extends BaseFragmentActivity {

    @BindView(R.id.ll_personal_center)
    LinearLayout mPersonalCenter;
    @BindView(R.id.iv_headPhoto)
    ImageView mIvHeadPhoto;
    @BindView(R.id.llHeadPhoto)
    LinearLayout mLlHeadPhoto;
    @BindView(R.id.tv_userName)
    TextView mTvUserName;
    @BindView(R.id.iv_qr)
    ImageView mIvQr;
    @BindView(R.id.tv_organization)
    TextView mTvOrganization;
    @BindView(R.id.tv_landline)
    TextView mTvLandline;
    @BindView(R.id.tv_cornet)
    TextView mTvCornet;
    @BindView(R.id.tv_mobilePhone)
    TextView mTvMobilePhone;
    @BindView(R.id.ll_bindPhone)
    LinearLayout mLlBindPhone;
    @BindView(R.id.tv_my_email)
    TextView mTvMyEmail;
    @BindView(R.id.ll_my_email)
    LinearLayout mLlMyEmail;
    @BindView(R.id.btn_login_out)
    Button mBtnLoginOut;
    @BindView(R.id.qrLayout)
    LinearLayout qrLayout;

    @BindView(R.id.ll_modify_landline)
    LinearLayout modifyLandline;
    @BindView(R.id.ll_modify_cornet)
    LinearLayout modifyCornet;

    private final int RESULT_REQUEST_PHOTO = 1005;
    private final int RESULT_REQUEST_PHOTO_CROP = 1006;
    private final int RESULT_REQUEST_MOBILE_CROP = 1007;
    private Uri fileCropUri;
    private Uri fileUri;
    private int QR_WIDTH = 400, QR_HEIGHT = 400;
    private ACache aCache;

    //二维码Bitmap
    private Bitmap qrBitmap;
    private DialogInput dialogInput;
    private int updateType = -1;
    private FileUploadPresenter fileUploadPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_center);

        ButterKnife.bind(this);
        setTitleText("个人中心");
        fileUploadPresenter = new FileUploadPresenter(this);
        showLoginUserInfo();
    }

    /**
     * 展示登录用户信息
     */
    private void showLoginUserInfo() {

        mTvUserName.setText(BaseMainActivity.loginUserBean.getActorname());
        //头像
        BaseImage.getInstance().displayCricleImage(this,
                BaseMainActivity.loginUserBean.getAvatar(),mIvHeadPhoto,R.mipmap.icon_user_image);
        mTvOrganization.setText(BaseMainActivity.loginUserBean.getOrgid());
        mTvLandline.setText(BaseMainActivity.loginUserBean.getLandline());
        mTvCornet.setText(BaseMainActivity.loginUserBean.getCornet());

        //短号
        if(isNull(BaseMainActivity.loginUserBean.getCornet())){
            setTipsTextColor(0,mTvCornet,"");
        }else{
            setTipsTextColor(1,mTvCornet,BaseMainActivity.loginUserBean.getCornet());
        }

        //座机
        if(isNull(BaseMainActivity.loginUserBean.getLandline())){
            setTipsTextColor(0,mTvLandline,"");
        }else{
            setTipsTextColor(1,mTvLandline,BaseMainActivity.loginUserBean.getLandline());
        }

        //手机号码
        if(isNull(BaseMainActivity.loginUserBean.getTelnum())){
            setTipsTextColor(0,mTvMobilePhone,"");
        }else{
            setTipsTextColor(1,mTvMobilePhone,BaseMainActivity.loginUserBean.getTelnum());
        }

        //邮箱
        if(isNull(BaseMainActivity.loginUserBean.getUsermail())){
            setTipsTextColor(0,mTvMyEmail,"");
        }else{
            setTipsTextColor(1,mTvMyEmail,BaseMainActivity.loginUserBean.getUsermail());
        }

    }


    @OnClick({R.id.llHeadPhoto, R.id.iv_qr, R.id.ll_bindPhone, R.id.ll_my_email,
            R.id.btn_login_out,R.id.qrLayout,R.id.ll_modify_landline,R.id.ll_modify_cornet})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llHeadPhoto:
                chooseHeadPortrait();
                break;
            case R.id.iv_qr:
            case R.id.qrLayout:
                showMyQRCord();
                break;
            case R.id.ll_bindPhone:
                IntentUtil.gotoActivityForResult(this,BindPhoneActivity.class,RESULT_REQUEST_MOBILE_CROP);
                break;
            case R.id.ll_modify_landline:
                setMyLandLine();
                break;

            case R.id.ll_modify_cornet:
                setMyCornet();
                break;
            case R.id.ll_my_email:
                //setMyEmail();
                break;
            case R.id.btn_login_out:

                showExitDialog();

                break;
        }
    }


    /**
     * 显示我的二维码
     */
    private void showMyQRCord(){

        if(qrBitmap==null) {
            String url = BaseMainActivity.loginUserBean.getActorno();
            int qrWidth = 470*2;
            int qrHight = 440*2;
            qrBitmap = MyUtils.createQRImage(url,qrWidth,qrHight);
        }

        new DialogQRcode(this).hideBottomTextView().setTitleInfo("我的二维码").setQRBitmap(qrBitmap).show();
    }

    /**
     * 短号
     */
    private void setMyCornet() {

        dialogInput = new DialogInput(this, new DialogInput.OnTextBackListener() {
            @Override
            public void onTextBack(String text) {
               if (!MyUtils.isNumeric(text)) {
                    T.showShortBottom("请输入正确的短号");
                    return;
                }
                updateType = 0;
                dialogInput.dismiss();
                showWithStatus("绑定中...");
                bindUserInfo(text);
            }
        });
        dialogInput.setTitle("我的短号")
                .setHint("请输入常用短号").show();
    }

    /**
     * 设置座机
     */
    private void setMyLandLine() {

        dialogInput = new DialogInput(this, new DialogInput.OnTextBackListener() {
            @Override
            public void onTextBack(String text) {
                if (!MyUtils.isLandLine(text)||!MyUtils.isPhone(text)) {
                    T.showShortBottom("请输入正确的座机号");
                    return;
                }
                updateType = 1;
                dialogInput.dismiss();
                showWithStatus("绑定中...");
                bindUserInfo(text);
            }
        });
        dialogInput.setTitle("我的座机").setHint("请输入常用座机号").show();
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
        show(mPersonalCenter);

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
        //拍照回来
        if (requestCode == RESULT_REQUEST_PHOTO) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    fileUri = data.getData();
                }
                fileCropUri = MyUtils.getOutputMediaFileUri(this);
                MyUtils.cropImageUri(this, fileUri, fileCropUri, 640, 640, RESULT_REQUEST_PHOTO_CROP);
            }

        }
        //剪切后的用户头像
        else if (requestCode == RESULT_REQUEST_PHOTO_CROP) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    showWithStatus("修改中...");
                    String filePath = FileUtil.getPath(this, fileCropUri);
                    BaseImage.getInstance().displayCricleImage(this,new File(filePath),mIvHeadPhoto);
                    //生成文件上传
                    String serno = BaseMainActivity.loginUserBean.getActorno();
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

        //手机号码
        else if (requestCode == RESULT_REQUEST_MOBILE_CROP) {
            if (resultCode == Activity.RESULT_OK) {

                String mobile = data.getStringExtra("mobile");
                BaseMainActivity.loginUserBean.setTelnum(mobile);
                setTipsTextColor(1,mTvMobilePhone,BaseMainActivity.loginUserBean.getTelnum());
                BaseDb.update(BaseMainActivity.loginUserBean);
            }
        }
    }


    @Override
    public void uploadSuccess(List<String> fileIds) {
        super.uploadSuccess(fileIds);
        String actorno = BaseMainActivity.loginUserBean.getActorno();
        String avatar = Constant.getImagePath(fileIds.get(0),actorno);
        updateType = 2;
        bindUserInfo(avatar);
    }

    /**
     * 退出APP提示
     */
    private void showExitDialog(){

        new DialogMessage(this).setMess("亲，您确定要退出APP吗?")
                .setConfirmListener(new DialogMessage.OnConfirmListener() {
                    @Override
                    public void onConfirm() {
                        showWithStatus("正在退出...");
                        logout();
                    }
                }).show();
    }

    /**
     * 绑定登录用户信息
     */
    private void bindUserInfo(String info) {

        String cornet = "";
        String landline = "";
        String avatar = "";
        if(updateType==0){
            cornet = info;
        }else if(updateType==1){
            landline = info;
        }else if(updateType==2){
            avatar = info;
        }

        String token = BaseMainActivity.loginUserBean.getToken();
        ClientModel.editManagerById(token,"","",cornet,landline,avatar)
                .timeout(20, TimeUnit.SECONDS)
                .compose(RxSchedulersHelper.<BaseEntity<LoginUserBean>>io_main())
                .map(new RxHttpResultFunc<LoginUserBean>())
                .subscribe(new RxSubscriber<LoginUserBean>() {
                    @Override
                    public void _onNext(LoginUserBean t) {
                        closeSVProgressHUD();
                        if(updateType==0){
                            mTvCornet.setText(t.getCornet());
                            BaseMainActivity.loginUserBean.setCornet(t.getCornet());
                        }else if(updateType==1){
                            mTvLandline.setText(t.getLandline());
                            BaseMainActivity.loginUserBean.setLandline(t.getLandline());
                        }else if(updateType==2){
                            delHeadPhoto();
                            BaseMainActivity.loginUserBean.setAvatar(t.getAvatar());
                        }
                        BaseMainActivity.loginUserBean.setUsermail(t.getUsermail());
                        BaseDb.update(BaseMainActivity.loginUserBean);
                        T.showShortBottom("修改成功");

                    }
                    @Override
                    public void _onError(String msg) {
                        closeSVProgressHUD();
                        showErrorWithStatus(msg);
                    }});
    }


    /**
     * 退出登录请求
     */
    private void logout(){
        ClientModel.logout(BaseMainActivity.loginUserBean.getToken())
                .timeout(20, TimeUnit.SECONDS)
                .compose(RxSchedulersHelper.<BaseEntity<BaseEntity>>io_main())
                .map(new RxHttpBaseResultFunc())
                .subscribe(new RxSubscriber<BaseEntity>() {
                    @Override
                    public void _onNext(BaseEntity t) {
                        closeSVProgressHUD();
                        if(t.getCode()==1){
                            clearLoginUserInfo();
                        }else{
                            T.showShortBottom("退出失败");
                        }
                    }
                    @Override
                    public void _onError(String msg) {
                        closeSVProgressHUD();
                        showErrorWithStatus("服务器开小差了.");
                    }});
    }

    /**
     * 清理退出用户登录信息
     */
    private void clearLoginUserInfo(){
        //finish MainActivity
        aCache = ACache.get(this);
        aCache.remove(CreateGestureActivity.GESTURE_PASSWORD);
        //清除手势密码输入错误次数
        aCache.remove(GESTURE_NUM);
        APP.getApplication().getMainActivity().finish();
        APP.getApplication().setMainActivity(null);
        //删除登录用户信息
        BaseDb.delete(LoginUserBean.class, BaseMainActivity.loginUserBean.getId());
        BaseMainActivity.loginUserBean = null;
        //跳转到登录界面
        IntentUtil.gotoActivityAndFinish(this, LoginActivity.class);
    }

    private void setTipsTextColor(int flag, TextView textView,String content){

        if(flag==0){
            textView.setText("未设置");
            textView.setTextColor(Color.parseColor("#b5b7c4"));
        }
        else {
            textView.setText(content);
            textView.setTextColor(Color.parseColor("#545564"));
        }
    }

    /**
     * 删除用户历史头像
     */
    public void delHeadPhoto(){
        String separator = File.separator;
        String oldHeadPhoto = BaseMainActivity.loginUserBean.getAvatar();
        if(oldHeadPhoto==null||"".equals(oldHeadPhoto))
            return;
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(qrBitmap!=null&&!qrBitmap.isRecycled()){
            qrBitmap.recycle();
            //System.gc();
        }
    }
}
