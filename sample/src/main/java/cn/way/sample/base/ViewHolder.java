package cn.way.sample.base;

import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Owner: way
 * 18-9-20
 */
public class ViewHolder {
    private SparseArray<View> mViews;
    private View mConvertView;

    public ViewHolder(View convertView) {
        mConvertView = convertView;
        mViews = new SparseArray<>();
    }

    public <T extends View> T getView(int viewId) {
        T view = (T) mViews.get(viewId);
        if (view == null) {
            view = (T) mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }

        return view;
    }

    public View getConvertView() {
        return mConvertView;
    }

    public void setText(int viewId, String text) {
        View childView = getView(viewId);
        if (childView instanceof TextView) {
            ((TextView) childView).setText(text);
        }
    }

    public void setDrawable(int viewId, Drawable drawable) {
        View childView = getView(viewId);
        if (childView instanceof ImageView) {
            ((ImageView) childView).setImageDrawable(drawable);
        }
    }
}
