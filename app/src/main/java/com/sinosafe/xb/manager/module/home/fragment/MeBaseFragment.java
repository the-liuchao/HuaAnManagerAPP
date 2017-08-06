package com.sinosafe.xb.manager.module.home.fragment;

import android.content.Intent;
import android.graphics.Bitmap;

import com.sinosafe.xb.manager.BaseMainActivity;
import com.sinosafe.xb.manager.R;
import com.sinosafe.xb.manager.api.rxjava.RxHttpBaseResultFunc;
import com.sinosafe.xb.manager.api.rxjava.RxHttpResultFunc;
import com.sinosafe.xb.manager.api.rxjava.RxSchedulersHelper;
import com.sinosafe.xb.manager.api.rxjava.RxSubscriber;
import com.sinosafe.xb.manager.bean.BaseEntity;
import com.sinosafe.xb.manager.bean.VersionBean;
import com.sinosafe.xb.manager.model.ClientModel;
import com.sinosafe.xb.manager.utils.MyUtils;
import com.sinosafe.xb.manager.utils.T;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import luo.library.base.base.BaseFragment;
import luo.library.base.utils.DensityUtil;
import luo.library.base.utils.MyLog;
import luo.library.base.utils.UpdateService;
import luo.library.base.widget.dialog.ActionShareDialog;
import luo.library.base.widget.dialog.DialogMessage;

/**
 * 类名称：   com.cnmobi.huaan.manager.fragment
 * 内容摘要： //我的 片。
 * 修改备注：
 * 创建时间： 2017/6/3 0003
 * 公司：     深圳市华移科技股份有限公司
 * 作者：     Mr 张
 */
public class MeBaseFragment extends BaseFragment {

    /** 分享图片 **/
    UMImage image = null;
    /** 分享标题 **/
    String title = "华安信保分享";
    /** 分享内容 **/
    String contentStr = "我正在使用华安信保，推荐您来一起使用!!!";
    /** 分享连接 **/
    public String shareUrl = "https://www.baidu.com/";//UURL.URL_SHARE_GROWTHRECORD+"?record_id=";
    /**分享dialog**/
    public ActionShareDialog shareDialog;
    //分享二维码Bitmap
    private Bitmap qrBitmap;
    private String url;

    /**
     * 分享QQ好友
     */
    public void qqClick() {
        makeShareQr();
        image = new UMImage(getActivity(), qrBitmap);
        new ShareAction(getActivity()).setPlatform(SHARE_MEDIA.QQ)
                .setCallback(umShareListener).withText(contentStr)
                .withTitle(title).withMedia(image)
                .withTargetUrl(shareUrl)
                .withExtra(new UMImage(getActivity(), R.mipmap.ic_launcher)).share();
    }

    private void makeShareQr(){
        if(qrBitmap==null) {
            int qrWidth = DensityUtil.widthPixels(getActivity())/5*3;
            int qrHight = DensityUtil.widthPixels(getActivity())/5*3;
            qrBitmap = MyUtils.createQRImage(shareUrl,qrWidth,qrHight);
        }
    }
    /**
     * 分享微信好友
     */
    public void wechatClick() {
        makeShareQr();
        image = new UMImage(getActivity(), qrBitmap);
        new ShareAction(getActivity()).setPlatform(SHARE_MEDIA.WEIXIN)
                .setCallback(umShareListener).withText(contentStr)
                .withTitle(title).withMedia(image)
                .withTargetUrl(shareUrl)
                .withExtra(new UMImage(getActivity(), R.mipmap.ic_launcher)).share();
    }

    /**
     * 分享微信朋友圈
     */
    public void wxcircleClick() {
        new ShareAction(getActivity()).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                .setCallback(umShareListener).withText(contentStr)
                .withTitle(title).withMedia(image)
                .withTargetUrl(shareUrl)
                .withExtra(new UMImage(getActivity(), R.mipmap.ic_launcher)).share();
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            T.showShortBottom("分享成功啦");
            MyLog.e("============分享成功啦=================");
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            T.showShortBottom("分享失败啦");
            MyLog.e("============分享失败啦=================");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            T.showShortBottom("分享取消了");
            MyLog.e("============分享取消了=================");
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(getActivity()).onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 版本检查
     */
    protected void checkVersion(){

        startProgressDialog("检查中...");
        ClientModel.checkVersion("1")
            .timeout(20, TimeUnit.SECONDS)
            .compose(RxSchedulersHelper.<BaseEntity<VersionBean>>io_main())
            .map(new RxHttpResultFunc<VersionBean>())
            .subscribe(new RxSubscriber<VersionBean>() {
                @Override
                public void _onNext(VersionBean versionBean) {
                    stopProgressDialog();
                    showVersionInfo(versionBean);
                }
                @Override
                public void _onError(String msg) {
                    stopProgressDialog();
                    showErrorWithStatus(msg);

                }});
    }

    /**
     * 显示版本信息
     * @param versionBean
     */
    protected void showVersionInfo(VersionBean versionBean){
        if(versionBean.getVersion_code()<=MyUtils.getVersionCode(getActivity())) {
            T.showShortBottom("当前已是最新版本.");
            return;
        }
        url = versionBean.getDownload_url();
        new DialogMessage(getActivity()).setTitle("版本检测")
                .setMess(versionBean.getUpdate_description())
                .setConfirmTips("立即更新")
                .setConfirmListener(new DialogMessage.OnConfirmListener() {
                    @Override
                    public void onConfirm() {
                        Intent intent = new Intent(getActivity(),UpdateService.class);
                        intent.putExtra("url",url);
                        getActivity().startService(intent);
                    }
                }).show();
    }


    /**
     * 分享
     */
    protected void shareLogin(){

        startProgressDialog("");
        Map<String,String> map = new HashMap<>();
        map.put("token", BaseMainActivity.loginUserBean.getToken());
        map.put("type","1");
        map.put("user_id", BaseMainActivity.loginUserBean.getActorno());
        ClientModel.shareLogin(map)
            .timeout(20, TimeUnit.SECONDS)
            .compose(RxSchedulersHelper.<BaseEntity>io_main())
            .map(new RxHttpBaseResultFunc())
            .subscribe(new RxSubscriber<BaseEntity>() {
                @Override
                public void _onNext(BaseEntity baseEntity) {
                    MyLog.e("======================"+baseEntity.toString());
                    stopProgressDialog();
                    shareDialog.show();
                }
                @Override
                public void _onError(String msg) {
                    stopProgressDialog();
                    MyLog.e("======================"+msg);
                    showErrorWithStatus(msg);
                }});
    }
}
