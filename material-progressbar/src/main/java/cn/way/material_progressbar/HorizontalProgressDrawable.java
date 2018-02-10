package cn.way.material_progressbar;

import android.content.Context;
import android.graphics.drawable.Drawable;

/**
 * <pre>
 *     author: Way Lin
 *     date  : 2018.02.03
 *     desc  : A backported {@code Drawable} for determinate horizontal {@code ProgressBar}.
 * </pre>
 */

public class HorizontalProgressDrawable extends BaseProgressLayerDrawable<
        SingleHorizontalProgressDrawable, HorizontalProgressBackgroundDrawable> {

    /**
     * Create a new {@code HorizontalProgressDrawable}.
     *
     * @param context the {@code Context} for retrieving style information.
     */
    public HorizontalProgressDrawable(Context context) {
        super(new Drawable[]{
                new HorizontalProgressBackgroundDrawable(context),
                new SingleHorizontalProgressDrawable(context),
                new SingleHorizontalProgressDrawable(context)
        }, context);
    }
}
