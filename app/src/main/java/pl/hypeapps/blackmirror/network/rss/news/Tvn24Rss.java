package pl.hypeapps.blackmirror.network.rss.news;

import io.reactivex.Single;
import pl.hypeapps.blackmirror.model.news.Tvn24News;
import retrofit2.http.GET;

/**
 *  Interfejs reprezentujący połączenie z kanałem TVN.
 */
public interface Tvn24Rss {

    @GET("/najnowsze.xml")
    Single<Tvn24News> getNews();

}
