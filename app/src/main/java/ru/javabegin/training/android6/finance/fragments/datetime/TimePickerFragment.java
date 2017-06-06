package ru.javabegin.training.android6.finance.fragments.datetime;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.widget.TimePicker;

import java.util.Calendar;

import ru.javabegin.training.android6.finance.utils.AppContext;


public class TimePickerFragment extends DialogFragment  {

    private static Calendar calendar;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        calendar = (Calendar) getArguments().getSerializable(AppContext.DATE_CALENDAR);

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), (OnTimeSetListener)getActivity(), hour, minute, true);
    }



}
