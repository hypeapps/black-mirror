package pl.hypeapps.blackmirror.network;


import io.reactivex.Single;
import pl.hypeapps.blackmirror.model.location.TimeZone;

/**
 * Interfejs służący do komunikacji pomiędzy warstwą
 * dostępu do danych lokalizacyjnych.
 */
public interface LocationRepository {

    Single<TimeZone> getTimeZoneByLocationName(String location);

}
