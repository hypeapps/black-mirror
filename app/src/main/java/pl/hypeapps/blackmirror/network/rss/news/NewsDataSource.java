package pl.hypeapps.blackmirror.network.rss.news;


import java.util.List;

import io.reactivex.Single;
import io.reactivex.functions.Function;
import pl.hypeapps.blackmirror.model.news.News;
import pl.hypeapps.blackmirror.model.news.PolsatNews;
import pl.hypeapps.blackmirror.model.news.Tvn24News;
import pl.hypeapps.blackmirror.network.NewsRepository;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Klasa warstwy dostępu do danych wiadomości ze świata.
 */
public class NewsDataSource implements NewsRepository {

    private Tvn24Rss tvn24Rss;

    private PolsatNewsRss polsatNewsRss;

    public NewsDataSource() {
        tvn24Rss = new Retrofit.Builder()
                .baseUrl("https://www.tvn24.pl/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build()
                .create(Tvn24Rss.class);
        polsatNewsRss = new Retrofit.Builder()
                .baseUrl("http://www.polsatnews.pl/rss/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build()
                .create(PolsatNewsRss.class);
    }


    /**
     * @return Zwraca model wiadomości dla kanału TVN24.
     */
    @Override
    public Single<List<News>> getTvnNews() {
        return tvn24Rss.getNews()
                .map(new Function<Tvn24News, List<News>>() {
                    @Override
                    public List<News> apply(Tvn24News tvn24News) throws Exception {
                        return tvn24News.channel.news;
                    }
                });
    }

    /**
     * @return Zwraca model wiadomości dla kanału PolsatNews.
     */
    @Override
    public Single<List<News>> getPolsatNews() {
        return polsatNewsRss.getNews()
                .map(new Function<PolsatNews, List<News>>() {
                    @Override
                    public List<News> apply(PolsatNews polsatNews) throws Exception {
                        return polsatNews.channel.news;
                    }
                });
    }
}
