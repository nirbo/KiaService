package org.nirbo.kiaservice.kia;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import org.nirbo.kiaservice.kia.KiaServiceViews.KiaEditText;
import org.nirbo.kiaservice.kia.Utilities.Utilities;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    LinearLayout mRootView;

    private List<View> mViewsArrayList = new ArrayList<View>();
    private TextView mTitle;
    private KiaEditText mFullNameET;
    private KiaEditText mEmailET;
    private KiaEditText mCellPhoneET;
    private KiaEditText mCarTypeET;
    private Spinner mDepartmentSpin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        mRootView = (LinearLayout) findViewById(R.id.rootLinearLayout);

        initViews();
        setDynamicViewSizes();
        assignCustomFont();

    }




    private void initViews() {
        mTitle = (TextView) findViewById(R.id.titleTextView);

        mFullNameET = (KiaEditText) findViewById(R.id.fullNameET);
        mFullNameET.setRtlField(true);
        mViewsArrayList.add(mFullNameET);

        mEmailET = (KiaEditText) findViewById(R.id.emailET);
        mEmailET.setRtlField(false);
        mViewsArrayList.add(mEmailET);

        mCellPhoneET = (KiaEditText) findViewById(R.id.cellPhoneET);
        mCellPhoneET.setRtlField(false);
        mViewsArrayList.add(mCellPhoneET);

        mCarTypeET = (KiaEditText) findViewById(R.id.carTypeET);
        mCarTypeET.setRtlField(true);
        mViewsArrayList.add(mCarTypeET);

        mDepartmentSpin = (Spinner) findViewById(R.id.departmentSpinner);
        mDepartmentSpin.setSelection(0);
        mViewsArrayList.add(mDepartmentSpin);
    }

    private void setDynamicViewSizes() {
        Display mDisplay = getWindowManager().getDefaultDisplay();
        Point mDisplaySize = Utilities.getDisplaySize(mDisplay);
        int mDispalyWidth = mDisplaySize.x;
        int mDispalyHeight = mDisplaySize.y;
        Float mNewWidth = (float) (mDispalyWidth / 1.5);
        Float mNewHeight = (float) (mDispalyHeight / 18);

        for (View view : mViewsArrayList) {
            ViewGroup.LayoutParams mParams = view.getLayoutParams();
            mParams.width = (mNewWidth.intValue());
            mParams.height = (mNewHeight.intValue());
            view.setLayoutParams(mParams);
        }
    }

    private void assignCustomFont() {
        Utilities.setTypefaceOnChildViews(Utilities.getRootView(mRootView),
                Utilities.getCustomTypeface(getApplicationContext()));
    }



}
