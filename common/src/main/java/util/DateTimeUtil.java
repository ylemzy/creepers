package util;/**
 * Created by huangzebin on 2017/4/7.
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateTimeUtil {
    private static final Logger logger = LogManager.getLogger();

    final static String dateRegx = "([0-9]{4})[-\\.年]([0-1]?[0-9]{1})[-\\.月]([0-3]?[0-9]{1})[日]?[ ]*(([0-2]?[0-9][:时])([0-6][0-9][:分]?)([0-6][0-9][秒]?)?)?";
    public static Pattern pattern = Pattern.compile(dateRegx);

    private static String[] dailyFormats = new String[]{
            "yyyy-MM-dd",              // 2013-09-19
            "yyyy-MM-dd HH:mm",           // 2013-09-19 14:22
            "yyyy-MM-dd HH:mm:ss",           // 2013-09-19 14:22:55
            "yyyy年MM月dd日 HH时mm分",
            "yyyy年MM月dd日 HH:mm",
            "yyyy年MM月dd日",
    };

    private static String[] formats = new String[] {

            "yyyy-MM-dd",              // 2013-09-19
            "yyyy-MM-dd HH:mm",           // 2013-09-19 14:22
            "yyyy年MM月dd日 HH时mm分",
            "yyyy年MM月dd日 HH:mm",
            "yyyy年MM月dd日",


          /*
            "yyyy-MM-dd HH:mm:ss",         // 2013-09-19 14:22:30
            "yyyy年MM月dd日 HH时mm分ss秒",
            "yyyy-MM-dd HH:mm:ss zzzz",       // 2013-09-19 14:22:30 中国标准时间
            "EEEE yyyy-MM-dd HH:mm:ss zzzz",    // 星期四 2013-09-19 14:22:30 中国标准时间

            "HH:mm",                // 14:22
            "h:mm a",                // 2:22 下午
            "HH:mm z",               // 14:22 CST
            "HH:mm Z",               // 14:22 +0800
            "HH:mm zzzz",              // 14:22 中国标准时间
            "HH:mm:ss",               // 14:22:30

            "yyyy-MM-dd HH:mm:ss.SSSZ",       // 2013-09-19 14:22:30.000+0800
            "yyyy-MM-dd'T'HH:mm:ss.SSSZ",      // 2013-09-19T14:22:30.000+0800
            "yyyy.MM.dd G 'at' HH:mm:ss z",     // 2013.09.19 公元 at 14:22:30 CST
            "K:mm a",                // 2:22 下午, CST
            "EEE, MMM d, ''yy",           // 星期四, 九月 19, '13
            "hh 'o''clock' a, zzzz",        // 02 o'clock 下午, 中国标准时间
            "yyyyy.MMMMM.dd GGG hh:mm aaa",     // 02013.九月.19 公元 02:22 下午
            "EEE, d MMM yyyy HH:mm:ss Z",      // 星期四, 19 九月 2013 14:22:30 +0800
            "yyMMddHHmmssZ",            // 130919142230+0800
            "yyyy-MM-dd'T'HH:mm:ss.SSSZ",      // 2013-09-19T14:22:30.000+0800
            "EEEE 'DATE('yyyy-MM-dd')' 'TIME('HH:mm:ss')' zzzz",    // 星期四 2013-09-19 14:22:30 中国标准时间*/
    };

    public static Date parse(String dateTime, String[] formats) {

        for (String format : formats) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            try {
                return sdf.parse(dateTime);
            } catch (ParseException e) {
            }

        }
        return null;
    }

    public static Date parseDaily(String dateTime){
        return parse(dateTime, dailyFormats);
    }


    public static boolean matchDate(String input){
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }


    public static String findDate(String input){
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()){
            return matcher.group();
        }
        return null;
    }
}
