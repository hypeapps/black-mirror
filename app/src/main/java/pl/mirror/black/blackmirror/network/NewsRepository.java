package pl.mirror.black.blackmirror.network;

import java.util.List;

import io.reactivex.Single;
import pl.mirror.black.blackmirror.model.news.News;

public interface NewsRepository {

    Single<List<News>> getTvnNews();

    Single<List<News>> getPolsatNews();

}
