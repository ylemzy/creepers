package application.kafka;

import application.fetch.UrlFlow;

/**
 * Created by huangzebin on 2017/5/25.
 */

public interface UrlConsumer {
    void process(UrlFlow flow, String content);
}
