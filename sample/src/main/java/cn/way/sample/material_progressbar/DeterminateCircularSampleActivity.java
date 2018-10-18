package cn.way.sample.material_progressbar;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ProgressBar;

import cn.way.sample.base.BaseActivity;
import cn.way.sample.R;

/**
 * <pre>
 *     author: Way Lin
 *     date  : 2018.02.10
 *     desc  :
 * </pre>
 */

public class DeterminateCircularSampleActivity extends BaseActivity {
    ProgressBar[] mPrimaryProgressBars;
    ProgressBar[] mPrimaryAndSecondaryProgressBars;

    private ValueAnimator mPrimaryProgressAnimator;
    private ValueAnimator mPrimaryAndSecondaryProgressAnimator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPrimaryProgressAnimator =
                Animators.makeDeterminateCircularPrimaryProgressAnimator(mPrimaryProgressBars);
        mPrimaryAndSecondaryProgressAnimator =
                Animators.makeDeterminateCircularPrimaryAndSecondaryProgressAnimator(
                        mPrimaryAndSecondaryProgressBars);
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_determinate_circular_sample;
    }

    @Override
    protected void initContentView() {
        ProgressBar normal_progress = findViewById(R.id.normal_progress);
        ProgressBar tinted_normal_progress = findViewById(R.id.tinted_normal_progress);
        ProgressBar dynamic_progress = findViewById(R.id.dynamic_progress);
        ProgressBar tinted_dynamic_progress = findViewById(R.id.tinted_dynamic_progress);

        mPrimaryProgressBars = new ProgressBar[]{
                normal_progress, tinted_normal_progress, dynamic_progress, tinted_dynamic_progress,
        };

        ProgressBar normal_secondary_progress = findViewById(R.id.normal_secondary_progress);
        ProgressBar normal_background_progress = findViewById(R.id.normal_background_progress);
        ProgressBar tinted_normal_secondary_progress = findViewById(R.id.tinted_normal_secondary_progress);
        ProgressBar tinted_normal_background_progress = findViewById(R.id.tinted_normal_background_progress);
        ProgressBar dynamic_secondary_progress = findViewById(R.id.dynamic_secondary_progress);
        ProgressBar dynamic_background_progress = findViewById(R.id.dynamic_background_progress);
        ProgressBar tinted_dynamic_secondary_progress = findViewById(R.id.tinted_dynamic_secondary_progress);
        ProgressBar tinted_dynamic_background_progress = findViewById(R.id.tinted_dynamic_background_progress);

        mPrimaryAndSecondaryProgressBars = new ProgressBar[]{
                normal_secondary_progress,
                normal_background_progress,
                tinted_normal_secondary_progress,
                tinted_normal_background_progress,
                dynamic_secondary_progress,
                dynamic_background_progress,
                tinted_dynamic_secondary_progress,
                tinted_dynamic_background_progress,
        };
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

        mPrimaryProgressAnimator.start();
        mPrimaryAndSecondaryProgressAnimator.start();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        mPrimaryProgressAnimator.end();
        mPrimaryAndSecondaryProgressAnimator.end();
    }
}
