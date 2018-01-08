package pl.mirror.black.blackmirror.network;


import io.reactivex.Single;
import pl.mirror.black.blackmirror.model.location.TimeZone;

/**
 * Interfejs służący do komunikacji pomiędzy warstwą
 * dostępu do danych lokalizacyjnych.
 */
public interface LocationRepository {

    Single<TimeZone> getTimeZoneByLocationName(String location);

}
