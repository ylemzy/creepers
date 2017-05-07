package application.fetch.request.filter;

import application.fetch.request.Request;
import application.fetch.request.RequestHelper;

/**
 * Created by huangzebin on 2017/3/7.
 */
public abstract class AbstractFilter implements RequestFilter{

    protected FilterResult filterResult = new FilterResult();

    @Override
    public void filter(Request request) {
        if (RequestHelper.isFirstPage(request)){
            filterResult.setFirstPageRequest(filterResult.getFirstPageRequest() + 1);
        }else if (RequestHelper.isPage(request)){
            filterResult.setPageRequest(filterResult.getPageRequest() + 1);
        }else if (RequestHelper.isNews(request)){
            filterResult.setNewsRequest(filterResult.getNewsRequest() + 1);
        }
    }

    protected void errorFilter(Request request){
        if (RequestHelper.isFirstPage(request)){
            filterResult.addFirstPageError(request);
        }else if (RequestHelper.isPage(request)){
            filterResult.addPageError(request);
        }else if (RequestHelper.isNews(request)){
            filterResult.addNewsError(request);
        }
    }

    @Override
    public FilterResult result() {
        return filterResult;
    }
}
