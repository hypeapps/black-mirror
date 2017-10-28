package pl.mirror.black.blackmirror.ui;

import android.util.Log;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import pl.mirror.black.blackmirror.model.location.TimeZone;
import pl.mirror.black.blackmirror.model.news.News;
import pl.mirror.black.blackmirror.model.weather.WeatherResponse;
import pl.mirror.black.blackmirror.network.LocationRepository;
import pl.mirror.black.blackmirror.network.NewsRepository;
import pl.mirror.black.blackmirror.network.WeatherRepository;
import pl.mirror.black.blackmirror.network.api.location.LocationDataSource;
import pl.mirror.black.blackmirror.network.api.weather.WeatherDataSource;
import pl.mirror.black.blackmirror.network.rss.news.NewsDataSource;
import pl.mirror.black.blackmirror.speechrecognition.TextCommandInterpreter;
import pl.mirror.black.blackmirror.ui.presenter.Presenter;

class HomePresenter extends Presenter<HomeView> implements TextCommandInterpreter.Listener {

    private final WeatherRepository weatherDataSource = new WeatherDataSource();

    private final LocationRepository locationDataSource = new LocationDataSource();

    private final NewsRepository newsDataSource = new NewsDataSource();

    private TextCommandInterpreter textCommandInterpreter = new TextCommandInterpreter(this);

    private CompositeDisposable disposables = new CompositeDisposable();

    @Override
    protected void onAttachView(HomeView view) {
        super.onAttachView(view);

    }

    @Override
    protected void onDetachView() {
        super.onDetachView();
        if (!disposables.isDisposed()) {
            disposables.dispose();
        }
    }

    @Override
    public void onFailureCommandRecognizing() {
        this.view.showError("Niepoprawna komenda");
    }

    @Override
    public void onWeatherCommandRecognized(String location) {
        disposables.add(weatherDataSource.getWeatherByCityName(location, "metric", "pl")
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new WeatherResponseObserver()));
    }

    @Override
    public void onTimeCommandRecognized(String location) {
        disposables.add(locationDataSource.getTimeZoneByLocationName(location)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new TimeZoneObserver()));
    }

    @Override
    public void onHideWeatherWidget() {
        this.view.hideWeather();
    }

    @Override
    public void onNewsCommandRecognized() {
        disposables.add(newsDataSource.getNews()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new NewsObserver()));
    }

    void onSpeechRecognized(String result) {
        textCommandInterpreter.interpret(result);
    }

    /**
     * Obserwuje status wykonanego żądania restowego pogody.
     */
    private class WeatherResponseObserver extends DisposableSingleObserver<WeatherResponse> {
        @Override
        public void onSuccess(@NonNull WeatherResponse weatherResponse) {
            HomePresenter.this.view.showWeatherWidget(weatherResponse);
        }

        @Override
        public void onError(@NonNull Throwable e) {
            HomePresenter.this.view.showError("Nie znaleziono pogody");
        }
    }

    /**
     * Obserwuje status wykonanego żądania restowego czasu.
     */
    private class TimeZoneObserver extends DisposableSingleObserver<TimeZone> {
        @Override
        public void onSuccess(@NonNull TimeZone timeZone) {
            HomePresenter.this.view.showClockWidget(timeZone.timeZone);
        }

        @Override
        public void onError(@NonNull Throwable e) {
            HomePresenter.this.view.showError("Nie znaleziono czasu dla podanej strefy");
        }
    }

    /**
     * Obserwuje status wykonanego żądania rss wiadomości ze świata.
     */
    private class NewsObserver extends DisposableSingleObserver<List<News>> {
        @Override
        public void onSuccess(List<News> news) {
            HomePresenter.this.view.showNewsWidget(news);
        }

        @Override
        public void onError(Throwable e) {
            HomePresenter.this.view.showError("Nie udało się pobrać wiadomości");
        }
    }
}
