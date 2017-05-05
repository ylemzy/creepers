package http.request;


/**
 *
 */
public class GetRequest extends HttpRequest {

    public GetRequest(String url) {
        super("get", url);
    }
}
