package application.fetch.templates;/**
 * Created by huangzebin on 2017/4/17.
 */

import application.fetch.*;
import util.DateTimeUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DavinciTemplate extends AbstractTemplate {
    private static final Logger logger = LogManager.getLogger();


    @Override
    public String webId() {
        return "Davinci";
    }

    @Override
    public String rootUrl() {
        return null;
    }

    @Override
    public Category category(Category root) throws Exception {
        root.addChild(Category.makeFetchable("", "http://news.ifeng.com/", root));
        return root;
    }

    @Override
    protected News itemRequest(Request request) throws Exception {
        Document document = get(request.getURL());

        News news = News.make(request);
        ItemParser itemParser = new ItemParser(document);
        itemParser.make(news);

        return news;
    }

    @Override
    protected boolean pageRequest(Request request) throws Exception {
        Document document = get(request.getURL());
        Elements a = document.getElementsByTag("a");
        Set<String> url = new HashSet<>();
        a.forEach(link -> {
            String href = link.absUrl("href");
            if (!href.endsWith(".js") && !href.endsWith(".css"))
                url.add(href);
        });

        url.forEach(link -> {
            addRequest(RequestHelper.itemRequest(link, request));
        });
        return false;
    }


    public class ItemParser {
        Document document;

        Element content;
        Element title;
        Element publishTime;
        Element media;

        final String[] hList = {
                "h1", "h2", "h3",// "h4", "h5", "h6"
        };

        public ItemParser(Document document) {
            this.document = document;
        }

        private Element findParentOfMostP() {
            //exclude title children
            for (Element element : title.children()) {
                element.remove();
            }

            Elements elements = document.getElementsByTag("p");
            Set<Element> parent = new HashSet<>();
            elements.forEach(e -> parent.add(e.parent()));

            Element mostChildrenP = elements.first();
            for (Element e : parent) {
                if (e.getElementsByTag("p").size() > mostChildrenP.getElementsByTag("p").size()) {
                    mostChildrenP = e;
                }
            }

            return mostChildrenP;
        }

        public Element findContent() {
            content = findParentOfMostP();
            return content;
        }

        private Element findTagH() {
            Elements allH = new Elements();
            for (String h : hList) {
                Elements elementsByTag = document.getElementsByTag(h);
                allH.addAll(elementsByTag.stream().filter(element -> element.text().length() > 0).collect(Collectors.toList()));
            }

            Element element = Finder.maxCost(allH);
            return element;
        }

        public Element findTitle() throws Exception {
            title = findTagH();
            if (title == null) {
                throw new Exception("No title has found but uncertainly result");
            }
            return title;
        }

        public Element findPublishTime() throws Exception {
            publishTime = findDateTime();
            return publishTime;
        }

        private Element findDateTime() throws Exception {
            Element commonParent = findCommonParent(content, title);
            if (commonParent == null) {
                throw new Exception("Can't find common parent");
            }
            Elements times = commonParent.getElementsMatchingOwnText(DateTimeUtil.pattern);
            if (times.size() == 0) {
                throw new Exception("Can't find datetime element");
            }
            Assert.isTrue(times.size() > 0);

            return times.first();
            //throw new Exception("Multiple datetime found but uncertainly result");
        }

        public Element findMedia() {
            String s = publishTime.ownText();
            String date = DateTimeUtil.findDate(s);
            String[] split = s.split(date);
            if (split.length == 1) {
                if (!split[0].contains("时间") && !split[0].contains("日期")) {
                    return publishTime;
                }
            }

            Element commonParent = findCommonParent(publishTime, title);

            media = findMediaBySiblingTop(publishTime, commonParent);
            return media;
        }

        private Element findMediaBySiblingTop(Element element, Element blockParent) {
            Element current = element;
            while (current != null && current != blockParent) {
                Elements elements = current.siblingElements();
                Element mediaText = findMediaText(elements);
                if (mediaText != null)
                    return mediaText;

                current = current.parent();
            }

            return null;
        }

        private Element findMediaText(Elements elements) {
            for (Element element : elements) {
                String replace = element.text().replace("来源", "").replace("出自", "").replace("转载", "").replace("作者", "").replace(":", "").trim();
                if (replace.length() > 2) {
                    return element;
                }
            }
            return null;
        }

        public Element findCommonParent(Element a, Element b) {
            Assert.notNull(a);
            Assert.notNull(b);

            Element parent = a;
            while (parent != null) {
                Element bParent = b;
                while (bParent != null) {
                    if (bParent == parent)
                        return parent;
                    bParent = bParent.parent();
                }

                parent = parent.parent();
            }
            return null;
        }

        public void make(News news) throws Exception {
            Element title = findTitle();
            news.setTitle(title.text());
            Element content = findContent();
            news.setHtml(content.html());
            Element publishTime = findPublishTime();
            String date = DateTimeUtil.findDate(publishTime.ownText());
            news.setPublishTime(date);
            Element media = findMedia();
            String mediaText = media.text().replace(date, "").trim();
            news.setMediaFrom(mediaText);
        }
    }

    static class Finder {

        static class ElementCost {
            List<Integer> siblings = new ArrayList<>();
            Element element;

            public ElementCost(Element element) {
                this.element = element;
            }

            public List<Integer> getSiblings() {
                return siblings;
            }

            public void setSiblings(List<Integer> siblings) {
                this.siblings = siblings;
            }

            public Element getElement() {
                return element;
            }

            public void setElement(Element element) {
                this.element = element;
            }

            public void addSibling(int sibling){
                siblings.add(sibling);
            }

            public int compare(ElementCost elementCost){
                List<Integer> other = elementCost.getSiblings();
                for (int i = 0; i < this.siblings.size() && i < other.size(); ++i){
                    int c = this.siblings.get(i) - other.get(i);
                    if (c != 0){
                        logger.info("{} - {} = {} ", this.siblings, other, c);
                        return c;
                    }
                }
                return 0;
            }
        }

        public static Element maxCost(Elements elements) {
            ElementCost min = null;
            for (Element element : elements) {
                ElementCost cost = toCost(findParentPath(element));
                if (min == null){
                    min = cost;
                }else if (min.compare(cost) > 0){
                    min = cost;
                }
            }
            if (min == null)
                return null;
            return min.getElement();
        }

        public static Elements findParentPath(Element element) {
            Elements elements = new Elements();
            Element tmp = element;
            while (tmp != null) {
                elements.add(tmp);
                tmp = tmp.parent();
            }
            return elements;
        }

        public static ElementCost toCost(Elements elements) {
            ElementCost elementCost = new ElementCost(elements.first());
            for (int i = elements.size() - 1; i >= 0; --i){
               elementCost.addSibling(elements.get(i).elementSiblingIndex());
            }

            return elementCost;
        }
    }
}
