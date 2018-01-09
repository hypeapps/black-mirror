package pl.mirror.black.blackmirror.ui.widget;

import android.animation.Animator;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;

import java.util.Locale;
import java.util.TimeZone;

import pl.mirror.black.blackmirror.R;

/**
 * Widok odpowiadający za widżet kalendarza.
 */
public class CalendarWidgetView extends ConstraintLayout {

    private CompactCalendarView calendarView;

    public CalendarWidgetView(Context context) {
        super(context);
        init();
    }

    public CalendarWidgetView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CalendarWidgetView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_calendar_widget, this);
        calendarView = (CompactCalendarView) findViewById(R.id.calendar_view);
        this.setVisibility(INVISIBLE);
        calendarView.setLocale(TimeZone.getDefault(), new Locale("pl"));
        calendarView.setUseThreeLetterAbbreviation(true);

    }

    /**
     * Pokazuje kalendarz z animacją.
     */
    public void show() {
        this.setVisibility(VISIBLE);
        YoYo.with(Techniques.SlideInRight)
                .onStart(new YoYo.AnimatorCallback() {
                    @Override
                    public void call(Animator animator) {
                        CalendarWidgetView.this.setVisibility(VISIBLE);
                    }
                })
                .playOn(this);
    }

    /**
     * Ukrywa kalendarz z animacją.
     */
    public void hide() {
        YoYo.with(Techniques.SlideOutRight).playOn(this);
    }

    /**
     * Zmienia miesiąc na kolejny.
     */
    public void nextMonth() {
        calendarView.showNextMonth();
    }

    /**
     * Zmienia miesiąć na poprzedni.
     */
    public void previousMonth() {
        calendarView.showPreviousMonth();
    }

}
