package application.http.request;


import application.http.response.HttpResponse;
import application.http.utils.HttpClientHelper;


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
