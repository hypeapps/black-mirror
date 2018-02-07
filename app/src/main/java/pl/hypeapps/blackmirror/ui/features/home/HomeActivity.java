package pl.hypeapps.blackmirror.ui.features.home;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pl.hypeapps.blackmirror.R;
import pl.hypeapps.blackmirror.model.news.News;
import pl.hypeapps.blackmirror.model.weather.WeatherResponse;
import pl.hypeapps.blackmirror.speechrecognition.googlespeechapi.SpeechRecognizer;
import pl.hypeapps.blackmirror.speechrecognition.sphinx.ActivationKeywordListener;
import pl.hypeapps.blackmirror.speechrecognition.sphinx.PocketSphinx;
import pl.hypeapps.blackmirror.ui.base.BaseActivity;
import pl.hypeapps.blackmirror.ui.widget.CalendarWidgetView;
import pl.hypeapps.blackmirror.ui.widget.CommandRecognizingAnimationView;
import pl.hypeapps.blackmirror.ui.widget.NewsWidgetView;
import pl.hypeapps.blackmirror.ui.widget.TimeWidgetView;
import pl.hypeapps.blackmirror.ui.widget.WeatherWidgetView;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

/**
 * Klasa odpowiada za główny ekran aplikacji.
 */
public class HomeActivity extends BaseActivity implements HomeView,
        ActivationKeywordListener, SpeechRecognizer.Listener {

    /**
     * @return Podajemy refernecje do odpowiedniego widoku xml.
     */
    @Override
    protected Integer getLayoutRes() {
        return R.layout.activity_home;
    }

    public HomePresenter homePresenter = new HomePresenter();

    @BindView(R.id.activation_keyword_indicator)
    public TextView activationKeywordTextIndicator;

    @BindView(R.id.command_recognizing_animation)
    public CommandRecognizingAnimationView commandRecognizingAnimation;

    @BindView(R.id.weather_widget)
    public WeatherWidgetView weatherWidget;

    @BindView(R.id.time_widget)
    public TimeWidgetView timeWidget;

    @BindView(R.id.calendar_widget)
    public CalendarWidgetView calendarWidget;

    @BindView(R.id.news_widget)
    public NewsWidgetView newsWidgetView;

    @BindView(R.id.active_speech_indicator)
    public ImageView activeSpeechIndicator;

    @BindView(R.id.welcome_view)
    public TextView welcomeView;

    private static final String TAG = "HomeActivity";

    private PocketSphinx pocketSphinx;

    private SpeechRecognizer commandSpeechRecognizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pocketSphinx = new PocketSphinx(this, this);
        commandSpeechRecognizer = new SpeechRecognizer(this, this);
        homePresenter.onAttachView(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        commandSpeechRecognizer.onStart();
    }

    @Override
    protected void onStop() {
        commandSpeechRecognizer.onStop();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pocketSphinx.onDestroy();
    }

    /**
     * Zdarzenie, które oznjamia o gotowości nasłuchiwania słowa kluczowego.
     */
    @Override
    public void onActivationKeywordRecognizerReady() {
        Log.i("ACTIVATION PHRASE ", " ACTIVATION READY ");
        activationKeywordTextIndicator.setVisibility(View.VISIBLE);
        pocketSphinx.startListeningToActivationKeyword();
    }

    /**
     * Zdarzenie, które oznjamia o wykryciu słowa kluczowego.
     */
    @Override
    public void onActivationKeywordDetected() {
        Log.i("ACTIVATION PHRASE ", " DETECTED ");
        commandRecognizingAnimation.startAnimation();
        activationKeywordTextIndicator.setVisibility(INVISIBLE);
        activeSpeechIndicator.setVisibility(View.INVISIBLE);
        // Zacznij słuchać komendy.
        commandSpeechRecognizer.startListeningCommand();
        if (welcomeView.getVisibility() == View.VISIBLE) {
            YoYo.with(Techniques.Tada)
                    .onEnd(new YoYo.AnimatorCallback() {
                        @Override
                        public void call(Animator animator) {
                            welcomeView.setVisibility(View.INVISIBLE);
                        }
                    }).playOn(welcomeView);
        }
    }

    /**
     * Zadrzenie, które oznajmia o rozpoznaniu mowy.
     *
     * @param result w parametrze zwraca rezultat rozpoznawania komendy głosowej.
     */
    @Override
    public void onSpeechRecognized(final String result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Informujemy presenter o rozpoznaniu komendy głosowej.
                homePresenter.onSpeechRecognized(result);
            }
        });
    }

    /**
     * Zdarzenie, które oznjamia o zakończeniu rozpoznawania komendy głosowej.
     */
    @Override
    public void onFinishSpeechRecognizing() {
        // zaczynamy znowu słuchąć słowa kluczowego.
        pocketSphinx.startListeningToActivationKeyword();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activationKeywordTextIndicator.setVisibility(VISIBLE);
                commandRecognizingAnimation.stopAnimation();
            }
        });
    }

    /**
     * Zdarzenie, które oznjamia o wykryciu mowy podczas nasłuchiwania słowa kluczowego.
     */
    @Override
    public void onActivationKeywordBeginningOfSpeech() {
        activeSpeechIndicator.setVisibility(View.VISIBLE);
    }

    /**
     * Zdarzenie, które oznjamia o zakończenia słuchania mowy podczas nasłuchiwania słowa kluczowego.
     */
    @Override
    public void onActivationKeywordEndOfSpeech() {
        activeSpeechIndicator.setVisibility(View.INVISIBLE);
    }

    /**
     * Pokazuje widżet pogody.
     */
    @Override
    public void showWeatherWidget(WeatherResponse weatherResponse) {
        weatherWidget.show(weatherResponse);
    }

    /**
     * Ukrywa widżet pogody.
     */
    @Override
    public void hideWeatherWidget() {
        weatherWidget.hide();
    }

    /**
     * Pokazuje widżet czasu.
     */
    @Override
    public void showTimeWidget(String timeZone) {
        timeWidget.show(timeZone);
    }

    /**
     * Ukrywa widżet czasu.
     */
    @Override
    public void hideTimeWidget() {
        timeWidget.hide();
    }

    /**
     * Pokazuje widżet z wiadomościami Tvn.
     */
    @Override
    public void showTvnNewsWidget(List<News> newsList) {
        newsWidgetView.setTvnNews(newsList);
    }

    /**
     * Pokazuje widżet z wiadomościami Polsatu.
     */
    @Override
    public void showPolsatNewsWidget(List<News> news) {
        newsWidgetView.setPolsatNews(news);
    }

    /**
     * Ukrywa wszystkie wiadomości.
     */
    @Override
    public void hideAllNewsWidget() {
        newsWidgetView.hide();
    }

    /**
     * Pokazuje widżet kalendarza.
     */
    @Override
    public void showCalendarWidget() {
        calendarWidget.show();
    }

    /**
     * Ukrywa widżet kalendarza.
     */
    @Override
    public void hideCalendarWidget() {
        calendarWidget.hide();
    }

    /**
     * Zmienia miesiąć kalendarza na kolejny.
     */
    @Override
    public void setCalendarNextMonth() {
        calendarWidget.nextMonth();
    }

    /**
     * Zmienia miesiąć kalendarza na poprzedni.
     */
    @Override
    public void setCalendarPreviousMonth() {
        calendarWidget.previousMonth();
    }

    /**
     * Wyświetla powiadomienie o błędzie.
     *
     * @param message w parametrze zwraca wiadomość błędu.
     */
    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    /**
     * Zamyka okno aplikacji.
     */
    @OnClick(R.id.exit)
    public void exit() {
        this.finish();
    }

    /**
     * Uruchamia ponownie aplikację.
     */
    @OnClick(R.id.restart)
    public void restart() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    /**
     * Uruchamia ponownie aplikację.
     */
    @Override
    public void startSplashScreen() {
        welcomeView.setBackground(ContextCompat.getDrawable(this, R.drawable.splash_screen));
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, 1f)
                .setDuration(3000);
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                welcomeView.setBackground(null);
                welcomeView.setText("Witaj!");
                YoYo.with(Techniques.ZoomIn).playOn(welcomeView);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        valueAnimator.start();
    }
}
