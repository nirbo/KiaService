package org.nirbo.kiaservice.kia.KiaServiceViews;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import org.nirbo.kiaservice.kia.R;

import java.util.ArrayList;

public class KiaSpinnerAdapter extends BaseAdapter {

    LayoutInflater mInflater;
    ArrayList<String> mStrings;

    public KiaSpinnerAdapter(Context context, ArrayList<String> mArrayList) {
        mInflater = LayoutInflater.from(context);
        mStrings = mArrayList;
    }

    @Override
    public int getCount() {
        return mStrings.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(R.layout.spinner_element, null);
        TextView mTextView = (TextView) convertView.findViewById(R.id.spinnerElement);

        mTextView.setTextSize(R.dimen.text_small);
        mTextView.setGravity(Gravity.CENTER);

        return convertView;
    }
}
