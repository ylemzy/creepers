package application.http.request;


import com.google.common.collect.Maps;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.springframework.http.HttpMethod;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.File;
import java.util.Map;


/**
 *
 */
public class HttpRequestWithBody extends HttpRequest {

    public HttpRequestWithBody(String url) {
        super(HttpMethod.POST, url);
    }

    @Override
    public HttpRequestWithBody route(String name, String value) {
        super.route(name, value);
        return this;
    }


    @Override
    public HttpRequestWithBody header(String name, String value) {
        return (HttpRequestWithBody) super.header(name, value);
    }

    @Override
    public HttpRequestWithBody headers(Map<String, String> headers) {
        return (HttpRequestWithBody) super.headers(headers);
    }

    @Override
    public HttpRequestWithBody basicAuth(String username, String password) {
        super.basicAuth(username, password);
        return this;
    }

    @Override
    public HttpRequestWithBody cookie(String value) {
        super.cookie(value);
        return this;
    }

    @Override
    public HttpRequestWithBody queryString(Map<String, Object> parameters) {
        return (HttpRequestWithBody) super.queryString(parameters);
    }

    @Override
    public HttpRequestWithBody queryString(String name, Object value) {
        return (HttpRequestWithBody) super.queryString(name, value);
    }


    private Map<String, Object> values = Maps.newLinkedHashMap();

    private boolean hasFile = false;


    /**
     * @param name
     * @param value
     * @return
     */
    public HttpRequestWithBody field(String name, Object value) {
        hasFile = true;
        values.put(name, value);
        return this;
    }

    /**
     * @param name
     * @param file
     * @return
     */
    public HttpRequestWithBody field(String name, File file) {
        hasFile = true;
        values.put(name, file);
        return this;
    }

    public HttpRequestWithBody field(String name, byte[] stream) {
        hasFile = true;
        values.put(name, stream);
        return this;
    }


    /**
     * @param body
     * @return
     */
    public HttpRequestWithBody body(String body) {
        this.body = RequestBody.create(null, body);
        return this;
    }


    public HttpRequestWithBody body(byte[] body) {
        this.body = RequestBody.create(null, body);
        return this;
    }

    /**
     * @param body
     * @param contentType
     * @return
     */
    public HttpRequestWithBody body(String body, String contentType) {
        this.body = RequestBody.create(MediaType.parse(contentType), body);
        return this;
    }


    /**
     * @param body
     * @param contentType
     * @return
     */
    public HttpRequestWithBody body(byte[] body, String contentType) {
        this.body = RequestBody.create(MediaType.parse(contentType), body);
        return this;
    }


    public RequestBody getBody() {
        if (this.body == null) {
            if (this.hasFile) {
                MultipartBody.Builder builder = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM);
                for (Map.Entry<String, Object> map : values.entrySet()) {
                    Object value = map.getValue();
                    if (value instanceof File) {
                        builder.addPart(RequestBody.create(null, (File) value));
                    } else if (value instanceof byte[]) {
                        builder.addPart(RequestBody.create(null, (byte[]) value));
                    } else {
                        builder.addFormDataPart(map.getKey(), String.valueOf(value));
                    }
                }

                this.body = builder.build();
            } else {
                if (!values.isEmpty()) {
                    FormBody.Builder builder = new FormBody.Builder();
                    for (Map.Entry<String, Object> map : values.entrySet()) {
                        builder.add(map.getKey(), String.valueOf(map.getValue()));
                    }
                    this.body = builder.build();
                }
            }
        }
        return body;
    }
}
