package com.m3libea.nytimessearch.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.m3libea.nytimessearch.models.SearchQuery;

import org.parceler.Parcels;

import java.util.Calendar;

/**
 * Created by m3libea on 9/19/17.
 */

public class DataPickerFragment extends DialogFragment {

    public DataPickerFragment() {
    }

    public static DataPickerFragment newInstance(SearchQuery sQuery){

        DataPickerFragment fragment = new DataPickerFragment();
        Bundle args = new Bundle();
        args.putParcelable("query", Parcels.wrap(sQuery));
        fragment.setArguments(args);
        return fragment;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();

        SearchQuery query = Parcels.unwrap(getArguments().getParcelable("query"));

        if (query.getBeginDate() != null){
            c.setTime(query.getBeginDate());
        }

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Activity needs to implement this interface
        DatePickerDialog.OnDateSetListener listener = (DatePickerDialog.OnDateSetListener) getParentFragment();

        // Create a new instance of TimePickerDialog and return it
        return new DatePickerDialog(getActivity(), listener, year, month, day);
    }

}
