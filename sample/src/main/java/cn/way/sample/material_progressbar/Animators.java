package cn.way.sample.material_progressbar;

import android.animation.ValueAnimator;
import android.view.animation.LinearInterpolator;
import android.widget.ProgressBar;

/**
 * <pre>
 *     author: Way Lin
 *     date  : 2018.02.09
 *     desc  :
 * </pre>
 */

class Animators {
    private Animators() {
    }

    public static ValueAnimator makeDeterminateCircularPrimaryProgressAnimator(
            final ProgressBar[] progressBars) {
        ValueAnimator animator = ValueAnimator.ofInt(0, 150);
        animator.setDuration(6000);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.addUpdateListener(
                new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animator) {
                        int value = (int) animator.getAnimatedValue();
                        for (ProgressBar progressBar : progressBars) {
                            progressBar.setProgress(value);
                        }
                    }
                });
        return animator;
    }

    public static ValueAnimator makeDeterminateCircularPrimaryAndSecondaryProgressAnimator(
            final ProgressBar[] progressBars) {
        ValueAnimator animator = makeDeterminateCircularPrimaryProgressAnimator(progressBars);
        animator.addUpdateListener(
                new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animator) {
                        int value = Math.round(1.25f * (int) animator.getAnimatedValue());
                        for (ProgressBar progressBar : progressBars) {
                            progressBar.setSecondaryProgress(value);
                        }
                    }
                });
        return animator;
    }
}
