package application.elastic.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Map;

/**
 * Created by huangzebin on 2017/3/8.
 */
@Document(indexName = "rule-product", type = "1")
public class RuleProduct {
    @Id
    String id;

    String name;

    Map rule;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map getRule() {
        return rule;
    }

    public void setRule(Map rule) {
        this.rule = rule;
    }
}
