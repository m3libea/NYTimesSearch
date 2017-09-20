package com.m3libea.nytimessearch.fragments;

import android.app.DatePickerDialog;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.TextView;

import com.m3libea.nytimessearch.R;

import java.text.DateFormat;
import java.util.Calendar;


public class FilterFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener  {

    TextView tvDate;
    DateFormat df;
    public FilterFragment() {
        // Required empty public constructor
    }

    public static FilterFragment newInstance() {
        FilterFragment fragment = new FilterFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        df = DateFormat.getDateInstance(DateFormat.MEDIUM);

        // Inflate the layout for this fragment
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        return inflater.inflate(R.layout.fragment_filter, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        tvDate = (TextView) view.findViewById(R.id.tvDate);
        tvDate.setText(df.format(Calendar.getInstance().getTime()));

        tvDate.setOnClickListener(v -> showDatePickerDialog(v));
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        // Store access variables for window and blank point
        Window window = getDialog().getWindow();
        Point size = new Point();
        // Store dimensions of the screen in `size`
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        // Set the width of the dialog proportional to 75% of the screen width
        window.setLayout((int) (size.x * 0.75), WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        // Call super onResume after sizing
        super.onResume();
    }

    // attach to an onclick handler to show the date picker
    public void showDatePickerDialog(View v) {
        DataPickerFragment newFragment = new DataPickerFragment();
        newFragment.show(getChildFragmentManager(), "dataPicker");
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        // store the values selected into a Calendar instance
        final Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        tvDate.setText(df.format(c.getTime()));
    }
}
