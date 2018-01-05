package pl.mirror.black.blackmirror.network.rss.news;

import io.reactivex.Single;
import pl.mirror.black.blackmirror.model.news.PolsatNews;
import retrofit2.http.GET;

public interface PolsatNewsRss {

    @GET("wszystkie.xml")
    Single<PolsatNews> getNews();
}
