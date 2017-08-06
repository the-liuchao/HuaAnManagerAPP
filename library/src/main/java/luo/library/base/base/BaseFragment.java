package luo.library.base.base;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import luo.library.R;
import luo.library.base.dialog.SVProgressHUD;
import luo.library.base.utils.SpUtils;
import luo.library.base.widget.LoadingDialog;


public class BaseFragment extends Fragment {

    //状态栏颜色设置,0:默认白色;1:浅蓝色
    public int statusBarType = 0;



    /**
     * 开启浮动加载进度条
     */
    public void startProgressDialog() {
        LoadingDialog.showDialogForLoading(getActivity());
    }

    /**
     * 开启浮动加载进度条
     *
     * @param msg
     */
    public void startProgressDialog(String msg) {
        LoadingDialog.showDialogForLoading(getActivity(), msg, false);
    }

    /**
     * 停止浮动加载进度条
     */
    public void stopProgressDialog() {
        LoadingDialog.cancelDialogForLoading();
    }

    /**
     * 加载提示：旋转动画
     */
    public void showLoading(){
        SVProgressHUD.show(getActivity());
    }

    /**
     * 加载提示：旋转动画，可选择类型
     */
    public void showWithMaskType(){
        SVProgressHUD.showWithMaskType(getActivity(), SVProgressHUD.SVProgressHUDMaskType.None);
//        SVProgressHUD.showWithMaskType(getActivity(),SVProgressHUD.SVProgressHUDMaskType.Black);
//        SVProgressHUD.showWithMaskType(getActivity(), SVProgressHUD.SVProgressHUDMaskType.BlackCancel);
//        SVProgressHUD.showWithMaskType(getActivity(), SVProgressHUD.SVProgressHUDMaskType.Clear);
//        SVProgressHUD.showWithMaskType(getActivity(), SVProgressHUD.SVProgressHUDMaskType.ClearCancel);
//        SVProgressHUD.showWithMaskType(getActivity(), SVProgressHUD.SVProgressHUDMaskType.Gradient);
//        SVProgressHUD.showWithMaskType(getActivity(), SVProgressHUD.SVProgressHUDMaskType.GradientCancel);
    }

    /**
     * 加载提示：旋转动画+文字提示
     */
    public void showWithStatus(String promptStr){
        if(promptStr==null)
            SVProgressHUD.showWithStatus(getActivity(), "加载中...");
        else{
            SVProgressHUD.showWithStatus(getActivity(), promptStr);
        }
    }

    /**
     * 窗口提示：文字提示,可选择类型
     */
    public void showInfoWithStatus(String promptStr){
        SVProgressHUD.showInfoWithStatus(getActivity(), promptStr, SVProgressHUD.SVProgressHUDMaskType.None);
    }

    /**
     * 操作成功提示：文字提示
     */
    public void showSuccessWithStatus(String promptStr){
        if(promptStr==null)
            SVProgressHUD.showSuccessWithStatus(getActivity(), "操作成功！");
        else{
            SVProgressHUD.showSuccessWithStatus(getActivity(), promptStr);
        }
    }

    /**
     * 操作失败提示：文字提示
     */
    public void showErrorWithStatus(String promptStr){
        if(promptStr==null)
            SVProgressHUD.showErrorWithStatus(getActivity(), "操作失败～", SVProgressHUD.SVProgressHUDMaskType.GradientCancel);
        else{
            SVProgressHUD.showErrorWithStatus(getActivity(), promptStr, SVProgressHUD.SVProgressHUDMaskType.GradientCancel);
        }
    }



    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progress = progress + 5;
            if (SVProgressHUD.getProgressBar(getActivity()).getMax() != SVProgressHUD.getProgressBar(getActivity()).getProgress()) {
                SVProgressHUD.getProgressBar(getActivity()).setProgress(progress);
                SVProgressHUD.setText(getActivity(), "进度 "+progress+"%");

                mHandler.sendEmptyMessageDelayed(0,500);
            }
            else{
                SVProgressHUD.dismiss(getActivity());
                SVProgressHUD.getProgressBar(getActivity()).setProgress(0);
            }

        }
    };

    int progress;
    /**
     * 窗口提示：进度提示,可选择进度颜色类型
     */
    public void showWithProgress(View view){
        SVProgressHUD.showWithProgress(getActivity(), "进度 "+progress+"%", SVProgressHUD.SVProgressHUDMaskType.Black);
        progress = 0;
        mHandler.sendEmptyMessageDelayed(0,500);
    }

    /**
     * 关闭提示窗口
     */
    public void closeSVProgressHUD(){
        if(SVProgressHUD.isShowing(getActivity())){
            SVProgressHUD.dismiss(getActivity());
        }
    }

    /**
     * 设置标题栏信息
     */
    public void setTitleText(String string) {
        RelativeLayout relativeLayout = (RelativeLayout) getView().findViewById(R.id.lay_bg);
        TextView titleTv = (TextView) getView().findViewById(R.id.tv_base_titleText);
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
        LinearLayout backTv = (LinearLayout)getView(). findViewById(R.id.ly_base_back);
        backTv.setVisibility(View.GONE);
    }


    /**
     * 显示左边标题
     */
    public void showLeftText(String string, View.OnClickListener onClickListener){
        TextView editTv = (TextView) getView().findViewById(R.id.tvLeft);
        editTv.setVisibility(View.VISIBLE);
        editTv.setText(string);
        editTv.setOnClickListener(onClickListener);
    }

    /**
     * 设置标题栏左边按钮可见
     */
    public void setLeftTextVisible(int visible) {
        TextView editTv = (TextView) getView().findViewById(R.id.tvLeft);
        editTv.setVisibility(visible);
    }

    /**
     * 设置标题栏左边按钮标题
     */
    public void setLeftText(String string) {
        TextView editTv = (TextView) getView().findViewById(R.id.tvLeft);
        editTv.setText(string);
    }

    /**
     * 显示后退
     */
    public void showBack(View.OnClickListener clickListener){
        LinearLayout backTv = (LinearLayout) getView().findViewById(R.id.ly_base_back);
        backTv.setVisibility(View.VISIBLE);
        backTv.setOnClickListener(clickListener);
    }

    /**
     * 设置标题栏右边按钮文字
     */
    public void setRightButtonText(String string, View.OnClickListener onClickListener) {
        TextView editTv = (TextView) getView().findViewById(R.id.tv_base_edit);
        LinearLayout rightLayout = (LinearLayout) getView().findViewById(R.id.rightLayout);
        if(statusBarType==0){
            editTv.setTextColor(Color.rgb(40,41,50));
        }else{
            editTv.setTextColor(Color.rgb(255,255,255));
        }
        editTv.setVisibility(View.VISIBLE);
        editTv.setText(string);
        rightLayout.setOnClickListener(onClickListener);
    }


    /**
     * 设置标题栏右边按钮文字
     */
    public void setRightButtonText(String string) {
        TextView editTv = (TextView) getView().findViewById(R.id.tv_base_edit);
        editTv.setVisibility(View.VISIBLE);
        editTv.setText(string);
    }

    /**
     * 设置标题栏右边按钮可见
     */
    public void setRightButtonVisible(int visible) {
        TextView editTv = (TextView) getView().findViewById(R.id.tv_base_edit);
        editTv.setVisibility(visible);
    }

    /**
     * 设置右边图片
     */
    public void setRightImg(int bg, View.OnClickListener onClickListener) {
        if(getView()==null)
            return;
        ImageView imageView = (ImageView) getView().findViewById(R.id.iv_right);
        LinearLayout rightLayout = (LinearLayout) getView().findViewById(R.id.rightLayout);
        imageView.setImageResource(bg);
        rightLayout.setOnClickListener(onClickListener);
    }

    /**
     * 设置右边图片
     */
    public void setRightImg(int bg) {
        ImageView imageView = (ImageView) getView().findViewById(R.id.iv_right);
        imageView.setImageResource(bg);
    }

    /**
     * 获取右边图标控件
     * @return
     */
    public ImageView getRightImageView(){

        return (ImageView) getView().findViewById(R.id.iv_right);
    }

    /**
     * 标题栏背景设为透明
     */
    public void setBgNul() {
        RelativeLayout relativeLayout = (RelativeLayout)getView(). findViewById(R.id.lay_bg);
        relativeLayout.setBackgroundColor(Color.parseColor("#00000000"));
    }

    /**
     * 标题栏背景
     */
    public void setTitleViewBg(int bg) {
        RelativeLayout relativeLayout = (RelativeLayout) getView().findViewById(R.id.lay_bg);
        relativeLayout.setBackgroundResource(bg);
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
        Intent intent = new Intent(getActivity(), cls);
        startActivity(intent);
    }

    /**
     * 保存sp数据
     *
     * @param key
     * @param object
     */
    public void putSp(String key, Object object) {
        SpUtils.put(getActivity(), key, object);
    }

    /**
     * 清除Sp数据
     */
    public void clearSp() {
        SpUtils.clear(getActivity());
    }

    /**
     * 获取sp数据
     *
     * @param key
     * @param object
     * @return
     */
    public Object getSp(String key, Object object) {
        return SpUtils.get(getActivity(), key, object);
    }

}
