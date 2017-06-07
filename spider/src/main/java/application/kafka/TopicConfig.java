package application.kafka;/**
 * Created by huangzebin on 2017/5/25.
 */

import application.fetch.UrlType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TopicConfig {
    private static final Logger logger = LogManager.getLogger();

    public static final String rawUrlTopic = "raw";

    public static final String categoryUrlTopic = "category";

    public static final String pageUrlTopic = "page";


    public static final String get(UrlType type) {
        switch (type) {
            case RAW:
                return rawUrlTopic;
            case CATEGORY:
            case HOST:
                return categoryUrlTopic;
            case PAGE:
                return pageUrlTopic;
            case TEST:
                return "test";
            default:
                return null;
        }

    }
}
