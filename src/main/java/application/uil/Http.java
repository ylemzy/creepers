package application.uil;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by huangzebin on 2017/3/1.
 */
public class Http {

    public static String postBody(String  url, String Cookie) throws IOException {
        HttpPost httpPost = new HttpPost(url);


        httpPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
        httpPost.setHeader("Accept-Language","zh-cn,zh;q=0.5");
        httpPost.setHeader("Accept-Charset","GBK,utf-8;q=0.7,*;q=0.7");
        httpPost.setHeader("Connection","keep-alive");
        httpPost.setHeader("Cookie", "JSESSIONID=9CE18CF4473E4CD220FED65FD3644305");


        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);


        builder.addTextBody("sortNo", "1", ContentType.DEFAULT_TEXT);
        builder.addTextBody("title", "htbaba1", ContentType.DEFAULT_TEXT);
        builder.addTextBody("descShort", "test+insert", ContentType.DEFAULT_TEXT);
        builder.addTextBody("infoType", "1", ContentType.DEFAULT_TEXT);
        builder.addTextBody("isRecommend", "1", ContentType.DEFAULT_TEXT);
        builder.addTextBody("img", "bg_content_14883652335786218_P_S.jpg", ContentType.DEFAULT_TEXT);
        builder.addTextBody("content", "test", ContentType.DEFAULT_TEXT);
        builder.addTextBody("newsType", "2", ContentType.DEFAULT_TEXT);
        builder.addTextBody("skuAid", "14883590953460200", ContentType.DEFAULT_TEXT);


        httpPost.setEntity(builder.build());
        HttpClient httpClient = HttpClients.createDefault();
        HttpResponse httpResponse = httpClient.execute(httpPost);
        HttpEntity httpEntity =  httpResponse.getEntity();
        return EntityUtils.toString(httpEntity);
    }
}
