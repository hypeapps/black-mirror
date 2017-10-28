package pl.mirror.black.blackmirror.model.news;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "item", strict = false)
public class News {

    @Element(name = "title", required = false)
    public String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description.replaceAll("<.*?>","")
                .trim();
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Element(name = "description")
    public String description;

}
