package application.controller;

import application.elastic.repository.NewsRepository;
import application.fetch.News;
import application.uil.JsonHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by huangzebin on 2017/3/6.
 */
@RestController
public class PageViewController {

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    NewsRepository newsRepository;

    @RequestMapping(value = "/page/test", method = RequestMethod.GET)
    public String baobaoPage() throws IOException {
        Connection connect = Jsoup.connect("http://m.htbaobao.com/hotspotdetail.html?aid=14702758170050858");
        Document document = connect.get();
        Elements totalMain = document.getElementsByClass("total-main");
        totalMain.first().select("div.content>#content").first().append(getBaobaoContent());
        String html = document.html();
        html = html.replace("href=\"css/", "href=\"http://m.htbaobao.com/css/")
                .replace("src=\"js/", "src=\"http://m.htbaobao.com/js/")
                .replace("src=\"images/", "src=\"http://m.htbaobao.com/images/");
        return html;
    }

    public String getBaobaoContent() throws IOException {
        Connection connect = Jsoup.connect("http://data.htbaobao.com/uc-controller/getnewsdetail.do");
        Connection.Response execute = connect.method(Connection.Method.POST)
                .data("newsId", "14702758170050858")
                .cookie("Hm_lvt_a87d01096a0d34762a5767f8920b9647", "1486535929,1487815383")
                .execute();
        HashMap hashMap = JsonHelper.toObject(execute.body(), HashMap.class);
        Map model = (Map)hashMap.get("model");
        return model.get("content").toString();
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public String page(@RequestParam String id) throws IOException {
        News news = newsRepository.findOne(id);
        if (news == null){
            return null;
        }

        Connection connect = Jsoup.connect("http://m.htbaobao.com/hotspotdetail.html?aid=14702758170050858");
        Document document = connect.get();
        Elements totalMain = document.getElementsByClass("total-main");
        totalMain.first().getElementById("title").html(news.getTitle());
        totalMain.first().select("div.content>#content").first().append(news.getHtml());
        String html = document.html();
        html = html.replace("href=\"css/", "href=\"http://m.htbaobao.com/css/")
                .replace("src=\"js/", "src=\"http://m.htbaobao.com/js/")
                .replace("src=\"images/", "src=\"http://m.htbaobao.com/images/");
        return html;
    }
}
