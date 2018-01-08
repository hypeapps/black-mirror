package pl.mirror.black.blackmirror.model.location;


import com.google.gson.annotations.SerializedName;

/**
 * Model reprezentujący dane o strefie czasowej.
 */
public class TimeZone {

    @SerializedName("zoneName")
    public String timeZone;

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }
}
