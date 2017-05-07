package application.fetch.request.filter;

import application.fetch.request.Request;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangzebin on 2017/3/7.
 */
public class FilterResult {

    int pageRequest;

    int newsRequest;

    int firstPageRequest;

    List<String> pageError = new ArrayList<>();

    List<String> newsError = new ArrayList<>();

    List<String> firstPageError = new ArrayList<>();

    public int getPageRequest() {
        return pageRequest;
    }

    public void setPageRequest(int pageRequest) {
        this.pageRequest = pageRequest;
    }

    public int getNewsRequest() {
        return newsRequest;
    }

    public void setNewsRequest(int newsRequest) {
        this.newsRequest = newsRequest;
    }

    public int getFirstPageRequest() {
        return firstPageRequest;
    }

    public void setFirstPageRequest(int firstPageRequest) {
        this.firstPageRequest = firstPageRequest;
    }

    public List<String> getPageError() {
        return pageError;
    }

    public void setPageError(List<String> pageError) {
        this.pageError = pageError;
    }

    public List<String> getNewsError() {
        return newsError;
    }

    public void setNewsError(List<String> newsError) {
        this.newsError = newsError;
    }

    public List<String> getFirstPageError() {
        return firstPageError;
    }

    public void setFirstPageError(List<String> firstPageError) {
        this.firstPageError = firstPageError;
    }

    public void addFirstPageError(Request request){
        this.firstPageError.add(request.getURL());
    }

    public void addPageError(Request request){
        this.pageError.add(request.getURL());
    }

    public void addNewsError(Request request){
        this.newsError.add(request.getURL());
    }

}
