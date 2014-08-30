package org.nirbo.kiaservice.kia.EditTexts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import org.nirbo.kiaservice.kia.R;

public class KiaEditText extends EditText {

    int sdkVersion = Build.VERSION.SDK_INT;
    boolean rtlField;

    public boolean isRtlField() {
        return rtlField;
    }

    public void setRtlField(boolean rtlField) {
        this.rtlField = rtlField;
    }

    public KiaEditText(Context context) {
        super(context);
        initEditText();
    }

    public KiaEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initEditText();
    }

    public KiaEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initEditText();
    }

    @SuppressLint("NewApi")
    private void initEditText() {
        this.setOnFocusChangeListener(mOnFocusChangeListener);
    }

    private OnFocusChangeListener mOnFocusChangeListener = new OnFocusChangeListener() {

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            TransitionDrawable mDefaultBackgroundTransition = (TransitionDrawable) v.getBackground();
            KiaEditText mView = (KiaEditText) v;

            if (hasFocus) {
                mDefaultBackgroundTransition.startTransition(150);
                mView.addTextChangedListener(mTextWatcher);
            } else {
                mDefaultBackgroundTransition.reverseTransition(150);
            }
        }
    };

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void afterTextChanged(Editable s) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            KiaEditText mView = KiaEditText.this;

            if (! mView.isRtlField() && s.length() >= 1) {
                mView.setGravity(Gravity.LEFT);
            } else if (! mView.isRtlField() && s.length() == 0) {
                mView.setGravity(Gravity.RIGHT);
            }

            if (s.length() > 0) {
                mView.setSelection(s.length());
            }
        }
    };

}
