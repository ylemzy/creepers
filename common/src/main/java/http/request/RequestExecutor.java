package http.request;


import http.response.HttpResponse;
import http.utils.HttpClientHelper;


public class RequestExecutor {


    private Class clazz;

    private HttpRequest httpRequest;

    /**
     * @param httpRequest
     * @param clazz
     */
    protected RequestExecutor(HttpRequest httpRequest, Class clazz) {
        this.clazz = clazz;
        this.httpRequest = httpRequest;
    }

    /**
     * @param <T>
     * @return
     */
    public <T> HttpResponse<T> execute() {
        return HttpClientHelper.request(httpRequest, clazz);
    }
}
