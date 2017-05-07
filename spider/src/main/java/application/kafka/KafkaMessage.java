package application.kafka;/**
 * Created by huangzebin on 2017/4/26.
 */

import application.elastic.document.Url;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;

public class KafkaMessage {
    private static final Logger logger = LogManager.getLogger();
    private Long id;
    private String msg;
    private Date sendTime;
    private Url url;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Url getUrl() {
        return url;
    }

    public void setUrl(Url url) {
        this.url = url;
    }
}
