package pl.mirror.black.blackmirror.ui.widget;

import android.animation.Animator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import pl.mirror.black.blackmirror.R;
import pl.mirror.black.blackmirror.model.weather.WeatherResponse;

public class WeatherWidgetView extends ConstraintLayout {

    private TextView actualTemp;

    private ImageView icon;

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
        packageName = context.getPackageName();
        actualTemp = (TextView) findViewById(R.id.weather_actual_temp);
        setVisibility(INVISIBLE);
    }

    public void show(WeatherResponse weatherResponse) {
        fillWeatherInfo(weatherResponse);
        if (getVisibility() == INVISIBLE) {
            Log.e("animation", "ani");
            YoYo.with(Techniques.ZoomIn).onStart(new YoYo.AnimatorCallback() {
                @Override
                public void call(Animator animator) {
                    setVisibility(VISIBLE);
                }
            }).playOn(this);
        } else {
            Log.e("animation2", "ani");

            YoYo.with(Techniques.FadeIn).playOn(this);
        }
    }

    public void hide() {
        // TODO
    }

    private void fillWeatherInfo(WeatherResponse weatherResponse) {
        icon.setImageDrawable(getIconDrawable(weatherResponse.weather.get(0).icon));
        city.setText(weatherResponse.cityName);
        actualTemp.setText(weatherResponse.main.temp.toString() + (char) 0x00B0);
    }

    private Drawable getIconDrawable(String icon) {
        int id = getResources().getIdentifier("w" + icon, "drawable", packageName);
        return getResources().getDrawable(id);
    }

}