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
import android.widget.AdapterView;
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

        ListView _sleepList = getView().findViewById(R.id.sleep_list);
        final SleepAdapter _sleepAdapter = new SleepAdapter(getActivity(), R.layout.fragment_sleep_item, sleeps);
        _sleepAdapter.clear();
        getDataFromSql();
        _sleepList.setAdapter(_sleepAdapter);
        _sleepList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        initAddSleepBtn();
        initSleepList(_sleepList);

    }

    public void initAddSleepBtn(){
        Log.d("SLEEP", "Sleep btn on click");
        Button _addWeight = getView().findViewById(R.id.add_sleep_btn);
        _addWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSleepForm();
            }
        });
    }

    private void getDataFromSql(){
        //open db
        myDB = getActivity().openOrCreateDatabase("my.db", Context.MODE_PRIVATE, null);

        try{
            myDB.execSQL("CREATE TABLE IF NOT EXISTS user(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " _date VARCHAR(8), " +
                    "_timeSleep VARCHAR(5), " +
                    "_timeWakeup VARCHAR(5))");
            Log.d("SLEEP_FORM", "Create db Success");
        }catch (Exception e){
            e.printStackTrace();
            Log.d("SLEEP_FORM", "error : "+e.toString());
        }

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

    private void initSleepList(ListView list){
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Log.d("SLEEP", "Item position : "+position);

                //create bundle
                Bundle bundle = new Bundle();
                bundle.putInt("_id", position);

                //frag instance
                SleepFormFragment sleepFormFrag = new SleepFormFragment();
                sleepFormFrag.setArguments(bundle);

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, sleepFormFrag)
                        .addToBackStack(null).commit();
                Log.d("SLEEP", "GO TO SLEEP FORM");

                Log.d("SLEEP", "Go to form\nBundle : "+bundle+"\nInt"+bundle.getInt("_id"));
            }
        });
    }

    private void goToSleepForm(){
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_view, new SleepFormFragment())
                .addToBackStack(null).commit();
        Log.d("SLEEP", "GO TO SLEEP FORM");
    }

}
