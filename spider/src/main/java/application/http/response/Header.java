package application.http.response;

import okhttp3.Headers;

import java.nio.charset.Charset;


public class Header implements java.io.Serializable {

    protected Headers headers;

    private String url;

    private long startTime;

    private long endTime;

    private String contentType;

    private int statusCode;

    private String statusText;

    private String userAgent;

    private String method;

    private Charset charset;

    private long contentLength;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getStartTime() {
        return startTime;
    }

    public boolean isSuccessful() {
        return statusCode >= 200 && statusCode < 300;
    }

    public boolean isDowntime(){
        return statusCode==503||(this.headers!=null&&this.headers.get("Retry-After")!=null);
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Charset getCharset() {
        return charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    public long getContentLength() {
        return contentLength;
    }

    public void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }

    /**
     * @return
     */
    public boolean isDocument() {
        return this.contentType != null && (this.contentType.contains("html") || !isImage() || !isJavascript() || !isJSON());
    }


    /**
     * @return
     */
    public boolean isJavascript() {
        return this.contentType != null && this.contentType.contains("javascript");
    }


    /**
     * @return
     */
    public boolean isJSON() {
        return this.contentType != null && (this.contentType.contains("json") || this.contentType.contains("jsonp"));
    }

    /*==============================================image type============================================*/

    /**
     * @return
     */
    public boolean isImage() {
        return this.contentType != null && this.contentType.contains("image");
    }

    /**
     * @return
     */
    public boolean isPNG() {
        return this.isImage() && this.contentType.contains("png");
    }

    /**
     * @return
     */
    public boolean isGIF() {
        return this.isImage() && this.contentType.contains("gif");
    }


    /**
     * @return
     */
    public boolean isJPEG() {
        return this.isImage() && this.contentType.contains("jpeg");
    }

}
