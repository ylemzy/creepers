package application.fetch.request.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by huangzebin on 2017/3/7.
 */
public class RequestFilterManager {

    private static final Logger logger = LogManager.getLogger();

    public enum FiltetType{
        CONSOLE,
        QUEUE_AS_CONSOLE,
        QUEUE_THREAD_AS_ES
    }

    private static RequestFilter queueFilter = new RequestQueueFilter();

    private static RequestFilter consoleFilter = new RequestConsoleFilter();

    private static RequestQueueConsoleFilter queueConsoleFilter = new RequestQueueConsoleFilter();

    private static RequestFilter defaultFilter;

    public static RequestFilter getConsoleFilter(){
        return consoleFilter;
    }

    public static RequestFilter getQueueFilter(){
        return queueFilter;
    }

    public static RequestQueueConsoleFilter getQueueConsoleFilter() {
        return queueConsoleFilter;
    }

    public static RequestFilter getDefaultFilter() {
        if (defaultFilter == null){
            logger.warn("Default filter is NULL");
            setDefaultFilter(FiltetType.QUEUE_THREAD_AS_ES);
        }
        return defaultFilter;
    }

    public static void setDefaultFilter(FiltetType type) {

        logger.info("Set default filter by type = {}", type);
        switch (type){
            case CONSOLE:
                RequestFilterManager.defaultFilter = getConsoleFilter();
                break;
            case QUEUE_AS_CONSOLE:
                RequestFilterManager.defaultFilter = getQueueConsoleFilter();
                break;
            case QUEUE_THREAD_AS_ES:
                RequestFilterManager.defaultFilter = getQueueFilter();
                break;
            default:
                RequestFilterManager.defaultFilter = getQueueFilter();
        }
    }
}
