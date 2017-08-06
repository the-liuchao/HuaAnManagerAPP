package com.sinosafe.xb.manager.api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import luo.library.base.utils.MyLog;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 封装公共参数（Key和密码）
 * 对请求参数加密
 * <p>
 */
public class CommonInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();

        String currentTimeMillis = String.valueOf(System.currentTimeMillis());
        if(oldRequest.method().equalsIgnoreCase("GET")){
            MyLog.e("请求URL==========："+oldRequest.url().toString());
            HashMap<String, String> stringStringHashMap = new HashMap<>();
            for(int i = 0;i<oldRequest.url().querySize();i++){
                MyLog.e("参数名："+oldRequest.url().queryParameterName(i)+"       参数值："+oldRequest.url().queryParameterValue(i));
                stringStringHashMap.put(oldRequest.url().queryParameterName(i), oldRequest.url().queryParameterValue(i));
            }
        }
        else{
            RequestBody requestBody = oldRequest.body();
            if (requestBody instanceof FormBody) {
                FormBody body = (FormBody) requestBody;
                for (int i = 0; i < body.size(); i++) {
                    MyLog.e("参数名："+body.encodedName(i)+"       参数值："+ body.encodedValue(i));
                }
            }
        }


        /*stringStringHashMap.put("timestamp",currentTimeMillis);
        String signature = Sign.getSignature(stringStringHashMap, SECRET);
        // 添加新的参数
        HttpUrl.Builder authorizedUrlBuilder = oldRequest.url()
                .newBuilder()
                .scheme(oldRequest.url().scheme())
                .host(oldRequest.url().host())
                .addQueryParameter("timestamp", currentTimeMillis)
                .addQueryParameter("sign",signature);


        // 新的请求
        Request newRequest = oldRequest.newBuilder()
                .method(oldRequest.method(), oldRequest.body())
                .url(authorizedUrlBuilder.build())
                .build();*/

        return chain.proceed(oldRequest);
    }

    private String toSign(String key) {
        // 使用MD5对待签名串求签
        byte[] bytes = null;
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            bytes = md5.digest(key.toString().getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
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