package pl.hypeapps.blackmirror.ui.widget;

import android.animation.Animator;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.widget.TextClock;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import pl.hypeapps.blackmirror.R;

/**
 * Widok odpowiadający za widżet zegara.
 */
public class TimeWidgetView extends ConstraintLayout {

    private TextClock clock;

    private TextView date;

    private Context context;

    public TimeWidgetView(Context context) {
        super(context);
        init(context);
    }

    public TimeWidgetView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TimeWidgetView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        inflate(context, R.layout.view_time_widget, this);
        clock = (TextClock) findViewById(R.id.text_clock);
        date = (TextView) findViewById(R.id.date);
        setVisibility(INVISIBLE);
    }

    /**
     * Metoda pokazująca widżet dla podanej strefy czasowej.
     *
     * @param timezone strefa czasowa.
     */
    public void show(String timezone) {
        clock.setTimeZone(timezone);
        clock.setFormat24Hour(clock.getFormat24Hour());
        clock.setFormat12Hour(null);
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(timezone));
        String[] dayNames = new DateFormatSymbols(new Locale("pl")).getWeekdays();
        String[] monthNames = new DateFormatSymbols(new Locale("pl")).getMonths();
        date.setText(String.format(context.getString(R.string.date_format), dayNames[calendar.get(Calendar.DAY_OF_WEEK)],
                Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)),
                monthNames[calendar.get(Calendar.MONTH)]));
        YoYo.with(Techniques.ZoomIn)
                .onStart(new YoYo.AnimatorCallback() {
                    @Override
                    public void call(Animator animator) {
                        setVisibility(VISIBLE);
                    }
                })
                .playOn(this);
    }

    /**
     * Metoda ukrywająca widżet.
     */
    public void hide() {
        YoYo.with(Techniques.ZoomOut)
                .onEnd(new YoYo.AnimatorCallback() {
                    @Override
                    public void call(Animator animator) {
                        TimeWidgetView.this.setVisibility(INVISIBLE);
                    }
                }).playOn(this);
    }
}

