package application.fetch;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangzebin on 2017/3/2.
 */
@Document(indexName = "news", type = "1")
public class News {

    @Id
    String id;

    String webId;

    String title;

    String mediaFrom;

    String publishTime;

    String url;

    String html;

    String firstImg;

    String descShort;

    List<String> keywords;

    public static News make(Request request){
        News news = new News();
        news.setUrl(request.getURL());
        news.setWebId(request.getWebId());
        news.setId(RequestHelper.makeNewsId(request.getURL(), request.getWebId()));
        return news;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWebId() {
        return webId;
    }

    public void setWebId(String webId) {
        this.webId = webId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMediaFrom() {
        return mediaFrom;
    }

    public void setMediaFrom(String mediaFrom) {
        this.mediaFrom = mediaFrom;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public void addKeyWord(String keyword){
        if (this.keywords == null)
            this.keywords = new ArrayList<>();
        this.keywords.add(keyword);
    }

    public String getFirstImg() {
        return firstImg;
    }

    public void setFirstImg(String firstImg) {
        this.firstImg = firstImg;
    }

    public String getDescShort() {
        return descShort;
    }

    public void setDescShort(String descShort) {
        this.descShort = descShort;
    }
}
