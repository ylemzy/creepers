package application.fetch.request.filter;

import application.fetch.request.Request;

/**
 * Created by huangzebin on 2017/3/7.
 */
public interface RequestFilter {
    void filter(Request request);
    FilterResult result();
}
