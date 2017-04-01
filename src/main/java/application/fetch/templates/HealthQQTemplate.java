package application.fetch.templates;

import application.fetch.*;
import org.apache.commons.lang.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.Assert;

/**
 * Created by huangzebin on 2017/3/2.
 */
public class HealthQQTemplate extends AbstractTemplate {

    @Override
    public Category category(Category root) throws Exception {
        Document document = get(root.getLink());
        Element nav_bd = document.getElementById("nav_bd");
        Elements select = nav_bd.select("ul > li > a");
        select.forEach(a -> {
            Category category = new Category(a.html(), a.absUrl("href"), webId());
            root.addChild(category);

            if (category.getName().equals("疾病") ||
                    category.getName().equals("养生")) {
                category.setFetchable(true);
            }
        });
        return root;
    }

    @Override
    protected News itemRequest(Request request) throws Exception {
        News news = C_Main_Article_QQ(request);
        if (news == null){
            news = qq_article(request);
        }

        if (news.getMediaFrom().contains("家庭医生")){
            return null;
        }

        Document doc = get(request.getURL());
        Element elementById = doc.getElementById("Cnt-Main-Article-QQ");

        Elements img = elementById.getElementsByTag("img");
        if (img.size() > 0) {
            news.setFirstImg(img.first().attr("src"));
        }
        Elements script = elementById.getElementsByTag("script");
        script.forEach(s -> s.remove());


        Elements plays = elementById.getElementsByClass("rv-root-v2");
        plays.forEach(p -> {
            p.previousElementSibling().remove();
            p.previousElementSibling().remove();
            p.remove();
        });

        elementById.getElementsByTag("img").forEach(d -> {
            String src = d.attr("src");
            if (src.equalsIgnoreCase("http://img1.gtimg.com/health/pics/hv1/201/112/2092/136061061.jpg") ||
                    src.equalsIgnoreCase("http://img1.gtimg.com/health/pics/hv1/200/112/2092/136061060.jpg")){
                d.remove();
            }
        });

        news.setHtml(elementById.html());
        String text = elementById.text();
        String substring = StringUtils.strip(text).substring(0, 10);
        news.setDescShort(substring + "...");

        return news;
    }

    News qq_article(Request request) throws Exception {
        News news = News.make(request);
        Document doc = get(request.getURL());
        //title
        Elements titleBlock = doc.select(".qq_article > .hd");
        news.setTitle(titleBlock.select("h1").html());
        news.setMediaFrom(titleBlock.select(".qq_bar .a_source").text());
        news.setPublishTime(titleBlock.select(".qq_bar .a_time").html());
        return news;
    }

    News C_Main_Article_QQ(Request request) throws Exception {
        Document document = get(request.getURL());
        Element elementById = document.getElementById("C-Main-Article-QQ");
        if (elementById == null)
            return null;
        News news = News.make(request);
        news.setTitle(elementById.select("div.hd>h1").text());

        news.setMediaFrom(elementById.select("div.tit-bar .color-a-1").text());
        news.setPublishTime(elementById.select("div.tit-bar .article-time").text());
        return news;

    }

    @Override
    protected boolean pageRequest(Request request) throws Exception {
        Document document = get(request.getURL());
        if (RequestHelper.isFirstPage(request)) {
            String html = document.html();
            String pageFlag = "getString.pageCount = ";
            int i = html.indexOf(pageFlag);
            Assert.isTrue(i != -1);
            html = html.substring(i + pageFlag.length());
            int i1 = html.indexOf(";");
            Assert.isTrue(i1 != -1);
            int totalPage = Integer.valueOf(html.substring(0, i1));
            for (int j = 2; j <= totalPage; ++j) {
                Request page = RequestHelper.pageRequest("http://health.qq.com/c/jibingkepu_" + j + ".htm", request);
                addRequest(page);
            }
        }

        Elements items = document.select(".sBox > .bd > h2.yh > a");
        items.forEach(item -> {
            Request news = RequestHelper.itemRequest(item.absUrl("href"), request);
            addRequest(news);
        });

        return true;
    }

    @Override
    public String webId() {
        return "HealthQQ";
    }

    @Override
    public String rootUrl() {
        return "http://health.qq.com/";
    }
}
