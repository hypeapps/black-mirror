package pl.hypeapps.blackmirror.network.api.location;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import pl.hypeapps.blackmirror.BuildConfig;
import pl.hypeapps.blackmirror.model.location.CoordResponse;
import pl.hypeapps.blackmirror.model.location.TimeZone;
import pl.hypeapps.blackmirror.network.LocationRepository;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Klasa warstwy dostępu do danych lokalizacyjnych tj. strefa czasaowa,
 * długość geograficzna oraz szerokość geograficzna.
 */
public class LocationDataSource implements LocationRepository {

    private static final String TIME_ZONE_DB_API_KEY = BuildConfig.TIMEZONEDB_API_KEY;

    private static final String GOOGLE_GEO_API_KEY = BuildConfig.GOOGLE_GEO_API_KEY;

    private final GoogleGeoApi googleGeoApi;

    private final TimeZoneDbApi timeZoneDbApi;

    public LocationDataSource() {
        googleGeoApi = new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GoogleGeoApi.class);
        timeZoneDbApi = new Retrofit.Builder()
                .baseUrl("http://api.timezonedb.com/v2/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TimeZoneDbApi.class);
    }

    /**
     * Zwraca strefę czasową na podstawie podanej lokalizacji.
       @param location Lokalizacja - miasto, kraj, wieś.
     */
    @Override
    public Single<TimeZone> getTimeZoneByLocationName(String location) {
        return googleGeoApi.getCoordForLocation(location, GOOGLE_GEO_API_KEY)
                .flatMap(new Function<CoordResponse, SingleSource<? extends TimeZone>>() {
                    @Override
                    public SingleSource<? extends TimeZone> apply(@NonNull CoordResponse coordResponse) throws Exception {
                        String lat = coordResponse.results.get(0).geometry.location.lat.toString();
                        String lng = coordResponse.results.get(0).geometry.location.lng.toString();
                        return timeZoneDbApi.getTimeZone(lat, lng, TIME_ZONE_DB_API_KEY);
                    }
                });
    }
}
