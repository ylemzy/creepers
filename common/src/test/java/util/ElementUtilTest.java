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
        //test("http://health.cnr.cn/");
        test("http://www.enorth.com.cn/");


    }

    private void test(String url) throws IOException {
        Document document = Jsoup.connect(url).get();
        Elements allLink = document.getElementsByTag("a");

        Elements shortTextLink = allLink
                .stream()
                .filter(element -> element.text().length() > 0 && element.text().length() < 6)
                .collect(Collectors.toCollection(Elements::new));

        Map<Element, Set<Element>> elementSetMap = ElementUtil.ModuleFinder.aggModuleInElement(shortTextLink);

        elementSetMap.forEach((k, v) ->{

            String text = "";
            for (Element item : v) {
                text += item.toString() + "\n";
            }

            logger.info("------------------------------------");
            logger.info("------------------------------------");
            logger.info("------------------------------------");
            logger.info("{}\n\n", k.toString());
            //logger.info("<<<<<<<<<<<<<{}>>>>>>>>>>>>>>", text);

        });
    }
}