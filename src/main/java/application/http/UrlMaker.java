package application.http;

import org.apache.logging.log4j.util.Strings;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huangzebin on 2017/3/6.
 */
public class UrlMaker {

    Map<String, String> params = new HashMap<>();
    String uri = null;

    public static UrlMaker make(String url){
        return new UrlMaker(url);
    }

    public UrlMaker(String url) {
        parse(url);
    }

    public String getUrl(){
        String url = uri + "?";

        StringBuffer buffer = new StringBuffer();
        params.forEach( (k, v)->{
            buffer.append(k + "=" + v + "&");
        });
        if (buffer.length() > 0){
            buffer.setLength(buffer.length() - 1);
        }
        return url + buffer.toString();
    }

    public UrlMaker param(String key, String value){
        if (this.params == null){
            this.params = new HashMap<>();
        }

        this.params.put(key, value);
        return this;
    }

    public String param(String key){
        if (params == null)
            return null;
        return params.get(key);
    }

    private void parse(String strURL) {

        String[] arrSplit = strURL.trim().toLowerCase().split("[?]");
        if (arrSplit.length > 0) {
            this.uri = arrSplit[0];
        }

        if (arrSplit.length > 1){
            parseParams(arrSplit[1]);
        }

        Assert.isTrue(!Strings.isBlank(this.uri));
    }

    private void parseParams(String paramUrl) {

        String[] split = paramUrl.split("[&]");
        for (String s : split) {
            String[] keyValue = s.split("[=]");
            if (keyValue.length == 2){
                this.params.put(keyValue[0], keyValue[1]);
            }else if (keyValue.length == 1){
                this.params.put(keyValue[0], "");
            }
        }
    }
}
