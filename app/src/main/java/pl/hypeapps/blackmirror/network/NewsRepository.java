package pl.hypeapps.blackmirror.network;

import java.util.List;

import io.reactivex.Single;
import pl.hypeapps.blackmirror.model.news.News;

/**
 * Interfejs służący do komunikacji pomiędzy warstwą
 * dostępu do danych o wiadomościach ze świata.
 */
public interface NewsRepository {

    Single<List<News>> getTvnNews();

    Single<List<News>> getPolsatNews();

}
