package pl.mirror.black.blackmirror.model.weather;

public class Sys {

    public String country;

    public Long sunrsie;

    public Long sunset;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Long getSunrsie() {
        return sunrsie;
    }

    public void setSunrsie(Long sunrsie) {
        this.sunrsie = sunrsie;
    }

    public Long getSunset() {
        return sunset;
    }

    public void setSunset(Long sunset) {
        this.sunset = sunset;
    }
}
