package com.example.lab203_07.healthy;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class BMIFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bmi, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initCal();
        initBack();
    }

    public void initCal(){
        Button _calBtn = getView().findViewById(R.id.bmi_cal_btn);
        _calBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _heightStr = ((EditText)(getView().findViewById(R.id.bmi_height))).getText().toString();
                String _weightStr = ((EditText)(getView().findViewById(R.id.bmi_weight))).getText().toString();
                if(_heightStr.isEmpty() && _weightStr.isEmpty()){
                    Toast.makeText(getActivity(), "กรุณาระบุข้อมูลให้ครบถ้วน", Toast.LENGTH_SHORT);
                    Log.d("BMI", "FIELD NAME IS EMPTY");
                }else{
                    Float total = Float.parseFloat(_weightStr)/((Float.parseFloat(_heightStr)/100)*(Float.parseFloat(_heightStr)/100));
                    TextView _total = getView().findViewById(R.id.bmi_result);
                    _total.setText(total.toString());
                    Log.d("BMI", "BMI IS VALUE"+total);
                }
            }
        });
    }

    public void initBack(){
        TextView _backbtn = getView().findViewById(R.id.bmi_back_btn);
        _backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("BMI", "BACK TO MENU");
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new MenuFragment()).addToBackStack(null).commit();
            }
        });
    }
}
