package application.fetch.templates;

import application.fetch.*;
import application.http.UrlMaker;
import application.uil.JsonHelper;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by huangzebin on 2017/3/3.
 */
public class HealthSinaTemplate extends AbstractTemplate {

    int pageSize = 30;

    @Override
    public String webId() {
        return "HealthSina";
    }

    @Override
    public String rootUrl() {
        return "http://health.sina.com.cn/";
    }

    @Override
    public Category category(Category root) throws Exception {
        UrlMaker urlMaker = UrlMaker.make("http://feed.mix.sina.com.cn/api/roll/get")
                .param("pageid", "39")
                .param("lid", "561")
                .param("page", "1")
                .param("num", String.valueOf(pageSize)); // the first page
        Category category = new Category("全部", urlMaker.getUrl(), root.getWebId());
        category.setFetchable(true);
        root.addChild(category);
        return root;
    }

    @Override
    protected News itemRequest(Request request) throws Exception {

        News news = News.make(request);
        Document document = get(request.getURL());
        String artibodyTitle = document.getElementById("main_title").html();
        Element contentElement = document.getElementById("artibody");
        String artibody = contentElement.html();

        Element pageTools = document.getElementById("page-tools");
        String pub_date = pageTools.select(".titer").first().html();
        Element mediaSource = pageTools.select("span.source > a").first();
        if (mediaSource == null){
            mediaSource = pageTools.select("span.source").first();
        }

        if (mediaSource == null){
            mediaSource = pageTools.select("#media_name > a").first();
        }
        String media_name = mediaSource.text();
        news.setTitle(artibodyTitle);
        news.setHtml(artibody);
        news.setPublishTime(pub_date);
        news.setMediaFrom(media_name);

        Elements img = contentElement.getElementsByTag("img");
        if (img.size() > 0){
            news.setFirstImg(img.first().attr("src"));
        }

        String text = contentElement.text();
        news.setDescShort(StringUtils.strip(text).substring(0, 10) + "...");
        return news;
    }

    @Override
    protected boolean pageRequest(Request request) throws Exception {
        UrlMaker urlMaker = UrlMaker.make(request.getURL())
                .param("num", String.valueOf(pageSize));

        String body = Jsoup.connect(urlMaker.getUrl()).ignoreContentType(true).get().body().html();
        Map map = (Map)JsonHelper.toObject(body, Map.class).get("result");

        if (RequestHelper.isFirstPage(request)) {
            //int total = Integer.valueOf(map.get("total").toString());
            int totalPage = (getFetchNewsLimit() / pageSize) + ((getFetchNewsLimit() % pageSize) == 0 ? 0 : 1);
            for (int i = 2; i <= totalPage; ++i) {
                String url = urlMaker.param("page", "" + i)
                        .getUrl();
                addRequest(RequestHelper.pageRequest(url, request));
            }
        }

        ArrayList<Map> datas = (ArrayList<Map>) map.get("data");
        datas.forEach(d -> {
            addRequest(RequestHelper.itemRequest(d.get("url").toString(), request));
        });
        return true;
    }
}
