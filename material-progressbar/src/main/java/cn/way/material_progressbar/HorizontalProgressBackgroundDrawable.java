package cn.way.material_progressbar;

import android.content.Context;
import android.graphics.Canvas;

/**
 * <pre>
 *     author: Way Lin
 *     date  : 2018.02.03
 *     desc  :
 * </pre>
 */

class HorizontalProgressBackgroundDrawable extends BaseSingleHorizontalProgressDrawable
        implements ShowBackgroundDrawable {
    private boolean mShow = true;

    public HorizontalProgressBackgroundDrawable(Context context) {
        super(context);
    }

    @Override
    public boolean getShowBackground() {
        return mShow;
    }

    @Override
    public void setShowBackground(boolean show) {
        if (mShow != show) {
            mShow = show;
            invalidateSelf();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        if (mShow) {
            super.draw(canvas);
        }
    }
}
