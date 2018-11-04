package com.example.lab203_07.healthy.Sleep;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.lab203_07.healthy.R;

import java.util.ArrayList;

public class SleepFragment extends Fragment {

    SQLiteDatabase myDB;
    ArrayList<Sleep> sleeps = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sleep, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initAddSleepBtn();

        ListView _sleepList = getView().findViewById(R.id.sleep_list);
        final SleepAdapter _sleepAdapter = new SleepAdapter(getActivity(), R.layout.fragment_sleep_item, sleeps);
        _sleepAdapter.clear();
        getDataFromSql();
        _sleepList.setAdapter(_sleepAdapter);

    }

    public void initAddSleepBtn(){
        Button _addWeight = getView().findViewById(R.id.add_sleep_btn);
        _addWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new SleepFormFragment()).addToBackStack(null).commit();
                Log.d("SLEEP", "GO TO SLEEP FORM");
            }
        });
    }

    private void getDataFromSql(){
        //open db
        myDB = getActivity().openOrCreateDatabase("my.db", Context.MODE_PRIVATE, null);

        //query
        Cursor myCursor = myDB.rawQuery("SELECT * FROM user", null);
        while(myCursor.moveToNext()){
            String _date = myCursor.getString(1);
            String _timeSleep = myCursor.getString(2);
            String _timeWake = myCursor.getString(3);

            Log.d("SLEEP", "_id : "+myCursor.getInt(0)+" date : "+_date + " Sleep : "+_timeSleep+" Wake : "+_timeWake);

            sleeps.add(new Sleep(_date, _timeSleep, _timeWake));
        }
        myCursor.close();
    }
}
