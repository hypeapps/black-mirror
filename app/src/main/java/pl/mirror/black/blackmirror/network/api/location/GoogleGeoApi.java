package pl.mirror.black.blackmirror.network.api.location;


import io.reactivex.Single;
import pl.mirror.black.blackmirror.model.location.CoordResponse;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GoogleGeoApi {

    @GET("maps/api/geocode/json")
    Single<CoordResponse> getCoordForLocation(@Query("address") String location, @Query("key") String apiKey);

}
