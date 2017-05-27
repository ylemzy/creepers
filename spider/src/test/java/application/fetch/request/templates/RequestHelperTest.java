package application.fetch.request.templates;

import application.fetch.request.Template;
import application.fetch.request.TemplateLoader;
import http.UrlMaker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.HashUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.springframework.util.Assert;

import java.net.MalformedURLException;
import java.util.*;

/**
 * Created by huangzebin on 2017/3/3.
 */
public class RequestHelperTest {

    private static final Logger logger = LogManager.getLogger();

    @Test
    public void testLoad(){
        Template healthQQ = TemplateLoader.getTemplate("HealthQQ");
    }

    @Test
    public void testUrl() throws MalformedURLException {
        //String url = "http://feed.mix.sina.com.cn/api/roll/get?pageid=39&lid=561&num=20&versionNumber=1.2.8&page=1&encode=utf-8&callback=feedCardJsonpCallback&_=1488792450494";

        String url = "http://www.baidu.com?p1=123";
        UrlMaker urlMaker = new UrlMaker(url).param("p1", "222");
        System.out.println(urlMaker.getRowUrl());
    }

    @Test
    public void testhash2(){
        logger.info(">>" + HashUtil.MD5("https://jingyan.baidu.com/article/ff41162582507512e5823763.html").hashCode());
    }

    @Test
    public void testHash(){

        for (int i = 0; i < 10; ++i){
            hashLoop(i);
        }

    }

    public void hashLoop(int th){

        List<Integer> res = new ArrayList<>();
        long l = System.currentTimeMillis();
        for (int i = 0; i < 10000; ++i){
            String strText = "" + (++l);
            //logger.info("Test={}", strText);
            res.add(HashUtil.MD5(strText).hashCode());
        }

        Set<Integer> set = new HashSet<>();
        res.forEach(i -> set.add(i));
        logger.info("Try th:{} A={}, B={}", th, res.size(), set.size());
        Assert.isTrue(res.size() == set.size());

    }


    @Test
    public void testSelenium(){
        System.setProperty ( "webdriver.firefox.bin" , "D:/exploreDriver/geckodriver.exe" );
        //System.setProperty ( "webdriver.chrome.driver" , "D:\\exploreDriver\\chromedriver.exe" );

/*        DesiredCapabilities capability=DesiredCapabilities.internetExplorer();
        capability.setCapability(
                InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);*/

  /*      System.setProperty("phantomjs.binary.path", "D:\\phantomjs\\bin\\phantomjs.exe");

        DesiredCapabilities desiredCapabilities = DesiredCapabilities.phantomjs();
        WebDriver driver = new PhantomJSDriver(desiredCapabilities);*/

        /*
*/

        WebDriver driver = new FirefoxDriver();
        driver.get("https://www.techempower.com/benchmarks/#section=data-r8&hw=ph&test=plaintext");


        // Find the text input element by its name
        WebElement element = driver.findElement(By.tagName("a"));
        logger.info("{}", element.getText());

        // Enter something to search for
 /*       element.sendKeys("Cheese!");

        // Now submit the form. WebDriver will find the form for us from the element
        element.submit();*/

        // Check the title of the page
      /*  System.out.println("Page title is: " + driver.getTitle());

        // Google's search is rendered dynamically with JavaScript.
        // Wait for the page to load, timeout after 10 seconds
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.getTitle().toLowerCase().startsWith("cheese!");
            }
        });

        // Should see: "cheese! - Google Search"
        System.out.println("Page title is: " + driver.getTitle());*/

        //Close the browser
        driver.quit();
    }
}