package application.http.response;


import application.http.HttpHeaders;
import okhttp3.Headers;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.InputStream;


/**
 * @param <T>
 */
public class HttpResponse<T> extends Header {


    private T body;


    @SuppressWarnings("unchecked")
    public HttpResponse(String url, String method, Response response, Class<T> responseClass) throws IOException {

        this.headers = response.headers();

        this.setContentType(response.header(HttpHeaders.CONTENT_TYPE));

        this.setStatusCode(response.code());

        this.setStatusText(response.message());

        this.setUrl(url);
        this.setMethod(method);

      /*  this.setStartTime(Long.valueOf(response.header("OkHttp-Sent-Millis")));
        this.setEndTime(Long.valueOf(response.header("OkHttp-Received-Millis")));*/

        ResponseBody responseBody = response.body();

        this.setContentLength(responseBody.contentLength());
        if (responseBody.contentType() != null) {
            this.setCharset(responseBody.contentType().charset());
        }

        try {

            if (Header.class.equals(responseClass)) {
                this.body = null;
            }  else if (JSONArray.class.equals(responseClass)){
                this.body = (T)new JSONArray(responseBody.string());
            } else if (JSONObject.class.equals(responseClass)) {
                this.body = (T)new JSONObject(responseBody.string());
            }else if (String.class.equals(responseClass)) {
                this.body = (T) responseBody.string();
            } else if (Document.class.equals(responseClass)) {
                this.body = (T) Jsoup.parse(responseBody.string(), this.getUrl());
            } else if (byte[].class.equals(responseClass)) {
                this.body = (T) responseBody.bytes();
            } else if (InputStream.class.equals(responseClass)) {
                this.body = (T) responseBody.byteStream();
            } else {
                throw new IOException("Unsupported!");
            }
        } finally {
            //response.body().close();
        }


    }

    @SuppressWarnings("unchecked")
    public HttpResponse(String url, String method, CloseableHttpResponse response, Class<T> responseClass) throws IOException {

        try {
            Headers.Builder builder = new Headers.Builder();
            for (org.apache.http.Header header : response.getAllHeaders()) {
                builder.add(header.getName(), header.getValue());
            }

            this.headers = builder.build();

            this.setContentType(response.getEntity().getContentType().getValue());

            this.setStatusCode(response.getStatusLine().getStatusCode());

            this.setStatusText(response.getStatusLine().toString());

            this.setUrl(url);
            this.setMethod(method);

            HttpEntity entity = response.getEntity();

            this.setContentLength(response.getEntity().getContentLength());
            this.setCharset(ContentType.getOrDefault(entity).getCharset());


            if (Header.class.equals(responseClass)) {
                this.body = null;
            } else if (JSONArray.class.equals(responseClass)){
                this.body = (T)new JSONArray(EntityUtils.toString(entity));
            } else if (JSONObject.class.equals(responseClass)) {
                this.body = (T)new JSONObject(EntityUtils.toString(entity));
            } else if (String.class.equals(responseClass)) {
                this.body = (T) EntityUtils.toString(entity);
            } else if (Document.class.equals(responseClass)) {
            /*need to fix baseUrl*/
                this.body = (T) Jsoup.parse(EntityUtils.toString(entity), this.getUrl());
            } else if (byte[].class.equals(responseClass)) {
                this.body = (T) (EntityUtils.toByteArray(entity));
            } else {
                throw new IOException("Unsupported!");
            }
        } finally {
            response.close();
        }
    }

    /**
     * @return
     */
    public T body() {
        return this.body;
    }


    /**
     * @return
     */
    public Headers headers() {
        return this.headers;
    }

}
