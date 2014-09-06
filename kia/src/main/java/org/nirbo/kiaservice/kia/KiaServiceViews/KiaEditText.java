package org.nirbo.kiaservice.kia.KiaServiceViews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import org.nirbo.kiaservice.kia.R;
import org.nirbo.kiaservice.kia.Utilities.Utilities;

public class KiaEditText extends EditText  {

    boolean rtlField;
    boolean mandatory;

    public boolean isRtlField() {
        return rtlField;
    }

    public void setRtlField(boolean rtlField) {
        this.rtlField = rtlField;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
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
                mDefaultBackgroundTransition.startTransition(100);
                mView.addTextChangedListener(mTextWatcher);
                mView.performClick();

                if (mView.getId() == R.id.departmentET) {
                    Utilities.hideKeyboard(getContext(), mView);
                }
            } else {
                mDefaultBackgroundTransition.reverseTransition(100);
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

            if (s.length() > 0) {
                mView.setCursorVisible(true);
            } else {
                mView.setCursorVisible(false);
            }
        }
    };

}
