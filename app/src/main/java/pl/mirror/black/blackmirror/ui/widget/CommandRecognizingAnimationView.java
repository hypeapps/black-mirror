package pl.mirror.black.blackmirror.ui.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.airbnb.lottie.LottieAnimationView;

import pl.mirror.black.blackmirror.R;

/**
 * Widok zajmujący sie zarządzaniem animacją.
 */
public class CommandRecognizingAnimationView extends FrameLayout {

    private LottieAnimationView animationView;

    private ValueAnimator valueAnimator;

    public CommandRecognizingAnimationView(Context context) {
        super(context);
        init(context);
    }

    public CommandRecognizingAnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CommandRecognizingAnimationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    void init(Context context) {
        inflate(context, R.layout.view_comand_recognizing_animation, this);
        animationView = (LottieAnimationView) findViewById(R.id.view_animation);
    }

    /**
     * Włącza animacje.
     */
    public void startAnimation() {
        valueAnimator = ValueAnimator.ofFloat(0f, 1f).setDuration(animationView.getDuration());
        valueAnimator.setCurrentPlayTime(2200L);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (animation.getCurrentPlayTime() >= 3200L && animation.getCurrentPlayTime() <= 3300L) {
                    animation.setCurrentFraction(0.266f);
                }
                animationView.setProgress(animation.getAnimatedFraction());
            }
        });
        valueAnimator.start();
    }

    /**
     * Zatrzymuje animacje.
     */
    public void stopAnimation() {
        valueAnimator.cancel();
        animationView.playAnimation(0.70f, 0.90f);
    }

}
