package pl.mirror.black.blackmirror.model.news;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "item", strict = false)
public class News {

    @Element(name = "title", required = false)
    public String title;

    @Element(name = "description")
    public String description;

}
