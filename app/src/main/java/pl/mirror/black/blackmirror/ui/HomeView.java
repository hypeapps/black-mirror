package pl.mirror.black.blackmirror.ui;

import java.util.List;

import pl.mirror.black.blackmirror.model.news.News;
import pl.mirror.black.blackmirror.model.weather.WeatherResponse;
import pl.mirror.black.blackmirror.ui.presenter.View;

public interface HomeView extends View {

    void showWeatherWidget(WeatherResponse weatherResponse);

    void showClockWidget(String timeZone);

    void showNewsWidget(List<News> newsList);

    void hideWeather();

    void showError(String message);

}
