package pl.mirror.black.blackmirror.model.weather;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherResponse {

    public List<Weather> weather;

    public Main main;

    public Sys sys;

    public Wind wind;

    @SerializedName("name")
    public String cityName;

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
