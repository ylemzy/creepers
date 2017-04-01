

package application.http.request;



//import application.http.utils.Base64Coder;
import application.http.utils.ScriptLoader;
import application.http.utils.URLParamEncoder;
import okhttp3.RequestBody;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
public class HttpRequest extends BaseRequest {

    private HttpMethod httpMethod;
    protected String url;
    Map<String, List<String>> headers = new TreeMap<String, List<String>>(String.CASE_INSENSITIVE_ORDER);
    protected RequestBody body;
    private ScriptLoader script;

    public HttpRequest(HttpMethod method, String url) {
        this.httpMethod = method;
        this.url = url;
        this.httpRequest = this;
    }

    /**
     * 异步设置函数，必须要调用execute
     *
     * @param clazz
     * @return
     */
    public RequestExecutor as(Class clazz) {
        return new RequestExecutor(this, clazz);
    }

    /**
     * @param name
     * @param value
     * @return
     */
    public HttpRequest route(String name, String value) {
        Matcher matcher = Pattern.compile("\\{" + name + "\\}").matcher(url);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        if (count == 0) {
            throw new RuntimeException("Can't find route parameter name \"" + name + "\"");
        }
        this.url = url.replaceAll("\\{" + name + "\\}", URLParamEncoder.encode(value));
        return this;
    }


    /**
     * @param script
     * @return
     */
    public HttpRequest script(ScriptLoader script) {
        this.script = script;
        return this;
    }

    /**
     * @param username
     * @param password
     * @return
     */
    public HttpRequest basicAuth(String username, String password) {
        header("Authorization", "Basic " + Base64Coder.encodeString(username + ":" + password));
        return this;
    }


    public HttpRequest cookie(String value) {
        header(HttpHeaders.COOKIE, value);
        return this;
    }

    /**
     * @param name
     * @param value
     * @return
     */
    public HttpRequest header(String name, String value) {
        List<String> list = this.headers.get(name.trim());
        if (list == null) {
            list = new ArrayList<String>();
        }
        list.add(value);
        this.headers.put(name.trim(), list);
        return this;
    }

    /**
     * @param headers
     * @return
     */
    public HttpRequest headers(Map<String, String> headers) {
        if (headers != null) {
            for (Entry<String, String> entry : headers.entrySet()) {
                header(entry.getKey(), entry.getValue());
            }
        }
        return this;
    }


    /**
     * @param name
     * @param value
     * @return
     */
    public HttpRequest queryString(String name, Collection<?> value) {
        for (Object cur : value) {
            queryString(name, cur);
        }
        return this;
    }


    /**
     * @param name
     * @param value
     * @return
     */
    public HttpRequest queryString(String name, Object value) {
        StringBuilder queryString = new StringBuilder();
        if (this.url.contains("?")) {
            queryString.append("&");
        } else {
            queryString.append("?");
        }
        try {
            queryString.append(name).append("=").append(URLEncoder.encode((value == null) ? "" : value.toString(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        this.url += queryString.toString();
        return this;
    }


    /**
     * @param parameters
     * @return
     */
    public HttpRequest queryString(Map<String, Object> parameters) {
        if (parameters != null) {
            for (Entry<String, Object> param : parameters.entrySet()) {
                if (param.getValue() instanceof String || param.getValue() instanceof Number || param.getValue() instanceof Boolean) {
                    queryString(param.getKey(), param.getValue());
                } else {
                    throw new RuntimeException("Parameter \"" + param.getKey() + "\" can't be sent with a GET request because of type: " + param.getValue().getClass().getName());
                }
            }
        }
        return this;
    }


    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getUrl() {
        return url;
    }

    public Map<String, List<String>> getHeaders() {
        if (headers == null)
            return new HashMap<>();
        return headers;
    }

    public RequestBody getBody() {
        return body;
    }

}
