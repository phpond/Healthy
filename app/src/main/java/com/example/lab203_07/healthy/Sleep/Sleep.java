package com.example.lab203_07.healthy.Sleep;

import android.content.ContentValues;
import android.util.Log;

public class Sleep {

    private String dateToSleep; //format DD/MM/YYYY
    private String timeToSleep; //format HH:MM
    private String timeToWakeUp; //format HH:MM
    private String totalSleep; //format HH:MM

    private ContentValues contentValues = new ContentValues();

    public Sleep(String _dateToSleep, String _timeToSleep, String _timeToWakeUp) {
        this.dateToSleep = _dateToSleep;
        this.timeToSleep = _timeToSleep;
        this.timeToWakeUp = _timeToWakeUp;

        calculateTime(_timeToSleep, _timeToWakeUp);
    }

    public String getDateToSleep() {
        return dateToSleep;
    }

    public void setDateToSleep(String dateToSleep) {
        this.dateToSleep = dateToSleep;
    }

    public String getTimeToSleep() {
        return timeToSleep;
    }

    public void setTimeToSleep(String timeToSleep) {
        this.timeToSleep = timeToSleep;
    }

    public String getTimeToWakeUp() {
        return timeToWakeUp;
    }

    public void setTimeToWakeUp(String timeToWakeUp) {
        this.timeToWakeUp = timeToWakeUp;
    }

    public String getTotalSleep() {
        return totalSleep;
    }

    public void setTotalSleep(String totalSleep) {
        this.totalSleep = totalSleep;
    }

    private void calculateTime(String _timeSleep, String _timeWake){
        int _sleepH; int _sleepM;
        int _wakeH; int _wakeM;
        int _totalH; int _totalM;

        String[] _sleepList = _timeSleep.split(":");
        Log.d("SLEEP", "Sleep list :"+_sleepList);
        _sleepH = Integer.parseInt(_sleepList[0]);
        _sleepM = Integer.parseInt(_sleepList[1]);

        String[] _wakeList = _timeWake.split(":");
        Log.d("SLEEP", "Wake list :"+_wakeList);
        _wakeH = Integer.parseInt(_wakeList[0]);
        _wakeM = Integer.parseInt(_wakeList[1]);

        //cal hour
        if(_sleepH > _wakeH ){
            _totalH = _sleepH - _wakeH;
        }else{
            _totalH = _wakeH - _sleepH;
        }

        //cal minute
        if(_sleepM > _wakeM){
            _totalM = ((60-_wakeM)+_sleepM)%60;
            _totalH += ((60-_wakeM)+_sleepM)/60;
        }else{
            _totalM = _wakeM - _sleepM;
        }

        //set total
        totalSleep = String.format("%02d:%02d", _totalH, _totalM);
        Log.d("SLEEP", "Time diff :"+totalSleep);
    }

    public ContentValues getContentValues() {
        return contentValues;
    }

    public void setContentValues() {
        this.contentValues.put("_date", getDateToSleep());
        this.contentValues.put("_timeSleep", getTimeToSleep());
        this.contentValues.put("_timeWakeup", getTimeToWakeUp());

    }
}
