package cn.way.material_progressbar;

import android.content.Context;
import android.graphics.drawable.Drawable;

/**
 * <pre>
 *     author: Way Lin
 *     date  : 2018.02.03
 *     desc  : A new {@code Drawable} for determinate circular {@code ProgressBar}.
 * </pre>
 */

public class CircularProgressDrawable extends BaseProgressLayerDrawable<
        SingleCircularProgressDrawable, CircularProgressBackgroundDrawable> {

    /**
     * Create a new {@code CircularProgressDrawable}.
     *
     * @param context the {@code Context} for retrieving style information.
     */
    public CircularProgressDrawable(int style, Context context) {
        super(new Drawable[]{
                new CircularProgressBackgroundDrawable(),
                new SingleCircularProgressDrawable(style),
                new SingleCircularProgressDrawable(style),
        }, context);
    }
}
