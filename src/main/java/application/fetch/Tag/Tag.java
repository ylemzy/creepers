package application.fetch.Tag;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huangzebin on 2017/3/3.
 */
public class Tag {
    private TagType type;
    private String content;
    private Map<String, Object> data;

    public Tag() {
    }

    public Tag(TagType type) {
        this.type = type;
    }

    public TagType getType() {
        return type;
    }

    public void setType(TagType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public Tag content(String content){
        this.content = content;
        return this;
    }

    public Tag putData(String key, Object value){
        if (this.data == null){
            this.data = new HashMap<>();
        }

        this.data.put(key, value);
        return this;
    }
}
