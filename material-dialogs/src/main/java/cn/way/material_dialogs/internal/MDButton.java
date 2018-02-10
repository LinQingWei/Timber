package cn.way.material_dialogs.internal;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

import cn.way.material_dialogs.GravityEnum;
import cn.way.material_dialogs.R;
import cn.way.material_dialogs.util.DialogUtils;

/**
 * <pre>
 *     author: Way Lin
 *     date  : 2018.02.03
 *     desc  :
 * </pre>
 */

@SuppressWarnings("AppCompatCustomView")
public class MDButton extends TextView {
    private boolean mStacked;
    private GravityEnum mStackedGravity;

    private int mStackedEndPadding;
    private Drawable mStackedBackground;
    private Drawable mDefaultBackground;

    public MDButton(Context context) {
        this(context, null);
    }

    public MDButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MDButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mStackedEndPadding = context.getResources().getDimensionPixelSize(R.dimen.md_dialog_frame_margin);
        mStackedGravity = GravityEnum.END;
    }

    /**
     * Set if the button should be displayed in stacked mode. This should only be called from
     * MDRootLayout's onMeasure, and we must be measured after calling this.
     */
  /* package */ void setStacked(boolean stacked, boolean force) {
        if (this.mStacked != stacked || force) {

            setGravity(
                    stacked ? (Gravity.CENTER_VERTICAL | mStackedGravity.getGravityInt()) : Gravity.CENTER);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                //noinspection ResourceType
                setTextAlignment(stacked ? mStackedGravity.getTextAlignment() : TEXT_ALIGNMENT_CENTER);
            }

            DialogUtils.setBackgroundCompat(this, stacked ? mStackedBackground : mDefaultBackground);
            if (stacked) {
                setPadding(mStackedEndPadding, getPaddingTop(), mStackedEndPadding, getPaddingBottom());
            } /* Else the padding was properly reset by the drawable */

            this.mStacked = stacked;
        }
    }

    public void setStackedGravity(GravityEnum stackedGravity) {
        mStackedGravity = stackedGravity;
    }

    public void setStackedBackground(Drawable stackedBackground) {
        mStackedBackground = stackedBackground;
        if (mStacked) {
            setStacked(true, true);
        }
    }

    public void setDefaultBackground(Drawable defaultBackground) {
        mDefaultBackground = defaultBackground;
        if (!mStacked) {
            setStacked(false, true);
        }
    }

    public void setAllCapsCompat(boolean allCaps) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            setAllCaps(allCaps);
        } else {
            if (allCaps) {
                setTransformationMethod(new AllCapsTransformationMethod(getContext()));
            } else {
                setTransformationMethod(null);
            }
        }
    }
}
