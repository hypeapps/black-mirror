package pl.mirror.black.blackmirror.ui.widget;

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

import pl.mirror.black.blackmirror.R;

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
        clock.setFormat24Hour(clock.getFormat24Hour());
        date = (TextView) findViewById(R.id.date);
        setVisibility(INVISIBLE);
    }

    public void showWidget(String timezone) {
        clock.setTimeZone(timezone);
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

    public void hideWidget() {
        YoYo.with(Techniques.ZoomOut)
                .onEnd(new YoYo.AnimatorCallback() {
                    @Override
                    public void call(Animator animator) {
                        TimeWidgetView.this.setVisibility(INVISIBLE);
                    }
                }).playOn(this);
    }
}

