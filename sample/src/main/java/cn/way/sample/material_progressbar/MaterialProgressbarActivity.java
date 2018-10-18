package cn.way.sample.material_progressbar;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import cn.way.sample.base.BaseActivity;
import cn.way.sample.R;
import cn.way.sample.util.AppUtils;


/**
 * <pre>
 *     author: Way Lin
 *     date  : 2018.02.09
 *     desc  :
 * </pre>
 */

public class MaterialProgressbarActivity extends BaseActivity {
    private ProgressBar[] mDeterminateCircularProgressBars;
    private ValueAnimator mDeterminateCircularProgressAnimator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDeterminateCircularProgressAnimator =
                Animators.makeDeterminateCircularPrimaryProgressAnimator(
                        mDeterminateCircularProgressBars);
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_material_progressbar;
    }

    @Override
    protected void initContentView() {
        ProgressBar largePb = findViewById(R.id.determinate_circular_large_progress);
        ProgressBar pb = findViewById(R.id.determinate_circular_progress);
        ProgressBar smallPb = findViewById(R.id.determinate_circular_small_progress);

        mDeterminateCircularProgressBars = new ProgressBar[]{
                largePb, pb, smallPb,
        };
    }

    @Override
    protected void updateActionBar(ActionBar actionBar) {
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.show();
        super.updateActionBar(actionBar);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

        mDeterminateCircularProgressAnimator.start();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        mDeterminateCircularProgressAnimator.end();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_material_progressbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_determinate_circular_sample:
                AppUtils.launch(this, DeterminateCircularSampleActivity.class);
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
