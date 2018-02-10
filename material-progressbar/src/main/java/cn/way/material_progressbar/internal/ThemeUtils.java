package cn.way.material_progressbar.internal;

import android.content.Context;
import android.content.res.TypedArray;

/**
 * <pre>
 *     author: Way Lin
 *     date  : 2018.02.03
 *     desc  :
 * </pre>
 */

public class ThemeUtils {
    private ThemeUtils() {
    }

    public static int getColorFromAttrRes(int attrRes, int defaultValue, Context context) {
        TypedArray a = context.obtainStyledAttributes(new int[]{attrRes});
        try {
            return a.getColor(0, defaultValue);
        } finally {
            a.recycle();
        }
    }

    public static float getFloatFromAttrRes(int attrRes, float defaultValue, Context context) {
        TypedArray a = context.obtainStyledAttributes(new int[]{attrRes});
        try {
            return a.getFloat(0, defaultValue);
        } finally {
            a.recycle();
        }
    }
}
