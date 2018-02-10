package cn.way.material_dialogs.prefs;

import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import java.lang.reflect.Method;

import cn.way.material_dialogs.common.R;


/**
 * @author Aidan Follestad (afollestad)
 */
class PrefUtil {

    private PrefUtil() {
    }

    static void setLayoutResource(
            Context context, Preference preference, @Nullable AttributeSet attrs) {
        boolean foundLayout = false;
        if (attrs != null) {
            for (int i = 0; i < attrs.getAttributeCount(); i++) {
                final String namespace = ((XmlResourceParser) attrs).getAttributeNamespace(0);
                if (namespace.equals("http://schemas.android.com/apk/res/android")
                        && attrs.getAttributeName(i).equals("layout")) {
                    foundLayout = true;
                    break;
                }
            }
        }

        boolean useStockLayout = false;
        if (attrs != null) {
            TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.Preference, 0, 0);
            try {
                useStockLayout = a.getBoolean(R.styleable.Preference_useStockLayout, false);
            } finally {
                a.recycle();
            }
        }

        if (!foundLayout && !useStockLayout) {
            preference.setLayoutResource(R.layout.md_preference_custom);
        }
    }

    static void registerOnActivityDestroyListener(
            Preference preference, PreferenceManager.OnActivityDestroyListener listener) {
        try {
            PreferenceManager pm = preference.getPreferenceManager();
            Method method =
                    pm.getClass()
                            .getDeclaredMethod(
                                    "registerOnActivityDestroyListener",
                                    PreferenceManager.OnActivityDestroyListener.class);
            method.setAccessible(true);
            method.invoke(pm, listener);
        } catch (Exception ignored) {
        }
    }

    static void unregisterOnActivityDestroyListener(
            Preference preference, PreferenceManager.OnActivityDestroyListener listener) {
        try {
            PreferenceManager pm = preference.getPreferenceManager();
            Method method =
                    pm.getClass()
                            .getDeclaredMethod(
                                    "unregisterOnActivityDestroyListener",
                                    PreferenceManager.OnActivityDestroyListener.class);
            method.setAccessible(true);
            method.invoke(pm, listener);
        } catch (Exception ignored) {
        }
    }
}