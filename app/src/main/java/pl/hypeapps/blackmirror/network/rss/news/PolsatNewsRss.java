package pl.hypeapps.blackmirror.network.rss.news;

import io.reactivex.Single;
import pl.hypeapps.blackmirror.model.news.PolsatNews;
import retrofit2.http.GET;

/**
 * Interfejs reprezentujący połączenie z kanałem PolsatNews.
 */
public interface PolsatNewsRss {

    @GET("wszystkie.xml")
    Single<PolsatNews> getNews();
}
