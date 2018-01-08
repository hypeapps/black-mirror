package pl.mirror.black.blackmirror.model.news;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Model reprezentujący kanał RSS wiadomości.
 */
@Root(name = "channel", strict = false)
public class Channel {

    @Element(name = "title", required = false)
    public String title;

    @Element(name = "description")
    public String description;

    @ElementList(name = "item", inline = true)
    public List<News> news;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<News> getNews() {
        return news;
    }

    public void setNews(List<News> news) {
        this.news = news;
    }
}
