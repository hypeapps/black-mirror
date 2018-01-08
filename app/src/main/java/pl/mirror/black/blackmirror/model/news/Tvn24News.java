package pl.mirror.black.blackmirror.model.news;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Model reprezentujący kanał wiadomości Tvn24News.
 */
@Root(name = "rss", strict = false)
public class Tvn24News {

    @Element(name = "channel")
    public Channel channel;

    @Attribute(required = false)
    private Double version;

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public Double getVersion() {
        return version;
    }

    public void setVersion(Double version) {
        this.version = version;
    }
}
