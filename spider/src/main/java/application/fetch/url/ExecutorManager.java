package application.fetch.url;/**
 * Created by huangzebin on 2017/5/8.
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

public class ExecutorManager {
    private static final Logger logger = LogManager.getLogger();

    static ExecutorService executorService =
            Executors.newFixedThreadPool(20, new ThreadFactory() {
                AtomicInteger atomic = new AtomicInteger();

                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r, "Thread " + atomic.getAndIncrement());
                }
            });


    public static ExecutorService getDiggerService(){
        return executorService;
    }


}
