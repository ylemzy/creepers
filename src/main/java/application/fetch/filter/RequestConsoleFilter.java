package application.fetch.filter;

import application.fetch.Request;
import application.uil.JsonHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by huangzebin on 2017/3/7.
 */
public class RequestConsoleFilter extends AbstractFilter{
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void filter(Request request) {
        super.filter(request);
        logger.info("Add request : {}", JsonHelper.toJSON(request));
    }
}
