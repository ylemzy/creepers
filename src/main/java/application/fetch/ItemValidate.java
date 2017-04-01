package application.fetch;/**
 * Created by huangzebin on 2017/3/21.
 */

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.Assert;

public class ItemValidate {
    private static final Logger logger = LogManager.getLogger();

    public static News valid(News news){

        if (news == null)
            return null;

        Assert.isTrue(StringUtils.isNotEmpty(news.getTitle()));

        return news;
    }
}
