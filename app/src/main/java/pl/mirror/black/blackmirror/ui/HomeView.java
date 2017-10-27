package pl.mirror.black.blackmirror.ui;

import pl.mirror.black.blackmirror.model.weather.WeatherResponse;
import pl.mirror.black.blackmirror.ui.presenter.View;

public interface HomeView extends View {

    void showWeatherWidget(WeatherResponse weatherResponse);

    void showClockWidget(String timeZone);

    void showNewsWidget();

    void showError(String message);

}
