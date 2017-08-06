package com.sinosafe.xb.manager.utils;

import android.os.Environment;

import com.sinosafe.xb.manager.api.APIManager;

import java.io.File;

/**
 * 类名称：   com.cnmobi.huaan.manager.utils
 * 内容摘要： //APP所有常量。
 * 修改备注：
 * 创建时间： 2017/6/5 0005
 * 公司：     深圳市华移科技股份有限公司
 * 作者：     Mr 张
 */
public class Constant {

    /**服务器约定的应用secret参数值**/
    public static final String  secret = "cnmobi_xiaomingwg_user";

    /**以后所有文件保存的根目录**/
    public final static String FILT_ROOT = Environment.getExternalStorageDirectory().getAbsolutePath()+"/sinosafe/";

    /**图片**/
    public final static String FILT_IMAGE = FILT_ROOT+"/image/";

    /**保存的图片**/
    public final static String FILT_IMAGE_SAVE = FILT_ROOT+"/saveImage/";

    /**安装包**/
    public final static String FILT_APK = FILT_ROOT+"/apk/";



    /**每一页展示多少条数据*/
    public static final int REQUEST_COUNT = 10;

    /**跳转到待办事项的广播*/
    public final static String TO_DAI_BAN_SHI_XIANG = "TO_DAI_BAN_SHI_XIANG";

    /**token失效广播*/
    public final static String TOKEN_INVALID_422 = "TOKEN_INVALID_422";

    /**查看贷款申请进度广播*/
    public final static String LOAD_APPLY_PROGRESS = "LOAD_APPLY_PROGRESS";

    /**跳转到申请中广播*/
    public final static String TO_LOAD_APPLYING = "TO_LOAD_APPLYING";

    /**刷新首页数据广播*/
    public final static String REFRESH_HOME_PAGE = "REFRESH_HOME_PAGE";

    /**刷新申请中数据广播*/
    public final static String APPLY_AFTER_AUTHORIZATION = "APPLY_AFTER_AUTHORIZATION";


    /**申请中---授权完成后继续申请广播*/
    public final static String REFRESH_APPLYING_DATA = "REFRESH_APPLYING_DATA";

    //影像系统 系统编号（信保系统）
    public static final String SYSCODE = "XBSYS";


    /**加载申请中的业务的广播*/
    public final static String LOAD_YEWU_DATA_OF_APPLY = "LOAD_YEWU_DATA_OF_APPLY";

    /**刷新业务中：审批中/待放款数据的广播*/
    public final static String REFRESH_YEWU_DATA = "REFRESH_YEWU_DATA";

    /**刷新业务中：还款中数据的广播*/
    public final static String REFRESH_YEWU_REPAYMENT_DATA = "REFRESH_YEWU_REPAYMENT_DATA";

    //PDF文件名称
    public final static String PDF_FILE_NAME = "pdf_file_name.pdf";

    /**
     * 获取影像系统图片地址
     *
     * @param fileId
     * @return
     */
    public static String getImagePath(String fileId,String actorno) {
        return APIManager.FILE_UPLOAD_URL + "store_down_new/download?SystemCode=" + Constant.SYSCODE + "&BusinessCode=" + actorno + "&FileId=" + fileId + "&Type=1";
    }


    /**
     * 方法名：  getOutputMediaFile	<br>
     * 方法描述：TODO(生产一个图片文件名称)<br>
     * @return
     */
    public static File getImageFile(String photoDes) {
        File imageFile = new File(FILT_IMAGE);
        if (!imageFile.exists()) {
            if (!imageFile.mkdirs()) {
                return null;
            }
        }
        String timeStamp = System.currentTimeMillis()+"";
        imageFile  = new File(FILT_IMAGE + photoDes+"IMG_" + timeStamp + ".jpg");
        return imageFile;
    }

    /**
     * 方法名：  getApkFilePath	<br>
     * 方法描述：TODO(获取下载安装包的文件路径)<br>
     * @return
     */
    public static String getApkFilePath() {
        return FILT_APK;
    }
}
