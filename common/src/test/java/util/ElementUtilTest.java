package util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by huangzebin on 2017/5/22.
 */

/*@RunWith(SpringRunner.class)
@SpringBootTest*/
public class ElementUtilTest {
    private static final Logger logger = LogManager.getLogger();


    @Test
    public void test() throws IOException {
        //test("http://www.caac.gov.cn/FWDT/");
        test("http://health.cnr.cn/");
        //test("http://www.enorth.com.cn/");
        //test("http://lx.huanqiu.com/");
        //test("http://lx.huanqiu.com/zhinan/"); ok


    }

    @Test
    public void pageTest() throws IOException {
        //test("http://culture.gmw.cn/node_10570.htm"); //page
        //test("http://lx.huanqiu.com/lxnews/"); //page
        test("http://china.huanqiu.com/fanfu/"); //page
        //test("http://sports.cri.cn/"); //page without time
        //test("http://lx.huanqiu.com/lxnews/2017-05/10709302.html"); //item
        //test("http://www.cyol.com/index.htm"); //main
        //test("http://news.cyol.com/node_60189.htm");
        //test("http://culture.gmw.cn/");
        //test("http://news.dahe.cn/city_zz-1/index.html");
        //test("http://lx.huanqiu.com/immigration/");
        //test("http://lx.huanqiu.com/"); //fade page
    }

    private void test(String url) throws IOException {
        Document document = Jsoup.connect(url).timeout(10000).get();
        Elements allLink = document.getElementsByTag("a");

        Elements shortTextLink = allLink
                .stream()
                .filter(element -> element.text().length() > 0)
                .collect(Collectors.toCollection(Elements::new));

      /*  for (int i = 0; i < shortTextLink.size(); ++i){
            logger.info("--> {}", shortTextLink.text());
        }
*/
        Map<Element, Set<Element>> elementSetMap = ElementUtil.ModuleFinder.aggModuleInElement(shortTextLink);

        if (ElementUtil.PageLogic.isPage(elementSetMap)){
            logger.info("__________It's a page");
        }else{
            Map<Element, Set<Element>> elementSetMap1 = ElementUtil.ModuleFinder.aggModuleInElement(shortTextLink, elementSetMap);
            if (ElementUtil.PageLogic.isPage(elementSetMap1)){
                logger.info("__________It's a page");
            }else{
                logger.info("__________Not a page");
            }

        }
    }

    @Test
    public void testCategory() throws IOException {
        //testCategory("http://culture.gmw.cn/");
        //testCategory("http://lx.huanqiu.com/zhinan/");
        //testCategory("http://yuqing.cyol.com/content/2017-05/23/content_16106772.htm");
        //testCategory("http://www.cri.cn/");
        //testCategory("http://www.dahe.cn/");
        testCategory("http://lx.huanqiu.com/immigration/");

    }


    private void testCategory(String url) throws IOException {

        Document document = Jsoup.connect(url).get();
        Elements allLink = document.getElementsByTag("a");

        Elements shortTextLink = allLink
                .stream()
                .filter(element -> element.text().length() > 0)
                .collect(Collectors.toCollection(Elements::new));


        Map<Element, Set<Element>> elementSetMap = ElementUtil.ModuleFinder.aggModuleInElement(shortTextLink);
        Map<Element, Set<Element>> category = ElementUtil.PageLogic.filterCategory(elementSetMap);

        category.forEach((k, v) ->{
            logger.info("key = {}", k.tagName(), k.outerHtml());
            logger.info("value = {}", k.text());
        });
    }
}