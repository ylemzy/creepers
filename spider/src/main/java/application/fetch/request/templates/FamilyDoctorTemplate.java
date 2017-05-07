package application.fetch.request.templates;/**
 * Created by huangzebin on 2017/3/16.
 */

import application.elastic.document.News;
import application.fetch.request.AbstractTemplate;
import application.fetch.request.Category;
import application.fetch.request.Request;
import application.fetch.request.RequestHelper;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.Assert;

public class FamilyDoctorTemplate extends AbstractTemplate {
    private static final Logger logger = LogManager.getLogger();

    final static int pageLimit = 3;

    @Override
    public String webId() {
        return "FamilyDoctor";
    }

    @Override
    public String rootUrl() {
        return "http://xnxg.familydoctor.com.cn/zs/";
    }

    @Override
    public Category category(Category root) throws Exception {
        root.addChild(Category.makeFetchable("疾病大全 > 肿瘤 > 肿瘤疾病", "http://cancer.familydoctor.com.cn/zljb/", root));
        root.addChild(Category.makeFetchable("疾病大全 > 心脑血管 > 心脑血管知识", "http://xnxg.familydoctor.com.cn/zs/", root));
        return root;
    }

    @Override
    protected News itemRequest(Request request) throws Exception {

        News news = News.make(request);
        Document document = get(request.getURL());
        String text = document.select(".article-titile h1").first().text();
        news.setTitle(text);

        String text1 = document.select(".article-titile .left").first().text();
        logger.debug("time and media = [{}]", text1);
        String[] split = text1.split(" ");
        Assert.isTrue(split.length >= 2);
        news.setPublishTime(split[0]);
        news.setMediaFrom(split[split.length-1]);

        Element viewContent = document.getElementById("viewContent");
        Elements adLeftPip = viewContent.getElementsByClass("adLeftPip");
        if (adLeftPip != null){
            adLeftPip.remove();
        }
        news.setHtml(viewContent.html());
        news.setDescShort(viewContent.text().substring(0, 10) + "...");

        return news;
    }

    @Override
    protected boolean pageRequest(Request request) throws Exception {
        Document document = get(request.getURL());
        if (RequestHelper.isFirstPage(request)){
            Element title = document.getElementsByTag("title").first();
            int i = title.text().lastIndexOf(" ");
            String substring = StringUtils.strip(title.text().substring(i));
            logger.info("Page info ->[{}]", substring);
            String replace = substring.replace("第", "").replace("页", "");
            int totalPage = Integer.valueOf(replace);
            for (int j = totalPage - 1; j >= totalPage - pageLimit && j > 0; --j){
                addRequest(RequestHelper.pageRequest(request.getURL() + j + ".html", request));
            }
        }


        Elements list = document.select(".mNyList .textTitle h4>a");
        list.forEach(a -> addRequest(RequestHelper.itemRequest(a.absUrl("href"), request)));
        return false;
    }
}
