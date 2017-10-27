package pl.mirror.black.blackmirror.network.rss.news;


import java.util.List;

import io.reactivex.Single;
import io.reactivex.functions.Function;
import pl.mirror.black.blackmirror.model.news.News;
import pl.mirror.black.blackmirror.model.news.Tvn24News;
import pl.mirror.black.blackmirror.network.NewsRepository;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class NewsDataSource implements NewsRepository {

    private Tvn24Rss tvn24Rss;

    public NewsDataSource() {
        tvn24Rss = new Retrofit.Builder()
                .baseUrl("https://www.tvn24.pl/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build()
                .create(Tvn24Rss.class);
    }

    @Override
    public Single<List<News>> getNews() {
        return tvn24Rss.getNews()
                .map(new Function<Tvn24News, List<News>>() {
                    @Override
                    public List<News> apply(Tvn24News tvn24News) throws Exception {
                        return tvn24News.channel.newsList;
                    }
                });
    }
}