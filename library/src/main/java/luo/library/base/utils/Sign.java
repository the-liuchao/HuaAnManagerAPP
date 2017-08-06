package luo.library.base.utils;

import android.annotation.SuppressLint;

import org.xutils.common.util.KeyValue;
import org.xutils.http.RequestParams;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

/**
 * 签名算法
 * <p/>
 * 假设参与参数签名计算的请求参数分别是“k1”、“k2”、“k3”，它们的值分别是“v1”、“v2”、“v3”，则参数签名计算方法如下：
 * <p/>
 * 将请求参数格式化为“key=value”格式，即“k1=v1”、“k2=v2”、“k3=v3”；
 * 将格式化好的参数键值对以字典序升序排列后，拼接在一起，即“k1=v1k2=v2k3=v3”；
 * 在拼接好的字符串末尾追加上与服务器约定的应用secret参数值； 上述字符串的MD5值即为签名的值。
 *
 * @author cnmobi_db
 */
public class Sign {


    /**
     * 方法名：  getSign	<br>
     * 方法描述：参数签名sign
     * @return
     */
    public static void getSignByOne(RequestParams params,String secret) {

        String sign = "";
        String timestamp = System.currentTimeMillis() + "";
        params.addQueryStringParameter("timestamp", timestamp);

        sign = Sign.getSignature(params.getQueryStringParams(), secret);

        params.addQueryStringParameter("sign", sign);
    }

    /**
     * 签名生成算法
     * @return 签名
     * @throws IOException
     */
    public static String getSignature(List<KeyValue> list, String secret) {
        try {
            HashMap<String, String> params = new HashMap<String, String>();
            for (KeyValue keyValue : list) {
                params.put(keyValue.key, keyValue.getValueStr());
            }

            if (params.size() == 0) {
                return null;
            } else
                return getSignature(params, secret);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 签名生成算法
     *
     * @return 签名
     * @throws IOException
     */
    @SuppressLint("NewApi")
    public static String getSignature(HashMap<String, String> params, String secret) throws IOException {
        // 先将参数以其参数名的字典序升序进行排序
        Map<String, String> sortedParams = new TreeMap<String, String>(params);
        Set<Entry<String, String>> entrys = sortedParams.entrySet();

        // 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
        StringBuilder basestring = new StringBuilder();
        for (Entry<String, String> param : entrys) {
            basestring.append(param.getKey()).append("=").append(param.getValue());
        }
        basestring.append(secret);
//        Log.e("sign","getSignature        "+basestring.toString());
        // 使用MD5对待签名串求签
        byte[] bytes = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            bytes = md5.digest(basestring.toString().getBytes("UTF-8"));
        } catch (GeneralSecurityException ex) {
            throw new IOException(ex);
        }

        // 将MD5输出的二进制结果转换为小写的十六进制
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex);
        }
        return sign.toString();
    }

}
