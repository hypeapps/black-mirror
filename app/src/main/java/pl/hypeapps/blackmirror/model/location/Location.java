package pl.hypeapps.blackmirror.model.location;


/**
 * Model reprezentujący lokalizacje.
 */
public class Location {

    public Double lat;

    public Double lng;

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
}
