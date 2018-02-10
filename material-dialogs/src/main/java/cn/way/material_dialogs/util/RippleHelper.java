package cn.way.material_dialogs.util;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;

/**
 * <pre>
 *     author: Way Lin
 *     date  : 2018.02.03
 *     desc  :
 * </pre>
 */

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class RippleHelper {
    public static void applyColor(Drawable d, @ColorInt int color) {
        if (d instanceof RippleDrawable) {
            ((RippleDrawable) d).setColor(ColorStateList.valueOf(color));
        }
    }
}
