package pl.mirror.black.blackmirror.network.api;

import io.reactivex.Single;
import pl.mirror.black.blackmirror.model.weather.WeatherResponse;

public interface WeatherRepository {

    Single<WeatherResponse> getWeatherByCityName(String cityName, String units, String lang);

}
