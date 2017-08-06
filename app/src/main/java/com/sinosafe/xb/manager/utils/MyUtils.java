package com.sinosafe.xb.manager.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.amap.api.maps2d.AMapUtils;
import com.amap.api.maps2d.model.LatLng;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.lzy.imagepicker.util.ProviderUtil;
import com.sinosafe.xb.manager.APP;
import com.sinosafe.xb.manager.bean.NameTypeBean;
import com.sinosafe.xb.manager.module.home.weidai.bean.ImagesInfoBean;
import com.sinosafe.xb.manager.widget.recycleview.LoadingFooter;
import com.sinosafe.xb.manager.widget.recycleview.RecyclerViewStateUtils;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Decoder.BASE64Encoder;
import luo.library.base.bean.WheelViewBean;
import luo.library.base.utils.AMapLocUtils;
import luo.library.base.utils.MyLog;

import static com.sinosafe.xb.manager.widget.DialogPowerOfAttorney.SIGNATURE_NAME;

/**
 * 类名称：   com.cnmobi.huaan.manager.utils
 * 内容摘要： //我的工具类。
 * 修改备注：
 * 创建时间： 2017/6/4 0004
 * 公司：     深圳市华移科技股份有限公司
 * 作者：     Mr 张
 */
public class MyUtils {


    /**
     * 中国公民身份证号码最大长度。
     */
    private static final int CHINA_ID_MAX_LENGTH = 18;

    /**
     * 身份证号码前17位每位对应加权因子。
     */
    private static final int[] POWER = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};


    // 检测手机上的一些硬件信息
    @NonNull
    public static Boolean CheckEmulatorBuild(Context context) {
        String BOARD = android.os.Build.BOARD;
        String BOOTLOADER = android.os.Build.BOOTLOADER;
        String BRAND = android.os.Build.BRAND;
        String DEVICE = android.os.Build.DEVICE;
        String HARDWARE = android.os.Build.HARDWARE;
        String MODEL = android.os.Build.MODEL;
        String PRODUCT = android.os.Build.PRODUCT;
        if (BRAND.equals("generic") || DEVICE.equals("generic") || MODEL.equals("sdk") || PRODUCT.equals("sdk")
                || HARDWARE.equals("goldfish")||MODEL.equals("google_sdk")) {
            Log.v("Result:", "Find Emulator by EmulatorBuild!");
            return true;
        }
        Log.v("Result:", "Not Find Emulator by EmulatorBuild!");
        return false;
    }


    /**
     * 方法名：  keepTwoDecimal	<br>
     * 方法描述：TODO(保留两位小数)<br>
     * 修改备注：<br>
     * 创建时间： 2016年8月3日下午4:29:01<br>
     *
     * @param num
     * @return
     */
    public static String keepTwoDecimal(double num) {

        DecimalFormat df = new DecimalFormat("######0.00");
        return df.format(num);
    }

    public static String keepNoeDecimal(double num) {

        DecimalFormat df = new DecimalFormat("######0.0");
        return df.format(num);
    }

    /**
     * 设置html
     *
     * @param tv
     * @param text
     */
    public static void setHtml(TextView tv, String text) {
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(text);
        }
        tv.setText(result);
    }

    public static Uri getOutputMediaFileUri(Context context) {
        Uri uri = null;

        File mediaStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "MyCameraApp");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp
                + ".jpg");

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            uri = Uri.fromFile(mediaFile);
        } else {
            /**
             * 7.0 调用系统相机拍照不再允许使用Uri方式，应该替换为FileProvider
             * 并且这样可以解决MIUI系统上拍照返回size为0的情况
             */
            uri = FileProvider.getUriForFile(context, ProviderUtil.getFileProviderName(context), mediaFile);
        }

        return uri;
    }

    /**
     * 剪切
     *
     * @param activity
     * @param uri
     * @param outputUri
     * @param outputX
     * @param outputY
     * @param requestCode
     */
    public static void cropImageUri(Activity activity, Uri uri, Uri outputUri, int outputX, int outputY, int requestCode) {
        try {
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(uri, "image/*");
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("outputX", outputX);
            intent.putExtra("outputY", outputY);
            intent.putExtra("scale", true);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
            intent.putExtra("return-data", false);
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            intent.putExtra("noFaceDetection", true);
            activity.startActivityForResult(intent, requestCode);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 验证邮箱地址格式合法性。规则：5-32个字符，只能以英文字母或者数字开头，支持英文字母、数字、下划线、@、小数点以及中划线。
     *
     * @param email 邮箱地址
     * @return true:合法 false:不合法
     */
    public static boolean isEmail(String email) {
        if (TextUtils.isEmpty(email) || email.length() > 32) {
            return false;
        }
        // 邮箱地址正则表达式
        String regex = "(?!_)\\w+([-.]\\w+)*@(\\w+)(\\.\\w+){1,2}";
        return isLegalString(regex, email);
    }

    /**
     * 校验目标字符串是否符合指定的正则表达式规则。
     *
     * @param regex 正则表达式
     * @param value 目标字符串
     * @return 校验结果
     */
    public static boolean isLegalString(String regex, String value) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }


    /**
     * 验证真实姓名合法性。规则：2-8个汉字。
     *
     * @param realName 真实姓名
     * @return true:合法 false:不合法
     */
    public static boolean isRealName(String realName) {
        if (TextUtils.isEmpty(realName)) {
            return false;
        }
        // 真实姓名正则表达式
        String regex = "^[\u4e00-\u9fa5]{2,8}$";
        return isLegalString(regex, realName);
    }


    /**
     * 验证手机号码合法性。
     *
     * @param phone 手机号码
     * @return true:合法 false:不合法
     */
    public static boolean isPhone(String phone) {
        if (TextUtils.isEmpty(phone)) {
            return false;
        }
        // 手机号码正则表达式
        String regex = "^[1][34578][0-9]\\d{8}$";
        return isLegalString(regex, phone);
    }

    /**
     * @return void
     * @throws
     * @方法功能说明: 生成二维码图片, 实际使用时要初始化sweepIV, 不然会报空指针错误
     * @参数: @param url 要转换的地址或字符串,可以是中文
     */
    // 要转换的地址或字符串,可以是中文
    public static Bitmap createQRImage(String url, int QR_WIDTH, int QR_HEIGHT) {
        try {
            // 判断URL合法性
            if (url == null || "".equals(url) || url.length() < 1) {
                return null;
            }
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            // 图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(url,
                    BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
            int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
            // 下面这里按照二维码的算法，逐个生成二维码的图片，
            // 两个for循环是图片横列扫描的结果
            for (int y = 0; y < QR_HEIGHT; y++) {
                for (int x = 0; x < QR_WIDTH; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * QR_WIDTH + x] = 0xff000000;
                    } else {
                        pixels[y * QR_WIDTH + x] = 0xffffffff;
                    }
                }
            }
            // 生成二维码图片的格式，使用ARGB_8888
            Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT,
                    Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);

            return bitmap;

        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 生成UUID
     *
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }


    /**
     * 验证是否是纯数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }


    /**
     * 验证是否是座机
     *
     * @param str
     * @return
     */
    public static boolean isLandLine(String str) {

        MyLog.e("str=====" + str);
        String regex = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}|[0]{1}[0-9]{2,3}-[0-9]{7,8}$";

        return isLegalString(regex, str);
    }

    /**
     * RecyclerView 自动加载
     *
     * @param list
     * @param mRecyclerView
     */
    public static void TheEndOrNomal(List list, RecyclerView mRecyclerView) {
        MyLog.e("====================================" + list.size());

        if (list.size() < 10)
            RecyclerViewStateUtils.setFooterViewState(mRecyclerView, LoadingFooter.State.TheEnd);
        else {
            RecyclerViewStateUtils.setFooterViewState(mRecyclerView, LoadingFooter.State.Normal);
        }
    }


    /**
     * 验证身份证号码是否合法。如果是18位的身份证，则校验18位的身份证。15的身份证不校验，也无法校验。
     *
     * @param idCard 身份证号码
     * @return true:合法 false:不合法
     */
    public static boolean isIdCard(String idCard) {
        if (TextUtils.isEmpty(idCard)) {
            return false;
        }
        // 15位或者18位身份证正则表达式
        String regex = "\\d{15}|\\d{17}[x,X,0-9]";
        if (!isLegalString(regex, idCard)) {
            return false;
        }

        // 如果是18位的身份证，则校验18位的身份证，15的身份证暂不校验。
        if (idCard.length() == CHINA_ID_MAX_LENGTH) {
            MyLog.e("如果是18位的身份证");
            // 前17位
            String code17 = idCard.substring(0, 17);
            // 第18位
            String code18 = idCard.substring(17, CHINA_ID_MAX_LENGTH);
            if (isNumber(code17)) {
                MyLog.e("如果是17位的都是数字");
                char[] charArr = code17.toCharArray();
                if (charArr != null) {
                    int[] intArr = convertCharToInt(charArr);
                    int iSum17 = getPowerSum(intArr);
                    // 获取校验位
                    String value = getCheckCode(iSum17);
                    if (code18.equalsIgnoreCase(value)) {
                        MyLog.e("校验位验证通过");
                        return true;
                    }
                }
            }
        }
        return false;
    }


    /**
     * 验证目标字符串是否为数字。
     *
     * @param value 目标字符串
     * @return true:数字 false:非数字
     */
    public static boolean isNumber(String value) {
        if (TextUtils.isEmpty(value)) {
            return false;
        }
        // 数字正则表达式
        String regex = "^[0-9]*$";
        return isLegalString(regex, value);
    }

    /**
     * 将字符数组转换成数字数组。
     *
     * @param charArr 字符数组
     * @return 数字数组
     */
    private static int[] convertCharToInt(char[] charArr) {
        int len = charArr.length;
        int[] intArr = new int[len];
        try {
            for (int i = 0; i < len; i++) {
                intArr[i] = Integer.parseInt(String.valueOf(charArr[i]));
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return intArr;
    }

    /**
     * 获取身份证的每位和对应位的加权因子相乘之后的和值。
     *
     * @param intArr 数字数组
     * @return 身份证号码权值
     */
    private static int getPowerSum(int[] intArr) {
        int iSum = 0;
        if (POWER.length == intArr.length) {
            for (int i = 0; i < intArr.length; i++) {
                iSum = iSum + intArr[i] * POWER[i];
            }
        }
        return iSum;
    }

    /**
     * 通过身份证号码前17位权值与11取模获得的余数判断，获取身份证最后一位校验码。
     *
     * @param iSum 身份证号码前17位权值
     * @return 身份证号码最后一位校验位
     */
    private static String getCheckCode(int iSum) {
        String sCode = "";
        switch (iSum % 11) {
            case 10:
                sCode = "2";
                break;
            case 9:
                sCode = "3";
                break;
            case 8:
                sCode = "4";
                break;
            case 7:
                sCode = "5";
                break;
            case 6:
                sCode = "6";
                break;
            case 5:
                sCode = "7";
                break;
            case 4:
                sCode = "8";
                break;
            case 3:
                sCode = "9";
                break;
            case 2:
                sCode = "X";
                break;
            case 1:
                sCode = "0";
                break;
            case 0:
                sCode = "1";
                break;
        }
        return sCode;
    }


    /**
     * 获取两点之间的距离
     *
     * @param startPoint
     * @param endPoint
     * @return
     */
    public static float getAMapLineDistance(LatLng startPoint, LatLng endPoint) {

        return AMapUtils.calculateLineDistance(startPoint, endPoint);
    }

    /**
     * 设置距离
     *
     * @param textView
     * @param latLngStr
     */
    public static void setDistance(TextView textView, String latLngStr) {

        if (latLngStr != null && !"".equals(latLngStr) && APP.lat != 0 && APP.lng != 0) {
            String latLngArr[] = latLngStr.split(",");
            LatLng startPoint = new LatLng(Double.valueOf(latLngArr[0]), Double.valueOf(latLngArr[1]));
            LatLng endPoint = new LatLng(APP.lat, APP.lng);

            float distance = getAMapLineDistance(startPoint, endPoint);
            if (distance > 1000) {
                textView.setText("距您" + keepTwoDecimal(distance / 1000) + "km");
            } else {
                textView.setText("距您" + keepTwoDecimal(distance) + "m");
            }
        } else {
            textView.setText("距离未知");
        }
    }

    public static void regeocodeSearched(Context mContext, final TextView textView, String latLngStr) {
        textView.setText("正在获取位置信息...");
        if (latLngStr != null && !"".equals(latLngStr)) {

            String latLngArr[] = latLngStr.split(",");
            LatLng startPoint = new LatLng(Double.valueOf(latLngArr[0]), Double.valueOf(latLngArr[1]));
            AMapLocUtils.regeocodeSearched(mContext, startPoint, new AMapLocUtils.SearchListener() {
                @Override
                public void regeocodeResult(String address) {
                    if (textView != null)
                        textView.setText(address);
                }
            });
        } else {
            textView.setText("获取位置失败");
        }
    }


    /**
     * 获取签名的图片串
     *
     * @param context
     * @return
     */
    public static String getPersonSealData(Context context) {
        BASE64Encoder encoder = new BASE64Encoder();
        FileInputStream fis = null;
        try {
            fis = context.openFileInput(SIGNATURE_NAME);
            byte[] bytes = new byte[fis.available()];
            fis.read(bytes);
            fis.close();
            return encoder.encode(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取还款方式
     *
     * @param repaymentModel
     * @return
     */
    public static String getRepaymentModel(String repaymentModel) {
        String repaymentModelName = "";
        for (int i = 0; i < NameTypeBean.getRepaymentModel().size(); i++) {

            if (NameTypeBean.getRepaymentModel().get(i).getKey().equals(repaymentModel)) {
                repaymentModelName = NameTypeBean.getRepaymentModel().get(i).getName();
                break;
            }
        }
        return repaymentModelName;
    }


    /**
     * 获取担保方式
     *
     * @param guaranteeModel
     * @return
     */
    public static String getGuaranteeModel(String guaranteeModel) {
        String guaranteeModelName = "";
        for (int i = 0; i < NameTypeBean.getGuaranteeModel().size(); i++) {

            if (NameTypeBean.getGuaranteeModel().get(i).getKey().equals(guaranteeModel)) {
                guaranteeModelName = NameTypeBean.getGuaranteeModel().get(i).getName();
                break;
            }
        }
        return guaranteeModelName;
    }


    /**
     * 通过身份证判断性别
     *
     * @param cerdCode
     * @return
     */
    public static String judgeSexByCerdCode(String cerdCode) {

        if (cerdCode.length() == 15) {
            int code = Integer.valueOf(cerdCode.substring(14, 15));
            if (code % 2 == 0) {
                return "女";
            } else {
                return "男";
            }
        } else if (cerdCode.length() == 18) {
            int code = Integer.valueOf(cerdCode.substring(16, 17));
            if (code % 2 == 0) {
                return "女";
            } else {
                return "男";
            }
        }

        return "男";
    }

    /**
     * 获取版本号
     * @return 当前应用的版本号
     */
    public static  int getVersionCode(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }


    /**
     * 获取版本号
     * @return 当前应用的版本号
     */
    public static  String getVersionName(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    /**
     * 移除无效的图片数据
     * @param imagesInfoBeens
     */
    public static void removeInvalidData(List<ImagesInfoBean> imagesInfoBeens){
        Iterator<ImagesInfoBean> it = imagesInfoBeens.iterator();
        while (it.hasNext()) {
            ImagesInfoBean imagesInfoBean = it.next();
            if (imagesInfoBean.getType() == -1)
                it.remove();
        }
    }

    /**
     *
     * @param marriageStatus
     * @return
     */
    public static String getMarriageStatus(String marriage,List<WheelViewBean> marriageStatus){

        for(int i=0;i<marriageStatus.size();i++){
            if(marriageStatus.get(i).getType().equals(marriage))
                return marriageStatus.get(i).getName();
        }
        return "";
    }

}
