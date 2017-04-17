package application.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by huangzebin on 2017/4/17.
 */
public class DateTimeUtilTest {
    private static final Logger logger = LogManager.getLogger();

    @Test
    public void parse() throws Exception {

    }

    @Test
    public void parseDaily() throws Exception {

    }

    String[] dateTimes = new String[]{
            "2017年03月28日 06:30",
            "2017年03月28日",
            "2017-03-30 10:40",
            "2017-03-30 10:40:59",
            "2017-03-30",
            "2017年03月31日 06:30",

            "2017年03月31日 06时30分",
            "2017年03月31日 06时30分59秒",

    };

    @Test
    public void test() {
        for (String dateTime : dateTimes) {
            boolean b = DateTimeUtil.matchDate(dateTime);
            Assert.assertTrue(b);
        }
    }

    @Test
    public void testSpe() {
        boolean b = DateTimeUtil.matchDate("2017-02-17");
        Assert.assertTrue(b);

        String date = DateTimeUtil.findDate("[日期：2017-02-17]");
        logger.info("Find result:{}", date);
    }

    @Test
    public void testSpe2() {
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher("[aaa2223bb]");
        logger.info(m.find());
        /*logger.info(m.start());
        logger.info(m.end());*/
        logger.info(m.group());
/*        m.find();//匹配2223
        m.start();//返回3
        m.end();//返回7,返回的是2223后的索引号
        m.group();//返回2223*/
    }

    @Test
    public void ss(){
        String a = "b";
        String[] bs = a.split("b");
        System.out.println("...................... " + bs.length);
    }


}