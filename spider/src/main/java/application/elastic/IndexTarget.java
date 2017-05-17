package application.elastic;/**
 * Created by huangzebin on 2017/5/11.
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class IndexTarget {
    private static final Logger logger = LogManager.getLogger();

    private static final String monthPattern = "yyyy-MM";

    private static final String dayPattern = "yyyy-MM-dd";

    private static final SimpleDateFormat monthFormat = new SimpleDateFormat(monthPattern);

    private static final SimpleDateFormat dayFormat = new SimpleDateFormat(dayPattern);

    enum TargetType{
        MONTH,
        DAY
    }

    public static String timeIndex(String index, long millisTime, TargetType type){

        String pre = index + "-";
        switch (type){
            case MONTH:
                return pre + monthFormat.format(millisTime);
            case DAY:
                return pre + dayFormat.format(millisTime);
            default:
                return index;
        }
    }


}
