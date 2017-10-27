package pl.mirror.black.blackmirror.network;


import io.reactivex.Single;
import pl.mirror.black.blackmirror.model.location.TimeZone;

public interface LocationRepository {

    Single<TimeZone> getTimeZoneByLocationName(String location);

}
