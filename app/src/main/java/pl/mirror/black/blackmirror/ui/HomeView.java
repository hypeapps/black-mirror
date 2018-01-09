package pl.mirror.black.blackmirror.ui;

import java.util.List;

import pl.mirror.black.blackmirror.model.news.News;
import pl.mirror.black.blackmirror.model.weather.WeatherResponse;
import pl.mirror.black.blackmirror.ui.presenter.View;

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
