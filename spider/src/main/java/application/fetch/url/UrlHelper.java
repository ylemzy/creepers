package application.fetch.url;

import http.UrlMaker;
import org.apache.commons.lang.StringUtils;

import java.net.URL;

/**
 * Created by J on 5/7/2017.
 */
public class UrlHelper {

    public static UrlType filter(UrlMaker url){
        URL link = url.getUrl();
        if (StringUtils.isEmpty(link.getProtocol())){
            return UrlType.ERROR;
        }

        if (StringUtils.isEmpty(link.getPath())){
            return UrlType.HOST;
        }

        if (StringUtils.isNotEmpty(link.getQuery())){
            return UrlType.QUERY;
        }else{
            return UrlType.FIX;
        }

    }
}
