package pl.hypeapps.blackmirror.network.api.location;

import io.reactivex.Single;
import pl.hypeapps.blackmirror.model.location.TimeZone;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Interfejs reprezentujący endpoint TimeZoneDbApi.
 */
public interface TimeZoneDbApi {

    @GET("get-time-zone?format=json&by=position")
    Single<TimeZone> getTimeZone(@Query("lat") String lat, @Query("lng") String lng, @Query("key") String apiKey);

}
