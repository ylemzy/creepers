package application.run;


import application.fetch.AbstractTemplate;
import application.fetch.Request;
import application.fetch.Response;
import application.fetch.TemplateLoader;
import application.service.WriteService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

@Service
public class RequestQueueRunner {
    private static final Logger logger = LogManager.getLogger();

    static RequestQueueRunner requestQueueRunner = null;

    public RequestQueueRunner(){
        requestQueueRunner = this;
    }

    public static RequestQueueRunner getQueue(){
        if (requestQueueRunner == null){
            requestQueueRunner = new RequestQueueRunner();
        }
        return requestQueueRunner;
    }

    @Autowired
    WriteService writeService;

    Queue<Request> queue = new ConcurrentLinkedDeque<>();

    public void addRequest(Request request) {
        queue.add(request);
    }

    @Async
    public void run() {
        while (true) {
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
            writeService.write(response);
        } catch (Exception e) {
            logger.error(e, e);
        }
    }


}
