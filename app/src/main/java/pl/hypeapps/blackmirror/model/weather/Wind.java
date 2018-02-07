package pl.hypeapps.blackmirror.model.weather;

/**
 * Model reprezentujący dane o wietrze.
 */
public class Wind {

    public Double speed;

    public Double deg;

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Double getDeg() {
        return deg;
    }

    public void setDeg(Double deg) {
        this.deg = deg;
    }
}
