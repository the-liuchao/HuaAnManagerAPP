package luo.library.base.base;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;

import java.util.List;

import luo.library.R;
import luo.library.base.dialog.SVProgressHUD;
import luo.library.base.utils.MyEasyPermissions;
import luo.library.base.utils.MyLog;
import luo.library.base.utils.SpUtils;
import luo.library.base.utils.SystemBarTintManager;
import luo.library.base.widget.DialogHelper;
import luo.library.base.widget.LoadingDialog;
import luo.library.base.widget.dialog.DialogMessage;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;


public class BaseFragmentActivity extends FragmentActivity implements EasyPermissions.PermissionCallbacks{

    protected static final int RC_READ_CONTACTS = 0x01;
    protected static final int RC_CAMERA_PERM = 0x03;
    protected static final int RC_EXTERNAL_STORAGE = 0x04;
    protected static final int LOCATION_PERMISSION = 0x0100;//定位权限
    protected static final int CALL_PHONE = 0x08;
    /**广播是否已经注册**/
    protected boolean hasRegister = false;
    private final static String TAG = "BaseFragmentActivity";
    //状态栏颜色设置,0:默认白色;1:浅蓝色
    public int statusBarType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
                | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_UNSPECIFIED);*/
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if(statusBarType==0){
            setStatusBarTransparent(this, getResources().getColor(R.color.statusBar));
        }else {
            setStatusBarTransparent(this, getResources().getColor(R.color.main_title_color));
        }
        //公司验证
        //new CnmobiAppManagement("TuJiClient",this);
    }


    /**
     * 沉浸状态栏
     * @param context
     * @param tintColor
     */
    public static void setStatusBarTransparent(Activity context, int tintColor) {
        // create our manager instance after the content view is set
        SystemBarTintManager tintManager = new SystemBarTintManager(context);
        // enable status bar tint
        tintManager.setStatusBarTintEnabled(true);
        // enable navigation bar tint
        tintManager.setNavigationBarTintEnabled(true);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            context.getWindow().setStatusBarColor(tintColor);
        } else {
            tintManager.setTintColor(tintColor);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(TAG);
        MobclickAgent.onResume(this);// 友盟统计
    }


    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(TAG);
        MobclickAgent.onPause(this);// 友盟统计
    }


    /**
     * 开启浮动加载进度条
     */
    public void startProgressDialog() {
        LoadingDialog.showDialogForLoading(this);
    }

    /**
     * 开启浮动加载进度条
     *
     * @param msg
     */
    public void startProgressDialog(String msg) {
        LoadingDialog.showDialogForLoading(this, msg, false);
    }

    /**
     * 开启浮动加载进度条
     *
     * @param msg
     */
    public void startProgressDialog2(String msg) {
        LoadingDialog.showDialogForLoading(this, msg, true);
    }

    /**
     * 停止浮动加载进度条
     */
    public void stopProgressDialog() {
        LoadingDialog.cancelDialogForLoading();
    }

    /**
     * 加载提示：旋转动画，可选择类型
     */
    public void showWithMaskType(){
        SVProgressHUD.showWithMaskType(this, SVProgressHUD.SVProgressHUDMaskType.None);
//        SVProgressHUD.showWithMaskType(this,SVProgressHUD.SVProgressHUDMaskType.Black);
//        SVProgressHUD.showWithMaskType(this, SVProgressHUD.SVProgressHUDMaskType.BlackCancel);
//        SVProgressHUD.showWithMaskType(this, SVProgressHUD.SVProgressHUDMaskType.Clear);
//        SVProgressHUD.showWithMaskType(this, SVProgressHUD.SVProgressHUDMaskType.ClearCancel);
//        SVProgressHUD.showWithMaskType(this, SVProgressHUD.SVProgressHUDMaskType.Gradient);
//        SVProgressHUD.showWithMaskType(this, SVProgressHUD.SVProgressHUDMaskType.GradientCancel);
    }

    /**
     * 加载提示：旋转动画+文字提示
     */
    public void showWithStatus(String promptStr){
        if(promptStr==null)
            SVProgressHUD.showWithStatus(this, "加载中...");
        else{
            SVProgressHUD.showWithStatus(this, promptStr);
        }
    }

    /**
     * 窗口提示：文字提示,可选择类型
     */
    public void showInfoWithStatus(String promptStr){
        SVProgressHUD.showInfoWithStatus(this, promptStr, SVProgressHUD.SVProgressHUDMaskType.None);
    }

    /**
     * 操作成功提示：文字提示
     */
    public void showSuccessWithStatus(String promptStr){
        if(promptStr==null)
            SVProgressHUD.showSuccessWithStatus(this, "操作成功！");
        else{
            SVProgressHUD.showSuccessWithStatus(this, promptStr);
        }
    }

    /**
     * 操作失败提示：文字提示
     */
    public void showErrorWithStatus(String promptStr){
        if(promptStr==null)
            SVProgressHUD.showErrorWithStatus(this, "操作失败～", SVProgressHUD.SVProgressHUDMaskType.GradientCancel);
        else{
            SVProgressHUD.showErrorWithStatus(this, promptStr, SVProgressHUD.SVProgressHUDMaskType.GradientCancel);
        }
    }



    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progress = progress + 5;
            if (SVProgressHUD.getProgressBar(BaseFragmentActivity.this).getMax() != SVProgressHUD.getProgressBar(BaseFragmentActivity.this).getProgress()) {
                SVProgressHUD.getProgressBar(BaseFragmentActivity.this).setProgress(progress);
                SVProgressHUD.setText(BaseFragmentActivity.this, "进度 "+progress+"%");

                mHandler.sendEmptyMessageDelayed(0,500);
            }
            else{
                SVProgressHUD.dismiss(BaseFragmentActivity.this);
                SVProgressHUD.getProgressBar(BaseFragmentActivity.this).setProgress(0);
            }

        }
    };

    int progress;
    /**
     * 窗口提示：进度提示,可选择进度颜色类型
     */
    public void showWithProgress(View view){
        SVProgressHUD.showWithProgress(this, "进度 "+progress+"%", SVProgressHUD.SVProgressHUDMaskType.Black);
        progress = 0;
        mHandler.sendEmptyMessageDelayed(0,500);
    }

    /**
     * 关闭提示窗口
     */
    public void closeSVProgressHUD(){
        if(SVProgressHUD.isShowing(this)){
            SVProgressHUD.dismiss(this);
        }
    }


    /**
     * 设置标题栏信息
     */
    public void setTitleText(String string) {
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.lay_bg);
        LinearLayout backTv = (LinearLayout) findViewById(R.id.ly_base_back);
        backTv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                finish();
            }
        });
        TextView titleTv = (TextView) findViewById(R.id.tv_base_titleText);
        if(statusBarType==0){
            relativeLayout.setBackgroundResource(R.color.other_title_color);
            titleTv.setTextColor(Color.rgb(40,41,50));
        }else{
            relativeLayout.setBackgroundResource(R.color.main_title_color);
            titleTv.setTextColor(Color.rgb(255,255,255));
        }
        titleTv.setText(string);
    }

    /**
     * 隐藏后退
     */
    public void hideBack() {
        LinearLayout backTv = (LinearLayout) findViewById(R.id.ly_base_back);
        backTv.setVisibility(View.INVISIBLE);
    }

    /**
     * 设置标题栏右边按钮文字
     */
    public void setRightButtonText(String string, View.OnClickListener onClickListener) {
        TextView editTv = (TextView) findViewById(R.id.tv_base_edit);
        LinearLayout rightLayout = (LinearLayout) findViewById(R.id.rightLayout);
        if(statusBarType==0){
            editTv.setTextColor(Color.rgb(40,41,50));
        }else{
            editTv.setTextColor(Color.rgb(255,255,255));
        }
        editTv.setVisibility(View.VISIBLE);
        rightLayout.setVisibility(View.VISIBLE);
        editTv.setText(string);
        rightLayout.setOnClickListener(onClickListener);
    }


    /**
     * 隐藏右边按钮
     */
    public void hideRightButtonText(){
        LinearLayout rightLayout = (LinearLayout) findViewById(R.id.rightLayout);
        rightLayout.setVisibility(View.GONE);
    }

    /**
     * 设置又边按钮字体颜色
     * @param recId
     */
    public void setRightButtonTextColor(int recId){
        TextView editTv = (TextView) findViewById(R.id.tv_base_edit);
        editTv.setTextColor(recId);
    }


    /**
     * 设置又边按钮文字
     * @param titleStr
     */
    public void setRightButtonText(String titleStr){
        TextView editTv = (TextView) findViewById(R.id.tv_base_edit);
        editTv.setText(titleStr);
    }

    /**
     * 设置右边图片
     */
    public void setRightImg(int bg, View.OnClickListener onClickListener) {
        ImageView imageView = (ImageView) findViewById(R.id.iv_right);
        LinearLayout rightLayout = (LinearLayout) findViewById(R.id.rightLayout);
        imageView.setImageResource(bg);
        rightLayout.setOnClickListener(onClickListener);
    }

    /**
     * 设置右边图片
     */
    public void setRightImg(int bg) {
        ImageView imageView = (ImageView) findViewById(R.id.iv_right);
        imageView.setImageResource(bg);
    }

    /**
     * 标题栏背景设为透明
     */
    public void setBgNul() {
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.lay_bg);
        relativeLayout.setBackgroundColor(Color.parseColor("#00000000"));
    }

    /**
     * 标题栏背景
     */
    public void setTitleViewBg(int bg) {
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.lay_bg);
        relativeLayout.setBackgroundResource(bg);
    }

    /**
     * 弹出Toast
     */
    public void showToast(String string) {
        Toast.makeText(BaseFragmentActivity.this, string, Toast.LENGTH_SHORT).show();
    }

    /**
     * 获取EditView的文字
     */
    public String getStr(EditText editText) {
        return editText.getText().toString().trim();
    }

    /**
     * 获取TextView的文字
     */
    public String getStr(TextView textView) {
        return textView.getText().toString();
    }

    /**
     * 获取string的文字
     */
    public String getStr(int id) {
        return getResources().getString(id);
    }

    /**
     * 检查字符串是否是空对象或空字符串
     */
    public boolean isNull(String str) {
        if (null == str || "".equals(str) || "null".equalsIgnoreCase(str)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 检查字符串是否是空对象或空字符串
     */
    public boolean isNull(Object object) {
        String str = (String)object;
        if (null == str || "".equals(str) || "null".equalsIgnoreCase(str)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 检查EditView是否是空对象或空字符串
     */
    public boolean isNull(EditText str) {
        if (null == str.getText().toString() || "".equals(str.getText().toString())
                || "null".equalsIgnoreCase(str.getText().toString())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 检查TextView是否是空对象或空字符串
     */
    public boolean isNull(TextView str) {
        if (null == str.getText().toString() || "".equals(str.getText().toString())
                || "null".equalsIgnoreCase(str.getText().toString())) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 启动Activity
     */
    public void openActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    /**
     * 保存sp数据
     *
     * @param key
     * @param object
     */
    public void putSp(String key, Object object) {
        SpUtils.put(BaseFragmentActivity.this, key, object);
    }

    /**
     * 清除Sp数据
     */
    public void clearSp() {
        SpUtils.clear(BaseFragmentActivity.this);
    }

    /**
     * 获取sp数据
     *
     * @param key
     * @param object
     * @return
     */
    public Object getSp(String key, Object object) {
        return SpUtils.get(BaseFragmentActivity.this, key, object);
    }

    /**
     * 打开设置详情页
     */
    protected void openDetails() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", getPackageName(), null));
        startActivity(intent);
    }

    /**
     * 读取联系人
     */
    @AfterPermissionGranted(RC_READ_CONTACTS)
    public void readContacts() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.READ_CONTACTS)) {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
            startActivityForResult(intent, 10000);
        } else {
            EasyPermissions.requestPermissions(this, "", RC_READ_CONTACTS, Manifest.permission.READ_CONTACTS);
        }
    }

    /**
     * 拨打电话
     * @param phoneNum
     */
    public void callPhoneTips(final String phoneNum){

        if (!MyEasyPermissions.hasPermissions(this, Manifest.permission.CALL_PHONE)) {
            EasyPermissions.requestPermissions(this ,"需要打电话权限", CALL_PHONE, Manifest.permission.CALL_PHONE);
            return;
        }

        new DialogMessage(this).setMess("即将拨打："+phoneNum )
                .setConfirmListener(new DialogMessage.OnConfirmListener() {
                    @Override
                    public void onConfirm() {
                        callPhone(phoneNum);
                    }
                }).show();
    }

    private void callPhone(String phoneNum){
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        try {
            startActivity(intent);
        }catch (Exception e){
            DialogHelper.getConfirmDialog(this, "", "没有权限, 您需要去设置中开打启电话权限。", "去设置", "取消", false, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    openDetails();
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            }).show();
        }
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        MyLog.e("onPermissionsDenied======"+requestCode);
        if (requestCode == RC_READ_CONTACTS) {
            DialogHelper.getConfirmDialog(this, "", "没有权限, 您需要去设置中开启访问联系人权限.", "去设置", "取消", false, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    openDetails();
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).show();
        } else if (requestCode == RC_EXTERNAL_STORAGE) {
            DialogHelper.getConfirmDialog(this, "", "没有权限, 您需要去设置中开启读取手机存储权限.", "去设置", "取消", false, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    openDetails();
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).show();
        } else if (requestCode == RC_CAMERA_PERM) {
            DialogHelper.getConfirmDialog(this, "", "没有权限, 您需要去设置中开启相机权限.", "去设置", "取消", false, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    openDetails();
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            }).show();
        }
        else if (requestCode == LOCATION_PERMISSION) {
            DialogHelper.getConfirmDialog(this, "", "没有权限, 您需要去设置中开启定位权限。", "去设置", "取消", false, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    openDetails();
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            }).show();
        }

        else if (requestCode == CALL_PHONE) {
            DialogHelper.getConfirmDialog(this, "", "没有权限, 您需要去设置中开打启电话权限。", "去设置", "取消", false, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    openDetails();
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            }).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions
            , @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
        MyLog.e("onRequestPermissionsResult======"+requestCode);
    }


    /**
     * 上传图片成功会重载该方法
     * @param fileIds
     */
    public void uploadSuccess(List<String> fileIds){

    }


    /**
     * 上传图片失败会重载该方法
     */
    public void uploadFail(){

    }

    /**
     * 关闭activity广播
     */
    protected BroadcastReceiver finishAtivity = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            BaseFragmentActivity.this.finish();
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
        {
            if(SVProgressHUD.isShowing(this)){
                SVProgressHUD.dismiss(this);
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(hasRegister){
            unregisterReceiver(finishAtivity);
        }
    }
}
