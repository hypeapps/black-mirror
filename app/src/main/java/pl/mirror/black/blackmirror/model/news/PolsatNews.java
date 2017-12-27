package pl.mirror.black.blackmirror.model.news;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "rss", strict = false)
public class PolsatNews {

    @Element(name = "channel")
    public Channel channel;

    @Attribute(required = false)
    private Double version;
}
