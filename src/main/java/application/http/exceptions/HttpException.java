package application.http.exceptions;

public class HttpException extends RuntimeException {

    private static final long serialVersionUID = -8527350615241058219L;

    public HttpException(Exception e) {
        super(e);
    }

    public HttpException(String msg) {
        super(msg);
    }

}
