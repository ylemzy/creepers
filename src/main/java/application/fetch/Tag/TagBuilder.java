package application.fetch.Tag;

/**
 * Created by huangzebin on 2017/3/3.
 */
public class TagBuilder {

    public static Tag imgTag(){
        return new Tag(TagType.img);
    }

    public static Tag hdTag(){
        return new Tag(TagType.hd);
    }

    public static Tag textTag(){
        return new Tag(TagType.text);
    }

    public static Tag blankTag(){
        return new Tag(TagType.blank);
    }
}
