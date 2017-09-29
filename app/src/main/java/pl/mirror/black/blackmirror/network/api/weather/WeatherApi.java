package pl.mirror.black.blackmirror.network.api.weather;

import io.reactivex.Single;
import pl.mirror.black.blackmirror.model.weather.WeatherResponse;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {

    @GET(value = "weather")
    Single<WeatherResponse> getWeatherByCityName(@Query("APPID") String apiKey,
                                                 @Query("q") String city,
                                                 @Query("units") String units,
                                                 @Query("lang") String lang);

}
