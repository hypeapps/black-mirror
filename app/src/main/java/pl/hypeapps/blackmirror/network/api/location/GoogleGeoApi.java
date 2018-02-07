package pl.hypeapps.blackmirror.network.api.location;


import io.reactivex.Single;
import pl.hypeapps.blackmirror.model.location.CoordResponse;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Interfejs reprezentujący endpoint GoogleGeoApi.
 */
public interface GoogleGeoApi {

    /**
     * Zwraca koordynaty geograficzne na podstawie podanej lokalizacji.
     *
     * @param location Lokalizacja - miasto, kraj, wieś.
     */
    @GET("maps/api/geocode/json")
    Single<CoordResponse> getCoordForLocation(@Query("address") String location, @Query("key") String apiKey);

}
