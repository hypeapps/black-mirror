package pl.hypeapps.blackmirror.network;

import io.reactivex.Single;
import pl.hypeapps.blackmirror.model.weather.WeatherResponse;

/**
 * Interfejs służący do komunikacji pomiędzy warstwą
 * dostępu do informacji pogodowych.
 */
public interface WeatherRepository {

    Single<WeatherResponse> getWeatherByCityName(String cityName, String units, String lang);

}
