package application.fetch.filter;

import application.fetch.Request;
import application.run.RequestQueueRunner;
import application.util.JsonHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by huangzebin on 2017/3/7.
 */
public class RequestQueueFilter extends AbstractFilter{

    private static final Logger logger = LogManager.getLogger();
    @Override
    public void filter(Request request) {
        super.filter(request);

        RequestQueueRunner queue = RequestQueueRunner.getQueue();
        if (queue != null){
            queue.addRequest(request);
        }else{
            logger.info("Add request : {}", JsonHelper.toJSON(request));
        }
    }
}
