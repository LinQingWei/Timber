package cn.way.sample.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import cn.way.sample.util.AppUtils;

/**
 * <pre>
 *     author: Way Lin
 *     date  : 2018.02.07
 *     desc  :
 * </pre>
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getContentLayout());
        invalidateActionBar();
        initContentView();
    }

    protected abstract int getContentLayout();

    protected abstract void initContentView();

    /**
     * Receives and handles action bar invalidation request from sub-components of this activity.
     */
    public void invalidateActionBar() {
        updateActionBar(getSupportActionBar());
    }

    protected void updateActionBar(final ActionBar actionBar) {
        actionBar.setHomeAsUpIndicator(null);
    }

    protected void onActionBarHome() {
        AppUtils.navigateUp(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onActionBarHome();
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
