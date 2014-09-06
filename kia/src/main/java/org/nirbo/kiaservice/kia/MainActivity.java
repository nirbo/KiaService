package org.nirbo.kiaservice.kia;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.HandlerThread;
import android.support.v4.app.FragmentManager;
import android.text.format.DateFormat;
import android.view.*;
import android.widget.*;
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

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        attachHideKeyboardListeners();
        initViews();
        setDynamicViewSizes();
        assignCustomFont();
    }

    @Override
    protected void onSaveInstanceState(Bundle saveState) {
        super.onSaveInstanceState(saveState);

        saveState.putCharSequenceArrayList("departments", mSelectedDepartments);
        saveState.putBoolean("carTransport", carTransportRequired);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mSelectedDepartments = savedInstanceState.getCharSequenceArrayList("departments");
        carTransportRequired = savedInstanceState.getBoolean("carTransport");
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

    @Override
    public void onBackPressed() {
        showQuitConfirmationDialog();
    }



    /* ************** */
    /* Global Methods */
    /* ************** */

    private void initViews() {
        mTitle = (TextView) findViewById(R.id.titleTextView);

        mFullNameET = (KiaEditText) findViewById(R.id.fullNameET);
        mFullNameET.setRtlField(true);
        mFullNameET.setMandatory(true);
        mViewsArrayList.add(mFullNameET);

        mEmailET = (KiaEditText) findViewById(R.id.emailET);
        mEmailET.setRtlField(false);
        mEmailET.setMandatory(false);
        mViewsArrayList.add(mEmailET);

        mCellPhoneET = (KiaEditText) findViewById(R.id.cellPhoneET);
        mCellPhoneET.setRtlField(false);
        mCellPhoneET.setMandatory(true);
        mViewsArrayList.add(mCellPhoneET);

        mCarTypeET = (KiaEditText) findViewById(R.id.carTypeET);
        mCarTypeET.setRtlField(true);
        mCarTypeET.setMandatory(true);
        mViewsArrayList.add(mCarTypeET);

        mDepartmentET = (KiaEditText) findViewById(R.id.departmentET);
        mDepartmentET.setRtlField(true);
        mDepartmentET.setOnClickListener(mDepOnClickListener);
        mViewsArrayList.add(mDepartmentET);

        mRequestedServiceET = (KiaEditText) findViewById(R.id.requestedServiceET);
        mRequestedServiceET.setRtlField(true);
        mRequestedServiceET.setMandatory(true);
        mViewsArrayList.add(mRequestedServiceET);

        mDatePickerET = (KiaEditText) findViewById(R.id.datePickET);
        mDatePickerET.setRtlField(false);
        mDatePickerET.setMandatory(true);
        mDatePickerET.setOnClickListener(mDatePickerOnClickListener);
        mViewsArrayList.add(mDatePickerET);

        mTimePickerET = (KiaEditText) findViewById(R.id.timePickET);
        mTimePickerET.setRtlField(false);
        mTimePickerET.setMandatory(true);
        mTimePickerET.setOnClickListener(mTimePickerOnClickListener);
        mViewsArrayList.add(mTimePickerET);

        mCarTransportET = (KiaEditText) findViewById(R.id.carTransportET);
        mCarTransportET.setRtlField(true);
        mCarTransportET.setMandatory(false);
        mCarTransportET.setOnClickListener(mCarTransportOnClickListener);
        mViewsArrayList.add(mCarTransportET);

        mCommentsET = (KiaEditText) findViewById(R.id.commentsET);
        mCommentsET.setRtlField(true);
        mCommentsET.setMandatory(false);
        mViewsArrayList.add(mCommentsET);

        mSubmitButton = (Button) findViewById(R.id.submitButton);
        mSubmitButton.setOnClickListener(mSubmitButtonOnClickListener);
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

    private void showQuitConfirmationDialog() {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle(R.string.quitApp)
                .setMessage(R.string.quitConfirm)
                .setPositiveButton(R.string.positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.this.finish();
                    }
                })

                .setNegativeButton(R.string.negative, null)
                .show();
    }

    private boolean verifyMandatoryFields() {
        boolean isValid = true;

        for (View view : mViewsArrayList) {
            if (view instanceof KiaEditText && ((KiaEditText) view).isMandatory()) {
                String mViewInput = ((KiaEditText) view).getText().toString();

                if ("".matches(mViewInput)) {
                    isValid = false;
                    ((KiaEditText) view).setError(getResources().getString(R.string.mandatoryField));
                }
            }
        }

        return isValid;
    }

    private void showInvalidFieldsDialog() {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle(R.string.invalidMandatory)
                .setMessage(R.string.invalidMessage)
                .setPositiveButton(R.string.returnToForm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void normalizeViewBackground(View view) {
        int mDeviceSdk = Utilities.getDeviceSdkVersion();
        Drawable mBackground = getResources().getDrawable(R.drawable.edittext_transition);

        if (mDeviceSdk >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(mBackground);
        } else {
            view.setBackgroundDrawable(mBackground);
        }
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
                .setPositiveButton(R.string.positive, mDialogListener)
                .setNegativeButton(R.string.negative, mDialogListener)
                .show();
    }

    private View.OnClickListener mSubmitButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            boolean mFieldsVerified = verifyMandatoryFields();

            if (! mFieldsVerified) {
                showInvalidFieldsDialog();
            } else {
                // TODO: Insert values into DB
            }
        }
    };



}