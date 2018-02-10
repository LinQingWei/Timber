package cn.way.material_dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import cn.way.material_dialogs.internal.MDRootLayout;

/**
 * <pre>
 *     author: Way Lin
 *     date  : 2018.02.03
 *     desc  :
 * </pre>
 */

class DialogBase extends Dialog implements DialogInterface.OnShowListener {
    protected MDRootLayout view;
    private OnShowListener showListener;

    public DialogBase(@NonNull Context context) {
        super(context);
    }

    public DialogBase(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public DialogBase(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    public <T extends View> T findViewById(int id) {
        return view.findViewById(id);
    }

    @Override
    public final void setOnShowListener(@Nullable OnShowListener listener) {
        showListener = listener;
    }

    final void setOnShowListenerInternal() {
        super.setOnShowListener(this);
    }

    final void setViewInternal(View view) {
        super.setContentView(view);
    }

    @Override
    public void onShow(DialogInterface dialog) {
        if (showListener != null) {
            showListener.onShow(dialog);
        }
    }

    @Override
    @Deprecated
    public void setContentView(int layoutResID) throws IllegalAccessError {
        throw new IllegalAccessError(
                "setContentView() is not supported in MaterialDialog. Specify a custom view in the Builder instead.");
    }

    @Override
    @Deprecated
    public void setContentView(View view) throws IllegalAccessError {
        throw new IllegalAccessError(
                "setContentView() is not supported in MaterialDialog. Specify a custom view in the Builder instead.");
    }

    @Override
    @Deprecated
    public void setContentView(View view, @Nullable ViewGroup.LayoutParams params)
            throws IllegalAccessError {
        throw new IllegalAccessError(
                "setContentView() is not supported in MaterialDialog. Specify a custom view in the Builder instead.");
    }
}
