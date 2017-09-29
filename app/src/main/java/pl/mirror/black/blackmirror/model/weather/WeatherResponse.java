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

}
