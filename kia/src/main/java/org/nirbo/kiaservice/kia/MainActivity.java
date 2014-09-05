package org.nirbo.kiaservice.kia;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.format.DateFormat;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.doomonafireball.betterpickers.calendardatepicker.CalendarDatePickerDialog;
import com.doomonafireball.betterpickers.radialtimepicker.RadialPickerLayout;
import com.doomonafireball.betterpickers.radialtimepicker.RadialTimePickerDialog;
import org.joda.time.DateTime;
import org.nirbo.kiaservice.kia.KiaServiceViews.KiaEditText;
import org.nirbo.kiaservice.kia.Utilities.Utilities;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends SherlockFragmentActivity
        implements CalendarDatePickerDialog.OnDateSetListener,
        RadialTimePickerDialog.OnTimeSetListener {

    private static final String DATE_PICKER_FRAGMENT = "datePickerFragment";
    private static final String TIME_PICKER_FRAGMENT = "timePickerFragment";

    LinearLayout mRootView;
    ScrollView mMainScrollView;

    private List<View> mViewsArrayList = new ArrayList<View>();
    private ArrayList<CharSequence> mSelectedDepartments = new ArrayList<CharSequence>();

    //        TODO: Populate the department names from DB values instead of hardcoded stuff.
    private CharSequence[] mDepartmentList = {"מכונאות", "טיפולים", "חשמל", "מיזוג",
            "צמיגים", "פוליש ווקס", "פחחות וצבע"};

    private TextView mTitle;
    private KiaEditText mFullNameET;
    private KiaEditText mEmailET;
    private KiaEditText mCellPhoneET;
    private KiaEditText mCarTypeET;
    private KiaEditText mDepartmentET;
    private KiaEditText mRequestedServiceET;
    private KiaEditText mDatePickerET;
    private KiaEditText mTimePickerET;
    private KiaEditText mCarTransportET;
    private KiaEditText mCommentsET;
    private Button mSubmitButton;

    private Boolean carTransportRequired;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        attachHideKeyboardListeners();
        initViews();
        setDynamicViewSizes();
        assignCustomFont();
    }


    @Override
    public void onResume() {
        super.onResume();

        CalendarDatePickerDialog mCalendarDatePickerDialog = (CalendarDatePickerDialog) getSupportFragmentManager().findFragmentByTag(DATE_PICKER_FRAGMENT);
        if (mCalendarDatePickerDialog != null) {
            mCalendarDatePickerDialog.setOnDateSetListener(this);
        }

        RadialTimePickerDialog mRadialTimePickerDialog = (RadialTimePickerDialog) getSupportFragmentManager().findFragmentByTag(TIME_PICKER_FRAGMENT);
        if (mRadialTimePickerDialog != null) {
            mRadialTimePickerDialog.setOnTimeSetListener(this);
        }
    }


    /* ************** */
    /* Global Methods */
    /* ************** */

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

        mRequestedServiceET = (KiaEditText) findViewById(R.id.requestedServiceET);
        mRequestedServiceET.setRtlField(true);
        mViewsArrayList.add(mRequestedServiceET);

        mDatePickerET = (KiaEditText) findViewById(R.id.datePickET);
        mDatePickerET.setRtlField(false);
        mDatePickerET.setOnClickListener(mDatePickerOnClickListener);
        mViewsArrayList.add(mDatePickerET);

        mTimePickerET = (KiaEditText) findViewById(R.id.timePickET);
        mTimePickerET.setRtlField(false);
        mTimePickerET.setOnClickListener(mTimePickerOnClickListener);
        mViewsArrayList.add(mTimePickerET);

        mCarTransportET = (KiaEditText) findViewById(R.id.carTransportET);
        mCarTransportET.setRtlField(true);
        mCarTransportET.setOnClickListener(mCarTransportOnClickListener);
        mViewsArrayList.add(mCarTransportET);

        mCommentsET = (KiaEditText) findViewById(R.id.commentsET);
        mCommentsET.setRtlField(true);
        mViewsArrayList.add(mCommentsET);

        mSubmitButton = (Button) findViewById(R.id.submitButton);
        mViewsArrayList.add(mSubmitButton);
    }

    protected void setDynamicViewSizes() {
        Display mDisplay = getWindowManager().getDefaultDisplay();
        Point mDisplaySize = Utilities.getDisplaySize(mDisplay);
        int mDispalyWidth = mDisplaySize.x;
        int mDispalyHeight = mDisplaySize.y;
        Float mNewHeight;
        Float mNewWidth;

        for (View view : mViewsArrayList) {
            ViewGroup.LayoutParams mParams = view.getLayoutParams();

            if (view.getId() == R.id.submitButton) {
                mNewWidth = (float) (mDispalyWidth / 1.9);
            } else {
                mNewWidth = (float) (mDispalyWidth / 1.4);
            }

            if (view.getId() == R.id.requestedServiceET) {
                mNewHeight = (float) (mDispalyHeight / 7.5);
            } else if (view.getId() == R.id.commentsET) {
                mNewHeight = (float) (mDispalyHeight / 5);
            } else if (view.getId() == R.id.submitButton) {
                mNewHeight = (float) (mDispalyHeight / 14);
            } else {
                mNewHeight = (float) (mDispalyHeight / 18);
            }

            mParams.width = (mNewWidth.intValue());
            mParams.height = (mNewHeight.intValue());
            view.setLayoutParams(mParams);
        }
    }

    protected void assignCustomFont() {
        Utilities.setTypefaceOnChildViews(Utilities.getRootView(mRootView),
                Utilities.getCustomTypeface(getApplicationContext()));
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

    private void attachHideKeyboardListeners() {
        mRootView = (LinearLayout) findViewById(R.id.rootLinearLayout);
        mRootView.setOnTouchListener(mHideKeyboardListener);
        mMainScrollView = (ScrollView) findViewById(R.id.mainScrollView);
        mMainScrollView.setOnTouchListener(mHideKeyboardListener);
    }


    /* *************** */
    /* Event Listeners */
    /* *************** */

    private View.OnTouchListener mHideKeyboardListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            Utilities.hideKeyboard(MainActivity.this, view);

            return false;
        }
    };

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

        DialogInterface.OnClickListener mMultiChoiceButtonListener = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        };

        AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(this);
        mDialogBuilder.setTitle(R.string.chooseDep)
                        .setMultiChoiceItems(mDepartmentList, mCheckedDepartments, mMultiChoiceDialogListener)
                        .setPositiveButton(R.string.proceed, mMultiChoiceButtonListener);
        AlertDialog mDialog = mDialogBuilder.create();
        mDialog.show();
    }

    public View.OnClickListener mDatePickerOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentManager fm = getSupportFragmentManager();
            DateTime mCurrentTime = new DateTime();

            CalendarDatePickerDialog mCalendarDatePickerDialog = CalendarDatePickerDialog
                    .newInstance(MainActivity.this, mCurrentTime.getYear(), mCurrentTime.getMonthOfYear() - 1, mCurrentTime.getDayOfMonth());

            mCalendarDatePickerDialog.show(fm, DATE_PICKER_FRAGMENT);
        }
    };

    @Override
    public void onDateSet(CalendarDatePickerDialog mDialog, int year, int monthOfYear, int dayOfMonth) {
        mDatePickerET.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
    }

    public View.OnClickListener mTimePickerOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentManager fm = getSupportFragmentManager();
            DateTime mCurrentTime = new DateTime();

            RadialTimePickerDialog mTimePickerDialog = RadialTimePickerDialog
                    .newInstance(MainActivity.this, mCurrentTime.getHourOfDay(), mCurrentTime.getMinuteOfHour(), DateFormat.is24HourFormat(MainActivity.this));

            mTimePickerDialog.show(fm, TIME_PICKER_FRAGMENT);
        }
    };

    @Override
    public void onTimeSet(RadialPickerLayout radialPickerLayout, int hourOfDay, int minuteOfHour) {
        mTimePickerET.setText(hourOfDay + ":" + minuteOfHour);
    }

    public View.OnClickListener mCarTransportOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.carTransportET:
                    carTransportRequest();
                    break;
                default:
                    break;
            }
        }
    };

    private void carTransportRequest() {
        DialogInterface.OnClickListener mDialogListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        mCarTransportET.setText(R.string.carTransportTrue);
                        carTransportRequired = true;
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        mCarTransportET.setText(R.string.carTransportFalse);
                        carTransportRequired = false;
                        break;
                }
            }
        };

        AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(this);
        mDialogBuilder.setMessage(R.string.carTransport)
                .setPositiveButton(R.string.carTransportPositiveButton, mDialogListener)
                .setNegativeButton(R.string.carTransportNegativeButton, mDialogListener)
                .show();
    }




}