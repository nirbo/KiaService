package org.nirbo.kiaservice.kia.Utilities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Build;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.nirbo.kiaservice.kia.BuildConfig;

public class Utilities {

    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    public static Point getDisplaySize(final Display display) {
        final Point point = new Point();

        try {
            display.getSize(point);
        } catch (java.lang.NoSuchMethodError ignore) {
            point.x = display.getWidth();
            point.y = display.getHeight();
        }

        return point;
    }

    public static Typeface getCustomTypeface(Context context) {
        final String mCustomFont = "fonts/Alef-regular.ttf";
        Typeface mTypeface = Typeface.createFromAsset(context.getAssets(), mCustomFont);

        return mTypeface;
    }

    public static ViewGroup getRootView(View view) {
        ViewGroup mViewGroup = null;

        if (view != null) {
            mViewGroup = (ViewGroup) view.getRootView();
        }

        return mViewGroup;
    }

    public static void setTypefaceOnChildViews(ViewGroup viewGroup, Typeface font) {
        if (viewGroup != null) {
            int mViewGroupCount = viewGroup.getChildCount();

            for (int i = 0; i < mViewGroupCount; i++) {
                if (viewGroup.getChildAt(i) == null) {
                    continue;
                }

                if (viewGroup.getChildAt(i) instanceof ViewGroup) {
                    setTypefaceOnChildViews((ViewGroup) viewGroup.getChildAt(i), font);
                } else {
                    View view = viewGroup.getChildAt(i);

                    if (view instanceof TextView) {
                        ((TextView)(view)).setTypeface(font);
                    } else if (view instanceof EditText) {
                        ((EditText)(view)).setTypeface(font);
                    } else if (view instanceof Button) {
                        ((Button)(view)).setTypeface(font);
                    }
                }
            }
        }
    }

    public static void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static int getDeviceSdkVersion() {
        return Build.VERSION.SDK_INT;
    }
}