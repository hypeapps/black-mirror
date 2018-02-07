package pl.hypeapps.blackmirror.ui.widget;

import android.animation.Animator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import pl.hypeapps.blackmirror.R;
import pl.hypeapps.blackmirror.model.weather.WeatherResponse;

/**
 * Widok odpowiadający za widżet poogody.
 */
public class WeatherWidgetView extends ConstraintLayout {

    private TextView actualTemp;

    private ImageView icon;

    private TextView windSpeed;

    private TextView pressure;

    private TextView humidity;

    private TextView description;

    private TextView city;

    private String packageName;

    public WeatherWidgetView(Context context) {
        super(context);
        init(context);
    }

    public WeatherWidgetView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public WeatherWidgetView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.view_weather_widget, this);
        icon = (ImageView) findViewById(R.id.weather_icon);
        city = (TextView) findViewById(R.id.weather_city);
        pressure = (TextView) findViewById(R.id.weather_pressure);
        humidity = (TextView) findViewById(R.id.weather_humidity);
        windSpeed = (TextView) findViewById(R.id.weather_wind_speed);
        description = (TextView) findViewById(R.id.weather_description);
        packageName = context.getPackageName();
        actualTemp = (TextView) findViewById(R.id.weather_actual_temp);
        setVisibility(INVISIBLE);
    }

    /**
     * Wypełnia widok modelem podanym w projekcie oraz wykonuje
     * animację pokazania widżetu.
     *
     * @param weatherResponse model reprezentujący dane pogody.
     */
    public void show(WeatherResponse weatherResponse) {
        fillWeatherInfo(weatherResponse);
        if (getVisibility() == INVISIBLE) {
            YoYo.with(Techniques.ZoomIn).onStart(new YoYo.AnimatorCallback() {
                @Override
                public void call(Animator animator) {
                    setVisibility(VISIBLE);
                }
            }).playOn(this);
        } else {
            YoYo.with(Techniques.FadeIn).playOn(this);
        }
    }

    /**
     * Metoda wykonująca pokazanie widżetu z animacją.
     */
    public void hide() {
        YoYo.with(Techniques.ZoomOut).onStart(new YoYo.AnimatorCallback() {
            @Override
            public void call(Animator animator) {
                setVisibility(INVISIBLE);
            }
        })
                .duration(800)
                .playOn(this);
    }

    private void fillWeatherInfo(WeatherResponse weatherResponse) {
        icon.setImageDrawable(getIconDrawable(weatherResponse.getWeather().get(0).getIcon()));
        city.setText(weatherResponse.getCityName());
        actualTemp.setText(weatherResponse.getMain().getTemp().toString() + (char) 0x00B0);
        description.setText(weatherResponse.getWeather().get(0).getDescription());
        pressure.setText(weatherResponse.getMain().getPressure().intValue() + " hPa");
        humidity.setText(weatherResponse.getMain().getHumidity().intValue() + " %");
        windSpeed.setText(weatherResponse.getWind().getSpeed().intValue() + " km/h");
    }

    private Drawable getIconDrawable(String icon) {
        int id = getResources().getIdentifier("w" + icon, "drawable", packageName);
        return getResources().getDrawable(id);
    }

}
