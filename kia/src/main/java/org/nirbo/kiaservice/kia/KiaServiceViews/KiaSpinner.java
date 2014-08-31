package org.nirbo.kiaservice.kia.KiaServiceViews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import org.nirbo.kiaservice.kia.R;

public class KiaSpinner extends Spinner implements AdapterView.OnItemSelectedListener {
    public KiaSpinner(Context context) {
        super(context);
        initSpinner();
    }

    public KiaSpinner(Context context, int mode) {
        super(context, mode);
        initSpinner();
    }

    public KiaSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        initSpinner();
    }

    public KiaSpinner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initSpinner();
    }

    public KiaSpinner(Context context, AttributeSet attrs, int defStyle, int mode) {
        super(context, attrs, defStyle, mode);
        initSpinner();
    }

    private void initSpinner() {
        this.setOnItemSelectedListener(this);
        this.setSelection(0);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        TextView mView = ((TextView)parent.getChildAt(position));

        if (position == 0) {
            mView.setTextColor(getResources().getColor(R.color.HintRed));
            mView.setGravity(Gravity.CENTER);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
}
