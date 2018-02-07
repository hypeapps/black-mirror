package pl.hypeapps.blackmirror.network.api.weather;

import io.reactivex.Single;
import pl.hypeapps.blackmirror.model.weather.WeatherResponse;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Interfejs reprezentujący połączenie z WeatherApi.
 */
public interface WeatherApi {

    @GET(value = "weather")
    Single<WeatherResponse> getWeatherByCityName(@Query("APPID") String apiKey,
                                                 @Query("q") String city,
                                                 @Query("units") String units,
                                                 @Query("lang") String lang);

}
