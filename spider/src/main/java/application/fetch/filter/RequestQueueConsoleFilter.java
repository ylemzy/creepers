package application.fetch.filter;

import application.elastic.document.News;
import application.fetch.*;
import util.JsonHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Created by huangzebin on 2017/3/7.
 */
public class RequestQueueConsoleFilter extends AbstractFilter{
    private static final Logger logger = LogManager.getLogger();

    Queue<Request> queue = new ConcurrentLinkedDeque<>();


    @Override
    public void filter(Request request) {
        super.filter(request);
        queue.add(request);
        pullRun();

    }

    public void pullRun(){
        while (!queue.isEmpty()){
            pollRequest();
        }
    }

    private void pollRequest(){
        Request request = queue.poll();
        if (request == null){
            return;
        }
        try {
            AbstractTemplate template = (AbstractTemplate) TemplateLoader.getTemplate(request.getWebId());
            Response response = template.process(request);
            if (!(response.getBody() instanceof News)){
                return;
            }

            News news = (News) response.getBody();
            logger.info(JsonHelper.toJSON(news));
        } catch (Exception e) {
            logger.error(e, e);
            super.errorFilter(request);
        }
    }
}
