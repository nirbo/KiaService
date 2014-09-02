package org.nirbo.kiaservice.kia;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.nirbo.kiaservice.kia.KiaServiceViews.KiaEditText;
import org.nirbo.kiaservice.kia.Utilities.Utilities;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    LinearLayout mRootView;

    private List<View> mViewsArrayList = new ArrayList<View>();
    private ArrayList<CharSequence> mSelectedDepartments = new ArrayList<CharSequence>();

//        TODO: Populate the department names from a DB values instead of hardcoded stuff.
    protected CharSequence[] mDepartmentList = { "מכונאות", "טיפולים", "חשמל", "מיזוג",
                                            "צמיגים", "פוליש ווקס", "פחחות וצבע" };

    private TextView mTitle;
    private KiaEditText mFullNameET;
    private KiaEditText mEmailET;
    private KiaEditText mCellPhoneET;
    private KiaEditText mCarTypeET;
    private KiaEditText mDepartmentET;

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

        mDepartmentET = (KiaEditText) findViewById(R.id.departmentET);
        mDepartmentET.setRtlField(true);
        mDepartmentET.setOnClickListener(mDepOnClickListener);
        mViewsArrayList.add(mDepartmentET);
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

    private View.OnClickListener mDepOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.departmentET:
                    showDepartmentsDialog();
                    break;
                default:
                    break;
            }
        }
    };

    private void showDepartmentsDialog() {
        boolean[] mCheckedDepartments = new boolean[mDepartmentList.length];
        int mDepCount = mDepartmentList.length;

        for (int i = 0; i < mDepCount; i++) {
            mCheckedDepartments[i] = mSelectedDepartments.contains(mDepartmentList[i]);
        }

        DialogInterface.OnMultiChoiceClickListener mMultiChoiceDialogListener = new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int selection, boolean isChecked) {
                if (isChecked) {
                    mSelectedDepartments.add(mDepartmentList[selection]);
                } else {
                    mSelectedDepartments.remove(mDepartmentList[selection]);
                }

                onChangeSelectedDepartments();
            }
        };

        AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(this);
        mDialogBuilder.setTitle(R.string.chooseDep);
        mDialogBuilder.setMultiChoiceItems(mDepartmentList, mCheckedDepartments, mMultiChoiceDialogListener);
        AlertDialog mDialog = mDialogBuilder.create();
        mDialog.show();
    }

    private void onChangeSelectedDepartments() {
        if (mSelectedDepartments.size() > 0) {
            mDepartmentET.setText(R.string.depSelected);
        } else {
            mDepartmentET.setText("");
            mDepartmentET.setHint(R.string.chooseDep);
            mDepartmentET.setHintTextColor(getResources().getColor(R.color.HintRed));
        }
    }

}









