package pl.mirror.black.blackmirror.model.news;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "channel", strict = false)
public class Channel {

    @Element(name = "title", required = false)
    public String title;

    @Element(name = "description")
    public String description;

    @ElementList(name = "item", inline = true)
    public List<News> news;

}
