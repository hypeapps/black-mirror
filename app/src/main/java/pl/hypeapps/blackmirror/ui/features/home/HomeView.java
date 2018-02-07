package pl.hypeapps.blackmirror.ui.features.home;

import java.util.List;

import pl.hypeapps.blackmirror.model.news.News;
import pl.hypeapps.blackmirror.model.weather.WeatherResponse;
import pl.hypeapps.blackmirror.ui.presenter.View;

/**
 * Interfejs precyzujący kontrakt pomiędzy prezenterem, a widokiem.
 */
public interface HomeView extends View {

    void startSplashScreen();

    void showWeatherWidget(WeatherResponse weatherResponse);

    void hideWeatherWidget();

    void showTimeWidget(String timeZone);

    void hideTimeWidget();

    void showTvnNewsWidget(List<News> newsList);

    void showPolsatNewsWidget(List<News> news);

    void setCalendarNextMonth();

    void setCalendarPreviousMonth();

    void hideAllNewsWidget();

    void showCalendarWidget();

    void hideCalendarWidget();

    void showError(String message);
}
