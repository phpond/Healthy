package com.example.lab203_07.healthy.Sleep;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lab203_07.healthy.R;

import java.util.ArrayList;
import java.util.List;

public class SleepAdapter extends ArrayAdapter {

    private List<Sleep> _sleeps = new ArrayList<>();
    Context context;

    public SleepAdapter(Context context, int resource, List<Sleep> _sleeps) {
        super(context, resource, _sleeps);
        this._sleeps = _sleeps;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View _sleepItem = LayoutInflater.from(context).inflate(R.layout.fragment_sleep_item, parent, false);

        TextView _date = _sleepItem.findViewById(R.id.date_sleep_item);
        TextView _timeSleep = _sleepItem.findViewById(R.id.time_to_sleep_wake_item);
        TextView _timeDiff = _sleepItem.findViewById(R.id.total_time_sleep_item);

        _date.setFocusable(false); _date.setFocusableInTouchMode(false);
        _timeSleep.setFocusable(false); _timeSleep.setFocusableInTouchMode(false);
        _timeDiff.setFocusable(false); _timeDiff.setFocusableInTouchMode(false);

        Sleep _row = _sleeps.get(position);
        _date.setText(_row.getDateToSleep());
        _timeSleep.setText(String.format("%s - %s", _row.getTimeToSleep(), _row.getTimeToWakeUp()));
        _timeDiff.setText(_row.getTotalSleep());

        return _sleepItem;
    }
}
