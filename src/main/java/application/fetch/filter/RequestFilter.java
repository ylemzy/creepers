package application.fetch.filter;

import application.fetch.Request;

/**
 * Created by huangzebin on 2017/3/7.
 */
public interface RequestFilter {
    void filter(Request request);
    FilterResult result();
}
