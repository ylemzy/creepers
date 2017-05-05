package application.http.utils;



import application.http.interceptors.GzipResponseInterceptor;
import application.http.interceptors.LoggingInterceptor;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

public class ClientFactory {

    private static Logger logger = LoggerFactory.getLogger("HTTP");

    private static SSLSocketFactory SSLSOCKETFACTORY_IGNORE_HOLDER = getSSLSocketFactory();

    private static SSLSocketFactory getSSLSocketFactory() {
        if (SSLSOCKETFACTORY_IGNORE_HOLDER == null) {
            try {
                TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        X509Certificate[] x509Certificates = new X509Certificate[0];
                        return x509Certificates;
                    }

                    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    }
                }};


                SSLContext sc = SSLContext.getInstance("TLS");
                sc.init(null, trustAllCerts, new java.security.SecureRandom());
                SSLSOCKETFACTORY_IGNORE_HOLDER = sc.getSocketFactory();
            } catch (Exception e) {
                //do nothing
            }


        }

        return SSLSOCKETFACTORY_IGNORE_HOLDER;

    }

    static OkHttpClient client;

    static OkHttpClient proxyClient;


   /* public static OkHttpClient getHttpClient() {
        return getHttpClient(null);
    }*/


    public static OkHttpClient getHttpClient() {

        if (client == null) {
            client = new OkHttpClient.Builder().sslSocketFactory(getSSLSocketFactory())
                    .retryOnConnectionFailure(true)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .addInterceptor(new GzipResponseInterceptor())
                    .addInterceptor(new LoggingInterceptor())
                    .build();
        }
        return client;

        /*Random proxy*/
/*        if (proxyClient == null) {
            proxyClient = new OkHttpClient.Builder()
            *//*        .proxy(authorizationProxy.getProxy())
                    .proxyAuthenticator(JsoupProxy.buildAuthenticator(authorizationProxy))*//*
                    .sslSocketFactory(getSSLSocketFactory())
                    .retryOnConnectionFailure(true)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .addInterceptor(new GzipResponseInterceptor())
                    .addInterceptor(new LoggingInterceptor())
                    .build();
        }
        return proxyClient.newBuilder()
   *//*             .proxy(authorizationProxy.getProxy())
                .proxyAuthenticator(JsoupProxy.buildAuthenticator(authorizationProxy))*//*
                .sslSocketFactory(getSSLSocketFactory())
                .retryOnConnectionFailure(true)
                .readTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(new GzipResponseInterceptor())
                .addInterceptor(new LoggingInterceptor())
                .build();*/
    }
}
