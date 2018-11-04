package com.example.lab203_07.healthy.Sleep;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lab203_07.healthy.R;
import com.example.lab203_07.healthy.fragments.MenuFragment;

import java.util.Calendar;

public class SleepFormFragment extends Fragment {

    String[] _month = {"ม.ค.", "ก.พ.", "มี.ค.", "เม.ย.", "พ.ย.", "มิ.ย.", "ก.ค.", "ส.ค.", "ก.ย.", "ต.ค.", "พ.ย.", "ธ.ค."};
//    String[] _month = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "July", "Aug", "Sep", "Oct", "Nov", "Dec"};

    private int year;
    private int month;
    private int day;
    private DatePickerDialog.OnDateSetListener mDateDataListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sleep_form, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initSaveBtn();
        initDate();
        initBack();
    }

    private void initSaveBtn(){
        Log.d("SLEEP_FORM", "Click save");
        Button _saveBtn = getView().findViewById(R.id.sleep_form_save_btn);
        try {
            _saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String _date = ((TextView)(getView().findViewById(R.id.sleep_form_date))).getText().toString();
                    String _timeToSleep = ((TextView)(getView().findViewById(R.id.sleep_form_time_sleep))).getText().toString();
                    String _timeToWake = ((TextView)(getView().findViewById(R.id.sleep_form_time_wake_up))).getText().toString();
                    Log.d("SLEEP_FORM", "on click");

                    Sleep _sleep = new Sleep(_date, _timeToSleep, _timeToWake);

                    if(_date.isEmpty() || _timeToSleep.isEmpty() || _timeToWake.isEmpty()){
                        Toast.makeText(getActivity(), "บันทึกข้อมูลไม่ครบถ้วน", Toast.LENGTH_SHORT).show();
                        Log.d("SLEEP_FORM", "Information is empty");
                    }else{
                        saveOnDevice(_sleep);
                        Toast.makeText(getActivity(), "บันทึกข้อมูลเรียบร้อย", Toast.LENGTH_SHORT).show();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new SleepFragment()).addToBackStack(null).commit();
                        Log.d("SLEEP_FORM", "Goto Sleep form");
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveOnDevice(Sleep sleep){
        SQLiteDatabase myDB = getActivity().openOrCreateDatabase("my.db",Context.MODE_PRIVATE, null);
        //create database on device
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

        //create obj
        ContentValues row = new ContentValues();
        sleep.setContentValues();
        row = sleep.getContentValues();
        Log.d("SLEEP_FORM", "Create obj Success \n Date : "+sleep.getDateToSleep()+"\nTime sleep : "+sleep.getTimeToSleep()
                +"\nTime wake : "+sleep.getTimeToWakeUp()+"\nTime Diff: "+sleep.getTotalSleep());

        //insert
        myDB.insert("user", null, row);
        Log.d("SLEEP_FORM", "Insert on db Success");

    }

    void initDate(){
        Log.d("SLEEP_FORM", "Click Date");
        final TextView _dateEdit = getView().findViewById(R.id.sleep_form_date);
        try{
            _dateEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar cal = Calendar.getInstance();
                    year = cal.get(Calendar.YEAR);
                    month = cal.get(Calendar.MONTH);
                    day = cal.get(Calendar.DAY_OF_MONTH);
                    Log.d("SLEEP_FORM", "Click calendar");

                    DatePickerDialog dialog = new DatePickerDialog(getContext(),
                            android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                            mDateDataListener,
                            year,month,day);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                }
            });
        } catch (Exception e) {
        e.printStackTrace();
    }
        mDateDataListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                _dateEdit.setText(String.format("%02d/%02d/%02d", day, month+1, year));
                Log.d("SLEEP_FORM", "On date : "+ day +"/"+month+1 + "/"+year);
            }
        };
    }

    void initBack(){
        Button _btn = getView().findViewById(R.id.sleep_form_back_btn);
        _btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new MenuFragment()).addToBackStack(null).commit();
                Log.d("FORM", "COME BACK TO MENU");
            }
        });
    }

}
