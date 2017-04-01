package application.http.request;

import application.http.response.Header;
import application.http.response.HttpResponse;
import application.http.utils.HttpClientHelper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.nodes.Document;

import java.io.InputStream;

/**
 * 基础request,主要用来改变返回的数据类型
 */
public abstract class BaseRequest {

    protected HttpRequest httpRequest;


    protected BaseRequest(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public HttpRequest getHttpRequest() {
        return this.httpRequest;
    }


    protected BaseRequest() {
        super();
    }

    /**
     * nobody
     *
     * @return
     */
    public HttpResponse<Header> asHeader() {
        return HttpClientHelper.request(httpRequest, Header.class);
    }


    /**
     * @return
     * @throws java.io.IOException
     */
    public HttpResponse<String> asString() {
        return HttpClientHelper.request(httpRequest, String.class);
    }


    /**
     * @return
     * @throws java.io.IOException
     */
    public HttpResponse<JSONObject> asJSONObject() {
        Class<JSONObject> jsonObjectClass = JSONObject.class;
        return HttpClientHelper.request(httpRequest, JSONObject.class);
    }


    /**
     * @return
     * @throws java.io.IOException
     */
    public HttpResponse<Document> asDocument() {
        return HttpClientHelper.request(httpRequest, Document.class);
    }


    /**
     * @return
     * @throws java.io.IOException
     */
    public HttpResponse<JSONArray> asJSONArray() {
        return HttpClientHelper.request(httpRequest, JSONArray.class);
    }


    /**
     * @return
     * @throws java.io.IOException
     */
    public HttpResponse<byte[]> asBinary() {
        return HttpClientHelper.request(httpRequest, byte[].class);
    }


    /**
     * @return
     */
    public HttpResponse<InputStream> asByteStream() {
        return HttpClientHelper.request(httpRequest, InputStream.class);
    }


    /**
     *
     */
    public void asStringAsync() {
        HttpClientHelper.requestAsync(httpRequest, InputStream.class);
    }



/*    public BaseRequest useAuthorizationProxy(AuthorizationProxy authorizationProxy) {
        this.authorizationProxy = authorizationProxy;
        return this;
    }*/

/*    public AuthorizationProxy getAuthorizationProxy(){
        return this.authorizationProxy;
    }*/
}
