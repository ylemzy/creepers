package application.http.request;


import org.springframework.http.HttpMethod;

/**
 *
 */
public class GetRequest extends HttpRequest {

    public GetRequest(String url) {
        super(HttpMethod.GET, url);
    }
}
