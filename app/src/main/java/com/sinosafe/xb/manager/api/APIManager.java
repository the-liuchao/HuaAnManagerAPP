package com.sinosafe.xb.manager.api;

import android.content.Context;

import com.sinosafe.xb.manager.APP;
import com.sinosafe.xb.manager.R;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import luo.library.base.utils.MyLog;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 类名称：   APIManager
 * 内容摘要： //网络请求管理类。
 * 修改备注：
 * 创建时间： 2017/5/11
 * 公司：     深圳市华移科技股份有限公司
 * 作者：     Mr 张
 */
public class APIManager {

    private static final int DEFAULT_TIMEOUT = 30*2;

    //信保base url
    //public static final String SINOSAFE_URL = "http://10.11.245.206:8080/haxb_api/api/";
    //public static final String SINOSAFE_URL = "http://10.1.109.60:8082/haxb_api/api/";
    //测试环境
    public static final String SINOSAFE_URL = "https://proxytest.sinosafe.com.cn/weixinxb1haxb_api/api/";
    //生产环境
    //public static final String SINOSAFE_URL = "http://cgi.sinosafe.com.cn/haxb_api/api/";

    //https://proxytest.sinosafe.com.cn/weixinxb1haxb/

    //影像系统地址Interface_Cluster/IImgXmlUpLoadSyn
    //public static final String FILE_UPLOAD_URL = "http://10.1.109.61:8080/";
    public static final String FILE_UPLOAD_URL = "http://proxytest.sinosafe.com.cn/xbapp/";


    //查询网络的Cache-Control设置
    //(假如请求了服务器并在a时刻返回响应结果，则在max-age规定的秒数内，浏览器将不会发送对应的请求到服务器，数据由缓存直接返回)
    static final String CACHE_CONTROL_NETWORK = "Cache-Control: public, max-age=3600";


    //构建公用的Retrofit对象
    public static final Retrofit mRetrofit = new Retrofit .Builder()
            .baseUrl(SINOSAFE_URL)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(genericClient())
            .build();

    private static OkHttpClient genericClient() {

        //请求参数加密
        CommonInterceptor commonInterceptor =new CommonInterceptor();

        //https安全证书
        int[] certificates = {R.raw.all_sinosafe_com_cn};
        SSLSocketFactory sslSocketFactory =getSSLSocketFactory(APP.getApplication(),certificates);
        String hosts[]={"proxytest.sinosafe.com.cn"};

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        final OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .build();
                        return chain.proceed(request);
                    }
                })
                .addInterceptor(logging)
                .addInterceptor(commonInterceptor)
                //.sslSocketFactory(sslSocketFactory)
                //.hostnameVerifier(getHostnameVerifier(hosts))

                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();

        return httpClient;
    }


    //通过证书构建ssl
    protected static SSLSocketFactory getSSLSocketFactory(Context context, int[] certificates) {

        if (context == null) {
            throw new NullPointerException("context == null");
        }
        CertificateFactory certificateFactory;
        try {
            certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);

            for (int i = 0; i < certificates.length; i++) {
                InputStream certificate = context.getResources().openRawResource(certificates[i]);
                keyStore.setCertificateEntry(String.valueOf(i), certificateFactory.generateCertificate(certificate));

                if (certificate != null) {
                    certificate.close();
                }
            }
            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
            return sslContext.getSocketFactory();
        }
        catch (KeyManagementException | CertificateException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //拿到服务器返回的证书数据，同自己请求的域名、证书签证的域名、证书接收的url的域名做对比，
    protected static HostnameVerifier getHostnameVerifier(final String[] hostUrls) {
        HostnameVerifier TRUSTED_VERIFIER = new HostnameVerifier() {

            public boolean verify(String hostname, SSLSession session) {
                boolean ret = false;
                //服务器返回的主机名
                String peerHost = session.getPeerHost();
                MyLog.e("服务器返回的主机名====="+peerHost);
                for (String host : hostUrls) {
                    if (host.equalsIgnoreCase(hostname)) {
                        ret = true;
                    }
                }
                return ret;
            }
        };
        return TRUSTED_VERIFIER;
    }
}
