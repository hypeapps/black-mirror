package pl.mirror.black.blackmirror.model.weather;

import com.google.gson.annotations.SerializedName;

public class Main {

    public Double temp;

    public Double pressure;

    public Double humidity;

    @SerializedName("temp_min")
    public Double tempMin;

    @SerializedName("temp_max")
    public Double tempMax;

}
